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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.portletbridge.ResourceException;
import org.portletbridge.portlet.HttpClientCallback;
import org.portletbridge.portlet.HttpClientState;
import org.portletbridge.portlet.HttpClientTemplate;

/**
 * @author JMcCrindle
 */
public class MockHttpClientTemplate implements HttpClientTemplate {

    private int statusCode = HttpStatus.SC_OK;
    private InputStream responseBody = new ByteArrayInputStream(new byte[] { -1 });
    private String responseCharSet = "UTF-8";
    private Map responseHeaders = new HashMap();
    
    /**
     * 
     */
    public MockHttpClientTemplate() {
        super();
    }

    /* (non-Javadoc)
     * @see org.portletbridge.portlet.HttpClientTemplate#doGet(java.net.URI, org.portletbridge.portlet.HttpClientState, org.portletbridge.portlet.HttpClientCallback)
     */
    public Object service(HttpMethodBase method, HttpClientState state,
            HttpClientCallback callback) throws ResourceException {
        try {
            return callback.doInHttpClient(statusCode, new GetMethod(method.getURI().toString()) {
                public InputStream getResponseBodyAsStream() throws IOException {
                    return responseBody;
                }

                public String getResponseCharSet() {
                    return responseCharSet;
                }
                
                /* (non-Javadoc)
                 * @see org.apache.commons.httpclient.HttpMethodBase#getResponseHeader(java.lang.String)
                 */
                public Header getResponseHeader(String name) {
                    String headerValue = (String) responseHeaders.get(name);
                    if(headerValue != null) {
                        Header header = new Header(name, headerValue);
                        return header;
                    } else {
                        return null;
                    }
                }
            });
        } catch (Throwable e) {
            throw new ResourceException("error.httpclient", e.getMessage(), e);
        }
    }

    public void setupResponseHeader(String name, String value) {
        this.responseHeaders.put(name, value);
    }

}
