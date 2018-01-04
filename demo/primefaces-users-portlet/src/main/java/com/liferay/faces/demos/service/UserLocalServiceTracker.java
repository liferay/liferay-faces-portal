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
package com.liferay.faces.demos.service;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import com.liferay.portal.kernel.service.UserLocalService;


/**
 * @author  Neil Griffin
 */
public class UserLocalServiceTracker extends ServiceTracker<UserLocalService, UserLocalService> {

	private BundleContext _bundleContext;
	private UserLocalService _userLocalService;

	public UserLocalServiceTracker(BundleContext bundleContext) {
		super(bundleContext, UserLocalService.class, null);
		_bundleContext = bundleContext;
	}
	
	@Override
	public UserLocalService addingService(ServiceReference<UserLocalService> reference) {
		_userLocalService = _bundleContext.getService(reference);
		return _userLocalService;
	}

	@Override
	public void removedService(ServiceReference<UserLocalService> reference, UserLocalService service) {
		_bundleContext.ungetService(reference);
		_userLocalService = null;
	}
	
	public UserLocalService getUserLocalService() {
		return _userLocalService;
	}
}
