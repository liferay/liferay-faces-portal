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
	 * <p><code>defaultPreferences</code> attribute description:</p>
	 *
	 * <div>XML markup in the form:<pre>&lt;preferences&gt;&lt;preference&gt;&lt;name&gt;&lt;/name&gt;&lt;value&gt;&lt;/value&gt;&lt;/preference&gt;&lt;/preferences&gt;</pre>This value is persisted in the Liferay database when the embedded portlet is rendered for the first time.</div>
	 */
	public String getDefaultPreferences() {
		return (String) getStateHelper().eval(RuntimePropertyKeys.defaultPreferences, null);
	}

	/**
	 * <p><code>defaultPreferences</code> attribute description:</p>
	 *
	 * <div>XML markup in the form:<pre>&lt;preferences&gt;&lt;preference&gt;&lt;name&gt;&lt;/name&gt;&lt;value&gt;&lt;/value&gt;&lt;/preference&gt;&lt;/preferences&gt;</pre>This value is persisted in the Liferay database when the embedded portlet is rendered for the first time.</div>
	 */
	public void setDefaultPreferences(String defaultPreferences) {
		getStateHelper().put(RuntimePropertyKeys.defaultPreferences, defaultPreferences);
	}

	/**
	 * <p><code>portletName</code> attribute description:</p>
	 *
	 * <div>The name of the portlet. For out-of-the-box portlets, refer to the portlet-name element of the <a href="https://github.com/liferay/liferay-portal/blob/6.2.2-ga3/portal-web/docroot/WEB-INF/liferay-portlet.xml">liferay-portlet.xml</a> descriptor.</div>
	 */
	public String getPortletName() {
		return (String) getStateHelper().eval(RuntimePropertyKeys.portletName, null);
	}

	/**
	 * <p><code>portletName</code> attribute description:</p>
	 *
	 * <div>The name of the portlet. For out-of-the-box portlets, refer to the portlet-name element of the <a href="https://github.com/liferay/liferay-portal/blob/6.2.2-ga3/portal-web/docroot/WEB-INF/liferay-portlet.xml">liferay-portlet.xml</a> descriptor.</div>
	 */
	public void setPortletName(String portletName) {
		getStateHelper().put(RuntimePropertyKeys.portletName, portletName);
	}

	/**
	 * <p><code>queryString</code> attribute description:</p>
	 *
	 * <div>Optional query string parameters that will be added to the request dispatcher that invokes the portlet's RENDER_PHASE.</div>
	 */
	public String getQueryString() {
		return (String) getStateHelper().eval(RuntimePropertyKeys.queryString, null);
	}

	/**
	 * <p><code>queryString</code> attribute description:</p>
	 *
	 * <div>Optional query string parameters that will be added to the request dispatcher that invokes the portlet's RENDER_PHASE.</div>
	 */
	public void setQueryString(String queryString) {
		getStateHelper().put(RuntimePropertyKeys.queryString, queryString);
	}

	/**
	 * <p><code>style</code> attribute description:</p>
	 *
	 * <div>HTML passthrough attribute specifying the css style of the element.</div>
	 */
	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(RuntimePropertyKeys.style, null);
	}

	/**
	 * <p><code>style</code> attribute description:</p>
	 *
	 * <div>HTML passthrough attribute specifying the css style of the element.</div>
	 */
	@Override
	public void setStyle(String style) {
		getStateHelper().put(RuntimePropertyKeys.style, style);
	}

	/**
	 * <p><code>styleClass</code> attribute description:</p>
	 *
	 * <div>List of CSS class names (separated by spaces) that are to be rendered within the class attribute.</div>
	 */
	@Override
	public String getStyleClass() {

		// getStateHelper().eval(RuntimePropertyKeys.styleClass, null) is called because
		// super.getStyleClass() may return the styleClass name of the super class.
		String styleClass = (String) getStateHelper().eval(RuntimePropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "portal-runtime");
	}

	/**
	 * <p><code>styleClass</code> attribute description:</p>
	 *
	 * <div>List of CSS class names (separated by spaces) that are to be rendered within the class attribute.</div>
	 */
	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(RuntimePropertyKeys.styleClass, styleClass);
	}
}
//J+
