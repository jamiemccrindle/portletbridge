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

import java.io.Serializable;
import java.net.URI;

/**
 * Default implementation fo the BridgeRequest
 * 
 * @author jmccrindle
 */
public class DefaultBridgeRequest implements Serializable, BridgeRequest {
    
    /**
     * default serial version id 
     */
    private static final long serialVersionUID = 4504756989609725196L;

    private String id = null;
    private String portletId = null;
    private String pageUrl = null;
    private URI url = null;
    
    public DefaultBridgeRequest() {
        
    }
    
    /**
     * Create a new BridgeRequest
     * 
     * @param id the id of the bridge request
     * @param portletId the portletId for the portlet instance (usually the portlet namespace)
     * @param pageUrl the portlet url to redirect to 
     * @param url the url of the downstream site.
     */
    public DefaultBridgeRequest(String id, String portletId, String pageUrl, URI url) {
        super();
        this.id = id;
        this.portletId = portletId;
        this.pageUrl = pageUrl;
        this.url = url;
    }
    
    public String getPageUrl() {
        return pageUrl;
    }
    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }
    public String getPortletId() {
        return portletId;
    }
    public void setPortletId(String portletId) {
        this.portletId = portletId;
    }
    public URI getUrl() {
        return url;
    }
    public void setUrl(URI url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

}
