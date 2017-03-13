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
package com.liferay.faces.test.hooks;

/**
 * This class exists as a compatibility layer in order to minimize diffs between branches.
 *
 * @author  Neil Griffin
 */
public class Bundle {

	// Public Constants
	public static final int ACTIVE = 32; // org.osgi.framework.Bundle.ACTIVE
	public static final int UNINSTALLED = 1; // org.osgi.framework.Bundle.UNINSTALLED

	// Private Data Members
	private long bundleId;
	private int state;
	private String symbolicName;
	private Version version;

	public Bundle(long bundleId, String symbolicName) {
		this(bundleId, symbolicName, UNINSTALLED);
	}

	public Bundle(long bundleId, String symbolicName, int state) {
		this.bundleId = bundleId;
		this.state = state;
		this.symbolicName = symbolicName;
		this.version = new Version();
	}

	public long getBundleId() {
		return bundleId;
	}

	public int getState() {
		return state;
	}

	public String getSymbolicName() {
		return symbolicName;
	}

	public Version getVersion() {
		return version;
	}
}
