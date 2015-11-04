/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.portal.context.internal;

import com.liferay.faces.portal.context.LiferayPortletHelper;
import com.liferay.faces.portal.context.LiferayPortletHelperFactory;


/**
 * @author  Neil Griffin
 */
public class LiferayPortletHelperFactoryImpl extends LiferayPortletHelperFactory {

	// Private Data Members
	private LiferayPortletHelper liferayPortletHelper;

	public LiferayPortletHelperFactoryImpl() {
		this.liferayPortletHelper = new LiferayPortletHelperImpl();
	}

	@Override
	public LiferayPortletHelper getLiferayPortletHelper() {
		return liferayPortletHelper;
	}

	@Override
	public LiferayPortletHelperFactory getWrapped() {

		// Since this is the default factory instance, it will never wrap another factory.
		return null;
	}
}
