/**
 * Copyright (c) 2000-2023 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.portal.context.internal;

import java.io.Serializable;
import java.util.List;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.application.Application;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.PortletRequest;

import com.liferay.faces.portal.context.LiferayPortletHelper;
import com.liferay.faces.portal.el.internal.Liferay;
import com.liferay.faces.portal.security.AuthorizationException;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.Theme;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;


/**
 * @author  Neil Griffin
 */
public class LiferayPortletHelperImpl implements LiferayPortletHelper, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 4883383450898636902L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(LiferayPortletHelperImpl.class);

	public void checkUserPortletPermission(String actionId) throws AuthorizationException {

		try {

			if (!userHasPortletPermission(actionId)) {
				throw new AuthorizationException("User " + getUserId() + " not authorized to perform action " +
					actionId);
			}
		}
		catch (Exception e) {
			throw new AuthorizationException("Exception checking permissions for actionId " + actionId, e);

		}
	}

	@Override
	public int getBuildNumber() {
		return ReleaseInfo.getBuildNumber();
	}

	public long getCompanyId() {

		ThemeDisplay themeDisplay = getThemeDisplay();

		return themeDisplay.getCompanyId();
	}

	public String getDocumentLibraryURL() {

		String portalURL = getPortalURL();

		return portalURL + "/c/document_library";
	}

	public long getHostGroupId() {

		Layout layout = getLayout();

		return layout.getGroupId();
	}

	public String getImageGalleryURL() {

		String portalURL = getPortalURL();

		return portalURL + "/image_gallery";
	}

	public Layout getLayout() {

		ThemeDisplay themeDisplay = getThemeDisplay();

		return themeDisplay.getLayout();
	}

	public PermissionChecker getPermissionChecker() {

		ThemeDisplay themeDisplay = getThemeDisplay();

		return themeDisplay.getPermissionChecker();
	}

	public long getPlid() {

		ThemeDisplay themeDisplay = getThemeDisplay();

		return themeDisplay.getPlid();
	}

	public String getPortalURL() {

		ThemeDisplay themeDisplay = getThemeDisplay();

		return themeDisplay.getPortalURL();
	}

	public Portlet getPortlet() {

		// Attempt to get the Portlet object from the "RENDER_PORTLET" request attribute.
		PortletRequest portletRequest = getPortletRequest();
		Portlet portlet = (Portlet) portletRequest.getAttribute(WebKeys.RENDER_PORTLET);

		// FACES-1212: If the request attribute was null, then this method is being called outside of the RENDER_PHASE
		// of the portlet lifecycle. In that case, use the cached version of the Portlet object from the "liferay"
		// ViewScoped managed-bean.
		if (portlet == null) {
			Liferay liferayManagedBean = getLiferayManagedBean();
			portlet = liferayManagedBean.getPortlet();
		}

		return portlet;
	}

	public String getPortletInstanceId() {

		Portlet portlet = getPortlet();

		return portlet.getPortletId();
	}

	public String getPortletRootId() {

		Portlet portlet = getPortlet();

		return portlet.getRootPortletId();
	}

	public Group getScopeGroup() {

		ThemeDisplay themeDisplay = getThemeDisplay();

		return themeDisplay.getScopeGroup();
	}

	public long getScopeGroupId() {

		ThemeDisplay themeDisplay = getThemeDisplay();

		return themeDisplay.getScopeGroupId();
	}

	public User getScopeGroupUser() {
		User groupUser = null;
		Group scopeGroup = getScopeGroup();

		if (scopeGroup.isUser()) {

			try {
				groupUser = UserLocalServiceUtil.getUserById(scopeGroup.getClassPK());
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		return groupUser;
	}

	public ServiceContext getServiceContext() {

		ServiceContext serviceContext = new ServiceContext();
		ThemeDisplay themeDisplay = getThemeDisplay();
		serviceContext.setCompanyId(themeDisplay.getCompanyId());
		serviceContext.setLanguageId(themeDisplay.getLanguageId());
		serviceContext.setPathMain(PortalUtil.getPathMain());
		serviceContext.setPlid(themeDisplay.getPlid());
		serviceContext.setPortalURL(PortalUtil.getPortalURL(getPortletRequest()));
		serviceContext.setScopeGroupId(themeDisplay.getScopeGroupId());
		serviceContext.setUserId(themeDisplay.getUserId());

		try {
			serviceContext.setLayoutFullURL(PortalUtil.getLayoutFullURL(themeDisplay));
			serviceContext.setLayoutURL(PortalUtil.getLayoutURL(themeDisplay));
			serviceContext.setUserDisplayURL(themeDisplay.getUser().getDisplayURL(themeDisplay));
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return serviceContext;
	}

	public Theme getTheme() {

		ThemeDisplay themeDisplay = getThemeDisplay();

		return themeDisplay.getTheme();
	}

	public ThemeDisplay getThemeDisplay() {

		ThemeDisplay themeDisplay = (ThemeDisplay) getPortletRequest().getAttribute(WebKeys.THEME_DISPLAY);

		return themeDisplay;
	}

	public String getThemeImagesURL() {

		String portalURL = null;
		ThemeDisplay themeDisplay = getThemeDisplay();
		String cdnHost = themeDisplay.getCDNHost();

		if ((cdnHost != null) && (cdnHost.length() > 0)) {
			portalURL = cdnHost;
		}
		else {
			portalURL = themeDisplay.getPortalURL();
		}

		String pathThemeImages = themeDisplay.getPathThemeImages();

		if (pathThemeImages.startsWith(portalURL)) {

			// The portalURL will already be included for versions of Liferay Portal newer than 6.1.0 CE GA1
			return pathThemeImages;
		}
		else {
			return portalURL + pathThemeImages;
		}
	}

	public User getUser() {

		ThemeDisplay themeDisplay = getThemeDisplay();

		return themeDisplay.getUser();
	}

	public long getUserId() {

		User user = getUser();

		return user.getUserId();
	}

	public List<Role> getUserRoles() throws SystemException {
		return RoleLocalServiceUtil.getUserRoles(getUserId());
	}

	public boolean userHasPortletPermission(String actionId) {

		ThemeDisplay themeDisplay = getThemeDisplay();
		PermissionChecker permissionChecker = themeDisplay.getPermissionChecker();
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();
		String portletId = portletDisplay.getId();
		boolean hasPermission = false;

		try {
			hasPermission = PortletPermissionUtil.contains(permissionChecker, themeDisplay.getPlid(), portletId,
					actionId);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return hasPermission;
	}

	public boolean userHasRole(String roleName) {

		try {
			List<Role> roles = getUserRoles();

			for (Role role : roles) {

				if (role.getName().equals(roleName)) {
					return true;
				}
			}

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return false;
	}

	protected Liferay getLiferayManagedBean() {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		Application application = facesContext.getApplication();
		ELResolver elResolver = application.getELResolver();
		ELContext elContext = facesContext.getELContext();

		return (Liferay) elResolver.getValue(elContext, null, "liferay");
	}

	protected PortletRequest getPortletRequest() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();

		return portletRequest;
	}
}
