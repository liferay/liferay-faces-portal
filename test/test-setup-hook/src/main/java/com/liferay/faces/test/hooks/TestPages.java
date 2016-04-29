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
package com.liferay.faces.test.hooks;

import java.util.ArrayList;
import java.util.List;


/**
 * The purpose of this class is to isolate source code differences between different versions of Liferay Portal.
 *
 * @author  Neil Griffin
 */
public class TestPages {

	public static final List<PortalPage> BRIDGE_DEMO_PAGES;
	public static final List<PortalPage> BRIDGE_ISSUE_PAGES;
	public static final List<PortalPage> LSV_ISSUE_PAGES;
	public static final List<PortalPage> PORTAL_DEMO_PAGES;
	public static final List<PortalPage> PORTAL_ISSUE_PAGES;
	public static final List<PortalPage> GUEST_PAGES;

	static {
		BRIDGE_DEMO_PAGES = new ArrayList<PortalPage>();
		BRIDGE_DEMO_PAGES.add(new PortalPage("jsf-applicant", new Portlet("1", "jsf-applicant-portlet")));
		BRIDGE_DEMO_PAGES.add(new PortalPage("jsf-cdi-applicant", new Portlet("1", "jsf-cdi-applicant-portlet")));
		BRIDGE_DEMO_PAGES.add(new PortalPage("jsf-flows", new Portlet("1", "jsf-flows-portlet")));
		BRIDGE_DEMO_PAGES.add(new PortalPage("jsf-html5-applicant", new Portlet("1", "jsf-html5-applicant-portlet")));
		BRIDGE_DEMO_PAGES.add(new PortalPage("jsf-spring-applicant", new Portlet("1", "jsf-spring-applicant-portlet")));
		BRIDGE_DEMO_PAGES.add(new PortalPage("jsf-jsp-applicant", new Portlet("1", "jsf-jsp-applicant-portlet")));
		BRIDGE_DEMO_PAGES.add(new PortalPage("jsf-pdf", new Portlet("1", "jsf-export-pdf-portlet", false)));
		BRIDGE_DEMO_PAGES.add(new PortalPage("jsf-events",
				new Portlet("customers", "jsf-ipc-events-customers-portlet", false),
				new Portlet("bookings", "jsf-ipc-events-bookings-portlet", false)));
		BRIDGE_DEMO_PAGES.add(new PortalPage("jsf-prp",
				new Portlet("customersPortlet", "jsf-ipc-pub-render-params-portlet", false),
				new Portlet("bookingsPortlet", "jsf-ipc-pub-render-params-portlet", false)));
		BRIDGE_DEMO_PAGES.add(new PortalPage("icefaces-applicant", new Portlet("1", "icefaces-applicant-portlet")));
		BRIDGE_DEMO_PAGES.add(new PortalPage("alloy-applicant", new Portlet("1", "alloy-applicant-portlet")));
		BRIDGE_DEMO_PAGES.add(new PortalPage("primefaces-applicant", new Portlet("1", "primefaces-applicant-portlet")));
		BRIDGE_DEMO_PAGES.add(new PortalPage("richfaces-applicant", new Portlet("1", "richfaces-applicant-portlet")));
	}

	static {
		BRIDGE_ISSUE_PAGES = new ArrayList<PortalPage>();
		BRIDGE_ISSUE_PAGES.add(new PortalPage("FACES-224", new Portlet("1", "FACES-224-portlet", false)));
		BRIDGE_ISSUE_PAGES.add(new PortalPage("FACES-1470", new Portlet("1", "FACES-1470-portlet", false)));
		BRIDGE_ISSUE_PAGES.add(new PortalPage("FACES-1478", new Portlet("1", "FACES-1478-portlet", false)));
		BRIDGE_ISSUE_PAGES.add(new PortalPage("FACES-1618", new Portlet("1", "FACES-1618-portlet", false)));
		BRIDGE_ISSUE_PAGES.add(new PortalPage("FACES-1635", new Portlet("1", "jsf-applicant-portlet"),
				new Portlet("1", "primefaces-applicant-portlet")));
		BRIDGE_ISSUE_PAGES.add(new PortalPage("FACES-1638", new Portlet("1", "FACES-1638-portlet")));
	}

	static {
		LSV_ISSUE_PAGES = new ArrayList<PortalPage>();
		LSV_ISSUE_PAGES.add(new PortalPage("LSV-5", new Portlet("1", "lsv5portlet", false)));
		LSV_ISSUE_PAGES.add(new PortalPage("LSV-71-Auto-Dispatch", new Portlet("1", "lsv71portlet", false)));
		LSV_ISSUE_PAGES.add(new PortalPage("LSV-71-Non-Dispatch", new Portlet("2", "lsv71portlet", false)));
		LSV_ISSUE_PAGES.add(new PortalPage("LSV-71-Resource-Handler", new Portlet("3", "lsv71portlet", false)));
		LSV_ISSUE_PAGES.add(new PortalPage("LSV-158-Auto-Dispatch", new Portlet("1", "lsv158portlet", false)));
		LSV_ISSUE_PAGES.add(new PortalPage("LSV-158-Non-Dispatch", new Portlet("2", "lsv158portlet", false)));
	}

	static {
		PORTAL_DEMO_PAGES = new ArrayList<PortalPage>();
	}

	static {
		PORTAL_ISSUE_PAGES = new ArrayList<PortalPage>();
		PORTAL_ISSUE_PAGES.add(new PortalPage("FACES-257", new Portlet("1", "FACES-257-portlet")));
		PORTAL_ISSUE_PAGES.add(new PortalPage("FACES-1427", new Portlet("1", "FACES-1427-portlet")));
		PORTAL_ISSUE_PAGES.add(new PortalPage("FACES-1439", new Portlet("1", "FACES-1439-portlet")));
	}

	static {
		GUEST_PAGES = new ArrayList<PortalPage>();
		GUEST_PAGES.add(new PortalPage("jsf-sign-in", new Portlet("1", "jsf-login-portlet")));

		PortalPage portalPage = new PortalPage("jsf-showcase", new Portlet("1", "jsf-showcase-portlet", false));
		portalPage.setLayoutTemplateId("1_column");
		GUEST_PAGES.add(portalPage);
		portalPage = new PortalPage("alloy-showcase", new Portlet("1", "alloy-showcase-portlet", false));
		portalPage.setLayoutTemplateId("1_column");
		GUEST_PAGES.add(portalPage);
		portalPage = new PortalPage("metal-showcase", new Portlet("1", "metal-showcase-portlet", false));
		portalPage.setLayoutTemplateId("1_column");
		GUEST_PAGES.add(portalPage);
		portalPage = new PortalPage("portal-showcase", new Portlet("1", "portal-showcase-portlet", false));
		portalPage.setLayoutTemplateId("1_column");
		GUEST_PAGES.add(portalPage);
	}
}
