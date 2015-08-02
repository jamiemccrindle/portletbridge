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

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;

/**
 * @author jmccrindle
 */
public class MockPortletConfig implements PortletConfig {

    private Hashtable initParams = new Hashtable();
    private ResourceBundle bundle;
    
    /**
     * Default Constructor
     */
    public MockPortletConfig() {
        super();
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletConfig#getPortletName()
     */
    public String getPortletName() {
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletConfig#getPortletContext()
     */
    public PortletContext getPortletContext() {
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletConfig#getResourceBundle(java.util.Locale)
     */
    public ResourceBundle getResourceBundle(Locale locale) {
        return bundle;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletConfig#getInitParameter(java.lang.String)
     */
    public String getInitParameter(String name) {
        return (String) initParams.get(name);
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletConfig#getInitParameterNames()
     */
    public Enumeration getInitParameterNames() {
        return initParams.keys();
    }
    
    public void setupInitParam(String name, String value) {
        initParams.put(name, value);
    }

    /**
     * @param bundle
     */
    public void setupResourceBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

}
