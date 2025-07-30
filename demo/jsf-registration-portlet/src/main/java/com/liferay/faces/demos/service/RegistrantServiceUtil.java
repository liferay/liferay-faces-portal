/**
 * Copyright (c) 2000-2022 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.demos.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.faces.demos.expando.UserExpando;
import com.liferay.faces.demos.model.Registrant;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;



/**
 * Service API for adding/updating registrants.
 *
 * @author  Neil Griffin
 */
public final class RegistrantServiceUtil {

	private static final Logger logger = LoggerFactory.getLogger(RegistrantServiceUtil.class);

	private RegistrantServiceUtil() {
		throw new AssertionError();
	}

	public static Registrant add(long creatorUserId, long companyId, Locale locale, Registrant registrant,
		boolean active, boolean autoScreenName, boolean sendEmail) throws PortalException, SystemException {
		boolean autoPassword = false;
		String password1 = registrant.getPassword1();
		String password2 = registrant.getPassword2();
		String screenName;

		if (autoScreenName) {
			screenName = "";
		}
		else {
			screenName = registrant.getScreenName();
		}

		String emailAddress = registrant.getEmailAddress();
		long facebookId = 0;
		String openId = "";
		String firstName = registrant.getFirstName();
		String middleName = registrant.getMiddleName();
		String lastName = registrant.getLastName();
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
		ServiceContext serviceContext = new ServiceContext();

		// Add the user to the Liferay database (create an account).
		User user = null;

		try {
			user = UserLocalServiceUtil.addUser(creatorUserId, companyId, autoPassword, password1, password2,
					autoScreenName, screenName, emailAddress, locale, firstName, middleName,
					lastName, prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear, jobTitle, UserConstants.TYPE_REGULAR, groupIds,
					organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);
		}
		catch (Throwable t) {

			// At some point the facebookId and openId arguments were removed from the method signature.
			try {
				Method method = UserLocalServiceUtil.class.getDeclaredMethod("addUser", long.class, long.class,
						boolean.class, String.class, String.class, boolean.class, String.class, String.class,
						Locale.class, String.class, String.class, String.class, long.class, long.class, boolean.class,
						int.class, int.class, int.class, String.class, groupIds.getClass(), organizationIds.getClass(),
						roleIds.getClass(), userGroupIds.getClass(), boolean.class, ServiceContext.class);

				user = (User) method.invoke(null, creatorUserId, companyId, autoPassword, password1, password2,
						autoScreenName, screenName, emailAddress, locale, firstName, middleName, lastName, prefixId,
						suffixId, male, birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds,
						roleIds, userGroupIds, sendEmail, serviceContext);
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);

				if (e instanceof PortalException) {
					throw (PortalException) e;
				}

				if (e instanceof InvocationTargetException) {
					InvocationTargetException ite = (InvocationTargetException) e;
					Throwable cause = ite.getCause();

					if (cause instanceof PortalException) {
						throw (PortalException) cause;
					}

					throw new PortalException(cause);
				}

				throw new PortalException(e);
			}
		}

		registrant.setUserId(user.getUserId());
		registrant.setContactId(user.getContactId());

		// Disable the ability to login until someone approves the account.
		if (!active) {
			UserLocalServiceUtil.updateStatus(user.getUserId(), user.getStatus(), serviceContext);
		}

		// Update Expandos
		updateExpandos(companyId, registrant);

		return registrant;
	}

	private static PermissionChecker getAdministratorPermissionChecker(long companyId) throws PortalException,
		SystemException {
		PermissionChecker administratorPermissionChecker;
		Role administratorRole = RoleLocalServiceUtil.getRole(companyId, RoleConstants.ADMINISTRATOR);
		List<User> administratorUsers = UserLocalServiceUtil.getRoleUsers(administratorRole.getRoleId());

		if ((administratorUsers != null) && (administratorUsers.size() > 0)) {

			User administratorUser = administratorUsers.get(0);

			try {
				administratorPermissionChecker = PermissionCheckerFactoryUtil.getPermissionCheckerFactory().create(
						administratorUser);
			}
			catch (Exception e) {
				throw new SystemException(e.getMessage(), e);
			}
		}
		else {
			throw new SystemException("Unable to find a user with the Administrator role! Impossible!");
		}

		return administratorPermissionChecker;
	}

	private static void updateExpandos(long companyId, Registrant registrant) throws PortalException, SystemException {

		// Set the expando column (custom field) values. Note that since the registration portlet is being used
		// by a "Guest" user, we have to trick the Liferay permissionChecker into thinking that the current
		// user is the Administrator user (someone who has the permission to set expando column values).
		PermissionChecker permissionCheckerBackup = PermissionThreadLocal.getPermissionChecker();
		PermissionThreadLocal.setPermissionChecker(getAdministratorPermissionChecker(companyId));

		ExpandoBridge expandoBridge = registrant.getExpandoBridge();
		expandoBridge.setAttribute(UserExpando.COMPANY_NAME.getName(), registrant.getCompanyName());
		expandoBridge.setAttribute(UserExpando.FAVORITE_COLOR.getName(), registrant.getFavoriteColor());

		PermissionThreadLocal.setPermissionChecker(permissionCheckerBackup);
	}
}
