/**
 * Copyright (c) 2000-2020 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.portal.component.captcha.internal;

import com.liferay.faces.portal.render.internal.PortalTagRenderer;

import com.liferay.portal.kernel.captcha.CaptchaUtil;


/**
 * @author  Neil Griffin
 */
public abstract class CaptchaRendererCompat extends PortalTagRenderer {

	// Protected Constants
	protected static final String RECAPTCHA_INPUT_NAME = "g-recaptcha-response";

	protected enum CaptchaType {
		SIMPLE, RECAPTCHA
	}

	protected final String fixMarkup(String markup) {
		return markup;
	}

	protected final CaptchaType getCaptchaType() {

		String captchaImpl = CaptchaUtil.getCaptcha().getClass().getName();

		if ((captchaImpl != null) && captchaImpl.contains("ReCaptcha")) {
			return CaptchaType.RECAPTCHA;
		}
		else {
			return CaptchaType.SIMPLE;
		}
	}
}
