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

import java.util.List;

import com.liferay.faces.portal.security.AuthorizationException;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.Theme;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;


/**
 * This class contains a set of static convenience methods that delegate to the corresponding methods on {@link
 * LiferayPortletHelper}.
 *
 * @author  Neil Griffin
 */
public class LiferayPortletHelperUtil {

	/**
	 * Checks to see if the current user has permission to execute the specified actionId (which, in turn, is assumed to
	 * be an action defined for the current portlet). If the authorization fails, a PrincipalException is thrown. Any
	 * other errors that occur will be caught and re-thrown, wrapped in a PortletRuntimeException.
	 *
	 * @throws  AuthorizationException
	 *
	 * @see     {@link LiferayPortletHelper#userHasPortletPermission(String)}
	 */
	public static void checkUserPortletPermission(String actionId) throws AuthorizationException {
		LiferayPortletHelperFactory.getLiferayPortletHelperInstance().checkUserPortletPermission(actionId);
	}

	/**
	 * Returns the build identifier for the running version of Liferay Portal.
	 */
	public static int getBuildNumber() {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance().getBuildNumber();
	}

	/**
	 * Returns the company Id associated with the community that is hosting the portlet associated with the current JSF
	 * FacesContext.
	 */
	public static long getCompanyId() {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance().getCompanyId();
	}

	/**
	 * Returns an absolute URL to the Liferay document library, which is the main part of different struts action paths.
	 */
	public static String getDocumentLibraryURL() {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance().getDocumentLibraryURL();
	}

	/**
	 * Returns the "group Id" associated with the community that is hosting the portlet associated with the current JSF
	 * FacesContext.
	 */
	public static long getHostGroupId() {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance().getHostGroupId();
	}

	/**
	 * Returns an absolute URL to the Liferay image gallery, which is the main part of different struts action paths.
	 */
	public static String getImageGalleryURL() {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance().getImageGalleryURL();
	}

	/**
	 * Returns the layout being displayed in the current context.
	 */
	public static Layout getLayout() {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance().getLayout();
	}

	/**
	 * Returns the Liferay PermissionChecker associated with the request.
	 */
	public static PermissionChecker getPermissionChecker() {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance().getPermissionChecker();
	}

	/**
	 * Returns the "Plid" (the "portal layout id"), which is the identifier of the specific "page" the portlet exists
	 * on. Plid is the primary key of the "Layout" table.
	 */
	public static long getPlid() {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance().getPlid();
	}

	/**
	 * Returns an absolute URL which is the context-path of the portal webapp.
	 */
	public static String getPortalURL() {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance().getPortalURL();
	}

	/**
	 * Returns the <code>com.liferay.portal.model.Portlet</code> object for the portlet associated with the current
	 * request.
	 */
	public static Portlet getPortlet() {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance().getPortlet();
	}

	/**
	 * Returns the "portlet instance Id" of the currently running portlet. This id is an internal identifier used by the
	 * Liferay API. Note that the Liferay API for getPorletId() is not exposed as getPortletId() in this API -- instead
	 * it is exposed as getPortletInstanceId(). This is because the Liferay API getPortletId() actually returns the
	 * "instance id" of the portlet, whereas most of the time developers really want the "root id" instead.
	 *
	 * @see  {@link PortletHelper#getPortletName()}
	 */
	public static String getPortletInstanceId() {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance().getPortletInstanceId();
	}

	/**
	 * Returns the "portlet root Id" of the currently running portlet. This id is an internal identifier used by the
	 * Liferay API. Note that the Liferay API for getPorletId() is not exposed as getPortletId() in this API -- instead
	 * it is exposed as getPortletInstanceId(). This is because the Liferay API getPortletId() actually returns the
	 * "instance id" of the portlet, whereas most of the time developers really want the "root id" instead.
	 *
	 * @see  {@link PortletHelper#getPortletName()}
	 */
	public static String getPortletRootId() {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance().getPortletRootId();
	}

	/**
	 * Returns the group (Liferay community) associated with the layout (portal page) that the portlet resides on.
	 */
	public static Group getScopeGroup() {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance().getScopeGroup();
	}

	/**
	 * Returns the unique id of the group (Liferay community) associated with the layout (portal page) that the portlet
	 * resides on.
	 */
	public static long getScopeGroupId() {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance().getScopeGroupId();
	}

	/**
	 * Returns the user associated with the group (Liferay community) associated with the layout (portal page) that the
	 * portlet resides on.
	 */
	public static User getScopeGroupUser() {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance().getScopeGroupUser();
	}

	/**
	 * Returns an instance of a ServiceContext associated with the specified className.
	 */
	public static ServiceContext getServiceContext() {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance().getServiceContext();
	}

	/**
	 * Returns the theme being displayed in the current context.
	 */
	public static Theme getTheme() {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance().getTheme();
	}

	/**
	 * Returns the <code>com.liferay.portal.theme.ThemeDisplay</code> object, which contains a variety methods for
	 * rendering theme specific look and feels.
	 */
	public static ThemeDisplay getThemeDisplay() {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance().getThemeDisplay();
	}

	/**
	 * Returns an absolute URL which is the path to the images of the theme associated with the current Layout.
	 */
	public static String getThemeImagesURL() {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance().getThemeImagesURL();
	}

	/**
	 * Returns the user record of the user associated with the current JSF FacesContext.
	 */
	public static User getUser() {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance().getUser();
	}

	/**
	 * Returns the user Id of the user associated with the current JSF FacesContext
	 */
	public static long getUserId() {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance().getUserId();
	}

	/**
	 * Returns a list of all of the roles played by the user associated with the current JSF FacesContext.
	 */
	public static List<Role> getUserRoles() throws SystemException {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance().getUserRoles();
	}

	/**
	 * Returns <code>true</code> if the current user has permission to execute the specified actionId (which, in turn,
	 * is assumed to be an action defined for the current portlet). Any errors that occur will be caught and re-thrown,
	 * wrapped in a PortletRuntimeException.
	 *
	 * @see  {@link LiferayPortletHelper#checkUserPortletPermission(String)}
	 */
	public static boolean userHasPortletPermission(String actionId) {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance().userHasPortletPermission(actionId);
	}

	/**
	 * Returns <code>true</code> if the current user has the specified role name.
	 */
	public static boolean userHasRole(String roleName) {
		return LiferayPortletHelperFactory.getLiferayPortletHelperInstance().userHasRole(roleName);
	}
}
