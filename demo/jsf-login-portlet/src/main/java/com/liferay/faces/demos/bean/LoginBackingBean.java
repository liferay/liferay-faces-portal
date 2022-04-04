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
package com.liferay.faces.demos.bean;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.faces.portal.context.LiferayPortletHelperUtil;
import com.liferay.faces.portal.context.PortletHelperUtil;
import com.liferay.faces.util.context.FacesContextHelperUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.LoginUtilCompat;
import com.liferay.portal.kernel.exception.CompanyMaxUsersException;
import com.liferay.portal.kernel.exception.CookieNotSupportedException;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PasswordExpiredException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.exception.UserLockoutException;
import com.liferay.portal.kernel.exception.UserPasswordException;
import com.liferay.portal.kernel.exception.UserScreenNameException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtilCompat;
import com.liferay.portal.util.PropsValuesCompat;


/**
 * @author  Neil Griffin
 */
@ManagedBean
@RequestScoped
public class LoginBackingBean {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(LoginBackingBean.class);

	// Injections
	@ManagedProperty(value = "#{loginModelBean}")
	private LoginModelBean loginModelBean;

	// Private Data Members
	private String authType;
	private String handleLabel;

	public void authenticate() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		PortletRequest portletRequest = PortletHelperUtil.getPortletRequest(facesContext);
		ActionResponse actionResponse = PortletHelperUtil.getActionResponse(facesContext);
		ThemeDisplay themeDisplay = LiferayPortletHelperUtil.getThemeDisplay(facesContext);
		HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(portletRequest);
		HttpServletResponse httpServletResponse = PortalUtil.getHttpServletResponse(actionResponse);

		String handle = loginModelBean.getHandle();
		String password = loginModelBean.getPassword();
		boolean rememberMe = loginModelBean.isRememberMe();

		boolean authenticated = false;
		String feedbackMessageId = null;

		try {

			LoginUtilCompat.login(httpServletRequest, httpServletResponse, handle, password, rememberMe, authType);

			authenticated = true;
		}
		catch (AuthException e) {
			feedbackMessageId = "authentication-failed";
		}
		catch (CompanyMaxUsersException e) {
			feedbackMessageId = "unable-to-login-because-the-maximum-number-of-users-has-been-reached";
		}
		catch (CookieNotSupportedException e) {
			feedbackMessageId = "authentication-failed-please-enable-browser-cookies";
		}
		catch (NoSuchUserException e) {
			feedbackMessageId = "authentication-failed";
		}
		catch (PasswordExpiredException e) {
			feedbackMessageId = "your-password-has-expired";
		}
		catch (UserEmailAddressException e) {
			feedbackMessageId = "authentication-failed";
		}
		catch (UserLockoutException e) {
			feedbackMessageId = "this-account-has-been-locked";
		}
		catch (UserPasswordException e) {
			feedbackMessageId = "authentication-failed";
		}
		catch (UserScreenNameException e) {
			feedbackMessageId = "authentication-failed";
		}
		catch (Exception e) {
			logger.error(e);
		}

		if (authenticated) {

			try {
				ExternalContext externalContext = facesContext.getExternalContext();

				if (PropsValuesCompat.PORTAL_JAAS_ENABLE) {
					externalContext.redirect(themeDisplay.getPathMain() + "/portal/protected");
				}
				else {
					String redirect = ParamUtil.getString(portletRequest, "redirect");

					if (Validator.isNotNull(redirect)) {
						redirect = PortalUtilCompat.escapeRedirect(redirect);

						if (!redirect.startsWith(Http.HTTP)) {
							redirect = getCompleteRedirectURL(httpServletRequest, redirect);
						}

						externalContext.redirect(redirect);
					}
					else {
						boolean doActionAfterLogin = ParamUtil.getBoolean(portletRequest, "doActionAfterLogin");

						if (doActionAfterLogin) {
							return;
						}
						else {

							redirect = getCompleteRedirectURL(httpServletRequest, themeDisplay.getPathMain());
							externalContext.redirect(redirect);
						}
					}
				}
			}
			catch (IOException e) {
				logger.error(e);
				FacesContextHelperUtil.addGlobalUnexpectedErrorMessage();
			}
		}
		else {

			if (feedbackMessageId != null) {
				FacesContextHelperUtil.addGlobalErrorMessage(feedbackMessageId);
			}
		}
	}

	public String getAuthType() {

		if (authType == null) {

			try {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				ThemeDisplay themeDisplay = LiferayPortletHelperUtil.getThemeDisplay(facesContext);
				Company company = themeDisplay.getCompany();

				authType = company.getAuthType();
			}
			catch (SystemException e) {
				logger.error(e);
				FacesContextHelperUtil.addGlobalErrorMessage("Unable to determine authentication type");
				authType = CompanyConstants.AUTH_TYPE_EA;
			}
		}

		return authType;
	}

	public String getCreateAccountURL() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
		HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(portletRequest);
		long plid = LiferayPortletHelperUtil.getPlid(facesContext);

		PortletURL createAccountURL = PortletURLFactoryUtil.create(httpServletRequest,
				"1_WAR_comliferayfacesdemojsfregistrationportlet", plid, PortletRequest.RENDER_PHASE);

		try {

			createAccountURL.setPortletMode(PortletMode.VIEW);
			createAccountURL.setWindowState(WindowState.MAXIMIZED);
		}
		catch (Exception e) {
			// do nothing.
		}

		return createAccountURL.toString();
	}

	public String getHandleLabel() {

		if (handleLabel == null) {

			String authType = getAuthType();

			if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
				handleLabel = "email-address";
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
				handleLabel = "screen-name";
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
				handleLabel = "id";
			}
		}

		return handleLabel;
	}

	public void setLoginModelBean(LoginModelBean loginModelBean) {
		this.loginModelBean = loginModelBean;
	}

	protected String getCompleteRedirectURL(HttpServletRequest httpServletRequest, String redirect) {

		String portalURL = PortalUtilCompat.getPortalURL(httpServletRequest);

		return portalURL.concat(redirect);
	}
}
