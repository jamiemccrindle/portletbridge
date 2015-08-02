package org.portletbridge.mock;

import java.net.URI;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.portletbridge.portlet.BridgeFunctions;
import org.portletbridge.portlet.PerPortletMemento;
import org.portletbridge.portlet.PortletBridgeMemento;

public class MockBridgeFunctions extends BridgeFunctions {

    public MockBridgeFunctions() {
        super(null, null, null, null, null,
                null, null, null, null);
    }

    public URI getCurrentUrl() {
        return null;
    }
    
    public PortletBridgeMemento getMemento() {
        return null;
    }
    
    public PerPortletMemento getPerPortletMemento() {
        return null;
    }
    
    public RenderRequest getRequest() {
        return null;
    }
    
    public RenderResponse getResponse() {
        return null;
    }
    
    public String getServletName() {
        return null;
    }
    
    public String link(String baseUrl, String link) {
        return link;
    }
    
    public String script(String baseUrl, String script) {
        return script;
    }
    
    public void setTitle(String title) {
    }
    
    public String style(String baseUrl, String css) {
        return css;
    }

}
