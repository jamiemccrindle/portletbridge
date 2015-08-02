package org.portletbridge.portlet;

import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import junit.framework.TestCase;

import org.cyberneko.html.parsers.SAXParser;
import org.portletbridge.mock.MockBridgeFunctions;
import org.xml.sax.InputSource;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

public class PortletBridgeXslTest extends TestCase {
    public void testSlashdotXsl() throws TransformerException, SAXNotRecognizedException, SAXNotSupportedException, FileNotFoundException {
        TransformerFactory tfactory = TransformerFactory.newInstance();
        URL resource = this.getClass().getResource("/org/portletbridge/xsl/default.xsl");
        Transformer transformer = tfactory.newTransformer(new StreamSource(resource.toExternalForm()));
        StringWriter writer = new StringWriter();
        SAXParser parser = new SAXParser();
        transformer.setParameter("bridge", new MockBridgeFunctions());
        transformer.transform(new SAXSource(parser, new InputSource(getClass().getResourceAsStream("/org/portletbridge/portlet/slashdot.html"))), new StreamResult(writer));
        writer.flush();
        System.out.println(writer.getBuffer().toString());
        
    }
    
    public void testHealtheVetXsl() throws TransformerException, SAXNotRecognizedException, SAXNotSupportedException, FileNotFoundException {
        TransformerFactory tfactory = TransformerFactory.newInstance();
        URL resource = this.getClass().getResource("/org/portletbridge/xsl/default.xsl");
        Transformer transformer = tfactory.newTransformer(new StreamSource(resource.toExternalForm()));
        StringWriter writer = new StringWriter();
        SAXParser parser = new SAXParser();
        transformer.setParameter("bridge", new MockBridgeFunctions());
        transformer.transform(new SAXSource(parser, new InputSource(getClass().getResourceAsStream("/org/portletbridge/portlet/myhealthevet.html"))), new StreamResult(writer));
        writer.flush();
        System.out.println(writer.getBuffer().toString());
        
    }
    
}
