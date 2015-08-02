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

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

public class BridgeFunctionsFactory {
    
    private final IdGenerator idGenerator;
    private final ContentRewriter javascriptRewriter;
    private final ContentRewriter cssRewriter;

    public BridgeFunctionsFactory(IdGenerator idGenerator, ContentRewriter javascriptRewriter, ContentRewriter cssRewriter) {
        this.idGenerator = idGenerator;
        this.javascriptRewriter = javascriptRewriter;
        this.cssRewriter = cssRewriter;
    }
    
    public BridgeFunctions createBridgeFunctions(PortletBridgeMemento memento,
            PerPortletMemento perPortletMemento, String servletName,
            URI currentUrl, RenderRequest request, RenderResponse response) {
        BridgeFunctions bridgeFunctions = new BridgeFunctions(javascriptRewriter, cssRewriter, idGenerator, memento, perPortletMemento, servletName, currentUrl, request, response);
        return bridgeFunctions;
    }
}
