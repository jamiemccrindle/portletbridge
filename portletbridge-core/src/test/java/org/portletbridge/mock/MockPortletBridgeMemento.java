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

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.RenderResponse;

import org.portletbridge.portlet.BridgeRequest;
import org.portletbridge.portlet.DefaultBridgeRequest;
import org.portletbridge.portlet.PerPortletMemento;
import org.portletbridge.portlet.PortletBridgeMemento;

/**
 * @author JMcCrindle
 */
public class MockPortletBridgeMemento implements PortletBridgeMemento {

    private Map bridgeRequests = new HashMap();
    private Map mementos = new HashMap();
    
    /**
     * 
     */
    public MockPortletBridgeMemento() {
        super();
    }
    
    public void setupBridgeRequest(String id, BridgeRequest bridgeRequest) {
        bridgeRequests.put(id, bridgeRequest);
    }

    public void setupMemento(String id, PerPortletMemento memento) {
        mementos.put(id, memento);
    }

    /* (non-Javadoc)
     * @see org.portletbridge.portlet.PortletBridgeMemento#getBridgeRequest(java.lang.String)
     */
    public BridgeRequest getBridgeRequest(String id) {
        return (BridgeRequest) bridgeRequests.get(id);
    }

    /* (non-Javadoc)
     * @see org.portletbridge.portlet.PortletBridgeMemento#getPerPortletMemento(java.lang.String)
     */
    public PerPortletMemento getPerPortletMemento(String portletId) {
        return (PerPortletMemento) mementos.get(portletId);
    }

    /* (non-Javadoc)
     * @see org.portletbridge.portlet.PortletBridgeMemento#createBridgeRequest(javax.portlet.RenderResponse, java.net.URI)
     */
    public BridgeRequest createBridgeRequest(RenderResponse response, String id, URI url) {
        return new DefaultBridgeRequest();
    }


}
