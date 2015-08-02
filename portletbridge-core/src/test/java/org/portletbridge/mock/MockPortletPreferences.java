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

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

/**
 * @author jmccrindle
 */
public class MockPortletPreferences implements PortletPreferences {

    private Hashtable preferences = new Hashtable();
    
    /**
     * 
     */
    public MockPortletPreferences() {
        super();
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletPreferences#isReadOnly(java.lang.String)
     */
    public boolean isReadOnly(String arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletPreferences#getValue(java.lang.String, java.lang.String)
     */
    public String getValue(String name, String defaultValue) {
        String[] values = getValues(name, null);
        if(values == null ||  values.length == 0) {
            return defaultValue;
        }
        return values[0];
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletPreferences#getValues(java.lang.String, java.lang.String[])
     */
    public String[] getValues(String name, String[] defaultValue) {
        String[] values = (String[]) preferences.get(name);
        if(values == null) {
            return defaultValue;
        }
        return values;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletPreferences#setValue(java.lang.String, java.lang.String)
     */
    public void setValue(String name, String value) throws ReadOnlyException {
        preferences.put(name, new String[] {value});
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletPreferences#setValues(java.lang.String, java.lang.String[])
     */
    public void setValues(String name, String[] value) throws ReadOnlyException {
        preferences.put(name, value);
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletPreferences#getNames()
     */
    public Enumeration getNames() {
        return preferences.keys();
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletPreferences#getMap()
     */
    public Map getMap() {
        return preferences;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletPreferences#reset(java.lang.String)
     */
    public void reset(String name) throws ReadOnlyException {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletPreferences#store()
     */
    public void store() throws IOException, ValidatorException {
        // TODO Auto-generated method stub
        
    }

}
