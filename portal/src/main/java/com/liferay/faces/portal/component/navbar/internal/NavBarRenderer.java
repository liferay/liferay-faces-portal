/**
 * Copyright (c) 2000-2020 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.portal.component.navbar.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.servlet.jsp.tagext.Tag;

import com.liferay.faces.portal.component.navbar.NavBar;

import com.liferay.taglib.aui.NavBarTag;


/**
 * @author  Neil Griffin
 */

//J-
@FacesRenderer(componentFamily = NavBar.COMPONENT_FAMILY, rendererType = NavBar.RENDERER_TYPE)
//J+
public class NavBarRenderer extends NavBarRendererBase {

	@Override
	public Tag createTag(FacesContext facesContext, UIComponent uiComponent) {

		NavBarTag navBarTag = new NavBarTag();
		NavBar navBar = (NavBar) uiComponent;

		// Set other attributes.
		char separatorChar = UINamingContainer.getSeparatorChar(facesContext);
		String id = navBar.getClientId(facesContext).replace(separatorChar, '_').concat("_jsptag");
		navBarTag.setId(id);
		navBarTag.setCssClass(navBar.getStyleClass());

		return navBarTag;
	}

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the starting <div> element that represents the navBar. In addition, write the id attribute. This is
		// necessary because portal-web/docroot/html/taglib/aui/nav_bar/page.jsp encodes a <div> element that needs an
		// id attribute that does not contain colons (which is the default JSF NamingContainer separator character).
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement("div", uiComponent);
		responseWriter.writeAttribute("id", uiComponent.getClientId(facesContext), "id");

		// Delegate to PortalTagRenderer so that the JSP tag output will get encoded.
		super.encodeBegin(facesContext, uiComponent);
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Delegate to PortalTagRenderer so that the JSP tag output will get encoded.
		super.encodeEnd(facesContext, uiComponent);

		// Encode the closing </div> element for the navBar.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement("div");
	}
}
