/**
 * Copyright (c) 2000-2022 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.faces.portal.context;

import javax.faces.FacesWrapper;
import javax.portlet.ActionResponse;
import javax.portlet.PortalContext;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;


/**
 * @author  Neil Griffin
 */
public abstract class PortletHelperWrapper implements PortletHelper, FacesWrapper<PortletHelper> {

	@Override
	public abstract PortletHelper getWrapped();

	public PortletURL createActionURL() {
		return getWrapped().createActionURL();
	}

	public PortletURL createRenderURL() {
		return getWrapped().createRenderURL();
	}

	public ActionResponse getActionResponse() {
		return getWrapped().getActionResponse();
	}

	public PortalContext getPortalContext() {
		return getWrapped().getPortalContext();
	}

	public PortletConfig getPortletConfig() {
		return getWrapped().getPortletConfig();
	}

	public PortletContext getPortletContext() {
		return getWrapped().getPortletContext();
	}

	public String getPortletContextName() {
		return getWrapped().getPortletContextName();
	}

	public String getPortletName() {
		return getWrapped().getPortletName();
	}

	public Object getPortletPreference(String preferenceName, Object defaultValue) {
		return getWrapped().getPortletPreference(preferenceName, defaultValue);
	}

	public boolean getPortletPreferenceAsBool(String preferenceName, boolean defaultValue) {
		return getWrapped().getPortletPreferenceAsBool(preferenceName, defaultValue);
	}

	public int getPortletPreferenceAsInt(String preferenceName, int defaultValue) {
		return getWrapped().getPortletPreferenceAsInt(preferenceName, defaultValue);
	}

	public short getPortletPreferenceAsShort(String preferenceName, short defaultValue) {
		return getWrapped().getPortletPreferenceAsShort(preferenceName, defaultValue);
	}

	public String getPortletPreferenceAsString(String preferenceName, String defaultValue) {
		return getWrapped().getPortletPreferenceAsString(preferenceName, defaultValue);
	}

	public PortletPreferences getPortletPreferences() {
		return getWrapped().getPortletPreferences();
	}

	public RenderRequest getPortletRenderRequest() {
		return getWrapped().getPortletRenderRequest();
	}

	public RenderResponse getPortletRenderResponse() {
		return getWrapped().getPortletRenderResponse();
	}

	public PortletRequest getPortletRequest() {
		return getWrapped().getPortletRequest();
	}

	public PortletResponse getPortletResponse() {
		return getWrapped().getPortletResponse();
	}

	public PortletSession getPortletSession() {
		return getWrapped().getPortletSession();
	}

	public String getRemoteUser() {
		return getWrapped().getRemoteUser();
	}

	public Object getSessionSharedAttribute(String name) {
		return getWrapped().getSessionSharedAttribute(name);
	}

	public WindowState getWindowState() {
		return getWrapped().getWindowState();
	}

	public boolean isPortletEnvironment() {
		return getWrapped().isPortletEnvironment();
	}

	public boolean isUserInRole(String roleName) {
		return getWrapped().isUserInRole(roleName);
	}

	public boolean isWindowMaximized() {
		return getWrapped().isWindowMaximized();
	}

	public boolean isWindowNormal() {
		return getWrapped().isWindowNormal();
	}

	public void setPortletMode(PortletMode portletMode) {
		getWrapped().setPortletMode(portletMode);
	}

	public void setSessionSharedAttribute(String name, Object value) {
		getWrapped().setSessionSharedAttribute(name, value);
	}

	public void setWindowState(WindowState windowState) {
		getWrapped().setWindowState(windowState);
	}

	public void setWindowStateToMaximized() {
		getWrapped().setWindowStateToMaximized();
	}

	public void setWindowStateToNormal() {
		getWrapped().setWindowStateToNormal();
	}
}
