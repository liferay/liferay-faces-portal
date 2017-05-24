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

import com.liferay.faces.test.selenium.IntegrationTesterBase;
import com.liferay.faces.test.selenium.TestUtil;
import com.liferay.faces.test.selenium.browser.BrowserDriver;
import com.liferay.faces.test.selenium.browser.BrowserStateAsserter;


/**
 * @author  Vernon Singleton
 * @author  Philip White
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PrimeFacesUsersPortletTester extends IntegrationTesterBase {

	private static final String firstNameFieldXpath = "//input[contains(@id,':firstName')]";
	private static final String lastNameFieldXpath = "//input[contains(@id,':lastName')]";
	private static final String emailAddressFieldXpath = "//input[contains(@id,':emailAddress')]";
	private static final String firstUserScreenNameXpath = "//span[contains(@id,':screenNameCell')]";
	private static final String secondUserScreenNameCellXpath = "(//span[contains(@id,':screenNameCell')])[2]";
	private static final String firstNameColumnFilterXpath = "//input[contains(@id,'users:firstName:filter')]";
	private static final String screenNameColumnFilterXpath = "//input[contains(@id,'users:screenName:filter')]";

	/**
	 * Reset user John Adams and retrieve other users from database if not already there.
	 */
	@Test
	public void runPrimeFacesUsersPortletTest_A_Reset_User_Data() {

		// Sign into the Liferay Portal and navigate to the PrimeFaces Users portlet.
		BrowserDriver browserDriver = getBrowserDriver();

		browserDriver.navigateWindowTo(
			"http://localhost:8080/group/control_panel/manage?p_p_id=1_WAR_primefacesusersportlet");

		browserDriver.waitForElementDisplayed(firstUserScreenNameXpath);

		WebElement hiddenButtonResetUsers = browserDriver.findElementByXpath(
				"(//button[contains(@id,':hiddenButtonResetUsers')]/span)");
		browserDriver.executeScriptInCurrentWindow("arguments[0].click();", hiddenButtonResetUsers);

		WebElement firstUserScreenNameCell = browserDriver.findElementByXpath(firstUserScreenNameXpath);

		browserDriver.waitFor(ExpectedConditions.stalenessOf(firstUserScreenNameCell));
		browserDriver.waitForElementDisplayed(firstUserScreenNameXpath);
	}

	/**
	 * Verify that every title and field is visible on the portlet.
	 */
	@Test
	public void runPrimeFacesUsersPortletTest_B_DetailView() {

		// Navigate to user "John Adams".
		BrowserDriver browserDriver = getBrowserDriver();

		browserDriver.navigateWindowTo(
			"http://localhost:8080/group/control_panel/manage?p_p_id=1_WAR_primefacesusersportlet");

		browserDriver.waitForElementDisplayed(firstUserScreenNameXpath);

		BrowserStateAsserter browserStateAsserter = getBrowserStateAsserter();
		verifyFirstUser(browserDriver, browserStateAsserter, "john.adams");
		browserDriver.clickElement(firstUserScreenNameXpath);

		browserDriver.waitForElementDisplayed(firstNameFieldXpath);

		// TODO: placed Thread.sleep(); here to see why staleElement reference failure, and then failure magically went
		// away.

		// TODO: change key word into the i18n value for that key.

		// Enter an invalid email address by removing the "@" symbol and verify that the error message
		// "invalid-email-address" is displayed.
		browserDriver.clearElement(emailAddressFieldXpath);

		browserDriver.sendKeysToElement(emailAddressFieldXpath, "john.adamsliferay.com");

		String submitButtonXpath = "//button[contains(@id, ':pushButtonSubmit') and @type='submit']";
		browserDriver.clickElement(submitButtonXpath);

		browserStateAsserter.assertElementDisplayed(
			"//input[contains(@id,':emailAddress')]/../span[@class='portlet-msg-error' and text()='invalid-email-address']");

		// Clear values for John Adams to assert required values
		browserDriver.clearElement(firstNameFieldXpath);

		browserDriver.clearElement(lastNameFieldXpath);

		browserDriver.clearElement(emailAddressFieldXpath);

		browserDriver.clickElement(submitButtonXpath);

		// Verify that the error message "Value is required" is displayed above required values.
		browserStateAsserter.assertElementDisplayed(
			"//input[contains(@id,':firstName')]/../span[@class='portlet-msg-error' and text()='Value is required']");

		browserStateAsserter.assertElementDisplayed(
			"//input[contains(@id,':lastName')]/../span[@class='portlet-msg-error' and text()='Value is required']");

		browserStateAsserter.assertElementDisplayed(
			"//input[contains(@id,':emailAddress')]/../span[@class='portlet-msg-error' and text()='Value is required']");

		// TODO talk to Neil, should we assert that the cancelled button has returned us to the correct view.  In
		// other words, are we testing the cancel button functionality?

		// Click on the "Cancel" button to go back to the list of users.
		browserDriver.clickElement("//button[contains(@id, ':pushButtonCancel') and @type='submit']");
		browserDriver.waitForElementDisplayed(firstUserScreenNameXpath);

		verifyFirstUser(browserDriver, browserStateAsserter, "john.adams");

		// Clear the filter so that we see the list of all the users again.
		browserDriver.clearElement(screenNameColumnFilterXpath);
		browserStateAsserter.assertElementDisplayed(secondUserScreenNameCellXpath);

		// Verify that when "Adams" is entered into the last name filter that only "Samuel Adams" and "John Adams" are
		// still present in the user list.
		String lastNameColumnFilterXpath = "//input[contains(@id,'users:lastName:filter')]";
		browserDriver.sendKeysToElement(lastNameColumnFilterXpath, "Adams");

		String thirdUserScreenNameCellXpath = "(//span[contains(@id,':screenNameCell')])[3]";
		browserStateAsserter.assertElementNotDisplayed(thirdUserScreenNameCellXpath);

		browserStateAsserter.assertElementDisplayed(firstUserScreenNameXpath);

		browserStateAsserter.assertElementDisplayed(secondUserScreenNameCellXpath);

		assertElementCount(browserDriver, "//tbody//tr", 2);

		// Clear the filter so that we see the list of all the users again.
		browserDriver.clearElement(lastNameColumnFilterXpath);
		browserStateAsserter.assertElementDisplayed(thirdUserScreenNameCellXpath);
	}

	/**
	 * Click on the navigation buttons and verify that the pages' list of users are properly displayed to test
	 * paginator.
	 */
	@Test
	public void runPrimeFacesUsersPortletTest_C_Paginator() {

		BrowserDriver browserDriver = getBrowserDriver();
		BrowserStateAsserter browserStateAsserter = getBrowserStateAsserter();

		browserDriver.navigateWindowTo(
			"http://localhost:8080/group/control_panel/manage?p_p_id=1_WAR_primefacesusersportlet");

		// Take note of the screen name of the first user in the list on page 1.
		String firstUserPageOne = browserDriver.findElementByXpath(firstUserScreenNameXpath).getText();

		System.out.println("firstUserPageOne = " + firstUserPageOne);

		// Click on the 'Next Page' button and verify that the page 2 button is highlighted.
		browserDriver.clickElement("//a[contains(@aria-label,'Next Page')]");
		browserDriver.waitForElementDisplayed("//span/a[contains(@class,'ui-state-active') and text()='2']");

		// Verify that the screen name noted from page 1 is not present on page 2.
		browserStateAsserter.assertElementNotDisplayed(firstUserPageOne);
		browserStateAsserter.assertTextNotPresentInElement(firstUserPageOne, firstUserScreenNameXpath);

		// Take note of the screen name of the first user in the list on page 2.
		String firstUserPageTwo = browserDriver.findElementByXpath(firstUserScreenNameXpath).getText();

		System.out.println("firstUserPageTwo = " + firstUserPageTwo);

		// Click on the '3' button and verify that it is highlighted.
		browserDriver.clickElement("//a[contains(@aria-label,'Page 3')]");
		browserDriver.waitForElementDisplayed("//span/a[contains(@class,'ui-state-active') and text()='3']");

		// Verify that the screen names noted from page 1 and 2 are not present on page 3.
		browserStateAsserter.assertTextNotPresentInElement(firstUserPageOne, firstUserScreenNameXpath);
		browserStateAsserter.assertTextNotPresentInElement(firstUserPageTwo, firstUserScreenNameXpath);

		// Take note of the screen name of the first user in the list on page 3.
		String firstUserPageThree = browserDriver.findElementByXpath(firstUserScreenNameXpath).getText();

		System.out.println("firstUserPageThree = " + firstUserPageThree);

		// Click on 'Previous Page' button and verify that the page 2 button is highlighted.
		browserDriver.clickElement("//a[contains(@aria-label,'Previous Page')]");
		browserDriver.waitForElementDisplayed("//span/a[contains(@class,'ui-state-active') and text()='2']");

		// Verify that the screen names noted from page 1 and 3 are not present on page 2.
		browserStateAsserter.assertTextNotPresentInElement(firstUserPageOne, firstUserScreenNameXpath);
		browserStateAsserter.assertTextNotPresentInElement(firstUserPageThree, firstUserScreenNameXpath);

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
		browserStateAsserter.assertTextNotPresentInElement(firstUserPageOne, firstUserScreenNameXpath);
		browserStateAsserter.assertTextNotPresentInElement(firstUserPageTwo, firstUserScreenNameXpath);
		browserStateAsserter.assertTextNotPresentInElement(firstUserPageThree, firstUserScreenNameXpath);

		// Take note of the screen name of the first user in the list on the last page.
		String firstUserLastPage = browserDriver.findElementByXpath(firstUserScreenNameXpath).getText();

		System.out.println("firstUserLastPage = " + firstUserLastPage);

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
		browserStateAsserter.assertTextNotPresentInElement(firstUserPageTwo, firstUserScreenNameXpath);
		browserStateAsserter.assertTextNotPresentInElement(firstUserPageThree, firstUserScreenNameXpath);
		browserStateAsserter.assertTextNotPresentInElement(firstUserLastPage, firstUserScreenNameXpath);
	}

	/**
	 * Verify that the pages' list of users are properly displayed and sorted from clicking the screen name header.
	 */
	@Test
	public void runPrimeFacesUsersPortletTest_D_Sort() {

		BrowserDriver browserDriver = getBrowserDriver();

		browserDriver.navigateWindowTo(
			"http://localhost:8080/group/control_panel/manage?p_p_id=1_WAR_primefacesusersportlet");

		// Click on the Screen Name header to sort screen names into ascending order and verify that the triangle icon
		// is pointed up.
		browserDriver.clickElement("//th[contains(@id,'users:screenName')]/child::span[1]");
		browserDriver.waitForElementDisplayed("(//span[contains(@class,'ui-icon-triangle-1-n')])");

		// Take note of each screen name in the first column of the table, clicking on the Next Button until all the
		// screen names have been noted.
		System.out.println("Sorted ascending");

		List<String> screenNames = extractColumnValuesFromDataTable(browserDriver, "screenName");

		// Verify that the list of screen names are in alphabetical order.
		List<String> sortedScreenNames = new ArrayList<String>(screenNames);

		Collections.sort(sortedScreenNames);

		System.out.println("screen names after sorting:");

		for (String sortedScreenName : sortedScreenNames) {
			System.out.println(sortedScreenName);
		}

		Assert.assertTrue(screenNames.equals(sortedScreenNames));

		// Start the sort test in descending order on Page 1.
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

		// Verify that the list of screen names are in reverse alphabetical order.
		sortedScreenNames = new ArrayList<String>(screenNames);

		Collections.sort(sortedScreenNames, Collections.reverseOrder());

		System.out.println("screen names after sorting:");

		for (String sortedScreenName : sortedScreenNames) {
			System.out.println(sortedScreenName);
		}

		Assert.assertTrue(screenNames.equals(sortedScreenNames));
	}

	/**
	 * Type the letter "O" into the First Name Column Text Filter and verify that the pages' list of users are properly
	 * displayed.
	 */
	@Test
	public void runPrimeFacesUsersPortletTest_E_FirstNameColumnTextFilter() {

		BrowserDriver browserDriver = getBrowserDriver();

		browserDriver.navigateWindowTo(
			"http://localhost:8080/group/control_panel/manage?p_p_id=1_WAR_primefacesusersportlet");

		// Start the sort test of First Name filtering in ascending order on Page 1.
		browserDriver.clickElement("//a[contains(@aria-label,'First Page')]");
		browserDriver.waitForElementDisplayed(
			"(//span[contains(@class,'ui-paginator-pages')]/a[contains(@class,'ui-state-active')])[1]");

		// Click on the First Name header to sort first names into ascending order and verify that the triangle icon
		// is pointed up.
		browserDriver.clickElement("//th[contains(@id,'users:firstName')]/child::span[1]");
		browserDriver.waitForElementDisplayed("(//span[contains(@class,'ui-icon-triangle-1-n')])");

		// Verify that when "O" is entered into the first name filter that only users with the letter "O" are still
		// present in the user list.
		WebElement firstNameFilterElement = browserDriver.findElementByXpath(firstNameColumnFilterXpath);
		Actions filterActions = browserDriver.createActions(firstNameColumnFilterXpath);
		filterActions.sendKeys(firstNameFilterElement, "O");
		browserDriver.performAndWaitForRerender(filterActions.build(), "(//span[contains(@id,':firstNameCell')])[1]");

		assertElementCount(browserDriver, "//tbody//tr", 5);

		// Take note of each first name in the third column of the table, clicking on the Next Button until all the
		// first names have been noted.
		List<String> firstNames = extractColumnValuesFromDataTable(browserDriver, "firstName");

		// Verify that the list of first names are in alphabetical order.
		List<String> sortedFirstNames = new ArrayList<String>(firstNames);

		Collections.sort(sortedFirstNames);

		System.out.println("first names after sorting:");

		for (String sortedFirstName : sortedFirstNames) {
			System.out.println(sortedFirstName);
			Assert.assertTrue(sortedFirstName.contains("o") || sortedFirstName.contains("O")); // Thomas or Oliver
		}

		Assert.assertTrue(firstNames.equals(sortedFirstNames));

		// Start the sort test of First Name filtering in descending order on Page 1.
		browserDriver.clickElement("//a[contains(@aria-label,'First Page')]");
		browserDriver.waitForElementDisplayed(
			"(//span[contains(@class,'ui-paginator-pages')]/a[contains(@class,'ui-state-active')])[1]");

		// Click on the First Name header to sort first names into descending order and verify that the triangle icon
		// is pointed down.
		browserDriver.clickElement("//th[contains(@id,'users:firstName')]/child::span[1]");
		browserDriver.waitForElementDisplayed("(//span[contains(@class,'ui-icon-triangle-1-s')])");

		// Take note of each first name in the third column of the table, clicking on the Next Button until all the
		// first names have been noted.
		firstNames = extractColumnValuesFromDataTable(browserDriver, "firstName");

		// Verify that the list of first names are in reverse alphabetical order.
		sortedFirstNames = new ArrayList<String>(firstNames);

		Collections.sort(sortedFirstNames, Collections.reverseOrder());

		System.out.println("first names after sorting:");

		for (String sortedFirstName : sortedFirstNames) {
			System.out.println(sortedFirstName);
			Assert.assertTrue(sortedFirstName.contains("o") || sortedFirstName.contains("O")); // Thomas or Oliver
		}

		Assert.assertTrue(firstNames.equals(sortedFirstNames));

		// Clear the filter so that we see the list of all the users again.
		browserDriver.clearElement(firstNameColumnFilterXpath);

		BrowserStateAsserter browserStateAsserter = getBrowserStateAsserter();
		browserStateAsserter.assertElementDisplayed(
			"//span[contains(@id,':firstNameCell') and contains(text(), 'Thomas')]");

		browserDriver.clickElement("//th[contains(@id,'users:lastName')]/child::span[1]");
		browserDriver.waitForElementDisplayed("(//span[contains(@class,'ui-icon-triangle-1-n')])");
	}

	/**
	 * Verify that changing user "John Adams"' attributes are visible on the portlet.
	 */
	@Test
	public void runPrimeFacesUsersPortletTest_F_ChangeUserAttributes() {

		// Navigate to user "John Adams".
		BrowserDriver browserDriver = getBrowserDriver();

		browserDriver.navigateWindowTo(
			"http://localhost:8080/group/control_panel/manage?p_p_id=1_WAR_primefacesusersportlet");

		browserDriver.waitForElementDisplayed(firstUserScreenNameXpath);

		BrowserStateAsserter browserStateAsserter = getBrowserStateAsserter();
		verifyFirstUser(browserDriver, browserStateAsserter, "john.adams");
		browserDriver.clickElement(firstUserScreenNameXpath);

		// Change user's Last Name to "Adamsy"
		browserDriver.waitForElementDisplayed(lastNameFieldXpath);
		browserDriver.clearElement(lastNameFieldXpath);

		String lastName = "Adamsy";
		browserDriver.sendKeysToElement(lastNameFieldXpath, lastName);

		// Change user's First Name to "Johnny"
		browserDriver.clearElement(firstNameFieldXpath);
		browserDriver.clearElement(firstNameFieldXpath);

		String firstName = "Johnny";
		browserDriver.sendKeysToElement(firstNameFieldXpath, firstName);

		// Change user's Email Address to "johnny.adamsy@liferay.com"
		browserDriver.clearElement(emailAddressFieldXpath);
		browserDriver.sendKeysToElement(emailAddressFieldXpath, "johnny.adamsy@liferay.com");

		// Click on the "Submit" button and go back to the list of users.
		browserDriver.clickElement("//button[contains(@id, ':pushButtonSubmit') and @type='submit']");
		browserDriver.waitForElementDisplayed(firstUserScreenNameXpath);

		verifyFirstUser(browserDriver, browserStateAsserter, "john.adams");

		// Verify that user "John Adams"' last name, first name, and email address attributes have been changed to
		// "Adams", "Johnny", and "johnny.adamsy@liferay.com", respectively.
		browserStateAsserter.assertTextPresentInElement(lastName, "//span[contains(@id,':lastNameCell')]");
		browserStateAsserter.assertTextPresentInElement(firstName, "//span[contains(@id,':firstNameCell')]");
		browserStateAsserter.assertElementDisplayed("//a[contains(.,'johnny.adamsy@liferay.com')]");

		// Clear the filter so that we see the list of all the users again.
		browserDriver.clearElement(screenNameColumnFilterXpath);
		browserStateAsserter.assertElementDisplayed(secondUserScreenNameCellXpath);
	}

	/**
	 * Verify that when a picture file is uploaded that it is visible.
	 */
	@Test
	public void runPrimeFacesUsersPortletTest_G_FileUpload() {

		BrowserDriver browserDriver = getBrowserDriver();

		browserDriver.navigateWindowTo(
			"http://localhost:8080/group/control_panel/manage?p_p_id=1_WAR_primefacesusersportlet");

		// Navigate to first user.
		BrowserStateAsserter browserStateAsserter = getBrowserStateAsserter();
		verifyFirstUser(browserDriver, browserStateAsserter, "john.adams");

		browserDriver.clickElement(firstUserScreenNameXpath);
		browserDriver.waitForElementDisplayed(firstNameFieldXpath);

		String fileUploadChooserXpath = "//input[@type='file']";

		browserStateAsserter.assertElementDisplayed(fileUploadChooserXpath + "/..");

		String portraitXpath = "//div[contains(@class,'has-success')]/img[contains(@id,':portrait')]";
		WebElement imgWebElement = browserDriver.findElementByXpath(portraitXpath);
		Dimension imageSizeBefore = imgWebElement.getSize();
		WebElement fileUploadChooser = browserDriver.findElementByXpath(fileUploadChooserXpath);

		// TECHNICAL NOTE:
		// Set PrimeFaces p:fileUpload transform style to "none" since it causes the element to not be displayed
		// according to Selenium (although the element is visible to users).
		browserDriver.executeScriptInCurrentWindow("arguments[0].style.transform = 'none';", fileUploadChooser);

		fileUploadChooser.sendKeys(TestUtil.JAVA_IO_TMPDIR + "liferay-jsf-jersey.png");

		browserDriver.waitFor(ExpectedConditions.stalenessOf(imgWebElement));
		browserStateAsserter.assertElementDisplayed(portraitXpath);

		imgWebElement = browserDriver.findElementByXpath(portraitXpath);

		Dimension imageSizeAfter = imgWebElement.getSize();
		Assert.assertNotEquals(imageSizeBefore.getHeight(), imageSizeAfter.getHeight());
	}

	/**
	 * Verifying that the number of elements present in the DOM associated with the specified xpath is equal to the
	 * specified value.
	 *
	 * @param  browserDriver
	 * @param  xpath
	 * @param  expecteds
	 */
	protected final void assertElementCount(BrowserDriver browserDriver, String xpath, int expecteds) {
		List<WebElement> elements = browserDriver.findElementsByXpath(xpath);
		Assert.assertNotNull("Element " + elements + " is not present in the DOM.", elements);

		int elementsSize = elements.size();
		Assert.assertEquals("Element " + elements + " does not equal \"" + expecteds + "\". Instead it equals \"" +
			elementsSize + "\".", expecteds, elementsSize);
	}

	/**
	 * Exctract the column values from the dataTable associated with the specified column name.
	 *
	 * @param  browserDriver
	 * @param  columnName
	 */
	protected final List<String> extractColumnValuesFromDataTable(BrowserDriver browserDriver, String columnName) {
		List<String> columnValues = new ArrayList<String>();

		System.out.println("--------------");
		System.out.println(columnName + " values:");

		String columnElementsXpath = "//span[contains(@id,':" + columnName + "Cell')]";

		while (browserDriver.findElementsByXpath(
					"//a[contains(@class,'ui-state-disabled') and contains(@aria-label,'Last Page')]").isEmpty()) {

			List<WebElement> columnElements = browserDriver.findElementsByXpath(columnElementsXpath);

			for (WebElement columnElement : columnElements) {
				String columnValue = columnElement.getText();
				columnValues.add(columnValue);
				System.out.println(columnValue);
			}

			browserDriver.performAndWaitForRerender(browserDriver.createClickElementAction(
					"//a[contains(@aria-label,'Next Page')]"), "(//span[contains(@id,':" + columnName + "Cell')])[1]");
		}

		List<WebElement> screenNameElements = browserDriver.findElementsByXpath(columnElementsXpath);

		for (WebElement screenNameElement : screenNameElements) {
			String screenName = screenNameElement.getText();
			columnValues.add(screenNameElement.getText());
			System.out.println(screenName);
		}

		return columnValues;
	}

	/**
	 * Verify that when a user's screen name is entered into the screen name filter that only "john.adams" is still
	 * present in the list of users.
	 *
	 * @param  browserDriver
	 * @param  browserStateAsserter
	 */
	protected final void verifyFirstUser(BrowserDriver browserDriver, BrowserStateAsserter browserStateAsserter,
		String screenName) {
		browserDriver.clearElement(screenNameColumnFilterXpath);
		browserDriver.sendKeysToElement(screenNameColumnFilterXpath, screenName);

		browserStateAsserter.assertElementNotDisplayed(secondUserScreenNameCellXpath);
		browserStateAsserter.assertElementDisplayed(firstUserScreenNameXpath);

		String rowsXpath = "//tbody//tr";
		assertElementCount(browserDriver, rowsXpath, 1);
	}
}
