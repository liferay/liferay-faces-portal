/**
 * Copyright (c) 2000-2019 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.demos.validation;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.helper.BooleanHelper;

import com.liferay.portal.kernel.util.WebKeys;


/**
 * @author  Kyle Stiemann
 */
@ManagedBean
@RequestScoped
public class CaptchaTestValidatorBean {

	// Private Data Members
	private String correctCaptchaValue;
	private Boolean validateCaptchaForTest;

	public String getCorrectCaptchaValue() {

		if (correctCaptchaValue == null) {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			correctCaptchaValue = (String) sessionMap.get(WebKeys.CAPTCHA_TEXT);

			if (correctCaptchaValue == null) {
				return "Correct Captcha Value";
			}
		}

		return correctCaptchaValue;
	}

	public boolean isValidateCaptchaForTest() {

		if (validateCaptchaForTest == null) {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			String initParameterValue = externalContext.getInitParameter("VALIDATE_CAPTCHA_FOR_TEST");

			validateCaptchaForTest = Boolean.valueOf(BooleanHelper.isTrueToken(initParameterValue));
		}

		return validateCaptchaForTest;
	}
}
