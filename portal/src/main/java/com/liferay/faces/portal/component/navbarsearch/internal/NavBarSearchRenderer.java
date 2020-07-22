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
package com.liferay.faces.portal.component.navbarsearch.internal;

import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;
import javax.servlet.jsp.tagext.Tag;

import com.liferay.faces.portal.component.navbarsearch.NavBarSearch;

import com.liferay.taglib.aui.NavBarSearchTag;


/**
 * @author  Juan Gonzalez
 */

//J-
@FacesRenderer(componentFamily = NavBarSearch.COMPONENT_FAMILY, rendererType = NavBarSearch.RENDERER_TYPE)
//J+
public class NavBarSearchRenderer extends NavBarSearchRendererBase {

	@Override
	public Tag createTag(FacesContext facesContext, UIComponent uiComponent) {

		NavBarSearchTag navBarSearchTag = new NavBarSearchTag();
		NavBarSearch navBarSearch = (NavBarSearch) uiComponent;

		// Set other attributes.
		char separatorChar = UINamingContainer.getSeparatorChar(facesContext);
		String id = navBarSearch.getClientId(facesContext).replace(separatorChar, '_').concat("_jsptag");
		navBarSearchTag.setId(id);
		navBarSearchTag.setCssClass(navBarSearch.getStyleClass());

		return navBarSearchTag;
	}

	@Override
	public String getChildrenInsertionMarker() {
		return "</div>";
	}
}
