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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.ServletException;

import junit.framework.TestCase;

import org.portletbridge.mock.MockHttpClientTemplate;
import org.portletbridge.mock.MockHttpServletRequest;
import org.portletbridge.mock.MockHttpServletResponse;
import org.portletbridge.mock.MockHttpSession;
import org.portletbridge.mock.MockPerPortletMemento;
import org.portletbridge.mock.MockPortletBridgeMemento;
import org.portletbridge.mock.MockServletConfig;
import org.portletbridge.mock.MockServletContext;

/**
 * @author JMcCrindle
 */
public class PortletBridgeServletTest extends TestCase {

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
     * Constructor for PortletBridgeServletTest.
     * @param name
     */
    public PortletBridgeServletTest(String name) {
        super(name);
    }

    /*
     * Class under test for void doGet(HttpServletRequest, HttpServletResponse)
     */
    public void testDoGetHttpServletRequestHttpServletResponse() throws ServletException, IOException, URISyntaxException {
        PortletBridgeServlet servlet = new PortletBridgeServlet();
        MockServletConfig config = new MockServletConfig();
        config.setupParam("mementoSessionKey", "mementoSessionKey");
        config.setupParam("jsRegex", "open\\('([^']*)'|open\\(\"([^\\\"]*)\"");
        config.setupParam("cssRegex", "pbhs");
        config.setupParam("ignoreRequestHeaders", "host");
        config.setupParam("ignorePostToGetRequestHeaders", "host");
        config.setupContext(new MockServletContext());
        servlet.init(config);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupContextPath("");
        request.setupUrl(new URL("http://www.test.com/blah/123/"));
        MockHttpSession session = new MockHttpSession();
        MockPortletBridgeMemento memento = new MockPortletBridgeMemento();
        memento.setupBridgeRequest("123", new DefaultBridgeRequest("1", "portletid", "pageurl", new URI("http://www.bob.com")));
        memento.setupMemento("portletid", new MockPerPortletMemento());
        session.setAttribute("mementoSessionKey", memento);
        request.setupSession(session);
        MockHttpClientTemplate mockHttpClientTemplate = new MockHttpClientTemplate();
        mockHttpClientTemplate.setupResponseHeader("Content-Type", "text/html");
        servlet.setHttpClientTemplate(mockHttpClientTemplate);
        servlet.doGet(request, new MockHttpServletResponse());
    }

    public void testUrl() throws Exception {
        URL url = new URL("http://www.test.com/blah/blah.html?test=one");
        assertEquals("/blah/blah.html", url.getPath());
    }

}
