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

import org.apache.commons.httpclient.HttpMethodBase;
import org.portletbridge.ResourceException;

/**
 * Implementations of this interface fetch content from a given resource.
 *
 * @author JMcCrindle
 */
public interface HttpClientTemplate {

    /**
     * Calls the URL specified by the given {@code method}.
     *
     * @param method the Http nethod used to fetch content at the given URL.
     * @param state any credentials or cookies needed by the URL.
     * @param callback used to manipulate the content that was fetched by
     * the {@code method}.
     * @return the result of the retrieval if it applies.
     * @throws ResourceException if there is a problem fetching the content.
     */
    Object service(HttpMethodBase method, HttpClientState state,
            HttpClientCallback callback) throws ResourceException;
}
