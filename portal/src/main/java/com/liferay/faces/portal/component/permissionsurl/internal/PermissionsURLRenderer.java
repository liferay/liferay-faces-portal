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
package com.liferay.faces.portal.component.permissionsurl.internal;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.servlet.jsp.tagext.Tag;

import com.liferay.faces.portal.component.permissionsurl.PermissionsURL;

import com.liferay.taglib.security.PermissionsURLTag;


/**
 * @author  Vernon Singleton
 */

//J-
@FacesRenderer(componentFamily = PermissionsURL.COMPONENT_FAMILY, rendererType = PermissionsURL.RENDERER_TYPE)
//J+
public class PermissionsURLRenderer extends PermissionsURLRendererBase {

	@Override
	public Tag createTag(FacesContext facesContext, UIComponent uiComponent) {

		PermissionsURLTag permissionsURLTag = new PermissionsURLTag();
		PermissionsURL permissionsURL = (PermissionsURL) uiComponent;

		// Set attributes that are common between the component and JSP tag.
		permissionsURLTag.setModelResource(permissionsURL.getModelResource());
		permissionsURLTag.setModelResourceDescription(permissionsURL.getModelResourceDescription());
		permissionsURLTag.setRedirect(permissionsURL.getRedirect());
		permissionsURLTag.setResourceGroupId(permissionsURL.getResourceGroupId());
		permissionsURLTag.setResourcePrimKey(permissionsURL.getResourcePrimKey());
		permissionsURLTag.setRoleTypes(permissionsURL.getRoleTypes());
		permissionsURLTag.setWindowState(permissionsURL.getWindowState());

		// Set other attributes.
		permissionsURLTag.setId(permissionsURL.getClientId(facesContext));

		return permissionsURLTag;
	}

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Get the URL from the tag output.
		String url = getPortalTagOutput(facesContext, uiComponent, null);

		// If the user didn't specify a value for the "var" attribute, then write the URL to the response.
		PermissionsURL permissionsURL = (PermissionsURL) uiComponent;
		String varName = permissionsURL.getVar();

		if (varName == null) {
			ResponseWriter responseWriter = facesContext.getResponseWriter();
			responseWriter.write(url);
		}

		// Otherwise, place the URL into the request scope so that it can be resolved via EL with the name
		// specified in the "var" attribute.
		else {
			ExternalContext externalContext = facesContext.getExternalContext();
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put(varName, url);
		}
	}
}
