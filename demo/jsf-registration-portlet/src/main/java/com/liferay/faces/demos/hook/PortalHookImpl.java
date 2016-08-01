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
package com.liferay.faces.demos.hook;

import java.lang.reflect.Method;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.servlet.http.HttpServletRequest;

import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;


/**
 * @author  Neil Griffin
 */
public class PortalHookImpl extends PortalWrapper {

	// Private Data Members
	private Portal wrappedPortal;

	public PortalHookImpl(Portal portal) {
		wrappedPortal = portal;
	}

	@Override
	public Portal getWrapped() {
		return wrappedPortal;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {

		Object returnValue = null;

		if (method.getName().equals("getCreateAccountURL")) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) arguments[0];
			ThemeDisplay themeDisplay = (ThemeDisplay) arguments[1];
			returnValue = getCreateAccountURL(httpServletRequest, themeDisplay);
		}
		else {
			returnValue = super.invoke(proxy, method, arguments);
		}

		return returnValue;
	}

	protected String getCreateAccountURL(HttpServletRequest request, ThemeDisplay themeDisplay) throws Exception {

		String portletName = "1_WAR_comliferayfacesdemojsfregistrationportlet";
		PortletURL urlCreateAccount = PortletURLFactoryUtil.create(request, portletName, themeDisplay.getPlid(),
				PortletRequest.ACTION_PHASE);

		urlCreateAccount.setWindowState(WindowState.MAXIMIZED);
		urlCreateAccount.setPortletMode(PortletMode.VIEW);

		urlCreateAccount.setParameter("saveLastPath", "0");
		urlCreateAccount.setParameter("mvcRenderCommandName", "/login/create_account");

		return urlCreateAccount.toString();
	}

}
