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
	 * <p><code>anchorCssClass</code> attribute description:</p>
	 *
	 * <div>If the href attribute has a value, then this is the value of the "class" attribute for the anchor tag rendered inside the list item element.</div>
	 */
	public String getAnchorCssClass() {
		return (String) getStateHelper().eval(NavItemPropertyKeys.anchorCssClass, null);
	}

	/**
	 * <p><code>anchorCssClass</code> attribute description:</p>
	 *
	 * <div>If the href attribute has a value, then this is the value of the "class" attribute for the anchor tag rendered inside the list item element.</div>
	 */
	public void setAnchorCssClass(String anchorCssClass) {
		getStateHelper().put(NavItemPropertyKeys.anchorCssClass, anchorCssClass);
	}

	/**
	 * <p><code>anchorData</code> attribute description:</p>
	 *
	 * <div>If the href attribute has a value, then this is the child text of the anchor tag rendered inside the list item element.</div>
	 */
	public Object getAnchorData() {
		return (Object) getStateHelper().eval(NavItemPropertyKeys.anchorData, null);
	}

	/**
	 * <p><code>anchorData</code> attribute description:</p>
	 *
	 * <div>If the href attribute has a value, then this is the child text of the anchor tag rendered inside the list item element.</div>
	 */
	public void setAnchorData(Object anchorData) {
		getStateHelper().put(NavItemPropertyKeys.anchorData, anchorData);
	}

	/**
	 * <p><code>anchorId</code> attribute description:</p>
	 *
	 * <div>If the href attribute has a value, then this is the value of the "id" attribute for the anchor tag rendered inside the list item element.</div>
	 */
	public String getAnchorId() {
		return (String) getStateHelper().eval(NavItemPropertyKeys.anchorId, null);
	}

	/**
	 * <p><code>anchorId</code> attribute description:</p>
	 *
	 * <div>If the href attribute has a value, then this is the value of the "id" attribute for the anchor tag rendered inside the list item element.</div>
	 */
	public void setAnchorId(String anchorId) {
		getStateHelper().put(NavItemPropertyKeys.anchorId, anchorId);
	}

	/**
	 * <p><code>ariaLabel</code> attribute description:</p>
	 *
	 * <div>The WAI-ARIA label which can help users with disabilities when a text label is not visible.</div>
	 */
	public String getAriaLabel() {
		return (String) getStateHelper().eval(NavItemPropertyKeys.ariaLabel, null);
	}

	/**
	 * <p><code>ariaLabel</code> attribute description:</p>
	 *
	 * <div>The WAI-ARIA label which can help users with disabilities when a text label is not visible.</div>
	 */
	public void setAriaLabel(String ariaLabel) {
		getStateHelper().put(NavItemPropertyKeys.ariaLabel, ariaLabel);
	}

	/**
	 * <p><code>ariaRole</code> attribute description:</p>
	 *
	 * <div>The WAI-ARIA role which can help users with disabilities understand the purpose of an element, such as whether it is a menu, progress indicator, or some other type of component.</div>
	 */
	public String getAriaRole() {
		return (String) getStateHelper().eval(NavItemPropertyKeys.ariaRole, null);
	}

	/**
	 * <p><code>ariaRole</code> attribute description:</p>
	 *
	 * <div>The WAI-ARIA role which can help users with disabilities understand the purpose of an element, such as whether it is a menu, progress indicator, or some other type of component.</div>
	 */
	public void setAriaRole(String ariaRole) {
		getStateHelper().put(NavItemPropertyKeys.ariaRole, ariaRole);
	}

	/**
	 * <p><code>data</code> attribute description:</p>
	 *
	 * <div>HTML5 data- attributes for the rendered list item element.</div>
	 */
	public Object getData() {
		return (Object) getStateHelper().eval(NavItemPropertyKeys.data, null);
	}

	/**
	 * <p><code>data</code> attribute description:</p>
	 *
	 * <div>HTML5 data- attributes for the rendered list item element.</div>
	 */
	public void setData(Object data) {
		getStateHelper().put(NavItemPropertyKeys.data, data);
	}

	/**
	 * <p><code>href</code> attribute description:</p>
	 *
	 * <div>The URL of the anchor tag rendered inside the list item element. If not specified, then an anchor tag is not rendered.</div>
	 */
	public String getHref() {
		return (String) getStateHelper().eval(NavItemPropertyKeys.href, null);
	}

	/**
	 * <p><code>href</code> attribute description:</p>
	 *
	 * <div>The URL of the anchor tag rendered inside the list item element. If not specified, then an anchor tag is not rendered.</div>
	 */
	public void setHref(String href) {
		getStateHelper().put(NavItemPropertyKeys.href, href);
	}

	/**
	 * <p><code>iconCssClass</code> attribute description:</p>
	 *
	 * <div>The name of the CSS class of the icon that is to be rendered inside the list item element.</div>
	 */
	public String getIconCssClass() {
		return (String) getStateHelper().eval(NavItemPropertyKeys.iconCssClass, null);
	}

	/**
	 * <p><code>iconCssClass</code> attribute description:</p>
	 *
	 * <div>The name of the CSS class of the icon that is to be rendered inside the list item element.</div>
	 */
	public void setIconCssClass(String iconCssClass) {
		getStateHelper().put(NavItemPropertyKeys.iconCssClass, iconCssClass);
	}

	/**
	 * <p><code>label</code> attribute description:</p>
	 *
	 * <div>The label of the anchor tag rendered inside the list item element.</div>
	 */
	public String getLabel() {
		return (String) getStateHelper().eval(NavItemPropertyKeys.label, null);
	}

	/**
	 * <p><code>label</code> attribute description:</p>
	 *
	 * <div>The label of the anchor tag rendered inside the list item element.</div>
	 */
	public void setLabel(String label) {
		getStateHelper().put(NavItemPropertyKeys.label, label);
	}

	/**
	 * <p><code>selected</code> attribute description:</p>
	 *
	 * <div>When true, the list item will be rendered with <code>class="active"</code>.</div>
	 */
	public boolean isSelected() {
		return (Boolean) getStateHelper().eval(NavItemPropertyKeys.selected, false);
	}

	/**
	 * <p><code>selected</code> attribute description:</p>
	 *
	 * <div>When true, the list item will be rendered with <code>class="active"</code>.</div>
	 */
	public void setSelected(boolean selected) {
		getStateHelper().put(NavItemPropertyKeys.selected, selected);
	}

	/**
	 * <p><code>style</code> attribute description:</p>
	 *
	 * <div>HTML passthrough attribute specifying the css style of the element.</div>
	 */
	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(NavItemPropertyKeys.style, null);
	}

	/**
	 * <p><code>style</code> attribute description:</p>
	 *
	 * <div>HTML passthrough attribute specifying the css style of the element.</div>
	 */
	@Override
	public void setStyle(String style) {
		getStateHelper().put(NavItemPropertyKeys.style, style);
	}

	/**
	 * <p><code>styleClass</code> attribute description:</p>
	 *
	 * <div>List of CSS class names (separated by spaces) that are to be rendered within the class attribute.</div>
	 */
	@Override
	public String getStyleClass() {

		// getStateHelper().eval(NavItemPropertyKeys.styleClass, null) is called because
		// super.getStyleClass() may return the styleClass name of the super class.
		String styleClass = (String) getStateHelper().eval(NavItemPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "portal-nav-item");
	}

	/**
	 * <p><code>styleClass</code> attribute description:</p>
	 *
	 * <div>List of CSS class names (separated by spaces) that are to be rendered within the class attribute.</div>
	 */
	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(NavItemPropertyKeys.styleClass, styleClass);
	}

	/**
	 * <p><code>title</code> attribute description:</p>
	 *
	 * <div>The title of the anchor tag rendered inside the list item element.</div>
	 */
	public String getTitle() {
		return (String) getStateHelper().eval(NavItemPropertyKeys.title, null);
	}

	/**
	 * <p><code>title</code> attribute description:</p>
	 *
	 * <div>The title of the anchor tag rendered inside the list item element.</div>
	 */
	public void setTitle(String title) {
		getStateHelper().put(NavItemPropertyKeys.title, title);
	}

	/**
	 * <p><code>useDialog</code> attribute description:</p>
	 *
	 * <div>When true, clicking on the href hyperlink will cause the resulting URL to popup in a modal dialog that contains an <code>iframe</code>.</div>
	 */
	public boolean isUseDialog() {
		return (Boolean) getStateHelper().eval(NavItemPropertyKeys.useDialog, false);
	}

	/**
	 * <p><code>useDialog</code> attribute description:</p>
	 *
	 * <div>When true, clicking on the href hyperlink will cause the resulting URL to popup in a modal dialog that contains an <code>iframe</code>.</div>
	 */
	public void setUseDialog(boolean useDialog) {
		getStateHelper().put(NavItemPropertyKeys.useDialog, useDialog);
	}
}
//J+
