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
import java.io.StringReader;
import java.util.Enumeration;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.portletbridge.ResourceException;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * @author JMcCrindle
 */
public class PortletBridgePortlet extends GenericPortlet {
    
    private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
            .getLog(PortletBridgePortlet.class);

    private Portlet viewPortlet = null;
    private Portlet editPortlet = null;
    private Portlet helpPortlet = null;
    private Templates errorTemplates = null;

    /* (non-Javadoc)
     * @see javax.portlet.GenericPortlet#init()
     */
    public void init() throws PortletException {
        
        
        TemplateFactory templateFactory = new DefaultTemplateFactory();
        
        // ResourceBundle resourceBundle = config.getResourceBundle(Locale.getDefault());
        ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("org.portletbridge.portlet.PortletBridgePortlet");

        // initialise portlets
        viewPortlet = createViewPortlet(resourceBundle, templateFactory);
        editPortlet = createEditPortlet(resourceBundle, templateFactory);
        helpPortlet = createHelpPortlet(resourceBundle, templateFactory);

        createErrorTemplates(resourceBundle, templateFactory);

        if(viewPortlet != null) {
            viewPortlet.init(this.getPortletConfig());
        }
        if(editPortlet != null) {
            editPortlet.init(this.getPortletConfig());
        }
        if(helpPortlet != null) {
            helpPortlet.init(this.getPortletConfig());
        }
    }

    /**
     * @param resourceBundle
     * @param templateFactory
     * @throws PortletException
     */
    protected void createErrorTemplates(ResourceBundle resourceBundle, TemplateFactory templateFactory) throws PortletException {
        // get the error stylesheet reference
        String errorStylesheet = getPortletConfig().getInitParameter("errorStylesheet");
        if (errorStylesheet == null) {
            throw new PortletException(resourceBundle
                    .getString("error.error.stylesheet"));
        }
        
        try {
            errorTemplates = templateFactory.getTemplatesFromUrl(errorStylesheet);
        } catch (ResourceException e) {
            throw new PortletException(e);
        } catch (TransformerFactoryConfigurationError e) {
            throw new PortletException(e);
        }
    }

    protected BridgeEditPortlet createEditPortlet(ResourceBundle resourceBundle, TemplateFactory templateFactory) throws PortletException {
        PortletConfig config = this.getPortletConfig();

        // get the edit stylesheet reference
        String editStylesheet = config.getInitParameter("editStylesheet");
        if (editStylesheet == null) {
            throw new PortletException(resourceBundle
                    .getString("error.edit.stylesheet"));
        }

        BridgeEditPortlet bridgeEditPortlet = new BridgeEditPortlet();
        try {
            bridgeEditPortlet.setTemplates(templateFactory.getTemplatesFromUrl(editStylesheet));
        } catch (ResourceException e) {
            throw new PortletException(e);
        } catch (TransformerFactoryConfigurationError e) {
            throw new PortletException(e);
        }
        return bridgeEditPortlet;
    }
    
    protected BridgeHelpPortlet createHelpPortlet(ResourceBundle resourceBundle, TemplateFactory templateFactory) throws PortletException {
        PortletConfig config = this.getPortletConfig();

        // get the help stylesheet reference
        String editStylesheet = config.getInitParameter("helpStylesheet");
        if (editStylesheet == null) {
            throw new PortletException(resourceBundle
                    .getString("error.help.stylesheet"));
        }

        BridgeHelpPortlet bridgeHelpPortlet = new BridgeHelpPortlet();
        try {
            bridgeHelpPortlet.setTemplates(templateFactory.getTemplatesFromUrl(editStylesheet));
        } catch (ResourceException e) {
            throw new PortletException(e);
        } catch (TransformerFactoryConfigurationError e) {
            throw new PortletException(e);
        }
        return bridgeHelpPortlet;
    }
    
