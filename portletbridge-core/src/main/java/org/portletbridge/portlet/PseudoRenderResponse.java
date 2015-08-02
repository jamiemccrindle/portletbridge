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

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

/**
 * TODO: There's a simpler way of doing this.
 * 
 * @author jmccrindle
 */
public class PseudoRenderResponse implements RenderResponse {
    
    private String namespace;
    private String start;
    private String end;
    private String idParamKey;
    
    @Deprecated
    public PseudoRenderResponse(String namespace, String currentUrl, String currentId) {
    	this(namespace, currentUrl, currentId, "id");
    }
    
    public PseudoRenderResponse(String namespace, String currentUrl, String currentId, String idParamKey) {
        this.namespace = namespace;
        int indexOfCurrentId = currentUrl.indexOf(currentId);
        if(indexOfCurrentId < 0) {
            this.start = "";
            this.end = "";
        } else {
            this.start = currentUrl.substring(0, indexOfCurrentId);
            this.end = currentUrl.substring(indexOfCurrentId + currentId.length());
        }
        
        this.idParamKey = idParamKey;
    }

    public String getContentType() {
        throw new UnsupportedOperationException();
    }

    public PortletURL createRenderURL() {
        return new PseudoPortletURL(start, end, idParamKey);
    }

    public PortletURL createActionURL() {
        throw new UnsupportedOperationException();
    }

    public String getNamespace() {
        return namespace;
    }

    public void setTitle(String arg0) {
        throw new UnsupportedOperationException();
    }

    public void setContentType(String arg0) {
        throw new UnsupportedOperationException();
    }

    public String getCharacterEncoding() {
        throw new UnsupportedOperationException();
    }

    public PrintWriter getWriter() throws IOException {
        throw new UnsupportedOperationException();
    }

    public Locale getLocale() {
        throw new UnsupportedOperationException();
    }

    public void setBufferSize(int arg0) {
        throw new UnsupportedOperationException();
    }

    public int getBufferSize() {
        throw new UnsupportedOperationException();
    }

    public void flushBuffer() throws IOException {
        throw new UnsupportedOperationException();
    }

    public void resetBuffer() {
        throw new UnsupportedOperationException();
    }

    public boolean isCommitted() {
        throw new UnsupportedOperationException();
    }

    public void reset() {
        throw new UnsupportedOperationException();
    }

    public OutputStream getPortletOutputStream() throws IOException {
        throw new UnsupportedOperationException();
    }

    public void addProperty(String arg0, String arg1) {
        throw new UnsupportedOperationException();
    }

    public void setProperty(String arg0, String arg1) {
        throw new UnsupportedOperationException();
    }

    public String encodeURL(String url) {
        return url;
    }

    public String getEnd() {
        return end;
    }

    public String getStart() {
        return start;
    }
    

}
