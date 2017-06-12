/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.demos.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.portlet.PortletRequest;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import com.liferay.faces.demos.list.UserLazyDataModel;
import com.liferay.faces.demos.resource.UserPortraitResource;
import com.liferay.faces.demos.service.UserLocalServiceTracker;
import com.liferay.faces.portal.context.LiferayPortletHelperUtil;
import com.liferay.faces.portal.context.PortletHelperUtil;
import com.liferay.faces.util.context.FacesContextHelperUtil;
import com.liferay.faces.util.model.UploadedFile;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;


/**
 * This class serves as a model bean for the users.xhtml Facelet view. The bean is kept in view scope since the model
 * data needs to be maintained as long as the end-user is interacting with the view.
 *
 * @author  Neil Griffin
 * @author  Kyle Stiemann
 */
@ManagedBean(name = "usersModelBean")
@ViewScoped
public class UsersModelBean implements Serializable {

	// serialVersionUID Note: This class implements Serializable only to avoid extraneous stacktraces from being thrown
	// by Mojarra. All of the private data members are marked as transient, because it's not possible to de-serialize
	// instances of Liferay's User class due to classloader prolems.
	private static final long serialVersionUID = 5267378433060095710L;

	// Private Data Members
	private transient UserLazyDataModel userDataModel;
	private transient User selectedUser;
	private transient List<SelectItem> statusSelectItems;
	private transient UploadedFile uploadedFile;
	private transient String selectedUserPortraitURL;
	protected transient UserLocalServiceTracker userLocalServiceTracker;

	public void forceListReload() {

		selectedUser = null;
		selectedUserPortraitURL = null;
		userDataModel = null;
		uploadedFile = null;
	}

	public UserLazyDataModel getDataModel() {

		if (userDataModel == null) {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			int rowsPerPage = PortletHelperUtil.getPortletPreferenceAsInt(facesContext, "rowsPerPage",
					SearchContainer.DEFAULT_DELTA);

			if (userLocalServiceTracker.isEmpty()) {
				userDataModel = new UserLazyDataModel(null, LiferayPortletHelperUtil.getCompanyId(facesContext),
						rowsPerPage);
				FacesContextHelperUtil.addGlobalErrorMessage("is-temporarily-unavailable", "User service");
			}
			else {
				UserLocalService userLocalService = userLocalServiceTracker.getService();
				userDataModel = new UserLazyDataModel(userLocalService,
						LiferayPortletHelperUtil.getCompanyId(facesContext), rowsPerPage);
			}
		}

		return userDataModel;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	/**
	 * This method returns a fully encoded URL that can be used in an HTML img tag to display the selected user's
	 * portrait. In order to determine the value of the URL, this method delegates much of that responsibility to the
	 * {@link UserPortraitResource} class.
	 */
	public String getSelectedUserPortraitURL() {

		if (selectedUserPortraitURL == null) {

			String uploadedFileId = null;

			if (uploadedFile != null) {
				uploadedFileId = uploadedFile.getId();
			}

			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
			ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
			String portalURL = themeDisplay.getPortalURL();
			String imagePath = portalURL + "/image";
			UserPortraitResource userPortraitResource = new UserPortraitResource(imagePath, selectedUser,
					uploadedFileId);
			String requestPath = userPortraitResource.getRequestPath();
			Application application = facesContext.getApplication();
			ViewHandler viewHandler = application.getViewHandler();
			String resourceURL = viewHandler.getResourceURL(facesContext, requestPath);
			selectedUserPortraitURL = externalContext.encodeResourceURL(resourceURL);
		}

		return selectedUserPortraitURL;
	}

	public List<SelectItem> getStatusSelectItems() {

		if (statusSelectItems == null) {
			statusSelectItems = new ArrayList<SelectItem>();
			statusSelectItems.add(new SelectItem(WorkflowConstants.STATUS_ANY,
					FacesContextHelperUtil.getMessage("any-status")));
			statusSelectItems.add(new SelectItem(WorkflowConstants.STATUS_APPROVED,
					FacesContextHelperUtil.getMessage("active")));
			statusSelectItems.add(new SelectItem(WorkflowConstants.STATUS_INACTIVE,
					FacesContextHelperUtil.getMessage("inactive")));
		}

		return statusSelectItems;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	@PostConstruct
	public void postConstruct() {
		Bundle bundle = FrameworkUtil.getBundle(this.getClass());
		BundleContext bundleContext = bundle.getBundleContext();
		userLocalServiceTracker = new UserLocalServiceTracker(bundleContext);
		userLocalServiceTracker.open();
	}

	@PreDestroy
	public void preDestroy() {
		userLocalServiceTracker.close();
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
		this.selectedUserPortraitURL = null;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;

		// Force PrimeFaces to re-render the portrait URL.
		selectedUserPortraitURL = null;
	}
}
