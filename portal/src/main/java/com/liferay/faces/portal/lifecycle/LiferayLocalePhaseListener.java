/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.portal.lifecycle;

import java.util.Locale;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import com.liferay.faces.portal.context.LiferayPortletHelperUtil;
import com.liferay.faces.portal.context.PortletHelperUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.theme.ThemeDisplay;


/**
 * This class is a JSF PhaseListener that applies the current Liferay Portal theme display's locale to the UIViewRoot.
 * This is done after the restore view phase (to have the correct locale during the following phases) and before the
 * render response phase (in case the view root has changed in the invoke application phase).
 *
 * @author  Neil Griffin
 */
public class LiferayLocalePhaseListener implements PhaseListener {

	// serialVersionUID
	private static final long serialVersionUID = 3186309855861540422L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(LiferayLocalePhaseListener.class);

	@Override
	public void afterPhase(PhaseEvent phaseEvent) {

		if (phaseEvent.getPhaseId() == PhaseId.RESTORE_VIEW) {
			setLocale();
		}
	}

	@Override
	public void beforePhase(PhaseEvent phaseEvent) {

		if (phaseEvent.getPhaseId() == PhaseId.RENDER_RESPONSE) {
			setLocale();
		}
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

	private void setLocale() {

		try {

			// It's possible that the FacesServlet was invoked directly, and so this PhaseListener
			// needs to check to see if the request/response is part of a portlet environment before
			// proceeding further.
			FacesContext facesContext = FacesContext.getCurrentInstance();

			if (PortletHelperUtil.isPortletEnvironment(facesContext)) {
				ThemeDisplay themeDisplay = LiferayPortletHelperUtil.getThemeDisplay(facesContext);

				if (themeDisplay != null) {
					Locale locale = themeDisplay.getLocale();

					if (locale != null) {
						UIViewRoot viewRoot = facesContext.getViewRoot();

						if (viewRoot != null) {
							viewRoot.setLocale(locale);
						}
						else {
							logger.error("viewRoot is null!");
						}
					}
				}
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
