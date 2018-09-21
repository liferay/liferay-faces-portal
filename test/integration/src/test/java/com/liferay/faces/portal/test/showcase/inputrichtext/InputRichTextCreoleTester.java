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
package com.liferay.faces.portal.test.showcase.inputrichtext;

import org.junit.Test;


/**
 * @author  Kyle Stiemann
 */
public class InputRichTextCreoleTester extends InputRichTextTester {

	// Package-Private Constants
	/* package-private */ static final String BOLD_OPEN = "**";
	/* package-private */ static final String BOLD_CLOSE = BOLD_OPEN;

	@Test
	public void runInputRichTextCreoleTest() {
		runInputRichTextTest("creole", BOLD_OPEN, BOLD_CLOSE, "//", "//");
	}
}
