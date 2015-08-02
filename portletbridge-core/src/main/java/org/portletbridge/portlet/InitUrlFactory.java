package org.portletbridge.portlet;

import java.net.URI;

import javax.portlet.RenderRequest;

import org.portletbridge.ResourceException;


/**
 * Interface for transforming the value of the {@code initUrl} preference.
 * 
 * Implementations of this interface are allowed to modify the value of the 
 * initUrl preference adding tokens (such as path elements or query string 
 * tokens) that are not known until the PortletBridge handles a request.
 *
 */
public interface InitUrlFactory {
	
	/**
	 * Modifies the value of the {@code initUrl} preference.
	 * 
	 * @param request the RenderRequest
	 * @return the transformed initUrl as a URI
	 * @throws ResourceException if there is a problem during building the
	 * transformed initUri.
	 */
	URI getInitUrl(RenderRequest request) throws ResourceException;
}
