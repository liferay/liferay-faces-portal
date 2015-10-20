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
package com.liferay.faces.portal.component.runtime;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIPanel;

import com.liferay.faces.util.component.Styleable;


/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class RuntimeBase extends UIPanel implements Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portal.component.runtime.Runtime";
	public static final String RENDERER_TYPE = "com.liferay.faces.portal.component.runtime.RuntimeRenderer";

	// Protected Enumerations
	protected enum RuntimePropertyKeys {
		defaultPreferences,
		portletName,
		queryString,
		style,
		styleClass
	}

	public RuntimeBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	/**
	 * <code>defaultPreferences</code> attribute description:
	 * <br /><br />
	 * XML markup in the form <preferences><preference><name></name><value></value></preference></preferences>. This value is persisted in the Liferay database when the embedded portlet is rendered for the first time.
	 */
	public String getDefaultPreferences() {
		return (String) getStateHelper().eval(RuntimePropertyKeys.defaultPreferences, null);
	}

	/**
	 * <code>defaultPreferences</code> attribute description:
	 * <br /><br />
	 * XML markup in the form <preferences><preference><name></name><value></value></preference></preferences>. This value is persisted in the Liferay database when the embedded portlet is rendered for the first time.
	 */
	public void setDefaultPreferences(String defaultPreferences) {
		getStateHelper().put(RuntimePropertyKeys.defaultPreferences, defaultPreferences);
	}

	/**
	 * <code>portletName</code> attribute description:
	 * <br /><br />
	 * The name of the portlet. For out-of-the-box portlets, refer to the portlet-name element of the <a href="https://github.com/liferay/liferay-portal/blob/6.2.2-ga3/portal-web/docroot/WEB-INF/liferay-portlet.xml">liferay-portlet.xml</a> descriptor.
	 */
	public String getPortletName() {
		return (String) getStateHelper().eval(RuntimePropertyKeys.portletName, null);
	}

	/**
	 * <code>portletName</code> attribute description:
	 * <br /><br />
	 * The name of the portlet. For out-of-the-box portlets, refer to the portlet-name element of the <a href="https://github.com/liferay/liferay-portal/blob/6.2.2-ga3/portal-web/docroot/WEB-INF/liferay-portlet.xml">liferay-portlet.xml</a> descriptor.
	 */
	public void setPortletName(String portletName) {
		getStateHelper().put(RuntimePropertyKeys.portletName, portletName);
	}

	/**
	 * <code>queryString</code> attribute description:
	 * <br /><br />
	 * Optional query string parameters that will be added to the request dispatcher that invokes the portlet's RENDER_PHASE.
	 */
	public String getQueryString() {
		return (String) getStateHelper().eval(RuntimePropertyKeys.queryString, null);
	}

	/**
	 * <code>queryString</code> attribute description:
	 * <br /><br />
	 * Optional query string parameters that will be added to the request dispatcher that invokes the portlet's RENDER_PHASE.
	 */
	public void setQueryString(String queryString) {
		getStateHelper().put(RuntimePropertyKeys.queryString, queryString);
	}

	/**
	 * <code>style</code> attribute description:
	 * <br /><br />
	 * HTML passthrough attribute specifying the css style of the element.
	 */
	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(RuntimePropertyKeys.style, null);
	}

	/**
	 * <code>style</code> attribute description:
	 * <br /><br />
	 * HTML passthrough attribute specifying the css style of the element.
	 */
	@Override
	public void setStyle(String style) {
		getStateHelper().put(RuntimePropertyKeys.style, style);
	}

	/**
	 * <code>styleClass</code> attribute description:
	 * <br /><br />
	 * List of CSS class names (separated by spaces) that are to be rendered within the class attribute.
	 */
	@Override
	public String getStyleClass() {

		// getStateHelper().eval(RuntimePropertyKeys.styleClass, null) is called because
		// super.getStyleClass() may return the styleClass name of the super class.
		String styleClass = (String) getStateHelper().eval(RuntimePropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "portal-runtime");
	}

	/**
	 * <code>styleClass</code> attribute description:
	 * <br /><br />
	 * List of CSS class names (separated by spaces) that are to be rendered within the class attribute.
	 */
	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(RuntimePropertyKeys.styleClass, styleClass);
	}
}
//J+
