/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.portletbridge.portlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.net.URI;
import java.util.Enumeration;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cyberneko.html.parsers.DOMParser;
import org.portletbridge.ResourceException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 * Factories for implementations of various types defined in PortletBridge.
 * 
 * @author agherna
 */
public class PortletBridgeObjects {

	private PortletBridgeObjects() {
		// private to ensure non-instiability
	}

	private static final Log factoryLogger = 
			LogFactory.getLog("PortletBridgeObjects");
	

    public static BridgeAuthenticator createBridgeAuthenticator(String authenticatorClassName) 
        throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class authenticatorClass = Class.forName(authenticatorClassName);
        BridgeAuthenticator instance = (BridgeAuthenticator) 
            authenticatorClass.newInstance();
        return instance;
    }

    public static InitUrlFactory createInitUrlFactory(String initUrlFactoryClassName) 
        throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class initUrlFactoryClass = Class.forName(initUrlFactoryClassName);
        InitUrlFactory instance = (InitUrlFactory)
            initUrlFactoryClass.newInstance();
        return instance;
    }


	/**
	 * Returns an implementation of HttpClientCallback that is suitable for use
	 * in the PortletBridgeServlet.
	 * 
	 * @param request
	 * @param response
	 * @param bridgeRequest
	 * @param memento
	 * @param perPortletMemento
	 * @param url
	 * @param servletName
	 * @param bridgeFunctionsFactory
	 * @return an HttpClientCallback suitable for use in PortletBridgeServlet
	 * for handling GET methods.
	 * 
	 * @see org.portletbridge.portlet.PortletBridgeServlet
	 */
	public static HttpClientCallback newGetMethodHttpClientCallback(final HttpServletRequest request,
			final HttpServletResponse response,
			final BridgeRequest bridgeRequest,
			final PortletBridgeMemento memento,
			final PerPortletMemento perPortletMemento,
			final URI url,
			final String servletName,
			final BridgeFunctionsFactory bridgeFunctionsFactory) {
		return new GetMethodHttpClientCallback(request, response, bridgeRequest,
				memento, perPortletMemento, url, servletName,
				bridgeFunctionsFactory);
	}


	/*
	 * Implementation of HttpClientCallback.  This implementation is meant for
	 * handling HTTP GET requests from the PortletBridgeServlet.
	 */
	private static class GetMethodHttpClientCallback implements HttpClientCallback {

		private final HttpServletRequest request;
		private final HttpServletResponse response;
		private final BridgeRequest bridgeRequest;
		private final PortletBridgeMemento memento;
		private final PerPortletMemento perPortletMemento;
		private final URI url;
		private final String servletName;
		private final BridgeFunctionsFactory bridgeFunctionsFactory;
		private final Log logger = LogFactory.getLog("GetMethodHttpClientCallback");


		GetMethodHttpClientCallback(HttpServletRequest request,
				HttpServletResponse response,
				BridgeRequest bridgeRequest,
				PortletBridgeMemento memento,
				PerPortletMemento perPortletMemento,
				URI url,
				String servletName,
				BridgeFunctionsFactory bridgeFunctionsFactory) {
			this.request = request;
			this.response = response;
			this.bridgeRequest = bridgeRequest;
			this.memento = memento;
			this.perPortletMemento = perPortletMemento;
			this.url = url;
			this.servletName = servletName;
			this.bridgeFunctionsFactory = bridgeFunctionsFactory;
		}


		/**
		 * {@inheritDoc}
		 */
		public Object doInHttpClient(int statusCode, HttpMethodBase method)
				throws ResourceException, Throwable {

			if (logger.isDebugEnabled()) {
				logger.debug(String.format("HTTP Status code: %d",
						statusCode));
			}

			if (statusCode == HttpStatus.SC_OK) {

				org.apache.commons.httpclient.URI effectiveUri = method.getURI();
				BridgeRequest effectiveBridgeRequest = null;
				if (!effectiveUri.toString().equals(url.toString())) {
					PseudoRenderResponse renderResponse =
							createRenderResponse(bridgeRequest);
					effectiveBridgeRequest =
							memento.createBridgeRequest(renderResponse,
							DefaultIdGenerator.getInstance().nextId(),
							new URI(effectiveUri.toString()));
				} else {
					effectiveBridgeRequest = bridgeRequest;
				}

				Header contentType = method.getResponseHeader("Content-Type");
				if (contentType != null && contentType.getValue().startsWith("text/html")) {
					String content =
							ResourceUtil.getString(method.getResponseBodyAsStream(),
							method.getResponseCharSet());

					// Check for 'X-Requested-With' HTTP header for a value of 
					// 'XMLHttpRequest' and transform the output if present
					// otherwise, send it to the portlet for rendering the view.
					if (request.getHeader("X-Requested-With") != null &&
						request.getHeader("X-Requested-With").equals("XMLHttpRequest")) {

						if (logger.isDebugEnabled()) {
							logger.debug(
								String.format("Request to %s using XMLHttpRequest",
									effectiveBridgeRequest.getUrl().toString()));
						}

						BridgeFunctions bridge =
								bridgeFunctionsFactory.createBridgeFunctions(memento,
								perPortletMemento, servletName, url,
								new PseudoRenderRequest(request.getContextPath()),
								createRenderResponse(effectiveBridgeRequest));
						DOMServletBridgeTransformer transformer =
								new DOMServletBridgeTransformer();
						transformer.transform(new StringReader(content),
								"classpath:/org/portletbridge/xsl/ajax-fragments.xsl",
								bridge, response.getWriter());

					} else {

						if (logger.isDebugEnabled()) {
							logger.debug(
								String.format("Request to %s will be rendered by the portlet",
									effectiveBridgeRequest.getUrl().toString()));
						}

						perPortletMemento.enqueueContent(effectiveBridgeRequest.getId(),
								new PortletBridgeContent(url, "get", content));
						response.sendRedirect(effectiveBridgeRequest.getPageUrl());
					}

				} else if (contentType != null &&
						(contentType.getValue().startsWith("text/javascript") ||
						contentType.getValue().startsWith("application/x-javascript"))) {

					if (logger.isDebugEnabled()) {
						logger.debug(String.format("Request to %s for Javascript",
								effectiveBridgeRequest.getUrl().toString()));
					}

					// rewrite external javascript
					String content =
							ResourceUtil.getString(method.getResponseBodyAsStream(),
							method.getResponseCharSet());

					BridgeFunctions bridge =
							bridgeFunctionsFactory.createBridgeFunctions(memento,
							perPortletMemento, servletName, url,
							new PseudoRenderRequest(request.getContextPath()),
							createRenderResponse(effectiveBridgeRequest));
					response.setContentType(contentType.getValue());
					PrintWriter writer = response.getWriter();

					// BUG: should be passing in base URL if it is available (which it is since we can get it from the url parameter)
					//writer.write(bridge.script(null, content));
					String baseUrl = perPortletMemento.getInitUrl().toString();
					writer.write(bridge.script(baseUrl, content));
					writer.flush();
				} else if (contentType != null &&
						contentType.getValue().startsWith("text/css")) {

					if (logger.isDebugEnabled()) {
						logger.debug(String.format("Request to %s for css",
								effectiveBridgeRequest.getUrl().toString()));
					}

					// rewrite external css
					String content =
							ResourceUtil.getString(method.getResponseBodyAsStream(),
							method.getResponseCharSet());
					BridgeFunctions bridge =
							bridgeFunctionsFactory.createBridgeFunctions(memento,
							perPortletMemento, servletName, url,
							new PseudoRenderRequest(request.getContextPath()),
							createRenderResponse(effectiveBridgeRequest));
					response.setContentType("text/css");
					PrintWriter writer = response.getWriter();
					writer.write(bridge.style(null, content));
					writer.flush();
				} else {
					Header header = method.getResponseHeader("Content-Type");
					response.setContentType(((null == header.getName() ? "" : 
						header.getName()) + ": " + 
						(null == header.getValue() ? "" : header.getValue())));

					if (logger.isDebugEnabled()) {
						logger.debug(
							String.format("Request to %s for %s",
								effectiveBridgeRequest.getUrl().toString(),
								header.getValue()));
					}

					ResourceUtil.copy(method.getResponseBodyAsStream(),
							response.getOutputStream(), 4096);
				}
				response.setStatus(statusCode);
			} else {
				// if there is a problem with the status code
				// then return that error back
				response.sendError(statusCode);
			}
			return null;
		}
	}


	public static HttpClientCallback newPostMethodHttpClientCallback(
			HttpServletRequest request, 
			HttpServletResponse response,
			BridgeRequest bridgeRequest,
			PortletBridgeMemento memento,
			PerPortletMemento perPortletMemento,
			URI url,
			String servletName,
			BridgeFunctionsFactory bridgeFunctionsFactory,
			Set<String> ignoreRequestHeaders,
			Set<String> ignorePostToGetRequestHeaders,
			HttpClientTemplate httpClientTemplate) {
		
		return new PostMethodHttpClientCallback(request, response, 
			bridgeRequest, memento, perPortletMemento, url, servletName, 
			bridgeFunctionsFactory, ignoreRequestHeaders, 
			ignorePostToGetRequestHeaders, httpClientTemplate);
		
	}


	private static class PostMethodHttpClientCallback implements HttpClientCallback {

		private final Set<String> ignoreRequestHeaders;
		private final Set<String> ignorePostToGetRequestHeaders;
		private final HttpServletRequest request;
		private final HttpServletResponse response;
		private final BridgeRequest bridgeRequest;
		private final PortletBridgeMemento memento;
		private final PerPortletMemento perPortletMemento;
		private final URI url;
		private final String servletName;
		private final BridgeFunctionsFactory bridgeFunctionsFactory;
		private final HttpClientTemplate httpClientTemplate;
		private final Log logger = LogFactory.getLog("PostMethodHttpClientCallback");
		
		PostMethodHttpClientCallback(HttpServletRequest request,
				HttpServletResponse response,
				BridgeRequest bridgeRequest,
				PortletBridgeMemento memento,
				PerPortletMemento perPortletMemento,
				URI url,
				String servletName,
				BridgeFunctionsFactory bridgeFunctionsFactory,
				Set<String> ignoreRequestHeaders,
				Set<String> ignorePostToGetRequestHeaders,
				HttpClientTemplate httpClientTemplate) {
			this.request = request;
			this.response = response;
			this.bridgeRequest = bridgeRequest;
			this.memento = memento;
			this.perPortletMemento = perPortletMemento;
			this.url = url;
			this.servletName = servletName;
			this.bridgeFunctionsFactory = bridgeFunctionsFactory;
			this.ignoreRequestHeaders = ignoreRequestHeaders;
			this.ignorePostToGetRequestHeaders = ignorePostToGetRequestHeaders;
			this.httpClientTemplate = httpClientTemplate;
		}


		public Object doInHttpClient(int statusCode, HttpMethodBase method)
				throws ResourceException, Throwable {

			if (logger.isDebugEnabled()) {
				logger.debug(String.format("HTTP Status code: %d",
						statusCode));
			}

			if (statusCode == HttpStatus.SC_OK) {

				// if it's text/html then store it and redirect
				// back to the portlet render view (portletUrl)
				Header contentType = method.getResponseHeader("Content-Type");
				if (contentType != null && contentType.getValue().startsWith("text/html")) {
					String content = 
						ResourceUtil.getString(method.getResponseBodyAsStream(),
							method.getResponseCharSet());
					// Check for 'X-Requested-With' HTTP header for a value of 
					// 'XMLHttpRequest' and transform the output if present
					// otherwise, send it to the portlet for rendering the view.
					if (request.getHeader("X-Requested-With") != null &&
							request.getHeader("X-Requested-With").equals("XMLHttpRequest")) {

						if (logger.isDebugEnabled()) {
							logger.debug(
								String.format("Request to %s using XMLHttpRequest",
									bridgeRequest.getUrl().toString()));
						}

						BridgeFunctions bridge =
								bridgeFunctionsFactory.createBridgeFunctions(memento,
								perPortletMemento, servletName, url,
								new PseudoRenderRequest(request.getContextPath()),
								createRenderResponse(bridgeRequest));
						DOMServletBridgeTransformer transformer =
								new DOMServletBridgeTransformer();
						transformer.transform(new StringReader(content),
								"classpath:/org/portletbridge/xsl/ajax-fragments.xsl",
								bridge, response.getWriter());

					} else {

						if (logger.isDebugEnabled()) {
							logger.debug(
								String.format("Request to %s will be rendered by the portlet",
									bridgeRequest.getUrl().toString()));
						}

						perPortletMemento.enqueueContent(bridgeRequest.getId(),
							new PortletBridgeContent(url, "post", content));
						response.sendRedirect(bridgeRequest.getPageUrl());
					}

				} else if (contentType != null &&
						(contentType.getValue().startsWith("text/javascript") ||
						contentType.getValue().startsWith("application/x-javascript"))) {

					if (logger.isDebugEnabled()) {
						logger.debug(String.format("Request to %s for Javascript",
								bridgeRequest.getUrl().toString()));
					}

					// rewrite external javascript
					String content =
							ResourceUtil.getString(method.getResponseBodyAsStream(),
							method.getResponseCharSet());

					BridgeFunctions bridge =
							bridgeFunctionsFactory.createBridgeFunctions(memento,
							perPortletMemento, servletName, url,
							new PseudoRenderRequest(request.getContextPath()),
							createRenderResponse(bridgeRequest));
					response.setContentType(contentType.getValue());
					PrintWriter writer = response.getWriter();

					// BUG: should be passing in base URL if it is available (which it is since we can get it from the url parameter)
					//writer.write(bridge.script(null, content));
					String baseUrl = perPortletMemento.getInitUrl().toString();
					writer.write(bridge.script(baseUrl, content));
					writer.flush();
				} else if (contentType != null &&
						contentType.getValue().startsWith("text/css")) {

					if (logger.isDebugEnabled()) {
						logger.debug(String.format("Request to %s for css",
								bridgeRequest.getUrl().toString()));
					}

					// rewrite external css
					String content =
							ResourceUtil.getString(method.getResponseBodyAsStream(),
							method.getResponseCharSet());
					BridgeFunctions bridge =
							bridgeFunctionsFactory.createBridgeFunctions(memento,
							perPortletMemento, servletName, url,
							new PseudoRenderRequest(request.getContextPath()),
							createRenderResponse(bridgeRequest));
					response.setContentType("text/css");
					PrintWriter writer = response.getWriter();
					writer.write(bridge.style(null, content));
					writer.flush();
				} else {
					Header header = method.getResponseHeader("Content-Type");
					response.setContentType(((null == header.getName() ? "" : 
						header.getName()) + ": " + 
						(null == header.getValue() ? "" : header.getValue())));

					if (logger.isDebugEnabled()) {
						logger.debug(
							String.format("Request to %s for %s",
								bridgeRequest.getUrl().toString(),
								header.getValue()));
					}

					ResourceUtil.copy(method.getResponseBodyAsStream(),
							response.getOutputStream(), 4096);
				}
				
			} else if (statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
				
				// Handle a redirect.  Redirects will be executed using a GET.
				Header locationHeader = method.getResponseHeader("Location");
				if (locationHeader != null) {
					URI redirectUrl = bridgeRequest.getUrl().resolve(locationHeader.getValue());
					PseudoRenderResponse renderResponse = createRenderResponse(bridgeRequest);
					BridgeRequest updatedBridgeRequest = 
						memento.createBridgeRequest(renderResponse, 
							DefaultIdGenerator.getInstance().nextId(), redirectUrl);
					
					if (logger.isDebugEnabled()) {
						logger.debug(String.format("Request redirected to %s",
								redirectUrl.toString()));
					}
					
					GetMethod getMethod = new GetMethod(redirectUrl.toString());
					copyRequestHeaders(request, getMethod, 
							ignoreRequestHeaders, ignorePostToGetRequestHeaders);
					HttpClientCallback servletClientCallbackForHttpGet = 
						newGetMethodHttpClientCallback(request, response, 
							updatedBridgeRequest, memento, perPortletMemento, 
							url, servletName, bridgeFunctionsFactory);
					return httpClientTemplate.service(getMethod, 
							perPortletMemento, servletClientCallbackForHttpGet);

				} else {
					throw new ResourceException("error.missingLocation");
				}
			} else {
				// if there is a problem with the status code
				// then return that error back
				response.sendError(statusCode);
			}
			return null;
		}
	}

	/*
	 * Transformer utility for handling content requested by an XMLHttpRequest
	 * object.
	 */

	private static class DOMServletBridgeTransformer {

		private final Log logger = LogFactory.getLog("DOMServletBridgeTransformer");


		void transform(Reader in, String xslLocation, BridgeFunctions bridge,
				Writer w) throws ResourceException {
			try {
				TemplateFactory tf = new DefaultTemplateFactory();
				Templates t = tf.getTemplatesFromUrl(xslLocation);
				Transformer transformer = t.newTransformer();

				transformer.setParameter("bridge", bridge);
				DOMParser parser = new DOMParser();

				parser.parse(new InputSource(in));
				DOMSource xml = new DOMSource(parser.getDocument());
				transformer.transform(xml, new StreamResult(w));
			} catch (TransformerConfigurationException ex) {
				logger.fatal(ex);
				throw new ResourceException("error.transformer",
						ex.getLocalizedMessage(), ex);
			} catch (SAXException ex) {
				logger.fatal(ex);
				throw new ResourceException("error.saxparser",
						ex.getLocalizedMessage(), ex);
			} catch (IOException ex) {
				logger.fatal(ex);
				throw new ResourceException("error.filter.io",
						ex.getLocalizedMessage(), ex);
			} catch (TransformerException ex) {
				logger.fatal(ex);
				throw new ResourceException("error.transformer",
						ex.getLocalizedMessage(), ex);
			}
		}
	}


    private static void copyRequestHeaders(HttpServletRequest request,
            HttpMethodBase method, 
			Set<String> ignoreRequestHeaders,
			Set<String> ignorePostToGetRequestHeaders) {

        Enumeration properties = request.getHeaderNames();
        while (properties.hasMoreElements()) {
			
            String propertyName = (String) properties.nextElement();
            String propertyNameToLower = propertyName.toLowerCase();
			
			if (!ignoreRequestHeaders.contains(propertyNameToLower)
            		&& !(method instanceof GetMethod && ignorePostToGetRequestHeaders.contains(propertyNameToLower))) {
                Enumeration values = request.getHeaders(propertyName);
                while (values.hasMoreElements()) {
                    String property = (String) values.nextElement();
					
					if (factoryLogger.isDebugEnabled()) {
						factoryLogger.debug(String.format("Copying Header %s: %s",
							propertyName, property));
					}
					
                    method.setRequestHeader(propertyName, property);
                }
            }
        }

        // TODO consider what happens if the host is different after a redirect...
        // Conditional cookie transfer
        try {
            if (method.getURI().getHost().equals(request.getHeader("host"))) {
                String cookie = request.getHeader("cookie");
                if (cookie != null) {
                    method.setRequestHeader("cookie", cookie);
				}
            }
        } catch (Throwable e) {
            factoryLogger.warn(e, e);
        }

    }
	
	public static PseudoRenderResponse createRenderResponse(BridgeRequest bridgeRequest) {
		PseudoRenderResponse renderResponse = new PseudoRenderResponse(
				bridgeRequest.getPortletId(),
				bridgeRequest.getPageUrl(),
				bridgeRequest.getId());
		return renderResponse;
	}
}
