/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.portal.component.navbar;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIPanel;

import com.liferay.faces.util.component.Styleable;


/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class NavBarBase extends UIPanel implements Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portal.component.navbar.NavBar";
	public static final String RENDERER_TYPE = "com.liferay.faces.portal.component.navbar.NavBarRenderer";

	// Protected Enumerations
	protected enum NavBarPropertyKeys {
		style,
		styleClass
	}

	public NavBarBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	/**
	 * <code>style</code> attribute description:
	 * <br /><br />
	 * HTML passthrough attribute specifying the css style of the element.
	 */
	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(NavBarPropertyKeys.style, null);
	}

	/**
	 * <code>style</code> attribute description:
	 * <br /><br />
	 * HTML passthrough attribute specifying the css style of the element.
	 */
	@Override
	public void setStyle(String style) {
		getStateHelper().put(NavBarPropertyKeys.style, style);
	}

	/**
	 * <code>styleClass</code> attribute description:
	 * <br /><br />
	 * List of CSS class names (separated by spaces) that are to be rendered within the class attribute.
	 */
	@Override
	public String getStyleClass() {

		// getStateHelper().eval(NavBarPropertyKeys.styleClass, null) is called because
		// super.getStyleClass() may return the styleClass name of the super class.
		String styleClass = (String) getStateHelper().eval(NavBarPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "portal-nav-bar");
	}

	/**
	 * <code>styleClass</code> attribute description:
	 * <br /><br />
	 * List of CSS class names (separated by spaces) that are to be rendered within the class attribute.
	 */
	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(NavBarPropertyKeys.styleClass, styleClass);
	}
}
//J+
