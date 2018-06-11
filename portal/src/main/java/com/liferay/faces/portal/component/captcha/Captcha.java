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
package com.liferay.faces.portal.component.captcha;

import java.util.Locale;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.PortletRequest;
import javax.portlet.filter.PortletRequestWrapper;

import com.liferay.faces.util.i18n.I18n;
import com.liferay.faces.util.i18n.I18nFactory;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.captcha.CaptchaException;
import com.liferay.portal.kernel.captcha.CaptchaMaxChallengesException;
import com.liferay.portal.kernel.captcha.CaptchaTextException;
import com.liferay.portal.kernel.captcha.CaptchaUtil;


/**
 * @author  Juan Gonzalez
 */
@FacesComponent(value = Captcha.COMPONENT_TYPE)
public class Captcha extends CaptchaBase {

	// Private Constants
	private static final String WEB_KEYS_CAPTCHA_TEXT = "CAPTCHA_TEXT";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(Captcha.class);

	@Override
	public boolean isRequired() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();

		// If the captcha is enabled, then return the value of the required attribute. Otherwise, if the captcha is
		// disabled, then since the liferay-ui:captcha JSP tag will not render any markup, there is no input data to
		// validate. In this case it is necessary to return false, indicating that the field is not required. Note that
		// the captcha will become disabled when a user is considered to be trustworthy. Only authenticated users can
		// achieve this level of trust, and only after they have successfully entered a correct captcha value
		// 'maxChallenges' times, which is 1 by default.
		boolean captchaEnabled = true;

		try {
			captchaEnabled = CaptchaUtil.isEnabled(portletRequest);
		}
		catch (CaptchaException e) {
			logger.error(e);
		}

		return captchaEnabled && super.isRequired();
	}

	@Override
	protected void validateValue(FacesContext facesContext, Object value) {

		super.validateValue(facesContext, value);

		if (isValid() && (value != null)) {

			String valueAsString = value.toString();

			if (isRequired() || (valueAsString.length() > 0)) {

				UIViewRoot viewRoot = facesContext.getViewRoot();
				Locale locale = viewRoot.getLocale();
				ExternalContext externalContext = facesContext.getExternalContext();
				I18n i18n = I18nFactory.getI18nInstance(externalContext);

				try {
					Map<String, Object> sessionMap = externalContext.getSessionMap();
					PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
					String userCaptchaTextValue = value.toString();
					String correctCaptchaTextValue = (String) sessionMap.get(WEB_KEYS_CAPTCHA_TEXT);

					CaptchaPortletRequest captchaPortletRequest = new CaptchaPortletRequest(portletRequest,
							userCaptchaTextValue);

					// The CaptchaUtil.check(PortletRequest) method will ultimately call
					// portletRequest.getParameter("captchaText") and so we have to pass a CaptchaPortletRequest to
					// handle that. This is because the string "captchaText" is hard-coded in the liferay-ui:captcha
					// JSP.
					CaptchaUtil.check(captchaPortletRequest);

					// Liferay Captcha implementations like SimpleCaptchaUtil will remove the "CAPTCHA_TEXT" session
					// attribute when calling the Capatcha.check(PortletRequest) method. But this will cause a problem
					// if we're using an Ajaxified input field. As a workaround, set the value of the attribute again.
					sessionMap.put(WEB_KEYS_CAPTCHA_TEXT, correctCaptchaTextValue);
				}
				catch (CaptchaTextException e) {
					String summary = getValidatorMessage();

					if (summary == null) {
						summary = i18n.getMessage(facesContext, locale, "text-verification-failed");
					}

					FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, summary);
					facesContext.addMessage(getClientId(facesContext), facesMessage);
					setValid(false);
				}
				catch (CaptchaMaxChallengesException e) {
					String summary = i18n.getMessage(facesContext, locale,
							"maximum-number-of-captcha-attempts-exceeded");
					FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, summary);
					facesContext.addMessage(getClientId(facesContext), facesMessage);
					setValid(false);
				}
				catch (Exception e) {
					logger.error(e);

					String summary = i18n.getMessage(facesContext, locale, "an-unexpected-error-occurred");
					FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, summary);
					facesContext.addMessage(getClientId(facesContext), facesMessage);
					setValid(false);
				}
			}
		}
	}

	private static final class CaptchaPortletRequest extends PortletRequestWrapper {

		private String userCaptchaTextValue;

		public CaptchaPortletRequest(PortletRequest portletRequest, String userCaptchaTextValue) {
			super(portletRequest);
			this.userCaptchaTextValue = userCaptchaTextValue;
		}

		@Override
		public String getParameter(String name) {

			if ("captchaText".equals(name)) {
				return userCaptchaTextValue;
			}
			else {
				return super.getParameter(name);
			}
		}
	}
}
