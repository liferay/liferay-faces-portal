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
package com.liferay.faces.portal.test.showcase.captcha;

import com.liferay.faces.portal.test.integration.PortalTestUtil;
import com.liferay.faces.test.selenium.browser.BrowserDriver;
import com.liferay.faces.test.selenium.browser.TestUtil;
import com.liferay.faces.test.selenium.browser.WaitingAsserter;
import org.junit.FixMethodOrder;
import org.junit.Test;
import com.liferay.faces.test.showcase.inputtext.InputTextTester;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebElement;


/**
 * This class provides a compatibility layer for different versions of Liferay
 *
 * @author  Vernon Singleton
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CaptchaGeneralTesterCompat extends InputTextTester {

	protected static final String CAPTCHA_INPUT_XPATH = "//input[contains(@id,'captchaText')]";
	protected static final String CAPTCHA_MSG_INFO_XPATH = "//tr[contains(@class,'portlet-msg-info')]";

	protected String correctCaptchaValue = "";

	@Test
	public void runCaptchaGeneralTest_C_Authenticated_NoLongerEnabled() throws Exception {

		BrowserDriver browserDriver = getBrowserDriver();
		WaitingAsserter waitingAsserter = getWaitingAsserter();

		try {

			TestUtil.signIn(browserDriver);
			navigateToUseCase(browserDriver, "captcha", "general");
			browserDriver.clickElement(requiredCheckbox1Xpath);

			// Authenticated + required + non-empty (correct) captcha value tests captcha.max.challenges to ensure
			// that the component is no longer required after one correct submission
			correctCaptchaValue = getCorrectCaptchaValue(browserDriver);
			browserDriver.clearElement(CAPTCHA_INPUT_XPATH);
			browserDriver.sendKeysToElement(CAPTCHA_INPUT_XPATH, correctCaptchaValue);
			browserDriver.clickElementAndWaitForRerender(submitButton1Xpath);
			waitingAsserter.assertElementDisplayed(CAPTCHA_MSG_INFO_XPATH);
			waitingAsserter.assertTextPresentInElement("You entered the correct text verification code", CAPTCHA_MSG_INFO_XPATH);

			browserDriver.clickElementAndWaitForRerender(submitButton1Xpath);
			waitingAsserter.assertElementDisplayed(CAPTCHA_MSG_INFO_XPATH);
			waitingAsserter.assertTextPresentInElement("The captcha is no longer enabled", CAPTCHA_MSG_INFO_XPATH);

		} finally {
			PortalTestUtil.signOut(browserDriver);
		}

	}

	private String getCorrectCaptchaValue(BrowserDriver browserDriver) {

		String correctCaptchaValueXpath = "//input[contains(@id,':correctCaptchaValue')]";
		browserDriver.clickElementAndWaitForRerender(correctCaptchaValueXpath);

		WebElement correctCaptchaValueElement = browserDriver.findElementByXpath(correctCaptchaValueXpath);

		return correctCaptchaValueElement.getAttribute("value");
	}
}
