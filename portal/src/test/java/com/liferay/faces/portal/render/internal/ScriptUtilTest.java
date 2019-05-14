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

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.util.HtmlImpl;


/**
 * @author  Kyle Stiemann
 */
public final class ScriptUtilTest {

	@BeforeClass
	public static void setUp() {

		HtmlUtil htmlUtil = new HtmlUtil();
		htmlUtil.setHtml(new HtmlImpl());
	}

	@Test
	public final void testScriptUtil_MOJARRA_4340_FACES_3441() {
		Assert.assertEquals(
			"<script data-senna-track=\"permanent\" type=\"text/javascript\">\nalert('hello');\n</script>",
			ScriptUtil.prepareScriptsForMojarraPartialResponse(
				"<script type=\"text/javascript\" data-senna-track=\"permanent\">\n<![CDATA[alert('hello');]]>\n</script>"));
		Assert.assertEquals(
			"<script test=\"&quot;&apos;test/test/test&apos;&quot;\" type=\"text/javascript\">\nalert('hello');\n</script>",
			ScriptUtil.prepareScriptsForMojarraPartialResponse(
				"<script type=\"text/javascript\" test=\"&quot;&apos;test&#x2f;test&#47;test&apos;&quot;\">\nalert('hello');\n</script>"));
		Assert.assertEquals(
			"<SCRIPT data-senna-track=\"permanent\" type=\"text/javascript\">\nalert('hello');\n</SCRIPT>",
			ScriptUtil.prepareScriptsForMojarraPartialResponse(
				"<SCRIPT type=\"text/javascript\" data-senna-track=\"permanent\">\nalert('hello');\n</SCRIPT>"));
		Assert.assertEquals(
			"<script data-senna-track=\"permanent\" type=\"text/javascript\">\nalert('hello');\n</script>",
			ScriptUtil.prepareScriptsForMojarraPartialResponse(
				"<script type=\"text/javascript\" data-senna-track=\"permanent\">\nalert('hello');\n</script>"));
		Assert.assertEquals(
			"<script data-senna-track=\"permanent\" type=\"text/javascript\">\nalert('hello');\n</script>",
			ScriptUtil.prepareScriptsForMojarraPartialResponse(
				"<script type=\"text&#x2f;javascript\" data-senna-track=\"permanent\">\nalert('hello');\n</script>"));
		Assert.assertEquals(
			"<script data-senna-track=\"permanent\" type=\"text/javascript\">\nalert('hello');\n</script>",
			ScriptUtil.prepareScriptsForMojarraPartialResponse(
				"<script\ntype=\"text&#x2f;javascript\"\ndata-senna-track=\"permanent\">\nalert('hello');\n</script>"));
		Assert.assertEquals(
			"<div>\n<script data-senna-track=\"permanent\" type=\"text/javascript\">\nalert('hello');\n</script>\n</div>",
			ScriptUtil.prepareScriptsForMojarraPartialResponse(
				"<div>\n<script type=\"text/javascript\" data-senna-track=\"permanent\">\nalert('hello');\n</script>\n</div>"));
		Assert.assertEquals(
			"<div>\n<script data-senna-track=\"permanent\" type=\"text/javascript\">\nalert('hello');\n</script>\n</div>",
			ScriptUtil.prepareScriptsForMojarraPartialResponse(
				"<div>\n<script type=\"text&#x2f;javascript\" data-senna-track=\"permanent\">\nalert('hello');\n</script>\n</div>"));
		Assert.assertEquals(
			"<div>\n<script data-senna-track=\"permanent\" type=\"text/javascript\">\nalert('hello');\n</script>\n</div>",
			ScriptUtil.prepareScriptsForMojarraPartialResponse(
				"<div>\n<script\ntype=\"text&#x2f;javascript\"\ndata-senna-track=\"permanent\">\nalert('hello');\n</script>\n</div>"));
		Assert.assertEquals(
			"<script src=\"https://www.google.com/recaptcha/api.js?hl=en&onload=_1_WAR_comliferayfacesdemoportalshowcaseportlet_onloadCallback&render=explicit\" type=\"text/javascript\"></script>",
			ScriptUtil.prepareScriptsForMojarraPartialResponse(
				"<script type=\"text/javascript\" src=\"https://www.google.com/recaptcha/api.js?hl=en&onload=_1_WAR_comliferayfacesdemoportalshowcaseportlet_onloadCallback&render=explicit\"></script>"));
		Assert.assertEquals(
			"<script src=\"https://www.google.com/recaptcha/api.js?hl=en&onload=_1_WAR_comliferayfacesdemoportalshowcaseportlet_onloadCallback&render=explicit\" type=\"text/javascript\"></script>",
			ScriptUtil.prepareScriptsForMojarraPartialResponse(
				"<script type=\"text/javascript\" src=\"https&#x3a;&#x2f;&#x2f;www&#x2e;google&#x2e;com&#x2f;recaptcha&#x2f;api&#x2e;js?hl=en&onload=_1_WAR_comliferayfacesdemoportalshowcaseportlet_onloadCallback&render=explicit\"></script>"));
		Assert.assertEquals(
			"<script src=\"https://www.google.com/recaptcha/api.js?hl=en&onload=_1_WAR_comliferayfacesdemoportalshowcaseportlet_onloadCallback&render=explicit\" type=\"text/javascript\"></script>",
			ScriptUtil.prepareScriptsForMojarraPartialResponse(
				"<script\n\ttype=\"text/javascript\"\n\tsrc=\"https&#x3a;&#x2f;&#x2f;www&#x2e;google&#x2e;com&#x2f;recaptcha&#x2f;api&#x2e;js?hl=en&onload=_1_WAR_comliferayfacesdemoportalshowcaseportlet_onloadCallback&render=explicit\"></script>"));
		Assert.assertEquals(
			"<div>\n<script src=\"https://www.google.com/recaptcha/api.js?hl=en&onload=_1_WAR_comliferayfacesdemoportalshowcaseportlet_onloadCallback&render=explicit\" type=\"text/javascript\"></script>\n</div>",
			ScriptUtil.prepareScriptsForMojarraPartialResponse(
				"<div>\n<script type=\"text/javascript\" src=\"https://www.google.com/recaptcha/api.js?hl=en&onload=_1_WAR_comliferayfacesdemoportalshowcaseportlet_onloadCallback&render=explicit\"></script>\n</div>"));
		Assert.assertEquals(
			"<div>\n<script src=\"https://www.google.com/recaptcha/api.js?hl=en&onload=_1_WAR_comliferayfacesdemoportalshowcaseportlet_onloadCallback&render=explicit\" type=\"text/javascript\"></script>\n</div>",
			ScriptUtil.prepareScriptsForMojarraPartialResponse(
				"<div>\n<script type=\"text/javascript\" src=\"https&#x3a;&#x2f;&#x2f;www&#x2e;google&#x2e;com&#x2f;recaptcha&#x2f;api&#x2e;js?hl=en&onload=_1_WAR_comliferayfacesdemoportalshowcaseportlet_onloadCallback&render=explicit\"></script>\n</div>"));
		Assert.assertEquals(
			"<div>\n<script src=\"https://www.google.com/recaptcha/api.js?hl=en&onload=_1_WAR_comliferayfacesdemoportalshowcaseportlet_onloadCallback&render=explicit\" type=\"text/javascript\"></script>\n</div>",
			ScriptUtil.prepareScriptsForMojarraPartialResponse(
				"<div>\n<script\n\ttype=\"text/javascript\"\n\tsrc=\"https&#x3a;&#x2f;&#x2f;www&#x2e;google&#x2e;com&#x2f;recaptcha&#x2f;api&#x2e;js?hl=en&onload=_1_WAR_comliferayfacesdemoportalshowcaseportlet_onloadCallback&render=explicit\"></script>\n</div>"));
	}
}
