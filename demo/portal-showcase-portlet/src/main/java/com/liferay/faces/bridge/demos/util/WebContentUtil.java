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
package com.liferay.faces.bridge.demos.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.journal.exception.NoSuchArticleException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;

import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocalizationUtil;


/**
 * @author  Neil Griffin
 */
public final class WebContentUtil {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(WebContentUtil.class);

	private WebContentUtil() {
		throw new AssertionError();
	}

	public static JournalArticle getArticle(long companyId, long userId, long groupId, long folderId, Locale locale,
		String title, String content) {

		JournalArticle journalArticle = null;

		try {

			try {
				String urlTitle = getUrlTitle(title);
				journalArticle = JournalArticleLocalServiceUtil.getArticleByUrlTitle(groupId, urlTitle);
			}
			catch (NoSuchArticleException e) {

				Map<Locale, String> titleMap = new HashMap<Locale, String>();
				titleMap.put(locale, title);

				Map<Locale, String> descriptionMap = Collections.emptyMap();
				String ddmStructureKey = "BASIC-WEB-CONTENT";
				String ddmTemplateKey = "BASIC-WEB-CONTENT";

				ServiceContext serviceContext = new ServiceContext();
				serviceContext.setCompanyId(companyId);
				serviceContext.setScopeGroupId(groupId);
				serviceContext.setUserId(userId);
				serviceContext.setAddGroupPermissions(true);
				serviceContext.setAddGuestPermissions(true);

				journalArticle = JournalArticleLocalServiceUtil.addArticle(userId, groupId, folderId, titleMap,
						descriptionMap, content, ddmStructureKey, ddmTemplateKey, serviceContext);
			}
		}
		catch (Exception e) {
			logger.error(e);
		}

		return journalArticle;
	}

	public static String getUrlTitle(String title) {

		title = title.toLowerCase();
		title = title.replaceAll(" ", "-");

		return title;
	}
}
