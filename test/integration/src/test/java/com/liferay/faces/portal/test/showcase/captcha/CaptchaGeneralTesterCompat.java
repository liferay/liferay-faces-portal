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
			waitingAsserter.assertTextPresentInElement("You entered the correct text verification code",
				CAPTCHA_MSG_INFO_XPATH);

			browserDriver.clickElementAndWaitForRerender(submitButton1Xpath);
			waitingAsserter.assertElementDisplayed(CAPTCHA_MSG_INFO_XPATH);
			waitingAsserter.assertTextPresentInElement("The captcha is no longer enabled", CAPTCHA_MSG_INFO_XPATH);

		}
		finally {
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
