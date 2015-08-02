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

import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletSecurityException;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

/**
 * TODO: There's a simpler way of doing this 
 * @author jmccrindle
 */
public class PseudoPortletURL implements PortletURL {

    private String id;
    private String idParamKey;
    private String start;
    private String end;

    @Deprecated
    public PseudoPortletURL(String start, String end) {
    	this(start, end, "id");
    }
    
    public PseudoPortletURL(String start, String end, String idParamKey) {
    	this.start = start;
    	this.end = end;
    	this.idParamKey = idParamKey;
    }

    public void setWindowState(WindowState arg0) throws WindowStateException {
        throw new UnsupportedOperationException();
    }

    public void setPortletMode(PortletMode arg0) throws PortletModeException {
        throw new UnsupportedOperationException();
    }

    public void setParameter(String key, String value) {
        if (idParamKey.equals(key)) {
            this.id = value;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public void setParameter(String arg0, String[] arg1) {
        throw new UnsupportedOperationException();
    }

    public void setParameters(Map arg0) {
        throw new UnsupportedOperationException();
    }

    public void setSecure(boolean arg0) throws PortletSecurityException {
        throw new UnsupportedOperationException();
    }
    
    public String toString() {
        return start + id + end;
    }

}
