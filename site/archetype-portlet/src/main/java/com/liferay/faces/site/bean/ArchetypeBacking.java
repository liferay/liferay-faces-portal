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
package com.liferay.faces.site.bean;

import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import com.liferay.faces.site.dto.Archetype;
import com.liferay.faces.site.dto.Suite;
import com.liferay.faces.site.service.ArchetypeService;
import com.liferay.faces.util.context.FacesContextHelperUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Vernon Singleton
 * @author  Neil Griffin
 */
@ManagedBean
@RequestScoped
public class ArchetypeBacking {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ArchetypeBacking.class);

	// Private Data Members
	private String favoriteSuite = "jsf";
	private String favoriteLiferayVersion = "7";
	private String favoriteJsfVersion = "2.2";
	private Archetype selectedArchetype;
	private String selectedConfiguration;

	@ManagedProperty(name = "archetypeService", value = "#{archetypeService}")
	private ArchetypeService archetypeService;

	public String getFavoriteJsfVersion() {
		return favoriteJsfVersion;
	}

	public String getFavoriteLiferayVersion() {
		return favoriteLiferayVersion;
	}

	public String getFavoriteSuite() {
		return favoriteSuite;
	}

	public List<String> getJsfVersions() {
		return archetypeService.getJsfVersions();
	}

	public List<String> getLiferayVersions() {
		return archetypeService.getLiferayVersions();
	}

	public Archetype getSelectedArchetype() {

		if ((selectedArchetype == null) && (favoriteLiferayVersion != null) && (favoriteJsfVersion != null) &&
				(favoriteSuite != null)) {

			List<Archetype> archetypes = archetypeService.getArchetypes();

			for (Archetype archetype : archetypes) {

				if (favoriteLiferayVersion.equals(archetype.getLiferayVersion()) &&
						favoriteJsfVersion.equals(archetype.getJsfVersion()) &&
						favoriteSuite.equals(archetype.getSuite())) {
					selectedArchetype = archetype;

					break;
				}
			}

			if (selectedArchetype == null) {
				selectedArchetype = new UnsupportedArchetype();
			}
		}

		return selectedArchetype;
	}

	public String getSelectedConfiguration() {

		if ((selectedConfiguration == null) && (favoriteLiferayVersion != null) && (favoriteJsfVersion != null) &&
				(favoriteSuite != null)) {

			List<Suite> suites = getSuites();

			for (Suite suite : suites) {

				if (suite.getName().equals(favoriteSuite)) {
					selectedConfiguration = "Liferay Portal " + favoriteLiferayVersion + " + JSF " +
						favoriteJsfVersion + " + " + suite.getTitle();
				}
			}
		}

		return selectedConfiguration;
	}

	public List<Suite> getSuites() {
		return archetypeService.getSuites();
	}

	public void itemSelectionListener(AjaxBehaviorEvent ajaxBehaviorEvent) {

		if (getSelectedArchetype() instanceof UnsupportedArchetype) {
			FacesContextHelperUtil.addGlobalErrorMessage("You have selected an unsupported configuration.");
		}
	}

	public void reinitializeListener(ActionEvent actionEvent) {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		synchronized (facesContext) {
			ExternalContext externalContext = facesContext.getExternalContext();

			logger.debug("Re-initializing archetype service");
			@SuppressWarnings("unchecked")
			Map<String, String> initParameterMap = (Map<String, String>) externalContext.getInitParameterMap();
			archetypeService.init(initParameterMap);
			FacesContextHelperUtil.addGlobalInfoMessage(facesContext, "Re-Initialization Complete.");
		}
	}

	public void setArchetypeService(ArchetypeService archetypeService) {

		// Injected via @ManagedProperty annotation
		this.archetypeService = archetypeService;
	}

	public void setFavoriteJsfVersion(String favoriteJsfVersion) {
		this.favoriteJsfVersion = favoriteJsfVersion;
	}

	public void setFavoriteLiferayVersion(String liferayVersion) {
		this.favoriteLiferayVersion = liferayVersion;
	}

	public void setFavoriteSuite(String favoriteSuite) {
		this.favoriteSuite = favoriteSuite;
	}

	private static class UnsupportedArchetype extends Archetype {

		// serialVersionUID
		private static final long serialVersionUID = 755404467236586895L;

		public UnsupportedArchetype() {
			super(0, "", "", "", "", "N/A", null, "N/A");
		}
	}
}
