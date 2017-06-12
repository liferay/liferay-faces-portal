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

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import com.liferay.faces.demos.service.CompanyLocalServiceTracker;
import com.liferay.faces.portal.context.LiferayPortletHelperUtil;
import com.liferay.faces.util.context.FacesContextHelperUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.exception.NoSuchCompanyException;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;


/**
 * @author  Philip White
 */
@ManagedBean(name = "testSetupBackingBean")
@RequestScoped
public class TestSetupBackingBean {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(TestSetupBackingBean.class);

	protected transient CompanyLocalServiceTracker companyLocalServiceTracker;

	// Injections
	@ManagedProperty(name = "usersModelBean", value = "#{usersModelBean}")
	private UsersModelBean usersModelBean;

	public User addUser(long creatorUserId, long companyId, String firstName, String lastName) throws PortalException {

		boolean autoPassword = false;
		String password1 = "test";
		String password2 = password1;
		boolean autoScreenName = false;
		String screenName = firstName.toLowerCase() + "." + lastName.toLowerCase();
		String emailAddress = screenName + "@" + "liferay.com";
		long facebookId = 0L;
		String openId = "";
		Locale locale = Locale.ENGLISH;
		String middleName = "";
		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = 1;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = "";
		long[] groupIds = new long[] {};
		long[] organizationIds = new long[] {};
		long[] roleIds = new long[] {};
		long[] userGroupIds = new long[] {};
		boolean sendEmail = false;
		ServiceContext serviceContext = new ServiceContext();
		User user = null;
		UserLocalService userLocalService;

		if (usersModelBean.userLocalServiceTracker.isEmpty()) {
			FacesContextHelperUtil.addGlobalErrorMessage("is-temporarily-unavailable", "User service");
		}
		else {
			userLocalService = usersModelBean.userLocalServiceTracker.getService();

			boolean shouldAddUser = false;

			try {
				user = userLocalService.getUserByScreenName(companyId, screenName);

				if ("john.adams".equals(screenName)) {
					userLocalService.deleteUser(user);
					shouldAddUser = true;
				}
			}
			catch (NoSuchUserException e) {
				shouldAddUser = true;
			}

			if (shouldAddUser) {
				user = userLocalService.addUser(creatorUserId, companyId, autoPassword, password1, password2,
						autoScreenName, screenName, emailAddress, facebookId, openId, locale, firstName, middleName,
						lastName, prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
						groupIds, organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);
				logger.debug("Added user=[{0}]", screenName);
			}
		}

		return user;
	}

	@PostConstruct
	public void postConstruct() {
		Bundle bundle = FrameworkUtil.getBundle(this.getClass());
		BundleContext bundleContext = bundle.getBundleContext();
		companyLocalServiceTracker = new CompanyLocalServiceTracker(bundleContext);
		companyLocalServiceTracker.open();
	}

	@PreDestroy
	public void preDestroy() {
		companyLocalServiceTracker.close();
	}

	public void resetUsers(ActionEvent actionEvent) throws PortalException {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		if (companyLocalServiceTracker.isEmpty()) {
			FacesContextHelperUtil.addGlobalErrorMessage(facesContext, "is-temporarily-unavailable", "Company service");
		}
		else {
			CompanyLocalService companyLocalService = companyLocalServiceTracker.getService();

			try {
				long companyId = LiferayPortletHelperUtil.getCompanyId(facesContext);
				Company company = companyLocalService.getCompanyById(companyId);
				long userId = company.getDefaultUser().getUserId();
				setupUsers(companyId, userId);
				usersModelBean.forceListReload();
			}
			catch (NoSuchCompanyException e) {
				FacesContextHelperUtil.addGlobalErrorMessage(facesContext, "is-temporarily-unavailable",
					"Company service");
				logger.error(e);
			}
		}
	}

	public void setUsersModelBean(UsersModelBean usersModelBean) {

		// Injected via ManagedProperty annotation
		this.usersModelBean = usersModelBean;
	}

	protected void setupUsers(long companyId, long userId) throws PortalException {
		addUser(userId, companyId, "John", "Adams");
		addUser(userId, companyId, "Samuel", "Adams");
		addUser(userId, companyId, "Josiah", "Bartlett");
		addUser(userId, companyId, "Carter", "Braxton");
		addUser(userId, companyId, "Charles", "Carroll");
		addUser(userId, companyId, "Benjamin", "Franklin");
		addUser(userId, companyId, "Elbridge", "Gerry");
		addUser(userId, companyId, "John", "Hancock");
		addUser(userId, companyId, "Thomas", "Jefferson");
		addUser(userId, companyId, "Francis", "Lewis");
		addUser(userId, companyId, "Philip", "Livingston");
		addUser(userId, companyId, "Thomas", "McKean");
		addUser(userId, companyId, "Arthur", "Middleton");
		addUser(userId, companyId, "John", "Penn");
		addUser(userId, companyId, "George", "Read");
		addUser(userId, companyId, "John", "Rutledge");
		addUser(userId, companyId, "Roger", "Sherman");
		addUser(userId, companyId, "Thomas", "Stone");
		addUser(userId, companyId, "George", "Taylor");
		addUser(userId, companyId, "George", "Washington");
		addUser(userId, companyId, "John", "Witherspoon");
		addUser(userId, companyId, "Oliver", "Wolcott");
		addUser(userId, companyId, "George", "Wythe");
	}
}
