package org.portletbridge.portlet;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;

import org.portletbridge.ResourceException;

/**
 * A default implementation of the InitUrlFactory.
 * 
 * This implementation does not change the value of the {@code initUrl} 
 * preference.
 *
 */
public class DefaultInitUrlFactory implements InitUrlFactory, Serializable {

	private static final long serialVersionUID = 8646992143972717917L;

	/**
	 * {@inheritDoc}
	 * 
	 * This implementation will throw a ResourceException if the initUrl 
	 * preference is not set or if the value of the initUrl preference is a
	 * 
	 */
	public URI getInitUrl(RenderRequest request) throws ResourceException {
		PortletPreferences preferences = request.getPreferences();
        String initUrlPreference = preferences.getValue("initUrl", null);
        if (initUrlPreference == null || initUrlPreference.trim().length() == 0) {
            throw new ResourceException("error.initurl",
                    "preference not defined");
        }
        try {
            return new URI(initUrlPreference);
        } catch (URISyntaxException e) {
            throw new ResourceException("error.initurl", e.getMessage(), e);
        }
	}

}
