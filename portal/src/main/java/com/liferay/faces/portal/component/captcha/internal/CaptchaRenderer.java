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
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.Tag;

import com.liferay.captcha.taglib.servlet.taglib.CaptchaTag;

import com.liferay.faces.portal.component.captcha.Captcha;
import com.liferay.faces.portal.resource.internal.CaptchaResource;
import com.liferay.faces.portal.resource.internal.LiferayFacesResourceHandler;

import com.liferay.portal.kernel.util.PortalUtil;


/**
 * @author  Juan Gonzalez
 */

//J-
@FacesRenderer(componentFamily = Captcha.COMPONENT_FAMILY, rendererType = Captcha.RENDERER_TYPE)
//J+
public class CaptchaRenderer extends CaptchaRendererBase {

	@Override
	public Tag createTag(FacesContext facesContext, UIComponent uiComponent) {

		String url;
		Captcha captcha = (Captcha) uiComponent;

		if (captcha.getUrl() != null) {
			url = captcha.getUrl();
		}
		else {
			Resource captchaResource = facesContext.getApplication().getResourceHandler().createResource(
					CaptchaResource.RESOURCE_NAME, LiferayFacesResourceHandler.LIBRARY_NAME);
			ExternalContext externalContext = facesContext.getExternalContext();
			url = externalContext.encodeResourceURL(captchaResource.getRequestPath());
		}

		CaptchaTag captchaTag = new CaptchaTag();
		captchaTag.setUrl(url);

		return captchaTag;
	}

	@Override
	public void decode(FacesContext facesContext, UIComponent uiComponent) {

		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();
		String submittedValue;
		Captcha captcha = (Captcha) uiComponent;

		if (getCaptchaType() == CaptchaType.RECAPTCHA) {

			// Note: Since the hidden input field for reCAPTCHA is added dynamically via JavaScript, it is necessary to
			// consult the underlying servlet request in order to obtain the submitted value for the non-namespaced
			// reCAPTCHA hidden field.
			PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
			HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(portletRequest);
			HttpServletRequest originalServletRequest = PortalUtil.getOriginalServletRequest(httpServletRequest);
			submittedValue = originalServletRequest.getParameter(RECAPTCHA_INPUT_NAME);
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

		PartialViewContext partialViewContext = facesContext.getPartialViewContext();

		if (partialViewContext.isAjaxRequest() && (getCaptchaType() == CaptchaType.RECAPTCHA)) {

			ResponseWriter responseWriter = facesContext.getResponseWriter();

			// The liferay-ui:captcha JSP tag will render the reCAPTCHA API script element:

			//J-
			//<script src="https://www.google.com/recaptcha/api.js?hl=en" type="text/javascript"></script>
			//J+

			// When the JSP tag is rendered during a full page GET (RenderRequest), the reCAPTCHA API script element
			// will be placed into the <head>...</head> section of the portal page. At some point during the script's
			// execution, another script element will be dynamically added to the <body>...</body> of the document. For
			// example:

			//J-
			//<script src="https://www.gstatic.com/recaptcha/api2/r20170816175713/recaptcha__en.js"></script>
			//J+

			// When the JSP tag is rendered during an Ajax request, the reCAPTCHA API script element will be
			// included in the <partial-response>...</partial-response>. However, if jsf.js detects that it is already
			// present in the <head>...</head> section, then jsf.js will not execute it. In order to force jsf.js to
			// execute it, include an inline script in the partial-response that deletes all scripts in the DOM that
			// contain "recaptcha" in the src attribute URL:

			//J-
			// var recaptchaScripts = document.querySelectorAll('script[src*=recaptcha]');
			// if (recaptchaScripts) {
			//	  for (var i = 0; i < recaptchaScripts.length; i++) {
			//		 recaptchaScripts[i].parentElement.removeChild(recaptchaScripts[i]);
			//	  }
			// }
			//J+
			responseWriter.startElement("script", uiComponent);
			responseWriter.write("var recaptchaScripts = document.querySelectorAll('script[src*=recaptcha]');");
			responseWriter.write("if (recaptchaScripts) {");
			responseWriter.write("for(var i = 0; i < recaptchaScripts.length; i++) {");
			responseWriter.write("recaptchaScripts[i].parentElement.removeChild(recaptchaScripts[i]);");
			responseWriter.write("}");
			responseWriter.write("}");
			responseWriter.endElement("script");
		}

	}

	@Override
	public String getMarkup(FacesContext facesContext, UIComponent uiComponent, String portalTagOutput)
		throws IOException {

		// Fix the refresh captcha link with the namespaced id when using SimpleCaptcha (default in
		// portal-ext.properties). It works using out-of-the-box Liferay portlets because it internally uses the
		// namespace and this is not populated if used through Liferay Faces.
		ExternalContext externalContext = facesContext.getExternalContext();
		PortletResponse portletResponse = (PortletResponse) externalContext.getResponse();
		String namespace = portletResponse.getNamespace();
		String textToReplace = "id=\"refreshCaptcha\"";
		String replacement = "id=\"".concat(namespace).concat("refreshCaptcha\"");
		String modifiedMarkup = fixMarkup(portalTagOutput);
		modifiedMarkup = modifiedMarkup.replace(textToReplace, replacement);

		// Note: It is only possible to prepend the hidden field with the portlet namespace for simple captcha since the
		// hidden input field for reCAPTCHA is added dynamically via JavaScript. As a result, the decode method must
		// take extra steps to obtain the submitted value in the case of reCAPTCHA.
		if (getCaptchaType() == CaptchaType.SIMPLE) {
			replacement = "name=\"".concat(namespace).concat("captchaText\"");
			textToReplace = "name=\"captchaText\"";
			modifiedMarkup = modifiedMarkup.replace(textToReplace, replacement);
		}

		// Remove <label>Text Verification<span class="required">...</span></label> since it is not possible to
		// customize the "Text Verification" label in the JSP tag. Better to let JSF developers decorate portal:captcha
		// with alloy:field if they want to.
		int labelStartPos = modifiedMarkup.indexOf("<label");

		if (labelStartPos > 0) {
			int labelFinishPos = modifiedMarkup.indexOf("</label>");
			modifiedMarkup = modifiedMarkup.substring(0, labelStartPos) + modifiedMarkup.substring(labelFinishPos + 8);
		}

		return modifiedMarkup;
	}
}
