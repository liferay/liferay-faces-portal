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
	protected static final String REFRESH_CAPTCHA_XPATH = "//a[contains(@id,'refreshCaptcha')]";
	protected static final String CAPTCHA_XPATH =
		"//div[contains(@class,'taglib-captcha')]/img[contains(@id,'captcha')]";

	@Test
	public void runCaptchaGeneralTest_C_Authenticated_NoLongerEnabled() throws Exception {

		BrowserDriver browserDriver = getBrowserDriver();
		WaitingAsserter waitingAsserter = getWaitingAsserter();

		try {

			// 1. Click on the "Sign-In" link and enter the email address and password of a valid user.
			TestUtil.signIn(browserDriver);
			navigateToUseCase(browserDriver, "captcha", "general");
			browserDriver.clickElement(requiredCheckbox1Xpath);

			// 2. Enter the correct value into the *Captcha* field.
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
	 * This test is to make sure that the fix for LPS-65011 does not regress.  It also tests the refresh button for the
	 * SimpleCaptcha.
	 */
	@Test
	public void runCaptchaGeneralTest_D_NonAuthenticated_IncorrectCaptchaAfterRefreshAndSubmit() throws Exception {

		BrowserDriver browserDriver = getBrowserDriver();
		WaitingAsserter waitingAsserter = getWaitingAsserter();
		navigateToUseCase(browserDriver, "captcha", "general");

		// 1. Enter the correct value into the *Captcha* field.
		String correctCaptchaValue = getCorrectCaptchaValue(browserDriver);
		browserDriver.clearElement(CAPTCHA_INPUT_XPATH);
		browserDriver.sendKeysToElement(CAPTCHA_INPUT_XPATH, correctCaptchaValue);

		// 2. Remember the first correct captcha value
		String correctCaptchaValue1 = correctCaptchaValue;

		// 3. Click on the *Submit* button and verify that an informational message is displayed indicating that the
		// correct value was submitted.
		browserDriver.clickElementAndWaitForRerender(submitButton1Xpath);
		waitingAsserter.assertElementDisplayed(CAPTCHA_MSG_INFO_XPATH);
		waitingAsserter.assertTextPresentInElement("You entered the correct text verification code",
				CAPTCHA_MSG_INFO_XPATH);

		// 4. Click the *Refresh CAPTCHA* button
		waitingAsserter.assertElementDisplayed(REFRESH_CAPTCHA_XPATH);
		browserDriver.clickElement(REFRESH_CAPTCHA_XPATH);
		browserDriver.waitForElementEnabled(CAPTCHA_XPATH, true);

		// 5. Enter the correct value into the *Captcha* field.
		correctCaptchaValue = getCorrectCaptchaValue(browserDriver);
		browserDriver.clearElement(CAPTCHA_INPUT_XPATH);
		browserDriver.sendKeysToElement(CAPTCHA_INPUT_XPATH, correctCaptchaValue);

		// 2. Remember the second correct captcha value
		String correctCaptchaValue2 = correctCaptchaValue;

		// 6. Click on the *Submit* button and verify that an informational message is displayed indicating that the
		// correct value was submitted.
		browserDriver.clickElementAndWaitForRerender(submitButton1Xpath);
		waitingAsserter.assertElementDisplayed(CAPTCHA_MSG_INFO_XPATH);
		waitingAsserter.assertTextPresentInElement("You entered the correct text verification code",
				CAPTCHA_MSG_INFO_XPATH);

		// 7. Get the correct captcha value for this third captcha rendered
		correctCaptchaValue = getCorrectCaptchaValue(browserDriver);

		// 8. Ensure that this final captcha value is not the same as the first
		Assert.assertNotEquals("The final captcha value should not be the same as the first (" + correctCaptchaValue1 +
				")", correctCaptchaValue1, correctCaptchaValue);

		// 9. Ensure that this final captcha value is not the same as the second
		Assert.assertNotEquals("The final captcha value should not be the same as the previous (" +
				correctCaptchaValue2 + ")", correctCaptchaValue2, correctCaptchaValue);

	}

	protected abstract String getCorrectCaptchaValue(BrowserDriver browserDriver);
}
