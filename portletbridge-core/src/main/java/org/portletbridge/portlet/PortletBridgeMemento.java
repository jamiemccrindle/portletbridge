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

import java.net.URI;

import javax.portlet.RenderResponse;

/**
 * Stores and creates requests for the PortletBridge and stores current state of
 * the Portlet.
 * 
 * Instances of PortletBridgeMemento are responsible for generating and storing
 * {@link BridgeRequest BridgeRequests} and for generating and storing 
 * {@link PerPortletMemento PerPortletMementos}.
 * 
 * @author jmccrindle
 */
public interface PortletBridgeMemento {
	
	/**
	 * Retrieves the BridgeRequest created with the given ID.
	 * 
	 * @param id the ID of the BridgeRequest
	 * @return the BridgeRequest
	 */
    BridgeRequest getBridgeRequest(String id);
    
    
    /**
     * Creates a BridgeRequest from the given RenderResponse and URI.
     * 
     * @param response the RenderResponse
     * @param id the ID to use for this BridgeRequest's key
     * @param url the URI of the request.
     * @return a BridgeRequest
     */
    BridgeRequest createBridgeRequest(RenderResponse response, String id, URI url);
    
    
    /**
     * Returns the PerPortletMemento keyed by the given portletId.
     * 
     * Implementations have the option of creating a new PerPortletMemento 
     * instance if none is keyed by the given portletId.
     * 
     * @param portletId the portletId
     * @return a PerPortletMemento.
     */
    PerPortletMemento getPerPortletMemento(String portletId);
}
