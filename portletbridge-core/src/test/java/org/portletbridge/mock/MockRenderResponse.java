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
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

/**
 * @author jmccrindle
 */
public class MockRenderResponse implements RenderResponse {

    private StringWriter writer = new StringWriter();
    private String contentType = null;
    private String title;
    
    /**
     * 
     */
    public MockRenderResponse() {
        super();
    }

    /* (non-Javadoc)
     * @see javax.portlet.RenderResponse#getContentType()
     */
    public String getContentType() {
        return contentType;
    }

    /* (non-Javadoc)
     * @see javax.portlet.RenderResponse#createRenderURL()
     */
    public PortletURL createRenderURL() {
        return new MockPortletURL();
    }

    /* (non-Javadoc)
     * @see javax.portlet.RenderResponse#createActionURL()
     */
    public PortletURL createActionURL() {
        return new MockPortletURL();
    }

    /* (non-Javadoc)
     * @see javax.portlet.RenderResponse#getNamespace()
     */
    public String getNamespace() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.RenderResponse#setTitle(java.lang.String)
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /* (non-Javadoc)
     * @see javax.portlet.RenderResponse#setContentType(java.lang.String)
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /* (non-Javadoc)
     * @see javax.portlet.RenderResponse#getCharacterEncoding()
     */
    public String getCharacterEncoding() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.RenderResponse#getWriter()
     */
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(writer);
    }

    /* (non-Javadoc)
     * @see javax.portlet.RenderResponse#getLocale()
     */
    public Locale getLocale() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.RenderResponse#setBufferSize(int)
     */
    public void setBufferSize(int arg0) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see javax.portlet.RenderResponse#getBufferSize()
     */
    public int getBufferSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see javax.portlet.RenderResponse#flushBuffer()
     */
    public void flushBuffer() throws IOException {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see javax.portlet.RenderResponse#resetBuffer()
     */
    public void resetBuffer() {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see javax.portlet.RenderResponse#isCommitted()
     */
    public boolean isCommitted() {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see javax.portlet.RenderResponse#reset()
     */
    public void reset() {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see javax.portlet.RenderResponse#getPortletOutputStream()
     */
    public OutputStream getPortletOutputStream() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletResponse#addProperty(java.lang.String, java.lang.String)
     */
    public void addProperty(String arg0, String arg1) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletResponse#setProperty(java.lang.String, java.lang.String)
     */
    public void setProperty(String arg0, String arg1) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see javax.portlet.PortletResponse#encodeURL(java.lang.String)
     */
    public String encodeURL(String url) {
        return url;
    }
    
    public String retrieveContent() {
        return writer.getBuffer().toString();
    }

    public String retrieveTitle() {
        return title;
    }
}
