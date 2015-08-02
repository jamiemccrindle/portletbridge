package org.portletbridge.portlet;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.NTCredentials;
import org.portletbridge.mock.MockPortletPreferences;
import org.portletbridge.mock.MockRenderRequest;

import junit.framework.TestCase;

public class DefaultBridgeAuthenticatorTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }
    
    public void testAuthNoCredentials() throws Exception {
        DefaultBridgeAuthenticator authenticator = new DefaultBridgeAuthenticator();
        MockRenderRequest request = new MockRenderRequest();
        request.setupPortletPreferences(new MockPortletPreferences());
        Credentials credentials = authenticator.getCredentials(request);
        assertNull(credentials);
    }

    public void testAuthNTLM() throws Exception {
        DefaultBridgeAuthenticator authenticator = new DefaultBridgeAuthenticator();
        MockRenderRequest request = new MockRenderRequest();
        MockPortletPreferences portletPreferences = new MockPortletPreferences();
        portletPreferences.setValue("authentication", "ntlm");
        portletPreferences.setValue("authenticationUsername", "test");
        portletPreferences.setValue("authenticationPassword", "test");
        portletPreferences.setValue("authenticationHost", "test");
        portletPreferences.setValue("authenticationDomain", "test");
        request.setupPortletPreferences(portletPreferences);
        Credentials credentials = authenticator.getCredentials(request);
        assertTrue(credentials instanceof NTCredentials);
    }

}
