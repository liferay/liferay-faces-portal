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
package com.liferay.faces.portal.test.integration.demo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.liferay.faces.portal.test.integration.PortalTestUtil;
import com.liferay.faces.test.selenium.IntegrationTesterBase;
import com.liferay.faces.test.selenium.TestUtil;
import com.liferay.faces.test.selenium.browser.BrowserDriver;
import com.liferay.faces.test.selenium.browser.BrowserStateAsserter;


/**
 * @author  Vernon Singleton
 * @author  Philip White
 */
public class JsfLoginPortletTester extends IntegrationTesterBase {

	@Test
	public void runJsfLoginPortletTester() {

		// Navigate the browser to the portal page that contains the jsf-sign-in portlet.
		BrowserDriver browserDriver = getBrowserDriver();
		String jsfLoginPageURL = PortalTestUtil.getGuestPageURL("jsf-sign-in");
		browserDriver.navigateWindowTo(jsfLoginPageURL);

		// Wait for a displayed element to start the test.
		String emailFieldXpath = "//input[contains(@id,':handle')]";
		browserDriver.waitForElementDisplayed(emailFieldXpath);

		BrowserStateAsserter browserStateAsserter = getBrowserStateAsserter();
		browserStateAsserter.assertElementDisplayed(emailFieldXpath);

		// Clear the email field text box since Liferay Portal will sometimes pre-populate the text box with a value.
		browserDriver.clearElement(emailFieldXpath);

		// Test an invalid 'Sign In' by submitting an invalid password.
		browserDriver.sendKeysToElement(emailFieldXpath, "test@liferay.com");

		String passwordFieldXpath = "//input[contains(@id,':password')]";
		browserDriver.sendKeysToElement(passwordFieldXpath, "invalid_password");

		String signInButtonXpath = "//input[@type='submit' and @value='Sign In']";
		browserDriver.clickElementAndWaitForRerender(signInButtonXpath);

		// Verify that the error message is displayed.
		String messageErrorXpath = "//form[@method='post']/ul/li";
		browserStateAsserter.assertTextPresentInElement("Authentication failed", messageErrorXpath);

		// Test a valid 'Sign In'. Verify that the email field value still contains "test@liferay.com" since h:inputText
		// should retain its text box value even if form submission fails validation.
		browserStateAsserter.assertTextPresentInElementValue("test@liferay.com", emailFieldXpath);

		// Verify that the password field value contains an empty value since h:inputSecret should not retain its text
		// box value when form submission fails validation.
		ExpectedCondition<Boolean> valueEmpty = ExpectedConditions.attributeToBe(By.xpath(passwordFieldXpath), "value",
				"");
		browserStateAsserter.assertTrue(valueEmpty);

		// Enter a valid password and sign in.
		browserDriver.sendKeysToElement(passwordFieldXpath, "test");
		browserDriver.clickElement(signInButtonXpath);

		// Verify that the 'Sign In' was successful.
		String portletBodyXpath = "//div[contains(text(),'You are signed in as')]";
		browserStateAsserter.assertTextPresentInElement("You are signed in", portletBodyXpath);
	}

	@After
	public void signIn() {

		// Sign out and in again in case any tests run after this one expect to be signed in.
		signOut();
		signIn(getBrowserDriver());
	}

	@Before
	public void signOut() {

		BrowserDriver browserDriver = getBrowserDriver();
		browserDriver.navigateWindowTo(TestUtil.DEFAULT_BASE_URL + "/c/portal/logout");
		browserDriver.clearBrowserCookies();
	}

	@Override
	protected void doSetUp() {
		// Avoid signing in in the initial setup.
	}
}
