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

import javax.faces.FacesWrapper;

import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * @author  Neil Griffin
 */
public abstract class PortletHelperFactory implements FacesWrapper<PortletHelperFactory> {

	/**
	 * Returns a stateless, thread-safe singleton instance of {@link PortletHelper} from the {@link
	 * PortletHelperFactory} found by the {@link FactoryExtensionFinder}.
	 */
	public static PortletHelper getPortletHelperInstance() {

		PortletHelperFactory portletHelperFactory = (PortletHelperFactory) FactoryExtensionFinder.getFactory(
				PortletHelperFactory.class);

		return portletHelperFactory.getPortletHelper();
	}

	/**
	 * Returns a stateless, thread-safe singleton instance of {@link PortletHelper}.
	 */
	public abstract PortletHelper getPortletHelper();

	/**
	 * Returns the wrapped factory instance if this factory has been decorated. Otherwise, this method returns null.
	 */
	@Override
	public abstract PortletHelperFactory getWrapped();
}
