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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

public class RewriterTest extends TestCase {
        
    public void testMatching() {
        String string = "123456";
        Matcher matcher = Pattern.compile("4(5)6|.(.).").matcher(string);
        assertEquals(2, matcher.groupCount());
        matcher.find();
        int matchingGroup = extractGroup(matcher);
        assertEquals(0, matcher.start());
        assertEquals(3, matcher.end());
        assertEquals("123", string.substring(matcher.start(), matcher.end()));
        assertEquals(1, matcher.start(matchingGroup));
        assertEquals(2, matcher.end(matchingGroup));
        matcher.find(matcher.end());
        for(int i = 1; i <= matcher.groupCount(); i++) {
            if(matcher.start(i) > -1) {
                matchingGroup = i;
                break;
            }
        }
        assertEquals(3, matcher.start());
        assertEquals(6, matcher.end());
        assertEquals("456", string.substring(matcher.start(), matcher.end()));
        assertEquals(4, matcher.start(matchingGroup));
        assertEquals(5, matcher.end(matchingGroup));
    }
    
    private int extractGroup(Matcher matcher) {
        int matchingGroup = -1;
        for(int i = 1; i <= matcher.groupCount(); i++) {
            if(matcher.start(i) > -1) {
                matchingGroup = i;
                break;
            }
        }
        return matchingGroup;
    }
}
