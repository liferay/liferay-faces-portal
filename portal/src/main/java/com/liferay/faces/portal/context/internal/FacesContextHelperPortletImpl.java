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
package com.liferay.faces.portal.context.internal;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.portlet.PortletRequest;

import com.liferay.faces.util.context.FacesContextHelper;
import com.liferay.faces.util.context.FacesContextHelperWrapper;


/**
 * @author  Neil Griffin
 */
public class FacesContextHelperPortletImpl extends FacesContextHelperWrapper implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 3890575634255388174L;

	// Private Data Members
	private FacesContextHelper wrappedFacesContextHelper;

	public FacesContextHelperPortletImpl(FacesContextHelper facesContextHelper) {
		this.wrappedFacesContextHelper = facesContextHelper;
	}

	@Override
	public String getRequestQueryString(FacesContext facesContext) {

		// Some portlet bridges (like the ICEfaces bridge) wrap the portal's PortletRequest implementation instance
		// (which prevents us from getting the query_string). As a workaround, we can still get it the original
		// PortletRequest instance, because the Portlet spec says it must be stored in the javax.portlet.request
		// attribute.
		String queryString = null;

		// JSR-168/286 request attribute that contains an instance of javax.portlet.PortletRequest
		Object portletRequestAsObject = getRequestAttribute(facesContext, "javax.portlet.request");

		if ((portletRequestAsObject != null) && (portletRequestAsObject instanceof PortletRequest)) {
			PortletRequest portletRequest = (PortletRequest) portletRequestAsObject;
			queryString = (String) portletRequest.getAttribute("javax.servlet.forward.query_string");
		}

		return queryString;
	}

	@Override
	public FacesContextHelper getWrapped() {
		return wrappedFacesContextHelper;
	}
}
