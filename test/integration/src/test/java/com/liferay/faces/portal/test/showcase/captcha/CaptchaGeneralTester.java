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

import com.liferay.faces.test.selenium.browser.BrowserDriver;
import com.liferay.faces.test.selenium.browser.WaitingAsserter;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebElement;


/**
 * @author  Kyle Stiemann
 * @author  Philip White
 * @author  Vernon Singleton
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CaptchaGeneralTester extends CaptchaGeneralTesterCompat {

	protected static final String CAPTCHA_INPUT_XPATH = "//input[contains(@id,'captchaText')]";
	protected static final String CAPTCHA_MSG_INFO_XPATH = "//tr[contains(@class,'portlet-msg-info')]";
	protected static final String CAPTCHA_MSG_ERROR_XPATH = "//tr[contains(@class,'portlet-msg-error')]";

	protected String correctCaptchaValue = "";

	@Test
	public void runCaptchaGeneralTest_A_NonAuthenticated_NonRequired() throws Exception {

		BrowserDriver browserDriver = getBrowserDriver();
		WaitingAsserter waitingAsserter = getWaitingAsserter();
		navigateToUseCase(browserDriver, "captcha", "general");

		// Non-authenticated + non-required + empty submitted value
		browserDriver.waitForElementEnabled(CAPTCHA_INPUT_XPATH);
		browserDriver.clearElement(CAPTCHA_INPUT_XPATH);
		browserDriver.clickElementAndWaitForRerender(submitButton1Xpath);
		waitingAsserter.assertElementDisplayed(CAPTCHA_MSG_INFO_XPATH);
		waitingAsserter.assertTextPresentInElement("No value was entered for the non-required captcha", CAPTCHA_MSG_INFO_XPATH);

		// Non-authenticated + non-required + non-empty (incorrect) captcha value
		correctCaptchaValue = getCorrectCaptchaValue(browserDriver);
		browserDriver.clearElement(CAPTCHA_INPUT_XPATH);
		browserDriver.sendKeysToElement(CAPTCHA_INPUT_XPATH, correctCaptchaValue + "1234");
		browserDriver.clickElementAndWaitForRerender(submitButton1Xpath);
		waitingAsserter.assertElementDisplayed(error1Xpath);
		waitingAsserter.assertElementDisplayed(CAPTCHA_MSG_ERROR_XPATH);
		waitingAsserter.assertTextPresentInElement("Text verification failed", CAPTCHA_MSG_ERROR_XPATH);

		// Non-authenticated + non-required + non-empty (correct) captcha value
		correctCaptchaValue = getCorrectCaptchaValue(browserDriver);
		browserDriver.clearElement(CAPTCHA_INPUT_XPATH);
		browserDriver.sendKeysToElement(CAPTCHA_INPUT_XPATH, correctCaptchaValue);
		browserDriver.clickElementAndWaitForRerender(submitButton1Xpath);
		waitingAsserter.assertElementDisplayed(CAPTCHA_MSG_INFO_XPATH);
		waitingAsserter.assertTextPresentInElement("You entered the correct text verification code", CAPTCHA_MSG_INFO_XPATH);

	}

	@Test
	public void runCaptchaGeneralTest_B_NonAuthenticated_Required() throws Exception {

		BrowserDriver browserDriver = getBrowserDriver();
		WaitingAsserter waitingAsserter = getWaitingAsserter();
		navigateToUseCase(browserDriver, "captcha", "general");

		browserDriver.clickElement(requiredCheckbox1Xpath);

		// Non-authenticated + required + empty submitted value
		browserDriver.clearElement(CAPTCHA_INPUT_XPATH);
		browserDriver.clickElementAndWaitForRerender(submitButton1Xpath);
		waitingAsserter.assertElementDisplayed(error1Xpath);
		waitingAsserter.assertElementDisplayed(CAPTCHA_MSG_ERROR_XPATH);
		waitingAsserter.assertTextPresentInElement("Text Verification Code: Validation Error: Value is required", CAPTCHA_MSG_ERROR_XPATH);

		// Non-authenticated + required + non-empty (incorrect) captcha value
		correctCaptchaValue = getCorrectCaptchaValue(browserDriver);
		browserDriver.clearElement(CAPTCHA_INPUT_XPATH);
		browserDriver.sendKeysToElement(CAPTCHA_INPUT_XPATH, correctCaptchaValue + "1234");
		browserDriver.clickElementAndWaitForRerender(submitButton1Xpath);
		waitingAsserter.assertElementDisplayed(error1Xpath);
		waitingAsserter.assertElementDisplayed(CAPTCHA_MSG_ERROR_XPATH);
		waitingAsserter.assertTextPresentInElement("Text verification failed", CAPTCHA_MSG_ERROR_XPATH);

		// Non-authenticated + required + non-empty (correct) captcha value
		correctCaptchaValue = getCorrectCaptchaValue(browserDriver);
		browserDriver.clearElement(CAPTCHA_INPUT_XPATH);
		browserDriver.sendKeysToElement(CAPTCHA_INPUT_XPATH, correctCaptchaValue);
		browserDriver.clickElementAndWaitForRerender(submitButton1Xpath);
		waitingAsserter.assertElementDisplayed(CAPTCHA_MSG_INFO_XPATH);
		waitingAsserter.assertTextPresentInElement("You entered the correct text verification code", CAPTCHA_MSG_INFO_XPATH);

	}

	private String getCorrectCaptchaValue(BrowserDriver browserDriver) {

		String correctCaptchaValueXpath = "//input[contains(@id,':correctCaptchaValue')]";
		browserDriver.clickElementAndWaitForRerender(correctCaptchaValueXpath);

		WebElement correctCaptchaValueElement = browserDriver.findElementByXpath(correctCaptchaValueXpath);

		return correctCaptchaValueElement.getAttribute("value");
	}
}
