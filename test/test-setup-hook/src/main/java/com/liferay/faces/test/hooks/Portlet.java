/**
 * Copyright (c) 2000-2016 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.test.hooks;

/**
 * @author  Neil Griffin
 */
public class Portlet {

	// Private Data Members
	private String portletName;
	private String bundleName;
	private boolean instanceable;

	public Portlet(String portletName, String bundleName) {
		this(portletName, bundleName, true);
	}

	public Portlet(String portletName, String bundleName, boolean instanceable) {
		this.portletName = portletName;
		this.bundleName = bundleName;
		this.instanceable = instanceable;
	}

	public String getBundleName() {
		return this.bundleName;
	}

	public String getInstanceToken() {

		if (instanceable) {
			return "_INSTANCE_ABCD";
		}
		else {
			return "";
		}
	}

	public String getPortletName() {
		return portletName;
	}
}
