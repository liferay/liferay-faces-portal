/**
 * Copyright (c) 2000-2022 Liferay, Inc. All rights reserved.
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

import com.liferay.faces.test.selenium.browser.FileUploadTesterBase;
import com.liferay.faces.test.selenium.browser.TestUtil;


/**
 * This class provides a compatibility layer that isolates differences between different versions of Liferay Portal.
 *
 * @author  Neil Griffin
 */
public abstract class PrimeFacesUsersPortletTesterCompat extends FileUploadTesterBase {

	protected static String getURL() {
		return TestUtil.DEFAULT_BASE_URL + "/group/control_panel/manage?p_p_id=1_WAR_primefacesusersportlet";
	}

	protected String getFirstNameFilterText() {
		return "John";
	}

	protected boolean isFirstNameMatch(String firstName) {
		return firstName.startsWith(getFirstNameFilterText());
	}
}
