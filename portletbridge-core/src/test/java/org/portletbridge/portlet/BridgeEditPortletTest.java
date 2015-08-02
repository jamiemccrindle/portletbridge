package org.portletbridge.portlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import junit.framework.TestCase;

import org.portletbridge.mock.MockActionRequest;
import org.portletbridge.mock.MockActionResponse;
import org.portletbridge.mock.MockPortletPreferences;
import org.portletbridge.mock.MockRenderRequest;
import org.portletbridge.mock.MockRenderResponse;

/**
 * Test the Bridge Edit Portlet 
 * 
 * @author jamie
 */
public class BridgeEditPortletTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }
    
    /**
     * Test the calling edit returns the edit HTML 
     * 
     * @throws Exception if anything goes wrong
     */
    public void testEdit() throws Exception {
        BridgeEditPortlet portlet = new BridgeEditPortlet();
        TemplateFactory templateFactory = new DefaultTemplateFactory();
        portlet.setTemplates(templateFactory.getTemplatesFromUrl("classpath:/org/portletbridge/xsl/pages/edit.xsl"));
        MockRenderRequest request = new MockRenderRequest();
        request.setupPortletPreferences(new MockPortletPreferences());
        
        final StringWriter result = new StringWriter();
        
        portlet.doEdit(request, new MockRenderResponse() {
            @Override
            public PrintWriter getWriter() throws IOException {
                return new PrintWriter(result);
            }
        });
        
        String editForm = result.toString();
        
        // check to see that what came out the other end contains what we expect it to
        
        assertTrue(editForm.matches("(?s).*form.*"));
        assertTrue(editForm.matches("(?s).*input.*"));
        assertTrue(editForm.matches("(?s).*Initial URL.*"));
        
    }
    
    public void testSubmitAction() throws Exception {
        BridgeEditPortlet portlet = new BridgeEditPortlet();
        MockActionRequest request = new MockActionRequest();
        request.setupPortletPreferences(new MockPortletPreferences());
        portlet.processAction(request, new MockActionResponse());
    }

}
