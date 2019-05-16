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
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.faces.render.Renderer;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.Tag;

import com.liferay.faces.portal.jsp.internal.JspWriterStringImpl;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.servlet.JSPSupportServlet;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import com.liferay.taglib.BaseBodyTagSupport;
import com.liferay.taglib.aui.ScriptTag;


/**
 * This abstract class serves as a generic JSF {@link Renderer} that invokes the JSP tag lifecycle of a Liferay Portal
 * JSP tag and encodes the output. This class will encode children as body content of the JSP tag unless {@link
 * #getChildrenInsertionMarker()} is overridden and returns a non-null value. See {@link #getChildrenInsertionMarker()},
 * for more details.
 *
 * @author  Neil Griffin
 * @author  Juan Gonzalez
 * @author  Kyle Stiemann
 */
public abstract class PortalTagRenderer extends Renderer {

	// Private Constants
	private static final String CORRESPONDING_JSP_TAG_KEY = PortalTagRenderer.class.getName() + "correspondingJspTag";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PortalTagRenderer.class);

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Create an instance of the JSP tag that corresponds to the JSF component.
		Tag tag = createTag(facesContext, uiComponent);
		UIComponent parentComponent = uiComponent.getParent();
		Map<String, Object> parentComponentAttributes = parentComponent.getAttributes();
		tag.setParent((Tag) parentComponentAttributes.get(CORRESPONDING_JSP_TAG_KEY));
		uiComponent.getAttributes().put(CORRESPONDING_JSP_TAG_KEY, tag);
	}

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		encodeChildren(facesContext, uiComponent, uiComponent.getChildren().iterator());
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		uiComponent.getAttributes().remove(CORRESPONDING_JSP_TAG_KEY);
	}

	/**
	 * @return  the marker string that indicates where children markup should be inserted. Children markup will be
	 *          inserted before this marker. If this method returns null, any child markup will be inserted as the body
	 *          content of the JSP Tag. The default return value is null.
	 */
	public String getChildrenInsertionMarker() {
		return null;
	}

	@Override
	public final boolean getRendersChildren() {
		return true;
	}

	/**
	 * @param   facesContext
	 * @param   uiComponent
	 *
	 * @return  the created JSP tag with all appropriate attributes set.
	 */
	protected abstract Tag createTag(FacesContext facesContext, UIComponent uiComponent);

	protected final void encodeChildren(FacesContext facesContext, UIComponent uiComponent,
		Iterator<UIComponent> childrenIterator) throws IOException {

		String bufferedChildrenMarkup = null;

		if (childrenIterator.hasNext()) {

			// Encode each of the children using a writer that can capture the child markup as a string.
			ResponseWriter originalResponseWriter = facesContext.getResponseWriter();
			RenderKit renderKit = facesContext.getRenderKit();
			StringWriter bufferedChildrenMarkupWriter = new StringWriter();
			ResponseWriter stringResponseWriter = renderKit.createResponseWriter(bufferedChildrenMarkupWriter, null,
					"UTF-8");
			facesContext.setResponseWriter(stringResponseWriter);

			while (childrenIterator.hasNext()) {
				childrenIterator.next().encodeAll(facesContext);
			}

			facesContext.setResponseWriter(originalResponseWriter);
			bufferedChildrenMarkup = bufferedChildrenMarkupWriter.toString();
		}

		String portalTagOutput = getPortalTagOutput(facesContext, uiComponent, bufferedChildrenMarkup);
		facesContext.getResponseWriter().write(portalTagOutput);
	}

	protected String getMarkup(FacesContext facesContext, UIComponent uiComponent, String portalTagOutput)
		throws IOException {
		return portalTagOutput;
	}

	protected final String getPortalTagOutput(FacesContext facesContext, UIComponent uiComponent, String childrenMarkup)
		throws IOException {

		Map<String, Object> uiComponentAttributes = uiComponent.getAttributes();
		Tag tag = (Tag) uiComponentAttributes.get(CORRESPONDING_JSP_TAG_KEY);

		// Setup the Facelet -> JSP tag adapter.
		ExternalContext externalContext = facesContext.getExternalContext();
		PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();

		portletRequest.setAttribute("aui:form:portletNamespace", "");
		portletRequest.setAttribute("aui:form:useNamespace", "false");

		HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(portletRequest);
		PortletResponse portletResponse = (PortletResponse) externalContext.getResponse();
		HttpServletResponse httpServletResponse = PortalUtil.getHttpServletResponse(portletResponse);
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

		try {
			tag.doStartTag();
		}
		catch (JspException e) {
			throw new IOException(e);
		}

		String childrenInsertionMarker = getChildrenInsertionMarker();

		if ((childrenInsertionMarker == null) && (childrenMarkup != null) && (!"".equals(childrenMarkup.trim())) &&
				(tag instanceof BaseBodyTagSupport)) {
			BaseBodyTagSupport bodyTag = (BaseBodyTagSupport) tag;
			BodyContent bodyContent = bodyTag.getBodyContent();

			if (bodyContent == null) {

				bodyContent = stringPageContext.pushBody();
				stringPageContext.popBody();
				bodyTag.setBodyContent(bodyContent);
			}

			bodyContent.write(childrenMarkup);
		}

		try {
			tag.doEndTag();
		}
		catch (JspException e) {
			throw new IOException(e);
		}

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
					throw new IOException(e);
				}
			}
		}

		jspFactory.releasePageContext(stringPageContext);

		String portalTagOutput = stringWriter.toString();

		if ((childrenInsertionMarker != null) && (childrenMarkup != null)) {

			int pos = portalTagOutput.indexOf(childrenInsertionMarker);

			if (pos > 0) {

				String preChildMarkup = portalTagOutput.substring(0, pos).trim();
				String postChildMarkup = portalTagOutput.substring(pos).trim();
				portalTagOutput = preChildMarkup.concat(childrenMarkup).concat(postChildMarkup);
			}
		}

		portalTagOutput = getMarkup(facesContext, uiComponent, portalTagOutput);

		if (ajaxRequest) {
			portalTagOutput = ScriptUtil.prepareScriptsForMojarraPartialResponse(portalTagOutput);
		}

		return portalTagOutput;
	}
}
