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

import java.io.Serializable;
import java.util.Locale;

import javax.faces.context.FacesContext;

import com.liferay.faces.util.i18n.I18n;
import com.liferay.faces.util.i18n.I18nWrapper;

import com.liferay.portal.kernel.language.LanguageUtil;


/**
 * @author  Neil Griffin
 */
public class I18nPortalImpl extends I18nWrapper implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 2228392557312272111L;

	// Private Data Members
	private I18n wrappedI18n;

	public I18nPortalImpl(I18n i18n) {
		this.wrappedI18n = i18n;
	}

	@Override
	public String getMessage(FacesContext facesContext, Locale locale, String messageId) {

		String value = LanguageUtil.get(locale, messageId);

		if ((value == null) || value.equals(messageId)) {
			value = super.getMessage(facesContext, locale, messageId);
		}

		return value;
	}

	@Override
	public String getMessage(FacesContext facesContext, Locale locale, String messageId, Object... arguments) {

		String value = LanguageUtil.format(locale, messageId, arguments);

		if ((value == null) || value.equals(messageId)) {
			value = super.getMessage(facesContext, locale, messageId, arguments);
		}

		return value;
	}

	@Override
	public I18n getWrapped() {
		return wrappedI18n;
	}
}
