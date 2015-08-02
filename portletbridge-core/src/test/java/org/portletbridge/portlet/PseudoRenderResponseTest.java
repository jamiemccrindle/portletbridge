package org.portletbridge.portlet;

import junit.framework.TestCase;

public class PseudoRenderResponseTest extends TestCase {

    public PseudoRenderResponseTest(String arg0) {
        super(arg0);
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /*
     * Test method for 'org.portletbridge.portlet.PseudoRenderResponse.PseudoRenderResponse(String, String, String)'
     */
    public void testPseudoRenderResponseEnd() {
        PseudoRenderResponse response = new PseudoRenderResponse("namespace", "blah?id=1234", "1234");
        assertEquals("blah?id=", response.getStart());
        assertEquals("", response.getEnd());
    }

    public void testPseudoRenderResponseStart() {
        PseudoRenderResponse response = new PseudoRenderResponse("namespace", "1234&blah", "1234");
        assertEquals("", response.getStart());
        assertEquals("&blah", response.getEnd());
    }

    public void testPseudoRenderResponseExact() {
        PseudoRenderResponse response = new PseudoRenderResponse("namespace", "1234", "1234");
        assertEquals("", response.getStart());
        assertEquals("", response.getEnd());
    }

    public void testPseudoRenderResponseShorter() {
        PseudoRenderResponse response = new PseudoRenderResponse("namespace", "1", "1234");
        assertEquals("", response.getStart());
        assertEquals("", response.getEnd());
    }

    public void testPseudoRenderResponseMiddle() {
        PseudoRenderResponse response = new PseudoRenderResponse("namespace", "blah?id=1234&somethingelse", "1234");
        assertEquals("blah?id=", response.getStart());
        assertEquals("&somethingelse", response.getEnd());
    }

    public void testPseudoRenderResponseNone() {
        PseudoRenderResponse response = new PseudoRenderResponse("namespace", "blah?id=1234", "12345");
        assertEquals("", response.getStart());
        assertEquals("", response.getEnd());
    }

}
