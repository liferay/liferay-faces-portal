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

import org.junit.Test;

import com.liferay.faces.portal.test.integration.PortalTestUtil;
import com.liferay.faces.test.selenium.Browser;
import com.liferay.faces.test.selenium.assertion.SeleniumAssert;


/**
 * @author  Liferay Faces Team
 */
public class FACES_257PortletTester {

	// alpha=1 beta=2 gamma=0
	private static final String anchor1Xpath = "//a[contains(text(), 'alpha=1 beta=2 gamma=0')]";

	private static final String assert1Xpath = anchor1Xpath +
		"/following-sibling::*[1]/following-sibling::*[1]/following-sibling::*[1]/following-sibling::span[1]";

	// alpha=1 beta=2 gamma=3
	private static final String anchor2Xpath = "//a[contains(text(), 'alpha=1 beta=2 gamma=3')]";

	private static final String assert2Xpath = anchor2Xpath +
		"/following-sibling::*[1]/following-sibling::*[1]/following-sibling::*[1]/following-sibling::span[1]";

	// alpha=4 beta=5 gamma=0
	private static final String button1Xpath = "//input[@type='button' and contains(@value, 'alpha=4 beta=5 gamma=0')]";

	private static final String assert3Xpath = button1Xpath +
		"/following-sibling::*[1]/following-sibling::*[1]/following-sibling::span[1]";

	// alpha=4 beta=5 gamma=6
	private static final String button2Xpath = "//input[@type='button' and contains(@value, 'alpha=4 beta=5 gamma=6')]";

	private static final String assert4Xpath = button2Xpath +
		"/following-sibling::*[1]/following-sibling::*[1]/following-sibling::span[1]";

	private static final String requestedUrlXpath = "//span[contains(@id, ':requestedURL')]";

	@Test
	public void runFACES_257PortletTest() {

		Browser browser = Browser.getInstance();
		browser.get(PortalTestUtil.getIssuePageURL("faces-257"));
		browser.waitForElementVisible(anchor1Xpath);

		// step 1
		SeleniumAssert.assertElementVisible(browser, requestedUrlXpath);

		browser.clickAndWaitForAjaxRerender(anchor1Xpath);

		SeleniumAssert.assertElementVisible(browser, assert1Xpath);
		browser.getCurrentUrl().contains(assert1Xpath);

		// step 2
		browser.clickAndWaitForAjaxRerender(anchor2Xpath);

		SeleniumAssert.assertElementVisible(browser, assert2Xpath);
		browser.getCurrentUrl().contains(assert2Xpath);

		// step 3
		browser.clickAndWaitForAjaxRerender(button1Xpath);

		SeleniumAssert.assertElementVisible(browser, assert3Xpath);
		browser.getCurrentUrl().contains(assert3Xpath);

		// step 4
		browser.clickAndWaitForAjaxRerender(button2Xpath);

		SeleniumAssert.assertElementVisible(browser, assert4Xpath);
		browser.getCurrentUrl().contains(assert4Xpath);
	}
}
