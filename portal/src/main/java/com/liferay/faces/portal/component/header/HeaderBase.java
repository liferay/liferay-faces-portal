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
	 * <code>backLabel</code> attribute description:
	 * <br /><br />
	 * This value will be used to populate the title attribute of the back icon.
	 */
	public String getBackLabel() {
		return (String) getStateHelper().eval(HeaderPropertyKeys.backLabel, null);
	}

	/**
	 * <code>backLabel</code> attribute description:
	 * <br /><br />
	 * This value will be used to populate the title attribute of the back icon.
	 */
	public void setBackLabel(String backLabel) {
		getStateHelper().put(HeaderPropertyKeys.backLabel, backLabel);
	}

	/**
	 * <code>backURL</code> attribute description:
	 * <br /><br />
	 * The URL of the anchor tag rendered for the back icon.  The back icon is not rendered unless this is specified.
	 */
	public String getBackURL() {
		return (String) getStateHelper().eval(HeaderPropertyKeys.backURL, null);
	}

	/**
	 * <code>backURL</code> attribute description:
	 * <br /><br />
	 * The URL of the anchor tag rendered for the back icon.  The back icon is not rendered unless this is specified.
	 */
	public void setBackURL(String backURL) {
		getStateHelper().put(HeaderPropertyKeys.backURL, backURL);
	}

	/**
	 * <code>escapeXml</code> attribute description:
	 * <br /><br />
	 * When false, markup will not be escaped.
	 */
	public boolean isEscapeXml() {
		return (Boolean) getStateHelper().eval(HeaderPropertyKeys.escapeXml, true);
	}

	/**
	 * <code>escapeXml</code> attribute description:
	 * <br /><br />
	 * When false, markup will not be escaped.
	 */
	public void setEscapeXml(boolean escapeXml) {
		getStateHelper().put(HeaderPropertyKeys.escapeXml, escapeXml);
	}

	/**
	 * <code>showBackURL</code> attribute description:
	 * <br /><br />
	 * When false, the back icon is not rendered.
	 */
	public boolean isShowBackURL() {
		return (Boolean) getStateHelper().eval(HeaderPropertyKeys.showBackURL, true);
	}

	/**
	 * <code>showBackURL</code> attribute description:
	 * <br /><br />
	 * When false, the back icon is not rendered.
	 */
	public void setShowBackURL(boolean showBackURL) {
		getStateHelper().put(HeaderPropertyKeys.showBackURL, showBackURL);
	}

	/**
	 * <code>styleClass</code> attribute description:
	 * <br /><br />
	 * List of CSS class names (separated by spaces) that are to be rendered within the class attribute.
	 */
	@Override
	public String getStyleClass() {

		// getStateHelper().eval(PropertyKeys.styleClass, null) is called because
		// super.getStyleClass() may return the styleClass name of the super class.
		String styleClass = (String) getStateHelper().eval(PropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "portal-header");
	}

	/**
	 * <code>title</code> attribute description:
	 * <br /><br />
	 * This value will be used as the header text for the component.
	 */
	public String getTitle() {
		return (String) getStateHelper().eval(HeaderPropertyKeys.title, null);
	}

	/**
	 * <code>title</code> attribute description:
	 * <br /><br />
	 * This value will be used as the header text for the component.
	 */
	public void setTitle(String title) {
		getStateHelper().put(HeaderPropertyKeys.title, title);
	}
}
//J+
