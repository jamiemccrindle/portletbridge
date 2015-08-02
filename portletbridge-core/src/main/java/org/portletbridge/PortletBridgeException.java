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
package org.portletbridge;

/**
 * @author jmccrindle
 */
public class PortletBridgeException extends ResourceException {

    /**
     * default serial version id 
     */
    private static final long serialVersionUID = 8111408787079575082L;

    /**
     * @param key
     */
    public PortletBridgeException(String key) {
        super(key);
    }
    /**
     * @param message
     * @param arg
     */
    public PortletBridgeException(String message, Object arg) {
        super(message, arg);
    }
    /**
     * @param key
     * @param arg
     * @param cause
     */
    public PortletBridgeException(String key, Object arg, Throwable cause) {
        super(key, arg, cause);
    }
    /**
     * @param key
     * @param args
     */
    public PortletBridgeException(String key, Object[] args) {
        super(key, args);
    }
    /**
     * @param key
     * @param args
     * @param cause
     */
    public PortletBridgeException(String key, Object[] args, Throwable cause) {
        super(key, args, cause);
    }
    /**
     * 
     */
    public PortletBridgeException() {
        super();
    }

}
