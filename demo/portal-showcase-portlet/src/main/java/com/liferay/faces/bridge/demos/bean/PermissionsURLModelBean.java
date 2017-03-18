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
package com.liferay.faces.bridge.demos.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.liferay.faces.portal.context.LiferayPortletHelperUtil;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;


/**
 * @author  Vernon Singleton
 */
@RequestScoped
@ManagedBean
public class PermissionsURLModelBean {

	// Private Data Members
	private String portletResourcePrimaryKey;
	private Map<String, Object> roleConstants;

	public String getPortletResourcePrimaryKey() {

		if (portletResourcePrimaryKey == null) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			long plid = LiferayPortletHelperUtil.getPlid(facesContext);
			Portlet portlet = LiferayPortletHelperUtil.getPortlet(facesContext);
			String portletId = portlet.getPortletId();
			portletResourcePrimaryKey = PortletPermissionUtil.getPrimaryKey(plid, portletId);
		}

		return portletResourcePrimaryKey;
	}

	public Map<String, Object> getRoleConstants() {

		if (roleConstants == null) {

			roleConstants = new HashMap<String, Object>();

			Field[] fields = RoleConstants.class.getFields();

			for (Field field : fields) {
				int modifiers = field.getModifiers();

				if (Modifier.isStatic(modifiers)) {

					try {
						roleConstants.put(field.getName(), field.get(null));
					}
					catch (IllegalAccessException e) {
						// ignore
					}
				}

			}
		}

		return roleConstants;
	}
}
