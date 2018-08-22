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
package com.liferay.faces.portal.test.showcase.inputrichtext;

import org.junit.Test;

import com.liferay.faces.test.selenium.browser.BrowserDriver;
import com.liferay.faces.test.selenium.browser.WaitingAsserter;


/**
 * @author  Kyle Stiemann
 */
public class InputRichTextDOMEventsTester extends InputRichTextTester {

	@Test
	public void runInputRichTextDOMEventsTest() {

		// 1. Navigate to the "inputRichText" "Dom Events" use case.
		BrowserDriver browserDriver = getBrowserDriver();
		navigateToUseCase(browserDriver, "inputRichText", "dom-events");

		// 2. Enter "Hello world!" into the rich text editor.
		browserDriver.switchToFrame(CK_EDITOR_IFRAME_XPATH);

		String text = "Hello world!";
		browserDriver.sendKeysToElement(BODY_XPATH, text);
		browserDriver.getWebDriver().switchTo().parentFrame();

		// 3. Verify that the feedback message shows "onfocus".
		WaitingAsserter waitingAsserter = getWaitingAsserter();
		String feedbackXpath = "//div[contains(@id,':feedback')]";
		waitingAsserter.assertTextPresentInElement("onfocus", feedbackXpath);

		// 4. Click the *Submit* button.
		browserDriver.getWebDriver().switchTo().parentFrame();
		submitRichText(browserDriver, submitButton1Xpath, 1, text);

		// 5. Verify that the feedback message shows "onfocus onchange onblur".
		waitingAsserter.assertTextPresentInElement("onfocus onchange onblur", feedbackXpath);

		// 6. Verify that the model value contains "Hello world!".
		waitingAsserter.assertTextPresentInElement(text, modelValue1Xpath);

		// 7. Click inside the rich text editor.
		browserDriver.switchToFrame(CK_EDITOR_IFRAME_XPATH);
		browserDriver.waitForElementEnabled(BODY_XPATH);
		browserDriver.clickElement(BODY_XPATH);
		browserDriver.getWebDriver().switchTo().parentFrame();

		// 8. Verify that the feedback message shows "onfocus onchange onblur onfocus".
		waitingAsserter.assertTextPresentInElement("onfocus onchange onblur onfocus", feedbackXpath);

		// 9. Click the *Submit* button.
		browserDriver.clickElement(submitButton1Xpath);

		// 10. Verify that the feedback message shows "onfocus onchange onblur onfocus".
		waitingAsserter.assertTextPresentInElement("onfocus onchange onblur onfocus onblur", feedbackXpath);
	}
}
