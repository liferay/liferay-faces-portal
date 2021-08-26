/**
 * Copyright (c) 2000-2021 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.demos.bean;

import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.liferay.document.library.kernel.model.DLFolderConstants;

import com.liferay.faces.bridge.demos.util.WebContentUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.journal.model.JournalArticle;

import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;


/**
 * @author  Juan Gonzalez
 */
@ManagedBean
@ApplicationScoped
public class RuntimeBacking {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(RuntimeBacking.class);

	// Private Data Members
	private String preferencesArticle1;
	private String preferencesArticle2;
	private String preferencesArticle3;

	public String getPreferencesArticle1() {
		return preferencesArticle1;
	}

	public String getPreferencesArticle2() {
		return preferencesArticle2;
	}

	@PostConstruct
	public void postConstruct() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, Object> requestAttributeMap = facesContext.getExternalContext().getRequestMap();
		ThemeDisplay themeDisplay = (ThemeDisplay) requestAttributeMap.get(WebKeys.THEME_DISPLAY);

		try {
			long companyId = themeDisplay.getCompanyId();
			long userId = themeDisplay.getUserId();
			long groupId = themeDisplay.getScopeGroupId();
			long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			Locale locale = themeDisplay.getLocale();

			StringBuilder content1 = new StringBuilder(9);
			content1.append("<?xml version=\"1.0\"?>");
			content1.append("<root available-locales=\"en_US\" default-locale=\"en_US\">");
			content1.append(
				"<dynamic-element name=\"content\" type=\"text_area\" index-type=\"keyword\" instance-id=\"zeun\">");
			content1.append("<dynamic-content language-id=\"en_US\"><![CDATA[");
			content1.append(
				"<p>Liferay Portal is an enterprise web platform for building business solutions that deliver ");
			content1.append("immediate results and long-term value.</p>");
			content1.append("]]></dynamic-content>");
			content1.append("</dynamic-element>");
			content1.append("</root>");

			JournalArticle article1 = WebContentUtil.getArticle(companyId, userId, groupId, folderId, locale,
					"Liferay Portal", content1.toString());
			this.preferencesArticle1 = getPreferences(article1.getGroupId(), article1.getArticleId());

			StringBuilder content2 = new StringBuilder(9);
			content2.append("<?xml version=\"1.0\"?>");
			content2.append("<root available-locales=\"en_US\" default-locale=\"en_US\">");
			content2.append(
				"<dynamic-element name=\"content\" type=\"text_area\" index-type=\"keyword\" instance-id=\"yujh\">");
			content2.append("<dynamic-content language-id=\"en_US\"><![CDATA[");
			content2.append(
				"<p>Liferay Faces is an umbrella project that provides support for the JavaServer™ Faces (JSF) ");
			content2.append("standard within Liferay Portal.</p>");
			content2.append("]]></dynamic-content>");
			content2.append("</dynamic-element>");
			content2.append("</root>");

			JournalArticle article2 = WebContentUtil.getArticle(companyId, userId, groupId, folderId, locale,
					"Liferay Faces", content2.toString());
			this.preferencesArticle2 = getPreferences(article2.getGroupId(), article2.getArticleId());
		}
		catch (Exception e) {
			logger.error(e);
		}
	}

	protected String getPreferences(long groupId, String articleId) {

		StringBuilder buf = new StringBuilder();
		buf.append("<portlet-preferences>");
		buf.append("<preference>");
		buf.append("<name>groupId</name>");
		buf.append("<value>");
		buf.append(groupId);
		buf.append("</value>");
		buf.append("</preference>");
		buf.append("<preference>");
		buf.append("<name>articleId</name>");
		buf.append("<value>");
		buf.append(articleId);
		buf.append("</value>");
		buf.append("</preference>");
		buf.append("<preference>");
		buf.append("<name>portletSetupShowBorders</name>");
		buf.append("<value>true</value>");
		buf.append("</preference>");
		buf.append("</portlet-preferences>");

		return buf.toString();
	}
}
