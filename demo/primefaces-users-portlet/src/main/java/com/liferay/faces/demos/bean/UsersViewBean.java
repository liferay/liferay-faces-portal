/**
 * Copyright (c) 2000-2020 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.demos.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;

import org.primefaces.event.data.FilterEvent;

import org.primefaces.model.FilterMeta;


/**
 * This class serves as a bean that remembers the rendered/non-rendered state of the list and form in the users.xhtml
 * Facelet view.
 *
 * @author  Neil Griffin
 */
@ManagedBean(name = "usersViewBean")
@ViewScoped
public class UsersViewBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 3791285914294079003L;

	// Private Data Members
	private boolean formRendered = false;
	private boolean workAroundPF3584DoubleRequest = false;
	private String emailAddressFilterValue;
	private String firstNameFilterValue;
	private String jobTitleFilterValue;
	private String lastNameFilterValue;
	private String middleNameFilterValue;
	private String screenNameFilterValue;

	public String getEmailAddressFilterValue() {
		return emailAddressFilterValue;
	}

	public String getFirstNameFilterValue() {
		return firstNameFilterValue;
	}

	public String getJobTitleFilterValue() {
		return jobTitleFilterValue;
	}

	public String getLastNameFilterValue() {
		return lastNameFilterValue;
	}

	public String getMiddleNameFilterValue() {
		return middleNameFilterValue;
	}

	public String getScreenNameFilterValue() {
		return screenNameFilterValue;
	}

	public boolean isFormRendered() {
		return formRendered;
	}

	public void setEmailAddressFilterValue(String emailAddressFilterValue) {
		this.emailAddressFilterValue = emailAddressFilterValue;
	}

	public void setFirstNameFilterValue(String firstNameFilterValue) {
		this.firstNameFilterValue = firstNameFilterValue;
	}

	public void setFormRendered(boolean formRendered) {
		this.formRendered = formRendered;
	}

	public void setJobTitleFilterValue(String jobTitleFilterValue) {
		this.jobTitleFilterValue = jobTitleFilterValue;
	}

	public void setLastNameFilterValue(String lastNameFilterValue) {
		this.lastNameFilterValue = lastNameFilterValue;
	}

	public void setMiddleNameFilterValue(String middleNameFilterValue) {
		this.middleNameFilterValue = middleNameFilterValue;
	}

	public void setScreenNameFilterValue(String screenNameFilterValue) {
		this.screenNameFilterValue = screenNameFilterValue;
	}

	public void workAroundPF3584DoubleRequest(FilterEvent filterEvent) {

		Map<String, FilterMeta> filters = filterEvent.getFilterBy();

		if (!workAroundPF3584DoubleRequest && filters.isEmpty()) {
			workAroundPF3584DoubleRequest = true;
		}
		else if (workAroundPF3584DoubleRequest) {
			workAroundPF3584DoubleRequest = false;

			// Work around https://github.com/primefaces/primefaces/issues/3584
			if (filters.isEmpty()) {

				FacesContext facesContext = FacesContext.getCurrentInstance();
				PartialViewContext partialViewContext = facesContext.getPartialViewContext();

				if (partialViewContext != null) {

					Collection<String> renderIds = partialViewContext.getRenderIds();
					renderIds.clear();

					Collection<String> executeIds = partialViewContext.getExecuteIds();
					executeIds.clear();
				}
			}
		}
	}
}
