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
package com.liferay.faces.portal.render.internal;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.Tag;

import com.liferay.faces.portal.jsp.internal.JspWriterStringImpl;
import com.liferay.faces.util.context.FacesRequestContext;

import com.liferay.portal.kernel.servlet.JSPSupportServlet;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import com.liferay.taglib.BaseBodyTagSupport;
import com.liferay.taglib.aui.ScriptTag;


/**
 * This abstract class serves as a generic JSF {@link Renderer} that invokes the JSP tag lifecycle of a Liferay Portal
 * JSP tag and encodes the output.
 *
 * @author  Neil Griffin
 */
public abstract class PortalTagRenderer<U extends UIComponent, T extends Tag> extends PortalTagRendererCompat {

	// Protected Constants
	protected static final String CORRESPONDING_JSP_TAG = "correspondingJspTag";
	protected static final String POST_CHILD_MARKUP = "postChildMarkup";

	/**
	 * Creates a new instance of the JSP tag.
	 */
	public abstract T newTag();

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		try {

			// Create an instance of the JSP tag that corresponds to the JSF component.
			T tag = newTag();
			copyAttributes(facesContext, cast(uiComponent), tag);

			Map<String, Object> componentAttributes = uiComponent.getAttributes();
			componentAttributes.put(CORRESPONDING_JSP_TAG, tag);

			// Get the output of the JSP tag (and all child JSP tags).
			String portalTagOutput = getPortalTagOutput(facesContext, uiComponent, tag);
			String preChildMarkup = portalTagOutput;

			// Determine the point at which children should be inserted into the markup.
			String childInsertionMarker = getChildInsertionMarker();

			if (childInsertionMarker != null) {

				int pos = preChildMarkup.indexOf(childInsertionMarker);

				if (pos > 0) {
					String postChildMarkup = preChildMarkup.substring(pos).trim();
					componentAttributes.put(POST_CHILD_MARKUP, postChildMarkup);
					preChildMarkup = preChildMarkup.substring(0, pos).trim();
				}
			}

			// Encode the output of the JSP tag up until the point at which children should be inserted.
			ResponseWriter responseWriter = facesContext.getResponseWriter();
			responseWriter.write(preChildMarkup);
		}
		catch (JspException e) {
			throw new IOException(e);
		}
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the output of the JSP tag that is to appear after the children.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		Map<String, Object> componentAttributes = uiComponent.getAttributes();
		String postChildMarkup = (String) componentAttributes.remove(POST_CHILD_MARKUP);

		if (postChildMarkup != null) {
			responseWriter.write(postChildMarkup);
		}

