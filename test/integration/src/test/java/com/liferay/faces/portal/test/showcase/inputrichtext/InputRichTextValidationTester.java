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

import org.openqa.selenium.Keys;

import com.liferay.faces.test.selenium.browser.BrowserDriver;
import com.liferay.faces.test.selenium.browser.WaitingAsserter;


/**
 * @author  Kyle Stiemann
 */
public class InputRichTextValidationTester extends InputRichTextTester {

	// Private Constants
	private static final int MIN_PLAIN_TEXT_CHARS = 10;
	private static final int MAX_PLAIN_TEXT_CHARS = 144;
	private static final String LESS_THAN_MIN_CHARS_ERROR_XPATH =
		"//div[(contains(@class,'field form-group has-error') or contains(@class,'field control-group error')) and contains(., concat('Validation Error: Length is less than allowable minimum of ', \"'" +
		MIN_PLAIN_TEXT_CHARS + "'\"))]";
	private static final String GREATER_THAN_MAX_CHARS_ERROR_XPATH =
		"//div[(contains(@class,'field form-group has-error') or contains(@class,'field control-group error')) and contains(., concat('Validation Error: Length is greater than allowable maximum of ', \"'" +
		MAX_PLAIN_TEXT_CHARS + "'\"))]";

	@Test
	public void runInputRichTextValidationTest() {

		// 1. Navigate to the "inputRichText" "Validation" use case.
		BrowserDriver browserDriver = getBrowserDriver();
		navigateToUseCase(browserDriver, "inputRichText", "validation");

		// 2. Enter "123456789" into the rich text editor.
		browserDriver.switchToFrame(CK_EDITOR_IFRAME_XPATH);

		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 1; i < MIN_PLAIN_TEXT_CHARS; i++) {
			stringBuilder.append(i % 10);
		}

		browserDriver.sendKeysToElement(BODY_XPATH, stringBuilder.toString());

		// 3. Click the *Submit* button.
		browserDriver.getWebDriver().switchTo().parentFrame();
		browserDriver.clickElement(submitButton1Xpath);

		// 4. Verify that the "Length is less than allowable minimum of '10'" feedback message appears.
		WaitingAsserter waitingAsserter = getWaitingAsserter();
		waitingAsserter.assertElementDisplayed(LESS_THAN_MIN_CHARS_ERROR_XPATH);

		// 5. Verify that nothing appears in the model value since the submitted value is invalid.
		waitingAsserter.assertTextNotPresentInElement(stringBuilder.toString(), modelValue1Xpath, false);

		// 6. Navigate to the "inputRichText" "Validation" use case to reset the rich text value.
		navigateToUseCase(browserDriver, "inputRichText", "validation");

		// 7. Enter "123456789a" into the rich text editor.
		browserDriver.switchToFrame(CK_EDITOR_IFRAME_XPATH);
		stringBuilder.append("a");
		browserDriver.sendKeysToElement(BODY_XPATH, stringBuilder.toString());

		// 8. Click the *Submit* button.
		submitRichText(browserDriver, submitButton1Xpath, 1, stringBuilder.toString());

		// 9. Verify that the "Length is less than allowable minimum of '10'" feedback message does not appear.
		waitingAsserter.assertElementNotDisplayed(LESS_THAN_MIN_CHARS_ERROR_XPATH);

		// 10. Verify that "123456789a" appears in the model value.
		waitingAsserter.assertTextPresentInElement(stringBuilder.toString(), modelValue1Xpath);

		// 11. Navigate to the "inputRichText" "Validation" use case to reset the rich text value.
		navigateToUseCase(browserDriver, "inputRichText", "validation");

		// 12. Enter the maximum number of characters (144) into the rich text editor.
		browserDriver.switchToFrame(CK_EDITOR_IFRAME_XPATH);
		stringBuilder.setLength(0);

		for (int i = 0; i < MAX_PLAIN_TEXT_CHARS; i++) {

			if (i == 0) {
				stringBuilder.append("b");
			}
			else {
				stringBuilder.append(i % 10);
			}
		}

		browserDriver.sendKeysToElement(BODY_XPATH, stringBuilder.toString());

		// 13. Click the *Submit* button.
		submitRichText(browserDriver, submitButton1Xpath, 1, stringBuilder.toString());

		// 14. Verify that the "Length is greater than allowable maximum of '144'" feedback message does not appear.
		waitingAsserter.assertElementNotDisplayed(GREATER_THAN_MAX_CHARS_ERROR_XPATH);

		// 15. Verify that the 144 submitted characters appear in the model value.
		waitingAsserter.assertTextPresentInElement(stringBuilder.toString(), modelValue1Xpath);

		//J-
		// 16. Select the first character in the rich text editor.
		// 17. Press Ctrl + b (Cmd + b on Mac) to bold the first character.
		//J+
		browserDriver.switchToFrame(CK_EDITOR_IFRAME_XPATH);

		selectTextAndSendKeys(browserDriver, stringBuilder.toString(), "b", Keys.CONTROL, Keys.COMMAND, "b");

		// 18. Click the *Submit* button.
		String boldModelValue = stringBuilder.toString().replace("b", "<strong>b</strong>");
		submitRichText(browserDriver, submitButton1Xpath, 1, boldModelValue);

		// 19. Verify that the 144 submitted characters appear in the model value with the first character surrounded by
		// <strong> tags.
		waitingAsserter.assertTextPresentInElement(boldModelValue, modelValue1Xpath);

		// 20. Verify that the "Length is greater than allowable maximum of '144'" feedback message does not appear.
		waitingAsserter.assertElementNotDisplayed(GREATER_THAN_MAX_CHARS_ERROR_XPATH);

		// 21. Navigate to the "inputRichText" "Validation" use case to reset the rich text value.
		navigateToUseCase(browserDriver, "inputRichText", "validation");

		// 22. Enter the maximum number of characters (144) plus one "a" into the rich text editor.
		browserDriver.switchToFrame(CK_EDITOR_IFRAME_XPATH);
		stringBuilder.append("a");
		browserDriver.sendKeysToElement(BODY_XPATH, stringBuilder.toString());

		// 23. Click the *Submit* button.
		submitRichText(browserDriver, submitButton1Xpath, 1, stringBuilder.toString());

		// 24. Verify that the "Length is greater than allowable maximum of '144'" feedback message appears.
		waitingAsserter.assertElementDisplayed(GREATER_THAN_MAX_CHARS_ERROR_XPATH);

		// 25. Verify that nothing appears in the model value since the submitted value is invalid.
		waitingAsserter.assertTextNotPresentInElement(stringBuilder.toString(), modelValue1Xpath, false);
	}
}