    /**
     * @return
     * @throws PortletException
     * @throws IllegalAccessException 
     * @throws  
     */
    protected BridgeViewPortlet createViewPortlet(ResourceBundle resourceBundle, TemplateFactory templateFactory) throws PortletException {
        PortletConfig config = this.getPortletConfig();

        // get the memento session key
        String mementoSessionKey = config.getInitParameter("mementoSessionKey");
        if (mementoSessionKey == null) {
            throw new PortletException(resourceBundle
                    .getString("error.mementoSessionKey"));
        }
        // get the servlet name
        String servletName = config.getInitParameter("servletName");
        if (servletName == null) {
            throw new PortletException(resourceBundle
                    .getString("error.servletName"));
        }
        // get parserClassName
        String parserClassName = config.getInitParameter("parserClassName");
        if (parserClassName == null) {
            throw new PortletException(resourceBundle
                    .getString("error.parserClassName"));
        }
        // get authenticatorClassName
        String authenticatorClassName = config.getInitParameter("authenticatorClassName");
        if (authenticatorClassName == null) {
            throw new PortletException(resourceBundle
                    .getString("error.authenticatorClassName"));
        }
        BridgeAuthenticator bridgeAuthenticator = null;
        try {
			bridgeAuthenticator = PortletBridgeObjects.createBridgeAuthenticator(authenticatorClassName);
		} catch (Exception e) {
			if (log.isWarnEnabled()) {
				log.warn("Problem constructing BridgeAuthenticator instance, returning instance of DefaultBridgeAuthenticator", e);
			}
			bridgeAuthenticator = new DefaultBridgeAuthenticator();
		}
//        try {
//            Class authenticatorClass = Class.forName(authenticatorClassName);
//            bridgeAuthenticator = (BridgeAuthenticator) authenticatorClass.newInstance();
//        } catch (ClassNotFoundException e) {
//            log.warn(e, e);
//            throw new PortletException(resourceBundle
//                    .getString("error.authenticator"));
//        } catch (InstantiationException e) {
//            log.warn(e, e);
//            throw new PortletException(resourceBundle
//                    .getString("error.authenticator"));
//        } catch (IllegalAccessException e) {
//            log.warn(e, e);
//            throw new PortletException(resourceBundle
//                    .getString("error.authenticator"));
//        }
        String initUrlFactoryClassName = config.getInitParameter("initUrlFactoryClassName");
		InitUrlFactory initUrlFactory = null;
        if(initUrlFactoryClassName != null && initUrlFactoryClassName.trim().length() > 0) {
        	try {
				initUrlFactory = PortletBridgeObjects.createInitUrlFactory(initUrlFactoryClassName);
			} catch (Exception e) {
				if (log.isWarnEnabled()) {
					log.warn("Problem constructing InitUrlFactory instance, returning instance of DefaultInitUrlFactory", e);
				}
				initUrlFactory = new DefaultInitUrlFactory();
			}
        }
        String idParamKey = config.getInitParameter("idParamKey");
        // setup parser
        BridgeTransformer transformer = null;
        try {
            String cssRegex = config.getInitParameter("cssRegex");
            String javascriptRegex = config.getInitParameter("jsRegex");
            XMLReader parser = XMLReaderFactory.createXMLReader(parserClassName);
            for(Enumeration names = config.getInitParameterNames(); names.hasMoreElements(); ) {
                String name = (String) names.nextElement();
                if(name.startsWith("parserFeature-")) {
                    parser.setFeature(name.substring("parserFeature-".length()), "true".equalsIgnoreCase(config.getInitParameter(name)));
                } else if (name.startsWith("parserProperty-")) {
                    parser.setProperty(name.substring("parserProperty-".length()), config.getInitParameter(name));
                }
            }
            IdGenerator idGenerator = new DefaultIdGenerator();
            ContentRewriter javascriptRewriter = new RegexContentRewriter(javascriptRegex);
            ContentRewriter cssRewriter = new RegexContentRewriter(cssRegex);
            BridgeFunctionsFactory bridgeFunctionsFactory = new BridgeFunctionsFactory(idGenerator, javascriptRewriter, cssRewriter); 
            transformer = new AltBridgeTransformer(bridgeFunctionsFactory, templateFactory, parser, servletName);
        } catch (SAXNotRecognizedException e) {
            throw new PortletException(e);
        } catch (SAXNotSupportedException e) {
            throw new PortletException(e);
        } catch (SAXException e) {
            throw new PortletException(e);
        }

        BridgeViewPortlet bridgeViewPortlet = new BridgeViewPortlet();
        
        bridgeViewPortlet.setInitUrlFactory(initUrlFactory);
        bridgeViewPortlet.setHttpClientTemplate(new DefaultHttpClientTemplate());
        bridgeViewPortlet.setTransformer(transformer);
        bridgeViewPortlet.setMementoSessionKey(mementoSessionKey);
        bridgeViewPortlet.setBridgeAuthenticator(bridgeAuthenticator);
        if(idParamKey != null) {
            bridgeViewPortlet.setIdParamKey(idParamKey);
        }
        return bridgeViewPortlet;
    }
    
