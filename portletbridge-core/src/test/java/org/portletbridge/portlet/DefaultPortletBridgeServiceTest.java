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

import junit.framework.TestCase;

/**
 * @author JMcCrindle
 */
public class DefaultPortletBridgeServiceTest extends TestCase {

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Constructor for DefaultPortletBridgeServiceTest.
     * @param name
     */
    public DefaultPortletBridgeServiceTest(String name) {
        super(name);
    }

    public void testGetIdFromRequestUri() {
        DefaultPortletBridgeService service = new DefaultPortletBridgeService();
        String idFromRequestUri = service.getIdFromRequestUri("/portletbridge-portlet", "/portletbridge-portlet/pbhs/23/awards.shtml");
        assertEquals("23", idFromRequestUri);
        idFromRequestUri = service.getIdFromRequestUri("/portletbridge-portlet", "/portletbridge-portlet/pbhs/23/test/");
        assertEquals("23", idFromRequestUri);
        idFromRequestUri = service.getIdFromRequestUri("/portletbridge-portlet", "/portletbridge-portlet/pbhs/23//");
        assertEquals("23", idFromRequestUri);
        idFromRequestUri = service.getIdFromRequestUri("/portletbridge-portlet", "/portletbridge-portlet/pbhs/23/");
        assertEquals("23", idFromRequestUri);
        idFromRequestUri = service.getIdFromRequestUri("/portletbridge-portlet", "/portletbridge-portlet/pbhs/");
        idFromRequestUri = service.getIdFromRequestUri("", "/pbhs/23/");
        assertEquals("23", idFromRequestUri);
    }

}
