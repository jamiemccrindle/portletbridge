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

import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamSource;

import org.portletbridge.ResourceException;

/**
 * @author JMcCrindle
 */
public class DefaultTemplateFactory implements TemplateFactory {

    private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
            .getLog(DefaultTemplateFactory.class);
    
    private Templates defaultTemplate;

    private String defaultTemplateSytemId;
    
    private Map templateCache = Collections.synchronizedMap(new HashMap());

    public DefaultTemplateFactory() {
        defaultTemplateSytemId = getClass().getResource(
                "/org/portletbridge/xsl/default.xsl").toExternalForm();
        Source templateSource = new StreamSource(defaultTemplateSytemId);
        try {
            defaultTemplate = TransformerFactory.newInstance().newTemplates(
                    templateSource);
            log.debug("created default template");
        } catch (TransformerConfigurationException e) {
            throw new IllegalStateException(e.getMessage());
        } catch (TransformerFactoryConfigurationError e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    /**
     * Creates compiled templates for a particular stylesheet for performance.
     * 
     * @param systemId
     *            the stylesheet to compile
     * @return @throws
     *         ResourceException if the stylesheet could not be found.
     * @throws TransformerFactoryConfigurationError
     *             if there was a problem finding a suitable transformer
     *             factory.
     */
    public Templates getTemplatesFromUrl(String systemId)
            throws ResourceException, TransformerFactoryConfigurationError {
        if (systemId == null) {
            throw new ResourceException("error.stylesheet");
        }
        Templates result = null;
        TransformerFactory factory = TransformerFactory.newInstance();
        try {
            // this means that the templatecache is going to be a mix
            // of md5 checksums and urls
            Templates templates = (Templates) templateCache.get(systemId);
            if(templates != null) {
                return templates;
            } else {
                URL resourceUrl = null;
                if (systemId.startsWith("classpath:")) {
                    String substring = systemId.substring(10);
                    resourceUrl = this.getClass().getResource(substring);
                } else {
                    resourceUrl = new URL(systemId);
                }
                if (resourceUrl == null) {
                    throw new ResourceException("error.stylesheet.notfound",
                            systemId);
                }
                result = factory.newTemplates(new StreamSource(resourceUrl
                        .toExternalForm()));
                templateCache.put(systemId, result);
            }
        } catch (TransformerConfigurationException e) {
            throw new ResourceException("error.transformer", e.getMessage(), e);
        } catch (MalformedURLException e) {
            throw new ResourceException("error.stylesheet.url", e.getMessage(),
                    e);
        }
        return result;
    }

    /**
     * Creates compiled templates for a particular stylesheet for performance.
     * 
     * @param stylesheet
     *            the stylesheet to compile
     * @return @throws
     *         ResourceException if the stylesheet could not be found.
     * @throws TransformerFactoryConfigurationError
     *             if there was a problem finding a suitable transformer
     *             factory.
     */
    public Templates getTemplatesFromString(String stylesheet)
            throws ResourceException, TransformerFactoryConfigurationError {

        if (stylesheet == null || stylesheet.trim().length() == 0) {
            return defaultTemplate;
        }
        Templates result = null;
        TransformerFactory factory = TransformerFactory.newInstance();
        final Exception[] exceptionHolder = new Exception[1];
        factory.setErrorListener(new ErrorListener() {
            public void error(TransformerException exception)
                    throws TransformerException {
                exceptionHolder[0] = exception;
            }

            public void fatalError(TransformerException exception)
                    throws TransformerException {
                exceptionHolder[0] = exception;
            }

            public void warning(TransformerException exception)
                    throws TransformerException {
                exceptionHolder[0] = exception;
            }
        });

        try {
            // caching but lots of object creation...
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(stylesheet.getBytes());
            String key = new String(messageDigest.digest());
            Templates templates = (Templates) templateCache.get(key);
            if(templates != null) {
                result = templates;
            } else {
                result = factory.newTemplates(new StreamSource(new StringReader(
                        stylesheet), defaultTemplateSytemId));
                templateCache.put(key, result);
            }
        } catch (TransformerConfigurationException e) {
            exceptionHolder[0] = e;
        } catch (NoSuchAlgorithmException e) {
            exceptionHolder[0] = e;
        }
        if (exceptionHolder[0] != null) {
            throw new ResourceException("error.transformer", exceptionHolder[0].getMessage(), exceptionHolder[0]);
        }
        return result;
    }

}