/**
 * Copyright (c) 2000-2023 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.portal.test.integration;

import com.liferay.faces.test.selenium.browser.BrowserDriver;
import com.liferay.faces.test.selenium.browser.TestUtil;


/**
 * @author  Kyle Stiemann
 * @author  Philip White
 */
public final class PortalTestUtil {

	// Private Constants
	private static final String DEFAULT_DEMO_CONTEXT = "/group/portal-demos";
	private static final String DEFAULT_GUEST_CONTEXT = "/web/guest";
	private static final String DEFAULT_ISSUE_CONTEXT = "/web/portal-issues";

	private PortalTestUtil() {
		throw new AssertionError();
	}

	public static String getDemoContext(String portletPageName) {
		return TestUtil.getSystemPropertyOrDefault("integration.demo.context", DEFAULT_DEMO_CONTEXT) + "/" +
			portletPageName;
	}

	public static String getDemoPageURL(String portletPageName) {
		return TestUtil.DEFAULT_BASE_URL + getDemoContext(portletPageName);
	}

	public static String getGuestContext(String portletPageName) {
		return TestUtil.getSystemPropertyOrDefault("integration.demo.context", DEFAULT_GUEST_CONTEXT) + "/" +
			portletPageName;
	}

	public static String getGuestPageURL(String portletPageName) {
		return TestUtil.DEFAULT_BASE_URL + getGuestContext(portletPageName);
	}

	public static String getIssueContext(String portletPageName) {
		return TestUtil.getSystemPropertyOrDefault("integration.issue.context", DEFAULT_ISSUE_CONTEXT) + "/" +
			portletPageName;
	}

	public static String getIssuePageURL(String portletPageName) {
		return TestUtil.DEFAULT_BASE_URL + getIssueContext(portletPageName);
	}

	public static void signOut(BrowserDriver browserDriver) {

		browserDriver.navigateWindowTo(TestUtil.DEFAULT_BASE_URL + "/c/portal/logout");
		browserDriver.clearBrowserCookies();
	}
}
