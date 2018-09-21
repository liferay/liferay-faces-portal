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
package com.liferay.faces.bridge.demos.portlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;
import java.util.TreeSet;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.GenericFacesPortlet;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class ShowcasePortlet extends GenericFacesPortlet {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ShowcasePortlet.class);

	@Override
	public void init(PortletConfig portletConfig) throws PortletException {

		super.init(portletConfig);

		InputStream inputStream = null;

		try {

			Properties properties = new Properties();
			ClassLoader classLoader = ShowcasePortlet.class.getClassLoader();
			inputStream = classLoader.getResourceAsStream("input-rich-text.properties");
			properties.load(inputStream);

			PortletContext portletContext = portletConfig.getPortletContext();
			portletContext.setAttribute("validationEditorKeys",
				Collections.unmodifiableSet(new TreeSet(properties.keySet())));
		}
		catch (IOException e) {
			throw new PortletException(e);
		}
		finally {

			if (inputStream != null) {

				try {
					inputStream.close();
				}
				catch (IOException e) {
					logger.error(e);
				}
			}
		}
	}

	@Override
	protected void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException,
		IOException {

		String componentPrefix = renderRequest.getParameter("componentPrefix");
		String componentName = renderRequest.getParameter("componentName");

		if ((componentPrefix != null) && (componentName != null)) {
			String viewId = "/views/component.xhtml";
			renderRequest.setAttribute(Bridge.VIEW_ID, viewId);
		}

		super.doView(renderRequest, renderResponse);
	}
}
