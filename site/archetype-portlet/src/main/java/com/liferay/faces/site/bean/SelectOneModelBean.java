/**
 * Copyright (c) 2000-2016 Liferay, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liferay.faces.site.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.liferay.faces.site.dto.Archetype;
import com.liferay.faces.site.dto.JsfVersion;
import com.liferay.faces.site.dto.LiferayVersion;
import com.liferay.faces.site.dto.Suite;
import com.liferay.faces.site.service.ArchetypeService;


/**
 * @author  Vernon Singleton
 */
@ManagedBean
@RequestScoped
public class SelectOneModelBean {

	@ManagedProperty(name = "archetypeService", value = "#{archetypeService}")
	private ArchetypeService archetypeService;
	
	public List<Archetype> getArchetypes() {
		return archetypeService.getArchetypes();
	}
	
	public List<LiferayVersion> getLiferayVersions() {
		return archetypeService.getLiferayVersions();
	}

	public List<JsfVersion> getJsfVersions() {
		return archetypeService.getJsfVersions();
	}
	
	public void reset() {
		archetypeService.init();
	}
	
	public List<Suite> getSuites() {
		return archetypeService.getSuites();
	}
	
	private Long archetypeId = 3L;
	
	public Long getArchetypeId() {
		return archetypeId;
	}

	public void setArchetypeId(Long archetypeId) {
		this.archetypeId = archetypeId;
	}
	
	private String favoriteSuite;
	
	public String getFavoriteSuite() {
		return favoriteSuite;
	}

	public void setFavoriteSuite(String favoriteSuite) {
		this.favoriteSuite = favoriteSuite;
	}

	private String favoriteLiferayVersion;

	public String getFavoriteLiferayVersion() {
		return favoriteLiferayVersion;
	}
	
	public void setFavoriteLiferayVersion(String liferayVersion) {
		this.favoriteLiferayVersion = liferayVersion;
	}
	
	private String favoriteJsfVersion;

	public String getFavoriteJsfVersion() {
		return favoriteJsfVersion;
	}

	public void setFavoriteJsfVersion(String favoriteJsfVersion) {
		this.favoriteJsfVersion = favoriteJsfVersion;
	}

	public void setArchetypeService(ArchetypeService archetypeService) {
		this.archetypeService = archetypeService;
	}

}
