/**
 * Copyright (c) 2000-2021 Liferay, Inc. All rights reserved.
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
public class InputRichTextImmediateTester extends InputRichTextTester {

	@Test
	public void runInputRichTextImmediateTest() {

		// 1. Navigate to the "inputRichText" "Immediate" use case.
		BrowserDriver browserDriver = getBrowserDriver();
		navigateToUseCase(browserDriver, "inputRichText", "immediate");

		// 2. Enter "Hello world!" into the first rich text editor.
		browserDriver.switchToFrame(CK_EDITOR_IFRAME_XPATH);

		String text = "Hello world!";
		browserDriver.sendKeysToElement(BODY_XPATH, text);

		// 3. Click the first *Submit* button.
		submitRichText(browserDriver, submitButton1Xpath, 1, text);

		// 4. Verify that the first model value contains "Hello world!".
		WaitingAsserter waitingAsserter = getWaitingAsserter();
		waitingAsserter.assertTextPresentInElement(text, modelValue1Xpath);

		// 5. Verify that the first feedback message shows that the valueChangeListener method was called during the
		// APPLY_REQUEST_VALUES phase.
		waitingAsserter.assertElementDisplayed(immediateMessage1Xpath);

		// 6. Enter "Hello world!" into the second rich text editor.
		browserDriver.switchToFrame("(" + CK_EDITOR_IFRAME_XPATH + ")[2]");
		browserDriver.sendKeysToElement(BODY_XPATH, text);

		// 7. Click the second *Submit* button.
		submitRichText(browserDriver, submitButton2Xpath, 2, text);

		// 8. Verify that the second model value contains "Hello world!".
		waitingAsserter.assertTextPresentInElement(text, modelValue2Xpath);

		// 9. Verify that the second feedback message shows that the valueChangeListener method was called during the
		// PROCESS_VALIDATIONS phase.
		waitingAsserter.assertElementDisplayed(immediateMessage2Xpath);
	}
}
