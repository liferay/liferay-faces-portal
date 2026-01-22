/**
 * Copyright (c) 2000-2022 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.portal.resource.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import jakarta.faces.application.Resource;
import jakarta.faces.application.ResourceHandler;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.portlet.PortletRequest;
import jakarta.portlet.PortletResponse;
import jakarta.portlet.PortletSession;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import jakarta.servlet.WriteListener;

import com.liferay.captcha.util.CaptchaUtil;

import com.liferay.portal.kernel.util.PortalUtil;


/**
 * @author  Neil Griffin
 * @author  Joe Ssemwogerere
 */
public class CaptchaResource extends Resource {

	// Public Constants
	public static final String CONTENT_TYPE = "image/png";
	public static final String RESOURCE_NAME = "captcha";

	// Private Constants
	private static final String CAPTCHA_TEXT = "CAPTCHA_TEXT";

	// Private Data Members
	private String requestPath;

	public CaptchaResource() {
		setLibraryName(LiferayFacesResourceHandler.LIBRARY_NAME);
		setResourceName(RESOURCE_NAME);
		setContentType(CONTENT_TYPE);
	}

	@Override
	public InputStream getInputStream() {
		ByteArrayInputStream byteArrayInputStream = null;

		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
			PortletResponse portletResponse = (PortletResponse) externalContext.getResponse();
			PortletSession portletSession = (PortletSession) externalContext.getSession(true);
			HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(portletRequest);
			HttpServletResponse httpServletResponse = PortalUtil.getHttpServletResponse(portletResponse);
			CaptchaHttpServletResponse captchaHttpServletResponse = new CaptchaHttpServletResponse(httpServletResponse);

			CaptchaUtil.serveImage(new PortletIdHttpServletRequest(httpServletRequest,
					PortalUtil.getPortletId(portletRequest)), captchaHttpServletResponse);

			String captchaText = (String) httpServletRequest.getSession().getAttribute(CAPTCHA_TEXT);
			portletSession.setAttribute(CAPTCHA_TEXT, captchaText);

			CaptchaServletOutputStream captchaServletOutputStream = (CaptchaServletOutputStream)
				captchaHttpServletResponse.getOutputStream();
			byteArrayInputStream = new ByteArrayInputStream(captchaServletOutputStream.toByteArray());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return byteArrayInputStream;
	}

	@Override
	public String getRequestPath() {

		if (requestPath == null) {
			StringBuilder buf = new StringBuilder();
			buf.append(ResourceHandler.RESOURCE_IDENTIFIER);
			buf.append("/");
			buf.append(getResourceName());
			buf.append("?ln=");
			buf.append(getLibraryName());
			requestPath = buf.toString();
			requestPath = FacesContext.getCurrentInstance().getExternalContext().encodeResourceURL(requestPath);
		}

		return requestPath;
	}

	@Override
	public Map<String, String> getResponseHeaders() {
		return null;
	}

	@Override
	public URL getURL() {
		return null;
	}

	@Override
	public boolean userAgentNeedsUpdate(FacesContext context) {

		// Since the captcha image changes for every request, always return true so that the browser does not attempt
		// to cache it.
		return true;
	}

	private static final class CaptchaHttpServletResponse extends HttpServletResponseWrapper {

		ServletOutputStream outputStream;

		public CaptchaHttpServletResponse(HttpServletResponse response) {
			super(response);
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {

			if (outputStream == null) {
				outputStream = new CaptchaServletOutputStream();
			}

			return outputStream;
		}

	}

	private static final class CaptchaServletOutputStream extends ServletOutputStream {

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		public byte[] toByteArray() {
			return byteArrayOutputStream.toByteArray();
		}

		@Override
		public void write(int b) throws IOException {
			byteArrayOutputStream.write(b);
		}

        @Override
        public void setWriteListener(WriteListener writeListener) {
        }

        @Override
        public boolean isReady() {
            return true;
        }

	}

}
