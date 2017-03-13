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
	 * <code>action</code> attribute description:
	 * <br /><br />
	 * A method that is executed when this component is clicked. The method must be <code>public</code>, return an <code>Object</code>, and take no arguments. The <code>toString()</code> method of the returned object is used to determine where to navigate (if at all) after the action has been performed.
	 */
	public javax.el.MethodExpression getAction() {
		return (javax.el.MethodExpression) getStateHelper().eval(InputSearchPropertyKeys.action, null);
	}

	/**
	 * <code>action</code> attribute description:
	 * <br /><br />
	 * A method that is executed when this component is clicked. The method must be <code>public</code>, return an <code>Object</code>, and take no arguments. The <code>toString()</code> method of the returned object is used to determine where to navigate (if at all) after the action has been performed.
	 */
	public void setAction(javax.el.MethodExpression action) {
		getStateHelper().put(InputSearchPropertyKeys.action, action);
	}

	/**
	 * <code>actionListener</code> attribute description:
	 * <br /><br />
	 * A method that is executed when this component is clicked. The method must be <code>public</code>, return <code>void</code>, and take either no arguments or one <code>ActionEvent</code> argument.
	 */
	public javax.el.MethodExpression getActionListener() {
		return (javax.el.MethodExpression) getStateHelper().eval(InputSearchPropertyKeys.actionListener, null);
	}

	/**
	 * <code>actionListener</code> attribute description:
	 * <br /><br />
	 * A method that is executed when this component is clicked. The method must be <code>public</code>, return <code>void</code>, and take either no arguments or one <code>ActionEvent</code> argument.
	 */
	public void setActionListener(javax.el.MethodExpression actionListener) {
		getStateHelper().put(InputSearchPropertyKeys.actionListener, actionListener);
	}

	/**
	 * <code>autoFocus</code> attribute description:
	 * <br /><br />
	 * Sets whether the search field gets focus by default. The default value is <code>false</code>.
	 */
	public boolean isAutoFocus() {
		return (Boolean) getStateHelper().eval(InputSearchPropertyKeys.autoFocus, false);
	}

	/**
	 * <code>autoFocus</code> attribute description:
	 * <br /><br />
	 * Sets whether the search field gets focus by default. The default value is <code>false</code>.
	 */
	public void setAutoFocus(boolean autoFocus) {
		getStateHelper().put(InputSearchPropertyKeys.autoFocus, autoFocus);
	}

	/**
	 * <code>buttonLabel</code> attribute description:
	 * <br /><br />
	 * Sets the text value for the search button's label. The default value is <code>Search</code>.
	 */
	public String getButtonLabel() {
		return (String) getStateHelper().eval(InputSearchPropertyKeys.buttonLabel, null);
	}

	/**
	 * <code>buttonLabel</code> attribute description:
	 * <br /><br />
	 * Sets the text value for the search button's label. The default value is <code>Search</code>.
	 */
	public void setButtonLabel(String buttonLabel) {
		getStateHelper().put(InputSearchPropertyKeys.buttonLabel, buttonLabel);
	}

	/**
	 * <code>placeholder</code> attribute description:
	 * <br /><br />
	 * Sets the placeholder text for the search field. The default value is the value of the <code>buttonLabel</code> attribute.
	 */
	public String getPlaceholder() {
		return (String) getStateHelper().eval(InputSearchPropertyKeys.placeholder, null);
	}

	/**
	 * <code>placeholder</code> attribute description:
	 * <br /><br />
	 * Sets the placeholder text for the search field. The default value is the value of the <code>buttonLabel</code> attribute.
	 */
	public void setPlaceholder(String placeholder) {
		getStateHelper().put(InputSearchPropertyKeys.placeholder, placeholder);
	}

	/**
	 * <code>showButton</code> attribute description:
	 * <br /><br />
	 * Sets whether to show the search button.
	 */
	public boolean isShowButton() {
		return (Boolean) getStateHelper().eval(InputSearchPropertyKeys.showButton, true);
	}

	/**
	 * <code>showButton</code> attribute description:
	 * <br /><br />
	 * Sets whether to show the search button.
	 */
	public void setShowButton(boolean showButton) {
		getStateHelper().put(InputSearchPropertyKeys.showButton, showButton);
	}

	/**
	 * <code>style</code> attribute description:
	 * <br /><br />
	 * HTML passthrough attribute specifying the css style of the element.
	 */
	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(InputSearchPropertyKeys.style, null);
	}

	/**
	 * <code>style</code> attribute description:
	 * <br /><br />
	 * HTML passthrough attribute specifying the css style of the element.
	 */
	@Override
	public void setStyle(String style) {
		getStateHelper().put(InputSearchPropertyKeys.style, style);
	}

	/**
	 * <code>styleClass</code> attribute description:
	 * <br /><br />
	 * List of CSS class names (separated by spaces) that are to be rendered within the class attribute.
	 */
	@Override
	public String getStyleClass() {

		// getStateHelper().eval(InputSearchPropertyKeys.styleClass, null) is called because
		// super.getStyleClass() may return the styleClass name of the super class.
		String styleClass = (String) getStateHelper().eval(InputSearchPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "portal-input-search");
	}

	/**
	 * <code>styleClass</code> attribute description:
	 * <br /><br />
	 * List of CSS class names (separated by spaces) that are to be rendered within the class attribute.
	 */
	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(InputSearchPropertyKeys.styleClass, styleClass);
	}

	/**
	 * <code>title</code> attribute description:
	 * <br /><br />
	 * Sets the search box's title.
	 */
	public String getTitle() {
		return (String) getStateHelper().eval(InputSearchPropertyKeys.title, null);
	}

	/**
	 * <code>title</code> attribute description:
	 * <br /><br />
	 * Sets the search box's title.
	 */
	public void setTitle(String title) {
		getStateHelper().put(InputSearchPropertyKeys.title, title);
	}
}
//J+
