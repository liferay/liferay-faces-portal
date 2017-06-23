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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;

import org.junit.runners.MethodSorters;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.liferay.faces.test.selenium.TestUtil;
import com.liferay.faces.test.selenium.browser.BrowserDriver;
import com.liferay.faces.test.selenium.browser.BrowserStateAsserter;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Philip White
 * @author  Kyle Stiemann
 * @author  Vernon Singleton
 * @author  Neil Griffin
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PrimeFacesUsersPortletTester extends PrimeFacesUsersPortletTesterCompat {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PrimeFacesUsersPortletTester.class);

	// Private Constants
	private static final String FIRST_NAME_FIELD_XPATH = "//input[contains(@id,':firstName')]";
	private static final String LAST_NAME_FIELD_XPATH = "//input[contains(@id,':lastName')]";
	private static final String EMAIL_ADDRESS_FIELD_XPATH = "//input[contains(@id,':emailAddress')]";
	private static final String FIRST_USER_SCREEN_NAME_XPATH = "//span[contains(@id,':screenNameCell')]";
	private static final String SECOND_USER_SCREEN_NAME_CELL_XPATH = "(//span[contains(@id,':screenNameCell')])[2]";
	private static final String FIRST_NAME_COLUMN_FILTER_XPATH = "//input[contains(@id,'users:firstName:filter')]";
	private static final String SCREEN_NAME_COLUMN_FILTER_XPATH = "//input[contains(@id,'users:screenName:filter')]";

	/**
	 * This test ensures that the state of the UI and data are reset before testing.
	 */
	@Test
	public void runPrimeFacesUsersPortletTest_A_Reset_User_Data() {

		// Sign into Liferay Portal and navigate to the PrimeFaces Users portlet.
		BrowserDriver browserDriver = getBrowserDriver();
		browserDriver.navigateWindowTo(getURL());
		browserDriver.waitForElementDisplayed(FIRST_USER_SCREEN_NAME_XPATH);

		// Click on the hidden "Reset Users" button in order to reset the state of the user data in preparation for
		// testing.
		WebElement hiddenButtonResetUsers = browserDriver.findElementByXpath(
				"(//button[contains(@id,':hiddenButtonResetUsers')]/span)");
		browserDriver.executeScriptInCurrentWindow("arguments[0].click();", hiddenButtonResetUsers);

		WebElement firstUserScreenNameCell = browserDriver.findElementByXpath(FIRST_USER_SCREEN_NAME_XPATH);
		browserDriver.waitFor(ExpectedConditions.stalenessOf(firstUserScreenNameCell));
		browserDriver.waitForElementDisplayed(FIRST_USER_SCREEN_NAME_XPATH);

		// Click on the "Preferences" button in order to navigate to edit mode.
		String preferencesLinkXpath = "//a[contains(@id,':preferences')]";
		browserDriver.clickElement(preferencesLinkXpath);

		// Enter "5" for the number of rows-per-page so that the pagination can be better tested with a small data set.
		String rowsPerPageInputXpath = "//input[contains(@id,':rowsPerPage')]";
		browserDriver.waitForElementEnabled(rowsPerPageInputXpath);
		browserDriver.clearElement(rowsPerPageInputXpath);
		browserDriver.sendKeysToElement(rowsPerPageInputXpath, "5");

		// Click on the Submit button in order to save the rows-per-page preference.
		browserDriver.clickElement("//input[contains(@id,':submit')]");
		browserDriver.waitForElementDisplayed(preferencesLinkXpath);
	}

	@Test
	public void runPrimeFacesUsersPortletTest_B_DetailView() {

		// Enter "john.adams" in the Screen Name filter in order so that "John Adams" appears as the only user in the
		// list. This is helpful in case there have been many users added to the database and "John Adams" is no longer
		// conveniently present as the first user.
		BrowserDriver browserDriver = getBrowserDriver();
		browserDriver.navigateWindowTo(getURL());
		browserDriver.waitForElementDisplayed(FIRST_USER_SCREEN_NAME_XPATH);

		BrowserStateAsserter browserStateAsserter = getBrowserStateAsserter();
		filterByScreenNameAndVerifySingleUserDisplayed(browserDriver, browserStateAsserter, "john.adams");

		// Select "John Adams" from the list of users so that the corresponding user's data is displayed in an
		// editable form.
		browserDriver.clickElement(FIRST_USER_SCREEN_NAME_XPATH);
		browserDriver.waitForElementDisplayed(FIRST_NAME_FIELD_XPATH);

		// Enter an invalid email address by removing the "@" symbol and click the "Submit" button.
		browserDriver.clearElement(EMAIL_ADDRESS_FIELD_XPATH);
		browserDriver.sendKeysToElement(EMAIL_ADDRESS_FIELD_XPATH, "john.adamsliferay.com");

		String submitButtonXpath = "//button[contains(@id, ':pushButtonSubmit') and @type='submit']";
		browserDriver.clickElement(submitButtonXpath);

		// Verify that the error message "Invalid Email Address" is displayed since only valid email addresses are
		// permitted.
		browserStateAsserter.assertElementDisplayed(
			"//input[contains(@id,':emailAddress')]/../span[@class='portlet-msg-error' and text()='Invalid Email Address']");

		// Clear the "First Name", "Last Name", and "Email Address" fields and click the "Submit" button.
		browserDriver.clearElement(FIRST_NAME_FIELD_XPATH);
		browserDriver.clearElement(LAST_NAME_FIELD_XPATH);
		browserDriver.clearElement(EMAIL_ADDRESS_FIELD_XPATH);
		browserDriver.clickElement(submitButtonXpath);

		// Verify that the error message "Value is required" is displayed for "First Name", "Last Name", and "Email
		// Address" since they are all required fields.
		browserStateAsserter.assertElementDisplayed(
			"//input[contains(@id,':firstName')]/../span[@class='portlet-msg-error' and text()='Value is required']");
		browserStateAsserter.assertElementDisplayed(
			"//input[contains(@id,':lastName')]/../span[@class='portlet-msg-error' and text()='Value is required']");
		browserStateAsserter.assertElementDisplayed(
			"//input[contains(@id,':emailAddress')]/../span[@class='portlet-msg-error' and text()='Value is required']");

		// Click on the "Cancel" button in order to return to the list of users.
		browserDriver.clickElement("//button[contains(@id, ':pushButtonCancel') and @type='submit']");
		browserDriver.waitForElementDisplayed(FIRST_USER_SCREEN_NAME_XPATH);

		// TODO: It should not be necessary to re-enter "john.adams" into the Screen Name filter but if not done, then
		// the test has subsequent assertion failures.
		filterByScreenNameAndVerifySingleUserDisplayed(browserDriver, browserStateAsserter, "john.adams");

		// Clear the Screen Name filter and verify that the list contains more than one user.
		browserDriver.clearElement(SCREEN_NAME_COLUMN_FILTER_XPATH);
		browserStateAsserter.assertElementDisplayed(SECOND_USER_SCREEN_NAME_CELL_XPATH);

		// Verify that when "Adams" is entered into the last name filter that only users with the last name "Adams"
		// are present in the list.
		String lastNameColumnFilterXpath = "//input[contains(@id,'users:lastName:filter')]";
		Actions filterActions = browserDriver.createActions(lastNameColumnFilterXpath);
		WebElement lastNameColumnFilterElement = browserDriver.findElementByXpath(lastNameColumnFilterXpath);
		filterActions.sendKeys(lastNameColumnFilterElement, "Adams");
		browserDriver.performAndWaitForRerender(filterActions.build(), "(//span[contains(@id,':firstNameCell')])[1]");

		List<String> lastNames = extractColumnValuesFromDataTable(browserDriver, "lastName");
		Assert.assertTrue(lastNames.size() > 1);

		for (String lastName : lastNames) {
			Assert.assertTrue("Adams".equals(lastName));
		}

		// Clear the filter and verify that more than one user is present in the list of users.
		browserDriver.clearElement(lastNameColumnFilterXpath);
		browserStateAsserter.assertElementDisplayed(SECOND_USER_SCREEN_NAME_CELL_XPATH);
	}

	@Test
	public void runPrimeFacesUsersPortletTest_C_Paginator() {

		BrowserDriver browserDriver = getBrowserDriver();
		browserDriver.navigateWindowTo(getURL());

		// Take note of the screen name of the first user in the list on page 1.
		String firstUserPageOne = browserDriver.findElementByXpath(FIRST_USER_SCREEN_NAME_XPATH).getText();
		logger.debug("firstUserPageOne=[{0}]", firstUserPageOne);

		// Click on the 'Next Page' button and verify that the page 2 button is highlighted.
		browserDriver.clickElement("//a[contains(@aria-label,'Next Page')]");
		browserDriver.waitForElementDisplayed("//span/a[contains(@class,'ui-state-active') and text()='2']");

		// Verify that the screen name noted from page 1 is not present on page 2.
		BrowserStateAsserter browserStateAsserter = getBrowserStateAsserter();
		browserStateAsserter.assertElementNotDisplayed(firstUserPageOne);
		browserStateAsserter.assertTextNotPresentInElement(firstUserPageOne, FIRST_USER_SCREEN_NAME_XPATH);

		// Take note of the screen name of the first user in the list on page 2.
		String firstUserPageTwo = browserDriver.findElementByXpath(FIRST_USER_SCREEN_NAME_XPATH).getText();
		logger.debug("firstUserPageTwo=[{1}]", firstUserPageTwo);

		// Click on the '3' button and verify that it is highlighted.
		browserDriver.clickElement("//a[contains(@aria-label,'Page 3')]");
		browserDriver.waitForElementDisplayed("//span/a[contains(@class,'ui-state-active') and text()='3']");

		// Verify that the screen names noted from page 1 and 2 are not present on page 3.
		browserStateAsserter.assertTextNotPresentInElement(firstUserPageOne, FIRST_USER_SCREEN_NAME_XPATH);
		browserStateAsserter.assertTextNotPresentInElement(firstUserPageTwo, FIRST_USER_SCREEN_NAME_XPATH);

		// Take note of the screen name of the first user in the list on page 3.
		String firstUserPageThree = browserDriver.findElementByXpath(FIRST_USER_SCREEN_NAME_XPATH).getText();
		logger.debug("firstUserPageThree=[{0}]", firstUserPageThree);

		// Click on 'Previous Page' button and verify that the page 2 button is highlighted.
		browserDriver.clickElement("//a[contains(@aria-label,'Previous Page')]");
		browserDriver.waitForElementDisplayed("//span/a[contains(@class,'ui-state-active') and text()='2']");

		// Verify that the screen names noted from page 1 and 3 are not present on page 2.
		browserStateAsserter.assertTextNotPresentInElement(firstUserPageOne, FIRST_USER_SCREEN_NAME_XPATH);
		browserStateAsserter.assertTextNotPresentInElement(firstUserPageThree, FIRST_USER_SCREEN_NAME_XPATH);

		// Take note of the screen name of the first user in the list on page 2. Click on 'Last Page' button and verify
		// that the last button is highlighted and the 'Next Page' and 'Last Page' buttons are disabled.
		browserDriver.clickElement("//a[contains(@aria-label,'Last Page')]");
		browserDriver.waitForElementDisplayed(
			"(//span[contains(@class,'ui-paginator-pages')]/a[last()][contains(@class,'ui-state-active')])");

		// TODO 0.3.1 expect NotElementEnabled
		browserDriver.waitForElementDisplayed(
			"//a[contains(@class,'ui-state-disabled') and contains(@aria-label,'Next Page')]");
		browserDriver.waitForElementDisplayed(
			"//a[contains(@class,'ui-state-disabled') and contains(@aria-label,'Last Page')]");

		// Verify that the screen names noted from page 1, 2 and 3 are not present on the last page.
		browserStateAsserter.assertTextNotPresentInElement(firstUserPageOne, FIRST_USER_SCREEN_NAME_XPATH);
		browserStateAsserter.assertTextNotPresentInElement(firstUserPageTwo, FIRST_USER_SCREEN_NAME_XPATH);
		browserStateAsserter.assertTextNotPresentInElement(firstUserPageThree, FIRST_USER_SCREEN_NAME_XPATH);

		// Take note of the screen name of the first user in the list on the last page.
		String firstUserLastPage = browserDriver.findElementByXpath(FIRST_USER_SCREEN_NAME_XPATH).getText();
		logger.debug("firstUserLastPage[{0}]", firstUserLastPage);

		// Click on 'First Page' button and verify that the page 1 button is highlighted and the 'First Page' and
		// 'Previous Page' buttons are disabled.
		browserDriver.clickElement("//a[contains(@aria-label,'First Page')]");
		browserDriver.waitForElementDisplayed(
			"(//span[contains(@class,'ui-paginator-pages')]/a[contains(@class,'ui-state-active')])[1]");
		browserDriver.waitForElementDisplayed(
			"//a[contains(@class,'ui-state-disabled') and contains(@aria-label,'First Page')]");
		browserDriver.waitForElementDisplayed(
			"//a[contains(@class,'ui-state-disabled') and contains(@aria-label,'Previous Page')]");

		// Verify that the screen names noted from page 2, 3 and the last page are not present on the first page.
		browserStateAsserter.assertTextNotPresentInElement(firstUserPageTwo, FIRST_USER_SCREEN_NAME_XPATH);
		browserStateAsserter.assertTextNotPresentInElement(firstUserPageThree, FIRST_USER_SCREEN_NAME_XPATH);
		browserStateAsserter.assertTextNotPresentInElement(firstUserLastPage, FIRST_USER_SCREEN_NAME_XPATH);
	}

	@Test
	public void runPrimeFacesUsersPortletTest_D_Sort() {

		BrowserDriver browserDriver = getBrowserDriver();
		browserDriver.navigateWindowTo(getURL());

		// Click on the Screen Name header to sort screen names into ascending order and verify that the triangle icon
		// is pointed up.
		browserDriver.clickElement("//th[contains(@id,'users:screenName')]/child::span[1]");
		browserDriver.waitForElementDisplayed("(//span[contains(@class,'ui-icon-triangle-1-n')])");

		// Take note of each screen name in the first column of the table, clicking on the Next Button until all the
		// screen names have been noted.
		List<String> screenNames = extractColumnValuesFromDataTable(browserDriver, "screenName");
		logger.debug(Logger.SEPARATOR);
		logger.debug("Screen names extracted from the UI after sorting by ascending order:");

		for (String screenName : screenNames) {
			logger.debug(screenName);
		}

		// Verify that the list of screen names are in alphabetical order.
		List<String> sortedScreenNames = new ArrayList<String>(screenNames);
		Collections.sort(sortedScreenNames);
		logger.debug(Logger.SEPARATOR);
		logger.debug("Same list of screen names, except guaranteed to be sorted in ascending order:");

		for (String sortedScreenName : sortedScreenNames) {
			logger.debug(sortedScreenName);
		}

		Assert.assertTrue(screenNames.equals(sortedScreenNames));

		// Click on the First Page icon in order to go back to the first page of users.
		browserDriver.clickElement("//a[contains(@aria-label,'First Page')]");
		browserDriver.waitForElementDisplayed(
			"(//span[contains(@class,'ui-paginator-pages')]/a[contains(@class,'ui-state-active')])[1]");

		// Click on the Screen Name header to sort screen names into descending order and verify that the triangle
		// icon is pointed down.
		browserDriver.clickElement("//th[contains(@id,'users:screenName')]/child::span[1]");
		browserDriver.waitForElementDisplayed("(//span[contains(@class,'ui-icon-triangle-1-s')])");

		// Take note of each screen name in the first column of the table, clicking on the Next Button until all the
		// screen names have been noted.
		screenNames = extractColumnValuesFromDataTable(browserDriver, "screenName");
		logger.debug(Logger.SEPARATOR);
		logger.debug("Screen names extracted from the UI after sorting by descending order:");

		for (String screenName : screenNames) {
			logger.debug(screenName);
		}

		// Verify that the list of screen names are in reverse alphabetical order.
		sortedScreenNames = new ArrayList<String>(screenNames);
		Collections.sort(sortedScreenNames, Collections.reverseOrder());
		logger.debug(Logger.SEPARATOR);
		logger.debug("Same list of screen names, except guaranteed to be sorted in descending order:");

		for (String sortedScreenName : sortedScreenNames) {
			logger.debug(sortedScreenName);
		}

		Assert.assertTrue(screenNames.equals(sortedScreenNames));
	}

	@Test
	public void runPrimeFacesUsersPortletTest_E_FirstNameColumnTextFilter() {

		BrowserDriver browserDriver = getBrowserDriver();
		browserDriver.navigateWindowTo(getURL());

		// Click on the First Page icon in order to go back to the first page of users.
		browserDriver.clickElement("//a[contains(@aria-label,'First Page')]");
		browserDriver.waitForElementDisplayed(
			"(//span[contains(@class,'ui-paginator-pages')]/a[contains(@class,'ui-state-active')])[1]");

		// Click on the First Name header to sort first names into ascending order and verify that the triangle icon
		// is pointed up.
		browserDriver.clickElement("//th[contains(@id,'users:firstName')]/child::span[1]");
		browserDriver.waitForElementDisplayed("(//span[contains(@class,'ui-icon-triangle-1-n')])");

		// If testing Liferay Portal 7.x, enter "J" into the first name filter since partial matching is supported.
		// Otherwise, if testing on Liferay Portal 6.2, enter "John" into the first name filter since only exact
		// matching is supported.
		WebElement firstNameFilterElement = browserDriver.findElementByXpath(FIRST_NAME_COLUMN_FILTER_XPATH);
		Actions filterActions = browserDriver.createActions(FIRST_NAME_COLUMN_FILTER_XPATH);
		filterActions.sendKeys(firstNameFilterElement, getFirstNameFilterText());
		browserDriver.performAndWaitForRerender(filterActions.build(), "(//span[contains(@id,':firstNameCell')])[1]");

		// Take note of each first name in the third column of the table, clicking on the Next button until all the
		// first names have been noted.
		List<String> firstNames = extractColumnValuesFromDataTable(browserDriver, "firstName");
		logger.debug(Logger.SEPARATOR);
		logger.debug("First names extracted from the UI after sorting by ascending order and filtering by 'O':");

		for (String firstName : firstNames) {
			logger.debug(firstName);
		}

		// If testing Liferay Portal 7.0, verify that the list of first names start with the letter "J" and are in
		// alphabetical order. Otherwise, if testing Liferay Portal 6.2, verify that the list of first names are all
		// "John".
		List<String> sortedFirstNames = new ArrayList<String>(firstNames);
		Collections.sort(sortedFirstNames);
		logger.debug(Logger.SEPARATOR);
		logger.debug("Same list of first names, except guaranteed to be sorted in ascending order:");

		for (String sortedFirstName : sortedFirstNames) {
			logger.debug(sortedFirstName);
			Assert.assertTrue(isFirstNameMatch(sortedFirstName));
		}

		Assert.assertTrue(firstNames.equals(sortedFirstNames));

		// Click on the First Page icon in order to go back to the first page of users.
		browserDriver.clickElement("//a[contains(@aria-label,'First Page')]");
		browserDriver.waitForElementDisplayed(
			"(//span[contains(@class,'ui-paginator-pages')]/a[contains(@class,'ui-state-active')])[1]");

		// Click on the First Name header to sort first names into descending order and verify that the triangle icon
		// is pointed down.
		browserDriver.clickElement("//th[contains(@id,'users:firstName')]/child::span[1]");
		browserDriver.waitForElementDisplayed("(//span[contains(@class,'ui-icon-triangle-1-s')])");

		// Take note of each first name in the list, clicking on the Next Button until all the first names have been
		// noted.
		firstNames = extractColumnValuesFromDataTable(browserDriver, "firstName");
		logger.debug(Logger.SEPARATOR);
		logger.debug("First names extracted from the UI after sorting by descending order and filtering by 'J':");

		// If testing Liferay Portal 7.0, verify that the list of first names start with the letter "J" and are in
		// reverse alphabetical order. Otherwise, if testing Liferay Portal 6.2, verify that the list of first names are
		// all "John".
		sortedFirstNames = new ArrayList<String>(firstNames);
		Collections.sort(sortedFirstNames, Collections.reverseOrder());
		logger.debug(Logger.SEPARATOR);
		logger.debug("Same list of first names, except guaranteed to be sorted in descending order:");

		for (String sortedFirstName : sortedFirstNames) {
			logger.debug(sortedFirstName);
			Assert.assertTrue(isFirstNameMatch(sortedFirstName));
		}

		Assert.assertTrue(firstNames.equals(sortedFirstNames));

		// Clear the filter so that the first page of the entire list of users is displayed again.
		browserDriver.clearElement(FIRST_NAME_COLUMN_FILTER_XPATH);

		BrowserStateAsserter browserStateAsserter = getBrowserStateAsserter();
		browserStateAsserter.assertElementDisplayed(
			"//span[contains(@id,':firstNameCell') and contains(text(), 'Thomas')]");
		browserDriver.clickElement("//th[contains(@id,'users:lastName')]/child::span[1]");
		browserDriver.waitForElementDisplayed("(//span[contains(@class,'ui-icon-triangle-1-n')])");
	}

	@Test
	public void runPrimeFacesUsersPortletTest_F_ChangeUserAttributes() {

		BrowserDriver browserDriver = getBrowserDriver();
		browserDriver.navigateWindowTo(getURL());
		browserDriver.waitForElementDisplayed(FIRST_USER_SCREEN_NAME_XPATH);

		// Enter "john.adams" in the Screen Name filter in order so that "John Adams" appears as the only user in the
		// list. This is helpful in case there have been many users added to the database and "John Adams" is no longer
		// conveniently present as the first user.
		BrowserStateAsserter browserStateAsserter = getBrowserStateAsserter();
		filterByScreenNameAndVerifySingleUserDisplayed(browserDriver, browserStateAsserter, "john.adams");

		// Select "John Adams" from the list of users so that the corresponding user's data is displayed in an
		// editable form.
		browserDriver.clickElement(FIRST_USER_SCREEN_NAME_XPATH);

		// Change the user's Last Name from "Adams" to "Adamsy"
		browserDriver.waitForElementDisplayed(LAST_NAME_FIELD_XPATH);
		browserDriver.clearElement(LAST_NAME_FIELD_XPATH);
		browserDriver.sendKeysToElement(LAST_NAME_FIELD_XPATH, "Adamsy");

		// Change the user's First Name from "John" to "Johnny"
		browserDriver.clearElement(FIRST_NAME_FIELD_XPATH);
		browserDriver.sendKeysToElement(FIRST_NAME_FIELD_XPATH, "Johnny");

		// Change the user's Email Address to "johnny.adamsy@liferay.com"
		browserDriver.clearElement(EMAIL_ADDRESS_FIELD_XPATH);
		browserDriver.sendKeysToElement(EMAIL_ADDRESS_FIELD_XPATH, "johnny.adamsy@liferay.com");

		// Click on the "Submit" button in order to go back to the list of users.
		browserDriver.clickElement("//button[contains(@id, ':pushButtonSubmit') and @type='submit']");
		browserDriver.waitForElementDisplayed(FIRST_USER_SCREEN_NAME_XPATH);

		// Enter "john.adams" in the Screen Name filter in order so that "John Adams" appears as the only user in the
		// list. This is helpful in case there have been many users added to the database and "John Adams" is no longer
		// conveniently present as the first user.
		filterByScreenNameAndVerifySingleUserDisplayed(browserDriver, browserStateAsserter, "john.adams");

		// Verify that the list contains "Adamsy" in the last name column, "Johnny" in the first name column, and
		// "johnny.adamsy@liferay.com" in the email address column.
		browserStateAsserter.assertTextPresentInElement("Adamsy", "//span[contains(@id,':lastNameCell')]");
		browserStateAsserter.assertTextPresentInElement("Johnny", "//span[contains(@id,':firstNameCell')]");
		browserStateAsserter.assertElementDisplayed("//a[contains(.,'johnny.adamsy@liferay.com')]");

		// Clear the filter in order to see the list of all the users again.
		browserDriver.clearElement(SCREEN_NAME_COLUMN_FILTER_XPATH);
		browserStateAsserter.assertElementDisplayed(SECOND_USER_SCREEN_NAME_CELL_XPATH);
	}

	@Test
	public void runPrimeFacesUsersPortletTest_G_FileUpload() {

		BrowserDriver browserDriver = getBrowserDriver();
		browserDriver.navigateWindowTo(getURL());

		// Enter "john.adams" in the Screen Name filter in order so that "John Adams" appears as the only user in the
		// list. This is helpful in case there have been many users added to the database and "John Adams" is no longer
		// conveniently present as the first user.
		BrowserStateAsserter browserStateAsserter = getBrowserStateAsserter();
		filterByScreenNameAndVerifySingleUserDisplayed(browserDriver, browserStateAsserter, "john.adams");

		// Select "John Adams" from the list of users so that the corresponding user's data is displayed in an
		// editable form.
		browserDriver.clickElement(FIRST_USER_SCREEN_NAME_XPATH);
		browserDriver.waitForElementDisplayed(FIRST_NAME_FIELD_XPATH);

		// Verify that the file upload chooser is displayed.
		String fileUploadChooserXpath = "//input[@type='file']";
		browserStateAsserter.assertElementDisplayed(fileUploadChooserXpath + "/..");

		// Prior to attempting to upload an image, note the height of the photo that is displayed.
		String portraitXpath = "//div[contains(@class,'has-success')]/img[contains(@id,':portrait')]";
		WebElement imgWebElement = browserDriver.findElementByXpath(portraitXpath);
		Dimension imageSizeBefore = imgWebElement.getSize();

		// Click on the "Choose" button and select the file named "liferay-jsf-jersey.png" for upload.
		WebElement fileUploadChooser = browserDriver.findElementByXpath(fileUploadChooserXpath);

		// TECHNICAL NOTE:
		// Set PrimeFaces p:fileUpload transform style to "none" since it causes the element to not be displayed
		// according to Selenium (although the element is visible to users).
		browserDriver.executeScriptInCurrentWindow("arguments[0].style.transform = 'none';", fileUploadChooser);
		fileUploadChooser.sendKeys(TestUtil.JAVA_IO_TMPDIR + "liferay-jsf-jersey.png");
		browserDriver.waitFor(ExpectedConditions.stalenessOf(imgWebElement));
		browserStateAsserter.assertElementDisplayed(portraitXpath);
		imgWebElement = browserDriver.findElementByXpath(portraitXpath);

		// Verify that the height of the displayed photo is different than the height noted earlier.
		Dimension imageSizeAfter = imgWebElement.getSize();
		Assert.assertNotEquals(imageSizeBefore.getHeight(), imageSizeAfter.getHeight());
	}

	/**
	 * Extract the column values from the dataTable associated with the specified column name.
	 */
	private List<String> extractColumnValuesFromDataTable(BrowserDriver browserDriver, String columnName) {

		// While the "Last Page" icon is still active:
		List<String> columnValues = new ArrayList<String>();
		String columnElementsXpath = "//span[contains(@id,':" + columnName + "Cell')]";

		while (browserDriver.findElementsByXpath(
					"//a[contains(@class,'ui-state-disabled') and contains(@aria-label,'Last Page')]").isEmpty()) {

			// Extract the column values from the current page of the dataTable.
			List<WebElement> columnElements = browserDriver.findElementsByXpath(columnElementsXpath);

			for (WebElement columnElement : columnElements) {
				String columnValue = columnElement.getText();
				columnValues.add(columnValue);
			}

			// Click on the "Next Page" icon.
			browserDriver.performAndWaitForRerender(browserDriver.createClickElementAction(
					"//a[contains(@aria-label,'Next Page')]"), "(//span[contains(@id,':" + columnName + "Cell')])[1]");
		}

		// Extract column values from the last page of the dataTable.
		List<WebElement> screenNameElements = browserDriver.findElementsByXpath(columnElementsXpath);

		for (WebElement screenNameElement : screenNameElements) {
			String screenName = screenNameElement.getText();
			columnValues.add(screenName);
		}

		return columnValues;
	}

	/**
	 * Enters the specified screen name into the "Screen Name" filter and verifies that afterwards the list contains
	 * only the specified user.
	 */
	private void filterByScreenNameAndVerifySingleUserDisplayed(BrowserDriver browserDriver,
		BrowserStateAsserter browserStateAsserter, String screenName) {

		browserDriver.clearElement(SCREEN_NAME_COLUMN_FILTER_XPATH);
		browserDriver.sendKeysToElement(SCREEN_NAME_COLUMN_FILTER_XPATH, screenName);
		browserStateAsserter.assertElementNotDisplayed(SECOND_USER_SCREEN_NAME_CELL_XPATH);
		browserStateAsserter.assertElementDisplayed(FIRST_USER_SCREEN_NAME_XPATH);

		List<String> screenNames = extractColumnValuesFromDataTable(browserDriver, "screenName");
		Assert.assertTrue(screenNames.size() == 1);
		Assert.assertTrue(screenNames.get(0).equals(screenName));
	}

	private String getURL() {
		return TestUtil.DEFAULT_BASE_URL + "/group/control_panel/manage?p_p_id=1_WAR_primefacesusersportlet";
	}
}
