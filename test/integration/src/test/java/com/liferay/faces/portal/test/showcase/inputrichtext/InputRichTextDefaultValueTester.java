/**
 * Copyright (c) 2000-2019 Liferay, Inc. All rights reserved.
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

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.liferay.faces.test.selenium.browser.BrowserDriver;
import com.liferay.faces.test.selenium.browser.WaitingAsserter;


/**
 * @author  Kyle Stiemann
 */
public class InputRichTextDefaultValueTester extends InputRichTextTester {

	@Test
	public void runInputRichTextGeneralTest() {

		// 1. Navigate to the "inputRichText" "Default Value" use case.
		BrowserDriver browserDriver = getBrowserDriver();
		navigateToUseCase(browserDriver, "inputRichText", "default-value");

		// 2. Verify that "<p>This is some <strong>bold</strong> text<br />\nand this is some <em>italic</em> text.</p>"
		// appears in the *Model Value*.
		WaitingAsserter waitingAsserter = getWaitingAsserter();
		waitingAsserter.assertTextPresentInElement(
			"<p>This is some <strong>bold</strong> text<br />\nand this is some <em>italic</em> text.</p>",
			modelValue1Xpath);

		// 3. Before the text "ld text and this is some italic text.", type "ld o".
		browserDriver.switchToFrame(CK_EDITOR_IFRAME_XPATH);

		Actions insertText = browserDriver.createActions(BODY_XPATH);
		WebElement body = browserDriver.findElementByXpath(BODY_XPATH);
		insertText.click(body);

		for (int i = 0; i < "ld text and this is some italic text.".length(); i++) {
			insertText.sendKeys(Keys.LEFT);
		}

		insertText.perform();
		browserDriver.sendKeysToElement(BODY_XPATH, "ld o");

		// 4. Click the *Submit* button.
		String expectedText =
			"<p>This is some <strong>bold old</strong> text<br />\nand this is some <em>italic</em> text.</p>";
		submitRichText(browserDriver, submitButton1Xpath, 1, expectedText);

		//J-
		// 5. Verify that
		// "<p>This is some <strong>bold old</strong> text<br />\nand this is some <em>italic</em> text.</p>" appears in
		// the *Model Value*.
		//J+
		waitingAsserter.assertTextPresentInElement(expectedText, modelValue1Xpath);
	}
}
