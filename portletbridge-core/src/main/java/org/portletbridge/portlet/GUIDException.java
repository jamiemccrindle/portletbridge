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

/**
 * @author jmccrindle
 */
public class GUIDException extends Exception {

    /**
     * default serial version id 
     */
    private static final long serialVersionUID = -1842486373115955260L;

    /**
     * 
     */
    public GUIDException() {
        super();
    }

    /**
     * @param message
     */
    public GUIDException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public GUIDException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public GUIDException(String message, Throwable cause) {
        super(message, cause);
    }

}
