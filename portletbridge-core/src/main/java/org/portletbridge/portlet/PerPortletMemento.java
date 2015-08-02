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
import java.util.regex.Pattern;

import javax.portlet.RenderRequest;

import org.portletbridge.ResourceException;

/**
 * A per user memento of preference values and Http state.
 * 
 * @author JMcCrindle
 */
public interface PerPortletMemento extends HttpClientState {


    /**
     * Converts PortletPreference values into values that are used by a single
     * user.  The preference values converted by this method depends on the
     * implementation of this method.  This could include but not be limited to
     * {@code initUrl} and {@code scope}
     *
     * @param request the RenderRequest
     *
     * @throws ResourceException if there's a problem converting the preference
     * values.
     */
    void setPreferences(RenderRequest request) throws ResourceException;


    /**
     * Returns a Pattern object usually constructed from the value of the {@code
     * scope} preference. The returned Pattern is used for deciding whether a 
     * URL should be rewritten or not i.e. does the URL fall under the scope of 
     * this portlet or is it an external URL. If the pattern matches, it is
     * considered in scope.
     *
     * @return a Pattern
     */
    Pattern getScope();


    /**
     * Returns the value of the {@code initUrl} as a {@link java.net.URI URI}.
     *
     * This URI could be modified by an implementation of {@link InitUrlFactory}
     * before it is returned.
     *
     * @return the initUrl as a URI.
     */
    URI getInitUrl();


    /**
     * Caches content fetched from a downstream source.  Content may or may not
     * be retained when it is fetched from the backing cache.
     *
     * @param bridgeRequestId the ID for the given content.
     * @param content the content to cache.
     */
    void enqueueContent(String bridgeRequestId, PortletBridgeContent content);


    /**
     * Retrieves the content with the given ID.  Content may or may not be
     * retained when it is fetched from the backing cache.
     *
     * @param bridgeRequestId the ID for the given content.
     * @return the PortletBridgeContent corresponding to the given ID.
     */
    PortletBridgeContent dequeueContent(String bridgeRequestId);
}
