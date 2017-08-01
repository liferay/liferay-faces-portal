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
package com.liferay.faces.demos.test.validation;

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

	public String getCorrectCaptchaValue() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		String correctCaptchaValue = (String) sessionMap.get(WebKeys.CAPTCHA_TEXT);

		if (correctCaptchaValue == null) {
			return "Correct Captcha Value";
		}

		return correctCaptchaValue;
	}

	public boolean isValidateCaptchaForTest() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		String validateCaptchaForTest = externalContext.getInitParameter("VALIDATE_CAPTCHA_FOR_TEST");

		return BooleanHelper.isTrueToken(validateCaptchaForTest);

	}
}
