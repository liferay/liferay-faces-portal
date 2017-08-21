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
package com.liferay.faces.portal.component.captcha.internal;

import com.liferay.faces.portal.component.captcha.Captcha;
import com.liferay.faces.portal.render.internal.DelayedPortalTagRenderer;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import com.liferay.taglib.ui.CaptchaTag;


/**
 * @author  Neil Griffin
 */
public abstract class CaptchaRendererCompat extends DelayedPortalTagRenderer<Captcha, CaptchaTag> {

	// Protected Constants
	protected static final String RECAPTCHA_INPUT_NAME = "recaptcha_response_field";

	protected enum CaptchaType {
		SIMPLE, RECAPTCHA
	}

	protected String fixMarkup(String markup) throws Exception {

		// Note: The following reCAPTCHA script has surrounding CDATA markers that must be removed in order for jsf.js
		// to be able to parse the partial-response as a well-formed XML document.

		//J-
		// // <![CDATA["
		// <script type="text/javascript">
		// var RecaptchaOptions = { lang : 'en', theme : 'white' };
		// </script>
		// // ]]>
		//J+

		if (getCaptchaType() == CaptchaType.RECAPTCHA) {
			String token = "// <![CDATA[";
			int pos = markup.indexOf(token);

			if (pos > 0) {
				markup = markup.substring(0, pos) + markup.substring(pos + token.length());
				token = "// ]]>";
				pos = markup.indexOf(token);

				if (pos > 0) {
					markup = markup.substring(0, pos) + markup.substring(pos + token.length());
				}
			}
		}

		return markup;
	}

	protected CaptchaType getCaptchaType() {

		String captchaImpl = PropsUtil.get(PropsKeys.CAPTCHA_ENGINE_IMPL);

		if ((captchaImpl != null) && captchaImpl.contains("ReCaptcha")) {
			return CaptchaType.RECAPTCHA;
		}
		else {
			return CaptchaType.SIMPLE;
		}
	}
}
