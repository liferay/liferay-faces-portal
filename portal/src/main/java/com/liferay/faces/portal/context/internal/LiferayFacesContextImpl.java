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
package com.liferay.faces.portal.context.internal;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.el.ELContext;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.application.ProjectStage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.render.RenderKit;
import javax.portlet.ActionResponse;
import javax.portlet.PortalContext;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import com.liferay.faces.portal.context.LiferayFacesContext;
import com.liferay.faces.portal.context.LiferayPortletHelper;
import com.liferay.faces.portal.context.LiferayPortletHelperUtil;
import com.liferay.faces.portal.context.PortletHelper;
import com.liferay.faces.portal.context.PortletHelperUtil;
import com.liferay.faces.portal.security.AuthorizationException;
import com.liferay.faces.util.client.Script;
import com.liferay.faces.util.context.FacesContextHelper;
import com.liferay.faces.util.context.FacesContextHelperUtil;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;


/**
 * @deprecated
 * @author      Neil Griffin
 */
@ManagedBean(name = "liferayFacesContext", eager = true)
@ApplicationScoped
@SuppressWarnings("deprecation")
@Deprecated
public class LiferayFacesContextImpl extends LiferayFacesContext implements Serializable {

	// serialVersionUID Note: This class implements Serializable only to avoid extraneous stacktraces from being thrown
	// by Mojarra.
	private static final long serialVersionUID = 905195020822157073L;

	public LiferayFacesContextImpl() {
		setInstance(this);
	}

	/**
	 * @see  FacesContextHelper#addComponentErrorMessage(String, String)
	 */
	@Override
	public void addComponentErrorMessage(String clientId, String messageId) {
		FacesContextHelperUtil.addComponentErrorMessage(clientId, messageId);
	}

	/**
	 * @see  FacesContextHelper#addComponentErrorMessage(String, String, Object...)
	 */
	@Override
	public void addComponentErrorMessage(String clientId, String messageId, Object... arguments) {
		FacesContextHelperUtil.addComponentErrorMessage(clientId, messageId, arguments);
	}

	/**
	 * @see  FacesContextHelper#addComponentErrorMessage(String, String)
	 */
	@Override
	public void addComponentErrorMessage(FacesContext facesContext, String clientId, String messageId) {
		FacesContextHelperUtil.addComponentErrorMessage(facesContext, clientId, messageId);
	}

	/**
	 * @see  FacesContextHelper#addComponentErrorMessage(String, String, Object...)
	 */
	@Override
	public void addComponentErrorMessage(FacesContext facesContext, String clientId, String messageId,
		Object... arguments) {
		FacesContextHelperUtil.addComponentErrorMessage(facesContext, clientId, messageId, arguments);
	}

	/**
	 * @see  FacesContextHelper#addComponentInfoMessage(String, String)
	 */
	@Override
	public void addComponentInfoMessage(String clientId, String messageId) {
		FacesContextHelperUtil.addComponentInfoMessage(clientId, messageId);
	}

	/**
	 * @see  FacesContextHelper#addComponentInfoMessage(String, String, Object...)
	 */
	@Override
	public void addComponentInfoMessage(String clientId, String messageId, Object... arguments) {
		FacesContextHelperUtil.addComponentInfoMessage(clientId, messageId, arguments);
	}

	/**
	 * @see  FacesContextHelper#addComponentInfoMessage(String, String)
	 */
	@Override
	public void addComponentInfoMessage(FacesContext facesContext, String clientId, String messageId) {
		FacesContextHelperUtil.addComponentInfoMessage(facesContext, clientId, messageId);
	}

	/**
	 * @see  FacesContextHelper#addComponentInfoMessage(String, String, Object...)
	 */
	@Override
	public void addComponentInfoMessage(FacesContext facesContext, String clientId, String messageId,
		Object... arguments) {
		FacesContextHelperUtil.addComponentInfoMessage(facesContext, clientId, messageId, arguments);
	}

	/**
	 * @see  FacesContextHelper#addGlobalErrorMessage(String)
	 */
	@Override
	public void addGlobalErrorMessage(String messageId) {
		FacesContextHelperUtil.addGlobalErrorMessage(messageId);
	}

	/**
	 * @see  FacesContextHelper#addGlobalErrorMessage(String, Object...)
	 */
	@Override
	public void addGlobalErrorMessage(String messageId, Object... arguments) {
		FacesContextHelperUtil.addGlobalErrorMessage(messageId, arguments);
	}

	/**
	 * @see  FacesContextHelper#addGlobalErrorMessage(String)
	 */
	@Override
	public void addGlobalErrorMessage(FacesContext facesContext, String messageId) {
		FacesContextHelperUtil.addGlobalErrorMessage(facesContext, messageId);
	}

	/**
	 * @see  FacesContextHelper#addGlobalErrorMessage(String, Object...)
	 */
	@Override
	public void addGlobalErrorMessage(FacesContext facesContext, String messageId, Object... arguments) {
		FacesContextHelperUtil.addGlobalErrorMessage(facesContext, messageId, arguments);
	}

	/**
	 * @see  FacesContextHelper#addGlobalInfoMessage(String)
	 */
	@Override
	public void addGlobalInfoMessage(String messageId) {
		FacesContextHelperUtil.addGlobalInfoMessage(messageId);
	}

	/**
	 * @see  FacesContextHelper#addGlobalInfoMessage(String, Object...)
	 */
	@Override
	public void addGlobalInfoMessage(String messageId, Object... arguments) {
		FacesContextHelperUtil.addGlobalInfoMessage(messageId, arguments);
	}

