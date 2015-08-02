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

  <form method="post">
    <xsl:attribute name="action"><xsl:value-of select="java:actionUrl($portlet)"/></xsl:attribute>
    <table style="width:200px">
    <tr>
        <td><span class="portlet-form-field-label">Initial URL</span></td>
	  	<td>
	  		<input class="portlet-form-input-field" type="text" name="initUrl">
	  		<xsl:attribute name="value"><xsl:value-of select="java:preference($portlet, 'initUrl', '')"/></xsl:attribute>
	  		</input>
	  	</td>
  	</tr>
    <tr>
        <td><span class="portlet-form-field-label">Scope (Regex)</span></td>
	  	<td>
	  		<input class="portlet-form-input-field" type="text" name="scope">
	  		<xsl:attribute name="value"><xsl:value-of select="java:preference($portlet, 'scope', '.*')"/></xsl:attribute>
	  		</input>
	  	</td>
  	</tr>
    <tr>
        <td><span class="portlet-form-field-label">Proxy Host</span></td>
	  	<td>
	  		<input class="portlet-form-input-field" type="text" name="proxyHost">
	  		<xsl:attribute name="value"><xsl:value-of select="java:preference($portlet, 'proxyHost', java:systemProxyHost($portlet))"/></xsl:attribute>
	  		</input>
	  	</td>
  	</tr>
    <tr>
        <td><span class="portlet-form-field-label">Proxy Port</span></td>
	  	<td>
	  		<input class="portlet-form-input-field" type="text" name="proxyPort">
	  		<xsl:attribute name="value"><xsl:value-of select="java:preference($portlet, 'proxyPort', java:systemProxyPort($portlet))"/></xsl:attribute>
	  		</input>
	  	</td>
  	</tr>
    <tr>
        <td><span class="portlet-form-field-label">Proxy Authentication</span></td>
	  	<td>
	  	    <xsl:variable name="proxyAuthentication" select="java:preference($portlet, 'proxyAuthentication', 'none')"/>
	  		<select class="portlet-form-input-field" name="proxyAuthentication">
	  			<option value="none">
				    <xsl:if test="$proxyAuthentication='none'">
					  <xsl:attribute name="selected"/>
					</xsl:if>
	   				None
	   			</option>
	  			<option value="basic">
				    <xsl:if test="$proxyAuthentication='basic'">
					  <xsl:attribute name="selected"/>
					</xsl:if>
	   				Basic
	   			</option>
	  			<option value="ntlm">
				    <xsl:if test="$proxyAuthentication='ntlm'">
					  <xsl:attribute name="selected"/>
					</xsl:if>
	   				NTLM
	   			</option>
	  		</select>
	  	</td>
  	</tr>
    <tr>
        <td><span class="portlet-form-field-label">Proxy Authentication Username</span></td>
	  	<td>
	  		<input class="portlet-form-input-field" type="text" name="proxyAuthenticationUsername">
	  		<xsl:attribute name="value"><xsl:value-of select="java:preference($portlet, 'proxyAuthenticationUsername', '')"/></xsl:attribute>
	  		</input>
	  	</td>
  	</tr>
    <tr>
        <td><span class="portlet-form-field-label">Proxy Authentication Password</span></td>
	  	<td>
	  		<input class="portlet-form-input-field" type="password" name="proxyAuthenticationPassword">
	  		<xsl:attribute name="value"><xsl:value-of select="java:preference($portlet, 'proxyAuthenticationPassword', '')"/></xsl:attribute>
	  		</input>
	  	</td>
  	</tr>
    <tr>
        <td><span class="portlet-form-field-label">Proxy Authentication Host</span></td>
	  	<td>
	  		<input class="portlet-form-input-field" type="text" name="proxyAuthenticationHost">
	  		<xsl:attribute name="value"><xsl:value-of select="java:preference($portlet, 'proxyAuthenticationHost', '')"/></xsl:attribute>
	  		</input>
	  	</td>
  	</tr>
    <tr>
        <td><span class="portlet-form-field-label">Proxy Authentication Domain</span></td>
	  	<td>
	  		<input class="portlet-form-input-field" type="text" name="proxyAuthenticationDomain">
	  		<xsl:attribute name="value"><xsl:value-of select="java:preference($portlet, 'proxyAuthenticationDomain', '')"/></xsl:attribute>
	  		</input>
	  	</td>
  	</tr>
    <tr>
        <td colspan="2"><span class="portlet-form-field-label">Stylesheet</span></td>
    </tr>
    <tr>
	  	<td colspan="2">
	  		<textarea rows="20" cols="35" style="font-family: Courier;" type="text" name="stylesheet">
		  		<xsl:value-of select="java:preference($portlet, 'stylesheet', '')"/>
	  		</textarea>
	  	</td>
  	</tr>
  	<tr><td colspan="2" align="right"><input class="portlet-form-button" type="submit" value="Update"/></td></tr>
  	</table>
  </form>  
  
  </xsl:template>

</xsl:stylesheet>
