/**
 * Copyright (c) 2000-2016 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.demos.portlet;

import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.faces.GenericFacesPortlet;
import javax.servlet.Servlet;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
@Component(
	immediate = true, property = {
			"com.liferay.portlet.display-category=category.sample", "com.liferay.portlet.instanceable=true",
			"com.liferay.portlet.ajaxable=false", "javax.portlet.name=jsf_ds_applicant",
			"javax.portlet.display-name=jsf-ds-applicant",
			"javax.portlet.init-param.javax.portlet.faces.defaultViewId.view=/WEB-INF/views/portletViewMode.xhtml",
			"javax.portlet.init-param.javax.portlet.faces.defaultViewId.edit=/WEB-INF/views/portletEditMode.xhtml",
			"javax.portlet.init-param.javax.portlet.faces.defaultViewId.help=/WEB-INF/views/portletHelpMode.xhtml",
			"javax.portlet.preferences=classpath:/META-INF/preferences.xml",
			"javax.portlet.security-role-ref=power-user,user", "javax.portlet.portlet-mode=text/html;view,edit,help"
		}, service = Portlet.class
)
public class ApplicantFacesPortlet extends GenericFacesPortlet {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ApplicantFacesPortlet.class);

	@Activate
	public void activate() {
		logger.debug("activate() called");
	}

	@Override
	public void destroy() {
		logger.debug("destroy() called");
		super.destroy();
	}

	@Override
	public void init(PortletConfig portletConfig) throws PortletException {
		logger.debug("init(PortletConfig) called");
		super.init(portletConfig);
	}

	// This method (along with the corresponding init-param in web.xml) is necessary in order to ensure that the
	// context listeners have been called before the init(PortletConfig) method is called.
	@Reference(target = "(servlet.init.portlet-class=com.liferay.faces.demos.portlet.ApplicantFacesPortlet)")
	protected void setServlet(Servlet servlet) {
		logger.debug("context listeners initialized");
	}
}
