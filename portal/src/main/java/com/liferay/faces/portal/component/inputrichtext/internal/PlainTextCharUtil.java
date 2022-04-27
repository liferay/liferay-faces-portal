/**
 * Copyright (c) 2000-2022 Liferay, Inc. All rights reserved.
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

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslator;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;

import com.liferay.wiki.engine.WikiEngine;
import com.liferay.wiki.exception.PageContentException;
import com.liferay.wiki.model.WikiPage;

/**
 * @author  Kyle Stiemann
 */
public final class PlainTextCharUtil {
    private static final Logger logger = LoggerFactory.getLogger(PlainTextCharUtil.class);
    // With https://github.com/liferay/liferay-portal/commit/0f098e0a87460a8a1b7afb13c7fbc6af25bf4f0b
    // the access to extractText was broken. Using reflection to call into the
    // correct method.
    private static final MethodHandle extractText;

    static {
        MethodHandle extractTextBuilder = null;
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        if (extractTextBuilder == null) {
            try {
                Class htmlParserUtil = Class.forName("com.liferay.portal.kernel.util.HtmlParserUtil");
                extractTextBuilder = lookup.findStatic(htmlParserUtil, "extractText", MethodType.methodType(String.class, String.class));
            } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException ex) {
                logger.debug("Failed to fetch method extractText from com.liferay.portal.kernel.util.HtmlParserUtil", ex);
            }
        }
        if (extractTextBuilder == null) {
            try {
                Class htmlUtil = Class.forName("com.liferay.portal.kernel.util.HtmlUtil");
                extractTextBuilder = lookup.findStatic(htmlUtil, "extractText", MethodType.methodType(String.class, String.class));
            } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException ex) {
                logger.debug("Failed to fetch method extractText from com.liferay.portal.kernel.util.HtmlUtil", ex);
            }
        }
        if(extractTextBuilder == null) {
            logger.error("Failed to retrieve extractText from com.liferay.portal.kernel.util.HtmlUtil or com.liferay.portal.kernel.util.HtmlParserUtil");
        }
        extractText = extractTextBuilder;
    }

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

        String plainText = "";

        try {
            plainText = (String) extractText.invoke(richText);
        } catch (Throwable ex) {
            logger.error("Failed to invoke extractText", ex);
        }

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
