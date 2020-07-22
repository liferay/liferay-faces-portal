/**
 * Copyright (c) 2000-2020 Liferay, Inc. All rights reserved.
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;

import org.junit.runners.MethodSorters;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liferay.faces.test.selenium.browser.BrowserDriver;
import com.liferay.faces.test.selenium.browser.TestUtil;
import com.liferay.faces.test.selenium.browser.WaitingAsserter;


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
	private static final int DOUBLED_DEFAULT_BROWSER_DRIVER_WAIT_TIME_OUT = 10;
	private static final String CANCEL_BUTTON_XPATH = "//button[contains(@id, ':pushButtonCancel') and @type='submit']";
	private static final String EMAIL_ADDRESS_FIELD_XPATH =
		"//input[contains(@id,':emailAddress')][not(contains(@id,':filter'))]";
	private static final String FIRST_NAME_CELL_XPATH = "//span[contains(@id,':firstNameCell')]";
	private static final String FIRST_NAME_COLUMN_FILTER_XPATH = "//input[contains(@id,'users:firstName:filter')]";
	private static final String FIRST_NAME_FIELD_XPATH =
		"//input[contains(@id,':firstName')][not(contains(@id,':filter'))]";
	private static final String FIRST_NAME_SORT_BUTTON_XPATH = "//th[contains(@id,'users:firstName')]/span";
	private static final String LAST_NAME_COLUMN_FILTER_XPATH = "//input[contains(@id,'users:lastName:filter')]";
	private static final String LAST_NAME_FIELD_XPATH =
		"//input[contains(@id,':lastName')][not(contains(@id,':filter'))]";
	private static final String PAGE_BUTTON_XPATH_PREFIX =
		"//span[contains(@class,'ui-paginator-pages')]/a[contains(@class,'ui-paginator-')][contains(@aria-label,'Page')]";
	private static final String SCREEN_NAME_CELL_XPATH = "//span[contains(@id,':screenNameCell')]";
	private static final String SCREEN_NAME_CELL_2_XPATH = "(" + SCREEN_NAME_CELL_XPATH + ")[2]";
	private static final String SCREEN_NAME_COLUMN_FILTER_XPATH = "//input[contains(@id,'users:screenName:filter')]";
	private static final String SCREEN_NAME_SORT_BUTTON_XPATH = "//th[contains(@id,'users:screenName')]/span";
	private static final String SORTED_ASCENDING_ICON_XPATH = "//span[contains(@class,'ui-icon-triangle-1-n')]";
	private static final String SORTED_DESCENDING_ICON_XPATH = "//span[contains(@class,'ui-icon-triangle-1-s')]";
	private static final String SUBMIT_BUTTON_XPATH = "//button[contains(@id, ':pushButtonSubmit') and @type='submit']";

	// Private Static Data Members
	private static boolean isStateReset = false;

	public static void resetTestUsers(BrowserDriver browserDriver) {

		// Navigate to the PrimeFaces Users portlet.
		browserDriver.navigateWindowTo(getURL());
		browserDriver.setWaitTimeOut(TestUtil.getBrowserDriverWaitTimeOut(
				DOUBLED_DEFAULT_BROWSER_DRIVER_WAIT_TIME_OUT));
		browserDriver.waitForElementDisplayed(SCREEN_NAME_COLUMN_FILTER_XPATH);

		List<WebElement> screenNameCells = browserDriver.findElementsByXpath(SCREEN_NAME_CELL_XPATH);

		// Click the hidden *Reset Users* button to reset the state of the user data in preparation
		// for testing.
		WebElement hiddenResetUsersButton = browserDriver.findElementByXpath(
				"//button[contains(@id,':hiddenResetUsersButton')]");
		browserDriver.executeScriptInCurrentWindow("arguments[0].click();", hiddenResetUsersButton);

		if (!screenNameCells.isEmpty()) {
			browserDriver.waitFor(ExpectedConditions.stalenessOf(screenNameCells.get(0)));
		}

		browserDriver.waitForElementDisplayed(SCREEN_NAME_CELL_XPATH);
		browserDriver.setWaitTimeOut(TestUtil.getBrowserDriverWaitTimeOut());
	}

	@Test
	public void runPrimeFacesUsersPortletTest_A_UsersPagination() {

		BrowserDriver browserDriver = getBrowserDriver();
		browserDriver.navigateWindowTo(getURL());

		// 1. Take note of the screen name of the first user in the list on page 1.
		String firstScreenNameOnPageOne = browserDriver.findElementByXpath(SCREEN_NAME_CELL_XPATH).getText();
		logger.debug("firstUserOnPageOne=[{}]", firstScreenNameOnPageOne);

		// 2. Click the *Next Page* button and verify that the page 2 button is highlighted.
		browserDriver.clickElement(getNavigationButtonXpath("Next"));

		WaitingAsserter waitingAsserter = getWaitingAsserter();
		waitingAsserter.assertTrue(pageButtonClassActive(2));

		// 3. Verify that the screen name noted from page 1 is not present on page 2.
		waitingAsserter.assertTextNotPresentInElement(firstScreenNameOnPageOne, SCREEN_NAME_CELL_XPATH);

		// 4. Take note of the screen name of the first user in the list on page 2.
		String firstScreenNameOnPageTwo = browserDriver.findElementByXpath(SCREEN_NAME_CELL_XPATH).getText();
		logger.debug("firstUserOnPageTwo=[{}]", firstScreenNameOnPageTwo);

		// 5. Click on *3* button and verify that the page 3 button is highlighted.
		browserDriver.clickElement(getPageButtonXpath(3));
		waitingAsserter.assertTrue(pageButtonClassActive(3));

		// 6. Verify that the screen names noted from page 1 and 2 are not present on page 3.
		waitingAsserter.assertTextNotPresentInElement(firstScreenNameOnPageOne, SCREEN_NAME_CELL_XPATH);
		waitingAsserter.assertTextNotPresentInElement(firstScreenNameOnPageTwo, SCREEN_NAME_CELL_XPATH);

		// 7. Take note of the screen name of the first user in the list on page 3.
		String firstScreenNameOnPageThree = browserDriver.findElementByXpath(SCREEN_NAME_CELL_XPATH).getText();
		logger.debug("firstUserOnPageThree=[{}]", firstScreenNameOnPageThree);

		// 8. Click on *Previous Page* button and verify that the page 2 button is highlighted.
		browserDriver.clickElement(getNavigationButtonXpath("Previous"));
		waitingAsserter.assertTrue(pageButtonClassActive(2));

		// 9. Verify that the screen names noted from page 1 and 3 are not present on page 2.
		waitingAsserter.assertTextNotPresentInElement(firstScreenNameOnPageOne, SCREEN_NAME_CELL_XPATH);
		waitingAsserter.assertTextNotPresentInElement(firstScreenNameOnPageThree, SCREEN_NAME_CELL_XPATH);

		// 9. Click on *Last Page* button and verify that the last button is highlighted and that the *Next Page* and
		// *Last Page* buttons are disabled.
		browserDriver.clickElement(getNavigationButtonXpath("Last"));
		waitingAsserter.assertTrue(textPresentInElementClass("ui-state-active", PAGE_BUTTON_XPATH_PREFIX + "[last()]"));
		waitingAsserter.assertTrue(navigationButtonClassDisabled("Next"));
		waitingAsserter.assertTrue(navigationButtonClassDisabled("Last"));

		// 10. Verify that the screen names noted from page 1, 2 and 3 are not present on the last page.
		waitingAsserter.assertTextNotPresentInElement(firstScreenNameOnPageOne, SCREEN_NAME_CELL_XPATH);
		waitingAsserter.assertTextNotPresentInElement(firstScreenNameOnPageTwo, SCREEN_NAME_CELL_XPATH);
		waitingAsserter.assertTextNotPresentInElement(firstScreenNameOnPageThree, SCREEN_NAME_CELL_XPATH);

		// 11. Take note of the screen name of the first user in the list on the last page.
		String firstScreenNameOnLastPage = browserDriver.findElementByXpath(SCREEN_NAME_CELL_XPATH).getText();
		logger.debug("firstScreenNameOnLastPage[{}]", firstScreenNameOnLastPage);

		// 12. Click on *First Page* button and verify that the page 1 button is highlighted and the *First Page* and
		// *Previous Page* buttons are disabled.
		browserDriver.clickElement(getNavigationButtonXpath("First"));
		waitingAsserter.assertTrue(pageButtonClassActive(1));
		waitingAsserter.assertTrue(navigationButtonClassDisabled("First"));
		waitingAsserter.assertTrue(navigationButtonClassDisabled("Previous"));

		// 13. Verify that the screen names noted from page 2, 3 and the last page are not present on the first page.
		waitingAsserter.assertTextNotPresentInElement(firstScreenNameOnPageTwo, SCREEN_NAME_CELL_XPATH);
		waitingAsserter.assertTextNotPresentInElement(firstScreenNameOnPageThree, SCREEN_NAME_CELL_XPATH);
		waitingAsserter.assertTextNotPresentInElement(firstScreenNameOnLastPage, SCREEN_NAME_CELL_XPATH);
	}

	@Test
	public void runPrimeFacesUsersPortletTest_B_UsersSort() {

		BrowserDriver browserDriver = getBrowserDriver();

		// 1. Take note of each screen name in the first column of the table, clicking the *Next* button until all
		// the screen names have been noted.
		List<String> screenNames = extractColumnValuesFromDataTable(browserDriver, "screenName");
		logList(screenNames, "Screen names extracted from the UI:");

		// 2. Sort the initial unsorted list of screen names to obtain the expected list sorted in ascending order.
		List<String> expectedScreenNamesAscending = new ArrayList<String>(screenNames);
		Collections.sort(expectedScreenNamesAscending);
		logList(expectedScreenNamesAscending, "Expected sorted list of screen names (ascending):");

		// 3. Click the *Screen Name* header to sort screen names into ascending order and verify that the sort
		// indication icon is pointed up.
		browserDriver.clickElement(SCREEN_NAME_SORT_BUTTON_XPATH);

		WaitingAsserter waitingAsserter = getWaitingAsserter();
		waitingAsserter.assertElementDisplayed(SORTED_ASCENDING_ICON_XPATH);

		// 4. Take note of each screen name in the first column of the table, clicking the *Next Page* button until
		// all the screen names have been noted.
		List<String> actualScreenNamesAscending = extractColumnValuesFromDataTable(browserDriver, "screenName");
		logList(actualScreenNamesAscending,
			"Actual screen names extracted from the UI after sorting by ascending order:");

		// 5. Verify that the sorted list obtained from the UI matches the order of the expected list (sorted in
		// ascending order).
		Assert.assertEquals(expectedScreenNamesAscending, actualScreenNamesAscending);

		// 6. Click the *First Page* button to go back to the first page of users.
		browserDriver.clickElement(getNavigationButtonXpath("First"));
		browserDriver.waitFor(pageButtonClassActive(1));

		// 7. Click the *Screen Name* header to sort screen names into descending order  and verify that the sort
		// indication icon is pointed down.
		browserDriver.clickElement(SCREEN_NAME_SORT_BUTTON_XPATH);
		waitingAsserter.assertElementDisplayed(SORTED_DESCENDING_ICON_XPATH);

		// 8. Take note of each screen name in the first column of the table, clicking the *Next Page* button until
		// all the screen names have been noted.
		List<String> actualScreenNamesDescending = extractColumnValuesFromDataTable(browserDriver, "screenName");
		logList(actualScreenNamesDescending,
			"Actual screen names extracted from the UI after sorting by descending order:");

		// 9. Sort the initial unsorted list of screen names to obtain the expected list sorted in descending order.
		List<String> expectedScreenNamesDescending = new ArrayList<String>(screenNames);
		Collections.sort(expectedScreenNamesDescending, Collections.reverseOrder());
		logList(expectedScreenNamesDescending, "Expected sorted list of screen names (descending):");

		// 10. Verify that the sorted list obtained from the UI matches the order of the expected list (sorted in
		// descending order).
		Assert.assertEquals(expectedScreenNamesDescending, actualScreenNamesDescending);

		// 11. Navigate back to the portlet via GET to reset its UI state.
		browserDriver.navigateWindowTo(getURL());
		browserDriver.waitForElementDisplayed(SCREEN_NAME_CELL_XPATH);
	}

	@Test
	public void runPrimeFacesUsersPortletTest_C_UsersFilter() {

		BrowserDriver browserDriver = getBrowserDriver();

		// 1. Click the *First Name* header to sort first names into ascending order and verify that the sort
		// indication icon is pointed up.
		browserDriver.clickElement(FIRST_NAME_SORT_BUTTON_XPATH);
		browserDriver.waitForElementDisplayed(SORTED_ASCENDING_ICON_XPATH);

		// 2. Take note of each first name in the third column of the table, clicking the *Next* button until all
		// the first names have been noted.
		String firstNameFilterText = getFirstNameFilterText();
		List<String> firstNames = extractColumnValuesFromDataTable(browserDriver, "firstName");

		// 3. Filter the initial unfiltered list of first names to obtain the expected list sorted in ascending order.
		List<String> expectedFilteredFirstNamesAscending = filterList(firstNames);
		logList(expectedFilteredFirstNamesAscending,
			"Expected list of first names (sorted in ascending order), filtered by text \"{0}\":", firstNameFilterText);

		//J-
		// 4. Enter "John" into the *First Name* column filter since only exact matching is supported.
		//J+
		filterColumnByText(browserDriver, FIRST_NAME_COLUMN_FILTER_XPATH, firstNameFilterText);

		// 5. Take note of each first name in the third column of the table, clicking the *Next* button until all
		// the first names have been noted.
		List<String> actualFilteredFirstNamesAscending = extractColumnValuesFromDataTable(browserDriver, "firstName");
		logList(actualFilteredFirstNamesAscending,
			"Actual list of first names (sorted in ascending order), filtered by text \"{0}\":", firstNameFilterText);

		// 6. Verify that the list obtained from the UI matches the expected filtered list (sorted in ascending order).
		Assert.assertEquals(expectedFilteredFirstNamesAscending, actualFilteredFirstNamesAscending);

		// 7. Sort the initial unfiltered list of first names, and filter the list to obtain the expected list sorted in
		// descending order.
		List<String> expectedFilteredFirstNamesDescending = new ArrayList<String>(firstNames);
		Collections.sort(expectedFilteredFirstNamesDescending, Collections.reverseOrder());
		expectedFilteredFirstNamesDescending = filterList(expectedFilteredFirstNamesDescending);
		logList(expectedFilteredFirstNamesDescending,
			"Expected list of first names (sorted in descending order), filtered by text \"{0}\":",
			firstNameFilterText);

		// 8. Click the *First Page* button to go back to the first page of users.
		browserDriver.clickElement(getNavigationButtonXpath("First"));
		browserDriver.waitFor(pageButtonClassActive(1));

		// 9. Click the *First Name* header to sort first names into descending order and verify that the sort
		// indication icon is pointed down.
		browserDriver.clickElement(FIRST_NAME_SORT_BUTTON_XPATH);
		browserDriver.waitForElementDisplayed(SORTED_DESCENDING_ICON_XPATH);

		// 10. Take note of each first name in the third column of the table, clicking the *Next* button until all
		// the first names have been noted.
		List<String> actualfilteredFirstNamesDescending = extractColumnValuesFromDataTable(browserDriver, "firstName");
		logList(actualfilteredFirstNamesDescending,
			"Actual list of first names (sorted in descending order), filtered by text \"{0}\":", firstNameFilterText);

		// 11. Verify that the list obtained from the UI matches the expected filtered list (sorted in descending
		// order).
		Assert.assertEquals(expectedFilteredFirstNamesDescending, actualfilteredFirstNamesDescending);

		// 12. Verify that the last page number button is highlighted and that the *Next Page* and *Last Page* buttons
		// are disabled.
		browserDriver.waitFor(navigationButtonClassDisabled("Last"));

		// 13. Click the *First Name* header to sort first names into ascending order and verify that the sort
		// indication icon is pointed up.
		browserDriver.clickElement(FIRST_NAME_SORT_BUTTON_XPATH);
		browserDriver.waitForElementDisplayed(SORTED_ASCENDING_ICON_XPATH);

		// 14. Clear the filter.
		WebElement screenNameCell = browserDriver.findElementByXpath(SCREEN_NAME_CELL_XPATH);
		browserDriver.clearElement(FIRST_NAME_COLUMN_FILTER_XPATH);
		browserDriver.waitFor(ExpectedConditions.stalenessOf(screenNameCell));
		browserDriver.waitForElementDisplayed(SCREEN_NAME_CELL_XPATH);

		// 15. Take note of each first name in the third column of the table, clicking the *Next* button until all
		// the first names have been noted.
		List<String> unfilteredFirstNames = extractColumnValuesFromDataTable(browserDriver, "firstName");

		// 16. Verify that the unfiltered list of first names from the UI is larger than the filtered lists and that the
		// unfiltered list matches the original list obtained from the UI.
		Assert.assertTrue((unfilteredFirstNames.size() > actualFilteredFirstNamesAscending.size()) &&
			(unfilteredFirstNames.size() > actualfilteredFirstNamesDescending.size()));
		Assert.assertEquals(firstNames, unfilteredFirstNames);

		// 17. Navigate back to the portlet via GET to reset its UI state.
		browserDriver.navigateWindowTo(getURL());
		browserDriver.waitForElementDisplayed(SCREEN_NAME_CELL_XPATH);

		// 18. Count the users with last name "Adams", clicking the *Next* button until all users with last name
		// "Adams" have been noted.
		int expectedAdamsUsersCount = 0;
		List<String> lastNames = extractColumnValuesFromDataTable(browserDriver, "lastName");

		for (String lastName : lastNames) {

			if (lastName.equals("Adams")) {
				expectedAdamsUsersCount++;
			}
		}

		// 19. Type "Adams" into the *First Name* column filter to test filtering by full value.
		filterColumnByText(browserDriver, LAST_NAME_COLUMN_FILTER_XPATH, "Adams");

		// 20. Take note of each last name in the second column of the table, clicking the *Next* button until all
		// the first names have been noted.
		List<String> actualAdamsList = extractColumnValuesFromDataTable(browserDriver, "lastName");

		// 21. Verify that the expected number of users with last name "Adams" matches the actual number of users with
		// last name "Adams" obtained from the UI after filtering.
		Assert.assertEquals(expectedAdamsUsersCount, actualAdamsList.size());

		// 22. Verify that last names obtained from the UI after filtering actually match "Adams".
		for (String actualAdams : actualAdamsList) {
			Assert.assertEquals("Adams", actualAdams);
		}

		// 23. Navigate back to the portlet via GET to reset its UI state.
		browserDriver.navigateWindowTo(getURL());
		browserDriver.waitForElementDisplayed(SCREEN_NAME_CELL_XPATH);
	}

	@Test
	public void runPrimeFacesUsersPortletTest_D_DetailViewErrorMessages() {

		BrowserDriver browserDriver = getBrowserDriver();

		// 1. Enter "john.adams" in the *Screen Name* filter so that *John Adams* appears as the only user in the
		// list.
		filterColumnByFullScreenName(browserDriver, "john.adams");

		// 2. Select *John Adams* from the list of users so that the corresponding user's data is displayed in an
		// editable form.
		browserDriver.clickElement(SCREEN_NAME_CELL_XPATH);
		browserDriver.waitForElementDisplayed(FIRST_NAME_FIELD_XPATH);

		// 3. Enter an invalid email address by removing the "@" symbol and click the *Submit* button.
		browserDriver.clearElement(EMAIL_ADDRESS_FIELD_XPATH);
		browserDriver.sendKeysToElement(EMAIL_ADDRESS_FIELD_XPATH, "john.adamsliferay.com");

		browserDriver.clickElement(SUBMIT_BUTTON_XPATH);

		// 4. Verify that the error message "Invalid Email Address" is displayed since only valid email addresses are
		// permitted.
		WaitingAsserter waitingAsserter = getWaitingAsserter();
		String errorMessageXpathSuffix = "/../span[@class='portlet-msg-error']";
		waitingAsserter.assertTextPresentInElement("Invalid Email Address",
			"//input[contains(@id,':emailAddress')]" + errorMessageXpathSuffix);

		// 5. Clear the *First Name*, *Last Name*, and *Email Address* fields and click the "Submit" button.
		browserDriver.clearElement(FIRST_NAME_FIELD_XPATH);
		browserDriver.clearElement(LAST_NAME_FIELD_XPATH);
		browserDriver.clearElement(EMAIL_ADDRESS_FIELD_XPATH);
		browserDriver.clickElement(SUBMIT_BUTTON_XPATH);

		// 6. Verify that the error message "Value is required" is displayed for *First Name*, *Last Name*, and *Email
		// Address* since they are all required fields.
		waitingAsserter.assertTextPresentInElement("Value is required",
			"//input[contains(@id,':firstName')]" + errorMessageXpathSuffix);
		waitingAsserter.assertTextPresentInElement("Value is required",
			"//input[contains(@id,':lastName')]" + errorMessageXpathSuffix);
		waitingAsserter.assertTextPresentInElement("Value is required",
			"//input[contains(@id,':emailAddress')]" + errorMessageXpathSuffix);

		// 7. Click the *Cancel* button to return to the list of users.
		browserDriver.clickElement(CANCEL_BUTTON_XPATH);
		browserDriver.waitForElementDisplayed(SCREEN_NAME_CELL_XPATH);
	}

	@Test
	public void runPrimeFacesUsersPortletTest_E_DetailViewFileUpload() throws IOException {

		BrowserDriver browserDriver = getBrowserDriver();

		// 1. Enter "john.adams" in the Screen Name filter so that *John Adams* appears as the only user in the
		// list.
		filterColumnByFullScreenName(browserDriver, "john.adams");

		// 2. Select *John Adams* from the list of users so that the corresponding user's data is displayed in an
		// editable form.
		browserDriver.clickElement(SCREEN_NAME_CELL_XPATH);
		browserDriver.waitForElementDisplayed(FIRST_NAME_FIELD_XPATH);

		// 3. Verify that the file upload chooser is displayed.
		String fileUploadChooserXpath = "//input[@type='file']";
		WebElement fileUploadChooser = browserDriver.findElementByXpath(fileUploadChooserXpath);

		// TECHNICAL NOTE:
		// Set PrimeFaces p:fileUpload transform style to "none" since it causes the element to not be displayed
		// according to Selenium (although the element is visible to users).
		browserDriver.executeScriptInCurrentWindow("arguments[0].style.transform = 'none';", fileUploadChooser);
		browserDriver.waitForElementEnabled(fileUploadChooserXpath + "/..");

		// 4. Prior to uploading an image, note the placeholder portrait that is displayed.
		String portraitXpath = "//div/img[contains(@id,':portrait')]";
		WebElement portraitElement = browserDriver.findElementByXpath(portraitXpath);
		Dimension originalPlaceholderPortraitSize = portraitElement.getSize();

		// 5. Click the *Choose* button and select "liferay-jsf-jersey.png" for upload.
		fileUploadChooser.sendKeys(getFileSystemPathForResource("liferay-jsf-jersey.png"));
		browserDriver.waitFor(ExpectedConditions.stalenessOf(portraitElement));

		WaitingAsserter waitingAsserter = getWaitingAsserter();
		waitingAsserter.assertElementDisplayed(portraitXpath);
		portraitElement = browserDriver.findElementByXpath(portraitXpath);

		// 6. Verify that the displayed portrait is different than the original placeholder portrait, and note the
		// displayed portrait.
		Dimension uploadedPortraitSize = portraitElement.getSize();
		Assert.assertFalse(areImageSizesEqual(originalPlaceholderPortraitSize, uploadedPortraitSize));

		// 7. Click the *Submit* button to submit the portrait update and go back to the list of users.
		browserDriver.clickElement(SUBMIT_BUTTON_XPATH);
		browserDriver.waitForElementDisplayed(SCREEN_NAME_CELL_XPATH);

		// 8. Select *John Adams* from the list of users so that the corresponding user's data is displayed in an
		// editable form.
		browserDriver.clickElement(SCREEN_NAME_CELL_XPATH);
		browserDriver.waitForElementDisplayed(FIRST_NAME_FIELD_XPATH);

		// 9. Verify that the displayed portrait is the same uploaded portrait and different than the original
		// placeholder portrait.
		portraitElement = browserDriver.findElementByXpath(portraitXpath);

		Dimension expectedUploadedPortraitSize = uploadedPortraitSize;
		Dimension actualUploadedPortraitSize = portraitElement.getSize();
		Assert.assertTrue(areImageSizesEqual(expectedUploadedPortraitSize, actualUploadedPortraitSize));
		Assert.assertFalse(areImageSizesEqual(originalPlaceholderPortraitSize, actualUploadedPortraitSize));

		// 10. Click the *Cancel* button to go back to the list of users.
		browserDriver.clickElement(CANCEL_BUTTON_XPATH);
		browserDriver.waitForElementDisplayed(SCREEN_NAME_CELL_XPATH);

		// 11. Clear the filter to see the list of all the users again.
		browserDriver.clearElement(SCREEN_NAME_COLUMN_FILTER_XPATH);
		browserDriver.waitForElementDisplayed(SCREEN_NAME_CELL_2_XPATH);
	}

	@Test
	public void runPrimeFacesUsersPortletTest_F_DetailViewChangeUserAttributes() {

		BrowserDriver browserDriver = getBrowserDriver();

		// 1. Enter "john.adams" in the Screen Name filter so that *John Adams* appears as the only user in the
		// list.
		filterColumnByFullScreenName(browserDriver, "john.adams");

		// 2. Select *John Adams* from the list of users so that the corresponding user's data is displayed in an
		// editable form.
		browserDriver.clickElement(SCREEN_NAME_CELL_XPATH);

		// 3. Change the user's Last Name from "Adams" to "Adamsy"
		browserDriver.waitForElementEnabled(LAST_NAME_FIELD_XPATH);
		browserDriver.clearElement(LAST_NAME_FIELD_XPATH);
		browserDriver.sendKeysToElement(LAST_NAME_FIELD_XPATH, "Adamsy");

		// 4. Change the user's First Name from "John" to "Johnny"
		browserDriver.clearElement(FIRST_NAME_FIELD_XPATH);
		browserDriver.sendKeysToElement(FIRST_NAME_FIELD_XPATH, "Johnny");

		// 5. Change the user's Email Address to "johnny.adamsy@liferay.com"
		browserDriver.clearElement(EMAIL_ADDRESS_FIELD_XPATH);
		browserDriver.sendKeysToElement(EMAIL_ADDRESS_FIELD_XPATH, "johnny.adamsy@liferay.com");

		// 6. Click the *Submit* button to go back to the list of users.
		browserDriver.clickElement(SUBMIT_BUTTON_XPATH);
		browserDriver.waitForElementDisplayed(SCREEN_NAME_CELL_XPATH);

		// 7. Enter "john.adams" in the Screen Name filter so that *John Adams* appears as the only user in the
		// list.
		filterColumnByFullScreenName(browserDriver, "john.adams");

		// 8. Verify that the list contains "Adamsy" in the last name column, "Johnny" in the first name column, and
		// "johnny.adamsy@liferay.com" in the email address column.
		WaitingAsserter waitingAsserter = getWaitingAsserter();
		waitingAsserter.assertTextPresentInElement("Adamsy", "//span[contains(@id,':lastNameCell')]");
		waitingAsserter.assertTextPresentInElement("Johnny", FIRST_NAME_CELL_XPATH);
		waitingAsserter.assertTextPresentInElement("johnny.adamsy@liferay.com", "//td//a[contains(@id,':email')]");
	}

	/**
	 * This method ensures that the state of the UI and data are reset before testing.
	 */
	@Before
	public void setUpPrimeFacesUsersPortletTester() {

		if (!isStateReset) {

			//J-
			// 1. Navigate to the PrimeFaces Users portlet.
			// 2. Click the hidden *Reset Users* button to reset the state of the user data in preparation
			// for testing.
			//J+
			BrowserDriver browserDriver = getBrowserDriver();
			resetTestUsers(browserDriver);

			// 3. Click the *Preferences* link to navigate to edit mode.
			String preferencesLinkXpath = "//a[contains(@id,':preferences')]";
			browserDriver.clickElement(preferencesLinkXpath);

			// 4. Enter "5" for the number of rows-per-page so that the pagination can be better tested with a small
			// data set.
			String rowsPerPageInputXpath = "//input[contains(@id,':rowsPerPage')]";
			browserDriver.waitForElementEnabled(rowsPerPageInputXpath);
			browserDriver.clearElement(rowsPerPageInputXpath);
			browserDriver.sendKeysToElement(rowsPerPageInputXpath, "5");

			// 5. Click the *Submit* button to save the rows-per-page preference.
			browserDriver.clickElement("//input[contains(@id,':submit')]");
			browserDriver.waitForElementDisplayed(SCREEN_NAME_CELL_XPATH);
			isStateReset = true;
		}
	}

	private boolean areImageSizesEqual(Dimension expectedDimensions, Dimension actualDimensions) {

		// Image sizes may be rounded from floating point values. So instead of verifying that the sizes are exactly
		// equal, verify that the actual size is one less than, one greater than, or exactly the same as the expected
		// size.
		int expectedHeight = expectedDimensions.getHeight();
		int expectedWidth = expectedDimensions.getWidth();

		int expectedHeightMin = expectedHeight - 1;
		int expectedWidthMin = expectedWidth - 1;
		int expectedHeightMax = expectedHeight + 1;
		int expectedWidthMax = expectedWidth + 1;
		int actualHeight = actualDimensions.getHeight();
		int actualWidth = actualDimensions.getWidth();

		return ((expectedHeightMin <= actualHeight) && (actualHeight <= expectedHeightMax)) &&
			((expectedWidthMin <= actualWidth) && (actualWidth <= expectedWidthMax));
	}

	/**
	 * Extract the column values from the dataTable associated with the specified column name.
	 */
	private List<String> extractColumnValuesFromDataTable(BrowserDriver browserDriver, String columnName) {

		// While the *Last Page* button is still active:
		List<String> columnValues = new ArrayList<String>();
		String columnElementsXpath = "//span[contains(@id,':" + columnName + "Cell')]";

		while (!isNavigationButtonDisabled(browserDriver)) {

			// Extract the column values from the current page of the dataTable.
			List<WebElement> columnElements = browserDriver.findElementsByXpath(columnElementsXpath);

			for (WebElement columnElement : columnElements) {

				String columnValue = columnElement.getText();
				columnValues.add(columnValue);
			}

			// Click the *Next Page* button.
			Action clickNextPageAction = browserDriver.createClickElementAction(getNavigationButtonXpath("Next"));
			browserDriver.performAndWaitForRerender(clickNextPageAction, columnElementsXpath);
		}

		// Extract column values from the last page of the dataTable.
		List<WebElement> screenNameElements = browserDriver.findElementsByXpath(columnElementsXpath);

		for (WebElement screenNameElement : screenNameElements) {

			String screenName = screenNameElement.getText();
			columnValues.add(screenName);
		}

		return Collections.unmodifiableList(columnValues);
	}

	private void filterColumnByFullScreenName(BrowserDriver browserDriver, String screenName) {

		browserDriver.clearElement(SCREEN_NAME_COLUMN_FILTER_XPATH);
		browserDriver.sendKeysToElement(SCREEN_NAME_COLUMN_FILTER_XPATH, screenName);
		browserDriver.waitForElementNotDisplayed(SCREEN_NAME_CELL_2_XPATH);
		browserDriver.waitForElementDisplayed(SCREEN_NAME_CELL_XPATH);
	}

	private void filterColumnByText(BrowserDriver browserDriver, String filterInputXpath, String filterText) {

		WebElement firstNameFilterElement = browserDriver.findElementByXpath(filterInputXpath);
		Action filterFirstNameAction = browserDriver.createActions(filterInputXpath).sendKeys(firstNameFilterElement,
				filterText).build();
		browserDriver.performAndWaitForRerender(filterFirstNameAction, SCREEN_NAME_CELL_XPATH);
	}

	private List<String> filterList(List<String> firstNameList) {

		List<String> filteredFirstNames = new ArrayList<String>();

		for (String firstName : firstNameList) {

			if (isFirstNameMatch(firstName)) {
				filteredFirstNames.add(firstName);
			}
		}

		return Collections.unmodifiableList(filteredFirstNames);
	}

	private String getNavigationButtonXpath(String buttonAriaLabelText) {
		return
			"//div[contains(@class,'ui-paginator')]/a[contains(@class,'ui-paginator-')][contains(@aria-label,'Page')][contains(@aria-label,'" +
			buttonAriaLabelText + "')]";
	}

	private String getPageButtonXpath(int buttonPageNumber) {
		return PAGE_BUTTON_XPATH_PREFIX + "[contains(@aria-label,'" + Integer.toString(buttonPageNumber) + "')]";
	}

	private boolean isNavigationButtonDisabled(BrowserDriver browserDriver) {

		ExpectedCondition<Boolean> navigationButtonClassDisabledCondition = navigationButtonClassDisabled("Last");
		WebDriver webDriver = browserDriver.getWebDriver();

		return navigationButtonClassDisabledCondition.apply(webDriver);
	}

	private void logList(List<String> list, String message, Object... arguments) {

		logger.debug("----------------------------------------------------------------------");
		logger.debug(message, arguments);

		for (String firstName : list) {
			logger.debug(firstName);
		}
	}

	private ExpectedCondition<Boolean> navigationButtonClassDisabled(String buttonAriaLabelText) {

		String pageButtonXpath = getNavigationButtonXpath(buttonAriaLabelText);

		return textPresentInElementClass("ui-state-disabled", pageButtonXpath);
	}

	private ExpectedCondition<Boolean> pageButtonClassActive(int pageButtonNumber) {

		String pageButtonXpath = getPageButtonXpath(pageButtonNumber);

		return textPresentInElementClass("ui-state-active", pageButtonXpath);
	}

	private ExpectedCondition<Boolean> textPresentInElementClass(String text, String elementXpath) {

		By locator = By.xpath(elementXpath);
		ExpectedCondition<WebElement> elementDisplayed = ExpectedConditions.visibilityOfElementLocated(locator);
		ExpectedCondition<Boolean> textPresentInElementClass = ExpectedConditions.attributeContains(locator, "class",
				text);

		return ExpectedConditions.and(elementDisplayed, textPresentInElementClass);
	}
}
