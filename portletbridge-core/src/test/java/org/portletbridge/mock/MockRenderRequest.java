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
package org.portletbridge.mock;

import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortalContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.WindowState;

/**
 * @author jmccrindle
 */
public class MockRenderRequest implements RenderRequest {

    private WindowState windowState;
    private PortletMode portletMode;
    private PortletSession portletSession;
    private PortletPreferences portletPreferences;

    /**
     * 
     */
    public MockRenderRequest() {
        super();
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#isWindowStateAllowed(javax.portlet.WindowState)
     */
    public boolean isWindowStateAllowed(WindowState arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#isPortletModeAllowed(javax.portlet.PortletMode)
     */
    public boolean isPortletModeAllowed(PortletMode arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#getPortletMode()
     */
    public PortletMode getPortletMode() {
        return portletMode;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#getWindowState()
     */
    public WindowState getWindowState() {
        return windowState;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#getPreferences()
     */
    public PortletPreferences getPreferences() {
        return portletPreferences;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#getPortletSession()
     */
    public PortletSession getPortletSession() {
        return portletSession;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#getPortletSession(boolean)
     */
    public PortletSession getPortletSession(boolean arg0) {
        return portletSession;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#getProperty(java.lang.String)
     */
    public String getProperty(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#getProperties(java.lang.String)
     */
    public Enumeration getProperties(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#getPropertyNames()
     */
    public Enumeration getPropertyNames() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#getPortalContext()
     */
    public PortalContext getPortalContext() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#getAuthType()
     */
    public String getAuthType() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#getContextPath()
     */
    public String getContextPath() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#getRemoteUser()
     */
    public String getRemoteUser() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#getUserPrincipal()
     */
    public Principal getUserPrincipal() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#isUserInRole(java.lang.String)
     */
    public boolean isUserInRole(String arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#getAttribute(java.lang.String)
     */
    public Object getAttribute(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#getAttributeNames()
     */
    public Enumeration getAttributeNames() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#getParameter(java.lang.String)
     */
    public String getParameter(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#getParameterNames()
     */
    public Enumeration getParameterNames() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#getParameterValues(java.lang.String)
     */
    public String[] getParameterValues(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#getParameterMap()
     */
    public Map getParameterMap() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#isSecure()
     */
    public boolean isSecure() {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#setAttribute(java.lang.String, java.lang.Object)
     */
    public void setAttribute(String arg0, Object arg1) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#removeAttribute(java.lang.String)
     */
    public void removeAttribute(String arg0) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#getRequestedSessionId()
     */
    public String getRequestedSessionId() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#isRequestedSessionIdValid()
     */
    public boolean isRequestedSessionIdValid() {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#getResponseContentType()
     */
    public String getResponseContentType() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#getResponseContentTypes()
     */
    public Enumeration getResponseContentTypes() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#getLocale()
     */
    public Locale getLocale() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#getLocales()
     */
    public Enumeration getLocales() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#getScheme()
     */
    public String getScheme() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#getServerName()
     */
    public String getServerName() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletRequest#getServerPort()
     */
    public int getServerPort() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    public void setupWindowState(WindowState windowState) {
        this.windowState = windowState;
    }
    
    public void setupPortletMode(PortletMode portletMode) {
        this.portletMode = portletMode;
    }
    
    public void setupPortletSession(PortletSession portletSession) {
        this.portletSession = portletSession;
    }
    
    public void setupPortletPreferences(PortletPreferences portletPreferences) {
        this.portletPreferences = portletPreferences;
    }

}
