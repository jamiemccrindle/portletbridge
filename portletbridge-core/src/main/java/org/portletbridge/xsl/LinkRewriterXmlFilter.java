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
package org.portletbridge.xsl;

import java.util.HashMap;
import java.util.Map;

import org.portletbridge.PortletBridgeException;
import org.portletbridge.StyleSheetRewriter;
import org.portletbridge.UrlRewriter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.XMLFilterImpl;

/**
 * This will not work with a base tag that points to a different host
 * 
 * @author JMcCrindle
 */
public class LinkRewriterXmlFilter extends XMLFilterImpl {

    private String baseUrl = null;

    private UrlRewriter urlRewriter = null;
    
    private StyleSheetRewriter styleSheetRewriter = null;

    private String originalUrl;
    
    private Map context = new HashMap();
    
    private boolean inStyle = false;
    
    private StringBuffer styleContent = null;

    private static final Map elementsWithUrls = new HashMap();

    static {
        elementsWithUrls.put("a", "href");
        elementsWithUrls.put("base", "href");
        elementsWithUrls.put("link", "href");
        elementsWithUrls.put("area", "href");
        elementsWithUrls.put("img", "src");
        elementsWithUrls.put("input", "src");
        elementsWithUrls.put("frame", "src");
        elementsWithUrls.put("iframe", "src");
        elementsWithUrls.put("embed", "src");
        elementsWithUrls.put("xml", "src");
        elementsWithUrls.put("script", "src");
        elementsWithUrls.put("form", "action");
    }

    /**
     * Default Constructor
     */
    public LinkRewriterXmlFilter() {
        super();
    }

    /**
     * @param parent
     */
    public LinkRewriterXmlFilter(XMLReader parent) {
        super(parent);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.XMLFilterImpl#startElement(java.lang.String,
     *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement(final String uri, final String localName,
            final String qName, final Attributes atts) throws SAXException {
        
        if("style".equalsIgnoreCase(localName) && !inStyle) {
            inStyle = true;
            styleContent = new StringBuffer();
        } else if(inStyle) {
            // rewrite
            String rewrittenStyleContent;
            try {
                rewrittenStyleContent = styleSheetRewriter.rewrite(styleContent.toString());
            } catch (PortletBridgeException e) {
                throw new SAXException(e);
            }
            char[] chars = rewrittenStyleContent.toCharArray();
            super.characters(chars, 0, chars.length);
            inStyle = false;
        }
        
        String attr = (String) elementsWithUrls.get(localName.toLowerCase());
        if (attr != null) {
            int urlIndex = atts.getIndex(attr);
            if (urlIndex != -1) {
                final String url = atts.getValue(urlIndex);
                final AttributesImpl attributes = new AttributesImpl(atts);
                try {
                    attributes.setAttribute(urlIndex, atts.getURI(urlIndex),
                            atts.getLocalName(urlIndex), atts
                                    .getQName(urlIndex),
                            atts.getType(urlIndex), urlRewriter.rewrite(
                                    url));
                } catch (PortletBridgeException e) {
                    throw new SAXException(e);
                }
                super.startElement(uri, localName,
                        qName, attributes);
            } else {
                super.startElement(uri, localName, qName, atts);
            }
        } else {
            super.startElement(uri, localName, qName, atts);
        }
    }

    /* (non-Javadoc)
     * @see org.xml.sax.helpers.XMLFilterImpl#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if("style".equalsIgnoreCase(localName) && inStyle) {
            String rewrittenStyleContent;
            try {
                rewrittenStyleContent = styleSheetRewriter.rewrite(styleContent.toString());
                char[] chars = rewrittenStyleContent.toCharArray();
                super.characters(chars, 0, chars.length);
                inStyle = false;
            } catch (PortletBridgeException e) {
                throw new SAXException(e);
            }
        }
        super.endElement(uri, localName, qName);
    }
    
    /* (non-Javadoc)
     * @see org.xml.sax.helpers.XMLFilterImpl#characters(char[], int, int)
     */
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        if(inStyle) {
            styleContent.append(ch, start, length);
        } else {
            super.characters(ch, start, length);
        }
    }

    public void setUrlRewriter(UrlRewriter urlRewriter) {
        this.urlRewriter = urlRewriter;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setStyleSheetRewriter(StyleSheetRewriter styleSheetRewriter) {
        this.styleSheetRewriter = styleSheetRewriter;
    }
}