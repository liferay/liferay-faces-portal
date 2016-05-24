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
import com.liferay.faces.util.helper.Wrapper;

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
 * @author  Neil Griffin
 */
public abstract class LiferayPortletHelperWrapper implements LiferayPortletHelper, Wrapper<LiferayPortletHelper> {

	@Override
	public abstract LiferayPortletHelper getWrapped();

	public void checkUserPortletPermission(String actionId) throws AuthorizationException {
		getWrapped().checkUserPortletPermission(actionId);
	}

	public long getCompanyId() {
		return getWrapped().getCompanyId();
	}

	public String getDocumentLibraryURL() {
		return getWrapped().getDocumentLibraryURL();
	}

	public long getHostGroupId() {
		return getWrapped().getHostGroupId();
	}

	public String getImageGalleryURL() {
		return getWrapped().getImageGalleryURL();
	}

	public Layout getLayout() {
		return getWrapped().getLayout();
	}

	public PermissionChecker getPermissionChecker() {
		return getWrapped().getPermissionChecker();
	}

	public long getPlid() {
		return getWrapped().getPlid();
	}

	public String getPortalURL() {
		return getWrapped().getPortalURL();
	}

	public Portlet getPortlet() {
		return getWrapped().getPortlet();
	}

	public String getPortletInstanceId() {
		return getWrapped().getPortletInstanceId();
	}

	public String getPortletRootId() {
		return getWrapped().getPortletRootId();
	}

	public Group getScopeGroup() {
		return getWrapped().getScopeGroup();
	}

	public long getScopeGroupId() {
		return getWrapped().getScopeGroupId();
	}

	public User getScopeGroupUser() {
		return getWrapped().getScopeGroupUser();
	}

	public ServiceContext getServiceContext() {
		return getWrapped().getServiceContext();
	}

	public Theme getTheme() {
		return getWrapped().getTheme();
	}

	public ThemeDisplay getThemeDisplay() {
		return getWrapped().getThemeDisplay();
	}

	public String getThemeImagesURL() {
		return getWrapped().getThemeImagesURL();
	}

	public User getUser() {
		return getWrapped().getUser();
	}

	public long getUserId() {
		return getWrapped().getUserId();
	}

	public List<Role> getUserRoles() throws SystemException {
		return getWrapped().getUserRoles();
	}

	public boolean userHasPortletPermission(String actionId) {
		return getWrapped().userHasPortletPermission(actionId);
	}

	public boolean userHasRole(String roleName) {
		return getWrapped().userHasRole(roleName);
	}
}
