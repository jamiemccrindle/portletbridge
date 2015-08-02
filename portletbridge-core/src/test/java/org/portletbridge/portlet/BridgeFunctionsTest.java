package org.portletbridge.portlet;

import java.net.URI;
import java.net.URISyntaxException;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.portletbridge.mock.MockRenderRequest;
import org.portletbridge.mock.MockRenderResponse;

import junit.framework.TestCase;

public class BridgeFunctionsTest extends TestCase {
    public void testRewriteQuestionMark() throws Exception {
        String link = "?test";
        URI currentUrl = new URI("http://www.test.com");
        URI url = null;
        if (link.startsWith("?")) {
            url = URI.create(currentUrl.toString() + link);
        }
        assertEquals("http://www.test.com?test", url.toString());
    }

    public void testRewrite() throws Exception {
        BridgeFunctions bridgeFunctions = createBridgeFunctions();
        assertNotNull(bridgeFunctions.link(null, ""));
    }

    public void testRewriteJavascript() throws Exception {
        BridgeFunctions bridgeFunctions = createBridgeFunctions();
        assertEquals("javascript:test", bridgeFunctions.link("", "javascript:test"));
    }

    public void testRewriteRelative() throws Exception {
        BridgeFunctions bridgeFunctions = createBridgeFunctions();
        assertEquals("#test", bridgeFunctions.link("", "#test"));
    }

    private BridgeFunctions createBridgeFunctions() throws URISyntaxException {
        RenderRequest request = new MockRenderRequest();
        RenderResponse response = new MockRenderResponse();
        DefaultBridgeAuthenticator authenticator = new DefaultBridgeAuthenticator();

        URI currentUri = new URI("http://www.example.org/test");
        InitUrlFactory initUrlFactory = new DefaultInitUrlFactory();
        
        PortletBridgeMemento memento = new DefaultPortletBridgeMemento("", 
                authenticator, initUrlFactory);

        PerPortletMemento perPortletMemento = new DefaultPerPortletMemento(authenticator, initUrlFactory);

        BridgeFunctions bridgeFunctions = new BridgeFunctions(
                new RegexContentRewriter("open\\('([^']*)'|open\\(\"([^\\\"]*)\""), 
                new RegexContentRewriter("(.*)"), 
                new DefaultIdGenerator(),
                memento,
                perPortletMemento, 
                "pb",
                currentUri, 
                request, 
                response);
        return bridgeFunctions;
    }
}
