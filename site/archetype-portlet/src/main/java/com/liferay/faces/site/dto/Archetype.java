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

import org.apache.maven.model.Model;


/**
 * @author  Vernon Singleton
 */
public class Archetype implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 4221259215512826105L;

	// Private Data Members
	private long id;
	private String propertiesAndDependencies;
	private String liferayVersion;
	private String jsfVersion;
	private String suite;
	private String extVersion;
	private Model model;
	private String command;

	public Archetype(long id, String liferayVersion, String jsfVersion, String suite, String extVersion,
		String propertiesAndDependencies, Model pom, String command) {
		this.id = id;
		this.liferayVersion = liferayVersion;
		this.jsfVersion = jsfVersion;
		this.suite = suite;
		this.extVersion = extVersion;
		this.propertiesAndDependencies = propertiesAndDependencies;
		this.model = pom;
		this.command = command;
	}

	public String getCommand() {
		return command;
	}

	public String getExtVersion() {
		return extVersion;
	}

	public long getId() {
		return id;
	}

	public String getJsfVersion() {
		return jsfVersion;
	}

	public String getLiferayVersion() {
		return liferayVersion;
	}

	public Model getModel() {
		return model;
	}

	public String getPropertiesAndDependencies() {
		return propertiesAndDependencies;
	}

	public String getSuite() {
		return suite;
	}
}