	/**
	 * @see  FacesContextHelper#addGlobalInfoMessage(String)
	 */
	@Override
	public void addGlobalInfoMessage(FacesContext facesContext, String messageId) {
		FacesContextHelperUtil.addGlobalInfoMessage(facesContext, messageId);
	}

	/**
	 * @see  FacesContextHelper#addGlobalInfoMessage(String, Object...)
	 */
	@Override
	public void addGlobalInfoMessage(FacesContext facesContext, String messageId, Object... arguments) {
		FacesContextHelperUtil.addGlobalInfoMessage(facesContext, messageId, arguments);
	}

	/**
	 * @see  FacesContextHelper#addGlobalSuccessInfoMessage()
	 */
	@Override
	public void addGlobalSuccessInfoMessage() {
		FacesContextHelperUtil.addGlobalSuccessInfoMessage();
	}

	/**
	 * @see  FacesContextHelper#addGlobalSuccessInfoMessage()
	 */
	@Override
	public void addGlobalSuccessInfoMessage(FacesContext facesContext) {
		FacesContextHelperUtil.addGlobalSuccessInfoMessage(facesContext);
	}

	/**
	 * @see  FacesContextHelper#addGlobalUnexpectedErrorMessage()
	 */
	@Override
	public void addGlobalUnexpectedErrorMessage() {
		FacesContextHelperUtil.addGlobalUnexpectedErrorMessage();
	}

