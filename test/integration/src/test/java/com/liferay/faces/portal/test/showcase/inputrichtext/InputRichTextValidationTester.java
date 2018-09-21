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

import java.util.List;

import org.junit.Test;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

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

	public void runInputRichTextPlainTextCharCountTest(BrowserDriver browserDriver, int editorKeyToSelect) {

		// 1. Select the appropriate editor key from the dropdown.
		resetPage(browserDriver, editorKeyToSelect);

		// 2. Enter "123456789" into the rich text editor.
		browserDriver.switchToFrame(CK_EDITOR_IFRAME_XPATH);

		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 1; i < MIN_PLAIN_TEXT_CHARS; i++) {
			stringBuilder.append(i % 10);
		}

		browserDriver.sendKeysToElement(BODY_XPATH, stringBuilder.toString());

		// 3. Press Ctrl + b (Cmd + b on Mac) to bold the first character.
		selectTextAndSendKeys(browserDriver, stringBuilder.toString(), Integer.toString(MIN_PLAIN_TEXT_CHARS - 1),
			Keys.CONTROL, Keys.COMMAND, "b");

		// 4. Click the *Submit* button.
		browserDriver.getWebDriver().switchTo().parentFrame();
		browserDriver.clickElement(submitButton1Xpath);

		// 5. Verify that the "Length is less than allowable minimum of '10'" feedback message appears.
		WaitingAsserter waitingAsserter = getWaitingAsserter();
		waitingAsserter.assertElementDisplayed(LESS_THAN_MIN_CHARS_ERROR_XPATH);

		// 6. Verify that nothing appears in the model value since the submitted value is invalid.
		waitingAsserter.assertTextNotPresentInElement(stringBuilder.toString(), modelValue1Xpath, false);

		//J-
		// 7. Navigate to the "inputRichText" "Validation" use case to reset the rich text value.
		// 8. Select the appropriate editor key from the dropdown.
		//J+
		resetPage(browserDriver, editorKeyToSelect);

		// 9. Enter "123456789a" into the rich text editor.
		browserDriver.switchToFrame(CK_EDITOR_IFRAME_XPATH);
		stringBuilder.append("a");
		browserDriver.sendKeysToElement(BODY_XPATH, stringBuilder.toString());

		// 10. Click the *Submit* button.
		submitRichText(browserDriver, submitButton1Xpath, 1, stringBuilder.toString());

		// 11. Verify that the "Length is less than allowable minimum of '10'" feedback message does not appear.
		waitingAsserter.assertElementNotDisplayed(LESS_THAN_MIN_CHARS_ERROR_XPATH);

		// 12. Verify that "123456789a" appears in the model value.
		waitingAsserter.assertTextPresentInElement(stringBuilder.toString(), modelValue1Xpath);

		//J-
		// 13. Navigate to the "inputRichText" "Validation" use case to reset the rich text value.
		// 14. Select the appropriate editor key from the dropdown.
		//J+
		resetPage(browserDriver, editorKeyToSelect);

		// 15. Enter the maximum number of characters (144) into the rich text editor.
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

		// 16. Click the *Submit* button.
		submitRichText(browserDriver, submitButton1Xpath, 1, stringBuilder.toString());

		// 17. Verify that the "Length is greater than allowable maximum of '144'" feedback message does not appear.
		waitingAsserter.assertElementNotDisplayed(GREATER_THAN_MAX_CHARS_ERROR_XPATH);

		// 18. Verify that the 144 submitted characters appear in the model value.
		waitingAsserter.assertTextPresentInElement(stringBuilder.toString(), modelValue1Xpath);

		//J-
		// 19. Select the first character in the rich text editor.
		// 20. Press Ctrl + b (Cmd + b on Mac) to bold the first character.
		//J+
		browserDriver.switchToFrame(CK_EDITOR_IFRAME_XPATH);

		selectTextAndSendKeys(browserDriver, stringBuilder.toString(), "b", Keys.CONTROL, Keys.COMMAND, "b");

		// 21. Click the *Submit* button.
		browserDriver.getWebDriver().switchTo().parentFrame();

		String expectedBoldText = getExpectedBoldText(browserDriver, editorKeyToSelect, "b");
		browserDriver.switchToFrame(CK_EDITOR_IFRAME_XPATH);

		String boldModelValue = stringBuilder.toString().replace("b", expectedBoldText);
		submitRichText(browserDriver, submitButton1Xpath, 1, boldModelValue);

		// 22. Verify that the 144 submitted characters appear in the model value with the first character surrounded by
		// bold tags.
		waitingAsserter.assertTextPresentInElement(boldModelValue, modelValue1Xpath);

		// 23. Verify that the "Length is greater than allowable maximum of '144'" feedback message does not appear.
		waitingAsserter.assertElementNotDisplayed(GREATER_THAN_MAX_CHARS_ERROR_XPATH);

		//J-
		// 24. Navigate to the "inputRichText" "Validation" use case to reset the rich text value.
		// 25. Select the appropriate editor key from the dropdown.
		//J+
		resetPage(browserDriver, editorKeyToSelect);

		// 26. Enter the maximum number of characters (144) plus one "a" into the rich text editor.
		browserDriver.switchToFrame(CK_EDITOR_IFRAME_XPATH);
		stringBuilder.append("a");
		browserDriver.sendKeysToElement(BODY_XPATH, stringBuilder.toString());

		// 27. Click the *Submit* button.
		submitRichText(browserDriver, submitButton1Xpath, 1, stringBuilder.toString());

		// 28. Verify that the "Length is greater than allowable maximum of '144'" feedback message appears.
		waitingAsserter.assertElementDisplayed(GREATER_THAN_MAX_CHARS_ERROR_XPATH);

		// 29. Verify that nothing appears in the model value since the submitted value is invalid.
		waitingAsserter.assertTextNotPresentInElement(stringBuilder.toString(), modelValue1Xpath, false);
	}

	@Test
	public void runInputRichTextValidationTest() {

		BrowserDriver browserDriver = getBrowserDriver();
		navigateToUseCase(browserDriver, "inputRichText", "validation");

		Select editorKeySelect = getEditorKeySelect(browserDriver);
		List<WebElement> options = editorKeySelect.getOptions();
		int editorKeyCount = options.size();

		for (int i = 0; i < editorKeyCount; i++) {
			runInputRichTextPlainTextCharCountTest(browserDriver, i);
		}
	}

	private Select getEditorKeySelect(BrowserDriver browserDriver) {

		WebElement editorKeyWebElement = browserDriver.findElementByXpath("//select[contains(@id,':editorKey')]");

		return new Select(editorKeyWebElement);
	}

	private String getExpectedBoldText(BrowserDriver browserDriver, int editorKeyToSelect, String text) {

		Select editorKeySelect = getEditorKeySelect(browserDriver);
		List<WebElement> options = editorKeySelect.getOptions();
		WebElement editorKeyOption = options.get(editorKeyToSelect);
		String editorKey = editorKeyOption.getText();
		String boldText;

		if (editorKey.endsWith("bbcode")) {
			boldText = InputRichTextBBCodeTester.BOLD_OPEN + text + InputRichTextBBCodeTester.BOLD_CLOSE;
		}
		else if (editorKey.endsWith("creole")) {
			boldText = InputRichTextCreoleTester.BOLD_OPEN + text + InputRichTextCreoleTester.BOLD_CLOSE;
		}
		else {
			boldText = BOLD_OPEN + text + BOLD_CLOSE;
		}

		return boldText;
	}

	private void resetPage(BrowserDriver browserDriver, int editorKeyToSelect) {

		navigateToUseCase(browserDriver, "inputRichText", "validation");
		selectEditorKey(browserDriver, editorKeyToSelect);
		browserDriver.waitForElementDisplayed(CK_EDITOR_IFRAME_XPATH);
	}

	private void selectEditorKey(BrowserDriver browserDriver, int editorKeyToSelect) {

		Select editorKeySelect = getEditorKeySelect(browserDriver);
		editorKeySelect.selectByIndex(editorKeyToSelect);
	}
}
