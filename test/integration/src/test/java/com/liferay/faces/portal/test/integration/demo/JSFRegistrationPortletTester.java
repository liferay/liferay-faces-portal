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

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;

import org.junit.runners.MethodSorters;

import org.openqa.selenium.WebElement;

import com.liferay.faces.portal.test.integration.PortalTestUtil;
import com.liferay.faces.test.selenium.browser.BrowserDriver;
import com.liferay.faces.test.selenium.browser.BrowserDriverManagingTesterBase;
import com.liferay.faces.test.selenium.browser.TestUtil;
import com.liferay.faces.test.selenium.browser.WaitingAsserter;


/**
 * @author  Kyle Stiemann
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JSFRegistrationPortletTester extends BrowserDriverManagingTesterBase {

	// Private Constants
	private static final String CAPTCHA_INPUT_XPATH = "//input[contains(@id,'captchaText')]";
	private static final String CONFIRM_PASSWORD_FIELD = "//input[contains(@id,':password2')]";
	private static final String EMAIL_ADDRESS_FIELD_XPATH = "//input[contains(@id,':emailAddress')]";
	private static final String MESSAGES_XPATH = "//table[contains(@class,'alloy-messages')]//td";
	private static final String PASSWORD_FIELD_XPATH = "//input[contains(@id,':password1')]";
	private static final String SCREEN_NAME_FIELD_XPATH = "//input[contains(@id,':screenName')]";
	private static final String SUBMIT_BUTTON_XPATH = "//button[contains(text(),'Submit')]";
	private static final String THANK_YOU_FOR_REGISTERING_MESSAGE_XPATH =
		"//table[contains(@class,'alloy-messages')]//td[contains(text(),'Thanks you for registering')]";

	// Private Static Data Members
	private static boolean isStateReset = false;

	@Test
	public void runJSFRegistrationPortletTest_A_RequiredValues() {

		// 1. Navigate the browser to the portal page that contains the jsf-sign-in portlet.
		String jsfLoginPageURL = PortalTestUtil.getGuestPageURL("jsf-sign-in");
		BrowserDriver browserDriver = getBrowserDriver();
		browserDriver.navigateWindowTo(jsfLoginPageURL);

		String createUserLinkXpath = "//a/span[contains(text(),'Create Account')]";
		browserDriver.waitForElementEnabled(createUserLinkXpath);

		// 2. Click the *Create User* link.
		browserDriver.clickElement(createUserLinkXpath);

		// 3. Click the *Submit* button.
		browserDriver.waitForElementEnabled(SUBMIT_BUTTON_XPATH);
		browserDriver.clickElement(SUBMIT_BUTTON_XPATH);

		// 4. Verify that the "Thank you for registering" message did not appear and that the *Password* field
		// indicates that an error has occurred.
		WaitingAsserter waitingAsserter = getWaitingAsserter();
		waitingAsserter.assertElementNotPresent(THANK_YOU_FOR_REGISTERING_MESSAGE_XPATH);
		waitingAsserter.assertElementDisplayed(PASSWORD_FIELD_XPATH + "/parent::*[contains(@class,'error')]");
	}

	@Test
	public void runJSFRegistrationPortletTest_B_Captcha() {

		// 1. Enter "registrant" in the *First Name* field
		BrowserDriver browserDriver = getBrowserDriver();
		browserDriver.sendKeysToElement("//input[contains(@id,':firstName')]", "registrant");

		// 2. Enter "registrant" in the *Middle Name* field
		browserDriver.sendKeysToElement("//input[contains(@id,':middleName')]", "registrant");

		// 3. Enter "registrant" in the *Last Name* field
		browserDriver.sendKeysToElement("//input[contains(@id,':lastName')]", "registrant");

		// 4. Enter "registrant@liferay.com" in the *Email Address* field
		browserDriver.sendKeysToElement(EMAIL_ADDRESS_FIELD_XPATH, "registrant@liferay.com");

		// 5. Enter "registrant" in the *Screen Name* field
		browserDriver.sendKeysToElement(SCREEN_NAME_FIELD_XPATH, "registrant");

		// 6. Enter "Example Company Inc." in the *Company Name* field
		browserDriver.sendKeysToElement("//input[contains(@id,':companyName')]", "Example Company Inc.");

		// 7. Enter "(555) 555-5555" in the *Mobile Phone* field
		browserDriver.sendKeysToElement("//input[contains(@id,':mobilePhone')]", "(555) 555-5555");

		// 8. Enter "registrant" in the *Password* field.
		browserDriver.sendKeysToElement(PASSWORD_FIELD_XPATH, "registrant");

		// 9. Enter "registrant" in the *Confirm Password* field.
		browserDriver.sendKeysToElement(CONFIRM_PASSWORD_FIELD, "registrant");

		// 10. Click the *Submit* button.
		browserDriver.clickElement(SUBMIT_BUTTON_XPATH);

		// 11. Verify that the "Thank you for registering" message did not appear and that the *Text Verification*
		// field indicates that an error has occurred because the captcha is required.
		WaitingAsserter waitingAsserter = getWaitingAsserter();
		waitingAsserter.assertElementNotPresent(THANK_YOU_FOR_REGISTERING_MESSAGE_XPATH);
		waitingAsserter.assertElementDisplayed("//*[contains(@class,'error')]" + CAPTCHA_INPUT_XPATH);

		// 12. Enter "registrant" in the *Password* field.
		browserDriver.sendKeysToElement(PASSWORD_FIELD_XPATH, "registrant");

		// 13. Enter "different registrant" in the *Confirm Password* field.
		browserDriver.sendKeysToElement(CONFIRM_PASSWORD_FIELD, "registrant");

		// 14. Enter an incorrect captcha value into the *Text Verification* field.
		String correctCaptchaValue = getCorrectCaptchaValue(browserDriver);
		browserDriver.sendKeysToElement(CAPTCHA_INPUT_XPATH, correctCaptchaValue + "1234");

		// 15. Click the *Submit* button.
		browserDriver.clickElement(SUBMIT_BUTTON_XPATH);

		// 16. Verify that the "Thank you for registering" message did not appear and that the *Text Verification*
		// field indicates that an error has occurred because the captcha is invalid.
		waitingAsserter.assertElementNotPresent(THANK_YOU_FOR_REGISTERING_MESSAGE_XPATH);
		waitingAsserter.assertElementDisplayed("//*[contains(@class,'error')]" + CAPTCHA_INPUT_XPATH);
	}

	@Test
	public void runJSFRegistrationPortletTest_C_PasswordValidation() {

		// 1. Enter "registrant" in the *Password* field.
		BrowserDriver browserDriver = getBrowserDriver();
		browserDriver.sendKeysToElement(PASSWORD_FIELD_XPATH, "registrant");

		// 2. Enter "different registrant" in the *Confirm Password* field.
		browserDriver.sendKeysToElement(CONFIRM_PASSWORD_FIELD, "different registrant");

		// 3. Enter the correct captcha value into the *Text Verification* field.
		String correctCaptchaValue = getCorrectCaptchaValue(browserDriver);
		browserDriver.sendKeysToElement(CAPTCHA_INPUT_XPATH, correctCaptchaValue);

		// 4. Click the *Submit* button.
		browserDriver.clickElement(SUBMIT_BUTTON_XPATH);

		// 5. Verify that the "Thank you for registering" message did not appear and that the "The passwords you
		// entered do not match. Please re-enter your password." error message appears.
		WaitingAsserter waitingAsserter = getWaitingAsserter();
		waitingAsserter.assertElementNotPresent(THANK_YOU_FOR_REGISTERING_MESSAGE_XPATH);
		waitingAsserter.assertTextPresentInElement("The passwords you entered do not match.", MESSAGES_XPATH);
	}

	@Test
	public void runJSFRegistrationPortletTest_D_DuplicateUser() {

		// 1. Change the *Email Address* field from "registrant@liferay.com" to "john.adams@liferay.com".
		BrowserDriver browserDriver = getBrowserDriver();
		browserDriver.clearElement(EMAIL_ADDRESS_FIELD_XPATH);
		browserDriver.sendKeysToElement(EMAIL_ADDRESS_FIELD_XPATH, "john.adams@liferay.com");

		// 2. Enter "registrant" in the *Password* field.
		browserDriver.sendKeysToElement(PASSWORD_FIELD_XPATH, "registrant");

		// 3. Enter "different registrant" in the *Confirm Password* field.
		browserDriver.sendKeysToElement(CONFIRM_PASSWORD_FIELD, "registrant");

		// 4. Enter the correct captcha value into the *Text Verification* field.
		String correctCaptchaValue = getCorrectCaptchaValue(browserDriver);
		browserDriver.sendKeysToElement(CAPTCHA_INPUT_XPATH, correctCaptchaValue);

		// 5. Click the *Submit* button.
		browserDriver.clickElement(SUBMIT_BUTTON_XPATH);

		// 6. Verify that the "Thank you for registering" message did not appear and that the "The email address you
		// requested is already taken." error message appears.
		WaitingAsserter waitingAsserter = getWaitingAsserter();
		waitingAsserter.assertElementNotPresent(THANK_YOU_FOR_REGISTERING_MESSAGE_XPATH);
		waitingAsserter.assertTextPresentInElement("The email address you requested is already taken.", MESSAGES_XPATH);

		// 7. Change the *Email Address* field from "john.adams@liferay.com" to "registrant@liferay.com".
		browserDriver.clearElement(EMAIL_ADDRESS_FIELD_XPATH);
		browserDriver.sendKeysToElement(EMAIL_ADDRESS_FIELD_XPATH, "registrant@liferay.com");

		// 8. Change the *Email Address* field from "registrant" to "john.adams".
		browserDriver.clearElement(SCREEN_NAME_FIELD_XPATH);
		browserDriver.sendKeysToElement(SCREEN_NAME_FIELD_XPATH, "john.adams");

		// 9. Enter "registrant" in the *Password* field.
		browserDriver.sendKeysToElement(PASSWORD_FIELD_XPATH, "registrant");

		// 10. Enter "different registrant" in the *Confirm Password* field.
		browserDriver.sendKeysToElement(CONFIRM_PASSWORD_FIELD, "registrant");

		// 11. Enter the correct captcha value into the *Text Verification* field.
		correctCaptchaValue = getCorrectCaptchaValue(browserDriver);
		browserDriver.sendKeysToElement(CAPTCHA_INPUT_XPATH, correctCaptchaValue);

		// 12. Click the *Submit* button.
		browserDriver.clickElement(SUBMIT_BUTTON_XPATH);

		// 13. Verify that the "The screen name you requested is already taken." error message appears.
		waitingAsserter.assertElementNotPresent(THANK_YOU_FOR_REGISTERING_MESSAGE_XPATH);
		waitingAsserter.assertTextPresentInElement("The screen name you requested is already taken.", MESSAGES_XPATH);

		// 14. Change the *Email Address* field from "john.adams" to "registrant".
		browserDriver.clearElement(SCREEN_NAME_FIELD_XPATH);
		browserDriver.sendKeysToElement(SCREEN_NAME_FIELD_XPATH, "registrant");
	}

	@Test
	public void runJSFRegistrationPortletTest_E_CreateUser() {

		BrowserDriver browserDriver = getBrowserDriver();

		try {

			// 1. Enter "registrant" in the *Password* field.
			browserDriver.sendKeysToElement(PASSWORD_FIELD_XPATH, "registrant");

			// 2. Enter "different registrant" in the *Confirm Password* field.
			browserDriver.sendKeysToElement(CONFIRM_PASSWORD_FIELD, "registrant");

			// 3. Enter the correct captcha value into the *Text Verification* field.
			String correctCaptchaValue = getCorrectCaptchaValue(browserDriver);
			browserDriver.sendKeysToElement(CAPTCHA_INPUT_XPATH, correctCaptchaValue);

			// 4. Click the *Submit* button.
			browserDriver.clickElement(SUBMIT_BUTTON_XPATH);
		}
		finally {

			// Ensure that the test always signs in before completeing (to prepare for other tests).
			TestUtil.signIn(browserDriver);
		}

		// 5. Navigate to the PrimeFaces Users portlet.
		browserDriver.navigateWindowTo(PrimeFacesUsersPortletTester.getURL());

		String screenNameCellXpath = "//span[contains(@id,':screenNameCell')]";
		browserDriver.waitForElementDisplayed(screenNameCellXpath);

		// 6. Type "registrant" into the *Screen Name* filter.
		browserDriver.clearElement(screenNameCellXpath);
		browserDriver.sendKeysToElement("//input[contains(@id,'users:screenName:filter')]", "registrant");
		browserDriver.waitForElementNotDisplayed("(" + screenNameCellXpath + ")[2]");
		browserDriver.waitForElementDisplayed(screenNameCellXpath);

		// 7. Verify that the new registrant user appears.
		WaitingAsserter waitingAsserter = getWaitingAsserter();
		waitingAsserter.assertTextPresentInElement("registrant", "//span[contains(@id,':lastNameCell')]");
		waitingAsserter.assertTextPresentInElement("registrant", "//span[contains(@id,':firstNameCell')]");
		waitingAsserter.assertTextPresentInElement("registrant@liferay.com", "//td//a[contains(@id,':email')]");
	}

	@Before
	public void setUpJSFRegistrationPortletTester() {

		if (!isStateReset) {

			BrowserDriver browserDriver = getBrowserDriver();
			PrimeFacesUsersPortletTester.resetTestUsers(browserDriver);
			PortalTestUtil.signOut(browserDriver);
			isStateReset = true;
		}
	}

	private String getCorrectCaptchaValue(BrowserDriver browserDriver) {

		String correctCaptchaValueXpath = "//button[contains(@id,':correctCaptchaValue')]";
		browserDriver.clickElementAndWaitForRerender(correctCaptchaValueXpath);

		WebElement correctCaptchaValueElement = browserDriver.findElementByXpath(correctCaptchaValueXpath);

		return correctCaptchaValueElement.getText();
	}
}
