 /*
 * Copyright 2001-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.portletbridge.portlet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import org.portletbridge.ResourceException;

/**
 * The portlet that renders the portlet view mode. Renders
 * content from the downstream site after transforming it.
 * 
 * @author JMcCrindle
 * @author rickard
 */
public class BridgeViewPortlet extends GenericPortlet {
    
    private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
            .getLog(BridgeViewPortlet.class);

    private String mementoSessionKey = null;

    private HttpClientTemplate httpClientTemplate = null;

    private BridgeTransformer transformer = null;
    
    private String idParamKey = "id";
    
    private BridgeAuthenticator bridgeAuthenticator = null;
    
    private InitUrlFactory initUrlFactory = null;

    /**
     * Does nothing. All requests are passed through the portlet bridge servlet
     * 
     * @see PortletBridgeServlet
     */
    public void processAction(ActionRequest request, ActionResponse response)
            throws PortletException, IOException {
        // noop
    }

    /**
     * Writes out the transformed content from the downstream site.
     */
    public void doView(final RenderRequest request,
            final RenderResponse response) throws PortletException, IOException {

        ResourceBundle resourceBundle = getPortletConfig().getResourceBundle(request
                .getLocale());
        
        // noop if window is minimised
        if(request.getWindowState().equals(WindowState.MINIMIZED)) {
            return;
        }
        
        response.setContentType("text/html");
        
        try {
            PortletSession session = request.getPortletSession();
            String portletId = response.getNamespace();
            PortletBridgeMemento tempMemento = (PortletBridgeMemento) session
                    .getAttribute(mementoSessionKey,
                            PortletSession.APPLICATION_SCOPE);
            if (tempMemento == null) {
                tempMemento = new DefaultPortletBridgeMemento(idParamKey, bridgeAuthenticator, initUrlFactory);
                session.setAttribute(mementoSessionKey, tempMemento, PortletSession.APPLICATION_SCOPE);
            }
            final PortletBridgeMemento memento = tempMemento;
            final PerPortletMemento perPortletMemento = memento
                    .getPerPortletMemento(portletId);
            perPortletMemento.setPreferences(request);
            String urlId = request.getParameter(idParamKey);

            
            final BridgeRequest bridgeRequest;
            
            if(urlId != null) { 
                bridgeRequest = memento
                    .getBridgeRequest(urlId);
            } else {
            	
            	if (log.isDebugEnabled()) { 
            		log.debug("no bridge request found, using initUrl");
            	}
            	
                bridgeRequest = null;
            }

            if (urlId == null || bridgeRequest == null) {
                // this is the default start page for the portlet so go and
                // fetch it
                final URI initUrl = perPortletMemento.getInitUrl();
                httpClientTemplate.service(new GetMethod(initUrl.toString()), perPortletMemento,
                        new HttpClientCallback() {
                            public Object doInHttpClient(int statusCode,
                                    HttpMethodBase method) throws Throwable {
                                transformer.transform(memento, perPortletMemento, initUrl, 
                                        request, response,
                                        new InputStreamReader(method
                                                .getResponseBodyAsStream(),
                                                method.getResponseCharSet()));
                                return null;
                            }
                        });
            } else {
                PortletBridgeContent content = perPortletMemento.dequeueContent(bridgeRequest.getId());
                if (content == null) {
                    // we're rerendering
                    httpClientTemplate.service(new GetMethod(bridgeRequest.getUrl().toString()),
                            perPortletMemento, new HttpClientCallback() {
                                public Object doInHttpClient(int statusCode,
                                        HttpMethodBase method) throws Throwable {
                                    transformer.transform(
                                            memento,
                                            perPortletMemento, 
                                            bridgeRequest.getUrl(),
                                            request,
                                            response,
                                            new InputStreamReader(method
                                                    .getResponseBodyAsStream(),
                                                    method.getResponseCharSet()));
                                    return null;
                                }
                            });
                } else {
                    // we have content, transform that
                    transformer.transform(memento, perPortletMemento, bridgeRequest.getUrl(), request,
                            response, new StringReader(content.getContent()));
                }
            }

        } catch (ResourceException resourceException) {
            String format = MessageFormat.format(resourceBundle
                    .getString(resourceException.getMessage()),
                    resourceException.getArgs());
            throw new PortletException(format, resourceException.getCause());
        }
    }


    public void destroy() {
        super.destroy();
    }

    public void setHttpClientTemplate(HttpClientTemplate httpClientTemplate) {
        this.httpClientTemplate = httpClientTemplate;
    }

    public void setTransformer(BridgeTransformer transformer) {
        this.transformer = transformer;
    }
    public void setMementoSessionKey(String mementoSessionKey) {
        this.mementoSessionKey = mementoSessionKey;
    }

    public void setIdParamKey(String idParamKey) {
        this.idParamKey = idParamKey;
    }

    public void setBridgeAuthenticator(BridgeAuthenticator bridgeAuthenticator) {
        this.bridgeAuthenticator = bridgeAuthenticator;
    }

	public void setInitUrlFactory(InitUrlFactory initUrlFactory) {
		this.initUrlFactory = initUrlFactory;
	}
}
