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

import com.liferay.faces.test.IntegrationTesterBase;


/**
 * @author  Liferay Faces Team
 */
public class Applicant extends IntegrationTesterBase {
	
	protected static final String inputTextURL = testBaseURL + "";

	// form tag found after submitting
	protected static final String formTagXpath = "//form[@method='post']";

	// portlet menu elements
	protected static final String menuButtonXpath = "//a[contains(@id,'_menu') and @title='Options']";
	protected static final String menuPreferencesXpath = "//a[contains(@id,'menu_preferences')]";

	// preferences elements
	protected static final String datePatternFieldXpath = "//input[contains(@id,':datePattern')]";
	protected static final String resetButtonXpath = "//input[@type='submit' and @value='Reset']";

	// elements for Job Applicants
	protected static final String logoXpath = "//img[contains(@src,'liferay-logo.png')]";

	protected static final String firstNameFieldXpath = "//input[contains(@id,':firstName')]";
	protected static final String firstNameFieldErrorXpath = "//input[contains(@id,':firstName')]/following-sibling::*[1]";

	protected static final String lastNameFieldXpath = "//input[contains(@id,':lastName')]";
	protected static final String lastNameFieldErrorXpath = "//input[contains(@id,':lastName')]/following-sibling::*[1]";

	protected static final String emailAddressFieldXpath = "//input[contains(@id,':emailAddress')]";
	protected static final String emailAddressFieldErrorXpath = "//input[contains(@id,':emailAddress')]/following-sibling::*[1]";

	protected static final String phoneNumberFieldXpath = "//input[contains(@id,':phoneNumber')]";
	protected static final String phoneNumberFieldErrorXpath = "//input[contains(@id,':phoneNumber')]/following-sibling::*[1]";

	protected static final String dateOfBirthFieldXpath = "//input[contains(@id,':dateOfBirth')]";
	protected static final String dateOfBirthFieldErrorXpath = "//input[contains(@id,':dateOfBirth')]/following-sibling::*[1]";

	protected static final String cityFieldXpath = "//input[contains(@id,':city')]";
	protected static final String cityFieldErrorXpath = "//input[contains(@id,':city')]/following-sibling::*[1]";

	protected static final String provinceIdFieldXpath = "//select[contains(@id,':provinceId')]";
	protected static final String provinceIdFieldErrorXpath = "//select[contains(@id,':provinceId')]/following-sibling::*[1]";

	protected static final String provinceIdSelectorXpath = "";

	protected static final String postalCodeFieldXpath = "//input[contains(@id,':postalCode')]";
	protected static final String postalCodeFieldErrorXpath = "//input[contains(@id,':postalCode')]/following-sibling::*[1]/following-sibling::*[1]";

	protected static final String postalCodeToolTipXpath = "//img[contains(@title,'Type any of these ZIP codes')]";

	protected static final String showCommentsLinkXpath = "//a[contains(text(),'Show Comments')]";
	protected static final String hideCommentsLinkXpath = "//a[contains(text(),'Hide Comments')]";
	protected static final String commentsXpath = "//textarea[contains(@id,':comments')]";

	protected static final String fileUploadChooserXpath = "//input[@type='file' and @multiple='multiple']";
	protected static final String submitFileXpath = "//form[@method='post' and @enctype='multipart/form-data']/input[@type='submit' and @value='Submit']";
	protected static final String uploadedFileXpath = "//tr[@class='portlet-section-body results-row']/td[2]";

	protected static final String submitButtonXpath = "//input[@type='submit' and @value='Submit']";
	protected static final String preferencesSubmitButtonXpath = "//input[@type='submit' and @value='Submit']";
	protected static final String editPreferencesButtonXpath = "//input[@type='submit' and @value='Edit Preferences']";
	protected static final String returnLinkXpath = "//a[contains(text(),'Return to Full Page')]";

	protected static final String mojarraVersionXpath = "//*[contains(text(),'Mojarra')]";
	protected static final String componentLibraryVersionXpath = "//*[contains(text(),'PrimeFaces ')]";
	protected static final String alloyVersionXpath = "//*[contains(text(),'Liferay Faces Alloy')]";
	protected static final String bridgeVersionXpath = "//*[contains(text(),'Liferay Faces Bridge')]";

	protected static final String versionUlXpath = "//*[contains(text(),'Liferay Faces Bridge')]/../../../ul";
	protected static final String windowInnerHeightXpath = "//em[@id='window.innerHeight']";
	protected static final String windowInnerWidthXpath = "//em[@id='window.innerWidth']";

}
