/**
 * Copyright (c) 2000-2019 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.portal.render.internal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.HtmlUtil;


/**
 * @author  Kyle Stiemann
 */
/* package-private */ final class ScriptUtil {

	// Private Constants
	private static final String CASE_INSENSITIVE_FLAG = "(?i)";
	private static final Pattern SCRIPT_PATTERN = Pattern.compile(CASE_INSENSITIVE_FLAG +
			"(<(script)([^>]*)(type=[\"']text.+javascript[\"'])([^>]*)>)");
	private static final String ENTITY_PREFIX = "&#";
	private static final int FULL_PATTERN_GROUP = 1;
	private static final int SCRIPT_TAG_GROUP = FULL_PATTERN_GROUP + 1;
	private static final int FIRST_NOT_ELEMENT_CLOSE_GROUP = SCRIPT_TAG_GROUP + 1;
	private static final int TYPE_TEXT_JAVASCRIPT_GROUP = FIRST_NOT_ELEMENT_CLOSE_GROUP + 1;
	private static final int LAST_NOT_ELEMENT_CLOSE_GROUP = TYPE_TEXT_JAVASCRIPT_GROUP + 1;
	private static final int HEX_RADIX = 16;

	private ScriptUtil() {
		throw new AssertionError();
	}

	/**
	 * This method ensures that scripts within the provided markup are rendered correctly so that Mojarra will be able
	 * to strip and run them. For more details, see <a href="https://github.com/javaserverfaces/mojarra/issues/4340">
	 * Mojarra #4340</a>: <em>Script resources in &lt;body&gt; are never run during Ajax requests</em> and <a
	 * href="https://issues.liferay.com/browse/FACES-3441">FACES-3441</a>: <em>portal:captcha ReCaptcha fails to
	 * re-render on ajax on Liferay 7.0 + FP81</em>.
	 *
	 * @param  markup
	 */
	/* package-private */ static String prepareScriptsForMojarraPartialResponse(String markup) {

		StringBuilder stringBuilder = new StringBuilder();
		Matcher matcher = SCRIPT_PATTERN.matcher(markup);
		int lastMatchEnd = 0;

		while (matcher.find()) {

			int matchStart = matcher.start();
			int matchEnd = matcher.end();
			stringBuilder.append(markup.substring(lastMatchEnd, matchStart));
			stringBuilder.append("<");
			stringBuilder.append(matcher.group(SCRIPT_TAG_GROUP));
			appendTrimmedGroupIfNotNull(stringBuilder, matcher, FIRST_NOT_ELEMENT_CLOSE_GROUP);
			appendTrimmedGroupIfNotNull(stringBuilder, matcher, LAST_NOT_ELEMENT_CLOSE_GROUP);
			appendTrimmedGroupIfNotNull(stringBuilder, matcher, TYPE_TEXT_JAVASCRIPT_GROUP);
			stringBuilder.append(">");

			lastMatchEnd = matchEnd;
		}

		if (lastMatchEnd < markup.length()) {
			stringBuilder.append(markup.substring(lastMatchEnd, markup.length()));
		}

		// Remove all the "<![CDATA[" and "]]>" tokens since they will interfere with the JSF partial-response.
		return stringBuilder.toString().replace("<![CDATA[", "").replace("]]>", "");
	}

	private static void appendTrimmedGroupIfNotNull(StringBuilder stringBuilder, Matcher matcher, int groupIndex) {

		String group = matcher.group(groupIndex);

		if (group != null) {

			String trimmedGroup = group.trim();

			if (!"".equals(trimmedGroup)) {

				stringBuilder.append(" ");
				stringBuilder.append(unescape(trimmedGroup));
			}
		}
	}

	private static String unescape(String html) {

		StringBuilder stringBuilder = new StringBuilder();
		int i = html.indexOf(ENTITY_PREFIX);

		while (i > -1) {

			stringBuilder.append(html.substring(0, i));
			html = html.substring(i);
			i = html.indexOf(";") + 1;

			String entity = html.substring(0, i);
			String entityPrefix = ENTITY_PREFIX + "x";
			int radix = HEX_RADIX;

			if (!entity.startsWith(entityPrefix)) {

				entityPrefix = ENTITY_PREFIX;
				radix = 10;
			}

			int charCode = Integer.parseInt(entity.substring(entityPrefix.length(), entity.length() - 1), radix);
			String unescapedCharacter = String.valueOf(Character.toChars(charCode));

			if (unescapedCharacter.equals("\"") || unescapedCharacter.endsWith("'")) {
				unescapedCharacter = HtmlUtil.escape(html);
			}

			stringBuilder.append(unescapedCharacter);
			html = html.substring(i);
			i = html.indexOf(ENTITY_PREFIX);
		}

		if (!"".equals(html)) {
			stringBuilder.append(html);
		}

		return stringBuilder.toString();
	}
}
