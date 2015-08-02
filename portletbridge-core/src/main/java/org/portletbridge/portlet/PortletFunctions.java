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

import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author JMcCrindle
 */
public class PortletFunctions {
    
    private RenderRequest request = null;
    private RenderResponse response = null;
    private PortletPreferences preferences;

    public PortletFunctions(RenderRequest request, RenderResponse response) {
        this.request = request;
        this.response = response;
        this.preferences = request.getPreferences();
    }

    public RenderRequest getRequest() {
        return request;
    }
    public RenderResponse getResponse() {
        return response;
    }
    public PortletPreferences getPreferences() {
        return preferences;
    }
    public String actionUrl() {
        PortletURL portletURL = this.response.createActionURL();
        return portletURL.toString();
    }
    public String preference(String name, String defaultValue) {
        return preferences.getValue(name, defaultValue);
    }
    public String systemProxyHost() {
        return System.getProperty("proxyHost");
    }
    public String systemProxyPort() {
        return System.getProperty("proxyPort");
    }
}
