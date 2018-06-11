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

import java.util.List;

import javax.faces.context.FacesContext;

import com.liferay.faces.portal.security.AuthorizationException;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;


/**
 * This class contains a set of static convenience methods that delegate to the corresponding methods on {@link
 * LiferayPortletHelper}.
 *
 * @author  Neil Griffin
 */
public final class LiferayPortletHelperUtil {

	private LiferayPortletHelperUtil() {
		throw new AssertionError();
	}

	/**
	 * Checks to see if the current user has permission to execute the specified actionId (which, in turn, is assumed to
	 * be an action defined for the current portlet). If the authorization fails, a PrincipalException is thrown. Any
	 * other errors that occur will be caught and re-thrown, wrapped in a PortletRuntimeException.
	 *
	 * @throws  AuthorizationException  If the user does not have the portlet permission associated with the specified
	 *                                  actionId.
	 *
	 * @see     LiferayPortletHelper#userHasPortletPermission(String)
	 */
	public static void checkUserPortletPermission(String actionId) throws AuthorizationException {
		checkUserPortletPermission(FacesContext.getCurrentInstance(), actionId);
	}

	/**
	 * Checks to see if the current user has permission to execute the specified actionId (which, in turn, is assumed to
	 * be an action defined for the current portlet). If the authorization fails, a PrincipalException is thrown. Any
	 * other errors that occur will be caught and re-thrown, wrapped in a PortletRuntimeException.
	 *
	 * @throws  AuthorizationException  If the user does not have the portlet permission associated with the specified
	 *                                  actionId.
	 *
	 * @see     LiferayPortletHelper#userHasPortletPermission(String)
	 * @since   3.1
	 * @since   2.1
	 * @since   1.1
	 */
	public static void checkUserPortletPermission(FacesContext facesContext, String actionId)
		throws AuthorizationException {
		LiferayPortletHelperFactory.getLiferayPortletHelperInstance(facesContext.getExternalContext())
			.checkUserPortletPermission(actionId);
	}

	/**
	 * Returns the build identifier for the running version of Liferay Portal.
	 */
	public static int getBuildNumber() {
		return getBuildNumber(FacesContext.getCurrentInstance());
	}

	/**
	 * Returns the build identifier for the running version of Liferay Portal.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static int getBuildNumber(FacesContext facesContext) {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance(facesContext.getExternalContext())
			.getBuildNumber();
	}

	/**
	 * Returns the company Id associated with the community that is hosting the portlet associated with the current
	 * faces context.
	 */
	public static long getCompanyId() {
		return getCompanyId(FacesContext.getCurrentInstance());
	}

	/**
	 * Returns the company Id associated with the community that is hosting the portlet associated with the current
	 * faces context.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static long getCompanyId(FacesContext facesContext) {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance(facesContext.getExternalContext())
			.getCompanyId();
	}

	/**
	 * Returns an absolute URL to the Liferay document library, which is the main part of different struts action paths.
	 */
	public static String getDocumentLibraryURL() {
		return getDocumentLibraryURL(FacesContext.getCurrentInstance());
	}

	/**
	 * Returns an absolute URL to the Liferay document library, which is the main part of different struts action paths.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static String getDocumentLibraryURL(FacesContext facesContext) {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance(facesContext.getExternalContext())
			.getDocumentLibraryURL();
	}

	/**
	 * Returns the "group Id" associated with the community that is hosting the portlet associated with the current
	 * faces context.
	 */
	public static long getHostGroupId() {
		return getHostGroupId(FacesContext.getCurrentInstance());
	}

	/**
	 * Returns the "group Id" associated with the community that is hosting the portlet associated with the current
	 * faces context.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static long getHostGroupId(FacesContext facesContext) {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance(facesContext.getExternalContext())
			.getHostGroupId();
	}

	/**
	 * Returns an absolute URL to the Liferay image gallery, which is the main part of different struts action paths.
	 */
	public static String getImageGalleryURL() {
		return getImageGalleryURL(FacesContext.getCurrentInstance());
	}

	/**
	 * Returns an absolute URL to the Liferay image gallery, which is the main part of different struts action paths.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static String getImageGalleryURL(FacesContext facesContext) {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance(facesContext.getExternalContext())
			.getImageGalleryURL();
	}

	/**
	 * Returns the layout being displayed in the current context.
	 */
	public static Layout getLayout() {
		return getLayout(FacesContext.getCurrentInstance());
	}

	/**
	 * Returns the layout being displayed in the current context.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static Layout getLayout(FacesContext facesContext) {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance(facesContext.getExternalContext())
			.getLayout();
	}

	/**
	 * Returns the Liferay PermissionChecker associated with the request.
	 */
	public static PermissionChecker getPermissionChecker() {
		return getPermissionChecker(FacesContext.getCurrentInstance());
	}

