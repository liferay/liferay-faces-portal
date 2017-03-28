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

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.osgi.framework.Bundle;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Namespace;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.kernel.xml.SAXReaderUtil;


/**
 * @author  Neil Griffin
 */
public class TestSetupAction extends TestSetupCompatAction {

	// Logger
	private static final Log logger = LogFactory.getLog(TestSetupAction.class);

	// Private Constants
	private static final String BRIDGE_TCK_MAIN_PORTLET_ARTIFACT_NAME =
		"com.liferay.faces.test.bridge.tck.main.portlet";
	private static final Namespace PLUTO_PORTAL_DRIVER_CONFIG_NAMESPACE = SAXReaderUtil.createNamespace(
			"http://portals.apache.org/pluto/xsd/pluto-portal-driver-config.xsd");

	@Override
	public void run(String[] companyIds) throws ActionException {

		try {

			for (String companyIdAsString : companyIds) {

				long companyId = Long.parseLong(companyIdAsString);
				setupPermissionChecker(companyId);

				Company company = CompanyLocalServiceUtil.getCompanyById(companyId);
				long userId = company.getDefaultUser().getUserId();
				setupUsers(companyId, userId);
				setupSites(companyId, userId);
				clearPermissionChecker();
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	protected void addAllUsersToSite(long groupId) throws Exception {

		List<User> users = UserLocalServiceUtil.getUsers(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		ArrayList<Long> userIdList = new ArrayList<Long>();

		for (User user : users) {

			if (!user.isDefaultUser()) {
				userIdList.add(user.getUserId());
			}
		}

		long[] userIds = new long[userIdList.size()];

		for (int i = 0; i < userIds.length; i++) {
			userIds[i] = userIdList.get(i);
		}

		UserLocalServiceUtil.addGroupUsers(groupId, userIds);
	}

	protected Layout getPortalPageLayout(long userId, long groupId, String portalPageName, boolean privateLayout)
		throws Exception {
		Layout portalPageLayout = null;

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(groupId, privateLayout);

		for (Layout layout : layouts) {

			if (layout.getName(Locale.US).equals(portalPageName)) {
				portalPageLayout = layout;
			}
		}

		if (portalPageLayout == null) {
			long parentLayoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;
			String type = LayoutConstants.TYPE_PORTLET;
			String friendlyURL = "/" + portalPageName.toLowerCase();
			portalPageLayout = ServiceUtil.addLayout(userId, groupId, privateLayout, parentLayoutId, portalPageName,
					portalPageName, portalPageName, type, false, friendlyURL);
		}

		return portalPageLayout;
	}

	protected Group getSiteForSetup(long companyId, long userId, String name) throws Exception {

		Group site;

		try {
			site = GroupLocalServiceUtil.getGroup(companyId, name);
		}
		catch (NoSuchGroupException e) {
			site = ServiceUtil.addActiveOpenGroup(userId, name);
		}

		logger.info("Setting up site name=[" + site.getName() + "] publicLayouts=[" + site.hasPublicLayouts() + "]");

		return site;
	}

	protected void setupArchetypesSite(long companyId, long userId, Bundle[] bundles) throws Exception {
		Group site = getSiteForSetup(companyId, userId, "Archetypes");
		long groupId = site.getGroupId();
		addAllUsersToSite(groupId);

		for (PortalPage portalPage : TestPages.ARCHETYPE_PAGES) {
			setupPrivatePage(userId, groupId, portalPage, bundles);
		}
	}

	protected void setupBridgeDemosSite(long companyId, long userId, Bundle[] bundles) throws Exception {
		Group site = getSiteForSetup(companyId, userId, "Bridge Demos");
		long groupId = site.getGroupId();
		addAllUsersToSite(groupId);

		for (PortalPage portalPage : TestPages.BRIDGE_DEMO_PAGES) {
			setupPrivatePage(userId, groupId, portalPage, bundles);
		}
	}

	protected void setupBridgeIssuesSite(long companyId, long userId, Bundle[] bundles) throws Exception {
		Group site = getSiteForSetup(companyId, userId, "Bridge Issues");
		long groupId = site.getGroupId();
		addAllUsersToSite(groupId);

		for (PortalPage portalPage : TestPages.BRIDGE_ISSUE_PAGES) {
			setupPublicPage(userId, groupId, portalPage, bundles);
		}
	}

	protected void setupBridgeTCKSite(long companyId, long userId, Bundle[] bundles) throws Exception {
		Group site = getSiteForSetup(companyId, userId, "Bridge TCK");
		long groupId = site.getGroupId();
		addAllUsersToSite(groupId);

		URL configFileURL = getClass().getClassLoader().getResource("pluto-portal-driver-config.xml");
		Document document = SAXReaderUtil.read(configFileURL);
		Element rootElement = document.getRootElement();
		Element renderConfigElement = rootElement.element(getPlutoElementQName("render-config"));
		Iterator<Element> pageElementIterator = renderConfigElement.elementIterator(getPlutoElementQName("page"));

		while (pageElementIterator.hasNext()) {

			Element pageElement = pageElementIterator.next();
			Attribute nameAttribute = pageElement.attribute("name");
			String pageName = nameAttribute.getValue();
			Element portletElement = pageElement.element(getPlutoElementQName("portlet"));
			Attribute contextAttribute = portletElement.attribute("context");
			String context = contextAttribute.getValue();

			if (context.endsWith(BRIDGE_TCK_MAIN_PORTLET_ARTIFACT_NAME)) {

				nameAttribute = portletElement.attribute("name");

				String portletName = nameAttribute.getValue();
				PortalPage portalPage = new PortalPage(pageName,
						new Portlet(portletName, BRIDGE_TCK_MAIN_PORTLET_ARTIFACT_NAME, false));
				setupPrivatePage(userId, groupId, portalPage, bundles);
			}
		}

		setupPrivatePage(userId, groupId,
			new PortalPage("Lifecycle Set",
				new Portlet("chapter3TestslifecycleTestportlet",
					"com.liferay.faces.test.bridge.tck.lifecycle.set.portlet")), bundles);
	}

	protected void setupGuestSite(long companyId, long userId, Bundle[] bundles) throws Exception {
		Group site = getSiteForSetup(companyId, userId, "Guest");
		long groupId = site.getGroupId();
		addAllUsersToSite(groupId);

		for (PortalPage portalPage : TestPages.GUEST_PAGES) {
			setupPublicPage(userId, groupId, portalPage, bundles);
		}
	}

	protected void setupLSVIssuesSite(long companyId, long userId, Bundle[] bundles) throws Exception {
		Group site = getSiteForSetup(companyId, userId, "LSV Issues");
		long groupId = site.getGroupId();
		addAllUsersToSite(groupId);

		for (PortalPage portalPage : TestPages.LSV_ISSUE_PAGES) {
			setupPublicPage(userId, groupId, portalPage, bundles);
		}
	}

	protected void setupPage(long userId, long groupId, PortalPage portalPage, boolean privateLayout, Bundle[] bundles)
		throws Exception {
		String portalPageName = portalPage.getPageName();
		List<Portlet> portlets = portalPage.getPortlets();
		Layout portalPageLayout = getPortalPageLayout(userId, groupId, portalPageName, privateLayout);
		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet) portalPageLayout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(userId, portalPage.getLayoutTemplateId(), false);

		int columnNumber = 1;

		List<String> addedPortletIds = new ArrayList<String>();

		for (Portlet portlet : portlets) {

			String bundleName = portlet.getBundleName();
			long bundleId = 0L;
			int bundleState = Bundle.UNINSTALLED;

			for (Bundle bundle : bundles) {

				String symbolicName = bundle.getSymbolicName();

				if (symbolicName.startsWith(bundleName)) {
					bundleId = bundle.getBundleId();
					bundleState = bundle.getState();
				}
			}

			String portletName = portlet.getPortletName();

			if (bundleId > 0) {

				if (bundleState == Bundle.ACTIVE) {

					String portletId;

					if (portlet.getArtifactType() == Portlet.ArtifactType.WAB) {
						portletId = portletName + portlet.getInstanceToken();
					}
					else {
						portletId = portletName.replaceAll("[-]", "") + "_WAR_" +
							bundleName.replaceAll("[-]", "").replaceAll("[.]", "") + portlet.getInstanceToken();
					}

					addPortlet(portalPageLayout, layoutTypePortlet, userId, columnNumber, portletId);
					storePortletPreferences(portalPageLayout, portletId);
					addedPortletIds.add(portletId);
				}
				else {
					logger.warn("Unable to add portletName=[" + portletName + "] since bundleName=[" + bundleName +
						"] is not active.");
				}
			}
			else {
				logger.warn("Unable to add portletName=[" + portletName + "] since bundleName=[" + bundleName +
					"] is not deployed.");
			}

			columnNumber++;
		}

		LayoutLocalServiceUtil.updateLayout(portalPageLayout);

		int totalAddedPortlets = addedPortletIds.size();

		if (totalAddedPortlets == 0) {
			logger.info("Setting up page name=[" + portalPageName + "] without any portlets");
		}
		else if (totalAddedPortlets == 1) {
			logger.info("Setting up page name=[" + portalPageName + "] with portlet=" + addedPortletIds.toString());
		}
		else {
			logger.info("Setting up page name=[" + portalPageName + "] with portlets=" + addedPortletIds.toString());
		}
	}

	protected void setupPortalDemosSite(long companyId, long userId, Bundle[] bundles) throws Exception {
		Group site = getSiteForSetup(companyId, userId, "Portal Demos");
		long groupId = site.getGroupId();
		addAllUsersToSite(groupId);

		for (PortalPage portalPage : TestPages.PORTAL_DEMO_PAGES) {
			setupPrivatePage(userId, groupId, portalPage, bundles);
		}
	}

	protected void setupPortalIssuesSite(long companyId, long userId, Bundle[] bundles) throws Exception {
		Group site = getSiteForSetup(companyId, userId, "Portal Issues");
		long groupId = site.getGroupId();
		addAllUsersToSite(groupId);

		for (PortalPage portalPage : TestPages.PORTAL_ISSUE_PAGES) {
			setupPublicPage(userId, groupId, portalPage, bundles);
		}
	}

	protected void setupPrivatePage(long userId, long groupId, PortalPage portalPage, Bundle[] bundles)
		throws Exception {
		setupPage(userId, groupId, portalPage, true, bundles);
	}

	protected void setupPublicPage(long userId, long groupId, PortalPage portalPage, Bundle[] bundles)
		throws Exception {
		setupPage(userId, groupId, portalPage, false, bundles);
	}

	protected void setupSites(long companyId, long userId) throws Exception {

		Bundle[] bundles = BundleUtil.getBundles();
		setupArchetypesSite(companyId, userId, bundles);
		setupBridgeDemosSite(companyId, userId, bundles);
		setupBridgeIssuesSite(companyId, userId, bundles);
		setupLSVIssuesSite(companyId, userId, bundles);
		setupPortalDemosSite(companyId, userId, bundles);
		setupPortalIssuesSite(companyId, userId, bundles);
		setupBridgeTCKSite(companyId, userId, bundles);
		setupGuestSite(companyId, userId, bundles);
	}

	protected void setupUsers(long companyId, long userId) throws Exception {
		ServiceUtil.addUser(userId, companyId, "John", "Adams");
		ServiceUtil.addUser(userId, companyId, "Samuel", "Adams");
		ServiceUtil.addUser(userId, companyId, "Josiah", "Bartlett");
		ServiceUtil.addUser(userId, companyId, "Carter", "Braxton");
		ServiceUtil.addUser(userId, companyId, "Charles", "Carroll");
		ServiceUtil.addUser(userId, companyId, "Benjamin", "Franklin");
		ServiceUtil.addUser(userId, companyId, "Elbridge", "Gerry");
		ServiceUtil.addUser(userId, companyId, "John", "Hancock");
		ServiceUtil.addUser(userId, companyId, "Thomas", "Jefferson");
		ServiceUtil.addUser(userId, companyId, "Francis", "Lewis");
		ServiceUtil.addUser(userId, companyId, "Philip", "Livingston");
		ServiceUtil.addUser(userId, companyId, "Thomas", "McKean");
		ServiceUtil.addUser(userId, companyId, "Arthur", "Middleton");
		ServiceUtil.addUser(userId, companyId, "John", "Penn");
		ServiceUtil.addUser(userId, companyId, "George", "Read");
		ServiceUtil.addUser(userId, companyId, "John", "Rutledge");
		ServiceUtil.addUser(userId, companyId, "Roger", "Sherman");
		ServiceUtil.addUser(userId, companyId, "Thomas", "Stone");
		ServiceUtil.addUser(userId, companyId, "George", "Taylor");
		ServiceUtil.addUser(userId, companyId, "George", "Washington");
		ServiceUtil.addUser(userId, companyId, "John", "Witherspoon");
		ServiceUtil.addUser(userId, companyId, "Oliver", "Wolcott");
		ServiceUtil.addUser(userId, companyId, "George", "Wythe");
	}

	private QName getPlutoElementQName(String name) {
		return SAXReaderUtil.createQName(name, PLUTO_PORTAL_DRIVER_CONFIG_NAMESPACE);
	}
}