	/**
	 * @see  FacesContextHelper#addGlobalUnexpectedErrorMessage()
	 */
	@Override
	public void addGlobalUnexpectedErrorMessage(FacesContext facesContext) {
		FacesContextHelperUtil.addGlobalUnexpectedErrorMessage(facesContext);
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public void addMessage(String clientId, FacesMessage facesMessage) {
		FacesContext.getCurrentInstance().addMessage(clientId, facesMessage);
	}

	/**
	 * @see  FacesContextHelper#addMessage(String, Severity, String)
	 */
	@Override
	public void addMessage(String clientId, Severity severity, String messageId) {
		FacesContextHelperUtil.addMessage(clientId, severity, messageId);
	}

	/**
	 * @see  FacesContextHelper#addMessage(String, Severity, String, Object...)
	 */
	@Override
	public void addMessage(String clientId, Severity severity, String messageId, Object... arguments) {
		FacesContextHelperUtil.addMessage(clientId, severity, messageId, arguments);
	}

	/**
	 * @see  FacesContextHelper#addMessage(String, Severity, String)
	 */
	@Override
	public void addMessage(FacesContext facesContext, String clientId, Severity severity, String messageId) {
		FacesContextHelperUtil.addMessage(facesContext, clientId, severity, messageId);
	}

	/**
	 * @see  FacesContextHelper#addMessage(String, Severity, String, Object...)
	 */
	@Override
	public void addMessage(FacesContext facesContext, String clientId, Severity severity, String messageId,
		Object... arguments) {
		FacesContextHelperUtil.addMessage(facesContext, clientId, severity, messageId, arguments);
	}

	@Override
	public void addScript(Script script) {
		FacesContextHelperUtil.addScript(script);
	}

	@Override
	public void addScript(String scriptString) {
		FacesContextHelperUtil.addScript(scriptString);
	}

	@Override
	public void addScript(FacesContext facesContext, Script script) {
		FacesContextHelperUtil.addScript(facesContext, script);
	}

	@Override
	public void addScript(FacesContext facesContext, String scriptString) {
		FacesContextHelperUtil.addScript(facesContext, scriptString);
	}

	/**
	 * @see  LiferayPortletHelper#checkUserPortletPermission(String)
	 */
	@Override
	public void checkUserPortletPermission(String actionId) throws AuthorizationException {
		LiferayPortletHelperUtil.checkUserPortletPermission(getFacesContext(), actionId);
	}

	/**
	 * @see  PortletHelper#createActionURL()
	 */
	@Override
	public PortletURL createActionURL() {
		return PortletHelperUtil.createActionURL(getFacesContext());
	}

	/**
	 * @see  PortletHelper#createRenderURL()
	 */
	@Override
	public PortletURL createRenderURL() {
		return PortletHelperUtil.createRenderURL(getFacesContext());
	}

	/**
	 * @see  PortletHelper#getActionResponse()
	 */
	@Override
	public ActionResponse getActionResponse() {
		return PortletHelperUtil.getActionResponse(getFacesContext());
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public Application getApplication() {
		return FacesContext.getCurrentInstance().getApplication();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public Map<Object, Object> getAttributes() {
		return FacesContext.getCurrentInstance().getAttributes();
	}

	@Override
	public int getBuildNumber() {
		return LiferayPortletHelperUtil.getBuildNumber(getFacesContext());
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public Iterator<String> getClientIdsWithMessages() {
		return FacesContext.getCurrentInstance().getClientIdsWithMessages();
	}

	/**
	 * @see  LiferayPortletHelper#getCompanyId()
	 */
	@Override
	public long getCompanyId() {
		return LiferayPortletHelperUtil.getCompanyId(getFacesContext());
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public PhaseId getCurrentPhaseId() {
		return FacesContext.getCurrentInstance().getCurrentPhaseId();
	}

	/**
	 * @see  LiferayPortletHelper#getDocumentLibraryURL()
	 */
	@Override
	public String getDocumentLibraryURL() {
		return LiferayPortletHelperUtil.getDocumentLibraryURL(getFacesContext());
	}

	/**
	 * @since  JSF 1.2
	 */
	@Override
	public ELContext getELContext() {
		return FacesContext.getCurrentInstance().getELContext();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public ExceptionHandler getExceptionHandler() {
		return FacesContext.getCurrentInstance().getExceptionHandler();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	/**
	 * @see  FacesContextHelper#getFacesContext()
	 */
	@Override
	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	/**
	 * @see  LiferayPortletHelper#getHostGroupId()
	 */
	@Override
	public long getHostGroupId() {
		return LiferayPortletHelperUtil.getHostGroupId(getFacesContext());
	}

	/**
	 * @see  LiferayPortletHelper#getImageGalleryURL()
	 */
	@Override
	public String getImageGalleryURL() {
		return LiferayPortletHelperUtil.getImageGalleryURL(getFacesContext());
	}

	/**
	 * @see  LiferayPortletHelper#getLayout()
	 */
	@Override
	public Layout getLayout() {
		return LiferayPortletHelperUtil.getLayout(getFacesContext());
	}

	/**
	 * @see  FacesContextHelper#getLocale()
	 */
	@Override
	public Locale getLocale() {
		Locale locale = null;

		// Try and get the current user's locale from Liferay, since they can override the locale with the Liferay
		// Language portlet.
		ThemeDisplay themeDisplay = getThemeDisplay();

		if (themeDisplay != null) {
			locale = themeDisplay.getLocale();
		}

		// If Liferay didn't return a locale, then try and get the locale from the JSF ViewRoot.
		if (locale == null) {
			locale = getViewRoot().getLocale();
		}

		// If the JSF ViewRoot didn't return a locale, then try and get it from the JSF Application.
		if (locale == null) {
			locale = getApplication().getDefaultLocale();
		}

		// Otherwise, if we couldn't determine the locale, just use the server's default value.
		if (locale == null) {
			locale = Locale.getDefault();
		}

		return locale;
	}

	/**
	 * @see  FacesContextHelper#getLocale()
	 */
	@Override
	public Locale getLocale(FacesContext facesContext) {
		Locale locale = null;

		// Try and get the current user's locale from Liferay, since they can override the locale with the Liferay
		// Language portlet.
		ThemeDisplay themeDisplay = getThemeDisplay();

		if (themeDisplay != null) {
			locale = themeDisplay.getLocale();
		}

		// If Liferay didn't return a locale, then try and get the locale from the JSF ViewRoot.
		if (locale == null) {
			locale = getViewRoot().getLocale();
		}

		// If the JSF ViewRoot didn't return a locale, then try and get it from the JSF Application.
		if (locale == null) {
			locale = getApplication().getDefaultLocale();
		}

		// Otherwise, if we couldn't determine the locale, just use the server's default value.
		if (locale == null) {
			locale = Locale.getDefault();
		}

		return locale;
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public Severity getMaximumSeverity() {
		return FacesContext.getCurrentInstance().getMaximumSeverity();
	}

	/**
	 * @see  FacesContextHelper#getMessage(String)
	 */
	@Override
	public String getMessage(String messageId) {
		return FacesContextHelperUtil.getMessage(messageId);
	}

	/**
	 * @see  FacesContextHelper#getMessage(String, Object...)
	 */
	@Override
	public String getMessage(String messageId, Object... arguments) {
		return FacesContextHelperUtil.getMessage(messageId, arguments);
	}

	/**
	 * @see  FacesContextHelper#getMessage(Locale, String)
	 */
	@Override
	public String getMessage(Locale locale, String messageId) {
		return FacesContextHelperUtil.getMessage(locale, messageId);
	}

	/**
	 * @see  FacesContextHelper#getMessage(String)
	 */
	@Override
	public String getMessage(FacesContext facesContext, String messageId) {
		return FacesContextHelperUtil.getMessage(facesContext, messageId);
	}

	/**
	 * @see  FacesContextHelper#getMessage(Locale, String, Object...)
	 */
	@Override
	public String getMessage(Locale locale, String messageId, Object... arguments) {
		return FacesContextHelperUtil.getMessage(locale, messageId, arguments);
	}

	/**
	 * @see  FacesContextHelper#getMessage(String, Object...)
	 */
	@Override
	public String getMessage(FacesContext facesContext, String messageId, Object... arguments) {
		return FacesContextHelperUtil.getMessage(facesContext, messageId, arguments);
	}

	/**
	 * @see  FacesContextHelper#getMessage(Locale, String)
	 */
	@Override
	public String getMessage(FacesContext facesContext, Locale locale, String messageId) {
		return FacesContextHelperUtil.getMessage(facesContext, locale, messageId);
	}

	/**
	 * @see  FacesContextHelper#getMessage(Locale, String, Object...)
	 */
	@Override
	public String getMessage(FacesContext facesContext, Locale locale, String messageId, Object... arguments) {
		return FacesContextHelperUtil.getMessage(facesContext, locale, messageId, arguments);
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public List<FacesMessage> getMessageList() {
		return FacesContext.getCurrentInstance().getMessageList();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public List<FacesMessage> getMessageList(String clientId) {
		return FacesContext.getCurrentInstance().getMessageList(clientId);
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public Iterator<FacesMessage> getMessages() {
		return FacesContext.getCurrentInstance().getMessages();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public Iterator<FacesMessage> getMessages(String clientId) {
		return FacesContext.getCurrentInstance().getMessages(clientId);
	}

	/**
	 * @see  FacesContextHelper#getNamespace()
	 */
	@Override
	public String getNamespace() {
		return FacesContextHelperUtil.getNamespace();
	}

	/**
	 * @see  FacesContextHelper#getNamespace()
	 */
	@Override
	public String getNamespace(FacesContext facesContext) {
		return FacesContextHelperUtil.getNamespace(facesContext);
	}

	/**
	 * @see  FacesContextHelper#getParentForm(UIComponent)
	 */
	@Override
	public UIForm getParentForm(UIComponent uiComponent) {
		return FacesContextHelperUtil.getParentForm(uiComponent);
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public PartialViewContext getPartialViewContext() {
		return FacesContext.getCurrentInstance().getPartialViewContext();
	}

	/**
	 * @see  LiferayPortletHelper#getPermissionChecker()
	 */
	@Override
	public PermissionChecker getPermissionChecker() {
		return LiferayPortletHelperUtil.getPermissionChecker(getFacesContext());
	}

	/**
	 * @see  LiferayPortletHelper#getPlid()
	 */
	@Override
	public long getPlid() {
		return LiferayPortletHelperUtil.getPlid(getFacesContext());
	}

	/**
	 * @see  PortletHelper#getPortalContext()
	 */
	@Override
	public PortalContext getPortalContext() {
		return PortletHelperUtil.getPortalContext(getFacesContext());
	}

	/**
	 * @see  LiferayPortletHelper#getPortalURL()
	 */
	@Override
	public String getPortalURL() {
		return LiferayPortletHelperUtil.getPortalURL(getFacesContext());
	}

	/**
	 * @see  LiferayPortletHelper#getPortlet()
	 */
	@Override
	public Portlet getPortlet() {
		return LiferayPortletHelperUtil.getPortlet(getFacesContext());
	}

	/**
	 * @see  PortletHelper#getPortletConfig()
	 */
	@Override
	public PortletConfig getPortletConfig() {
		return PortletHelperUtil.getPortletConfig(getFacesContext());
	}

	/**
	 * @see  PortletHelper#getPortletContext()
	 */
	@Override
	public PortletContext getPortletContext() {
		return PortletHelperUtil.getPortletContext(getFacesContext());
	}

	/**
	 * @see  PortletHelper#getPortletContextName()
	 */
	@Override
	public String getPortletContextName() {
		return PortletHelperUtil.getPortletContextName(getFacesContext());
	}

	/**
	 * @see  LiferayPortletHelper#getPortletInstanceId()
	 */
	@Override
	public String getPortletInstanceId() {
		return LiferayPortletHelperUtil.getPortletInstanceId(getFacesContext());
	}

	/**
	 * @see  PortletHelper#getPortletName()
	 */
	@Override
	public String getPortletName() {
		return PortletHelperUtil.getPortletName(getFacesContext());
	}

	/**
	 * @see  PortletHelper#getPortletPreference(String, Object)
	 */
	@Override
	public Object getPortletPreference(String preferenceName, Object defaultValue) {
		return PortletHelperUtil.getPortletPreference(getFacesContext(), preferenceName, defaultValue);
	}

	/**
	 * @see  PortletHelper#getPortletPreferenceAsBool(String, boolean)
	 */
	@Override
	public boolean getPortletPreferenceAsBool(String preferenceName, boolean defaultValue) {
		return PortletHelperUtil.getPortletPreferenceAsBool(getFacesContext(), preferenceName, defaultValue);
	}

	/**
	 * @see  PortletHelper#getPortletPreferenceAsInt(String, int)
	 */
	@Override
	public int getPortletPreferenceAsInt(String preferenceName, int defaultValue) {
		return PortletHelperUtil.getPortletPreferenceAsInt(getFacesContext(), preferenceName, defaultValue);
	}

	/**
	 * @see  PortletHelper#getPortletPreferenceAsShort(String, short)
	 */
	@Override
	public short getPortletPreferenceAsShort(String preferenceName, short defaultValue) {
		return PortletHelperUtil.getPortletPreferenceAsShort(getFacesContext(), preferenceName, defaultValue);
	}

	/**
	 * @see  PortletHelper#getPortletPreferenceAsString(String, String)
	 */
	@Override
	public String getPortletPreferenceAsString(String preferenceName, String defaultValue) {
		return PortletHelperUtil.getPortletPreferenceAsString(getFacesContext(), preferenceName, defaultValue);
	}

	/**
	 * @see  PortletHelper#getPortletPreferences()
	 */
	@Override
	public PortletPreferences getPortletPreferences() {
		return PortletHelperUtil.getPortletPreferences(getFacesContext());
	}

	/**
	 * @see  PortletHelper#getPortletRenderRequest()
	 */
	@Override
	public RenderRequest getPortletRenderRequest() {
		return PortletHelperUtil.getPortletRenderRequest(getFacesContext());
	}

	/**
	 * @see  PortletHelper#getPortletRenderResponse()
	 */
	@Override
	public RenderResponse getPortletRenderResponse() {
		return PortletHelperUtil.getPortletRenderResponse(getFacesContext());
	}

	/**
	 * @see  PortletHelper#getPortletRequest()
	 */
	@Override
	public PortletRequest getPortletRequest() {
		return PortletHelperUtil.getPortletRequest(getFacesContext());
	}

	/**
	 * @see  PortletHelper#getPortletResponse()
	 */
	@Override
	public PortletResponse getPortletResponse() {
		return PortletHelperUtil.getPortletResponse(getFacesContext());
	}

	/**
	 * @see  LiferayPortletHelper#getPortletRootId()
	 */
	@Override
	public String getPortletRootId() {
		return LiferayPortletHelperUtil.getPortletRootId(getFacesContext());
	}

	/**
	 * @see  PortletHelper#getPortletSession()
	 */
	@Override
	public PortletSession getPortletSession() {
		return PortletHelperUtil.getPortletSession(getFacesContext());
	}

	/**
	 * @see  PortletHelper#getRemoteUser()
	 */
	@Override
	public String getRemoteUser() {
		return PortletHelperUtil.getRemoteUser(getFacesContext());
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public RenderKit getRenderKit() {
		return FacesContext.getCurrentInstance().getRenderKit();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public boolean getRenderResponse() {
		return FacesContext.getCurrentInstance().getRenderResponse();
	}

	/**
	 * @see  FacesContextHelper#getRequestAttribute(String)
	 */
	@Override
	public Object getRequestAttribute(String name) {
		return FacesContextHelperUtil.getRequestAttribute(name);
	}

	/**
	 * @see  FacesContextHelper#getRequestAttribute(String)
	 */
	@Override
	public Object getRequestAttribute(FacesContext facesContext, String name) {
		return FacesContextHelperUtil.getRequestAttribute(facesContext, name);
	}

	/**
	 * @see  FacesContextHelper#getRequestContextPath()
	 */
	@Override
	public String getRequestContextPath() {
		return FacesContextHelperUtil.getRequestContextPath();
	}

	/**
	 * @see  FacesContextHelper#getRequestContextPath()
	 */
	@Override
	public String getRequestContextPath(FacesContext facesContext) {
		return FacesContextHelperUtil.getRequestContextPath(facesContext);
	}

	/**
	 * @see  FacesContextHelper#getRequestParameter(String)
	 */
	@Override
	public String getRequestParameter(String name) {
		return FacesContextHelperUtil.getRequestParameter(name);
	}

	/**
	 * @see  FacesContextHelper#getRequestParameter(String)
	 */
	@Override
	public String getRequestParameter(FacesContext facesContext, String name) {
		return FacesContextHelperUtil.getRequestParameter(facesContext, name);
	}

	/**
	 * @see  FacesContextHelper#getRequestParameterAsBool(String, boolean)
	 */
	@Override
	public boolean getRequestParameterAsBool(String name, boolean defaultValue) {
		return FacesContextHelperUtil.getRequestParameterAsBool(name, defaultValue);
	}

	/**
	 * @see  FacesContextHelper#getRequestParameterAsBool(String, boolean)
	 */
	@Override
	public boolean getRequestParameterAsBool(FacesContext facesContext, String name, boolean defaultValue) {
		return FacesContextHelperUtil.getRequestParameterAsBool(facesContext, name, defaultValue);
	}

	/**
	 * @see  FacesContextHelper#getRequestParameterAsInt(String, int)
	 */
	@Override
	public int getRequestParameterAsInt(String name, int defaultValue) {
		return FacesContextHelperUtil.getRequestParameterAsInt(name, defaultValue);
	}

	/**
	 * @see  FacesContextHelper#getRequestParameterAsInt(String, int)
	 */
	@Override
	public int getRequestParameterAsInt(FacesContext facesContext, String name, int defaultValue) {
		return FacesContextHelperUtil.getRequestParameterAsInt(facesContext, name, defaultValue);
	}

	/**
	 * @see  FacesContextHelper#getRequestParameterAsLong(String, long)
	 */
	@Override
	public long getRequestParameterAsLong(String name, long defaultValue) {
		return FacesContextHelperUtil.getRequestParameterAsLong(name, defaultValue);
	}

	/**
	 * @see  FacesContextHelper#getRequestParameterAsLong(String, long)
	 */
	@Override
	public long getRequestParameterAsLong(FacesContext facesContext, String name, long defaultValue) {
		return FacesContextHelperUtil.getRequestParameterAsLong(facesContext, name, defaultValue);
	}

	/**
	 * @see  FacesContextHelper#getRequestParameterFromMap(String)
	 */
	@Override
	public String getRequestParameterFromMap(String name) {
		return FacesContextHelperUtil.getRequestParameterFromMap(name);
	}

	/**
	 * @see  FacesContextHelper#getRequestParameterFromMap(String)
	 */
	@Override
	public String getRequestParameterFromMap(FacesContext facesContext, String name) {
		return FacesContextHelperUtil.getRequestParameterFromMap(facesContext, name);
	}

	/**
	 * @see  FacesContextHelper#getRequestParameterMap()
	 */
	@Override
	public Map<String, String> getRequestParameterMap() {
		return FacesContextHelperUtil.getRequestParameterMap();
	}

	/**
	 * @see  FacesContextHelper#getRequestParameterMap()
	 */
	@Override
	public Map<String, String> getRequestParameterMap(FacesContext facesContext) {
		return FacesContextHelperUtil.getRequestParameterMap(facesContext);
	}

	/**
	 * @see  FacesContextHelper#getRequestQueryString()
	 */
	@Override
	public String getRequestQueryString() {
		return FacesContextHelperUtil.getRequestQueryString();
	}

	/**
	 * @see  FacesContextHelper#getRequestQueryString()
	 */
	@Override
	public String getRequestQueryString(FacesContext facesContext) {
		return FacesContextHelperUtil.getRequestQueryString(facesContext);
	}

	/**
	 * @see  FacesContextHelper#getRequestQueryStringParameter(String)
	 */
	@Override
	public String getRequestQueryStringParameter(String name) {
		return FacesContextHelperUtil.getRequestQueryStringParameter(name);
	}

	/**
	 * @see  FacesContextHelper#getRequestQueryStringParameter(String)
	 */
	@Override
	public String getRequestQueryStringParameter(FacesContext facesContext, String name) {
		return FacesContextHelperUtil.getRequestQueryStringParameter(facesContext, name);
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public boolean getResponseComplete() {
		return FacesContext.getCurrentInstance().getRenderResponse();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public ResponseStream getResponseStream() {
		return FacesContext.getCurrentInstance().getResponseStream();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public ResponseWriter getResponseWriter() {
		return FacesContext.getCurrentInstance().getResponseWriter();
	}

	/**
	 * @see  LiferayPortletHelper#getScopeGroup()
	 */
	@Override
	public Group getScopeGroup() {
		return LiferayPortletHelperUtil.getScopeGroup(getFacesContext());
	}

	/**
	 * @see  LiferayPortletHelper#getScopeGroupId()
	 */
	@Override
	public long getScopeGroupId() {
		return LiferayPortletHelperUtil.getScopeGroupId(getFacesContext());
	}

	/**
	 * @see  LiferayPortletHelper#getScopeGroupUser()
	 */
	@Override
	public User getScopeGroupUser() {
		return LiferayPortletHelperUtil.getScopeGroupUser(getFacesContext());
	}

	@Override
	public List<Script> getScripts() {
		return FacesContextHelperUtil.getScripts();
	}

	@Override
	public List<Script> getScripts(FacesContext facesContext) {
		return FacesContextHelperUtil.getScripts(facesContext);
	}

	/**
	 * @see  LiferayPortletHelper#getServiceContext()
	 */
	@Override
	public ServiceContext getServiceContext() {
		return LiferayPortletHelperUtil.getServiceContext(getFacesContext());
	}

	/**
	 * @see  FacesContextHelper#getSession(boolean)
	 */
	@Override
	public Object getSession(boolean create) {
		return FacesContextHelperUtil.getSession(create);
	}

	/**
	 * @see  FacesContextHelper#getSession(boolean)
	 */
	@Override
	public Object getSession(FacesContext facesContext, boolean create) {
		return FacesContextHelperUtil.getSession(facesContext, create);
	}

	/**
	 * @see  FacesContextHelper#getSessionAttribute(String)
	 */
	@Override
	public Object getSessionAttribute(String name) {
		return FacesContextHelperUtil.getSessionAttribute(name);
	}

	/**
	 * @see  FacesContextHelper#getSessionAttribute(String)
	 */
	@Override
	public Object getSessionAttribute(FacesContext facesContext, String name) {
		return FacesContextHelperUtil.getSessionAttribute(facesContext, name);
	}

	/**
	 * @see  PortletHelper#getSessionSharedAttribute(String)
	 */
	@Override
	public Object getSessionSharedAttribute(String name) {
		return PortletHelperUtil.getSessionSharedAttribute(getFacesContext(), name);
	}

	/**
	 * @see  LiferayPortletHelper#getTheme()
	 */
	@Override
	public Theme getTheme() {
		return LiferayPortletHelperUtil.getTheme(getFacesContext());
	}

	/**
	 * @see  LiferayPortletHelper#getThemeDisplay()
	 */
	@Override
	public ThemeDisplay getThemeDisplay() {
		return LiferayPortletHelperUtil.getThemeDisplay(getFacesContext());
	}

	/**
	 * @see  LiferayPortletHelper#getThemeImagesURL()
	 */
	@Override
	public String getThemeImagesURL() {
		return LiferayPortletHelperUtil.getThemeImagesURL(getFacesContext());
	}

	/**
	 * @see  LiferayPortletHelper#getUser()
	 */
	@Override
	public User getUser() {
		return LiferayPortletHelperUtil.getUser(getFacesContext());
	}

	/**
	 * @see  LiferayPortletHelper#getUserId()
	 */
	@Override
	public long getUserId() {
		return LiferayPortletHelperUtil.getUserId(getFacesContext());
	}

	/**
	 * @see  LiferayPortletHelper#getUserRoles()
	 */
	@Override
	public List<Role> getUserRoles() throws SystemException {
		return LiferayPortletHelperUtil.getUserRoles(getFacesContext());
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public UIViewRoot getViewRoot() {
		return FacesContext.getCurrentInstance().getViewRoot();
	}

	/**
	 * @see  PortletHelper#getWindowState()
	 */
	@Override
	public WindowState getWindowState() {
		return PortletHelperUtil.getWindowState(getFacesContext());
	}

	/**
	 * @see  PortletHelper#isPortletEnvironment()
	 */
	@Override
	public boolean isPortletEnvironment() {
		return PortletHelperUtil.isPortletEnvironment(getFacesContext());
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public boolean isPostback() {
		return FacesContext.getCurrentInstance().isPostback();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public boolean isProcessingEvents() {
		return FacesContext.getCurrentInstance().isProcessingEvents();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public boolean isProjectStage(ProjectStage stage) {
		return FacesContext.getCurrentInstance().isProjectStage(stage);
	}

	/**
	 * @since  JSF 2.1
	 */
	@Override
	public boolean isReleased() {
		return FacesContext.getCurrentInstance().isReleased();
	}

	/**
	 * @see  PortletHelper#isUserInRole(String)
	 */
	@Override
	public boolean isUserInRole(String roleName) {
		return PortletHelperUtil.isUserInRole(getFacesContext(), roleName);
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public boolean isValidationFailed() {
		return FacesContext.getCurrentInstance().isValidationFailed();
	}

	/**
	 * @see  PortletHelper#isWindowMaximized()
	 */
	@Override
	public boolean isWindowMaximized() {
		return PortletHelperUtil.isWindowMaximized(getFacesContext());
	}

	/**
	 * @see  PortletHelper#isWindowNormal()
	 */
	@Override
	public boolean isWindowNormal() {
		return PortletHelperUtil.isWindowNormal(getFacesContext());
	}

	/**
	 * @see  FacesContextHelper#matchComponentInHierarchy(UIComponent, String)
	 */
	@Override
	public UIComponent matchComponentInHierarchy(UIComponent parent, String partialClientId) {
		return FacesContextHelperUtil.matchComponentInHierarchy(parent, partialClientId);
	}

	/**
	 * @see  FacesContextHelper#matchComponentInHierarchy(UIComponent, String)
	 */
	@Override
	public UIComponent matchComponentInHierarchy(FacesContext facesContext, UIComponent parent,
		String partialClientId) {
		return FacesContextHelperUtil.matchComponentInHierarchy(facesContext, parent, partialClientId);
	}

	/**
	 * @see  FacesContextHelper#matchComponentInViewRoot(String)
	 */
	@Override
	public UIComponent matchComponentInViewRoot(String partialClientId) {
		return FacesContextHelperUtil.matchComponentInViewRoot(partialClientId);
	}

	/**
	 * @see  FacesContextHelper#matchComponentInViewRoot(String)
	 */
	@Override
	public UIComponent matchComponentInViewRoot(FacesContext facesContext, String partialClientId) {
		return FacesContextHelperUtil.matchComponentInViewRoot(facesContext, partialClientId);
	}

	/**
	 * @see  FacesContextHelper#navigate(String, String)
	 */
	@Override
	public void navigate(String fromAction, String outcome) {
		FacesContextHelperUtil.navigate(fromAction, outcome);
	}

	/**
	 * @see  FacesContextHelper#navigate(String, String)
	 */
	@Override
	public void navigate(FacesContext facesContext, String fromAction, String outcome) {
		FacesContextHelperUtil.navigate(facesContext, fromAction, outcome);
	}

	/**
	 * @see  FacesContextHelper#navigateTo(String)
	 */
	@Override
	public void navigateTo(String outcome) {
		FacesContextHelperUtil.navigateTo(outcome);
	}

	/**
	 * @see  FacesContextHelper#navigateTo(String)
	 */
	@Override
	public void navigateTo(FacesContext facesContext, String outcome) {
		FacesContextHelperUtil.navigateTo(facesContext, outcome);
	}

	/**
	 * @see  FacesContextHelper#recreateComponentTree()
	 */
	@Override
	public void recreateComponentTree() {
		FacesContextHelperUtil.recreateComponentTree();
	}

	/**
	 * @see  FacesContextHelper#recreateComponentTree()
	 */
	@Override
	public void recreateComponentTree(FacesContext facesContext) {
		FacesContextHelperUtil.recreateComponentTree(facesContext);
	}

	/**
	 * @see  FacesContextHelper#registerPhaseListener(PhaseListener)
	 */
	@Override
	public void registerPhaseListener(PhaseListener phaseListener) {
		FacesContextHelperUtil.registerPhaseListener(phaseListener);
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public void release() {
		FacesContext.getCurrentInstance().release();
	}

	/**
	 * @see  FacesContextHelper#removeChildrenFromComponentTree(String)
	 */
	@Override
	public void removeChildrenFromComponentTree(String clientId) {
		FacesContextHelperUtil.removeChildrenFromComponentTree(clientId);
	}

	/**
	 * @see  FacesContextHelper#removeChildrenFromComponentTree(String)
	 */
	@Override
	public void removeChildrenFromComponentTree(FacesContext facesContext, String clientId) {
		FacesContextHelperUtil.removeChildrenFromComponentTree(facesContext, clientId);
	}

	/**
	 * @see  FacesContextHelper#removeMessages(String)
	 */
	@Override
	public void removeMessages(String clientId) {
		FacesContextHelperUtil.removeMessages(clientId);
	}

	/**
	 * @see  FacesContextHelper#removeMessages(String)
	 */
	@Override
	public void removeMessages(FacesContext facesContext, String clientId) {
		FacesContextHelperUtil.removeMessages(facesContext, clientId);
	}

	/**
	 * @see  FacesContextHelper#removeMessagesForImmediateComponents()
	 */
	@Override
	public void removeMessagesForImmediateComponents() {
		FacesContextHelperUtil.removeMessagesForImmediateComponents();
	}

	/**
	 * @see  FacesContextHelper#removeMessagesForImmediateComponents(UIComponent)
	 */
	@Override
	public void removeMessagesForImmediateComponents(UIComponent uiComponent) {
		FacesContextHelperUtil.removeMessagesForImmediateComponents(uiComponent);
	}

	/**
	 * @see  FacesContextHelper#removeMessagesForImmediateComponents()
	 */
	@Override
	public void removeMessagesForImmediateComponents(FacesContext facesContext) {
		FacesContextHelperUtil.removeMessagesForImmediateComponents(facesContext);
	}

	/**
	 * @see  FacesContextHelper#removeMessagesForImmediateComponents(UIComponent)
	 */
	@Override
	public void removeMessagesForImmediateComponents(FacesContext facesContext, UIComponent uiComponent) {
		FacesContextHelperUtil.removeMessagesForImmediateComponents(facesContext, uiComponent);
	}

	/**
	 * @see  FacesContextHelper#removeParentFormFromComponentTree(UIComponent)
	 */
	@Override
	public void removeParentFormFromComponentTree(UIComponent uiComponent) {
		FacesContextHelperUtil.removeParentFormFromComponentTree(uiComponent);
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public void renderResponse() {
		FacesContext.getCurrentInstance().renderResponse();
	}

	/**
	 * @see  FacesContextHelper#resetView()
	 */
	@Override
	public void resetView() {
		FacesContextHelperUtil.resetView();
	}

	/**
	 * @see  FacesContextHelper#resetView(boolean)
	 */
	@Override
	public void resetView(boolean renderResponse) {
		FacesContextHelperUtil.resetView();
	}

	/**
	 * @see  FacesContextHelper#resetView()
	 */
	@Override
	public void resetView(FacesContext facesContext) {
		FacesContextHelperUtil.resetView(facesContext);
	}

	/**
	 * @see  FacesContextHelper#resetView(boolean)
	 */
	@Override
	public void resetView(FacesContext facesContext, boolean renderResponse) {
		FacesContextHelperUtil.resetView(facesContext);
	}

	/**
	 * @see  FacesContextHelper#resolveExpression(String)
	 */
	@Override
	public Object resolveExpression(String elExpression) {
		return FacesContextHelperUtil.resolveExpression(elExpression);
	}

	/**
	 * @see  FacesContextHelper#resolveExpression(String)
	 */
	@Override
	public Object resolveExpression(FacesContext facesContext, String elExpression) {
		return FacesContextHelperUtil.resolveExpression(facesContext, elExpression);
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public void responseComplete() {
		FacesContext.getCurrentInstance().responseComplete();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public void setCurrentPhaseId(PhaseId currentPhaseId) {
		FacesContext.getCurrentInstance().setCurrentPhaseId(currentPhaseId);
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		FacesContext.getCurrentInstance().setExceptionHandler(exceptionHandler);
	}

	/**
	 * @see  PortletHelper#setPortletMode(PortletMode)
	 */
	@Override
	public void setPortletMode(PortletMode portletMode) {
		PortletHelperUtil.setPortletMode(getFacesContext(), portletMode);
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public void setProcessingEvents(boolean processingEvents) {
		FacesContext.getCurrentInstance().setProcessingEvents(processingEvents);
	}

	/**
	 * @see  FacesContextHelper#setRequestAttribute(String, Object)
	 */
	@Override
	public void setRequestAttribute(String name, Object value) {
		FacesContextHelperUtil.setRequestAttribute(name, value);
	}

	/**
	 * @see  FacesContextHelper#setRequestAttribute(String, Object)
	 */
	@Override
	public void setRequestAttribute(FacesContext facesContext, String name, Object value) {
		FacesContextHelperUtil.setRequestAttribute(facesContext, name, value);
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public void setResponseStream(ResponseStream responseStream) {
		FacesContext.getCurrentInstance().setResponseStream(responseStream);
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public void setResponseWriter(ResponseWriter responseWriter) {
		FacesContext.getCurrentInstance().setResponseWriter(responseWriter);
	}

	/**
	 * @see  FacesContextHelper#setSessionAttribute(String, Object)
	 */
	@Override
	public void setSessionAttribute(String name, Object value) {
		FacesContextHelperUtil.setSessionAttribute(name, value);
	}

	/**
	 * @see  FacesContextHelper#setSessionAttribute(String, Object)
	 */
	@Override
	public void setSessionAttribute(FacesContext facesContext, String name, Object value) {
		FacesContextHelperUtil.setSessionAttribute(facesContext, name, value);
	}

	/**
	 * @see  PortletHelper#setSessionSharedAttribute(String, Object)
	 */
	@Override
	public void setSessionSharedAttribute(String name, Object value) {
		PortletHelperUtil.setSessionSharedAttribute(getFacesContext(), name, value);
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public void setViewRoot(UIViewRoot viewRoot) {
		FacesContext.getCurrentInstance().setViewRoot(viewRoot);
	}

	/**
	 * @see  PortletHelper#setWindowState(WindowState)
	 */
	@Override
	public void setWindowState(WindowState windowState) {
		PortletHelperUtil.setWindowState(getFacesContext(), windowState);
	}

	/**
	 * @see  PortletHelper#setWindowStateToMaximized()
	 */
	@Override
	public void setWindowStateToMaximized() {
		PortletHelperUtil.setWindowStateToMaximized(getFacesContext());
	}

	/**
	 * @see  PortletHelper#setWindowStateToNormal()
	 */
	@Override
	public void setWindowStateToNormal() {
		PortletHelperUtil.setWindowStateToNormal(getFacesContext());
	}

	/**
	 * @see  LiferayPortletHelper#userHasPortletPermission(String)
	 */
	@Override
	public boolean userHasPortletPermission(String actionId) {
		return LiferayPortletHelperUtil.userHasPortletPermission(getFacesContext(), actionId);
	}

	/**
	 * @see  LiferayPortletHelper#userHasRole(String)
	 */
	@Override
	public boolean userHasRole(String roleName) {
		return LiferayPortletHelperUtil.userHasRole(getFacesContext(), roleName);
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public void validationFailed() {
		FacesContext.getCurrentInstance().validationFailed();
	}
}
