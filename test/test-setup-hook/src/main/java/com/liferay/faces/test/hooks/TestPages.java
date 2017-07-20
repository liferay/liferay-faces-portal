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

	public static final List<PortalPage> ARCHETYPE_PAGES;
	public static final List<PortalPage> BRIDGE_DEMO_PAGES;
	public static final List<PortalPage> BRIDGE_ISSUE_PAGES;
	public static final List<PortalPage> LSV_ISSUE_PAGES;
	public static final List<PortalPage> PORTAL_DEMO_PAGES;
	public static final List<PortalPage> PORTAL_ISSUE_PAGES;
	public static final List<PortalPage> GUEST_PAGES;

	static {
		ARCHETYPE_PAGES = new ArrayList<PortalPage>();
		ARCHETYPE_PAGES.add(new PortalPage("my-alloy",
				new Portlet("commycompanymyalloyportlet", "com.mycompany.my.alloy.portlet", false)));
		ARCHETYPE_PAGES.add(new PortalPage("my-icefaces",
				new Portlet("commycompanymyicefacesportlet", "com.mycompany.my.icefaces.portlet", false)));
		ARCHETYPE_PAGES.add(new PortalPage("my-jsf",
				new Portlet("commycompanymyjsfportlet", "com.mycompany.my.jsf.portlet", false)));
		ARCHETYPE_PAGES.add(new PortalPage("my-primefaces",
				new Portlet("commycompanymyprimefacesportlet", "com.mycompany.my.primefaces.portlet", false)));
		ARCHETYPE_PAGES.add(new PortalPage("my-richfaces",
				new Portlet("commycompanymyrichfacesportlet", "com.mycompany.my.richfaces.portlet", false)));
	}

	static {
		BRIDGE_DEMO_PAGES = new ArrayList<PortalPage>();
		BRIDGE_DEMO_PAGES.add(new PortalPage("jsf-applicant",
				new Portlet("1", "com.liferay.faces.demo.jsf.applicant.portlet")));
		BRIDGE_DEMO_PAGES.add(new PortalPage("butterfaces-applicant",
				new Portlet("1", "com.liferay.faces.demo.butterfaces.applicant.portlet")));
		BRIDGE_DEMO_PAGES.add(new PortalPage("jsf-cdi-applicant",
				new Portlet("1", "com.liferay.faces.demo.jsf.cdi.applicant.portlet")));
		BRIDGE_DEMO_PAGES.add(new PortalPage("jsf-ds-applicant",
				new Portlet("jsf_ds_applicant", "com.liferay.faces.demo.jsf.ds.applicant.portlet",
					Portlet.ArtifactType.WAB)));
		BRIDGE_DEMO_PAGES.add(new PortalPage("jsf-flows",
				new Portlet("1", "com.liferay.faces.demo.jsf.flows.portlet")));
		BRIDGE_DEMO_PAGES.add(new PortalPage("jsf-html5-applicant",
				new Portlet("1", "com.liferay.faces.demo.jsf.html5.applicant.portlet")));
		BRIDGE_DEMO_PAGES.add(new PortalPage("jsf-spring-applicant",
				new Portlet("1", "com.liferay.faces.demo.jsf.spring.applicant.portlet")));
		BRIDGE_DEMO_PAGES.add(new PortalPage("jsf-jsp-applicant",
				new Portlet("1", "com.liferay.faces.demo.jsf.jsp.applicant.portlet")));
		BRIDGE_DEMO_PAGES.add(new PortalPage("jsf-pdf",
				new Portlet("1", "com.liferay.faces.demo.jsf.export.pdf.portlet", false)));
		BRIDGE_DEMO_PAGES.add(new PortalPage("jsf-events",
				new Portlet("customers", "com.liferay.faces.demo.jsf.ipc.events.customers.portlet", false),
				new Portlet("bookings", "com.liferay.faces.demo.jsf.ipc.events.bookings.portlet", false)));
		BRIDGE_DEMO_PAGES.add(new PortalPage("jsf-prp",
				new Portlet("customersPortlet", "com.liferay.faces.demo.jsf.ipc.pub.render.params.portlet", false),
				new Portlet("bookingsPortlet", "com.liferay.faces.demo.jsf.ipc.pub.render.params.portlet", false)));
		BRIDGE_DEMO_PAGES.add(new PortalPage("icefaces-applicant",
				new Portlet("1", "com.liferay.faces.demo.icefaces.applicant.portlet")));
		BRIDGE_DEMO_PAGES.add(new PortalPage("alloy-applicant",
				new Portlet("1", "com.liferay.faces.demo.alloy.applicant.portlet")));
		BRIDGE_DEMO_PAGES.add(new PortalPage("primefaces-applicant",
				new Portlet("1", "com.liferay.faces.demo.primefaces.applicant.portlet")));
		BRIDGE_DEMO_PAGES.add(new PortalPage("primepush-cdi-counter",
				new Portlet("1", "com.liferay.faces.demo.primepush.cdi.counter.portlet")));
		BRIDGE_DEMO_PAGES.add(new PortalPage("richfaces-applicant",
				new Portlet("1", "com.liferay.faces.demo.richfaces.applicant.portlet")));
	}

	static {
		BRIDGE_ISSUE_PAGES = new ArrayList<PortalPage>();
		BRIDGE_ISSUE_PAGES.add(new PortalPage("FACES-224",
				new Portlet("1", "com.liferay.faces.issue.224.portlet", false)));
		BRIDGE_ISSUE_PAGES.add(new PortalPage("FACES-1470",
				new Portlet("1", "com.liferay.faces.issue.1470.portlet", false)));
		BRIDGE_ISSUE_PAGES.add(new PortalPage("FACES-1478",
				new Portlet("1", "com.liferay.faces.issue.1478.portlet", false)));
		BRIDGE_ISSUE_PAGES.add(new PortalPage("FACES-1618",
				new Portlet("1", "com.liferay.faces.issue.1618.portlet", false)));
		BRIDGE_ISSUE_PAGES.add(new PortalPage("FACES-1635",
				new Portlet("1", "com.liferay.faces.demo.jsf.applicant.portlet"),
				new Portlet("1", "com.liferay.faces.demo.primefaces.applicant.portlet")));
		BRIDGE_ISSUE_PAGES.add(new PortalPage("FACES-1638", new Portlet("1", "com.liferay.faces.issue.1638.portlet")));
		BRIDGE_ISSUE_PAGES.add(new PortalPage("FACES-2921",
				new Portlet("FACES-2921", "com.liferay.faces.issue.primefaces.portlet")));
		BRIDGE_ISSUE_PAGES.add(new PortalPage("FACES-3031",
				new Portlet("FACES-3031", "com.liferay.faces.issue.primefaces.portlet")));
	}

	static {
		LSV_ISSUE_PAGES = new ArrayList<PortalPage>();
		LSV_ISSUE_PAGES.add(new PortalPage("LSV-5", new Portlet("1", "com.liferay.faces.lsv.5.portlet", false)));
		LSV_ISSUE_PAGES.add(new PortalPage("LSV-71-Auto-Dispatch",
				new Portlet("1", "com.liferay.faces.lsv.71.portlet", false)));
		LSV_ISSUE_PAGES.add(new PortalPage("LSV-71-Non-Dispatch",
				new Portlet("2", "com.liferay.faces.lsv.71.portlet", false)));
		LSV_ISSUE_PAGES.add(new PortalPage("LSV-71-Resource-Handler",
				new Portlet("3", "com.liferay.faces.lsv.71.portlet", false)));
		LSV_ISSUE_PAGES.add(new PortalPage("LSV-158-Auto-Dispatch",
				new Portlet("1", "com.liferay.faces.lsv.158.portlet", false)));
		LSV_ISSUE_PAGES.add(new PortalPage("LSV-158-Non-Dispatch",
				new Portlet("2", "com.liferay.faces.lsv.158.portlet", false)));
		LSV_ISSUE_PAGES.add(new PortalPage("LSV-169", new Portlet("1", "com.liferay.faces.lsv.169.portlet", false)));
	}

	static {
		PORTAL_DEMO_PAGES = new ArrayList<PortalPage>();
	}

	static {
		PORTAL_ISSUE_PAGES = new ArrayList<PortalPage>();
		PORTAL_ISSUE_PAGES.add(new PortalPage("FACES-257",
				new Portlet("1", "com.liferay.faces.issue.257.portlet", false)));
		PORTAL_ISSUE_PAGES.add(new PortalPage("FACES-1427", new Portlet("1", "com.liferay.faces.issue.1427.portlet")));
		PORTAL_ISSUE_PAGES.add(new PortalPage("FACES-1439", new Portlet("1", "com.liferay.faces.issue.1439.portlet")));
	}

	static {
		GUEST_PAGES = new ArrayList<PortalPage>();
		GUEST_PAGES.add(new PortalPage("jsf-sign-in", new Portlet("1", "com.liferay.faces.demo.jsf.login.portlet")));

		PortalPage portalPage = new PortalPage("jsf-showcase",
				new Portlet("1", "com.liferay.faces.demo.jsf.showcase.portlet", false));
		portalPage.setLayoutTemplateId("1_column");
		GUEST_PAGES.add(portalPage);
		portalPage = new PortalPage("alloy-showcase",
				new Portlet("1", "com.liferay.faces.demo.alloy.showcase.portlet", false));
		portalPage.setLayoutTemplateId("1_column");
		GUEST_PAGES.add(portalPage);
		portalPage = new PortalPage("metal-showcase",
				new Portlet("1", "com.liferay.faces.demo.metal.showcase.portlet", false));
		portalPage.setLayoutTemplateId("1_column");
		GUEST_PAGES.add(portalPage);
		portalPage = new PortalPage("portal-showcase",
				new Portlet("1", "com.liferay.faces.demo.portal.showcase.portlet", false));
		portalPage.setLayoutTemplateId("1_column");
		GUEST_PAGES.add(portalPage);
	}
}
