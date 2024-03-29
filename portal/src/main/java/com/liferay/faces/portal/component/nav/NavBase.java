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
package com.liferay.faces.portal.component.nav;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIData;

import com.liferay.faces.util.component.Styleable;


/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class NavBase extends UIData implements Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portal.component.nav.Nav";
	public static final String RENDERER_TYPE = "com.liferay.faces.portal.component.nav.NavRenderer";

	// Protected Enumerations
	protected enum NavPropertyKeys {
		ariaLabel,
		ariaRole,
		responsive,
		style,
		styleClass
	}

	public NavBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	/**
	 * <p><code>ariaLabel</code> attribute description:</p>
	 *
	 * <div>The WAI-ARIA label which can help users with disabilities when a text label is not visible.</div>
	 */
	public String getAriaLabel() {
		return (String) getStateHelper().eval(NavPropertyKeys.ariaLabel, null);
	}

	/**
	 * <p><code>ariaLabel</code> attribute description:</p>
	 *
	 * <div>The WAI-ARIA label which can help users with disabilities when a text label is not visible.</div>
	 */
	public void setAriaLabel(String ariaLabel) {
		getStateHelper().put(NavPropertyKeys.ariaLabel, ariaLabel);
	}

	/**
	 * <p><code>ariaRole</code> attribute description:</p>
	 *
	 * <div>The WAI-ARIA role which can help users with disabilities understand the purpose of an element, such as whether it is a menu, progress indicator, or some other type of component.</div>
	 */
	public String getAriaRole() {
		return (String) getStateHelper().eval(NavPropertyKeys.ariaRole, null);
	}

	/**
	 * <p><code>ariaRole</code> attribute description:</p>
	 *
	 * <div>The WAI-ARIA role which can help users with disabilities understand the purpose of an element, such as whether it is a menu, progress indicator, or some other type of component.</div>
	 */
	public void setAriaRole(String ariaRole) {
		getStateHelper().put(NavPropertyKeys.ariaRole, ariaRole);
	}

	/**
	 * <p><code>responsive</code> attribute description:</p>
	 *
	 * <div>When true, the unordered list will be surrounded by <code>&lt;div class="collapse nav-collapse"&gt;...&lt;/div&gt;</code>. This is a responsive layout feature (that works in conjunction with an portal:navBar parent tag) for small/mobile displays that causes the child portal:navItem links to be displayed in a popup menu.</div>
	 */
	public boolean isResponsive() {
		return (Boolean) getStateHelper().eval(NavPropertyKeys.responsive, true);
	}

	/**
	 * <p><code>responsive</code> attribute description:</p>
	 *
	 * <div>When true, the unordered list will be surrounded by <code>&lt;div class="collapse nav-collapse"&gt;...&lt;/div&gt;</code>. This is a responsive layout feature (that works in conjunction with an portal:navBar parent tag) for small/mobile displays that causes the child portal:navItem links to be displayed in a popup menu.</div>
	 */
	public void setResponsive(boolean responsive) {
		getStateHelper().put(NavPropertyKeys.responsive, responsive);
	}

	/**
	 * <p><code>style</code> attribute description:</p>
	 *
	 * <div>HTML passthrough attribute specifying the css style of the element.</div>
	 */
	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(NavPropertyKeys.style, null);
	}

	/**
	 * <p><code>style</code> attribute description:</p>
	 *
	 * <div>HTML passthrough attribute specifying the css style of the element.</div>
	 */
	@Override
	public void setStyle(String style) {
		getStateHelper().put(NavPropertyKeys.style, style);
	}

	/**
	 * <p><code>styleClass</code> attribute description:</p>
	 *
	 * <div>List of CSS class names (separated by spaces) that are to be rendered within the class attribute.</div>
	 */
	@Override
	public String getStyleClass() {

		// getStateHelper().eval(NavPropertyKeys.styleClass, null) is called because
		// super.getStyleClass() may return the styleClass name of the super class.
		String styleClass = (String) getStateHelper().eval(NavPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "portal-nav");
	}

	/**
	 * <p><code>styleClass</code> attribute description:</p>
	 *
	 * <div>List of CSS class names (separated by spaces) that are to be rendered within the class attribute.</div>
	 */
	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(NavPropertyKeys.styleClass, styleClass);
	}
}
//J+
