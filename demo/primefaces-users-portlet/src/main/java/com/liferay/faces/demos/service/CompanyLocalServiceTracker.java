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
package com.liferay.faces.demos.service;

import org.osgi.framework.BundleContext;

import org.osgi.util.tracker.ServiceTracker;

import com.liferay.portal.kernel.service.CompanyLocalService;


/**
 * @author  Neil Griffin
 */
public class CompanyLocalServiceTracker extends ServiceTracker<CompanyLocalService, CompanyLocalService> {

	public CompanyLocalServiceTracker(BundleContext bundleContext) {
		super(bundleContext, CompanyLocalService.class, null);
	}
}
