package org.portletbridge.portlet;

import junit.framework.TestCase;

public class RegexContentRewriterTest extends TestCase {

    public RegexContentRewriterTest(String arg0) {
        super(arg0);
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /*
     * Test method for
     * 'org.portletbridge.portlet.RegexContentRewriter.rewrite(String,
     * LinkRewriter)'
     */
    public void testRewriteCss() {
        RegexContentRewriter rewriter = new RegexContentRewriter(
                "(?:url\\((?:'|\")?(.*?)(?:'|\")?\\))|(?:@import\\s+[^url](?:'|\")?(.*?)(?:'|\")|;|\\s+|$)");
        String string = "" + "@import 'one';\n" + "@import url('two');\n"
                + "url('three');\n";
        String result = rewriter.rewrite(null, string, new LinkRewriter() {
            public String link(String baseUrl, String link) {
                return "MATCHED_URL(" + link + ")";
            }
        });
        assertEquals("@import 'MATCHED_URL(one)';\n"
                + "@import url('MATCHED_URL(two)');\n"
                + "url('MATCHED_URL(three)');\n", result);
    }

    /*
     * Test method for
     * 'org.portletbridge.portlet.RegexContentRewriter.rewrite(String,
     * LinkRewriter)'
     */
    public void testRewriteJavascript() {
        RegexContentRewriter rewriter = new RegexContentRewriter(
                "open\\('([^']*)'|open\\(\"([^\"]*)\"");
        String string = "open('http://www.blah.com')";
        String result = rewriter.rewrite(null, string, new LinkRewriter() {
            public String link(String baseUrl, String link) {
                return "MATCHED_URL(" + link + ")";
            }
        });
        assertEquals("open('MATCHED_URL(http://www.blah.com)')", result);
    }

}
