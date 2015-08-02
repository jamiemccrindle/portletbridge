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

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * @author JMcCrindle
 */
public class MockServletConfig implements ServletConfig {

    private Hashtable params = new Hashtable();
    private String name = null;
    private ServletContext context = null;
    
    /**
     * 
     */
    public MockServletConfig() {
        super();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletConfig#getServletName()
     */
    public String getServletName() {
        return name;
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletConfig#getServletContext()
     */
    public ServletContext getServletContext() {
        return context;
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletConfig#getInitParameter(java.lang.String)
     */
    public String getInitParameter(String name) {
        return (String) params.get(name);
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletConfig#getInitParameterNames()
     */
    public Enumeration getInitParameterNames() {
        return params.keys();
    }
    
    public void setupParam(String name, String value) {
        params.put(name, value);
    }
    
    public void setupContext(ServletContext context) {
        this.context = context;
    }

}
