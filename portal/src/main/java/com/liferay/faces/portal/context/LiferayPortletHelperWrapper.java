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

import javax.faces.FacesWrapper;

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
 * @author  Neil Griffin
 */
public abstract class LiferayPortletHelperWrapper implements LiferayPortletHelper, FacesWrapper<LiferayPortletHelper> {

	@Override
	public abstract LiferayPortletHelper getWrapped();

	@Override
	public void checkUserPortletPermission(String actionId) throws AuthorizationException {
		getWrapped().checkUserPortletPermission(actionId);
	}

	@Override
	public long getCompanyId() {
		return getWrapped().getCompanyId();
	}

	@Override
	public String getDocumentLibraryURL() {
		return getWrapped().getDocumentLibraryURL();
	}

	@Override
	public long getHostGroupId() {
		return getWrapped().getHostGroupId();
	}

	@Override
	public String getImageGalleryURL() {
		return getWrapped().getImageGalleryURL();
	}

	@Override
	public Layout getLayout() {
		return getWrapped().getLayout();
	}

	@Override
	public PermissionChecker getPermissionChecker() {
		return getWrapped().getPermissionChecker();
	}

	@Override
	public long getPlid() {
		return getWrapped().getPlid();
	}

	@Override
	public String getPortalURL() {
		return getWrapped().getPortalURL();
	}

	@Override
	public Portlet getPortlet() {
		return getWrapped().getPortlet();
	}

	@Override
	public String getPortletInstanceId() {
		return getWrapped().getPortletInstanceId();
	}

	@Override
	public String getPortletRootId() {
		return getWrapped().getPortletRootId();
	}

	@Override
	public Group getScopeGroup() {
		return getWrapped().getScopeGroup();
	}

	@Override
	public long getScopeGroupId() {
		return getWrapped().getScopeGroupId();
	}

	@Override
	public User getScopeGroupUser() {
		return getWrapped().getScopeGroupUser();
	}

	@Override
	public ServiceContext getServiceContext() {
		return getWrapped().getServiceContext();
	}

	@Override
	public Theme getTheme() {
		return getWrapped().getTheme();
	}

	@Override
	public ThemeDisplay getThemeDisplay() {
		return getWrapped().getThemeDisplay();
	}

	@Override
	public String getThemeImagesURL() {
		return getWrapped().getThemeImagesURL();
	}

	@Override
	public User getUser() {
		return getWrapped().getUser();
	}

	@Override
	public long getUserId() {
		return getWrapped().getUserId();
	}

	@Override
	public List<Role> getUserRoles() throws SystemException {
		return getWrapped().getUserRoles();
	}

	@Override
	public boolean userHasPortletPermission(String actionId) {
		return getWrapped().userHasPortletPermission(actionId);
	}

	@Override
	public boolean userHasRole(String roleName) {
		return getWrapped().userHasRole(roleName);
	}
}
