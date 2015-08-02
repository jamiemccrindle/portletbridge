package org.portletbridge.portlet;

public interface ContentRewriter {
    String rewrite(String baseUrl, String content, LinkRewriter linkRewriter);
}
