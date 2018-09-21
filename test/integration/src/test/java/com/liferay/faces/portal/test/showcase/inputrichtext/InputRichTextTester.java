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
import java.util.Locale;

import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.liferay.faces.test.selenium.browser.BrowserDriver;
import com.liferay.faces.test.selenium.browser.TestUtil;
import com.liferay.faces.test.selenium.browser.WaitingAsserter;
import com.liferay.faces.test.showcase.input.InputTester;


/**
 * @author  Kyle Stiemann
 */
public abstract class InputRichTextTester extends InputTester {

	// Protected Constants
	protected static final String BODY_XPATH = "//body";
	protected static final String BOLD_OPEN = "<strong>";
	protected static final String BOLD_CLOSE = "</strong>";
	protected static final String CK_EDITOR_IFRAME_XPATH = "//div[contains(@id,':comments')]//iframe";

	// Private Constants
	private static final String INPUT_RICH_TEXT_DIV_XPATH =
		"//div[contains(@id,':comments')][@class='portal-input-rich-text']";
	private static final String RENDERED_CHECKBOX_XPATH = "//input[contains(@id,':renderedCheckbox')]";

	protected static void selectTextAndSendKeys(BrowserDriver browserDriver, String completeText, String textToSelect,
		CharSequence... keys) {

		if (!completeText.contains(textToSelect)) {
			throw new IllegalArgumentException("\"" + completeText + "\" does not contain \"" + textToSelect + "\"");
		}

		Actions boldAndItalicize = browserDriver.createActions(BODY_XPATH);
		WebElement body = browserDriver.findElementByXpath(BODY_XPATH);
		boldAndItalicize.click(body);

		for (int i = 0; i < completeText.length(); i++) {
			boldAndItalicize.sendKeys(Keys.LEFT);
		}

		int beginIndex = completeText.indexOf(textToSelect);

		for (int i = 0; i < beginIndex; i++) {
			boldAndItalicize.sendKeys(Keys.RIGHT);
		}

		boldAndItalicize.keyDown(Keys.SHIFT);

		for (int i = 0; i < textToSelect.length(); i++) {
			boldAndItalicize.sendKeys(Keys.RIGHT);
		}

		boldAndItalicize.keyUp(Keys.SHIFT);
		boldAndItalicize.perform();
		browserDriver.sendKeysToElement(BODY_XPATH, keys);
	}

	private static boolean isCheckboxDisplayedAndEnabled(BrowserDriver browserDriver, String checkboxType) {

		String checkboxXpath = "//input[contains(@id,':{0}Checkbox')]".replace("{0}", checkboxType);
		WebElement checkbox = null;
		List<WebElement> webElements = browserDriver.findElementsByXpath(checkboxXpath);

		if (!webElements.isEmpty()) {
			checkbox = webElements.get(0);
		}

		return (checkbox != null) && checkbox.isDisplayed() && checkbox.isEnabled();
	}

	public void runInputRichTextTest(String useCase, String boldOpen, String boldClose, String italicsOpen,
		String italicsClose) {
		runInputRichTextTest(useCase, boldOpen, boldClose, italicsOpen, italicsClose, submitButton1Xpath);
	}

