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
package com.liferay.faces.portal.resource.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import javax.faces.application.Resource;
import javax.faces.context.FacesContext;

import com.liferay.faces.portal.context.LiferayPortletHelperUtil;


/**
 * This class represents a Liferay Portal CSS/JavaScript resource and is designed to be used in conjunction with the
 * {@link LiferayPortalResourceHandler}.
 *
 * @author  Neil Griffin
 */
public class LiferayPortalResource extends Resource {

	// Private Data Members
	private String resourceName;

	public LiferayPortalResource(String resourceName) {
		this.resourceName = resourceName;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return null;
	}

	@Override
	public String getRequestPath() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		String portalURL = LiferayPortletHelperUtil.getPortalURL(facesContext);

		return portalURL + resourceName;
	}

	@Override
	public Map<String, String> getResponseHeaders() {
		return null;
	}

	@Override
	public URL getURL() {
		return null;
	}

	@Override
	public boolean userAgentNeedsUpdate(FacesContext facesContext) {
		return false;
	}
}
