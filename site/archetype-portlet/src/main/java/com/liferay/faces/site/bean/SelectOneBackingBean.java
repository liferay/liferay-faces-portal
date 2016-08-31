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

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Vernon Singleton
 */
@ManagedBean
@RequestScoped
public class SelectOneBackingBean {

	private static final Logger logger = LoggerFactory.getLogger(SelectOneBackingBean.class);

	@ManagedProperty(name = "selectOneModelBean", value = "#{selectOneModelBean}")
	private SelectOneModelBean selectOneModelBean;

	public void setSelectOneModelBean(SelectOneModelBean selectOneModelBean) {
		this.selectOneModelBean = selectOneModelBean;
	}

	public void submit() {
		PhaseId phaseId = FacesContext.getCurrentInstance().getCurrentPhaseId();
		logger.info("submit: phaseId=[{0}] liferayVersion=[{1}] jsfVersion=[{2}] suite[{3}]", 
			phaseId.toString(), 
			selectOneModelBean.getFavoriteLiferayVersion(), 
			selectOneModelBean.getFavoriteJsfVersion(),
			selectOneModelBean.getFavoriteSuite()
		);
	}

	public void actionListener(ActionEvent actionEvent) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		PhaseId phaseId = facesContext.getCurrentPhaseId();
		logger.debug("actionListener: phaseId=[{0}]", phaseId.toString());

		String phaseName = phaseId.toString();
		FacesMessage facesMessage = new FacesMessage("The actionListener method was called during the " +
				phaseName + " phase of the JSF lifecycle.");
		facesContext.addMessage(null, facesMessage);
		
		logger.info("actionListener: resetting the archetypeService ...");
		selectOneModelBean.reset();
	}
}
