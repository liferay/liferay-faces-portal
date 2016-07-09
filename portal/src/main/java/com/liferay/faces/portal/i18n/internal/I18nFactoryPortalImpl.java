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
package com.liferay.faces.portal.i18n.internal;

import com.liferay.faces.util.i18n.I18n;
import com.liferay.faces.util.i18n.I18nFactory;


/**
 * @author  Neil Griffin
 */
public class I18nFactoryPortalImpl extends I18nFactory {

	// Private Data Members
	private I18n i18n;
	private I18nFactory wrappedI18nFactory;

	public I18nFactoryPortalImpl(I18nFactory i18nFactory) {
		I18n wrappedI18n = i18nFactory.getI18n();
		this.i18n = new I18nPortalImpl(wrappedI18n);
		this.wrappedI18nFactory = i18nFactory;
	}

	@Override
	public I18n getI18n() {
		return i18n;
	}

	@Override
	public I18nFactory getWrapped() {
		return wrappedI18nFactory;
	}
}
