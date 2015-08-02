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

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import junit.framework.TestCase;

import org.portletbridge.mock.MockPortletConfig;

/**
 * @author JMcCrindle
 */
public class PortletBridgePortletTest extends TestCase {

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Constructor for PortletBridgePortletTest.
     * @param name
     */
    public PortletBridgePortletTest(String name) {
        super(name);
    }

    public void testInit() throws Exception {
        PortletBridgePortlet portlet = new PortletBridgePortlet();
        ResourceBundle bundle = PropertyResourceBundle.getBundle("org.portletbridge.portlet.PortletBridgePortlet");
        MockPortletConfig mockPortletConfig = new MockPortletConfig();
        mockPortletConfig.setupResourceBundle(bundle);
        mockPortletConfig.setupInitParam("mementoSessionKey", "mementoSessionKey");
        mockPortletConfig.setupInitParam("authenticatorClassName", DefaultBridgeAuthenticator.class.getName());
        mockPortletConfig.setupInitParam("parserClassName", "org.cyberneko.html.parsers.SAXParser");
        mockPortletConfig.setupInitParam("servletName", "(?:url\\((?:'|\")?(.*?)(?:'|\")?\\))|(?:@import\\s+[^url](?:'|\")?(.*?)(?:'|\")|;|\\s+|$)");
        mockPortletConfig.setupInitParam("jsRegex", "open\\('([^']*)'|open\\(\"([^\\\"]*)\"");
        mockPortletConfig.setupInitParam("cssRegex", "pbhs");
        mockPortletConfig.setupInitParam("editStylesheet", "classpath:/org/portletbridge/xsl/pages/edit.xsl");
        mockPortletConfig.setupInitParam("helpStylesheet", "classpath:/org/portletbridge/xsl/pages/help.xsl");
        mockPortletConfig.setupInitParam("errorStylesheet", "classpath:/org/portletbridge/xsl/pages/error.xsl");
        portlet.init(mockPortletConfig);
    }

}
