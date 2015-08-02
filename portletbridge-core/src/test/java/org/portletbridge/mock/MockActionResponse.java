package org.portletbridge.mock;

import java.io.IOException;
import java.util.Map;

import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

public class MockActionResponse implements ActionResponse {

    @Override
    public void sendRedirect(String arg0) throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void setPortletMode(PortletMode arg0) throws PortletModeException {
        // TODO Auto-generated method stub
    }

    @Override
    public void setRenderParameter(String arg0, String arg1) {
        // TODO Auto-generated method stub
    }

    @Override
    public void setRenderParameter(String arg0, String[] arg1) {
        // TODO Auto-generated method stub
    }

    @Override
    public void setRenderParameters(Map arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void setWindowState(WindowState arg0) throws WindowStateException {
        // TODO Auto-generated method stub
    }

    @Override
    public void addProperty(String arg0, String arg1) {
        // TODO Auto-generated method stub
    }

    @Override
    public String encodeURL(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setProperty(String arg0, String arg1) {
        // TODO Auto-generated method stub
    }

}
