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

import java.net.URI;
import java.util.regex.Pattern;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * Functions class for XSLT used for rewriting urls etc.
 * 
 * @author JMcCrindle
 * @author rickard
 */
public class BridgeFunctions implements LinkRewriter {
    
    private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
    .getLog(BridgeFunctions.class);
    
    private final Pattern DEFAULT_PATTERN = Pattern.compile(".*");

    private final URI currentUrl;

    private final RenderRequest request;

    private final RenderResponse response;

    private final String servletName;

    private final PortletBridgeMemento memento;

    private final Pattern scope;

    private final PerPortletMemento perPortletMemento;

    private final IdGenerator idGenerator;

    private final ContentRewriter javascriptRewriter;

    private final ContentRewriter cssRewriter;

    public BridgeFunctions(ContentRewriter javascriptRewriter, ContentRewriter cssRewriter, IdGenerator idGenerator, PortletBridgeMemento memento,
            PerPortletMemento perPortletMemento, String servletName,
            URI currentUrl, RenderRequest request, RenderResponse response) {
        this.javascriptRewriter = javascriptRewriter;
        this.cssRewriter = cssRewriter;
        this.idGenerator = idGenerator;
        this.memento = memento;
        this.perPortletMemento = perPortletMemento;
        this.servletName = servletName;
        this.currentUrl = currentUrl;
        this.request = request;
        this.response = response;
        if(perPortletMemento != null) {
            this.scope = perPortletMemento.getScope();
        } else {
            this.scope = DEFAULT_PATTERN;
        }
    }

    /**
     * Rewrite a link
     */
    public String link(String baseUrl, String link) {
        if (link.startsWith("javascript:")) {
            // if's a javascript link, rewrite it using the script rewriter
            return script(baseUrl, link);
        } else if (link.startsWith("#")) {
            // if it's a reference to somewhere in the current page leave it alone
            return link;
        } else {
            // otherwise rewrite it
            return rewrite(baseUrl, link, true);
        }
    }
    
    private URI resolveUrl(URI base, String trim) {
        URI url = null;
        if(trim.startsWith("?")) {
            return URI.create(base.getScheme( ) + "://" + base.getAuthority() + base.getPath() + trim);            
        } else {
            url = base.resolve(trim);
        }
        return url;
    }

    /**
     * The rewriter
     * 
     * @param baseUrl the base url from the document
     * @param link the link to rewrite
     * @param checkScope whether scope should be checked
     * @return the rewritten url
     */
    private String rewrite(String baseUrl, String link, boolean checkScope) {
        // replacing spaces in the url with %20's because... well, there shouldn't be spaces.
        String trim = link.trim().replace(" ", "%20");
        URI url = null;
        // if we have a base url, we need to resolve it and then use the result 
        // rather then the currentUrl
        if(baseUrl != null && baseUrl.trim().length() > 0) {
            // consider caching this
            URI baseUri = currentUrl.resolve(baseUrl);
            url = resolveUrl(baseUri, trim);
        } else {
            url = resolveUrl(currentUrl, trim);
        }
        if (url.getScheme().equals("http") || url.getScheme().equals("https")) {
            if (!checkScope || shouldRewrite(url)) {
                BridgeRequest bridgeRequest = memento.createBridgeRequest(
                        response, idGenerator.nextId(), url);
                String name = url.getPath();
                int lastIndex = name.lastIndexOf('/');
                if (lastIndex != -1) {
                    name = name.substring(lastIndex + 1);
                    if (name.equals("") && lastIndex > 0)
                        name = url.getPath().substring(
                                url.getPath().lastIndexOf('/', lastIndex - 1));

                }
                if (name.startsWith("/"))
                    name = name.substring(1);
                name = response.encodeURL(request.getContextPath() + '/' + servletName + '/'
                        + bridgeRequest.getId() + "/" + name);
                return name;
            } else {
                return url.toString();
            }
        } else {
            return link;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.portletbridge.StyleSheetRewriter#rewrite(java.lang.String)
     */
    public String style(String baseUrl, String css) {
        return cssRewriter.rewrite(baseUrl, css, this);
    }

    private boolean shouldRewrite(URI uri) {
        return scope.matcher(uri.toString()).matches();
    }

    public String script(String baseUrl, String script) {
        return javascriptRewriter.rewrite(baseUrl, script, this);
    }

    public URI getCurrentUrl() {
        return currentUrl;
    }
    
    public void setTitle(String title) {
        response.setTitle(title);
    }

    public PortletBridgeMemento getMemento() {
        return memento;
    }

    public PerPortletMemento getPerPortletMemento() {
        return perPortletMemento;
    }

    public RenderRequest getRequest() {
        return request;
    }

    public RenderResponse getResponse() {
        return response;
    }

    public String getServletName() {
        return servletName;
    }
    
    public boolean equalsIgnoreCase(String s1, String s2) {
        if(s1 == s2) return true;
        if(s1 == null) return false;
        return s1.equalsIgnoreCase(s2);
    }

}
