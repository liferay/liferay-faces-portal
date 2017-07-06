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

import com.liferay.faces.portal.test.integration.PortalTestUtil;
import com.liferay.faces.test.selenium.browser.BrowserDriver;
import com.liferay.faces.test.selenium.browser.BrowserDriverManagingTesterBase;
import com.liferay.faces.test.selenium.browser.WaitingAsserter;


/**
 * @author  Vernon Singleton
 * @author  Philip White
 */
public class FACES_257PortletTester extends BrowserDriverManagingTesterBase {

	@Test
	public void runFACES_257PortletTest() {

		// Navigate the browser to the portal page that contains the FACES-257 portlet.
		BrowserDriver browserDriver = getBrowserDriver();
		String issuePageURL = PortalTestUtil.getIssuePageURL("faces-257");
		browserDriver.navigateWindowTo(issuePageURL);

		// Verify that the <span> that contains "requestedURL=..." is displayed
		browserDriver.waitForElementDisplayed("//span[contains(@id, ':requestedURL')]");

		// STEP 1: alpha=1 beta=2 gamma=0
		// Click on the link in step 1 in order to cause the browser to navigate to a friendly Liferay RenderURL via
		// HTTP GET.
		browserDriver.clickElementAndWaitForRerender("//a[contains(text(), 'alpha=1 beta=2 gamma=0')]");

		// Verify that the current URL of the browser ends with the expected friendly URL mapping.
		Assert.assertTrue(browserDriver.getCurrentWindowUrl().endsWith(
				"/-/my-friendly-url-mapping/1/my-friendly-action/2"));

		// Verify that the alpha, beta, and gamma parameter values appear in the markup
		WaitingAsserter waitingAsserter = getWaitingAsserter();
		String alphaXpath = "//span[contains(@id, ':alpha')]";
		waitingAsserter.assertTextPresentInElement("1", alphaXpath);

		String betaXpath = "//span[contains(@id, ':beta')]";
		waitingAsserter.assertTextPresentInElement("2", betaXpath);

		String gammaXpath = "//span[contains(@id, ':gamma')]";
		waitingAsserter.assertTextPresentInElement("0", gammaXpath);

		// STEP 2: alpha=1 beta=2 gamma=3
		// Click on the link in step 2 in order to cause the browser to navigate to a friendly Liferay RenderURL via
		// HTTP GET.
		browserDriver.clickElementAndWaitForRerender("//a[contains(text(), 'alpha=1 beta=2 gamma=3')]");

		// Verify that the current URL of the browser ends with the expected friendly URL mapping.
		Assert.assertTrue(browserDriver.getCurrentWindowUrl().endsWith(
				"/-/my-friendly-url-mapping/1/my-friendly-action/2/3"));

		// Verify that the alpha, beta, and gamma parameter values appear in the markup
		waitingAsserter.assertTextPresentInElement("1", alphaXpath);
		waitingAsserter.assertTextPresentInElement("2", betaXpath);
		waitingAsserter.assertTextPresentInElement("3", gammaXpath);

		// STEP 3: alpha=4 beta=5 gamma=0
		// Click on the button in step 3 in order to cause the browser to navigate to a friendly Liferay RenderURL via
		// HTTP GET.
		browserDriver.clickElementAndWaitForRerender(
			"//input[@type='button' and contains(@value, 'alpha=4 beta=5 gamma=0')]");

		// Verify that the current URL of the browser ends with the expected friendly URL mapping.
		Assert.assertTrue(browserDriver.getCurrentWindowUrl().endsWith(
				"/-/my-friendly-url-mapping/4/my-friendly-action/5"));

		// Verify that the alpha, beta, and gamma parameter values appear in the markup
		waitingAsserter.assertTextPresentInElement("4", alphaXpath);
		waitingAsserter.assertTextPresentInElement("5", betaXpath);
		waitingAsserter.assertTextPresentInElement("0", gammaXpath);

		// STEP 4: alpha=4 beta=5 gamma=6
		// Click on the button in step 4 in order to cause the browser to navigate to a friendly Liferay RenderURL via
		// HTTP GET.
		browserDriver.clickElementAndWaitForRerender(
			"//input[@type='button' and contains(@value, 'alpha=4 beta=5 gamma=6')]");

		// Verify that the current URL of the browser ends with the expected friendly URL mapping.
		Assert.assertTrue(browserDriver.getCurrentWindowUrl().endsWith(
				"/-/my-friendly-url-mapping/4/my-friendly-action/5/6"));

		// Verify that the alpha, beta, and gamma parameter values appear in the markup
		waitingAsserter.assertTextPresentInElement("4", alphaXpath);
		waitingAsserter.assertTextPresentInElement("5", betaXpath);
		waitingAsserter.assertTextPresentInElement("6", gammaXpath);
	}
}
