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
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

/**
 * Default implementation of PortletBridgeMemento.
 * 
 * This implementation returns instances of {@link DefaultBridgeRequest 
 * DefaultBridgeRequests} and {@link DefaultPerPortletMemento
 * DefaultPerPortletMementos}
 * 
 * @author JMcCrindle
 */
public class DefaultPortletBridgeMemento implements PortletBridgeMemento, Serializable {

    /**
     * default serial version id 
     */
    private static final long serialVersionUID = -5751042731400361166L;

    private final Map<String, BridgeRequest> idToRequests =
        new HashMap<String, BridgeRequest>();
    private Map<String, BridgeRequest> dataToRequests =
        new HashMap<String, BridgeRequest>();
    private Map<String, PerPortletMemento> mementos =
        new HashMap<String, PerPortletMemento>();
    private final String idParamKey;
    private final BridgeAuthenticator bridgeAuthenticator;
    private final InitUrlFactory initUrlFactory;

    
    /**
     * Constructs a new instance of DefaultPortletBridgeMemento.
     * 
     * Implementation note:  the InitUrlFactory and BridgeAuthenticator 
     * parameters are used when constructing the PerPortletMementos.
     * 
     * @param idParamKey name for the query parameter to use for referencing the
     * id of the BridgeRequest. 
     * @param bridgeAuthenticator the BridgeAuthenticator
     * @param initUrlFactory the initUrlFactory
     */
    public DefaultPortletBridgeMemento(String idParamKey, BridgeAuthenticator bridgeAuthenticator, InitUrlFactory initUrlFactory) {
        this.idParamKey = idParamKey;
        this.bridgeAuthenticator = bridgeAuthenticator;
        this.initUrlFactory = initUrlFactory;
    }

    
    /**
     * {@inheritDoc}
     */
    public BridgeRequest getBridgeRequest(String id) {
        return idToRequests.get(id);
    }


    /**
     * {@inheritDoc}
     * 
     * This implementation will return instances of DefaultPerPortletMemento.
     * If no instance of PerPortletMemento is stored with the given portletId,
     * a new one is created, stored with the key and returned.
     */
    public PerPortletMemento getPerPortletMemento(String portletId) {
        PerPortletMemento memento = mementos.get(portletId);
        if(memento == null) {
            memento = new DefaultPerPortletMemento(bridgeAuthenticator, initUrlFactory);
            mementos.put(portletId, memento);
        }
        return memento;
    }

    
    /**
     * {@inheritDoc}
     * 
     * This implementation will return instances of 
     * {@link DefaultBridgeRequest}.
     */
    public BridgeRequest createBridgeRequest(RenderResponse response, String id, URI url) {
        PortletURL pageUrl = response.createRenderURL();
        String namespace = response.getNamespace();
        String key = namespace + pageUrl.toString() + url.toString();
        BridgeRequest request = dataToRequests.get(key);
        if(request != null) {
            return request;
        } else {
            pageUrl.setParameter(idParamKey, id);
            DefaultBridgeRequest bridgeRequest = new DefaultBridgeRequest(id, namespace, pageUrl.toString(), url);
            idToRequests.put(id, bridgeRequest);
            dataToRequests.put(key, bridgeRequest);
            return bridgeRequest;
        }
    }

}
