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
package com.liferay.faces.portal.component.header;
//J-

import javax.annotation.Generated;
import javax.faces.component.html.HtmlPanelGroup;

import com.liferay.faces.util.component.Styleable;


/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class HeaderBase extends HtmlPanelGroup implements Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portal.component.header.Header";
	public static final String RENDERER_TYPE = "com.liferay.faces.portal.component.header.HeaderRenderer";

	// Protected Enumerations
	protected enum HeaderPropertyKeys {
		backLabel,
		backURL,
		escapeXml,
		showBackURL,
		title
	}

	public HeaderBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	/**
	 * <p><code>backLabel</code> attribute description:</p>
	 *
	 * <div>This value will be used to populate the title attribute of the back icon.</div>
	 */
	public String getBackLabel() {
		return (String) getStateHelper().eval(HeaderPropertyKeys.backLabel, null);
	}

	/**
	 * <p><code>backLabel</code> attribute description:</p>
	 *
	 * <div>This value will be used to populate the title attribute of the back icon.</div>
	 */
	public void setBackLabel(String backLabel) {
		getStateHelper().put(HeaderPropertyKeys.backLabel, backLabel);
	}

	/**
	 * <p><code>backURL</code> attribute description:</p>
	 *
	 * <div>The URL of the anchor tag rendered for the back icon.  The back icon is not rendered unless this is specified.</div>
	 */
	public String getBackURL() {
		return (String) getStateHelper().eval(HeaderPropertyKeys.backURL, null);
	}

	/**
	 * <p><code>backURL</code> attribute description:</p>
	 *
	 * <div>The URL of the anchor tag rendered for the back icon.  The back icon is not rendered unless this is specified.</div>
	 */
	public void setBackURL(String backURL) {
		getStateHelper().put(HeaderPropertyKeys.backURL, backURL);
	}

	/**
	 * <p><code>escapeXml</code> attribute description:</p>
	 *
	 * <div>When false, markup will not be escaped.</div>
	 */
	public boolean isEscapeXml() {
		return (Boolean) getStateHelper().eval(HeaderPropertyKeys.escapeXml, true);
	}

	/**
	 * <p><code>escapeXml</code> attribute description:</p>
	 *
	 * <div>When false, markup will not be escaped.</div>
	 */
	public void setEscapeXml(boolean escapeXml) {
		getStateHelper().put(HeaderPropertyKeys.escapeXml, escapeXml);
	}

	/**
	 * <p><code>showBackURL</code> attribute description:</p>
	 *
	 * <div>When false, the back icon is not rendered.</div>
	 */
	public boolean isShowBackURL() {
		return (Boolean) getStateHelper().eval(HeaderPropertyKeys.showBackURL, true);
	}

	/**
	 * <p><code>showBackURL</code> attribute description:</p>
	 *
	 * <div>When false, the back icon is not rendered.</div>
	 */
	public void setShowBackURL(boolean showBackURL) {
		getStateHelper().put(HeaderPropertyKeys.showBackURL, showBackURL);
	}

	/**
	 * <p><code>styleClass</code> attribute description:</p>
	 *
	 * <div>List of CSS class names (separated by spaces) that are to be rendered within the class attribute.</div>
	 */
	@Override
	public String getStyleClass() {

		// getStateHelper().eval(PropertyKeys.styleClass, null) is called because
		// super.getStyleClass() may return the styleClass name of the super class.
		String styleClass = (String) getStateHelper().eval(PropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "portal-header");
	}

	/**
	 * <p><code>title</code> attribute description:</p>
	 *
	 * <div>This value will be used as the header text for the component.</div>
	 */
	public String getTitle() {
		return (String) getStateHelper().eval(HeaderPropertyKeys.title, null);
	}

	/**
	 * <p><code>title</code> attribute description:</p>
	 *
	 * <div>This value will be used as the header text for the component.</div>
	 */
	public void setTitle(String title) {
		getStateHelper().put(HeaderPropertyKeys.title, title);
	}
}
//J+
