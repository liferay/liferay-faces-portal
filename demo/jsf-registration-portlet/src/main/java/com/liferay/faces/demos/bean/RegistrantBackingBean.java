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
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.liferay.faces.demos.model.Registrant;
import com.liferay.faces.demos.service.RegistrantServiceUtil;
import com.liferay.faces.demos.test.validation.CaptchaTestValidatorBean;
import com.liferay.faces.portal.context.LiferayPortletHelperUtil;
import com.liferay.faces.util.context.FacesContextHelperUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.exception.UserPasswordException;
import com.liferay.portal.kernel.exception.UserScreenNameException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;


/**
 * This is a JSF backing managed-bean for the registrant.xhtml composition.
 *
 * @author  "Neil Griffin"
 */
@ManagedBean(name = "registrantBackingBean")
@ViewScoped
public class RegistrantBackingBean extends CaptchaTestValidatorBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 2947548873495692163L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(RegistrantBackingBean.class);

	// Injections
	@ManagedProperty(value = "#{registrantModelBean}")
	private transient RegistrantModelBean registrantModelBean;

	// Private Data Members
	private Boolean captchaRendered;

	public boolean isCaptchaRendered() {

		if (captchaRendered == null) {
			captchaRendered = Boolean.valueOf(GetterUtil.getBoolean(
						PropsUtil.get(PropsKeys.CAPTCHA_CHECK_PORTAL_CREATE_ACCOUNT), true));
		}

		return captchaRendered.booleanValue();
	}

	public void setRegistrantModelBean(RegistrantModelBean registrantModelBean) {

		// Injected via @ManagedProperty annotation
		this.registrantModelBean = registrantModelBean;
	}

	public void submit(ActionEvent actionEvent) {

		Registrant submittedRegistrant = registrantModelBean.getRegistrant();
		logger.debug("Adding user firstName=[{0}], lastName=[{1}], emailAddress=[{2}], captchaText=[{3}]",
			new Object[] {
				submittedRegistrant.getFirstName(), submittedRegistrant.getLastName(),
				submittedRegistrant.getEmailAddress()
			});

		FacesContext facesContext = FacesContext.getCurrentInstance();
		long creatorUserId = LiferayPortletHelperUtil.getUserId(facesContext);
		long companyId = LiferayPortletHelperUtil.getCompanyId(facesContext);
		Locale locale = FacesContextHelperUtil.getLocale(facesContext);

		try {
			boolean active = true;
			boolean autoScreenName = false;
			boolean sendEmail = true;
			RegistrantServiceUtil.add(creatorUserId, companyId, locale, submittedRegistrant, active, autoScreenName,
				sendEmail);

			String key = "thank-you-for-registering";
			FacesContextHelperUtil.addGlobalInfoMessage(key, submittedRegistrant.getEmailAddress());
			submittedRegistrant.clearProperties();
		}
		catch (UserScreenNameException.MustNotBeDuplicate e1) {
			FacesContextHelperUtil.addGlobalErrorMessage("the-screen-name-you-requested-is-already-taken");
		}
		catch (UserEmailAddressException.MustNotBeDuplicate e2) {
			FacesContextHelperUtil.addGlobalErrorMessage("the-email-address-you-requested-is-already-taken");
		}
		catch (UserPasswordException.MustNotBeRecentlyUsed e3) {
			FacesContextHelperUtil.addGlobalErrorMessage(
				"that-password-has-already-been-used-please-enter-in-a-different-password");
		}
		catch (UserPasswordException.MustNotContainDictionaryWords e4) {
			FacesContextHelperUtil.addGlobalErrorMessage(
				"that-password-uses-common-words-please-enter-in-a-password-that-is-harder-to-guess-i-e-contains-a-mix-of-numbers-and-letters");
		}
		catch (UserPasswordException.MustComplyWithModelListeners e5) {
			FacesContextHelperUtil.addGlobalErrorMessage(
				"that-password-is-invalid-please-enter-in-a-different-password");
		}
		catch (UserPasswordException.MustComplyWithRegex e6) {
			FacesContextHelperUtil.addGlobalErrorMessage(
				"that-password-is-invalid-please-enter-in-a-different-password");
		}
		catch (UserPasswordException.MustMatchCurrentPassword e7) {
			FacesContextHelperUtil.addGlobalErrorMessage(
				"that-password-is-invalid-please-enter-in-a-different-password");
		}
		catch (UserPasswordException.MustNotBeNull e8) {
			FacesContextHelperUtil.addGlobalErrorMessage(
				"that-password-is-invalid-please-enter-in-a-different-password");
		}
		catch (UserPasswordException.MustBeLonger e9) {

			try {
				Company company = CompanyLocalServiceUtil.getCompany(companyId);
				PasswordPolicy passwordPolicy = company.getDefaultUser().getPasswordPolicy();
				FacesContextHelperUtil.addGlobalErrorMessage(
					"that-password-is-too-short-or-too-long-please-make-sure-your-password-is-between-x-and-512-characters",
					new Object[] { String.valueOf(passwordPolicy.getMinLength()) });
			}
			catch (Exception e1) {
				logger.error(e1.getMessage(), e1);
				FacesContextHelperUtil.addGlobalUnexpectedErrorMessage();
			}
		}
		catch (UserPasswordException.MustNotBeChanged e10) {
			FacesContextHelperUtil.addGlobalErrorMessage("your-password-cannot-be-changed");
		}
		catch (UserPasswordException.MustNotBeEqualToCurrent e11) {
			FacesContextHelperUtil.addGlobalErrorMessage(
				"your-new-password-cannot-be-the-same-as-your-old-password-please-enter-in-a-different-password");
		}
		catch (UserPasswordException.MustNotBeTrivial e12) {
			FacesContextHelperUtil.addGlobalErrorMessage("that-password-is-too-trivial");
		}
		catch (UserPasswordException.MustNotBeChangedYet e13) {

			try {
				Company company = CompanyLocalServiceUtil.getCompany(companyId);
				PasswordPolicy passwordPolicy = company.getDefaultUser().getPasswordPolicy();
				FacesContextHelperUtil.addGlobalErrorMessage(
					"you-cannot-change-your-password-yet-please-wait-at-least-x-before-changing-your-password-again",
					new Object[] { String.valueOf(passwordPolicy.getMinAge() * 1000) });

			}
			catch (Exception e1) {
				logger.error(e1.getMessage(), e1);
				FacesContextHelperUtil.addGlobalUnexpectedErrorMessage();
			}
		}
		catch (UserPasswordException.MustMatch e14) {
			FacesContextHelperUtil.addGlobalErrorMessage("the-passwords-you-entered-do-not-match");
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesContextHelperUtil.addGlobalUnexpectedErrorMessage();
		}
	}

}
