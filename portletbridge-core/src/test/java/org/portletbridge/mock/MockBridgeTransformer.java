package org.portletbridge.mock;

import java.io.Reader;
import java.net.URI;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.portletbridge.ResourceException;
import org.portletbridge.portlet.BridgeTransformer;
import org.portletbridge.portlet.PerPortletMemento;
import org.portletbridge.portlet.PortletBridgeMemento;

public class MockBridgeTransformer implements BridgeTransformer {

    public void transform(PortletBridgeMemento memento,
            PerPortletMemento perPortletMemento, URI currentUrl,
            RenderRequest request, RenderResponse response, Reader in)
            throws ResourceException {
    }

}
