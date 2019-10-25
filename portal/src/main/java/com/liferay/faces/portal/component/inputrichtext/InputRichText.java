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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.FacesComponent;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.LengthValidator;

import com.liferay.faces.portal.component.inputrichtext.internal.FailedToCalculatePlainTextCharCountException;
import com.liferay.faces.portal.component.inputrichtext.internal.PlainTextCharUtil;
import com.liferay.faces.util.i18n.I18n;
import com.liferay.faces.util.i18n.I18nFactory;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.util.PropsUtil;


/**
 * @author  Neil Griffin
 */
@FacesComponent(value = InputRichText.COMPONENT_TYPE)
public class InputRichText extends InputRichTextBase implements ClientBehaviorHolder {

	// Private Constants
	private static final Collection<String> EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList("blur",
				"change", "valueChange", "focus"));
	private static final String FAILED_TO_CALCULATE_PLAIN_TEXT_CHAR_COUNT = "failed-to-calculate-plain-text-char-count";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(InputRichText.class);

	@Override
	public String getDefaultEventName() {
		return "valueChange";
	}

	@Override
	public Collection<String> getEventNames() {
		return EVENT_NAMES;
	}

	@Override
	protected void validateValue(FacesContext facesContext, Object newValue) {

		super.validateValue(facesContext, newValue);

		if (isValid()) {

			int minimum = getMinPlainTextChars();
			int maximum = getMaxPlainTextChars();

			if ((minimum > 0) || (maximum > 0)) {

				try {

					int length = 0;
					String richText = (String) newValue;

					if (richText != null) {

						String editorType = "";
						String editorKey = getEditorKey();

						if (editorKey != null) {
							editorType = PropsUtil.get(editorKey);
						}

						if (editorType.endsWith("_bbcode")) {
							length = PlainTextCharUtil.getBBCodePlainTextCharCount(richText);
						}
						else if (editorType.endsWith("_creole")) {
							length = PlainTextCharUtil.getCreolePlainTextCharCount(richText);
						}
						else if (!editorType.contains("_") || editorType.endsWith("_html")) {
							length = PlainTextCharUtil.getHTMLPlainTextCharCount(richText);
						}
						else {
							throw new FailedToCalculatePlainTextCharCountException(new UnsupportedOperationException(
									"Cannot calculate plain text characters for editor type: " + editorType));
						}
					}

					logger.debug("length=[{0}] minimum=[{1}] maximum=[{2}]", length, minimum, maximum);

					if ((minimum > 0) && (length < minimum)) {
						setInvalidPlainTextCount(facesContext, LengthValidator.MINIMUM_MESSAGE_ID, minimum);
					}

					if ((maximum > 0) && (length > maximum)) {
						setInvalidPlainTextCount(facesContext, LengthValidator.MAXIMUM_MESSAGE_ID, maximum);
					}
				}
				catch (FailedToCalculatePlainTextCharCountException e) {

					logger.error(e);
					setInvalidPlainTextCount(facesContext, FAILED_TO_CALCULATE_PLAIN_TEXT_CHAR_COUNT, null);
				}
			}
		}
	}

	private void setInvalidPlainTextCount(FacesContext facesContext, String validationMessageId, Integer integer) {

		Object label = getAttributes().get("label");
		Locale locale = facesContext.getViewRoot().getLocale();
		ExternalContext externalContext = facesContext.getExternalContext();
		I18n i18n = I18nFactory.getI18nInstance(externalContext);
		FacesMessage facesMessage;

		if (integer != null) {
			facesMessage = i18n.getFacesMessage(facesContext, locale, FacesMessage.SEVERITY_ERROR, validationMessageId,
					integer, label);
		}
		else {
			facesMessage = i18n.getFacesMessage(facesContext, locale, FacesMessage.SEVERITY_ERROR, validationMessageId,
					label);
		}

		facesContext.addMessage(getClientId(facesContext), facesMessage);
		setValid(false);
	}
}
