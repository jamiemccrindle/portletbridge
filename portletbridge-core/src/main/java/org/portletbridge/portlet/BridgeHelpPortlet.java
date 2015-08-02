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

import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Portlet to display the PortletBridgeHelp
 * 
 * @author JMcCrindle
 */
public class BridgeHelpPortlet extends GenericPortlet {

    private Templates templates = null;

    public BridgeHelpPortlet() {
        super();
    }
    
    /* (non-Javadoc)
     * @see javax.portlet.GenericPortlet#doHelp(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
     */
    protected void doHelp(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {
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

    public void setTemplates(Templates templates) {
        this.templates = templates;
    }
}
