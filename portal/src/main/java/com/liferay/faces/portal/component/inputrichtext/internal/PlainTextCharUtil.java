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
package com.liferay.faces.portal.component.inputrichtext.internal;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslator;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.util.HtmlUtil;

import com.liferay.wiki.engine.WikiEngine;
import com.liferay.wiki.exception.PageContentException;
import com.liferay.wiki.model.WikiPage;


/**
 * @author  Kyle Stiemann
 */
public final class PlainTextCharUtil {

	private PlainTextCharUtil() {
		throw new AssertionError();
	}

	public static int getBBCodePlainTextCharCount(String richText) {

		BBCodeTranslator bbcodeTranslator = BBCodeTranslatorUtil.getBBCodeTranslator();

		return getBBCodePlainTextCharCount(richText, bbcodeTranslator);
	}

	public static int getCreolePlainTextCharCount(String richText) throws FailedToCalculatePlainTextCharCountException {

		WikiEngine wikiEngine = CreoleWikiEngineServiceTrackerCustomizer.CREOLE_WIKI_ENGINE_SERVICE_TRACKER
			.getService();

		return getCreolePlainTextCharCount(richText, wikiEngine);
	}

	public static int getHTMLPlainTextCharCount(String richText) {

		String plainText = HtmlUtil.extractText(richText);

		return plainText.length();
	}

	/* package-private */ static int getBBCodePlainTextCharCount(String richText, BBCodeTranslator bbcodeTranslator) {

		String htmlRichText = bbcodeTranslator.getHTML(richText);

		return getHTMLPlainTextCharCount(htmlRichText);
	}

	/* package-private */ static int getCreolePlainTextCharCount(String richText, WikiEngine wikiEngine)
		throws FailedToCalculatePlainTextCharCountException {

		try {

			WikiPage wikiPage = new WikiPageStringImpl(richText);
			String htmlRichText = wikiEngine.convert(wikiPage, null, null, null);

			return getHTMLPlainTextCharCount(htmlRichText);
		}
		catch (PageContentException | NullPointerException e) {
			throw new FailedToCalculatePlainTextCharCountException(e);
		}
	}

	private static final class CreoleWikiEngineServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<WikiEngine, WikiEngine> {

		// Private Constants
		private static final ServiceTracker<WikiEngine, WikiEngine> CREOLE_WIKI_ENGINE_SERVICE_TRACKER;

		static {

			Bundle bundle = FrameworkUtil.getBundle(CreoleWikiEngineServiceTrackerCustomizer.class);
			BundleContext bundleContext = bundle.getBundleContext();
			CREOLE_WIKI_ENGINE_SERVICE_TRACKER = new ServiceTracker<>(bundleContext, WikiEngine.class,
					new CreoleWikiEngineServiceTrackerCustomizer(bundleContext));
			CREOLE_WIKI_ENGINE_SERVICE_TRACKER.open();
		}

		// Private Final Data Members
		private final BundleContext bundleContext;

		public CreoleWikiEngineServiceTrackerCustomizer(BundleContext bundleContext) {
			this.bundleContext = bundleContext;
		}

		@Override
		public WikiEngine addingService(ServiceReference<WikiEngine> serviceReference) {

			WikiEngine wikiEngine = bundleContext.getService(serviceReference);

			if (wikiEngine != null) {

				String format = wikiEngine.getFormat();

				if (!"creole".equalsIgnoreCase(format)) {
					wikiEngine = null;
				}
			}

			return wikiEngine;
		}

		@Override
		public void modifiedService(ServiceReference<WikiEngine> serviceReference, WikiEngine wikiEngine) {
			// no-op
		}

		@Override
		public void removedService(ServiceReference<WikiEngine> serviceReference, WikiEngine wikiEngine) {
			bundleContext.ungetService(serviceReference);
		}
	}
}
