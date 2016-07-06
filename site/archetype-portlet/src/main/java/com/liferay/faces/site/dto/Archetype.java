package com.liferay.faces.site.dto;

import java.io.Serializable;

import org.apache.maven.model.Model;

public class Archetype implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 1L;

	// Private Data Members
	private long id;
	private String propertiesAndDependencies;
	private String liferayVersion;
	private String jsfVersion;
	private String suite;
	private String extVersion;
	private Model model;
	private String command;
	
	public Archetype(long id, String liferayVersion, String jsfVersion, String suite, String extVersion, String propertiesAndDependencies, Model pom, String command) {
		this.id = id;
		this.liferayVersion = liferayVersion;
		this.jsfVersion = jsfVersion;
		this.suite = suite;
		this.extVersion = extVersion;
		this.propertiesAndDependencies = propertiesAndDependencies;
		this.model = pom;
		this.command = command;
	}
	
	public long getId() {
		return id;
	}
	
	public String getCommand() {
		return command;
	}
	
	public String getExtVersion() {
		return extVersion;
	}
	
	public String getPropertiesAndDependencies() {
		return propertiesAndDependencies;
	}
	
	public String getLiferayVersion() {
		return liferayVersion;
	}
	
	public String getJsfVersion() {
		return jsfVersion;
	}
	
	public Model getModel() {
		return model;
	}
	
	public String getSuite() {
		return suite;
	}
}