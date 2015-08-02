package org.portletbridge.portlet;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import junit.framework.TestCase;

import org.cyberneko.html.parsers.SAXParser;
import org.xml.sax.InputSource;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

public class XslTest extends TestCase {
    public void testXsl() throws TransformerException, SAXNotRecognizedException, SAXNotSupportedException {
        TransformerFactory tfactory = TransformerFactory.newInstance();
        Transformer transformer = tfactory.newTransformer(new StreamSource(new StringReader("" +
                "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">" +
                "<xsl:output method=\"html\"" + 
                "    encoding=\"UTF-8\"" + 
                "    indent=\"yes\"" + 
                "    standalone=\"no\"" + 
                "    omit-xml-declaration=\"yes\"/>" + 
                "   <xsl:template match=\"//TITLE\">" +
                "   <a href=\"http://www.slashdot.org\"><xsl:value-of select=\".\"/></a>" +
                "   </xsl:template>" +
                "</xsl:stylesheet>")));
        StringWriter writer = new StringWriter();
        SAXParser parser = new SAXParser();
        //parser.setFeature("http://cyberneko.org/html/features/override-namespaces", true);
        //parser.setFeature("http://cyberneko.org/html/features/insert-namespaces", true);  
        transformer.transform(new SAXSource(parser, new InputSource(
                new StringReader("" + 
                // "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" +
                // "\r\n<HTML xmlns=\"http://www.w3.org/1999/xhtml\">" +
                "<HTML>" + 
                "<HEAD><TITLE>TEST</TITLE></HEAD>" +
                "<BODY>" +
                "<FORM>" +
                "<INPUT type=\"image\" src=\"blah\"/>" +
                "<INPUT type=\"text\" src=\"blah\"/>" +
                "Blah" + 
                "</FORM>" +
                "</BODY>" +
                "</HTML>"))), new StreamResult(writer));
        writer.flush();
        System.out.println(writer.getBuffer().toString());
        
    }
}
