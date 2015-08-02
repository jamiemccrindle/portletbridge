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
public class ResourceException extends Exception {

    /**
     * default serial version id 
     */
    private static final long serialVersionUID = 3347348123681235963L;

    private Object[] args;
    
    /**
     * 
     */
    public ResourceException() {
        super();
    }

    /**
     * @param key
     */
    public ResourceException(String key) {
        super(key);
    }

    /**
     * @param key
     */
    public ResourceException(String key, Object[] args) {
        super(key);
        this.args = args;
    }

    /**
     * @param key
     * @param string
     * @param cause
     */
    public ResourceException(String key, Object arg, Throwable cause) {
        this(key, new Object[] {arg}, cause);
    }

    /**
     * @param key
     * @param cause
     */
    public ResourceException(String key, Object[] args, Throwable cause) {
        super(key, cause);
        this.args = args;
    }

    /**
     * @param string
     * @param stylesheet
     */
    public ResourceException(String message, Object arg) {
        this(message, new Object[] {arg});
    }

    public Object[] getArgs() {
        return args;
    }
}