	/**
	 * Returns the Liferay PermissionChecker associated with the request.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static PermissionChecker getPermissionChecker(FacesContext facesContext) {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance(facesContext.getExternalContext())
			.getPermissionChecker();
	}

	/**
	 * Returns the "Plid" (the "portal layout id"), which is the identifier of the specific "page" the portlet exists
	 * on. Plid is the primary key of the "Layout" table.
	 */
	public static long getPlid() {
		return getPlid(FacesContext.getCurrentInstance());
	}

	/**
	 * Returns the "Plid" (the "portal layout id"), which is the identifier of the specific "page" the portlet exists
	 * on. Plid is the primary key of the "Layout" table.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static long getPlid(FacesContext facesContext) {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance(facesContext.getExternalContext()).getPlid();
	}

	/**
	 * Returns an absolute URL which is the context-path of the portal webapp.
	 */
	public static String getPortalURL() {
		return getPortalURL(FacesContext.getCurrentInstance());
	}

	/**
	 * Returns an absolute URL which is the context-path of the portal webapp.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static String getPortalURL(FacesContext facesContext) {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance(facesContext.getExternalContext())
			.getPortalURL();
	}

	/**
	 * Returns the <code>com.liferay.portal.model.Portlet</code> object for the portlet associated with the current
	 * request.
	 */
	public static Portlet getPortlet() {
		return getPortlet(FacesContext.getCurrentInstance());
	}

	/**
	 * Returns the <code>com.liferay.portal.model.Portlet</code> object for the portlet associated with the current
	 * request.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static Portlet getPortlet(FacesContext facesContext) {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance(facesContext.getExternalContext())
			.getPortlet();
	}

	/**
	 * Returns the "portlet instance Id" of the currently running portlet. This id is an internal identifier used by the
	 * Liferay API. Note that the Liferay API for getPorletId() is not exposed as getPortletId() in this API -- instead
	 * it is exposed as getPortletInstanceId(). This is because the Liferay API getPortletId() actually returns the
	 * "instance id" of the portlet, whereas most of the time developers really want the "root id" instead.
	 *
	 * @see  PortletHelper#getPortletName()
	 */
	public static String getPortletInstanceId() {
		return getPortletInstanceId(FacesContext.getCurrentInstance());
	}

	/**
	 * Returns the "portlet instance Id" of the currently running portlet. This id is an internal identifier used by the
	 * Liferay API. Note that the Liferay API for getPorletId() is not exposed as getPortletId() in this API -- instead
	 * it is exposed as getPortletInstanceId(). This is because the Liferay API getPortletId() actually returns the
	 * "instance id" of the portlet, whereas most of the time developers really want the "root id" instead.
	 *
	 * @see    PortletHelper#getPortletName()
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static String getPortletInstanceId(FacesContext facesContext) {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance(facesContext.getExternalContext())
			.getPortletInstanceId();
	}

	/**
	 * Returns the "portlet root Id" of the currently running portlet. This id is an internal identifier used by the
	 * Liferay API. Note that the Liferay API for getPorletId() is not exposed as getPortletId() in this API -- instead
	 * it is exposed as getPortletInstanceId(). This is because the Liferay API getPortletId() actually returns the
	 * "instance id" of the portlet, whereas most of the time developers really want the "root id" instead.
	 *
	 * @see  PortletHelper#getPortletName()
	 */
	public static String getPortletRootId() {
		return getPortletRootId(FacesContext.getCurrentInstance());
	}

	/**
	 * Returns the "portlet root Id" of the currently running portlet. This id is an internal identifier used by the
	 * Liferay API. Note that the Liferay API for getPorletId() is not exposed as getPortletId() in this API -- instead
	 * it is exposed as getPortletInstanceId(). This is because the Liferay API getPortletId() actually returns the
	 * "instance id" of the portlet, whereas most of the time developers really want the "root id" instead.
	 *
	 * @see    PortletHelper#getPortletName()
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static String getPortletRootId(FacesContext facesContext) {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance(facesContext.getExternalContext())
			.getPortletRootId();
	}

	/**
	 * Returns the group (Liferay community) associated with the layout (portal page) that the portlet resides on.
	 */
	public static Group getScopeGroup() {
		return getScopeGroup(FacesContext.getCurrentInstance());
	}

	/**
	 * Returns the group (Liferay community) associated with the layout (portal page) that the portlet resides on.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static Group getScopeGroup(FacesContext facesContext) {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance(facesContext.getExternalContext())
			.getScopeGroup();
	}

	/**
	 * Returns the unique id of the group (Liferay community) associated with the layout (portal page) that the portlet
	 * resides on.
	 */
	public static long getScopeGroupId() {
		return getScopeGroupId(FacesContext.getCurrentInstance());
	}

	/**
	 * Returns the unique id of the group (Liferay community) associated with the layout (portal page) that the portlet
	 * resides on.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static long getScopeGroupId(FacesContext facesContext) {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance(facesContext.getExternalContext())
			.getScopeGroupId();
	}

	/**
	 * Returns the user associated with the group (Liferay community) associated with the layout (portal page) that the
	 * portlet resides on.
	 */
	public static User getScopeGroupUser() {
		return getScopeGroupUser(FacesContext.getCurrentInstance());
	}

