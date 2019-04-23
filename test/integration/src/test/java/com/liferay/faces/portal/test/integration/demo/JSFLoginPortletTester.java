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
package com.liferay.faces.portal.test.integration.demo;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;

import org.junit.runners.MethodSorters;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liferay.faces.portal.test.integration.PortalTestUtil;
import com.liferay.faces.test.selenium.browser.BrowserDriver;
import com.liferay.faces.test.selenium.browser.BrowserDriverManagingTesterBase;
import com.liferay.faces.test.selenium.browser.TestUtil;
import com.liferay.faces.test.selenium.browser.WaitingAsserter;


/**
 * @author  Vernon Singleton
 * @author  Philip White
 * @author  Kyle Stiemann
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JSFLoginPortletTester extends BrowserDriverManagingTesterBase {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(JSFLoginPortletTester.class);

	// Private Static Data Members
	private static boolean afterFinalTest = false;

	@After
	public void reset() {

		if (afterFinalTest) {

			// Sign out and set up for the next test.
			afterFinalTest = false;
			signOut();
			super.doSetUp();
		}

	}

	@Test
	public void runJSF_LoginPortletTester_A_Login() {
		testJSF_Login(false);
	}

	@Test
	public void runJSF_LoginPortletTester_B_RememberMe() {

		afterFinalTest = true;
		testJSF_Login(true);
	}

	@Before
	public void signOut() {

		BrowserDriver browserDriver = getBrowserDriver();
		PortalTestUtil.signOut(browserDriver);
	}

	public void testJSF_Login(boolean testRememberMe) {

		// 1. Navigate the browser to the portal page that contains the jsf-sign-in portlet.
		BrowserDriver browserDriver = getBrowserDriver();
		WaitingAsserter waitingAsserter = getWaitingAsserter();
		String jsfLoginPageURL = PortalTestUtil.getGuestPageURL("jsf-sign-in");
		browserDriver.navigateWindowTo(jsfLoginPageURL);

		String emailFieldXpath = "//input[contains(@id,':handle')]";
		browserDriver.waitForElementEnabled(emailFieldXpath);

		if (testRememberMe) {
			browserDriver.clickElement("//div[contains(@id,'rememberMe')]/input");
		}

		// 2. Wait for the *Email Address* field element to accept input.
		browserDriver.waitForElementDisplayed(emailFieldXpath);
		waitingAsserter.assertElementDisplayed(emailFieldXpath);

		// 3. Clear the *Email Address* field.
		browserDriver.clearElement(emailFieldXpath);

		// 4. Enter "test@liferay.com" into the *Email Address* field.
		browserDriver.sendKeysToElement(emailFieldXpath, "test@liferay.com");

		String passwordFieldXpath = "//input[contains(@id,':password')]";
		String signInButtonXpath = "//button[@value='Sign In']";

		// Note: skip steps 5-9 if you are testing the "Remember Me" feature.
		if (!testRememberMe) {

			// 5. Enter "invalid_password" into the *Password* feild.
			browserDriver.sendKeysToElement(passwordFieldXpath, "invalid_password");

			// 6. Click the *Sign In* button.
			browserDriver.clickElementAndWaitForRerender(signInButtonXpath);

			// 7. Verify that the "Authentication failed" error message is displayed.
			String messageErrorXpath = "//form[@method='post']/ul/li";
			waitingAsserter.assertTextPresentInElement("Authentication failed", messageErrorXpath);

			// 8. Verify that the email field value still contains "test@liferay.com".
			waitingAsserter.assertTextPresentInElementValue("test@liferay.com", emailFieldXpath);

			// 9. Verify that the password field value is empty.
			ExpectedCondition<Boolean> passwordValueEmptyCondition = ExpectedConditions.attributeToBe(By.xpath(
						passwordFieldXpath), "value", "");
			waitingAsserter.assertTrue(passwordValueEmptyCondition);
		}

		// 10. Enter "test" into the *Password* feild.
		browserDriver.sendKeysToElement(passwordFieldXpath, "test");

		// 11. Click the *Sign In* button.
		browserDriver.clickElement(signInButtonXpath);

		// 12. Verify that the 'Sign In' was successful.
		waitingAsserter.assertTextPresentInElement("You are signed in",
			"//div[contains(@class,'liferay-faces-bridge-body')]");

		//J-
		// 13. Close the browser.
		// 14. Reopen the browser.
		//J+

		// TECHNICAL NOTE: BrowserDriver removes all cookies on closing, so simulate the browser closing by removing all
		// cookies that do not have an expiry (non-persistent cookies). For more details, see here:
		// https://stackoverflow.com/questions/3869821/how-do-i-create-a-persistent-vs-a-non-persistent-cookie.
		Set<Cookie> browserCookies = browserDriver.getBrowserCookies();
		WebDriver webDriver = browserDriver.getWebDriver();

		for (Cookie cookie : browserCookies) {

			if (cookie.getExpiry() == null) {
				webDriver.manage().deleteCookie(cookie);
			}
		}

		// 15. Navigate the browser to the portal page that contains the jsf-sign-in portlet.
		browserDriver.navigateWindowTo(jsfLoginPageURL);

		// 16. If you are testing the "Remember Me" feature then,...
		if (testRememberMe) {

			// ...verify that you are still signed in.
			waitingAsserter.assertTextPresentInElement("You are signed in",
				"//div[contains(@class,'liferay-faces-bridge-body')]");
		}

		// Otherwise,...
		else {

			// ...verify that you are no longer signed in.
			waitingAsserter = getWaitingAsserter();
			waitingAsserter.assertTextNotPresentInElement("You are signed in",
				"//div[contains(@class,'liferay-faces-bridge-body')]", false);
		}
	}

	@Override
	protected void doSetUp() {
		// Avoid signing in in the initial setup.
	}
}
