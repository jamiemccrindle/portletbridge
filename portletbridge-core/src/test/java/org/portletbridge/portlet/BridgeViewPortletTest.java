package org.portletbridge.portlet;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

import org.portletbridge.mock.MockBridgeTransformer;
import org.portletbridge.mock.MockHttpClientTemplate;
import org.portletbridge.mock.MockPortletConfig;
import org.portletbridge.mock.MockPortletPreferences;
import org.portletbridge.mock.MockPortletSession;
import org.portletbridge.mock.MockRenderRequest;
import org.portletbridge.mock.MockRenderResponse;

import junit.framework.TestCase;

public class BridgeViewPortletTest extends TestCase {

    public BridgeViewPortletTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /*
     * Test method for 'org.portletbridge.portlet.BridgeViewPortlet.doView(RenderRequest, RenderResponse)'
     */
    public void testDoView() throws Exception {
        BridgeViewPortlet portlet = new BridgeViewPortlet();
        ResourceBundle bundle = PropertyResourceBundle.getBundle("org.portletbridge.portlet.PortletBridgePortlet");
        MockPortletConfig mockPortletConfig = new MockPortletConfig();
        mockPortletConfig.setupResourceBundle(bundle);
        portlet.init(mockPortletConfig);
        portlet.setHttpClientTemplate(new MockHttpClientTemplate());
        portlet.setMementoSessionKey("mementoSessionKey");
        portlet.setTransformer(new MockBridgeTransformer());
        MockRenderRequest mockRenderRequest = new MockRenderRequest();
        mockRenderRequest.setupWindowState(WindowState.NORMAL);
        mockRenderRequest.setupPortletMode(PortletMode.VIEW);
        mockRenderRequest.setupPortletSession(new MockPortletSession());
        MockPortletPreferences mockPortletPreferences = new MockPortletPreferences();
        mockPortletPreferences.setValue("initUrl", "http://blah:8080/");
        mockPortletPreferences.setValue("stylesheet", "classpath:/org/portletbridge/xsl/default.xsl");
        mockRenderRequest.setupPortletPreferences(mockPortletPreferences);
        MockRenderResponse mockRenderResponse = new MockRenderResponse();
        portlet.render(mockRenderRequest, mockRenderResponse);
    }
    
    public static void main(String[] args) throws Exception {
        BridgeViewPortletTest test = new BridgeViewPortletTest("BridgeViewPortletTest");
        test.setUp();
        test.testDoView();
        test.tearDown();
    }

}
