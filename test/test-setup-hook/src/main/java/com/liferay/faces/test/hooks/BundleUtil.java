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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.PortletLocalServiceUtil;


/**
 * @author  Neil Griffin
 */
public class BundleUtil {

	public static Bundle[] getBundles(long companyId) {

		List<Bundle> bundles = new ArrayList<Bundle>();

		try {
			List<com.liferay.portal.model.Portlet> portlets = PortletLocalServiceUtil.getPortlets(companyId, false,
					false);

			int bundleId = 1;

			for (com.liferay.portal.model.Portlet portlet : portlets) {

				String portletId = portlet.getPortletId();

				int pos = portletId.indexOf("_WAR_");

				if (pos > 0) {

					String symbolicName = portletId.substring(pos + 5);
					// System.err.println("!@#$ portlet symbolicName=" + symbolicName + " name=" +
						// portlet.getPortletName());

					Bundle bundle = new Bundle(bundleId++, symbolicName, Bundle.ACTIVE);
					bundles.add(bundle);
				}
				// else {
					// System.err.println("!@#$ NOT WAR: " + portletId + " name=" + portlet.getPortletName());
				// }
			}
		}
		catch (SystemException e) {
			e.printStackTrace();
		}

		return bundles.toArray(new Bundle[bundles.size()]);
	}
}
