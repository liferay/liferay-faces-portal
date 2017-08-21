/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