    /* (non-Javadoc)
     * @see javax.portlet.GenericPortlet#render(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
     */
    public void render(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {
        try {
            super.render(request, response);
        } catch (Throwable exception) {
            // getPortletConfig().getPortletContext().log(exception.getMessage(), exception);
            // using this instead because pluto doesn't seem to print out portletcontext logs
            log.warn(exception, exception);
            response.setContentType("text/html");
            try {
                Transformer transformer = errorTemplates.newTransformer();
                transformer.setParameter("portlet", new PortletFunctions(request, response));
                transformer.setParameter("exception", exception);
                transformer.transform(new StreamSource(new StringReader("<xml/>")), new StreamResult(response.getWriter()));
            } catch (TransformerConfigurationException e) {
                throw new PortletException(e);
            } catch (TransformerException e) {
                throw new PortletException(e);
            } catch (IOException e) {
                throw new PortletException(e);
            }
        }
    }

    /* (non-Javadoc)
     * @see javax.portlet.GenericPortlet#doView(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
     */
    protected void doView(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {
        if(viewPortlet != null) {
            viewPortlet.render(request, response);
        }
    }
    
    /* (non-Javadoc)
     * @see javax.portlet.GenericPortlet#doEdit(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
     */
    protected void doEdit(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {
        if(editPortlet != null) {
            editPortlet.render(request, response);
        }
    }
    
    /* (non-Javadoc)
     * @see javax.portlet.GenericPortlet#doHelp(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
     */
    protected void doHelp(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {
        if(helpPortlet != null) {
            helpPortlet.render(request, response);
        }
    }
    
    /* (non-Javadoc)
     * @see javax.portlet.GenericPortlet#processAction(javax.portlet.ActionRequest, javax.portlet.ActionResponse)
     */
    public void processAction(ActionRequest request, ActionResponse response)
            throws PortletException, IOException {
        PortletMode portletMode = request.getPortletMode();
        if(portletMode.equals(PortletMode.VIEW)) {
            viewPortlet.processAction(request, response);
        } else if(portletMode.equals(PortletMode.EDIT)) {
            editPortlet.processAction(request, response);
        } else if (portletMode.equals(PortletMode.HELP)) {
            helpPortlet.processAction(request, response);
        }
    }

	public Portlet getEditPortlet() {
		return editPortlet;
	}

	public void setEditPortlet(Portlet editPortlet) {
		this.editPortlet = editPortlet;
	}

	public Portlet getHelpPortlet() {
		return helpPortlet;
	}

	public void setHelpPortlet(Portlet helpPortlet) {
		this.helpPortlet = helpPortlet;
	}

	public Portlet getViewPortlet() {
		return viewPortlet;
	}

	public void setViewPortlet(Portlet viewPortlet) {
		this.viewPortlet = viewPortlet;
	}

	public Templates getErrorTemplates() {
		return errorTemplates;
	}

	public void setErrorTemplates(Templates errorTemplates) {
		this.errorTemplates = errorTemplates;
	}
    
}
