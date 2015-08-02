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
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.Serializer;
import org.apache.xml.serialize.SerializerFactory;
import org.portletbridge.ResourceException;
import org.portletbridge.xsl.XslFilter;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * The default bridge transformer. Uses XSLT to transform the downstream 
 * site into portlet content.
 * 
 * @author JMcCrindle
 */
public class DefaultBridgeTransformer implements
        BridgeTransformer {

    private TemplateFactory templateFactory = null;
    private XMLReader parser;
    private String servletName;
    private final BridgeFunctionsFactory bridgeFunctionsFactory;

    /**
     * Create a new transformer
     */
    public DefaultBridgeTransformer(BridgeFunctionsFactory bridgeFunctionsFactory, TemplateFactory templateFactory, XMLReader parser, String servletName) {
        this.bridgeFunctionsFactory = bridgeFunctionsFactory;
        this.templateFactory = templateFactory;
        this.parser = parser;
        this.servletName = servletName;
    }

    /**
     * Transforms the HTML from a downstream site using a configured XSL
     * stylesheet.
     * 
     * @param request
     *            the render request
     * @param response
     *            the render response
     * @param in
     *            the http result from calling the downstream site.
     * @throws ResourceException
     *             if there was a problem doing the transform (e.g. if the
     *             stylesheet throws an error).
     */
    public void transform(PortletBridgeMemento memento, PerPortletMemento perPortletMemento, URI currentUrl,
            RenderRequest request, RenderResponse response,
            Reader in) throws ResourceException {
        try {
            PortletPreferences preferences = request.getPreferences();
            String stylesheet = preferences.getValue("stylesheet", null);
            String stylesheetUrl = preferences.getValue("stylesheetUrl", null);
            Templates templates = null;
            if(stylesheetUrl != null) {
                templates = templateFactory.getTemplatesFromUrl(stylesheetUrl);
            } else {
                templates = templateFactory.getTemplatesFromString(stylesheet);
            }
            SerializerFactory factory = SerializerFactory
                    .getSerializerFactory("html");
            OutputFormat outputFormat = new OutputFormat();
            outputFormat.setPreserveSpace(true);
            outputFormat.setOmitDocumentType(true);
            outputFormat.setOmitXMLDeclaration(true);
            Serializer writer = factory.makeSerializer(outputFormat);
            PrintWriter responseWriter = response.getWriter();
            writer.setOutputCharStream(responseWriter);
            XslFilter filter = new XslFilter(templates);
            Map context = new HashMap();
            context.put("bridge", bridgeFunctionsFactory.createBridgeFunctions(memento, perPortletMemento, servletName,
                    currentUrl, request, response));
            filter.setContext(context);
            filter.setParent(parser);
            filter.setContentHandler(writer.asContentHandler());
            InputSource inputSource = new InputSource(in);
            filter.parse(inputSource);
        } catch (TransformerConfigurationException e) {
            throw new ResourceException("error.transformer", e
                    .getLocalizedMessage(), e);
        } catch (SAXException e) {
            throw new ResourceException("error.filter.sax", e
                    .getLocalizedMessage(), e);
        } catch (IOException e) {
            throw new ResourceException("error.filter.io", e
                    .getLocalizedMessage(), e);
        }

    }

    public void setTemplateFactory(TemplateFactory templateFactory) {
        this.templateFactory = templateFactory;
    }
}
