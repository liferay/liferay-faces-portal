package com.liferay.faces.site.dto;

import java.io.Serializable;

public class LiferayVersion implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 1L;

	// Private Data Members
	private long id;
	private String description;
	private String version;
	
	public LiferayVersion(long id, String version, String description) {
		this.id = id;
		this.version = version;
		this.description = description;
	}
	
	public long getId() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getVersion() {
		return version;
	}
}
