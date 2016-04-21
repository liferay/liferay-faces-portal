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
package com.liferay.faces.test;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * @author  Kyle Stiemann
 */
public class Browser {

	private static WebDriver webDriver = null;
	private static WebDriverWait wait = null;
	private static Browser instance = null;
	private static final Logger logger = Logger.getLogger(Browser.class.getName());

	static {

		String logLevelString = System.getProperty("integration.log.level", "WARNING");
		Level logLevel = Level.parse(logLevelString);
		logger.setLevel(logLevel);
	}

	private Browser() {

		String browser = System.getProperty("integration.browser", "firefox");

		if ("phantomjs".equals(browser)) {
			webDriver = new PhantomJSDriver();
		}
		else if ("chrome".equals(browser)) {
			webDriver = new ChromeDriver();
		}
		else if ("firefox".equals(browser)) {
			webDriver = new FirefoxDriver();
		}

		webDriver.manage();
		wait = new WebDriverWait(webDriver, 5);
	}

	public static Browser getInstance() {

		if (instance == null) {
			instance = new Browser();
		}

		return instance;
	}

	public void assertElementPresent(String xpath) {

		WebElement element = getElement(xpath);
		Assert.assertNotNull("Element " + xpath + " is not present in the DOM.", element);
	}

	public void assertElementTextVisible(String xpath, String text) {

		WebElement element = getElement(xpath);
		Assert.assertNotNull("Element " + xpath + " is not present in the DOM.", element);

		boolean elementDisplayed = element.isDisplayed();
		Assert.assertTrue("Element " + xpath + " is not displayed.", elementDisplayed);

		String elementText = element.getText();
		Assert.assertEquals("Element " + xpath + " does not contain text \"" + text +
			"\". Instead it contains text \"" + elementText + "\".", text, elementText);
	}
	
	public void assertElementValue(String xpath, String text) {

		WebElement element = getElement(xpath);
		Assert.assertNotNull("Element " + xpath + " is not present in the DOM.", element);

		boolean elementDisplayed = element.isDisplayed();
		Assert.assertTrue("Element " + xpath + " is not displayed.", elementDisplayed);

		String elementValue = element.getAttribute("value");
		Assert.assertEquals("Element " + xpath + " does have the value \"" + text +
			"\". Instead it has the value \"" + elementValue + "\".", text, elementValue);
	}

	public void assertElementVisible(String xpath) {

		WebElement element = getElement(xpath);
		Assert.assertNotNull("Element " + xpath + " is not present in the DOM.", element);

		boolean elementDisplayed = element.isDisplayed();
		Assert.assertTrue("Element " + xpath + " is not displayed.", elementDisplayed);
	}

	public void click(String xpath) {
		getElement(xpath).click();
	}

	public void clickAndWaitForAjaxRerender(String xpath) {
		clickAndWaitForAjaxRerender(xpath, xpath);
	}

	public void clickAndWaitForAjaxRerender(String clickXpath, String rerenderXpath) {

		WebElement rerenderElement = getElement(rerenderXpath);
		click(clickXpath);
		logger.log(Level.INFO, "Waiting for element {0} to be stale.", rerenderXpath);
		wait.until(ExpectedConditions.stalenessOf(rerenderElement));
		logger.log(Level.INFO, "Element {0} is stale.", rerenderXpath);
		waitForElementVisible(rerenderXpath);
	}

	public void navigateToURL(String url) {
		webDriver.navigate().to(url);
	}

	public void quit() {
		webDriver.quit();
	}

	public void sendKeys(String xpath, String keys) {
		getElement(xpath).sendKeys(keys);
	}

	// Currently unused:
	public void waitForElementNotPresent(String xpath) {

		logger.log(Level.INFO, "Waiting for element {0} to not be present in the DOM.", xpath);
		wait.until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(By.ByXPath.xpath(xpath))));
		logger.log(Level.INFO, "Element {0} is not present in the DOM.", xpath);
	}

	// Currently unused:
	public void waitForElementPresent(String xpath) {

		logger.log(Level.INFO, "Waiting for element {0} to be present in the DOM.", xpath);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.ByXPath.xpath(xpath)));
		logger.log(Level.INFO, "Element {0} is present in the DOM.", xpath);
	}

	// Currently unused:
	public void waitForElementTextVisible(String xpath, String text) {

		String[] loggerArgs = new String[] { xpath, text };
		waitForElementVisible(xpath);
		logger.log(Level.INFO, "Waiting for element {0} to contain text \"{1}\".", loggerArgs);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.ByXPath.xpath(xpath), text));
		logger.log(Level.INFO, "Element {0} is visible and contains text \"{1}\".", loggerArgs);
	}
	
	public void waitForElementValue(String xpath, String text) {

		String[] loggerArgs = new String[] { xpath, text };
		waitForElementVisible(xpath);
		logger.log(Level.INFO, "Waiting for element {0} to contain text \"{1}\".", loggerArgs);
		wait.until(ExpectedConditions.textToBePresentInElementValue(By.ByXPath.xpath(xpath), text));
		logger.log(Level.INFO, "Element {0} is visible and contains text \"{1}\".", loggerArgs);
	}

	public void waitForElementVisible(String xpath) {

		logger.log(Level.INFO, "Waiting for element {0} to be visible.", xpath);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.ByXPath.xpath(xpath)));
		logger.log(Level.INFO, "Element {0} is visible.", xpath);
	}

	public WebElement getElement(String xpath) {
		return webDriver.findElement(By.xpath(xpath));
	}
}
