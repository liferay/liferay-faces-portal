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
package com.liferay.faces.portal.test.integration.issue;

import org.junit.Assert;
import org.junit.Test;

import org.openqa.selenium.WebElement;

import com.liferay.faces.portal.test.integration.PortalTestUtil;
import com.liferay.faces.test.selenium.Browser;
import com.liferay.faces.test.selenium.assertion.SeleniumAssert;


/**
 * @author  Vernon Singleton
 * @author  Philip White
 */
public class FACES_257PortletTester {

	@Test
	public void runFACES_257PortletTest() {

		// Navigate the browser to the portal page that contains the FACES-257 portlet.
		Browser browser = Browser.getInstance();
		String issuePageURL = PortalTestUtil.getIssuePageURL("faces-257");
		browser.get(issuePageURL);

		// STEP 1: alpha=1 beta=2 gamma=0
		String anchor1Xpath = "//a[contains(text(), 'alpha=1 beta=2 gamma=0')]";
		browser.waitForElementVisible(anchor1Xpath);

		// Verify that the <span> that contains "requestedURL=..." is visible
		String requestedUrlXpath = "//span[contains(@id, ':requestedURL')]";
		SeleniumAssert.assertElementVisible(browser, requestedUrlXpath);

		// Click on the link in step 1 in order to cause the browser to navigate to a friendly Liferay RenderURL via
		// HTTP GET.
		browser.clickAndWaitForAjaxRerender(anchor1Xpath);

		// Verify that the friendly URL mapping (corresponding to the link in step 1) is present.
		String assert1Xpath = anchor1Xpath +
			"/following-sibling::*[1]/following-sibling::*[1]/following-sibling::*[1]/following-sibling::span[1]";
		SeleniumAssert.assertElementVisible(browser, assert1Xpath);

		// Verify that the current URL of the browser contains the expected friendly URL mapping. For example:
		// "/-/my-friendly-url-mapping/1/my-friendly-action/2"
		WebElement assert1WebElement = browser.findElementByXpath(assert1Xpath);
		String assert1Text = assert1WebElement.getText();
		Assert.assertTrue(browser.getCurrentUrl().contains(assert1Text));

		// STEP 2: alpha=1 beta=2 gamma=3
		String anchor2Xpath = "//a[contains(text(), 'alpha=1 beta=2 gamma=3')]";

		// Click on the link in step 2 in order to cause the browser to navigate to a friendly Liferay RenderURL via
		// HTTP GET.
		browser.clickAndWaitForAjaxRerender(anchor2Xpath);

		// Verify that the friendly URL mapping (corresponding to the link in step 2) is present.
		String assert2Xpath = anchor2Xpath +
			"/following-sibling::*[1]/following-sibling::*[1]/following-sibling::*[1]/following-sibling::span[1]";
		SeleniumAssert.assertElementVisible(browser, assert2Xpath);

		// Verify that the current URL of the browser contains the expected friendly URL mapping. For example:
		// "/-/my-friendly-url-mapping/1/my-friendly-action/2/3"
		WebElement assert2WebElement = browser.findElementByXpath(assert2Xpath);
		String assert2Text = assert2WebElement.getText();
		Assert.assertTrue(browser.getCurrentUrl().contains(assert2Text));

		// STEP 3: alpha=4 beta=5 gamma=0
		String button1Xpath = "//input[@type='button' and contains(@value, 'alpha=4 beta=5 gamma=0')]";

		// Click on the button in step 3 in order to cause the browser to navigate to a friendly Liferay RenderURL via
		// HTTP GET.
		browser.clickAndWaitForAjaxRerender(button1Xpath);

		// Verify that the friendly URL mapping (corresponding to the button in step 3) is present.
		String assert3Xpath = button1Xpath +
			"/following-sibling::*[1]/following-sibling::*[1]/following-sibling::span[1]";
		SeleniumAssert.assertElementVisible(browser, assert3Xpath);

		// Verify that the current URL of the browser contains the expected friendly URL mapping. For example:
		// "/-/my-friendly-url-mapping/4/my-friendly-action/5"
		WebElement assert3WebElement = browser.findElementByXpath(assert3Xpath);
		String assert3Text = assert3WebElement.getText();
		Assert.assertTrue(browser.getCurrentUrl().contains(assert3Text));

		// STEP 4: alpha=4 beta=5 gamma=6
		String button2Xpath = "//input[@type='button' and contains(@value, 'alpha=4 beta=5 gamma=6')]";

		// Click on the button in step 4 in order to cause the browser to navigate to a friendly Liferay RenderURL via
		// HTTP GET.
		browser.clickAndWaitForAjaxRerender(button2Xpath);

		// Verify that the friendly URL mapping (corresponding to the button in step 4) is present.
		String assert4Xpath = button2Xpath +
			"/following-sibling::*[1]/following-sibling::*[1]/following-sibling::span[1]";
		SeleniumAssert.assertElementVisible(browser, assert4Xpath);

		// Verify that the current URL of the browser contains the expected friendly URL mapping. For example:
		// "/-/my-friendly-url-mapping/4/my-friendly-action/5/6"
		WebElement assert4WebElement = browser.findElementByXpath(assert4Xpath);
		String assert4Text = assert4WebElement.getText();
		Assert.assertTrue(browser.getCurrentUrl().contains(assert4Text));
	}
}
