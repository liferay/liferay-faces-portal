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

		// Verify that the <span> that contains "requestedURL=..." is visible
		String requestedUrlXpath = "//span[contains(@id, ':requestedURL')]";
		browser.waitForElementVisible(requestedUrlXpath);

		// STEP 1: alpha=1 beta=2 gamma=0
		// Click on the link in step 1 in order to cause the browser to navigate to a friendly Liferay RenderURL via
		// HTTP GET.
		browser.clickAndWaitForAjaxRerender("//a[contains(text(), 'alpha=1 beta=2 gamma=0')]");

		// Verify that the current URL of the browser ends with the expected friendly URL mapping.
		Assert.assertTrue(browser.getCurrentUrl().endsWith("/-/my-friendly-url-mapping/1/my-friendly-action/2"));

		// Verify that the alpha, beta, and gamma parameter values appear in the markup
		String alphaXpath = "//span[contains(@id, ':alpha')]";
		SeleniumAssert.assertElementTextVisible(browser, alphaXpath, "1");

		String betaXpath = "//span[contains(@id, ':beta')]";
		SeleniumAssert.assertElementTextVisible(browser, betaXpath, "2");

		String gammaXpath = "//span[contains(@id, ':gamma')]";
		SeleniumAssert.assertElementTextVisible(browser, gammaXpath, "0");

		// STEP 2: alpha=1 beta=2 gamma=3
		// Click on the link in step 2 in order to cause the browser to navigate to a friendly Liferay RenderURL via
		// HTTP GET.
		browser.clickAndWaitForAjaxRerender("//a[contains(text(), 'alpha=1 beta=2 gamma=3')]");

		// Verify that the current URL of the browser ends with the expected friendly URL mapping.
		Assert.assertTrue(browser.getCurrentUrl().endsWith("/-/my-friendly-url-mapping/1/my-friendly-action/2/3"));

		// Verify that the alpha, beta, and gamma parameter values appear in the markup
		SeleniumAssert.assertElementTextVisible(browser, alphaXpath, "1");
		SeleniumAssert.assertElementTextVisible(browser, betaXpath, "2");
		SeleniumAssert.assertElementTextVisible(browser, gammaXpath, "3");

		// STEP 3: alpha=4 beta=5 gamma=0
		// Click on the button in step 3 in order to cause the browser to navigate to a friendly Liferay RenderURL via
		// HTTP GET.
		browser.clickAndWaitForAjaxRerender("//input[@type='button' and contains(@value, 'alpha=4 beta=5 gamma=0')]");

		// Verify that the current URL of the browser ends with the expected friendly URL mapping.
		Assert.assertTrue(browser.getCurrentUrl().endsWith("/-/my-friendly-url-mapping/4/my-friendly-action/5"));

		// Verify that the alpha, beta, and gamma parameter values appear in the markup
		SeleniumAssert.assertElementTextVisible(browser, alphaXpath, "4");
		SeleniumAssert.assertElementTextVisible(browser, betaXpath, "5");
		SeleniumAssert.assertElementTextVisible(browser, gammaXpath, "0");

		// STEP 4: alpha=4 beta=5 gamma=6
		// Click on the button in step 4 in order to cause the browser to navigate to a friendly Liferay RenderURL via
		// HTTP GET.
		browser.clickAndWaitForAjaxRerender("//input[@type='button' and contains(@value, 'alpha=4 beta=5 gamma=6')]");

		// Verify that the current URL of the browser ends with the expected friendly URL mapping.
		Assert.assertTrue(browser.getCurrentUrl().endsWith("/-/my-friendly-url-mapping/4/my-friendly-action/5/6"));

		// Verify that the alpha, beta, and gamma parameter values appear in the markup
		SeleniumAssert.assertElementTextVisible(browser, alphaXpath, "4");
		SeleniumAssert.assertElementTextVisible(browser, betaXpath, "5");
		SeleniumAssert.assertElementTextVisible(browser, gammaXpath, "6");
	}
}
