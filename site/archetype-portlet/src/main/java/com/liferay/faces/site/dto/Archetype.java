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
package com.liferay.faces.site.dto;

import java.io.Serializable;


/**
 * @author  Vernon Singleton
 */
public class Archetype implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 4221259215512826105L;

	// Private Data Members
	private String dependencies;
	private String gradle;
	private String liferayVersion;
	private String jsfVersion;
	private String suite;
	private String command;

	public Archetype(String liferayVersion, String jsfVersion, String suite, String dependencies, String gradle, String command) {
		this.liferayVersion = liferayVersion;
		this.jsfVersion = jsfVersion;
		this.suite = suite;
		this.dependencies = dependencies;
		this.gradle = gradle;
		this.command = command;
	}

	public String getCommand() {
		return command;
	}

	public String getDependencies() {
		return dependencies;
	}

	public String getGradle() {
		return gradle;
	}

	public String getJsfVersion() {
		return jsfVersion;
	}

	public String getLiferayVersion() {
		return liferayVersion;
	}

	public String getSuite() {
		return suite;
	}
}
