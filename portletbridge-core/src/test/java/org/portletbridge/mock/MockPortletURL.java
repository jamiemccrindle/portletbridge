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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletSecurityException;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

/**
 * @author jmccrindle
 */
public class MockPortletURL implements PortletURL {

    private Map parameters = new HashMap();
    private WindowState windowState;
    private PortletMode portletMode;
    
    /**
     * 
     */
    public MockPortletURL() {
        super();
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletURL#setWindowState(javax.portlet.WindowState)
     */
    public void setWindowState(WindowState windowState) throws WindowStateException {
        this.windowState = windowState;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletURL#setPortletMode(javax.portlet.PortletMode)
     */
    public void setPortletMode(PortletMode portletMode) throws PortletModeException {
        this.portletMode = portletMode;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletURL#setParameter(java.lang.String, java.lang.String)
     */
    public void setParameter(String name, String value) {
        parameters.put(name, new String[] {value});
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletURL#setParameter(java.lang.String, java.lang.String[])
     */
    public void setParameter(String name, String[] values) {
        parameters.put(name, values);
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletURL#setParameters(java.util.Map)
     */
    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletURL#setSecure(boolean)
     */
    public void setSecure(boolean secure) throws PortletSecurityException {
    }
    
    /* (non-Javadoc)
     * @see javax.portlet.PortletURL#toString()
     */
    public String toString() {
        StringBuffer result = new StringBuffer("mock?");
        if(windowState != null) {
            result.append("ws=" + windowState + "&");
        }
        if(portletMode != null) {
            result.append("pm=" + portletMode + "&");
        }
        if(parameters.size() > 0) {
            for (Iterator iter = parameters.entrySet().iterator(); iter.hasNext();) {
                Map.Entry entry = (Map.Entry) iter.next();
                String name = (String) entry.getKey();
                String[] values = (String[]) entry.getValue();
                for (int i = 0; i < values.length; i++) {
                    String value = values[i];
                    result.append(name + "=" + value + "&");
                }
            }
        }
        return result.toString().substring(0, result.length() - 1);
    }

}
