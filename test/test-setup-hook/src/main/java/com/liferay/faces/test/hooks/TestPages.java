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
		BRIDGE_DEMO_PAGES.add(new PortalPage("jsf-applicant",
				new Portlet("1", "com.liferay.faces.demo.jsf.applicant.portlet")));
		BRIDGE_DEMO_PAGES.add(new PortalPage("jsf-jsp-applicant",
				new Portlet("1", "com.liferay.faces.demo.jsf.jsp.applicant.portlet")));
		BRIDGE_DEMO_PAGES.add(new PortalPage("jsf-events",
				new Portlet("customers", "com.liferay.faces.demo.jsf.ipc.events.customers.portlet", false),
				new Portlet("bookings", "com.liferay.faces.demo.jsf.ipc.events.bookings.portlet", false)));
		BRIDGE_DEMO_PAGES.add(new PortalPage("jsf-prp",
				new Portlet("customersPortlet", "com.liferay.faces.demo.jsf.ipc.pub.render.params.portlet", false),
				new Portlet("bookingsPortlet", "com.liferay.faces.demo.jsf.ipc.pub.render.params.portlet", false)));
		BRIDGE_DEMO_PAGES.add(new PortalPage("icefaces-applicant",
				new Portlet("1", "com.liferay.faces.demo.icefaces.applicant.portlet")));
		BRIDGE_DEMO_PAGES.add(new PortalPage("icefaces-ipc",
				new Portlet("1", "com.liferay.faces.demo.icefaces.ipc.ajax.push.portlet", false),
				new Portlet("2", "com.liferay.faces.demo.icefaces.ipc.ajax.push.portlet", false)));
	}

	static {
		BRIDGE_ISSUE_PAGES = new ArrayList<PortalPage>();
		BRIDGE_ISSUE_PAGES.add(new PortalPage("FACES-1478",
				new Portlet("1", "com.liferay.faces.issue.1478.portlet", false)));
	}

	static {
		LSV_ISSUE_PAGES = new ArrayList<PortalPage>();
		LSV_ISSUE_PAGES.add(new PortalPage("LSV-5", new Portlet("1", "com.liferay.faces.lsv.5.portlet", false)));
		LSV_ISSUE_PAGES.add(new PortalPage("LSV-71-Auto-Dispatch",
				new Portlet("1", "com.liferay.faces.lsv.71.portlet", false)));
		LSV_ISSUE_PAGES.add(new PortalPage("LSV-71-Non-Dispatch",
				new Portlet("2", "com.liferay.faces.lsv.71.portlet", false)));
		LSV_ISSUE_PAGES.add(new PortalPage("LSV-158-Auto-Dispatch",
				new Portlet("1", "com.liferay.faces.lsv.158.portlet", false)));
		LSV_ISSUE_PAGES.add(new PortalPage("LSV-158-Non-Dispatch",
				new Portlet("2", "com.liferay.faces.lsv.158.portlet", false)));
	}

	static {
		PORTAL_DEMO_PAGES = new ArrayList<PortalPage>();
	}

	static {
		PORTAL_ISSUE_PAGES = new ArrayList<PortalPage>();
	}

	static {
		GUEST_PAGES = new ArrayList<PortalPage>();
	}
}