	/**
	 * Returns the user associated with the group (Liferay community) associated with the layout (portal page) that the
	 * portlet resides on.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static User getScopeGroupUser(FacesContext facesContext) {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance(facesContext.getExternalContext())
			.getScopeGroupUser();
	}

	/**
	 * Returns an instance of a ServiceContext associated with the specified className.
	 */
	public static ServiceContext getServiceContext() {
		return getServiceContext(FacesContext.getCurrentInstance());
	}

	/**
	 * Returns an instance of a ServiceContext associated with the specified className.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static ServiceContext getServiceContext(FacesContext facesContext) {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance(facesContext.getExternalContext())
			.getServiceContext();
	}

	/**
	 * Returns the theme being displayed in the current context.
	 */
	public static Theme getTheme() {
		return getTheme(FacesContext.getCurrentInstance());
	}

	/**
	 * Returns the theme being displayed in the current context.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static Theme getTheme(FacesContext facesContext) {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance(facesContext.getExternalContext())
			.getTheme();
	}

	/**
	 * Returns the <code>com.liferay.portal.kernel.theme.ThemeDisplay</code> object, which contains a variety methods
	 * for rendering theme specific look and feels.
	 */
	public static ThemeDisplay getThemeDisplay() {
		return getThemeDisplay(FacesContext.getCurrentInstance());
	}

	/**
	 * Returns the <code>com.liferay.portal.kernel.theme.ThemeDisplay</code> object, which contains a variety methods
	 * for rendering theme specific look and feels.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static ThemeDisplay getThemeDisplay(FacesContext facesContext) {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance(facesContext.getExternalContext())
			.getThemeDisplay();
	}

	/**
	 * Returns an absolute URL which is the path to the images of the theme associated with the current Layout.
	 */
	public static String getThemeImagesURL() {
		return getThemeImagesURL(FacesContext.getCurrentInstance());
	}

	/**
	 * Returns an absolute URL which is the path to the images of the theme associated with the current Layout.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static String getThemeImagesURL(FacesContext facesContext) {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance(facesContext.getExternalContext())
			.getThemeImagesURL();
	}

	/**
	 * Returns the user record of the user associated with the current faces context.
	 */
	public static User getUser() {
		return getUser(FacesContext.getCurrentInstance());
	}

	/**
	 * Returns the user record of the user associated with the specified faces context.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static User getUser(FacesContext facesContext) {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance(facesContext.getExternalContext()).getUser();
	}

	/**
	 * Returns the user Id of the user associated with the current faces context.
	 */
	public static long getUserId() {
		return getUserId(FacesContext.getCurrentInstance());
	}

	/**
	 * Returns the user Id of the user associated with the specified faces context.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static long getUserId(FacesContext facesContext) {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance(facesContext.getExternalContext())
			.getUserId();
	}

	/**
	 * Returns a list of all of the roles played by the user associated with the current faces context.
	 */
	public static List<Role> getUserRoles() throws SystemException {
		return getUserRoles(FacesContext.getCurrentInstance());
	}

	/**
	 * Returns a list of all of the roles played by the user associated with the specified faces context.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static List<Role> getUserRoles(FacesContext facesContext) throws SystemException {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance(facesContext.getExternalContext())
			.getUserRoles();
	}

	/**
	 * Returns <code>true</code> if the current user has permission to execute the specified actionId (which, in turn,
	 * is assumed to be an action defined for the current portlet). Any errors that occur will be caught and re-thrown,
	 * wrapped in a PortletRuntimeException.
	 *
	 * @see  LiferayPortletHelper#checkUserPortletPermission(String)
	 */
	public static boolean userHasPortletPermission(String actionId) {
		return userHasPortletPermission(FacesContext.getCurrentInstance(), actionId);
	}

	/**
	 * Returns <code>true</code> if the current user has permission to execute the specified actionId (which, in turn,
	 * is assumed to be an action defined for the current portlet). Any errors that occur will be caught and re-thrown,
	 * wrapped in a PortletRuntimeException.
	 *
	 * @see    LiferayPortletHelper#checkUserPortletPermission(String)
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static boolean userHasPortletPermission(FacesContext facesContext, String actionId) {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance(facesContext.getExternalContext())
			.userHasPortletPermission(actionId);
	}

	/**
	 * Returns <code>true</code> if the current user has the specified role name.
	 */
	public static boolean userHasRole(String roleName) {
		return userHasRole(FacesContext.getCurrentInstance(), roleName);
	}

	/**
	 * Returns <code>true</code> if the current user has the specified role name.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static boolean userHasRole(FacesContext facesContext, String roleName) {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance(facesContext.getExternalContext())
			.userHasRole(roleName);
	}
}
