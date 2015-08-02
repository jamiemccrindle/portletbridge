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

import java.io.Reader;
import java.net.URI;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.portletbridge.ResourceException;

/**
 * The portlet uses implementations of this class to transform data 
 * from the downstream site into the portlet content
 * 
 * @author JMcCrindle
 */
public interface BridgeTransformer {

    /**
     * Transform the downstream content to portlet content
     * 
     * @param memento the portlet memento to use
     * @param perPortletMemento the memento for the particular portlet instance
     * @param currentUrl the current url
     * @param request the render request
     * @param response the render response to write the result out to
     * @param in the reader to read the downstream content from
     * @throws ResourceException if there is a problem transforming the content
     */
    void transform(PortletBridgeMemento memento, PerPortletMemento perPortletMemento, URI currentUrl,
            RenderRequest request, RenderResponse response,
            Reader in) throws ResourceException;
}
