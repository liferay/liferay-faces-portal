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

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.portlet.PortletSession;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;

import com.liferay.faces.demos.dto.UploadedFileWrapper;
import com.liferay.faces.util.context.FacesContextHelperUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.model.UploadedFile;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;


/**
 * This class serves as a backing bean for the users.xhtml Facelet view. The bean is kept in request scope since it
 * doesn't maintain any model data.
 *
 * @author  Neil Griffin
 * @author  Kyle Stiemann
 */
@ManagedBean(name = "usersBackingBean")
@RequestScoped
public class UsersBackingBean {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(UsersBackingBean.class);

	// Injections
	@ManagedProperty(name = "usersModelBean", value = "#{usersModelBean}")
	private UsersModelBean usersModelBean;

	@ManagedProperty(name = "usersViewBean", value = "#{usersViewBean}")
	private UsersViewBean usersViewBean;

	// Private Data Members
	private ServiceTracker<UserLocalService, UserLocalService> userLocalServiceTracker;
	private UserLocalService userLocalService;

	public void cancel(ActionEvent actionEvent) {
		usersViewBean.setFormRendered(false);

		try {

			UploadedFile modelUploadedFile = usersModelBean.getUploadedFile();

			if (modelUploadedFile != null) {
				usersModelBean.setUploadedFile(null);
			}
		}
		catch (Exception e) {
			logger.error(e);

			FacesContextHelperUtil.addGlobalUnexpectedErrorMessage();
		}
	}

	public void handleFileUpload(FileUploadEvent fileUploadEvent) {

		try {

			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			PortletSession portletSession = (PortletSession) externalContext.getSession(false);
			String uniqueFolderName = portletSession.getId();
			org.primefaces.model.UploadedFile uploadedFile = fileUploadEvent.getFile();
			UploadedFileWrapper uploadedFileWrapper = new UploadedFileWrapper(uploadedFile,
					UploadedFile.Status.FILE_SAVED, uniqueFolderName);
			usersModelBean.setUploadedFile(uploadedFileWrapper);
			logger.debug("Received fileName=[{0}] absolutePath=[{1}]", uploadedFileWrapper.getName(),
				uploadedFileWrapper.getAbsolutePath());
		}
		catch (Exception e) {
			logger.error(e);

			FacesContextHelperUtil.addGlobalUnexpectedErrorMessage();
		}
	}

	@PostConstruct
	public void postConstruct() {
		Bundle bundle = FrameworkUtil.getBundle(this.getClass());
		final BundleContext bundleContext = bundle.getBundleContext();
		userLocalServiceTracker = ServiceTrackerFactory.open(bundleContext, UserLocalService.class,
				new ServiceTrackerCustomizer<UserLocalService, UserLocalService>() {
					@Override
					public UserLocalService addingService(ServiceReference<UserLocalService> reference) {
						userLocalService = bundleContext.getService(reference);
						return userLocalService;
					}

					@Override
					public void modifiedService(ServiceReference<UserLocalService> reference, UserLocalService service) {
					}

					@Override
					public void removedService(ServiceReference<UserLocalService> reference, UserLocalService service) {
						userLocalService = null;
						bundleContext.ungetService(reference);
					}

		});

	}

	@PreDestroy
	public void preDestroy() {
		userLocalServiceTracker.close();
	}

	public void save(ActionEvent actionEvent) {

		try {

			if (userLocalService != null) {

				// Update the selected user in the Liferay database.
				User user = usersModelBean.getSelectedUser();
				long userId = user.getUserId();

				userLocalService.updateUser(user);

				// If the end-user uploaded a portrait, then update the portrait in
				// the Liferay database and delete the temporary file.
				UploadedFile modelUploadedFile = usersModelBean.getUploadedFile();

				if (modelUploadedFile != null) {

					byte[] imageBytes = modelUploadedFile.getBytes();
					userLocalService.updatePortrait(userId, imageBytes);
					modelUploadedFile.delete();
				}
			}
			else {
				FacesContextHelperUtil.addGlobalErrorMessage("is-temporarily-unavailable", "User service");
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			FacesContextHelperUtil.addGlobalUnexpectedErrorMessage();
		}

		usersViewBean.setFormRendered(false);
		usersModelBean.forceListReload();
	}

	public void selectUser(SelectEvent selectEvent) {

		try {
			User selectedUser = (User) selectEvent.getObject();
			usersModelBean.setSelectedUser(selectedUser);
			usersViewBean.setFormRendered(true);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			FacesContextHelperUtil.addGlobalUnexpectedErrorMessage();
		}
	}

	public void setUsersModelBean(UsersModelBean usersModelBean) {

		// Injected via ManagedProperty annotation
		this.usersModelBean = usersModelBean;
	}

	public void setUsersViewBean(UsersViewBean usersViewBean) {

		// Injected via ManagedProperty annotation
		this.usersViewBean = usersViewBean;
	}
}
