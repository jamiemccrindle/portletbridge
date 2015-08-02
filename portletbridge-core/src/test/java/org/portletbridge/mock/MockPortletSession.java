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

import javax.portlet.PortletContext;
import javax.portlet.PortletSession;

/**
 * @author jmccrindle
 */
public class MockPortletSession implements PortletSession {

    private Hashtable localAttributes = new Hashtable();
    private Hashtable globalAttributes = new Hashtable();
    
    /**
     * 
     */
    public MockPortletSession() {
        super();
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletSession#getAttribute(java.lang.String)
     */
    public Object getAttribute(String name) {
        return localAttributes.get(name);
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletSession#getAttribute(java.lang.String, int)
     */
    public Object getAttribute(String name, int scope) {
        return scope == PortletSession.PORTLET_SCOPE ? localAttributes.get(name) : globalAttributes.get(name);
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletSession#getAttributeNames()
     */
    public Enumeration getAttributeNames() {
        return localAttributes.keys();
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletSession#getAttributeNames(int)
     */
    public Enumeration getAttributeNames(int scope) {
        return scope == PortletSession.PORTLET_SCOPE ? localAttributes.keys() : globalAttributes.keys();
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletSession#getCreationTime()
     */
    public long getCreationTime() {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletSession#getId()
     */
    public String getId() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletSession#getLastAccessedTime()
     */
    public long getLastAccessedTime() {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletSession#getMaxInactiveInterval()
     */
    public int getMaxInactiveInterval() {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletSession#invalidate()
     */
    public void invalidate() {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletSession#isNew()
     */
    public boolean isNew() {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletSession#removeAttribute(java.lang.String)
     */
    public void removeAttribute(String name) {
        localAttributes.remove(name);
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletSession#removeAttribute(java.lang.String, int)
     */
    public void removeAttribute(String name, int scope) {
        if(scope == PortletSession.PORTLET_SCOPE) {
            localAttributes.remove(name);
        } else {
            globalAttributes.remove(name);
        }
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletSession#setAttribute(java.lang.String, java.lang.Object)
     */
    public void setAttribute(String name, Object value) {
        localAttributes.put(name, value);
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletSession#setAttribute(java.lang.String, java.lang.Object, int)
     */
    public void setAttribute(String name, Object value, int scope) {
        if(scope == PortletSession.PORTLET_SCOPE) {
            localAttributes.put(name, value);
        } else {
            globalAttributes.put(name, value);
        }
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletSession#setMaxInactiveInterval(int)
     */
    public void setMaxInactiveInterval(int arg0) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletSession#getPortletContext()
     */
    public PortletContext getPortletContext() {
        // TODO Auto-generated method stub
        return null;
    }

}
