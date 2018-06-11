/**
 * Copyright (c) 2000-2018 Liferay, Inc. All rights reserved.
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

import javax.faces.context.FacesContext;
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
public final class PortletHelperUtil {

	private PortletHelperUtil() {
		throw new AssertionError();
	}

	/**
	 * Retrieves a render URL for the current faces context.
	 */
	public static PortletURL createActionURL() {
		return createActionURL(FacesContext.getCurrentInstance());
	}

	/**
	 * Retrieves a render URL for the specified faces context.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static PortletURL createActionURL(FacesContext facesContext) {
		return PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext()).createActionURL();
	}

	/**
	 * Retrieves a render URL for the current faces context.
	 */
	public static PortletURL createRenderURL() {
		return createRenderURL(FacesContext.getCurrentInstance());
	}

	/**
	 * Retrieves a render URL for the specified faces context.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static PortletURL createRenderURL(FacesContext facesContext) {
		return PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext()).createRenderURL();
	}

	/**
	 * Retrieves the <code>javax.portlet.ActionResponse</code> object associated with the current faces context.
	 */
	public static ActionResponse getActionResponse() {
		return getActionResponse(FacesContext.getCurrentInstance());
	}

	/**
	 * Retrieves the <code>javax.portlet.ActionResponse</code> object associated with the specified faces context.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static ActionResponse getActionResponse(FacesContext facesContext) {
		return PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext()).getActionResponse();
	}

	/**
	 * Retrieves the <code>javax.portlet.PortalContext</code> object associated with the current faces context.
	 */
	public static PortalContext getPortalContext() {
		return getPortalContext(FacesContext.getCurrentInstance());
	}

	/**
	 * Retrieves the <code>javax.portlet.PortalContext</code> object associated with the specified faces context.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static PortalContext getPortalContext(FacesContext facesContext) {
		return PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext()).getPortalContext();
	}

	/**
	 * Retrieves the <code>javax.portlet.PortletConfig</code> object associated with the current faces context.
	 */
	public static PortletConfig getPortletConfig() {
		return getPortletConfig(FacesContext.getCurrentInstance());
	}

	/**
	 * Retrieves the <code>javax.portlet.PortletConfig</code> object associated with the specified faces context.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static PortletConfig getPortletConfig(FacesContext facesContext) {
		return PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext()).getPortletConfig();
	}

	/**
	 * Retrieves the <code>javax.portlet.PortletContext</code> object associated with the current faces context.
	 */
	public static PortletContext getPortletContext() {
		return getPortletContext(FacesContext.getCurrentInstance());
	}

	/**
	 * Retrieves the <code>javax.portlet.PortletContext</code> object associated with the specified faces context.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static PortletContext getPortletContext(FacesContext facesContext) {
		return PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext()).getPortletContext();
	}

	/**
	 * Retrieves the name of the portlet associated with the current faces context (usually, its the name of the portlet
	 * .war)
	 */
	public static String getPortletContextName() {
		return getPortletContextName(FacesContext.getCurrentInstance());
	}

	/**
	 * Retrieves the name of the portlet associated with the specified faces context (usually, its the name of the
	 * portlet .war)
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static String getPortletContextName(FacesContext facesContext) {
		return PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext()).getPortletContextName();
	}

	/**
	 * Retrieves the name of the portlet associated with the current faces context (as defined in portlet.xml's
	 * &lt;portlet-name&gt; tag).
	 */
	public static String getPortletName() {
		return getPortletName(FacesContext.getCurrentInstance());
	}

	/**
	 * Retrieves the name of the portlet associated with the specified faces context (as defined in portlet.xml's
	 * &lt;portlet-name&gt; tag).
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static String getPortletName(FacesContext facesContext) {
		return PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext()).getPortletName();
	}

	/**
	 * Returns String representation of the value of the portlet preference associated with the specified name in from
	 * the <code>javax.portlet.PortletPreferences</code> object associated with the current faces context.
	 */
	public static Object getPortletPreference(String preferenceName, Object defaultValue) {
		return getPortletPreference(FacesContext.getCurrentInstance(), preferenceName, defaultValue);
	}

	/**
	 * Returns String representation of the value of the portlet preference associated with the specified name in from
	 * the <code>javax.portlet.PortletPreferences</code> object associated with the specified faces context.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static Object getPortletPreference(FacesContext facesContext, String preferenceName, Object defaultValue) {
		return PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext()).getPortletPreference(
				preferenceName, defaultValue);
	}

	/**
	 * Returns boolean representation of the value of the portlet preference associated with the specified name in from
	 * the PortletPreferenceMap object associated with the current faces context.
	 */
	public static boolean getPortletPreferenceAsBool(String preferenceName, boolean defaultValue) {
		return getPortletPreferenceAsBool(FacesContext.getCurrentInstance(), preferenceName, defaultValue);
	}

	/**
	 * Returns boolean representation of the value of the portlet preference associated with the specified name in from
	 * the PortletPreferenceMap object associated with the specified faces context.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static boolean getPortletPreferenceAsBool(FacesContext facesContext, String preferenceName,
		boolean defaultValue) {
		return PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext())
			.getPortletPreferenceAsBool(preferenceName, defaultValue);
	}

	/**
	 * Returns integer representation of the value of the portlet preference associated with the specified name in from
	 * the PortletPreferenceMap object associated with the current faces context.
	 */
	public static int getPortletPreferenceAsInt(String preferenceName, int defaultValue) {
		return getPortletPreferenceAsInt(FacesContext.getCurrentInstance(), preferenceName, defaultValue);
	}

	/**
	 * Returns integer representation of the value of the portlet preference associated with the specified name in from
	 * the PortletPreferenceMap object associated with the specified faces context.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static int getPortletPreferenceAsInt(FacesContext facesContext, String preferenceName, int defaultValue) {
		return PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext())
			.getPortletPreferenceAsInt(preferenceName, defaultValue);
	}

	/**
	 * Returns short representation of the value of the portlet preference associated with the specified name in from
	 * the PortletPreferenceMap object associated with the current faces context.
	 */
	public static short getPortletPreferenceAsShort(String preferenceName, short defaultValue) {
		return getPortletPreferenceAsShort(FacesContext.getCurrentInstance(), preferenceName, defaultValue);
	}

	/**
	 * Returns short representation of the value of the portlet preference associated with the specified name in from
	 * the PortletPreferenceMap object associated with the specified faces context.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static short getPortletPreferenceAsShort(FacesContext facesContext, String preferenceName,
		short defaultValue) {
		return PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext())
			.getPortletPreferenceAsShort(preferenceName, defaultValue);
	}

	/**
	 * Returns short representation of the value of the portlet preference associated with the specified name in from
	 * the PortletPreferenceMap object associated with the current faces context.
	 */
	public static String getPortletPreferenceAsString(String preferenceName, String defaultValue) {
		return getPortletPreferenceAsString(FacesContext.getCurrentInstance(), preferenceName, defaultValue);
	}

	/**
	 * Returns short representation of the value of the portlet preference associated with the specified name in from
	 * the PortletPreferenceMap object associated with the specified faces context.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static String getPortletPreferenceAsString(FacesContext facesContext, String preferenceName,
		String defaultValue) {
		return PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext())
			.getPortletPreferenceAsString(preferenceName, defaultValue);
	}

	/**
	 * Retrieves the <code>javax.portlet.PortletPreferences</code> object associated with the current faces context.
	 */
	public static PortletPreferences getPortletPreferences() {
		return getPortletPreferences(FacesContext.getCurrentInstance());
	}

	/**
	 * Retrieves the <code>javax.portlet.PortletPreferences</code> object associated with the specified faces context.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static PortletPreferences getPortletPreferences(FacesContext facesContext) {
		return PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext()).getPortletPreferences();
	}

	/**
	 * Retrieves the <code>javax.portlet.RenderRequest</code> object associated with the current faces context.
	 */
	public static RenderRequest getPortletRenderRequest() {
		return getPortletRenderRequest(FacesContext.getCurrentInstance());
	}

	/**
	 * Retrieves the <code>javax.portlet.RenderRequest</code> object associated with the specified faces context.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static RenderRequest getPortletRenderRequest(FacesContext facesContext) {
		return PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext())
			.getPortletRenderRequest();
	}

	/**
	 * Retrieves the <code>javax.portlet.RenderResponse</code> object associated with the current faces context.
	 */
	public static RenderResponse getPortletRenderResponse() {
		return getPortletRenderResponse(FacesContext.getCurrentInstance());
	}

	/**
	 * Retrieves the <code>javax.portlet.RenderResponse</code> object associated with the specified faces context.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static RenderResponse getPortletRenderResponse(FacesContext facesContext) {
		return PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext())
			.getPortletRenderResponse();
	}

	/**
	 * Retrieves the <code>javax.portlet.PortletRequest</code> object associated with the current faces context.
	 */
	public static PortletRequest getPortletRequest() {
		return getPortletRequest(FacesContext.getCurrentInstance());
	}

	/**
	 * Retrieves the <code>javax.portlet.PortletRequest</code> object associated with the specified faces context.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static PortletRequest getPortletRequest(FacesContext facesContext) {
		return PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext()).getPortletRequest();
	}

	/**
	 * Retrieves the <code>javax.portlet.PortletResponse</code> object associated with the current faces context.
	 */
	public static PortletResponse getPortletResponse() {
		return getPortletResponse(FacesContext.getCurrentInstance());
	}

	/**
	 * Retrieves the <code>javax.portlet.PortletResponse</code> object associated with the specified faces context.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static PortletResponse getPortletResponse(FacesContext facesContext) {
		return PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext()).getPortletResponse();
	}

	/**
	 * Retrieves the <code>javax.portlet.PortletSession</code> object associated with the current faces context.
	 */
	public static PortletSession getPortletSession() {
		return getPortletSession(FacesContext.getCurrentInstance());
	}

	/**
	 * Retrieves the <code>javax.portlet.PortletSession</code> object associated with the specified faces context.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static PortletSession getPortletSession(FacesContext facesContext) {
		return PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext()).getPortletSession();
	}

	/**
	 * Returns the user name/id of the user associated with the current faces context
	 */
	public static String getRemoteUser() {
		return getRemoteUser(FacesContext.getCurrentInstance());
	}

	/**
	 * Returns the user name/id of the user associated with the specified faces context
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static String getRemoteUser(FacesContext facesContext) {
		return PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext()).getRemoteUser();
	}

	/**
	 * Returns the value of the session attribute associated with the specified name from
	 * PortletSession.APPLICATION_SCOPE
	 */
	public static Object getSessionSharedAttribute(String name) {
		return getSessionSharedAttribute(FacesContext.getCurrentInstance(), name);
	}

	/**
	 * Returns the value of the session attribute associated with the specified name from
	 * PortletSession.APPLICATION_SCOPE
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static Object getSessionSharedAttribute(FacesContext facesContext, String name) {
		return PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext())
			.getSessionSharedAttribute(name);
	}

	/**
	 * Returns the window state of the portlet associated with the current faces context
	 */
	public static WindowState getWindowState() {
		return getWindowState(FacesContext.getCurrentInstance());
	}

	/**
	 * Returns the window state of the portlet associated with the specified faces context
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static WindowState getWindowState(FacesContext facesContext) {
		return PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext()).getWindowState();
	}

	/**
	 * Returns TRUE if the underlying request/response is from a portlet environment. Otherwise, must be running in a
	 * servlet environment.
	 */
	public static boolean isPortletEnvironment() {
		return isPortletEnvironment(FacesContext.getCurrentInstance());
	}

	/**
	 * Returns TRUE if the underlying request/response is from a portlet environment. Otherwise, must be running in a
	 * servlet environment.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static boolean isPortletEnvironment(FacesContext facesContext) {
		return PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext()).isPortletEnvironment();
	}

	/**
	 * Returns TRUE if the current user is associated with the specified role.
	 */
	public static boolean isUserInRole(String roleName) {
		return isUserInRole(FacesContext.getCurrentInstance(), roleName);
	}

	/**
	 * Returns TRUE if the current user is associated with the specified role.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static boolean isUserInRole(FacesContext facesContext, String roleName) {
		return PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext()).isUserInRole(roleName);
	}

	/**
	 * Returns TRUE if the Window state of the current portlet is set to MAXIMIZED
	 */
	public static boolean isWindowMaximized() {
		return isWindowMaximized(FacesContext.getCurrentInstance());
	}

	/**
	 * Returns TRUE if the Window state of the current portlet is set to MAXIMIZED
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static boolean isWindowMaximized(FacesContext facesContext) {
		return PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext()).isWindowMaximized();
	}

	/**
	 * Returns TRUE if the Window state of the current portlet is set to MAXIMIZED
	 */
	public static boolean isWindowNormal() {
		return isWindowNormal(FacesContext.getCurrentInstance());
	}

	/**
	 * Returns TRUE if the Window state of the current portlet is set to MAXIMIZED
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static boolean isWindowNormal(FacesContext facesContext) {
		return PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext()).isWindowNormal();
	}

	/**
	 * Sets the portlet mode of the portlet associated with the current faces context to the specified portlet mode.
	 */
	public static void setPortletMode(PortletMode portletMode) {
		setPortletMode(FacesContext.getCurrentInstance(), portletMode);
	}

	/**
	 * Sets the portlet mode of the portlet associated with the specified faces context to the specified portlet mode.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static void setPortletMode(FacesContext facesContext, PortletMode portletMode) {
		PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext()).setPortletMode(portletMode);
	}

	/**
	 * Sets the value of the a session attribute using the specified name and value within
	 * PortletSession.APPLICATION_SCOPE
	 */
	public static void setSessionSharedAttribute(String name, Object value) {
		setSessionSharedAttribute(FacesContext.getCurrentInstance(), name, value);
	}

	/**
	 * Sets the value of the a session attribute using the specified name and value within
	 * PortletSession.APPLICATION_SCOPE
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static void setSessionSharedAttribute(FacesContext facesContext, String name, Object value) {
		PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext()).setSessionSharedAttribute(name,
			value);
	}

	/**
	 * Sets the window state of the portlet associated with the current faces context to the specified window state.
	 */
	public static void setWindowState(WindowState windowState) {
		setWindowState(FacesContext.getCurrentInstance(), windowState);
	}

	/**
	 * Sets the window state of the portlet associated with the specified faces context to the specified window state.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static void setWindowState(FacesContext facesContext, WindowState windowState) {
		PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext()).setWindowState(windowState);
	}

	/**
	 * A short-cut method for calling setWindowState(WindowState.MAXIMIZED)
	 */
	public static void setWindowStateToMaximized() {
		setWindowStateToMaximized(FacesContext.getCurrentInstance());
	}

	/**
	 * A short-cut method for calling setWindowState(WindowState.MAXIMIZED)
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static void setWindowStateToMaximized(FacesContext facesContext) {
		PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext()).setWindowStateToMaximized();
	}

	/**
	 * A short-cut method for calling setWindowState(WindowState.NORMAL)
	 */
	public static void setWindowStateToNormal() {
		setWindowStateToNormal(FacesContext.getCurrentInstance());
	}

	/**
	 * A short-cut method for calling setWindowState(WindowState.NORMAL)
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static void setWindowStateToNormal(FacesContext facesContext) {
		PortletHelperFactory.getPortletHelperInstance(facesContext.getExternalContext()).setWindowStateToNormal();
	}
}
