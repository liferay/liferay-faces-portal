/**
 * Copyright (c) 2000-2016 Liferay, Inc. All rights reserved.
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
 * This class contains a set of static convenience methods that delegate to the corresponding methods on {@link
 * PortletHelper}.
 *
 * @author  Neil Griffin
 */
public class PortletHelperUtil {

	/**
	 * Retrieves a render URL for the current facesContext.
	 */
	public static PortletURL createActionURL() {
		return PortletHelperFactory.getPortletHelperInstance().createActionURL();
	}

	/**
	 * Retrieves a render URL for the current facesContext.
	 */
	public static PortletURL createRenderURL() {
		return PortletHelperFactory.getPortletHelperInstance().createRenderURL();
	}

	/**
	 * Retrieves the <code>javax.portlet.ActionResponse</code> object associated with the current JSF FacesContext.
	 */
	public static ActionResponse getActionResponse() {
		return PortletHelperFactory.getPortletHelperInstance().getActionResponse();
	}

	/**
	 * Retrieves the <code>javax.portlet.PortalContext</code> object associated with the current JSF FacesContext.
	 */
	public static PortalContext getPortalContext() {
		return PortletHelperFactory.getPortletHelperInstance().getPortalContext();
	}

	/**
	 * Retrieves the <code>javax.portlet.PortletConfig</code> object associated with the current JSF FacesContext.
	 */
	public static PortletConfig getPortletConfig() {
		return PortletHelperFactory.getPortletHelperInstance().getPortletConfig();
	}

	/**
	 * Retrieves the <code>javax.portlet.PortletContext</code> object associated with the current JSF FacesContext.
	 */
	public static PortletContext getPortletContext() {
		return PortletHelperFactory.getPortletHelperInstance().getPortletContext();
	}

	/**
	 * Retrieves the name of the portlet associated with the current JSF FacesContext (usually, its the name of the
	 * portlet .war)
	 */
	public static String getPortletContextName() {
		return PortletHelperFactory.getPortletHelperInstance().getPortletContextName();
	}

	/**
	 * Retrieves the name of the portlet associated with the current JSF FacesContext (as defined in portlet.xml's
	 * &lt;portlet-name&gt; tag).
	 */
	public static String getPortletName() {
		return PortletHelperFactory.getPortletHelperInstance().getPortletName();
	}

	/**
	 * Returns String representation of the value of the portlet preference associated with the specified name in from
	 * the <code>javax.portlet.PortletPreferences</code> object associated with the current JSF FacesContext.
	 */
	public static Object getPortletPreference(String preferenceName, Object defaultValue) {
		return PortletHelperFactory.getPortletHelperInstance().getPortletPreference(preferenceName, defaultValue);
	}

	/**
	 * Returns boolean representation of the value of the portlet preference associated with the specified name in from
	 * the PortletPreferenceMap object associated with the current JSF FacesContext.
	 */
	public static boolean getPortletPreferenceAsBool(String preferenceName, boolean defaultValue) {
		return PortletHelperFactory.getPortletHelperInstance().getPortletPreferenceAsBool(preferenceName, defaultValue);
	}

	/**
	 * Returns integer representation of the value of the portlet preference associated with the specified name in from
	 * the PortletPreferenceMap object associated with the current JSF FacesContext.
	 */
	public static int getPortletPreferenceAsInt(String preferenceName, int defaultValue) {
		return PortletHelperFactory.getPortletHelperInstance().getPortletPreferenceAsInt(preferenceName, defaultValue);
	}

	/**
	 * Returns short representation of the value of the portlet preference associated with the specified name in from
	 * the PortletPreferenceMap object associated with the current JSF FacesContext.
	 */
	public static short getPortletPreferenceAsShort(String preferenceName, short defaultValue) {
		return PortletHelperFactory.getPortletHelperInstance().getPortletPreferenceAsShort(preferenceName,
				defaultValue);
	}

	/**
	 * Returns short representation of the value of the portlet preference associated with the specified name in from
	 * the PortletPreferenceMap object associated with the current JSF FacesContext.
	 */
	public static String getPortletPreferenceAsString(String preferenceName, String defaultValue) {
		return PortletHelperFactory.getPortletHelperInstance().getPortletPreferenceAsString(preferenceName,
				defaultValue);
	}

	/**
	 * Retrieves the <code>javax.portlet.PortletPreferences</code> object associated with the current JSF FacesContext.
	 */
	public static PortletPreferences getPortletPreferences() {
		return PortletHelperFactory.getPortletHelperInstance().getPortletPreferences();
	}

