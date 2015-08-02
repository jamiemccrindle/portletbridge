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

import javax.portlet.RenderRequest;

import org.apache.commons.httpclient.Credentials;
import org.portletbridge.ResourceException;

/**
 * Interface for supporting pluggable authentication. 
 * 
 * Implementations of this interface build a set of 
 * {@link org.apache.commons.httpclient.Credentials Credentials} that are used 
 * by the downstream resource (e.g. allowing access to a page behind BASIC or 
 * NTLM authentication).
 * 
 * Implementations of this interface need to be specified as an init
 * parameter in the portlet.xml file using the {@code authenticatorClassName}
 * parameter.
 * 
 * @author jmccrindle
 *
 */
public interface BridgeAuthenticator {
	
    /**
     * Builds and returns a set of Credentials.  Implementations of this method
     * are allowed to return null.
     * 
     * @param request the render request.
     * @return a subclass of Credentials for HttpClient to use or null if
     * the implementation allows for it.
     * @throws ResourceException if there was a problem getting
     *          the credentials.
     */
    Credentials getCredentials(RenderRequest request) throws ResourceException;
}
