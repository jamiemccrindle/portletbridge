<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
    exclude-result-prefixes="java">

  <xsl:output 
      method="html" 
      version="1.0" 
      indent="yes"
      omit-xml-declaration="yes"/>

  <xsl:param name="portlet"/>
  
  <xsl:template match="/">
  
  <div class="portlet-font">
  <p align="center">
    Written by Jamie McCrindle with code kindly donated by Rickard Öberg.
  </p>
  <p align="center">
    PortletBridge is licensed under the <a href="http://www.apache.org/licenses/LICENSE-2.0.html">Apache 2.0 license</a>
  </p>
  <p align="center">
  	See <a href="http://www.portletbridge.org">Portlet Bridge</a> for more information.
  </p>
  </div>
  
  </xsl:template>
   
</xsl:stylesheet>
