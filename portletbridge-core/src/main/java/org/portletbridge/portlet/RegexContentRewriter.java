package org.portletbridge.portlet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexContentRewriter implements ContentRewriter {

    private Pattern urlPattern;

    public RegexContentRewriter(String pattern) {
        urlPattern = Pattern
                .compile(pattern);
    }
    
    public String rewrite(String baseUrl, String content, LinkRewriter linkRewriter) {
        Matcher matcher = urlPattern.matcher(content);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()) {
            int group = extractGroup(matcher);
            if(group > 0) {
                String before = content.substring(matcher.start(), matcher.start(group));
                String url = matcher.group(group);
                String after = content.substring(matcher.end(group), matcher.end());
                matcher.appendReplacement(sb, before + linkRewriter.link(baseUrl, url) + after);
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
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
