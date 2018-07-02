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
package com.liferay.faces.portal.component.inputsearch;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIInput;

import com.liferay.faces.util.component.Styleable;


/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class InputSearchBase extends UIInput implements Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portal.component.inputsearch.InputSearch";
	public static final String RENDERER_TYPE = "com.liferay.faces.portal.component.inputsearch.InputSearchRenderer";

	// Protected Enumerations
	protected enum InputSearchPropertyKeys {
		action,
		actionListener,
		autoFocus,
		buttonLabel,
		placeholder,
		showButton,
		style,
		styleClass,
		title
	}

	public InputSearchBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	/**
	 * <p><code>action</code> attribute description:</p>
	 *
	 * <div>A method that is executed when this component is clicked. The method must be <code>public</code>, return an <code>Object</code>, and take no arguments. The <code>toString()</code> method of the returned object is used to determine where to navigate (if at all) after the action has been performed.</div>
	 */
	public javax.el.MethodExpression getAction() {
		return (javax.el.MethodExpression) getStateHelper().eval(InputSearchPropertyKeys.action, null);
	}

	/**
	 * <p><code>action</code> attribute description:</p>
	 *
	 * <div>A method that is executed when this component is clicked. The method must be <code>public</code>, return an <code>Object</code>, and take no arguments. The <code>toString()</code> method of the returned object is used to determine where to navigate (if at all) after the action has been performed.</div>
	 */
	public void setAction(javax.el.MethodExpression action) {
		getStateHelper().put(InputSearchPropertyKeys.action, action);
	}

	/**
	 * <p><code>actionListener</code> attribute description:</p>
	 *
	 * <div>A method that is executed when this component is clicked. The method must be <code>public</code>, return <code>void</code>, and take either no arguments or one <code>ActionEvent</code> argument.</div>
	 */
	public javax.el.MethodExpression getActionListener() {
		return (javax.el.MethodExpression) getStateHelper().eval(InputSearchPropertyKeys.actionListener, null);
	}

	/**
	 * <p><code>actionListener</code> attribute description:</p>
	 *
	 * <div>A method that is executed when this component is clicked. The method must be <code>public</code>, return <code>void</code>, and take either no arguments or one <code>ActionEvent</code> argument.</div>
	 */
	public void setActionListener(javax.el.MethodExpression actionListener) {
		getStateHelper().put(InputSearchPropertyKeys.actionListener, actionListener);
	}

	/**
	 * <p><code>autoFocus</code> attribute description:</p>
	 *
	 * <div>Sets whether the search field gets focus by default. The default value is <code>false</code>.</div>
	 */
	public boolean isAutoFocus() {
		return (Boolean) getStateHelper().eval(InputSearchPropertyKeys.autoFocus, false);
	}

	/**
	 * <p><code>autoFocus</code> attribute description:</p>
	 *
	 * <div>Sets whether the search field gets focus by default. The default value is <code>false</code>.</div>
	 */
	public void setAutoFocus(boolean autoFocus) {
		getStateHelper().put(InputSearchPropertyKeys.autoFocus, autoFocus);
	}

	/**
	 * <p><code>buttonLabel</code> attribute description:</p>
	 *
	 * <div>Sets the text value for the search button's label. The default value is <code>Search</code>.</div>
	 */
	public String getButtonLabel() {
		return (String) getStateHelper().eval(InputSearchPropertyKeys.buttonLabel, null);
	}

	/**
	 * <p><code>buttonLabel</code> attribute description:</p>
	 *
	 * <div>Sets the text value for the search button's label. The default value is <code>Search</code>.</div>
	 */
	public void setButtonLabel(String buttonLabel) {
		getStateHelper().put(InputSearchPropertyKeys.buttonLabel, buttonLabel);
	}

	/**
	 * <p><code>placeholder</code> attribute description:</p>
	 *
	 * <div>Sets the placeholder text for the search field. The default value is the value of the <code>buttonLabel</code> attribute.</div>
	 */
	public String getPlaceholder() {
		return (String) getStateHelper().eval(InputSearchPropertyKeys.placeholder, null);
	}

	/**
	 * <p><code>placeholder</code> attribute description:</p>
	 *
	 * <div>Sets the placeholder text for the search field. The default value is the value of the <code>buttonLabel</code> attribute.</div>
	 */
	public void setPlaceholder(String placeholder) {
		getStateHelper().put(InputSearchPropertyKeys.placeholder, placeholder);
	}

	/**
	 * <p><code>showButton</code> attribute description:</p>
	 *
	 * <div>Sets whether to show the search button.</div>
	 */
	public boolean isShowButton() {
		return (Boolean) getStateHelper().eval(InputSearchPropertyKeys.showButton, true);
	}

	/**
	 * <p><code>showButton</code> attribute description:</p>
	 *
	 * <div>Sets whether to show the search button.</div>
	 */
	public void setShowButton(boolean showButton) {
		getStateHelper().put(InputSearchPropertyKeys.showButton, showButton);
	}

	/**
	 * <p><code>style</code> attribute description:</p>
	 *
	 * <div>HTML passthrough attribute specifying the css style of the element.</div>
	 */
	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(InputSearchPropertyKeys.style, null);
	}

	/**
	 * <p><code>style</code> attribute description:</p>
	 *
	 * <div>HTML passthrough attribute specifying the css style of the element.</div>
	 */
	@Override
	public void setStyle(String style) {
		getStateHelper().put(InputSearchPropertyKeys.style, style);
	}

	/**
	 * <p><code>styleClass</code> attribute description:</p>
	 *
	 * <div>List of CSS class names (separated by spaces) that are to be rendered within the class attribute.</div>
	 */
	@Override
	public String getStyleClass() {

		// getStateHelper().eval(InputSearchPropertyKeys.styleClass, null) is called because
		// super.getStyleClass() may return the styleClass name of the super class.
		String styleClass = (String) getStateHelper().eval(InputSearchPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "portal-input-search");
	}

	/**
	 * <p><code>styleClass</code> attribute description:</p>
	 *
	 * <div>List of CSS class names (separated by spaces) that are to be rendered within the class attribute.</div>
	 */
	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(InputSearchPropertyKeys.styleClass, styleClass);
	}

	/**
	 * <p><code>title</code> attribute description:</p>
	 *
	 * <div>Sets the search box's title.</div>
	 */
	public String getTitle() {
		return (String) getStateHelper().eval(InputSearchPropertyKeys.title, null);
	}

	/**
	 * <p><code>title</code> attribute description:</p>
	 *
	 * <div>Sets the search box's title.</div>
	 */
	public void setTitle(String title) {
		getStateHelper().put(InputSearchPropertyKeys.title, title);
	}
}
//J+
