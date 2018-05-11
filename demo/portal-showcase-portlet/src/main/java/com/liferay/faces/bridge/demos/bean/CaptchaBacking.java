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
package com.liferay.faces.bridge.demos.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.portal.component.captcha.Captcha;
import com.liferay.faces.util.context.FacesContextHelper;
import com.liferay.faces.util.context.FacesContextHelperFactory;

import com.liferay.portal.kernel.captcha.CaptchaUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;


/**
 * @author  Juan Gonzalez
 */
@ManagedBean
@RequestScoped
public class CaptchaBacking {

	@ManagedProperty(value = "#{showcaseModelBean.selectedComponent.required}")
	private boolean requiredChecked;

	private String captchaText;

	public String getCaptchaImpl() {
		return CaptchaUtil.getCaptcha().getClass().getName();
	}

	public String getCaptchaText() {
		return captchaText;
	}

	public boolean isRequiredChecked() {
		return requiredChecked;
	}

	public void setCaptchaText(String captchaText) {
		this.captchaText = captchaText;
	}

	public void setRequiredChecked(boolean requiredChecked) {

		// Injected via @ManagedProperty
		this.requiredChecked = requiredChecked;
	}

	public void submit() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				externalContext);

		// If the user entered non-blank value for the captcha then validation was successful in the
		// PROCESS_VALIDATIONS phase of the JSF lifecycle.
		if ((captchaText != null) && (captchaText.trim().length() > 0)) {

			facesContextHelper.addGlobalInfoMessage(facesContext, "you-entered-the-correct-text-verification-code");
		}

		// Otherwise, the user entered a blank value for the captcha.
		else {

			// If the user checked the "Required" checkbox, then
			if (requiredChecked) {

				// If the portal:captcha component indicates that the value is required, then validation should have
				// failed in the PROCESS_VALIDATIONS phase of the JSF lifecycle and this method should never have been
				// called now in the INVOKE_APPLICATION phase. This indicates an error condition that should never
				// happen.
				UIViewRoot uiViewRoot = facesContext.getViewRoot();
				Captcha captcha = (Captcha) uiViewRoot.findComponent(":f1:simpleCaptcha");

				if (captcha.isRequired()) {

					facesContextHelper.addGlobalUnexpectedErrorMessage();
				}

				// Otherwise, if the portal:captcha component indicates that the value is not required, then according
				// to the JavaDoc for the required attribute, that means the captcha was "inactive" because an
				// authenticated user has correctly entered the captcha the maximum number of required times. This does
				// not indicate an error condition.
				else {

					String maxChallenges = PropsUtil.get(PropsKeys.CAPTCHA_MAX_CHALLENGES);
					facesContextHelper.addGlobalInfoMessage(facesContext,
						"the-captcha-is-no-longer-enabled-since-the-user-entered-a-correct-value-x-times",
						maxChallenges);
				}
			}

			// Otherwise, since the user did not check the "Required" checkbox, it's OK that the user entered a blank
			// value.
			else {

				facesContextHelper.addGlobalInfoMessage(facesContext,
					"no-value-was-entered-for-the-non-required-captcha");
			}
		}
	}
}
