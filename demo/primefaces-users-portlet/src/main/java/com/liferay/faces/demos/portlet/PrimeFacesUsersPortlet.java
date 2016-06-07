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
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.faces.GenericFacesPortlet;
import javax.servlet.Servlet;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.service.UserLocalService;


/**
 * @author  Neil Griffin
 */
@Component(
	immediate = true, property = {
			"com.liferay.portlet.display-category=category.hidden", "com.liferay.portlet.instanceable=false",
			"com.liferay.portlet.add-default-resource=true", "com.liferay.portlet.ajaxable=false",
			"com.liferay.portlet.control-panel-entry-category=users",
			"com.liferay.portlet.control-panel-entry-weight=1.0", "com.liferay.portlet.remoteable=false",
			"com.liferay.portlet.render-weight=1", "com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.name=primefaces_users_portlet", "javax.portlet.display-name=primefaces-users-portlet",
			"javax.portlet.init-param.javax.portlet.faces.defaultViewId.view=/WEB-INF/views/portletViewMode.xhtml",
			"javax.portlet.preferences=classpath:/META-INF/preferences.xml",
			"javax.portlet.security-role-ref=administrator", "javax.portlet.portlet-mode=text/html;view"
		}, service = Portlet.class
)
public class PrimeFacesUsersPortlet extends GenericFacesPortlet {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PrimeFacesUsersPortlet.class);

	// Workaround for LPS-66225:
	// This reference (along with the corresponding init-param in web.xml) is necessary in order to ensure that the
	// context listeners have been called before the init(PortletConfig) method is called.
	@Reference(target = "(servlet.init.portlet-class=com.liferay.faces.demos.portlet.PrimeFacesUsersPortlet)")
	private Servlet servlet;

	// Private Data Members
	@Reference
	private UserLocalService userLocalService;

	@Activate
	public void activate() {
		logger.debug("activate() called");
	}

	@Deactivate
	public void deactivate() {
		logger.debug("deactivate() called");
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

		// Save the user service as a portlet context (application scoped) attribute so that it can be easily accessible
		// from a managed bean during the JSF lifecycle.
		PortletContext portletContext = getPortletContext();
		portletContext.setAttribute("userLocalService", userLocalService);
	}
}