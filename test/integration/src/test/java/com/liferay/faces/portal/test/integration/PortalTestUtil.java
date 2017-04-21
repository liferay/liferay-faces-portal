/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.portal.test.integration;

import com.liferay.faces.test.selenium.TestUtil;


/**
 * @author  Kyle Stiemann
 * @author  Philip White
 */
public final class PortalTestUtil {

	// Private Constants
	private static final String DEFAULT_ISSUE_CONTEXT;

	static {

		String defaultIssueContext = "/web/portal-issues";

		DEFAULT_ISSUE_CONTEXT = defaultIssueContext;
	}

	public static String getIssueContext(String portletPageName) {
		return TestUtil.getSystemPropertyOrDefault("integration.issue.context", DEFAULT_ISSUE_CONTEXT) + "/" +
			portletPageName;
	}

	public static String getIssuePageURL(String portletPageName) {
		return TestUtil.DEFAULT_BASE_URL + getIssueContext(portletPageName);
	}
}
