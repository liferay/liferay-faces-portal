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
package com.liferay.faces.portal.component.inputrichtext;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;


/**
 * @author  Kyle Stiemann
 */
/* package private */ abstract class AjaxUIInput extends UIInput {

	/* package private */ static boolean isAjaxRequest() {

		boolean ajaxRequest = false;
		FacesContext facesContext = FacesContext.getCurrentInstance();

		if (facesContext != null) {

			PartialViewContext partialViewContext = facesContext.getPartialViewContext();

			if (partialViewContext != null) {
				ajaxRequest = partialViewContext.isAjaxRequest();
			}
		}

		return ajaxRequest;
	}
}
