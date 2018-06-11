/**
 * Copyright (c) 2000-2018 Liferay, Inc. All rights reserved.
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
package com.liferay.portal.kernel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.security.auth.session.AuthenticatedSessionManagerUtil;


/**
 * This class provides a compatibility layer that isolates differences between different versions of Liferay Portal.
 *
 * @author  Neil Griffin
 */
public final class LoginUtilCompat {

	private LoginUtilCompat() {
		throw new AssertionError();
	}

	public static Object login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
		String handle, String password, boolean rememberMe, String authType) throws Exception {

		AuthenticatedSessionManagerUtil.login(httpServletRequest, httpServletResponse, handle, password, rememberMe,
			authType);

		return null;
	}
}
