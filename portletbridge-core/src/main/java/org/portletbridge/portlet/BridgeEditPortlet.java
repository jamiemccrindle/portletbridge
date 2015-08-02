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
import java.net.URI;
import java.net.URISyntaxException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Portlet that handles editing of preferences
 * 
 * @author JMcCrindle
 */
public class BridgeEditPortlet extends GenericPortlet {

    private Templates templates = null;
    
    public BridgeEditPortlet() {
        super();
    }
    
    /* (non-Javadoc)
     * @see javax.portlet.GenericPortlet#init()
     */
    public void init() throws PortletException {
    }
    
    /* (non-Javadoc)
     * @see javax.portlet.GenericPortlet#doView(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
     */
    protected void doEdit(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {
        PortletPreferences preferences = request.getPreferences();
        String secureEdit = preferences.getValue("secureEdit", "false");
        if("true".equalsIgnoreCase(secureEdit) && !request.isUserInRole("portletbridge")) {
            throw new PortletException(getPortletConfig().getResourceBundle(request.getLocale()).getString("error.invalid.role"));
        }
        response.setContentType("text/html");
        try {
            Transformer transformer = templates.newTransformer();
            transformer.setParameter("portlet", new PortletFunctions(request, response));
            transformer.transform(new StreamSource(new StringReader("<xml/>")), new StreamResult(response.getWriter()));
        } catch (TransformerConfigurationException e) {
            throw new PortletException(e);
        } catch (TransformerException e) {
            throw new PortletException(e);
        } catch (IOException e) {
            throw new PortletException(e);
        }
    }
    
    /* (non-Javadoc)
     * @see javax.portlet.GenericPortlet#processAction(javax.portlet.ActionRequest, javax.portlet.ActionResponse)
     */
    public void processAction(ActionRequest request, ActionResponse response)
            throws PortletException, IOException {
        PortletPreferences preferences = request.getPreferences();
        String initUrlParameter = request.getParameter("initUrl");
        if(initUrlParameter != null && initUrlParameter.trim().length() > 0) {
            try {
                URI initUrl = new URI(initUrlParameter);
                if(initUrl.getPath() == null || "".equals(initUrl.getPath())) {
                    initUrl = new URI(initUrlParameter + "/");
                }
                preferences.setValue("initUrl", initUrl.toString());
            } catch (URISyntaxException e) {
                // TODO: validation
                throw new PortletException(e);
            }
        }
        setIfNotNull(request, preferences, "scope");
        setIfNotNull(request, preferences, "proxyHost");
        setIfNotNull(request, preferences, "proxyPort");
        setIfNotNull(request, preferences, "proxyAuthentication");
        setIfNotNull(request, preferences, "proxyAuthenticationUsername");
        setIfNotNull(request, preferences, "proxyAuthenticationPassword");
        setIfNotNull(request, preferences, "proxyAuthenticationHost");
        setIfNotNull(request, preferences, "proxyAuthenticationDomain");
        setIfNotNull(request, preferences, "stylesheet");
        
        preferences.store();
    }
    
    protected void setIfNotNull(ActionRequest request, PortletPreferences preferences, String parameterName) throws ReadOnlyException {
        String parameterValue = request.getParameter(parameterName);
        if(parameterValue != null) {
            preferences.setValue(parameterName, parameterValue);
        }
    }

    public void setTemplates(Templates templates) {
        this.templates = templates;
    }
}
