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

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author jmccrindle
 */
public class ResourceUtil {

    private ResourceUtil() {
        super();
    }

    public static void copy(InputStream inputStream, OutputStream outputStream,
            int bufSize) throws IOException {
        InputStream in = inputStream;
        BufferedOutputStream out = new BufferedOutputStream(outputStream,
                bufSize);
        try {
            byte[] b = new byte[4096];
            int i = -1;
            while ((i = in.read(b)) != -1) {
                out.write(b, 0, i);
            }
            out.flush();
        } finally {
            in.close();
        }
    }
    
    public static String getString(InputStream in, String charSet) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        copy(in, out, 4096);
        return new String(out.toByteArray(), charSet);
    }
}