		componentAttributes.remove(CORRESPONDING_JSP_TAG);
	}

	public String getChildInsertionMarker() {
		return null;
	}

	public boolean writeBodyContent() {
		return false;
	}

	/**
	 * Casts a UIComponent to a concrete instance of UIComponent.
	 */
	protected abstract U cast(UIComponent uiComponent);

	/**
	 * Copy attributes from the JSF component to the JSP tag that are common between JSF and JSP.
	 */
	protected abstract void copyFrameworkAttributes(FacesContext facesContext, U u, T t);

	/**
	 * Copy attributes from the JSF component to the JSP tag that are not common between JSF and JSP.
	 */
	protected abstract void copyNonFrameworkAttributes(FacesContext facesContext, U u, T t);

	protected void copyAttributes(FacesContext facesContext, U u, T t) {
		copyFrameworkAttributes(facesContext, u, t);
		copyNonFrameworkAttributes(facesContext, u, t);
		t.setParent(getParentTag(facesContext, u, t));
	}

	protected HttpServletResponse getHttpServletResponse(PortletResponse portletResponse) {
		return PortalUtil.getHttpServletResponse(portletResponse);
	}

	protected Tag getParentTag(FacesContext facesContext, U u, T t) {

		UIComponent uiComponent = cast(u);
		UIComponent parentComponent = uiComponent.getParent();
		Map<String, Object> parentComponentAttributes = parentComponent.getAttributes();

		return (Tag) parentComponentAttributes.get(CORRESPONDING_JSP_TAG);
	}

	protected String getPortalTagOutput(FacesContext facesContext, UIComponent uiComponent, Tag tag)
		throws JspException {

		return getPortalTagOutput(facesContext, uiComponent, tag, null);
	}

	protected String getPortalTagOutput(FacesContext facesContext, UIComponent uiComponent, Tag tag,
		String customBodyContent) throws JspException {

		// Setup the Facelet -> JSP tag adapter.
		ExternalContext externalContext = facesContext.getExternalContext();
		PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();

		portletRequest.setAttribute("aui:form:portletNamespace", "");
		portletRequest.setAttribute("aui:form:useNamespace", "false");

		HttpServletRequest httpServletRequest = getHttpServletRequest(portletRequest);
		PortletResponse portletResponse = (PortletResponse) externalContext.getResponse();
		HttpServletResponse httpServletResponse = getHttpServletResponse(portletResponse);
		String contentType = httpServletResponse.getContentType();

		JspWriterStringImpl stringWriter = new JspWriterStringImpl();
		JspFactory jspFactory = JspFactory.getDefaultFactory();
		ServletContext servletContext = httpServletRequest.getServletContext();
		JSPSupportServlet jspSupportServlet = new JSPSupportServlet(servletContext);
		PageContext stringPageContext = jspFactory.getPageContext(jspSupportServlet, httpServletRequest,
				httpServletResponse, null, false, 0, false);
		stringPageContext.pushBody(stringWriter);

		// Invoke the JSP tag lifecycle directly (rather than using the tag from a JSP).
		tag.setPageContext(stringPageContext);
		tag.doStartTag();

		if (writeBodyContent() && (customBodyContent != null) && (tag instanceof BaseBodyTagSupport)) {
			BaseBodyTagSupport bodyTag = (BaseBodyTagSupport) tag;
			BodyContent bodyContent = bodyTag.getBodyContent();

			if (bodyContent == null) {

				bodyContent = stringPageContext.pushBody();
				stringPageContext.popBody();
				bodyTag.setBodyContent(bodyContent);
			}

			try {
				bodyContent.write(customBodyContent);
			}
			catch (Exception e) {
				throw new JspException(e);
			}
		}

		tag.doEndTag();

		// If executing within an Ajax request, then write all the scripts contained in the AUI_SCRIPT_DATA attribute
		// directly to the tag output.
		PartialViewContext partialViewContext = facesContext.getPartialViewContext();
		boolean ajaxRequest = partialViewContext.isAjaxRequest();

		if (ajaxRequest) {

			if ((contentType != null) && (contentType.length() > 0) &&
					!contentType.equals(httpServletResponse.getContentType())) {
				httpServletResponse.setContentType(contentType);
			}

			//J-
			// TODO: Possibly need to be concerned about inline scripts written in the <head>...</head> section during Ajax.
			//
			// StringBundler data = HtmlTopTag.getData(httpServletRequest, WebKeys.PAGE_TOP);
			//J+

			Object scriptData = httpServletRequest.getAttribute(WebKeys.AUI_SCRIPT_DATA);

			if (scriptData != null) {

				try {
					ScriptTag.flushScriptData(stringPageContext);
				}
				catch (Exception e) {
					throw new JspException(e);
				}
			}
		}

		jspFactory.releasePageContext(stringPageContext);

		String markup = stringWriter.toString();

		if (ajaxRequest) {

			// Workaround Mojarra #4340: ensure type="text/javascript" is the last attribute so that jsf.js will strip
			// and run the script.
			markup = markup.replaceAll("(<script[^>]+)(type=\"text/javascript\")([^>]+)>", "$1 $3 $2>");

			// Remove all the "<![CDATA[" and "]]>" tokens since they will interfere with the JSF partial-response.
			markup = markup.replace("<![CDATA[", "").replace("]]>", "");
		}

		return markup;
	}
}
