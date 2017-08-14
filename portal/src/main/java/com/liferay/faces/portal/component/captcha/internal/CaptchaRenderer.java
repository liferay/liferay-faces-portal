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
package com.liferay.faces.portal.component.captcha.internal;

import java.io.IOException;
import java.util.Map;

import javax.faces.application.Resource;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.portlet.PortletResponse;

import com.liferay.captcha.taglib.servlet.taglib.CaptchaTag;

import com.liferay.faces.portal.component.captcha.Captcha;
import com.liferay.faces.portal.render.internal.DelayedPortalTagRenderer;
import com.liferay.faces.portal.resource.internal.CaptchaResource;
import com.liferay.faces.portal.resource.internal.LiferayFacesResourceHandler;
import com.liferay.faces.util.render.BufferedScriptResponseWriter;

import com.liferay.portal.kernel.captcha.CaptchaUtil;


/**
 * @author  Juan Gonzalez
 */

//J-
@FacesRenderer(componentFamily = Captcha.COMPONENT_FAMILY, rendererType = Captcha.RENDERER_TYPE)
//J+
public class CaptchaRenderer extends DelayedPortalTagRenderer<Captcha, CaptchaTag> {

	@Override
	public void decode(FacesContext facesContext, UIComponent uiComponent) {

		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();
		String submittedValue;
		Captcha captcha = cast(uiComponent);
		String captchaImpl = CaptchaUtil.getCaptcha().getClass().getName();

		if (captchaImpl.contains("ReCaptcha")) {
			submittedValue = requestParameterMap.get("g-recaptcha-response");
		}
		else {
			submittedValue = requestParameterMap.get("captchaText");
		}

		if (submittedValue == null) {
			submittedValue = "";
		}

		captcha.setSubmittedValue(submittedValue);
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		super.encodeEnd(facesContext, uiComponent);

		String captchaImpl = CaptchaUtil.getCaptcha().getClass().getName();

		PartialViewContext partialViewContext = facesContext.getPartialViewContext();

		if (partialViewContext.isAjaxRequest() && captchaImpl.contains("ReCaptcha")) {

			ResponseWriter responseWriter = facesContext.getResponseWriter();

			BufferedScriptResponseWriter bufferedScriptResponseWriter = new BufferedScriptResponseWriter();
			facesContext.setResponseWriter(bufferedScriptResponseWriter);

			// If recaptcha scripts are present in the head section during an ajax postback, jsf.js will not run them
			// again for the update. These recaptcha scripts need to be run each time they are returned by the component
			// for an update, or else the recaptcha will not be rendered.  Also, one recaptcha script adds another one
			// in such a way that it causes a client side memory leak with each ajax postback, so let's just remove them
			// all after each ajax postback.

			//J-
			// var js = document.querySelectorAll('script[src*=recaptcha]');
			// if (js) {
			//	  for (var i = 0; i < js.length; i++) {
			//		 js[i].parentElement.removeChild(js[i]);
			//	  }
			// }
			//J+
			responseWriter.write("<script>");
			responseWriter.write("var js = document.querySelectorAll('script[src*=recaptcha]');");
			responseWriter.write("if (js) {");
			responseWriter.write("for(var i = 0; i < js.length; i++) {");
			responseWriter.write("js[i].parentElement.removeChild(js[i]);");
			responseWriter.write("}");
			responseWriter.write("}");
			responseWriter.write("</script>");

			facesContext.setResponseWriter(responseWriter);
		}

	}

	@Override
	public CaptchaTag newTag() {
		return new CaptchaTag();
	}

	@Override
	protected Captcha cast(UIComponent uiComponent) {
		return (Captcha) uiComponent;
	}

	@Override
	protected void copyFrameworkAttributes(FacesContext facesContext, Captcha captcha, CaptchaTag captchaTag) {

		String url;

		if (captcha.getUrl() != null) {
			url = captcha.getUrl();
		}
		else {
			Resource captchaResource = facesContext.getApplication().getResourceHandler().createResource(
					CaptchaResource.RESOURCE_NAME, LiferayFacesResourceHandler.LIBRARY_NAME);
			ExternalContext externalContext = facesContext.getExternalContext();
			url = externalContext.encodeResourceURL(captchaResource.getRequestPath());
		}

		captchaTag.setUrl(url);
	}

	@Override
	protected void copyNonFrameworkAttributes(FacesContext facesContext, Captcha captcha, CaptchaTag captchaTag) {
		// no-op
	}

	@Override
	protected StringBuilder getMarkup(UIComponent uiComponent, StringBuilder markup) throws Exception {

		// Fix the refresh captcha link with the namespaced id when using SimpleCaptcha (default in
		// portal-ext.properties). It works using out-of-the-box Liferay portlets because it internally uses the
		// namespace and this is not populated if used through Liferay Faces.
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		PortletResponse portletResponse = (PortletResponse) externalContext.getResponse();
		String namespace = portletResponse.getNamespace();
		String textToReplace = "id=\"refreshCaptcha\"";
		String replacement = "id=\"".concat(namespace).concat("refreshCaptcha\"");
		String modifiedMarkup = markup.toString();
		modifiedMarkup = modifiedMarkup.replace(textToReplace, replacement);

		String captchaImpl = CaptchaUtil.getCaptcha().getClass().getName();

		if (captchaImpl.contains("ReCaptcha")) {
			replacement = "name=\"".concat(namespace).concat("g-recaptcha-response\"");
			textToReplace = "name=\"g-recaptcha-response\"";
		}
		else {
			replacement = "name=\"".concat(namespace).concat("captchaText\"");
			textToReplace = "name=\"captchaText\"";
		}

		modifiedMarkup = modifiedMarkup.replace(textToReplace, replacement);

		// Remove <label>Text Verification<span class="required">...</span></label> since it is not possible to
		// customize the "Text Verification" label in the JSP tag. Better to let JSF developers decorate portal:captcha
		// with alloy:field if they want to.
		int labelStartPos = modifiedMarkup.indexOf("<label");

		if (labelStartPos > 0) {
			int labelFinishPos = modifiedMarkup.indexOf("</label>");
			modifiedMarkup = modifiedMarkup.substring(0, labelStartPos) + modifiedMarkup.substring(labelFinishPos + 8);
		}

		return new StringBuilder(modifiedMarkup);
	}
}
