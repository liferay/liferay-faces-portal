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

import org.junit.Test;

import com.liferay.faces.portal.test.integration.PortalTestUtil;
import com.liferay.faces.test.selenium.Browser;
import com.liferay.faces.test.selenium.assertion.SeleniumAssert;


/**
 * @author  Vernon Singleton
 * @author  Philip White
 */
public class JsfLoginPortletTester {

	@Test
	public void runJsfLoginPortletTester() {

		// Navigate the browser to the portal page that contains the jsf-sign-in portlet.
		Browser browser = Browser.getInstance();
		String jsfLoginPageURL = PortalTestUtil.getGuestPageURL("jsf-sign-in");
		browser.get(jsfLoginPageURL);

		// Wait for a visible element to start the test.
		String emailFieldXpath = "//input[contains(@id,':handle')]";
		browser.waitForElementVisible(emailFieldXpath);
		SeleniumAssert.assertElementVisible(browser, emailFieldXpath);

		// Clear the email field text box since Liferay Portal will sometimes pre-populate the text box with a value.
		browser.clear(emailFieldXpath);

		// Test an invalid 'Sign In' by submitting an invalid password.
		browser.sendKeys(emailFieldXpath, "test@liferay.com");

		String passwordFieldXpath = "//input[contains(@id,':password')]";
		browser.sendKeys(passwordFieldXpath, "invalid_password");

		String signInButtonXpath = "//input[@type='submit' and @value='Sign In']";
		browser.clickAndWaitForAjaxRerender(signInButtonXpath);

		// Verify that the error message is visible.
		String messageErrorXpath = "//form[@method='post']/ul/li";
		browser.waitForElementTextVisible(messageErrorXpath, "Authentication failed");
		SeleniumAssert.assertElementTextVisible(browser, messageErrorXpath, "Authentication failed");

		// Test a valid 'Sign In'. Verify that the email field value still contains "test@liferay.com" since h:inputText
		// should retain its text box value even if form submission fails validation.
		SeleniumAssert.assertElementValue(browser, emailFieldXpath, "test@liferay.com");

		// Verify that the password field value contains an empty value since h:inputSecret should not retain its text
		// box value when form submission fails validation.
		SeleniumAssert.assertElementValue(browser, passwordFieldXpath, "");

		// Enter a valid password and sign in.
		browser.sendKeys(passwordFieldXpath, "test");
		browser.click(signInButtonXpath);

		// Verify that the 'Sign In' was successful.
		String portletBodyXpath = "//div[contains(text(),'You are signed in as')]";
		browser.waitForElementTextVisible(portletBodyXpath, "You are signed in");
		SeleniumAssert.assertElementTextVisible(browser, portletBodyXpath, "You are signed in");
	}

}
