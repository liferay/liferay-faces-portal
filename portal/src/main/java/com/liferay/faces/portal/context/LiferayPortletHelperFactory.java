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
package com.liferay.faces.portal.context;

import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.helper.Wrapper;


/**
 * @author  Neil Griffin
 */
public abstract class LiferayPortletHelperFactory implements Wrapper<LiferayPortletHelperFactory> {

	/**
	 * Returns a stateless, thread-safe singleton instance of {@link LiferayPortletHelper} from the {@link
	 * LiferayPortletHelperFactory} found by the {@link FactoryExtensionFinder}.
	 */
	public static LiferayPortletHelper getLiferayPortletHelperInstance() {

		LiferayPortletHelperFactory liferayPortletHelperFactory = (LiferayPortletHelperFactory) FactoryExtensionFinder
			.getFactory(LiferayPortletHelperFactory.class);

		return liferayPortletHelperFactory.getLiferayPortletHelper();
	}

	/**
	 * Returns a stateless, thread-safe singleton instance of {@link LiferayPortletHelper}.
	 */
	public abstract LiferayPortletHelper getLiferayPortletHelper();

	/**
	 * Returns the wrapped factory instance if this factory has been decorated. Otherwise, this method returns null.
	 */
	@Override
	public abstract LiferayPortletHelperFactory getWrapped();
}
