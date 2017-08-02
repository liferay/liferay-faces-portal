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
package com.liferay.faces.portal.test.showcase.captcha;

import org.junit.FixMethodOrder;
import org.junit.Test;

import org.junit.runners.MethodSorters;

import org.openqa.selenium.WebElement;

import com.liferay.faces.test.selenium.browser.BrowserDriver;
import com.liferay.faces.test.selenium.browser.WaitingAsserter;


/**
 * @author  Kyle Stiemann
 * @author  Philip White
 * @author  Vernon Singleton
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CaptchaGeneralTester extends CaptchaGeneralTesterCompat {

	// Private Constants
	private static final String CAPTCHA_MSG_INFO_XPATH = "//tr[contains(@class,'portlet-msg-info')]";

	@Test
	public void runCaptchaGeneralTest_A_NonAuthenticated_NonRequired() throws Exception {

		BrowserDriver browserDriver = getBrowserDriver();
		WaitingAsserter waitingAsserter = getWaitingAsserter();
		navigateToUseCase(browserDriver, "captcha", "general");

		// 1. Ensure that there is no value entered into the *Captcha* field.
		browserDriver.waitForElementEnabled(CAPTCHA_INPUT_XPATH);
		browserDriver.clearElement(CAPTCHA_INPUT_XPATH);

		// 2. Click on the *Submit* button and verify that an informational message is displayed indicating that a
		// value was not submitted.
		browserDriver.clickElementAndWaitForRerender(submitButton1Xpath);
		waitingAsserter.assertElementDisplayed(CAPTCHA_MSG_INFO_XPATH);
		waitingAsserter.assertTextPresentInElement("No value was entered for the non-required captcha",
			CAPTCHA_MSG_INFO_XPATH);

		// 3. Enter an incorrect value into the *Captcha* field.
		String correctCaptchaValue = getCorrectCaptchaValue(browserDriver);
		browserDriver.clearElement(CAPTCHA_INPUT_XPATH);
		browserDriver.sendKeysToElement(CAPTCHA_INPUT_XPATH, correctCaptchaValue + "1234");

		// 4. Click on the *Submit* button and verify that an error message is displayed indicating that an incorrect
		// value was submitted.
		browserDriver.clickElementAndWaitForRerender(submitButton1Xpath);
		waitingAsserter.assertElementDisplayed(error1Xpath);
		waitingAsserter.assertElementDisplayed(CAPTCHA_MSG_ERROR_XPATH);
		waitingAsserter.assertTextPresentInElement("Text verification failed", CAPTCHA_MSG_ERROR_XPATH);

		// 5. Enter the correct value into the *Captcha* field.
		correctCaptchaValue = getCorrectCaptchaValue(browserDriver);
		browserDriver.clearElement(CAPTCHA_INPUT_XPATH);
		browserDriver.sendKeysToElement(CAPTCHA_INPUT_XPATH, correctCaptchaValue);

		// 6. Click on the *Submit* button and verify that an informational message is displayed indicating that the
		// correct value was submitted.
		browserDriver.clickElementAndWaitForRerender(submitButton1Xpath);
		waitingAsserter.assertElementDisplayed(CAPTCHA_MSG_INFO_XPATH);
		waitingAsserter.assertTextPresentInElement("You entered the correct text verification code",
			CAPTCHA_MSG_INFO_XPATH);

	}

	@Test
	public void runCaptchaGeneralTest_B_NonAuthenticated_Required() throws Exception {

		BrowserDriver browserDriver = getBrowserDriver();
		WaitingAsserter waitingAsserter = getWaitingAsserter();
		navigateToUseCase(browserDriver, "captcha", "general");

		// 1. Click on the *Required* checkbox.
		browserDriver.clickElement(requiredCheckbox1Xpath);

		// 2. Ensure that there is no value entered into the *Captcha* field.
		browserDriver.clearElement(CAPTCHA_INPUT_XPATH);

		// 3. Click on the *Submit* button and verify that an error message is displayed indicating that the
		// value is required but was not submitted.
		browserDriver.clickElementAndWaitForRerender(submitButton1Xpath);
		waitingAsserter.assertElementDisplayed(error1Xpath);
		waitingAsserter.assertElementDisplayed(CAPTCHA_MSG_ERROR_XPATH);
		waitingAsserter.assertTextPresentInElement("Text Verification Code: Validation Error: Value is required",
			CAPTCHA_MSG_ERROR_XPATH);

		// 4. Enter an incorrect value into the *Captcha* field.
		String correctCaptchaValue = getCorrectCaptchaValue(browserDriver);
		browserDriver.clearElement(CAPTCHA_INPUT_XPATH);
		browserDriver.sendKeysToElement(CAPTCHA_INPUT_XPATH, correctCaptchaValue + "1234");

		// 5. Click on the *Submit* button and verify that an error message is displayed indicating that an incorrect
		// value was submitted.
		browserDriver.clickElementAndWaitForRerender(submitButton1Xpath);
		waitingAsserter.assertElementDisplayed(error1Xpath);
		waitingAsserter.assertElementDisplayed(CAPTCHA_MSG_ERROR_XPATH);
		waitingAsserter.assertTextPresentInElement("Text verification failed", CAPTCHA_MSG_ERROR_XPATH);

		// 6. Enter the correct value into the *Captcha* field.
		correctCaptchaValue = getCorrectCaptchaValue(browserDriver);
		browserDriver.clearElement(CAPTCHA_INPUT_XPATH);
		browserDriver.sendKeysToElement(CAPTCHA_INPUT_XPATH, correctCaptchaValue);

		// 7. Click on the *Submit* button and verify that an informational message is displayed indicating that the
		// correct value was submitted.
		browserDriver.clickElementAndWaitForRerender(submitButton1Xpath);
		waitingAsserter.assertElementDisplayed(CAPTCHA_MSG_INFO_XPATH);
		waitingAsserter.assertTextPresentInElement("You entered the correct text verification code",
			CAPTCHA_MSG_INFO_XPATH);
	}

	@Override
	protected String getCorrectCaptchaValue(BrowserDriver browserDriver) {

		String correctCaptchaValueXpath = "//a[contains(@id,':correctCaptchaValue')]";
		browserDriver.clickElementAndWaitForRerender(correctCaptchaValueXpath);

		WebElement correctCaptchaValueElement = browserDriver.findElementByXpath(correctCaptchaValueXpath);

		return correctCaptchaValueElement.getText();
	}
}
