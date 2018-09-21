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
package com.liferay.faces.bridge.demos.bean;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;


/**
 * @author  Kyle Stiemann
 */
@ManagedBean
@ViewScoped
public class InputRichTextValidationBacking {

	private String validationEditorKey;

	public String getValidationEditorKey() {

		if (validationEditorKey == null) {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			Map<String, Object> applicationMap = externalContext.getApplicationMap();
			Set<String> validationEditorKeys = (Set<String>) applicationMap.get("validationEditorKeys");
			Iterator<String> iterator = validationEditorKeys.iterator();

			if (iterator.hasNext()) {
				validationEditorKey = iterator.next();
			}
		}

		return validationEditorKey;
	}

	public void setValidationEditorKey(String validationEditorKey) {
		this.validationEditorKey = validationEditorKey;
	}

}
