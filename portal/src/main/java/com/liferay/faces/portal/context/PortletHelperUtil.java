/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
		return PortletHelperFactory.getInstance().createActionURL();
	}

	/**
	 * Retrieves a render URL for the current facesContext.
	 */
	public static PortletURL createRenderURL() {
		return PortletHelperFactory.getInstance().createRenderURL();
	}

	/**
	 * Retrieves the <code>javax.portlet.ActionResponse</code> object associated with the current JSF FacesContext.
	 */
	public static ActionResponse getActionResponse() {
		return PortletHelperFactory.getInstance().getActionResponse();
	}

	/**
	 * Returns TRUE if the Window state of the current portlet is set to MAXIMIZED
	 */
	public static boolean isWindowMaximized() {
		return PortletHelperFactory.getInstance().isWindowMaximized();
	}

	/**
	 * Returns TRUE if the current user is associated with the specified role.
	 */
	public static boolean isUserInRole(String roleName) {
		return PortletHelperFactory.getInstance().isUserInRole(roleName);
	}

	/**
	 * Returns TRUE if the Window state of the current portlet is set to MAXIMIZED
	 */
	public static boolean isWindowNormal() {
		return PortletHelperFactory.getInstance().isWindowNormal();
	}

	/**
	 * Retrieves the <code>javax.portlet.PortalContext</code> object associated with the current JSF FacesContext.
	 */
	public static PortalContext getPortalContext() {
		return PortletHelperFactory.getInstance().getPortalContext();
	}

	/**
	 * Retrieves the <code>javax.portlet.PortletConfig</code> object associated with the current JSF FacesContext.
	 */
	public static PortletConfig getPortletConfig() {
		return PortletHelperFactory.getInstance().getPortletConfig();
	}

	/**
	 * Retrieves the <code>javax.portlet.PortletContext</code> object associated with the current JSF FacesContext.
	 */
	public static PortletContext getPortletContext() {
		return PortletHelperFactory.getInstance().getPortletContext();
	}

	/**
	 * Retrieves the name of the portlet associated with the current JSF FacesContext (usually, its the name of the
	 * portlet .war)
	 */
	public static String getPortletContextName() {
		return PortletHelperFactory.getInstance().getPortletContextName();
	}

	/**
	 * Sets the portlet mode of the portlet associated with the current JSF FacesContext to the specified portlet mode.
	 */
	public static void setPortletMode(PortletMode portletMode) {
		PortletHelperFactory.getInstance().setPortletMode(portletMode);
	}

	/**
	 * Retrieves the name of the portlet associated with the current JSF FacesContext (as defined in portlet.xml's
	 * &lt;portlet-name&gt; tag).
	 */
	public static String getPortletName() {
		return PortletHelperFactory.getInstance().getPortletName();
	}

	/**
	 * Returns String representation of the value of the portlet preference associated with the specified name in from
	 * the <code>javax.portlet.PortletPreferences</code> object associated with the current JSF FacesContext.
	 */
	public static Object getPortletPreference(String preferenceName, Object defaultValue) {
		return PortletHelperFactory.getInstance().getPortletPreference(preferenceName, defaultValue);
	}

	/**
	 * Returns boolean representation of the value of the portlet preference associated with the specified name in from
	 * the PortletPreferenceMap object associated with the current JSF FacesContext.
	 */
	public static boolean getPortletPreferenceAsBool(String preferenceName, boolean defaultValue) {
		return PortletHelperFactory.getInstance().getPortletPreferenceAsBool(preferenceName, defaultValue);
	}

	/**
	 * Returns integer representation of the value of the portlet preference associated with the specified name in from
	 * the PortletPreferenceMap object associated with the current JSF FacesContext.
	 */
	public static int getPortletPreferenceAsInt(String preferenceName, int defaultValue) {
		return PortletHelperFactory.getInstance().getPortletPreferenceAsInt(preferenceName, defaultValue);
	}

	/**
	 * Returns short representation of the value of the portlet preference associated with the specified name in from
	 * the PortletPreferenceMap object associated with the current JSF FacesContext.
	 */
	public static short getPortletPreferenceAsShort(String preferenceName, short defaultValue) {
		return PortletHelperFactory.getInstance().getPortletPreferenceAsShort(preferenceName, defaultValue);
	}

	/**
	 * Returns short representation of the value of the portlet preference associated with the specified name in from
	 * the PortletPreferenceMap object associated with the current JSF FacesContext.
	 */
	public static String getPortletPreferenceAsString(String preferenceName, String defaultValue) {
		return PortletHelperFactory.getInstance().getPortletPreferenceAsString(preferenceName, defaultValue);
	}

	/**
	 * Retrieves the <code>javax.portlet.PortletPreferences</code> object associated with the current JSF FacesContext.
	 */
	public static PortletPreferences getPortletPreferences() {
		return PortletHelperFactory.getInstance().getPortletPreferences();
	}

	/**
	 * Retrieves the <code>javax.portlet.RenderRequest</code> object associated with the current JSF FacesContext.
	 */
	public static RenderRequest getPortletRenderRequest() {
		return PortletHelperFactory.getInstance().getPortletRenderRequest();
	}

	/**
	 * Retrieves the <code>javax.portlet.RenderResponse</code> object associated with the current JSF FacesContext.
	 */
	public static RenderResponse getPortletRenderResponse() {
		return PortletHelperFactory.getInstance().getPortletRenderResponse();
	}

	/**
	 * Retrieves the <code>javax.portlet.PortletRequest</code> object associated with the current JSF FacesContext.
	 */
	public static PortletRequest getPortletRequest() {
		return PortletHelperFactory.getInstance().getPortletRequest();
	}

	/**
	 * Retrieves the <code>javax.portlet.PortletResponse</code> object associated with the current JSF FacesContext.
	 */
	public static PortletResponse getPortletResponse() {
		return PortletHelperFactory.getInstance().getPortletResponse();
	}

	/**
	 * Retrieves the <code>javax.portlet.PortletSession</code> object associated with the current JSF FacesContext.
	 */
	public static PortletSession getPortletSession() {
		return PortletHelperFactory.getInstance().getPortletSession();
	}

	/**
	 * Returns the user name/id of the user associated with the current JSF FacesContext
	 */
	public static String getRemoteUser() {
		return PortletHelperFactory.getInstance().getRemoteUser();
	}

	/**
	 * Returns the value of the session attribute associated with the specified name from
	 * PortletSession.APPLICATION_SCOPE
	 */
	public static Object getSessionSharedAttribute(String name) {
		return PortletHelperFactory.getInstance().getSessionSharedAttribute(name);
	}

	/**
	 * Sets the value of the a session attribute using the specified name and value within
	 * PortletSession.APPLICATION_SCOPE
	 */
	public static void setSessionSharedAttribute(String name, Object value) {
		PortletHelperFactory.getInstance().setSessionSharedAttribute(name, value);
	}

	/**
	 * Returns TRUE if the underlying request/response is from a portlet environment. Otherwise, must be running in a
	 * servlet environment.
	 */
	public static boolean isPortletEnvironment() {
		return PortletHelperFactory.getInstance().isPortletEnvironment();
	}

	/**
	 * Returns the window state of the portlet associated with the current JSF FacesContext
	 */
	public static WindowState getWindowState() {
		return PortletHelperFactory.getInstance().getWindowState();
	}

	/**
	 * Sets the window state of the portlet associated with the current JSF FacesContext to the specified window state.
	 */
	public static void setWindowState(WindowState windowState) {
		PortletHelperFactory.getInstance().setWindowState(windowState);
	}

	/**
	 * A short-cut method for calling setWindowState(WindowState.MAXIMIZED)
	 */
	public static void setWindowStateToMaximized() {
		PortletHelperFactory.getInstance().setWindowStateToMaximized();
	}

	/**
	 * A short-cut method for calling setWindowState(WindowState.NORMAL)
	 */
	public static void setWindowStateToNormal() {
		PortletHelperFactory.getInstance().setWindowStateToNormal();
	}
}