	public void runInputRichTextTest(String useCase, String boldOpen, String boldClose, String italicsOpen,
		String italicsClose, String elementToClickXpath) {

		// 1. Navigate to the appropriate "inputRichText" use case.
		BrowserDriver browserDriver = getBrowserDriver();
		navigateToUseCase(browserDriver, "inputRichText", useCase);

		// 2. Verify that the rich text editor is visible.
		WaitingAsserter waitingAsserter = getWaitingAsserter();
		waitingAsserter.assertElementDisplayed(INPUT_RICH_TEXT_DIV_XPATH);

		if (isCheckboxDisplayedAndEnabled(browserDriver, "rendered")) {

			// 3. Click the *Rendered* checkbox.
			browserDriver.clickElement(RENDERED_CHECKBOX_XPATH);

			// 4. Verify that the rich text editor is not visible.
			waitingAsserter.assertElementNotDisplayed(INPUT_RICH_TEXT_DIV_XPATH);

			// 5. Click the *Rendered* checkbox.
			browserDriver.clickElement(RENDERED_CHECKBOX_XPATH);

			// 6. Verify that the rich text editor is visible.
			waitingAsserter.assertElementDisplayed(INPUT_RICH_TEXT_DIV_XPATH);
		}

		if (isCheckboxDisplayedAndEnabled(browserDriver, "required")) {

			// 7. Click the *Submit* button.
			browserDriver.clickElement(elementToClickXpath);

			// 8. Verify that no error messages are shown since the rich text editor is not a required field.
			waitingAsserter.assertElementNotDisplayed(valueIsRequiredError1Xpath);

			// 9. Click the *Required* checkbox.
			browserDriver.clickElement(requiredCheckbox1Xpath);

			// 10. Click the *Submit* button.
			browserDriver.clickElement(elementToClickXpath);

			// 11. Verify that a "Value is required." error message appears.
			waitingAsserter.assertElementDisplayed(valueIsRequiredError1Xpath);
		}

		// 12. Enter "Hello to the whole World!" into the rich text editor.
		browserDriver.switchToFrame(CK_EDITOR_IFRAME_XPATH);

		String text = "Hello to the whole World!";
		browserDriver.sendKeysToElement(BODY_XPATH, text);

		// 13. Click the *Submit* button.
		submitRichText(browserDriver, elementToClickXpath, 1, text);

		// 14. Verify that "Hello to the whole World!" appears in the *Model Value*.
		waitingAsserter.assertTextPresentInElement(text, modelValue1Xpath);

		// 15. Verify that the "Value is required." error message does not appear.
		waitingAsserter.assertElementNotDisplayed(valueIsRequiredError1Xpath);

		// 16. Select the text "lo to the wh" in the rich text editor.
		// 17. Press Ctrl + b (or Cmd + b) to bold the text.
		browserDriver.switchToFrame(CK_EDITOR_IFRAME_XPATH);

		String boldSelection = "lo to the wh";
		selectTextAndSendKeys(browserDriver, text, boldSelection, Keys.CONTROL, Keys.COMMAND, "b");

		// 18. Select the text "o the whole Wor" in the rich text editor.
		// 19. Press Ctrl + i (or Cmd + i) to italicize the text.
		String italicizedSelection = "o the whole Wor";
		selectTextAndSendKeys(browserDriver, text, italicizedSelection, Keys.CONTROL, Keys.COMMAND, "i");

		// 20. Click the *Submit* button.
		String expectedFormattedText = "Hel" + BOLD_OPEN + "lo t<em>o the wh</em>" + BOLD_CLOSE + "<em>ole Wor</em>ld!";
		expectedFormattedText = expectedFormattedText.replace(BOLD_OPEN, boldOpen).replace(BOLD_CLOSE, boldClose)
			.replace("<em>", italicsOpen).replace("</em>", italicsClose);
		submitRichText(browserDriver, elementToClickXpath, 1, expectedFormattedText);

		// 21. Verify that "Hel<strong>lo t<em>o the wh</em></strong><em>ole Wor</em>ld!" appears (with the appropriate
		// markup for the current use-case) in the *Model Value*.
		waitingAsserter.assertTextPresentInElement(expectedFormattedText, modelValue1Xpath);

		// 22. Verify that the "Value is required." error message does not appear.
		waitingAsserter.assertElementNotDisplayed(valueIsRequiredError1Xpath);
	}

	@Override
	protected void navigateToUseCase(BrowserDriver browserDriver, String componentPrefix, String componentName,
		String componentUseCase) {

		browserDriver.navigateWindowTo(SHOWCASE_CONTEXT_URL + "/" + componentPrefix + "/" +
			componentName.toLowerCase(Locale.ENGLISH) + "/" + componentUseCase);
		waitForShowcasePageReady(browserDriver);

		// TECHNICAL NOTE: Wait until all CKEditors are ready to be tested.
		browserDriver.waitFor(ExpectedConditions.jsReturnsValue(
				"if (CKEDITOR && CKEDITOR.instances) { return true; }"));

		//J-
		browserDriver.waitFor(ExpectedConditions.jsReturnsValue(
			"for (var ckeditorKey in CKEDITOR.instances) {" +
				"if (!CKEDITOR.instances[ckeditorKey].instanceReady) {" +
					"return null;" +
				"}" +
			"}" +
			"return true;"));
		//J+
	}

	protected final void submitRichText(BrowserDriver browserDriver, String elementToClickXpath,
		int ckeditorExampleNumber, String expectedText) {

		browserDriver.getWebDriver().switchTo().parentFrame();

		//J-
		browserDriver.executeScriptInCurrentWindow(
			"for (var ckeditorKey in CKEDITOR.instances) {" +
				"CKEDITOR.instances[ckeditorKey].updateElement();" +
			"}");
		//J+

		try {

			browserDriver.setWaitTimeOut(1);

			WebElement textarea = browserDriver.findElementByXpath("(//textarea[contains(@id,':comments_input')])[" +
					ckeditorExampleNumber + "]");

			//J-
			browserDriver.waitFor(ExpectedConditions.jsReturnsValue(
				"if (document.getElementById('" + textarea.getAttribute("id") + "').value" +
						"=== '" + expectedText + "') {" +
						"return true;" +
				"}"));
			//J+
		}
		catch (TimeoutException e) {
			// Do nothing.
		}

		browserDriver.setWaitTimeOut(TestUtil.getBrowserDriverWaitTimeOut());
		browserDriver.clickElement(elementToClickXpath);
	}
}
