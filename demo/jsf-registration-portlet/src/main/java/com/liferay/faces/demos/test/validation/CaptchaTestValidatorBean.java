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

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.liferay.faces.portal.component.captcha.Captcha;
import com.liferay.faces.util.context.FacesContextHelperUtil;
import com.liferay.faces.util.helper.BooleanHelper;


/**
 * @author  Kyle Stiemann
 */
public abstract class CaptchaTestValidatorBean {

	@PostConstruct
	public void addValidatorToCaptcha() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		String validateCaptchaForTest = externalContext.getInitParameter("VALIDATE_CAPTCHA_FOR_TEST");

		if (BooleanHelper.isTrueToken(validateCaptchaForTest)) {

			Captcha captcha = (Captcha) FacesContextHelperUtil.matchComponentInViewRoot("captcha");
			boolean captchaTestValidatorAdded = false;
			Validator[] validators = captcha.getValidators();

			for (Validator validator : validators) {

				if (validator instanceof CaptchaTestValidator) {

					captchaTestValidatorAdded = true;

					break;
				}
			}

			if (!captchaTestValidatorAdded) {
				captcha.addValidator(new CaptchaTestValidator());
			}
		}
	}

	private static class CaptchaTestValidator implements Validator, Serializable {

		// serialVersionUID
		private static final long serialVersionUID = 5129264232758522926L;

		@Override
		public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

			// Set the captcha to always accept "1111".
			context.getExternalContext().getSessionMap().put("CAPTCHA_TEXT", "1111");
		}
	}
}
