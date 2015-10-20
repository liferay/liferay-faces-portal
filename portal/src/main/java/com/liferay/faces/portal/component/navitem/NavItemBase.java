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
package com.liferay.faces.portal.component.navitem;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIColumn;

import com.liferay.faces.util.component.Styleable;


/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class NavItemBase extends UIColumn implements Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portal.component.navitem.NavItem";
	public static final String RENDERER_TYPE = "com.liferay.faces.portal.component.navitem.NavItemRenderer";

	// Protected Enumerations
	protected enum NavItemPropertyKeys {
		anchorCssClass,
		anchorData,
		anchorId,
		ariaLabel,
		ariaRole,
		data,
		href,
		iconCssClass,
		label,
		selected,
		style,
		styleClass,
		title,
		useDialog
	}

	public NavItemBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	/**
	 * <code>anchorCssClass</code> attribute description:
	 * <br /><br />
	 * If the href attribute has a value, then this is the value of the "class" attribute for the anchor tag rendered inside the list item element.
	 */
	public String getAnchorCssClass() {
		return (String) getStateHelper().eval(NavItemPropertyKeys.anchorCssClass, null);
	}

	/**
	 * <code>anchorCssClass</code> attribute description:
	 * <br /><br />
	 * If the href attribute has a value, then this is the value of the "class" attribute for the anchor tag rendered inside the list item element.
	 */
	public void setAnchorCssClass(String anchorCssClass) {
		getStateHelper().put(NavItemPropertyKeys.anchorCssClass, anchorCssClass);
	}

	/**
	 * <code>anchorData</code> attribute description:
	 * <br /><br />
	 * If the href attribute has a value, then this is the child text of the anchor tag rendered inside the list item element.
	 */
	public Object getAnchorData() {
		return (Object) getStateHelper().eval(NavItemPropertyKeys.anchorData, null);
	}

	/**
	 * <code>anchorData</code> attribute description:
	 * <br /><br />
	 * If the href attribute has a value, then this is the child text of the anchor tag rendered inside the list item element.
	 */
	public void setAnchorData(Object anchorData) {
		getStateHelper().put(NavItemPropertyKeys.anchorData, anchorData);
	}

	/**
	 * <code>anchorId</code> attribute description:
	 * <br /><br />
	 * If the href attribute has a value, then this is the value of the "id" attribute for the anchor tag rendered inside the list item element.
	 */
	public String getAnchorId() {
		return (String) getStateHelper().eval(NavItemPropertyKeys.anchorId, null);
	}

	/**
	 * <code>anchorId</code> attribute description:
	 * <br /><br />
	 * If the href attribute has a value, then this is the value of the "id" attribute for the anchor tag rendered inside the list item element.
	 */
	public void setAnchorId(String anchorId) {
		getStateHelper().put(NavItemPropertyKeys.anchorId, anchorId);
	}

	/**
	 * <code>ariaLabel</code> attribute description:
	 * <br /><br />
	 * The WAI-ARIA label which can help users with disabilities when a text label is not visible.
	 */
	public String getAriaLabel() {
		return (String) getStateHelper().eval(NavItemPropertyKeys.ariaLabel, null);
	}

	/**
	 * <code>ariaLabel</code> attribute description:
	 * <br /><br />
	 * The WAI-ARIA label which can help users with disabilities when a text label is not visible.
	 */
	public void setAriaLabel(String ariaLabel) {
		getStateHelper().put(NavItemPropertyKeys.ariaLabel, ariaLabel);
	}

	/**
	 * <code>ariaRole</code> attribute description:
	 * <br /><br />
	 * The WAI-ARIA role which can help users with disabilities understand the purpose of an element, such as whether it is a menu, progress indicator, or some other type of component.
	 */
	public String getAriaRole() {
		return (String) getStateHelper().eval(NavItemPropertyKeys.ariaRole, null);
	}

	/**
	 * <code>ariaRole</code> attribute description:
	 * <br /><br />
	 * The WAI-ARIA role which can help users with disabilities understand the purpose of an element, such as whether it is a menu, progress indicator, or some other type of component.
	 */
	public void setAriaRole(String ariaRole) {
		getStateHelper().put(NavItemPropertyKeys.ariaRole, ariaRole);
	}

	/**
	 * <code>data</code> attribute description:
	 * <br /><br />
	 * HTML5 data- attributes for the rendered list item element.
	 */
	public Object getData() {
		return (Object) getStateHelper().eval(NavItemPropertyKeys.data, null);
	}

	/**
	 * <code>data</code> attribute description:
	 * <br /><br />
	 * HTML5 data- attributes for the rendered list item element.
	 */
	public void setData(Object data) {
		getStateHelper().put(NavItemPropertyKeys.data, data);
	}

	/**
	 * <code>href</code> attribute description:
	 * <br /><br />
	 * The URL of the anchor tag rendered inside the list item element. If not specified, then an anchor tag is not rendered.
	 */
	public String getHref() {
		return (String) getStateHelper().eval(NavItemPropertyKeys.href, null);
	}

	/**
	 * <code>href</code> attribute description:
	 * <br /><br />
	 * The URL of the anchor tag rendered inside the list item element. If not specified, then an anchor tag is not rendered.
	 */
	public void setHref(String href) {
		getStateHelper().put(NavItemPropertyKeys.href, href);
	}

	/**
	 * <code>iconCssClass</code> attribute description:
	 * <br /><br />
	 * The name of the CSS class of the icon that is to be rendered inside the list item element.
	 */
	public String getIconCssClass() {
		return (String) getStateHelper().eval(NavItemPropertyKeys.iconCssClass, null);
	}

	/**
	 * <code>iconCssClass</code> attribute description:
	 * <br /><br />
	 * The name of the CSS class of the icon that is to be rendered inside the list item element.
	 */
	public void setIconCssClass(String iconCssClass) {
		getStateHelper().put(NavItemPropertyKeys.iconCssClass, iconCssClass);
	}

	/**
	 * <code>label</code> attribute description:
	 * <br /><br />
	 * The label of the anchor tag rendered inside the list item element.
	 */
	public String getLabel() {
		return (String) getStateHelper().eval(NavItemPropertyKeys.label, null);
	}

	/**
	 * <code>label</code> attribute description:
	 * <br /><br />
	 * The label of the anchor tag rendered inside the list item element.
	 */
	public void setLabel(String label) {
		getStateHelper().put(NavItemPropertyKeys.label, label);
	}

	/**
	 * <code>selected</code> attribute description:
	 * <br /><br />
	 * When true, the list item will be rendered with <code>class="active"</code>.
	 */
	public boolean isSelected() {
		return (Boolean) getStateHelper().eval(NavItemPropertyKeys.selected, false);
	}

	/**
	 * <code>selected</code> attribute description:
	 * <br /><br />
	 * When true, the list item will be rendered with <code>class="active"</code>.
	 */
	public void setSelected(boolean selected) {
		getStateHelper().put(NavItemPropertyKeys.selected, selected);
	}

	/**
	 * <code>style</code> attribute description:
	 * <br /><br />
	 * HTML passthrough attribute specifying the css style of the element.
	 */
	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(NavItemPropertyKeys.style, null);
	}

	/**
	 * <code>style</code> attribute description:
	 * <br /><br />
	 * HTML passthrough attribute specifying the css style of the element.
	 */
	@Override
	public void setStyle(String style) {
		getStateHelper().put(NavItemPropertyKeys.style, style);
	}

	/**
	 * <code>styleClass</code> attribute description:
	 * <br /><br />
	 * List of CSS class names (separated by spaces) that are to be rendered within the class attribute.
	 */
	@Override
	public String getStyleClass() {

		// getStateHelper().eval(NavItemPropertyKeys.styleClass, null) is called because
		// super.getStyleClass() may return the styleClass name of the super class.
		String styleClass = (String) getStateHelper().eval(NavItemPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "portal-nav-item");
	}

	/**
	 * <code>styleClass</code> attribute description:
	 * <br /><br />
	 * List of CSS class names (separated by spaces) that are to be rendered within the class attribute.
	 */
	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(NavItemPropertyKeys.styleClass, styleClass);
	}

	/**
	 * <code>title</code> attribute description:
	 * <br /><br />
	 * The title of the anchor tag rendered inside the list item element.
	 */
	public String getTitle() {
		return (String) getStateHelper().eval(NavItemPropertyKeys.title, null);
	}

	/**
	 * <code>title</code> attribute description:
	 * <br /><br />
	 * The title of the anchor tag rendered inside the list item element.
	 */
	public void setTitle(String title) {
		getStateHelper().put(NavItemPropertyKeys.title, title);
	}

	/**
	 * <code>useDialog</code> attribute description:
	 * <br /><br />
	 * When true, clicking on the href hyperlink will cause the resulting URL to popup in a modal dialog that contains an <code>iframe</code>.
	 */
	public boolean isUseDialog() {
		return (Boolean) getStateHelper().eval(NavItemPropertyKeys.useDialog, false);
	}

	/**
	 * <code>useDialog</code> attribute description:
	 * <br /><br />
	 * When true, clicking on the href hyperlink will cause the resulting URL to popup in a modal dialog that contains an <code>iframe</code>.
	 */
	public void setUseDialog(boolean useDialog) {
		getStateHelper().put(NavItemPropertyKeys.useDialog, useDialog);
	}
}
//J+
