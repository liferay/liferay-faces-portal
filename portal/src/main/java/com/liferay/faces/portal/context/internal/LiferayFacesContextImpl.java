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
import com.liferay.faces.util.context.FacesContextHelper;
import com.liferay.faces.util.context.FacesContextHelperUtil;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;


/**
 * @author  Neil Griffin
 */
@ManagedBean(name = "liferayFacesContext", eager = true)
@ApplicationScoped
@SuppressWarnings("deprecation")
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
	 * @see  FacesContextHelper#addComponentErrorMessage(String, String, Object)
	 */
	@Override
	public void addComponentErrorMessage(String clientId, String messageId, Object argument) {
		FacesContextHelperUtil.addComponentErrorMessage(clientId, messageId, argument);
	}

	/**
	 * @see  FacesContextHelper#addComponentErrorMessage(String, String, Object...)
	 */
	@Override
	public void addComponentErrorMessage(String clientId, String messageId, Object... arguments) {
		FacesContextHelperUtil.addComponentErrorMessage(clientId, messageId, arguments);
	}

	/**
	 * @see  FacesContextHelper#addComponentInfoMessage(String, String)
	 */
	@Override
	public void addComponentInfoMessage(String clientId, String messageId) {
		FacesContextHelperUtil.addComponentInfoMessage(clientId, messageId);
	}

	/**
	 * @see  FacesContextHelper#addComponentInfoMessage(String, String, Object)
	 */
	@Override
	public void addComponentInfoMessage(String clientId, String messageId, Object argument) {
		FacesContextHelperUtil.addComponentInfoMessage(clientId, messageId, argument);
	}

	/**
	 * @see  FacesContextHelper#addComponentInfoMessage(String, String, Object...)
	 */
	@Override
	public void addComponentInfoMessage(String clientId, String messageId, Object... arguments) {
		FacesContextHelperUtil.addComponentInfoMessage(clientId, messageId, arguments);
	}

	/**
	 * @see  FacesContextHelper#addGlobalErrorMessage(String)
	 */
	@Override
	public void addGlobalErrorMessage(String messageId) {
		FacesContextHelperUtil.addGlobalErrorMessage(messageId);
	}

	/**
	 * @see  FacesContextHelper#addGlobalErrorMessage(String, Object)
	 */
	@Override
	public void addGlobalErrorMessage(String messageId, Object argument) {
		FacesContextHelperUtil.addGlobalErrorMessage(messageId, argument);
	}

	/**
	 * @see  FacesContextHelper#addGlobalErrorMessage(String, Object...)
	 */
	@Override
	public void addGlobalErrorMessage(String messageId, Object... arguments) {
		FacesContextHelperUtil.addGlobalErrorMessage(messageId, arguments);
	}

	/**
	 * @see  FacesContextHelper#addGlobalInfoMessage(String)
	 */
	@Override
	public void addGlobalInfoMessage(String messageId) {
		FacesContextHelperUtil.addGlobalInfoMessage(messageId);
	}

	/**
	 * @see  FacesContextHelper#addGlobalInfoMessage(String, Object)
	 */
	@Override
	public void addGlobalInfoMessage(String messageId, Object argument) {
		FacesContextHelperUtil.addGlobalInfoMessage(messageId, argument);
	}

	/**
	 * @see  FacesContextHelper#addGlobalInfoMessage(String, Object...)
	 */
	@Override
	public void addGlobalInfoMessage(String messageId, Object... arguments) {
		FacesContextHelperUtil.addGlobalInfoMessage(messageId, arguments);
	}

	/**
	 * @see  FacesContextHelper#addGlobalSuccessInfoMessage()
	 */
	@Override
	public void addGlobalSuccessInfoMessage() {
		FacesContextHelperUtil.addGlobalSuccessInfoMessage();
	}

	/**
	 * @see  FacesContextHelper#addGlobalUnexpectedErrorMessage()
	 */
	@Override
	public void addGlobalUnexpectedErrorMessage() {
		FacesContextHelperUtil.addGlobalUnexpectedErrorMessage();
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
	 * @see  FacesContextHelper#addMessage(String, Severity, String, Object)
	 */
	@Override
	public void addMessage(String clientId, Severity severity, String messageId, Object argument) {
		FacesContextHelperUtil.addMessage(clientId, severity, messageId, argument);

	}

	/**
	 * @see  FacesContextHelper#addMessage(String, Severity, String, Object...)
	 */
	@Override
	public void addMessage(String clientId, Severity severity, String messageId, Object... arguments) {
		FacesContextHelperUtil.addMessage(clientId, severity, messageId, arguments);
	}

	/**
	 * @see  LiferayPortletHelper#checkUserPortletPermission(String)
	 */
	@Override
	public void checkUserPortletPermission(String actionId) throws AuthorizationException {
		LiferayPortletHelperUtil.checkUserPortletPermission(actionId);
	}

	/**
	 * @see  PortletHelper#createActionURL()
	 */
	@Override
	public PortletURL createActionURL() {
		return PortletHelperUtil.createActionURL();
	}

	/**
	 * @see  PortletHelper#createRenderURL()
	 */
	@Override
	public PortletURL createRenderURL() {
		return PortletHelperUtil.createRenderURL();
	}

	/**
	 * @see  FacesContextHelper#matchComponentInHierarchy(UIComponent, String)
	 */
	@Override
	public UIComponent matchComponentInHierarchy(UIComponent parent, String partialClientId) {
		return FacesContextHelperUtil.matchComponentInHierarchy(parent, partialClientId);
	}

	/**
	 * @see  FacesContextHelper#matchComponentInViewRoot(String)
	 */
	@Override
	public UIComponent matchComponentInViewRoot(String partialClientId) {
		return FacesContextHelperUtil.matchComponentInViewRoot(partialClientId);
	}

	/**
	 * @see  FacesContextHelper#navigate(String, String)
	 */
	@Override
	public void navigate(String fromAction, String outcome) {
		FacesContextHelperUtil.navigate(fromAction, outcome);
	}

	/**
	 * @see  FacesContextHelper#navigateTo(String)
	 */
	@Override
	public void navigateTo(String outcome) {
		FacesContextHelperUtil.navigateTo(outcome);
	}

	/**
	 * @see  FacesContextHelper#recreateComponentTree()
	 */
	@Override
	public void recreateComponentTree() {
		FacesContextHelperUtil.recreateComponentTree();
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
	 * @see  FacesContextHelper#removeMessages(String)
	 */
	@Override
	public void removeMessages(String clientId) {
		FacesContextHelperUtil.removeMessages(clientId);
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
	 * @see  FacesContextHelper#resolveExpression(String)
	 */
	@Override
	public Object resolveExpression(String elExpression) {
		return FacesContextHelperUtil.resolveExpression(elExpression);
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public void responseComplete() {
		FacesContext.getCurrentInstance().responseComplete();
	}

	/**
	 * @see  LiferayPortletHelper#userHasPortletPermission(String)
	 */
	@Override
	public boolean userHasPortletPermission(String actionId) {
		return LiferayPortletHelperUtil.userHasPortletPermission(actionId);
	}

	/**
	 * @see  LiferayPortletHelper#userHasRole(String)
	 */
	@Override
	public boolean userHasRole(String roleName) {
		return LiferayPortletHelperUtil.userHasRole(roleName);
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public void validationFailed() {
		FacesContext.getCurrentInstance().validationFailed();
	}

	/**
	 * @see  PortletHelper#getActionResponse()
	 */
	@Override
	public ActionResponse getActionResponse() {
		return PortletHelperUtil.getActionResponse();
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
		return LiferayPortletHelperUtil.getBuildNumber();
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
		return LiferayPortletHelperUtil.getCompanyId();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public PhaseId getCurrentPhaseId() {
		return FacesContext.getCurrentInstance().getCurrentPhaseId();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public void setCurrentPhaseId(PhaseId currentPhaseId) {
		FacesContext.getCurrentInstance().setCurrentPhaseId(currentPhaseId);
	}

	/**
	 * @since  JSF 2.1
	 */
	@Override
	public boolean isReleased() {
		return FacesContext.getCurrentInstance().isReleased();
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
		return PortletHelperUtil.isWindowMaximized();
	}

	/**
	 * @see  LiferayPortletHelper#getDocumentLibraryURL()
	 */
	@Override
	public String getDocumentLibraryURL() {
		return LiferayPortletHelperUtil.getDocumentLibraryURL();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public boolean isProjectStage(ProjectStage stage) {
		return FacesContext.getCurrentInstance().isProjectStage(stage);
	}

	/**
	 * @see  PortletHelper#isUserInRole(String)
	 */
	@Override
	public boolean isUserInRole(String roleName) {
		return PortletHelperUtil.isUserInRole(roleName);
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
	 * @since  JSF 2.0
	 */
	@Override
	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		FacesContext.getCurrentInstance().setExceptionHandler(exceptionHandler);
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
		return LiferayPortletHelperUtil.getHostGroupId();
	}

	/**
	 * @see  LiferayPortletHelper#getImageGalleryURL()
	 */
	@Override
	public String getImageGalleryURL() {
		return LiferayPortletHelperUtil.getImageGalleryURL();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public boolean isPostback() {
		return FacesContext.getCurrentInstance().isPostback();
	}

	/**
	 * @see  PortletHelper#isWindowNormal()
	 */
	@Override
	public boolean isWindowNormal() {
		return PortletHelperUtil.isWindowNormal();
	}

	/**
	 * @see  LiferayPortletHelper#getLayout()
	 */
	@Override
	public Layout getLayout() {
		return LiferayPortletHelperUtil.getLayout();
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
	 * @see  FacesContextHelper#getMessage(Locale, String, Object...)
	 */
	@Override
	public String getMessage(Locale locale, String messageId, Object... arguments) {
		return FacesContextHelperUtil.getMessage(locale, messageId, arguments);
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
		return LiferayPortletHelperUtil.getPermissionChecker();
	}

	/**
	 * @see  LiferayPortletHelper#getPlid()
	 */
	@Override
	public long getPlid() {
		return LiferayPortletHelperUtil.getPlid();
	}

	/**
	 * @see  PortletHelper#getPortalContext()
	 */
	@Override
	public PortalContext getPortalContext() {
		return PortletHelperUtil.getPortalContext();
	}

	/**
	 * @see  LiferayPortletHelper#getPortalURL()
	 */
	@Override
	public String getPortalURL() {
		return LiferayPortletHelperUtil.getPortalURL();
	}

	/**
	 * @see  LiferayPortletHelper#getPortlet()
	 */
	@Override
	public Portlet getPortlet() {
		return LiferayPortletHelperUtil.getPortlet();
	}

	/**
	 * @see  PortletHelper#getPortletConfig()
	 */
	@Override
	public PortletConfig getPortletConfig() {
		return PortletHelperUtil.getPortletConfig();
	}

	/**
	 * @see  PortletHelper#getPortletContext()
	 */
	@Override
	public PortletContext getPortletContext() {
		return PortletHelperUtil.getPortletContext();
	}

	/**
	 * @see  PortletHelper#getPortletContextName()
	 */
	@Override
	public String getPortletContextName() {
		return PortletHelperUtil.getPortletContextName();
	}

	/**
	 * @see  LiferayPortletHelper#getPortletInstanceId()
	 */
	@Override
	public String getPortletInstanceId() {
		return LiferayPortletHelperUtil.getPortletInstanceId();
	}

	/**
	 * @see  PortletHelper#setPortletMode(PortletMode)
	 */
	@Override
	public void setPortletMode(PortletMode portletMode) {
		PortletHelperUtil.setPortletMode(portletMode);
	}

	/**
	 * @see  PortletHelper#getPortletName()
	 */
	@Override
	public String getPortletName() {
		return PortletHelperUtil.getPortletName();
	}

	/**
	 * @see  PortletHelper#getPortletPreference(String, Object)
	 */
	@Override
	public Object getPortletPreference(String preferenceName, Object defaultValue) {
		return PortletHelperUtil.getPortletPreference(preferenceName, defaultValue);
	}

	/**
	 * @see  PortletHelper#getPortletPreferenceAsBool(String, boolean)
	 */
	@Override
	public boolean getPortletPreferenceAsBool(String preferenceName, boolean defaultValue) {
		return PortletHelperUtil.getPortletPreferenceAsBool(preferenceName, defaultValue);
	}

	/**
	 * @see  PortletHelper#getPortletPreferenceAsInt(String, int)
	 */
	@Override
	public int getPortletPreferenceAsInt(String preferenceName, int defaultValue) {
		return PortletHelperUtil.getPortletPreferenceAsInt(preferenceName, defaultValue);
	}

	/**
	 * @see  PortletHelper#getPortletPreferenceAsShort(String, short)
	 */
	@Override
	public short getPortletPreferenceAsShort(String preferenceName, short defaultValue) {
		return PortletHelperUtil.getPortletPreferenceAsShort(preferenceName, defaultValue);
	}

	/**
	 * @see  PortletHelper#getPortletPreferenceAsString(String, String)
	 */
	@Override
	public String getPortletPreferenceAsString(String preferenceName, String defaultValue) {
		return PortletHelperUtil.getPortletPreferenceAsString(preferenceName, defaultValue);
	}

	/**
	 * @see  PortletHelper#getPortletPreferences()
	 */
	@Override
	public PortletPreferences getPortletPreferences() {
		return PortletHelperUtil.getPortletPreferences();
	}

	/**
	 * @see  PortletHelper#getPortletRenderRequest()
	 */
	@Override
	public RenderRequest getPortletRenderRequest() {
		return PortletHelperUtil.getPortletRenderRequest();
	}

	/**
	 * @see  PortletHelper#getPortletRenderResponse()
	 */
	@Override
	public RenderResponse getPortletRenderResponse() {
		return PortletHelperUtil.getPortletRenderResponse();
	}

	/**
	 * @see  PortletHelper#getPortletRequest()
	 */
	@Override
	public PortletRequest getPortletRequest() {
		return PortletHelperUtil.getPortletRequest();
	}

	/**
	 * @see  PortletHelper#getPortletResponse()
	 */
	@Override
	public PortletResponse getPortletResponse() {
		return PortletHelperUtil.getPortletResponse();
	}

	/**
	 * @see  LiferayPortletHelper#getPortletRootId()
	 */
	@Override
	public String getPortletRootId() {
		return LiferayPortletHelperUtil.getPortletRootId();
	}

	/**
	 * @see  PortletHelper#getPortletSession()
	 */
	@Override
	public PortletSession getPortletSession() {
		return PortletHelperUtil.getPortletSession();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public void setProcessingEvents(boolean processingEvents) {
		FacesContext.getCurrentInstance().setProcessingEvents(processingEvents);
	}

	/**
	 * @see  PortletHelper#getRemoteUser()
	 */
	@Override
	public String getRemoteUser() {
		return PortletHelperUtil.getRemoteUser();
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
	 * @see  FacesContextHelper#setRequestAttribute(String, Object)
	 */
	@Override
	public void setRequestAttribute(String name, Object value) {
		FacesContextHelperUtil.setRequestAttribute(name, value);

	}

	/**
	 * @see  FacesContextHelper#getRequestContextPath()
	 */
	@Override
	public String getRequestContextPath() {

		return FacesContextHelperUtil.getRequestContextPath();
	}

	/**
	 * @see  FacesContextHelper#getRequestParameter(String)
	 */
	@Override
	public String getRequestParameter(String name) {

		return FacesContextHelperUtil.getRequestParameter(name);
	}

	/**
	 * @see  FacesContextHelper#getRequestParameterAsBool(String, boolean)
	 */
	@Override
	public boolean getRequestParameterAsBool(String name, boolean defaultValue) {

		return FacesContextHelperUtil.getRequestParameterAsBool(name, defaultValue);
	}

	/**
	 * @see  FacesContextHelper#getRequestParameterAsInt(String, int)
	 */
	@Override
	public int getRequestParameterAsInt(String name, int defaultValue) {

		return FacesContextHelperUtil.getRequestParameterAsInt(name, defaultValue);
	}

	/**
	 * @see  FacesContextHelper#getRequestParameterAsLong(String, long)
	 */
	@Override
	public long getRequestParameterAsLong(String name, long defaultValue) {

		return FacesContextHelperUtil.getRequestParameterAsLong(name, defaultValue);
	}

	/**
	 * @see  FacesContextHelper#getRequestParameterFromMap(String)
	 */
	@Override
	public String getRequestParameterFromMap(String name) {

		return FacesContextHelperUtil.getRequestParameterFromMap(name);
	}

	/**
	 * @see  FacesContextHelper#getRequestParameterMap()
	 */
	@Override
	public Map<String, String> getRequestParameterMap() {
		return FacesContextHelperUtil.getRequestParameterMap();
	}

	/**
	 * @see  FacesContextHelper#getRequestQueryString()
	 */
	@Override
	public String getRequestQueryString() {

		return FacesContextHelperUtil.getRequestQueryString();
	}

	/**
	 * @see  FacesContextHelper#getRequestQueryStringParameter(String)
	 */
	@Override
	public String getRequestQueryStringParameter(String name) {

		return FacesContextHelperUtil.getRequestQueryStringParameter(name);
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
	public void setResponseStream(ResponseStream responseStream) {
		FacesContext.getCurrentInstance().setResponseStream(responseStream);
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public ResponseWriter getResponseWriter() {
		return FacesContext.getCurrentInstance().getResponseWriter();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public void setResponseWriter(ResponseWriter responseWriter) {
		FacesContext.getCurrentInstance().setResponseWriter(responseWriter);
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public boolean isProcessingEvents() {
		return FacesContext.getCurrentInstance().isProcessingEvents();
	}

	/**
	 * @see  LiferayPortletHelper#getScopeGroup()
	 */
	@Override
	public Group getScopeGroup() {
		return LiferayPortletHelperUtil.getScopeGroup();
	}

	/**
	 * @see  LiferayPortletHelper#getScopeGroupId()
	 */
	@Override
	public long getScopeGroupId() {
		return LiferayPortletHelperUtil.getScopeGroupId();
	}

	/**
	 * @see  LiferayPortletHelper#getScopeGroupUser()
	 */
	@Override
	public User getScopeGroupUser() {
		return LiferayPortletHelperUtil.getScopeGroupUser();
	}

	/**
	 * @see  LiferayPortletHelper#getServiceContext()
	 */
	@Override
	public ServiceContext getServiceContext() {
		return LiferayPortletHelperUtil.getServiceContext();
	}

	/**
	 * @see  FacesContextHelper#getSession(boolean)
	 */
	@Override
	public Object getSession(boolean create) {

		return FacesContextHelperUtil.getSession(create);
	}

	/**
	 * @see  FacesContextHelper#getSessionAttribute(String)
	 */
	@Override
	public Object getSessionAttribute(String name) {

		return FacesContextHelperUtil.getSessionAttribute(name);
	}

	/**
	 * @see  FacesContextHelper#setSessionAttribute(String, Object)
	 */
	@Override
	public void setSessionAttribute(String name, Object value) {

		FacesContextHelperUtil.setSessionAttribute(name, value);
	}

	/**
	 * @see  PortletHelper#getSessionSharedAttribute(String)
	 */
	@Override
	public Object getSessionSharedAttribute(String name) {
		return PortletHelperUtil.getSessionSharedAttribute(name);
	}

	/**
	 * @see  PortletHelper#setSessionSharedAttribute(String, Object)
	 */
	@Override
	public void setSessionSharedAttribute(String name, Object value) {
		PortletHelperUtil.setSessionSharedAttribute(name, value);
	}

	/**
	 * @see  PortletHelper#isPortletEnvironment()
	 */
	@Override
	public boolean isPortletEnvironment() {
		return PortletHelperUtil.isPortletEnvironment();
	}

	/**
	 * @see  LiferayPortletHelper#getTheme()
	 */
	@Override
	public Theme getTheme() {
		return LiferayPortletHelperUtil.getTheme();
	}

	/**
	 * @see  LiferayPortletHelper#getThemeDisplay()
	 */
	@Override
	public ThemeDisplay getThemeDisplay() {
		return LiferayPortletHelperUtil.getThemeDisplay();
	}

	/**
	 * @see  LiferayPortletHelper#getThemeImagesURL()
	 */
	@Override
	public String getThemeImagesURL() {
		return LiferayPortletHelperUtil.getThemeImagesURL();
	}

	/**
	 * @see  LiferayPortletHelper#getUser()
	 */
	@Override
	public User getUser() {
		return LiferayPortletHelperUtil.getUser();
	}

	/**
	 * @see  LiferayPortletHelper#getUserId()
	 */
	@Override
	public long getUserId() {
		return LiferayPortletHelperUtil.getUserId();
	}

	/**
	 * @see  LiferayPortletHelper#getUserRoles()
	 */
	@Override
	public List<Role> getUserRoles() throws SystemException {
		return LiferayPortletHelperUtil.getUserRoles();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public UIViewRoot getViewRoot() {
		return FacesContext.getCurrentInstance().getViewRoot();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public void setViewRoot(UIViewRoot viewRoot) {
		FacesContext.getCurrentInstance().setViewRoot(viewRoot);
	}

	/**
	 * @see  PortletHelper#getWindowState()
	 */
	@Override
	public WindowState getWindowState() {
		return PortletHelperUtil.getWindowState();
	}

	/**
	 * @see  PortletHelper#setWindowState(WindowState)
	 */
	@Override
	public void setWindowState(WindowState windowState) {
		PortletHelperUtil.setWindowState(windowState);
	}

	/**
	 * @see  PortletHelper#setWindowStateToMaximized()
	 */
	@Override
	public void setWindowStateToMaximized() {
		PortletHelperUtil.setWindowStateToMaximized();
	}

	/**
	 * @see  PortletHelper#setWindowStateToNormal()
	 */
	@Override
	public void setWindowStateToNormal() {
		PortletHelperUtil.setWindowStateToNormal();
	}
}
