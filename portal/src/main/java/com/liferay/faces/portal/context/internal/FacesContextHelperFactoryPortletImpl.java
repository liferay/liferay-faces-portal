/**
 * Copyright (c) 2000-2021 Liferay, Inc. All rights reserved.
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

import java.io.Serializable;

import com.liferay.faces.util.context.FacesContextHelper;
import com.liferay.faces.util.context.FacesContextHelperFactory;


/**
 * @author  Neil Griffin
 */
public class FacesContextHelperFactoryPortletImpl extends FacesContextHelperFactory implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 2214823838784919856L;

	// Private Data Members
	private FacesContextHelper facesContextHelper;
	private FacesContextHelperFactory wrappedFacesContextHelperFactory;

	public FacesContextHelperFactoryPortletImpl(FacesContextHelperFactory facesContextHelperFactory) {

		FacesContextHelper wrappedFacesContextHelper = facesContextHelperFactory.getFacesContextHelper();
		this.facesContextHelper = new FacesContextHelperPortletImpl(wrappedFacesContextHelper);
		this.wrappedFacesContextHelperFactory = facesContextHelperFactory;
	}

	@Override
	public FacesContextHelper getFacesContextHelper() {
		return facesContextHelper;
	}

	@Override
	public FacesContextHelperFactory getWrapped() {
		return wrappedFacesContextHelperFactory;
	}
}
