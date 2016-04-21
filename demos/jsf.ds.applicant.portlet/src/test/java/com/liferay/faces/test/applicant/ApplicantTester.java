/**
 * Copyright (c) 2000-2016 Liferay, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liferay.faces.test.applicant;

import org.junit.Test;

import com.liferay.faces.test.Browser;


/**
 * @author  Liferay Faces Team
 */
public class ApplicantTester extends Applicant {

	@Test
	public void runInputTextGeneralTest() throws Exception {

		Browser browser = Browser.getInstance();
		browser.navigateToURL(inputTextURL + "");

		// Wait to begin the test until the logo is rendered.
		browser.waitForElementVisible(logoXpath);

		// Test that an empty value submits successfully, but with validation errors.
		browser.clickAndWaitForAjaxRerender(submitButtonXpath);
		browser.assertElementVisible(firstNameFieldErrorXpath);

		// Test that a text value submits successfully.
		String text = "asdf";
		browser.sendKeys(firstNameFieldXpath, text);
		browser.waitForElementValue(firstNameFieldXpath, text);
		browser.clickAndWaitForAjaxRerender(submitButtonXpath);
		browser.assertElementValue(firstNameFieldXpath, text);
		
		browser.assertElementVisible(logoXpath);
	}
}
