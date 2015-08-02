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
 * Default Id Generator. Uses the GUIDGenerator to generate
 * url friendly id's.
 * 
 * @author jmccrindle
 */
public class DefaultIdGenerator implements IdGenerator {

    private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
    .getLog(DefaultIdGenerator.class);

    private final GUIDGenerator generator;

    private static IdGenerator instance = new DefaultIdGenerator();

    /**
     * Unfortunate, because I can't stand singletons
     */
    public synchronized static IdGenerator getInstance() {
        return instance;
    }
    
    /**
     * Default Constructor
     */
    public DefaultIdGenerator() {
        try {
            this.generator = new GUIDGenerator();
        } catch (GUIDException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return the next integer
     */
    public synchronized String nextId() {
        log.trace("entering nextId()");
        
        return generator.getUUID();
    }

}
