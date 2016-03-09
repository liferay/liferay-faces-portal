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
		BRIDGE_DEMO_PAGES.add(new PortalPage("jsf-applicant", "1_WAR_jsfapplicantportlet_INSTANCE_"));
		BRIDGE_DEMO_PAGES.add(new PortalPage("jsf-jsp-applicant", "1_WAR_jsfjspapplicantportlet_INSTANCE_"));
		BRIDGE_DEMO_PAGES.add(new PortalPage("jsf-events",
				new String[] {
					"customers_WAR_jsfipceventscustomersportlet", "bookings_WAR_jsfipceventsbookingsportlet"
				}));
		BRIDGE_DEMO_PAGES.add(new PortalPage("jsf-prp",
				new String[] {
					"customersPortlet_WAR_jsfipcpubrenderparamsportlet",
					"bookingsPortlet_WAR_jsfipcpubrenderparamsportlet"
				}));
		BRIDGE_DEMO_PAGES.add(new PortalPage("icefaces-applicant", "1_WAR_icefacesapplicantportlet_INSTANCE_"));
		BRIDGE_DEMO_PAGES.add(new PortalPage("icefaces-ipc",
				new String[] { "1_WAR_icefacesipcajaxpushportlet", "2_WAR_icefacesipcajaxpushportlet" }));
	}

	static {
		BRIDGE_ISSUE_PAGES = new ArrayList<PortalPage>();
		BRIDGE_ISSUE_PAGES.add(new PortalPage("FACES-1478", "1_WAR_FACES1478portlet"));
	}

	static {
		LSV_ISSUE_PAGES = new ArrayList<PortalPage>();
		LSV_ISSUE_PAGES.add(new PortalPage("LSV-5", "1_WAR_lsv5portlet"));
		LSV_ISSUE_PAGES.add(new PortalPage("LSV-71-Auto-Dispatch", "1_WAR_lsv71portlet"));
		LSV_ISSUE_PAGES.add(new PortalPage("LSV-71-Non-Dispatch", "2_WAR_lsv71portlet"));
		LSV_ISSUE_PAGES.add(new PortalPage("LSV-158-Auto-Dispatch", "1_WAR_lsv158portlet"));
		LSV_ISSUE_PAGES.add(new PortalPage("LSV-158-Non-Dispatch", "2_WAR_lsv158portlet"));
	}

	static {
		PORTAL_DEMO_PAGES = new ArrayList<PortalPage>();
	}

	static {
		PORTAL_ISSUE_PAGES = new ArrayList<PortalPage>();
	}

	static {
		GUEST_PAGES = new ArrayList<PortalPage>();
		GUEST_PAGES.add(new PortalPage("SHOWCASE", "1_WAR_showcaseportlet", "2_columns_iii"));
	}
}
