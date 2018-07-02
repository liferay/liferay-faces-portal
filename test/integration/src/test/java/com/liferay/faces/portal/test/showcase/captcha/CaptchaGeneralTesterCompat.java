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
package com.liferay.faces.portal.test.showcase.captcha;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;

import org.junit.runners.MethodSorters;

import com.liferay.faces.portal.test.integration.PortalTestUtil;
import com.liferay.faces.test.selenium.browser.BrowserDriver;
import com.liferay.faces.test.selenium.browser.TestUtil;
import com.liferay.faces.test.selenium.browser.WaitingAsserter;
import com.liferay.faces.test.showcase.inputtext.InputTextTester;


/**
 * This class provides a compatibility layer for different versions of Liferay
 *
 * @author  Vernon Singleton
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class CaptchaGeneralTesterCompat extends InputTextTester {

	// Protected Constants
	protected static final String CAPTCHA_INPUT_XPATH = "//input[contains(@id,'captchaText')]";
	protected static final String CAPTCHA_MSG_INFO_XPATH = "//tr[contains(@class,'portlet-msg-info')]";

	@Test
	public void runCaptchaGeneralTest_C_Authenticated_NoLongerEnabled() throws Exception {

		BrowserDriver browserDriver = getBrowserDriver();
		WaitingAsserter waitingAsserter = getWaitingAsserter();

		try {

			// 1. Click on the "Sign-In" link and enter the email address and password of a valid user.
			TestUtil.signIn(browserDriver);
			navigateToUseCase(browserDriver, "captcha", "general");
			browserDriver.clickElement(requiredCheckbox1Xpath);

			// 2. Enter the value that is displayed in the distorted captcha image into the *Captcha* text field.
			String correctCaptchaValue = getCorrectCaptchaValue(browserDriver);
			browserDriver.clearElement(CAPTCHA_INPUT_XPATH);
			browserDriver.sendKeysToElement(CAPTCHA_INPUT_XPATH, correctCaptchaValue);

			// 3. Click on the *Submit* button and verify that an informational message is displayed indicating that the
			// correct value was submitted.
			browserDriver.clickElementAndWaitForRerender(submitButton1Xpath);
			waitingAsserter.assertElementDisplayed(CAPTCHA_MSG_INFO_XPATH);
			waitingAsserter.assertTextPresentInElement("You entered the correct text verification code",
				CAPTCHA_MSG_INFO_XPATH);

			// 4. Click on the *Submit* button again and verify that in informational message is displayed indicating
			// that the captcha is no longer enabled since 1 correct submission is all that is required.
			browserDriver.clickElementAndWaitForRerender(submitButton1Xpath);
			waitingAsserter.assertElementDisplayed(CAPTCHA_MSG_INFO_XPATH);
			waitingAsserter.assertTextPresentInElement("The captcha is no longer enabled", CAPTCHA_MSG_INFO_XPATH);

		}
		finally {

			// 5. Click on the "Sign-Out" link.
			PortalTestUtil.signOut(browserDriver);
		}

	}

	/*
	 * This test is to make sure that the fix for LPS-65011 does not regress. It also tests the refresh button for the
	 * SimpleCaptcha.
	 */
	@Test
	public void runCaptchaGeneralTest_D_NonAuthenticated_IncorrectCaptchaAfterRefreshAndSubmit() throws Exception {

		BrowserDriver browserDriver = getBrowserDriver();
		WaitingAsserter waitingAsserter = getWaitingAsserter();
		navigateToUseCase(browserDriver, "captcha", "general");

		// 1. Take note of the first value that is displayed in the distorted captcha image.
		String correctCaptchaValue1 = getCorrectCaptchaValue(browserDriver);

		// 2. Enter the first value into the *Captcha* text field.
		browserDriver.clearElement(CAPTCHA_INPUT_XPATH);
		browserDriver.sendKeysToElement(CAPTCHA_INPUT_XPATH, correctCaptchaValue1);

		// 3. Click on the *Submit* button and verify that an informational message is displayed indicating that the
		// correct value was submitted.
		browserDriver.clickElementAndWaitForRerender(submitButton1Xpath);
		waitingAsserter.assertElementDisplayed(CAPTCHA_MSG_INFO_XPATH);
		waitingAsserter.assertTextPresentInElement("You entered the correct text verification code",
			CAPTCHA_MSG_INFO_XPATH);

		// 4. Click on the *Refresh CAPTCHA* icon.
		String refreshCaptchaXpath = "//a[contains(@id,'refreshCaptcha')]";
		waitingAsserter.assertElementDisplayed(refreshCaptchaXpath);
		browserDriver.clickElement(refreshCaptchaXpath);
		browserDriver.waitForElementEnabled("//div[contains(@class,'taglib-captcha')]/img[contains(@id,'captcha')]",
			true);

		// 5. Take note of the second value that is displayed in the distorted captcha image.
		String correctCaptchaValue2 = getCorrectCaptchaValue(browserDriver);

		// 6. Enter the second value into the *Captcha* text field.
		browserDriver.clearElement(CAPTCHA_INPUT_XPATH);
		browserDriver.sendKeysToElement(CAPTCHA_INPUT_XPATH, correctCaptchaValue2);

		// 7. Click on the *Submit* button and verify that an informational message is displayed indicating that the
		// correct value was submitted.
		browserDriver.clickElementAndWaitForRerender(submitButton1Xpath);
		waitingAsserter.assertElementDisplayed(CAPTCHA_MSG_INFO_XPATH);
		waitingAsserter.assertTextPresentInElement("You entered the correct text verification code",
			CAPTCHA_MSG_INFO_XPATH);

		// 8. Take note of the third value that is displayed in the distorted captcha image.
		String correctCaptchaValue3 = getCorrectCaptchaValue(browserDriver);

		// 9. Verify that the third value is not the same as the first one.
		Assert.assertNotEquals(correctCaptchaValue1, correctCaptchaValue3);

		// 10. Verify that the third value is not the same as the second one.
		Assert.assertNotEquals(correctCaptchaValue2, correctCaptchaValue3);
	}

	protected abstract String getCorrectCaptchaValue(BrowserDriver browserDriver);
}
