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

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.liferay.message.boards.parser.bbcode.internal.HtmlBBCodeTranslatorImpl;

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.util.HtmlImpl;

import com.liferay.wiki.engine.creole.internal.CreoleWikiEngine;


/**
 * @author  Kyle Stiemann
 */
public class PlainTextCharUtilTest {

	@BeforeClass
	public static void setUp() {

		HtmlUtil htmlUtil = new HtmlUtil();
		htmlUtil.setHtml(new HtmlImpl());
	}

	@Test
	public void testPlainTextCharCount() throws FailedToCalculatePlainTextCharCountException {

		Assert.assertEquals("test a lot of formatted text".length(),
			PlainTextCharUtil.getBBCodePlainTextCharCount(
				"[i]test [b]a [s]lot[/s][/b][s] [u]of formatted[/u] text[/s][/i]", new HtmlBBCodeTranslatorImpl()));
		Assert.assertEquals("test a lot of formatted text".length(),
			PlainTextCharUtil.getCreolePlainTextCharCount("//test **a **//**lot** of formatted// text//",
				new CreoleWikiEngine()));
		Assert.assertEquals("test a lot of formatted text".length(),
			PlainTextCharUtil.getHTMLPlainTextCharCount(
				"<em>test <strong>a <strike>lot</strike></strong><strike> <u>of formatted</u> text</strike></em>"));
	}
}
