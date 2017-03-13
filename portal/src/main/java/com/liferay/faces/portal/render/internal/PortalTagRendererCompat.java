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
package com.liferay.faces.portal.render.internal;

import javax.faces.render.Renderer;
import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.util.PortalUtil;


/**
 * This abstract class provides a compatibility layer for different versions of Liferay
 *
 * @author  Juan Gonzalez
 */
public abstract class PortalTagRendererCompat extends Renderer {

	protected HttpServletRequest getHttpServletRequest(PortletRequest portletRequest) {
		return _getHttpServletRequest(portletRequest);
	}

	private HttpServletRequest _getHttpServletRequest(PortletRequest portletRequest) {
		LiferayPortletRequest liferayPortletRequest = PortalUtil.getLiferayPortletRequest(portletRequest);

		return liferayPortletRequest.getHttpServletRequest();
	}
}
