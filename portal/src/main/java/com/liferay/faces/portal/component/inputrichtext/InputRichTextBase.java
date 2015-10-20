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
package com.liferay.faces.portal.component.inputrichtext;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIInput;

import com.liferay.faces.util.component.Styleable;


/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class InputRichTextBase extends UIInput implements Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portal.component.inputrichtext.InputRichText";
	public static final String RENDERER_TYPE = "com.liferay.faces.portal.component.inputrichtext.InputRichTextRenderer";

	// Protected Enumerations
	protected enum InputRichTextPropertyKeys {
		configParams,
		contentsLanguageId,
		editorKey,
		fileBrowserParams,
		label,
		maxPlainTextChars,
		minPlainTextChars,
		onblur,
		onchange,
		onfocus,
		resizable,
		skipEditorLoading,
		style,
		styleClass,
		toolbarSet
	}

	public InputRichTextBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	/**
	 * <code>configParams</code> attribute description:
	 * <br /><br />
	 * Optional map of configuration parameters. For example usage, see the Wiki portlet <a href="https://github.com/liferay/liferay-portal/blob/6.2.1-ga2/portal-web/docroot/html/portlet/wiki/edit/editor_config.jspf#L28" target="_blank">editor_config.jsp</a> source code.
	 */
	@SuppressWarnings("unchecked")
	public java.util.Map<String,String> getConfigParams() {
		return (java.util.Map<String,String>) getStateHelper().eval(InputRichTextPropertyKeys.configParams, null);
	}

	/**
	 * <code>configParams</code> attribute description:
	 * <br /><br />
	 * Optional map of configuration parameters. For example usage, see the Wiki portlet <a href="https://github.com/liferay/liferay-portal/blob/6.2.1-ga2/portal-web/docroot/html/portlet/wiki/edit/editor_config.jspf#L28" target="_blank">editor_config.jsp</a> source code.
	 */
	public void setConfigParams(java.util.Map<String,String> configParams) {
		getStateHelper().put(InputRichTextPropertyKeys.configParams, configParams);
	}

	/**
	 * <code>contentsLanguageId</code> attribute description:
	 * <br /><br />
	 * The locale (language id) of the rich text content.
	 */
	public String getContentsLanguageId() {
		return (String) getStateHelper().eval(InputRichTextPropertyKeys.contentsLanguageId, null);
	}

	/**
	 * <code>contentsLanguageId</code> attribute description:
	 * <br /><br />
	 * The locale (language id) of the rich text content.
	 */
	public void setContentsLanguageId(String contentsLanguageId) {
		getStateHelper().put(InputRichTextPropertyKeys.contentsLanguageId, contentsLanguageId);
	}

	/**
	 * <code>editorKey</code> attribute description:
	 * <br /><br />
	 * Liferay Portal property key name (typically found in portal.properties or portal-ext.properties). Valid property key values are "ckeditor", "ckeditor_bbcode", and "ckeditor_creole". The default key name is "editor.wysiwyg.default" which has a value of "ckeditor".
	 */
	public String getEditorKey() {
		return (String) getStateHelper().eval(InputRichTextPropertyKeys.editorKey, "editor.wysiwyg.default");
	}

	/**
	 * <code>editorKey</code> attribute description:
	 * <br /><br />
	 * Liferay Portal property key name (typically found in portal.properties or portal-ext.properties). Valid property key values are "ckeditor", "ckeditor_bbcode", and "ckeditor_creole". The default key name is "editor.wysiwyg.default" which has a value of "ckeditor".
	 */
	public void setEditorKey(String editorKey) {
		getStateHelper().put(InputRichTextPropertyKeys.editorKey, editorKey);
	}

	/**
	 * <code>fileBrowserParams</code> attribute description:
	 * <br /><br />
	 * Optional map of file browser parameters. For example usage, see the Wiki portlet <a href="https://github.com/liferay/liferay-portal/blob/6.2.1-ga2/portal-web/docroot/html/portlet/wiki/edit/editor_config.jspf#L33" target="_blank">editor_config.jsp</a> source code.
	 */
	@SuppressWarnings("unchecked")
	public java.util.Map<String,String> getFileBrowserParams() {
		return (java.util.Map<String,String>) getStateHelper().eval(InputRichTextPropertyKeys.fileBrowserParams, null);
	}

	/**
	 * <code>fileBrowserParams</code> attribute description:
	 * <br /><br />
	 * Optional map of file browser parameters. For example usage, see the Wiki portlet <a href="https://github.com/liferay/liferay-portal/blob/6.2.1-ga2/portal-web/docroot/html/portlet/wiki/edit/editor_config.jspf#L33" target="_blank">editor_config.jsp</a> source code.
	 */
	public void setFileBrowserParams(java.util.Map<String,String> fileBrowserParams) {
		getStateHelper().put(InputRichTextPropertyKeys.fileBrowserParams, fileBrowserParams);
	}

	/**
	 * <code>label</code> attribute description:
	 * <br /><br />
	 * A localized label for this component that is typically only rendered in a FacesMessage when validation fails.
	 */
	public String getLabel() {

		String label = (String) getStateHelper().eval(InputRichTextPropertyKeys.label, null);

		if (label == null) {

			javax.faces.context.FacesContext facesContext = javax.faces.context.FacesContext.getCurrentInstance();

			if (facesContext.getCurrentPhaseId() == javax.faces.event.PhaseId.PROCESS_VALIDATIONS) {
				label = com.liferay.faces.util.component.ComponentUtil.getComponentLabel(this);
			}
		}

		return label;
	}

	/**
	 * <code>label</code> attribute description:
	 * <br /><br />
	 * A localized label for this component that is typically only rendered in a FacesMessage when validation fails.
	 */
	public void setLabel(String label) {
		getStateHelper().put(InputRichTextPropertyKeys.label, label);
	}

	/**
	 * <code>maxPlainTextChars</code> attribute description:
	 * <br /><br />
	 * The maximum number of plain text characters, meaning characters that do not include HTML tags such as <code>&lt;strong&gt;</code>, <code>&lt;em&gt;</code>, and <code>&lt;a href="..."&gt;</code>.
	 */
	public int getMaxPlainTextChars() {
		return (Integer) getStateHelper().eval(InputRichTextPropertyKeys.maxPlainTextChars, Integer.MAX_VALUE);
	}

	/**
	 * <code>maxPlainTextChars</code> attribute description:
	 * <br /><br />
	 * The maximum number of plain text characters, meaning characters that do not include HTML tags such as <code>&lt;strong&gt;</code>, <code>&lt;em&gt;</code>, and <code>&lt;a href="..."&gt;</code>.
	 */
	public void setMaxPlainTextChars(int maxPlainTextChars) {
		getStateHelper().put(InputRichTextPropertyKeys.maxPlainTextChars, maxPlainTextChars);
	}

	/**
	 * <code>minPlainTextChars</code> attribute description:
	 * <br /><br />
	 * The minimum number of plain text characters, meaning characters that do not include HTML tags such as <code>&lt;strong&gt;</code>, <code>&lt;em&gt;</code>, and <code>&lt;a href="..."&gt;</code>.
	 */
	public int getMinPlainTextChars() {
		return (Integer) getStateHelper().eval(InputRichTextPropertyKeys.minPlainTextChars, 0);
	}

	/**
	 * <code>minPlainTextChars</code> attribute description:
	 * <br /><br />
	 * The minimum number of plain text characters, meaning characters that do not include HTML tags such as <code>&lt;strong&gt;</code>, <code>&lt;em&gt;</code>, and <code>&lt;a href="..."&gt;</code>.
	 */
	public void setMinPlainTextChars(int minPlainTextChars) {
		getStateHelper().put(InputRichTextPropertyKeys.minPlainTextChars, minPlainTextChars);
	}

	/**
	 * <code>onblur</code> attribute description:
	 * <br /><br />
	 * Javascript to execute after this component loses focus.
	 */
	public String getOnblur() {
		return (String) getStateHelper().eval(InputRichTextPropertyKeys.onblur, null);
	}

	/**
	 * <code>onblur</code> attribute description:
	 * <br /><br />
	 * Javascript to execute after this component loses focus.
	 */
	public void setOnblur(String onblur) {
		getStateHelper().put(InputRichTextPropertyKeys.onblur, onblur);
	}

	/**
	 * <code>onchange</code> attribute description:
	 * <br /><br />
	 * Javascript to execute after this component's value has changed, and then it loses focus.
	 */
	public String getOnchange() {
		return (String) getStateHelper().eval(InputRichTextPropertyKeys.onchange, null);
	}

	/**
	 * <code>onchange</code> attribute description:
	 * <br /><br />
	 * Javascript to execute after this component's value has changed, and then it loses focus.
	 */
	public void setOnchange(String onchange) {
		getStateHelper().put(InputRichTextPropertyKeys.onchange, onchange);
	}

	/**
	 * <code>onfocus</code> attribute description:
	 * <br /><br />
	 * Javascript to execute when this component gets focus.
	 */
	public String getOnfocus() {
		return (String) getStateHelper().eval(InputRichTextPropertyKeys.onfocus, null);
	}

	/**
	 * <code>onfocus</code> attribute description:
	 * <br /><br />
	 * Javascript to execute when this component gets focus.
	 */
	public void setOnfocus(String onfocus) {
		getStateHelper().put(InputRichTextPropertyKeys.onfocus, onfocus);
	}

	/**
	 * <code>resizable</code> attribute description:
	 * <br /><br />
	 * When true, the will be resizable by the end-user when dragging on the bottom-right resize handle.
	 */
	public boolean isResizable() {
		return (Boolean) getStateHelper().eval(InputRichTextPropertyKeys.resizable, true);
	}

	/**
	 * <code>resizable</code> attribute description:
	 * <br /><br />
	 * When true, the will be resizable by the end-user when dragging on the bottom-right resize handle.
	 */
	public void setResizable(boolean resizable) {
		getStateHelper().put(InputRichTextPropertyKeys.resizable, resizable);
	}

	/**
	 * <code>skipEditorLoading</code> attribute description:
	 * <br /><br />
	 * When true, the resources necessary for loading the CKEditor will not be added to the <code>&lt;head&gt;...&lt;/head&gt;</code> section of the portal page.
	 */
	public boolean isSkipEditorLoading() {
		return (Boolean) getStateHelper().eval(InputRichTextPropertyKeys.skipEditorLoading, false);
	}

	/**
	 * <code>skipEditorLoading</code> attribute description:
	 * <br /><br />
	 * When true, the resources necessary for loading the CKEditor will not be added to the <code>&lt;head&gt;...&lt;/head&gt;</code> section of the portal page.
	 */
	public void setSkipEditorLoading(boolean skipEditorLoading) {
		getStateHelper().put(InputRichTextPropertyKeys.skipEditorLoading, skipEditorLoading);
	}

	/**
	 * <code>style</code> attribute description:
	 * <br /><br />
	 * HTML passthrough attribute specifying the css style of the element.
	 */
	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(InputRichTextPropertyKeys.style, null);
	}

	/**
	 * <code>style</code> attribute description:
	 * <br /><br />
	 * HTML passthrough attribute specifying the css style of the element.
	 */
	@Override
	public void setStyle(String style) {
		getStateHelper().put(InputRichTextPropertyKeys.style, style);
	}

	/**
	 * <code>styleClass</code> attribute description:
	 * <br /><br />
	 * List of CSS class names (separated by spaces) that are to be rendered within the class attribute.
	 */
	@Override
	public String getStyleClass() {

		// getStateHelper().eval(InputRichTextPropertyKeys.styleClass, null) is called because
		// super.getStyleClass() may return the styleClass name of the super class.
		String styleClass = (String) getStateHelper().eval(InputRichTextPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "portal-input-rich-text");
	}

	/**
	 * <code>styleClass</code> attribute description:
	 * <br /><br />
	 * List of CSS class names (separated by spaces) that are to be rendered within the class attribute.
	 */
	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(InputRichTextPropertyKeys.styleClass, styleClass);
	}

	/**
	 * <code>toolbarSet</code> attribute description:
	 * <br /><br />
	 * The name of the set of toolbars that are to be displayed above the rich text input area. Valid values are "bbcode", "creole", "editInPlace", "email", "liferay", "liferayArticle", "phone", "simple", and "tablet". The default value depends on the value of the editorKey attribute. If editorKey's property evaluates to "ckeditor" then the default value is "liferay". If editorKey's property evaluates to "ckeditor_bbcode" then the default value is "bbcode". If editorKey's property evaluates to "ckeditor_creole" then the default value is "creole".
	 */
	public String getToolbarSet() {
		return (String) getStateHelper().eval(InputRichTextPropertyKeys.toolbarSet, null);
	}

	/**
	 * <code>toolbarSet</code> attribute description:
	 * <br /><br />
	 * The name of the set of toolbars that are to be displayed above the rich text input area. Valid values are "bbcode", "creole", "editInPlace", "email", "liferay", "liferayArticle", "phone", "simple", and "tablet". The default value depends on the value of the editorKey attribute. If editorKey's property evaluates to "ckeditor" then the default value is "liferay". If editorKey's property evaluates to "ckeditor_bbcode" then the default value is "bbcode". If editorKey's property evaluates to "ckeditor_creole" then the default value is "creole".
	 */
	public void setToolbarSet(String toolbarSet) {
		getStateHelper().put(InputRichTextPropertyKeys.toolbarSet, toolbarSet);
	}
}
//J+