	/**
	 * Retrieves the <code>javax.portlet.RenderRequest</code> object associated with the current JSF FacesContext.
	 */
	public static RenderRequest getPortletRenderRequest() {
		return PortletHelperFactory.getPortletHelperInstance().getPortletRenderRequest();
	}

	/**
	 * Retrieves the <code>javax.portlet.RenderResponse</code> object associated with the current JSF FacesContext.
	 */
	public static RenderResponse getPortletRenderResponse() {
		return PortletHelperFactory.getPortletHelperInstance().getPortletRenderResponse();
	}

	/**
	 * Retrieves the <code>javax.portlet.PortletRequest</code> object associated with the current JSF FacesContext.
	 */
	public static PortletRequest getPortletRequest() {
		return PortletHelperFactory.getPortletHelperInstance().getPortletRequest();
	}

	/**
	 * Retrieves the <code>javax.portlet.PortletResponse</code> object associated with the current JSF FacesContext.
	 */
	public static PortletResponse getPortletResponse() {
		return PortletHelperFactory.getPortletHelperInstance().getPortletResponse();
	}

	/**
	 * Retrieves the <code>javax.portlet.PortletSession</code> object associated with the current JSF FacesContext.
	 */
	public static PortletSession getPortletSession() {
		return PortletHelperFactory.getPortletHelperInstance().getPortletSession();
	}

	/**
	 * Returns the user name/id of the user associated with the current JSF FacesContext
	 */
	public static String getRemoteUser() {
		return PortletHelperFactory.getPortletHelperInstance().getRemoteUser();
	}

	/**
	 * Returns the value of the session attribute associated with the specified name from
	 * PortletSession.APPLICATION_SCOPE
	 */
	public static Object getSessionSharedAttribute(String name) {
		return PortletHelperFactory.getPortletHelperInstance().getSessionSharedAttribute(name);
	}

	/**
	 * Returns the window state of the portlet associated with the current JSF FacesContext
	 */
	public static WindowState getWindowState() {
		return PortletHelperFactory.getPortletHelperInstance().getWindowState();
	}

	/**
	 * Returns TRUE if the underlying request/response is from a portlet environment. Otherwise, must be running in a
	 * servlet environment.
	 */
	public static boolean isPortletEnvironment() {
		return PortletHelperFactory.getPortletHelperInstance().isPortletEnvironment();
	}

	/**
	 * Returns TRUE if the current user is associated with the specified role.
	 */
	public static boolean isUserInRole(String roleName) {
		return PortletHelperFactory.getPortletHelperInstance().isUserInRole(roleName);
	}

	/**
	 * Returns TRUE if the Window state of the current portlet is set to MAXIMIZED
	 */
	public static boolean isWindowMaximized() {
		return PortletHelperFactory.getPortletHelperInstance().isWindowMaximized();
	}

	/**
	 * Returns TRUE if the Window state of the current portlet is set to MAXIMIZED
	 */
	public static boolean isWindowNormal() {
		return PortletHelperFactory.getPortletHelperInstance().isWindowNormal();
	}

	/**
	 * Sets the portlet mode of the portlet associated with the current JSF FacesContext to the specified portlet mode.
	 */
	public static void setPortletMode(PortletMode portletMode) {
		PortletHelperFactory.getPortletHelperInstance().setPortletMode(portletMode);
	}

	/**
	 * Sets the value of the a session attribute using the specified name and value within
	 * PortletSession.APPLICATION_SCOPE
	 */
	public static void setSessionSharedAttribute(String name, Object value) {
		PortletHelperFactory.getPortletHelperInstance().setSessionSharedAttribute(name, value);
	}

	/**
	 * Sets the window state of the portlet associated with the current JSF FacesContext to the specified window state.
	 */
	public static void setWindowState(WindowState windowState) {
		PortletHelperFactory.getPortletHelperInstance().setWindowState(windowState);
	}

	/**
	 * A short-cut method for calling setWindowState(WindowState.MAXIMIZED)
	 */
	public static void setWindowStateToMaximized() {
		PortletHelperFactory.getPortletHelperInstance().setWindowStateToMaximized();
	}

	/**
	 * A short-cut method for calling setWindowState(WindowState.NORMAL)
	 */
	public static void setWindowStateToNormal() {
		PortletHelperFactory.getPortletHelperInstance().setWindowStateToNormal();
	}
}
