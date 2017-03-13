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
package com.liferay.faces.portal.context;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.el.ELContext;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ProjectStage;
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

import com.liferay.faces.portal.security.AuthorizationException;
import com.liferay.faces.util.context.FacesContextHelper;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.Theme;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;


/**
 * This class is deprecated and has been replaced by {@link com.liferay.faces.util.context.FacesContextHelperUtil},
 * {@link PortletHelperUtil}, and {@link LiferayPortletHelperUtil}. For more information, see <a
 * href="https://issues.liferay.com/browse/FACES-2502">FACES-2502</a>.
 *
 * @author  Neil Griffin
 */
public abstract class LiferayFacesContext extends FacesContext implements FacesContextHelper, PortletHelper,
	LiferayPortletHelper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(LiferayFacesContext.class);

	// Singleton Instance
	private static transient LiferayFacesContext instance;

	/**
	 * Returns the implementation singleton instance.
	 */
	public static LiferayFacesContext getInstance() {

		if (instance == null) {
			logger.error("Instance not initialized -- caller might be static");
		}

		return instance;
	}

	/**
	 * Sets the implementation singleton instance.
	 */
	public static void setInstance(LiferayFacesContext liferayFacesContext) {
		instance = liferayFacesContext;
	}

	/**
	 * @deprecated  Call {@link FacesContextHelper#addComponentErrorMessage(String, String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addComponentErrorMessage(String clientId, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelper#addComponentErrorMessage(String, String, Object...)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addComponentErrorMessage(String clientId, String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelper#addComponentErrorMessage(String, String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addComponentErrorMessage(FacesContext facesContext, String clientId, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelper#addComponentErrorMessage(String, String, Object...)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addComponentErrorMessage(FacesContext facesContext, String clientId, String messageId,
		Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelper#addComponentInfoMessage(String, String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addComponentInfoMessage(String clientId, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelper#addComponentInfoMessage(String, String, Object...)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addComponentInfoMessage(String clientId, String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelper#addComponentInfoMessage(String, String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addComponentInfoMessage(FacesContext facesContext, String clientId, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelper#addComponentInfoMessage(String, String, Object...)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addComponentInfoMessage(FacesContext facesContext, String clientId, String messageId,
		Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelper#addGlobalErrorMessage(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addGlobalErrorMessage(String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelper#addGlobalErrorMessage(String, Object...)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addGlobalErrorMessage(String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelper#addGlobalErrorMessage(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addGlobalErrorMessage(FacesContext facesContext, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelper#addGlobalErrorMessage(String, Object...)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addGlobalErrorMessage(FacesContext facesContext, String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelper#addGlobalInfoMessage(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addGlobalInfoMessage(String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelper#addGlobalInfoMessage(String, Object...)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addGlobalInfoMessage(String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelper#addGlobalInfoMessage(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addGlobalInfoMessage(FacesContext facesContext, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelper#addGlobalInfoMessage(String, Object...)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addGlobalInfoMessage(FacesContext facesContext, String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelper#addGlobalSuccessInfoMessage()} instead.
	 */
	@Deprecated
	@Override
	public abstract void addGlobalSuccessInfoMessage();

	/**
	 * @deprecated  Call {@link FacesContextHelper#addGlobalSuccessInfoMessage()} instead.
	 */
	@Deprecated
	@Override
	public abstract void addGlobalSuccessInfoMessage(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContextHelper#addGlobalUnexpectedErrorMessage()} instead.
	 */
	@Deprecated
	@Override
	public abstract void addGlobalUnexpectedErrorMessage();

	/**
	 * @deprecated  Call {@link FacesContextHelper#addGlobalUnexpectedErrorMessage()} instead.
	 */
	@Deprecated
	@Override
	public abstract void addGlobalUnexpectedErrorMessage(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContext#addMessage(String, FacesMessage)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addMessage(String s, FacesMessage facesMessage);

	/**
	 * @deprecated  Call {@link FacesContextHelper#addMessage(String, FacesMessage.Severity, String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addMessage(String clientId, FacesMessage.Severity severity, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelper#addMessage(String, FacesMessage.Severity, String, Object...)}
	 *              instead.
	 */
	@Deprecated
	@Override
	public abstract void addMessage(String clientId, FacesMessage.Severity severity, String messageId,
		Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelper#addMessage(String, FacesMessage.Severity, String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addMessage(FacesContext facesContext, String clientId, FacesMessage.Severity severity,
		String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelper#addMessage(String, FacesMessage.Severity, String, Object...)}
	 *              instead.
	 */
	@Deprecated
	@Override
	public abstract void addMessage(FacesContext facesContext, String clientId, FacesMessage.Severity severity,
		String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link LiferayPortletHelper#checkUserPortletPermission(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void checkUserPortletPermission(String actionId) throws AuthorizationException;

	/**
	 * @deprecated  Call {@link PortletHelper#createActionURL()} instead.
	 */
	@Deprecated
	@Override
	public abstract PortletURL createActionURL();

	/**
	 * @deprecated  Call {@link PortletHelper#createRenderURL()} instead.
	 */
	@Deprecated
	@Override
	public abstract PortletURL createRenderURL();

	/**
	 * @deprecated  Call {@link PortletHelper#getActionResponse()} instead.
	 */
	@Deprecated
	@Override
	public abstract ActionResponse getActionResponse();

	/**
	 * @deprecated  Call {@link FacesContext#getApplication()} instead.
	 */
	@Deprecated
	@Override
	public abstract Application getApplication();

	/**
	 * @deprecated  Call {@link FacesContext#getAttributes()} instead.
	 */
	@Deprecated
	@Override
	public abstract Map<Object, Object> getAttributes();

	/**
	 * @deprecated  Call {@link LiferayPortletHelper#getBuildNumber()} instead.
	 */
	@Override
	public abstract int getBuildNumber();

	/**
	 * @deprecated  Call {@link FacesContext#getClientIdsWithMessages()} instead.
	 */
	@Deprecated
	@Override
	public abstract Iterator<String> getClientIdsWithMessages();

	/**
	 * @deprecated  Call {@link LiferayPortletHelper#getCompanyId()} instead.
	 */
	@Deprecated
	@Override
	public abstract long getCompanyId();

	/**
	 * @deprecated  Call {@link FacesContext#getCurrentPhaseId()} instead.
	 */
	@Deprecated
	@Override
	public abstract PhaseId getCurrentPhaseId();

	/**
	 * @deprecated  Call {@link LiferayPortletHelper#getDocumentLibraryURL()} instead.
	 */
	@Deprecated
	@Override
	public abstract String getDocumentLibraryURL();

	/**
	 * @deprecated  Call {@link FacesContext#getELContext()} instead.
	 */
	@Deprecated
	@Override
	public abstract ELContext getELContext();

	/**
	 * @deprecated  Call {@link FacesContext#getExceptionHandler()} instead.
	 */
	@Deprecated
	@Override
	public abstract ExceptionHandler getExceptionHandler();

	/**
	 * @deprecated  Call {@link FacesContext#getExternalContext()} instead.
	 */
	@Deprecated
	@Override
	public abstract ExternalContext getExternalContext();

	/**
	 * @deprecated  Call {@link FacesContextHelper#getFacesContext()} instead.
	 */
	@Deprecated
	@Override
	public abstract FacesContext getFacesContext();

	/**
	 * @deprecated  Call {@link LiferayPortletHelper#getHostGroupId()} instead.
	 */
	@Deprecated
	@Override
	public abstract long getHostGroupId();

	/**
	 * @deprecated  Call {@link LiferayPortletHelper#getImageGalleryURL()} instead.
	 */
	@Deprecated
	@Override
	public abstract String getImageGalleryURL();

	/**
	 * @deprecated  Call {@link LiferayPortletHelper#getLayout()} instead.
	 */
	@Deprecated
	@Override
	public abstract Layout getLayout();

	/**
	 * @deprecated  Call {@link FacesContextHelper#getLocale()} instead.
	 */
	@Deprecated
	@Override
	public abstract Locale getLocale();

	/**
	 * @deprecated  Call {@link FacesContextHelper#getLocale()} instead.
	 */
	@Deprecated
	@Override
	public abstract Locale getLocale(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContext#getMaximumSeverity()} instead.
	 */
	@Deprecated
	@Override
	public abstract FacesMessage.Severity getMaximumSeverity();

	/**
	 * @deprecated  Call {@link FacesContextHelper#getMessage(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getMessage(String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getMessage(String, Object...)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getMessage(String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getMessage(Locale, String)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getMessage(Locale locale, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getMessage(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getMessage(FacesContext facesContext, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getMessage(Locale, String, Object...)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getMessage(Locale locale, String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getMessage(String, Object...)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getMessage(FacesContext facesContext, String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getMessage(Locale, String)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getMessage(FacesContext facesContext, Locale locale, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getMessage(Locale, String, Object...)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getMessage(FacesContext facesContext, Locale locale, String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContext#getMessageList()} instead.
	 */
	@Deprecated
	@Override
	public abstract List<FacesMessage> getMessageList();

	/**
	 * @deprecated  Call {@link FacesContext#getMessageList(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract List<FacesMessage> getMessageList(String clientId);

	/**
	 * @deprecated  Call {@link FacesContext#getMessages()} instead.
	 */
	@Deprecated
	@Override
	public abstract Iterator<FacesMessage> getMessages();

	/**
	 * @deprecated  Call {@link FacesContext#getMessages(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract Iterator<FacesMessage> getMessages(String s);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getNamespace()} instead.
	 */
	@Deprecated
	@Override
	public abstract String getNamespace();

	/**
	 * @deprecated  Call {@link FacesContextHelper#getNamespace()} instead.
	 */
	@Deprecated
	@Override
	public abstract String getNamespace(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getParentForm(UIComponent)} instead.
	 */
	@Deprecated
	@Override
	public abstract UIForm getParentForm(UIComponent uiComponent);

	/**
	 * @deprecated  Call {@link FacesContext#getPartialViewContext()} instead.
	 */
	@Deprecated
	@Override
	public abstract PartialViewContext getPartialViewContext();

	/**
	 * @deprecated  Call {@link LiferayPortletHelper#getPermissionChecker()} instead.
	 */
	@Deprecated
	@Override
	public abstract PermissionChecker getPermissionChecker();

	/**
	 * @deprecated  Call {@link LiferayPortletHelper#getPlid()} instead.
	 */
	@Deprecated
	@Override
	public abstract long getPlid();

	/**
	 * @deprecated  Call {@link PortletHelper#getPortalContext()} instead.
	 */
	@Deprecated
	@Override
	public abstract PortalContext getPortalContext();

	/**
	 * @deprecated  Call {@link LiferayPortletHelper#getPortalURL()} instead.
	 */
	@Deprecated
	@Override
	public abstract String getPortalURL();

	/**
	 * @deprecated  Call {@link LiferayPortletHelper#getPortlet()} instead.
	 */
	@Deprecated
	@Override
	public abstract Portlet getPortlet();

	/**
	 * @deprecated  Call {@link PortletHelper#getPortletConfig()} instead.
	 */
	@Deprecated
	@Override
	public abstract PortletConfig getPortletConfig();

	/**
	 * @deprecated  Call {@link PortletHelper#getPortletContext()} instead.
	 */
	@Deprecated
	@Override
	public abstract PortletContext getPortletContext();

	/**
	 * @deprecated  Call {@link PortletHelper#getPortletContextName()} instead.
	 */
	@Deprecated
	@Override
	public abstract String getPortletContextName();

	/**
	 * @deprecated  Call {@link LiferayPortletHelper#getPortletInstanceId()} instead.
	 */
	@Deprecated
	@Override
	public abstract String getPortletInstanceId();

	/**
	 * @deprecated  Call {@link PortletHelper#getPortletName()} instead.
	 */
	@Deprecated
	@Override
	public abstract String getPortletName();

	/**
	 * @deprecated  Call {@link PortletHelper#getPortletPreference(String, Object)} instead.
	 */
	@Deprecated
	@Override
	public abstract Object getPortletPreference(String preferenceName, Object defaultValue);

	/**
	 * @deprecated  Call {@link PortletHelper#getPortletPreferenceAsBool(String, boolean)} instead.
	 */
	@Deprecated
	@Override
	public abstract boolean getPortletPreferenceAsBool(String preferenceName, boolean defaultValue);

	/**
	 * @deprecated  Call {@link PortletHelper#getPortletPreferenceAsInt(String, int)} instead.
	 */
	@Deprecated
	@Override
	public abstract int getPortletPreferenceAsInt(String preferenceName, int defaultValue);

	/**
	 * @deprecated  Call {@link PortletHelper#getPortletPreferenceAsShort(String, short)} instead.
	 */
	@Deprecated
	@Override
	public abstract short getPortletPreferenceAsShort(String preferenceName, short defaultValue);

	/**
	 * @deprecated  Call {@link PortletHelper#getPortletPreferenceAsString(String, String)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getPortletPreferenceAsString(String preferenceName, String defaultValue);

	/**
	 * @deprecated  Call {@link PortletHelper#getPortletPreferences()} instead.
	 */
	@Deprecated
	@Override
	public abstract PortletPreferences getPortletPreferences();

	/**
	 * @deprecated  Call {@link PortletHelper#getPortletRenderRequest()} instead.
	 */
	@Deprecated
	@Override
	public abstract RenderRequest getPortletRenderRequest();

	/**
	 * @deprecated  Call {@link PortletHelper#getPortletRenderResponse()} instead.
	 */
	@Deprecated
	@Override
	public abstract RenderResponse getPortletRenderResponse();

	/**
	 * @deprecated  Call {@link PortletHelper#getPortletRequest()} instead.
	 */
	@Deprecated
	@Override
	public abstract PortletRequest getPortletRequest();

	/**
	 * @deprecated  Call {@link PortletHelper#getPortletResponse()} instead.
	 */
	@Deprecated
	@Override
	public abstract PortletResponse getPortletResponse();

	/**
	 * @deprecated  Call {@link LiferayPortletHelper#getPortletRootId()} instead.
	 */
	@Deprecated
	@Override
	public abstract String getPortletRootId();

	/**
	 * @deprecated  Call {@link PortletHelper#getPortletSession()} instead.
	 */
	@Deprecated
	@Override
	public abstract PortletSession getPortletSession();

	/**
	 * @deprecated  Call {@link PortletHelper#getRemoteUser()} instead.
	 */
	@Deprecated
	@Override
	public abstract String getRemoteUser();

	/**
	 * @deprecated  Call {@link FacesContext#getRenderKit()} instead.
	 */
	@Deprecated
	@Override
	public abstract RenderKit getRenderKit();

	/**
	 * @deprecated  Call {@link FacesContext#getRenderResponse()} instead.
	 */
	@Deprecated
	@Override
	public abstract boolean getRenderResponse();

	/**
	 * @deprecated  Call {@link FacesContextHelper#getRequestAttribute(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract Object getRequestAttribute(String name);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getRequestAttribute(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract Object getRequestAttribute(FacesContext facesContext, String name);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getRequestContextPath()} instead.
	 */
	@Deprecated
	@Override
	public abstract String getRequestContextPath();

	/**
	 * @deprecated  Call {@link FacesContextHelper#getRequestContextPath()} instead.
	 */
	@Deprecated
	@Override
	public abstract String getRequestContextPath(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getRequestParameter(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getRequestParameter(String name);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getRequestParameter(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getRequestParameter(FacesContext facesContext, String name);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getRequestParameterAsBool(String, boolean)} instead.
	 */
	@Deprecated
	@Override
	public abstract boolean getRequestParameterAsBool(String name, boolean defaultValue);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getRequestParameterAsBool(String, boolean)} instead.
	 */
	@Deprecated
	@Override
	public abstract boolean getRequestParameterAsBool(FacesContext facesContext, String name, boolean defaultValue);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getRequestParameterAsInt(String, int)} instead.
	 */
	@Deprecated
	@Override
	public abstract int getRequestParameterAsInt(String name, int defaultValue);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getRequestParameterAsInt(String, int)} instead.
	 */
	@Deprecated
	@Override
	public abstract int getRequestParameterAsInt(FacesContext facesContext, String name, int defaultValue);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getRequestParameterAsLong(String, long)} instead.
	 */
	@Deprecated
	@Override
	public abstract long getRequestParameterAsLong(String name, long defaultValue);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getRequestParameterAsLong(String, long)} instead.
	 */
	@Deprecated
	@Override
	public abstract long getRequestParameterAsLong(FacesContext facesContext, String name, long defaultValue);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getRequestParameterFromMap(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getRequestParameterFromMap(String name);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getRequestParameterFromMap(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getRequestParameterFromMap(FacesContext facesContext, String name);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getRequestParameterMap()} instead.
	 */
	@Deprecated
	@Override
	public abstract Map<String, String> getRequestParameterMap();

	/**
	 * @deprecated  Call {@link FacesContextHelper#getRequestParameterMap()} instead.
	 */
	@Deprecated
	@Override
	public abstract Map<String, String> getRequestParameterMap(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getRequestQueryString()} instead.
	 */
	@Deprecated
	@Override
	public abstract String getRequestQueryString();

	/**
	 * @deprecated  Call {@link FacesContextHelper#getRequestQueryString()} instead.
	 */
	@Deprecated
	@Override
	public abstract String getRequestQueryString(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getRequestQueryStringParameter(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getRequestQueryStringParameter(String name);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getRequestQueryStringParameter(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getRequestQueryStringParameter(FacesContext facesContext, String name);

	/**
	 * @deprecated  Call {@link FacesContext#getResponseComplete()} instead.
	 */
	@Deprecated
	@Override
	public abstract boolean getResponseComplete();

	/**
	 * @deprecated  Call {@link FacesContext#getResponseStream()} instead.
	 */
	@Deprecated
	@Override
	public abstract ResponseStream getResponseStream();

	/**
	 * @deprecated  Call {@link FacesContext#getResponseWriter()} instead.
	 */
	@Deprecated
	@Override
	public abstract ResponseWriter getResponseWriter();

	/**
	 * @deprecated  Call {@link LiferayPortletHelper#getScopeGroup()} instead.
	 */
	@Deprecated
	@Override
	public abstract Group getScopeGroup();

	/**
	 * @deprecated  Call {@link LiferayPortletHelper#getScopeGroupId()}instead.
	 */
	@Deprecated
	@Override
	public abstract long getScopeGroupId();

	/**
	 * @deprecated  Call {@link LiferayPortletHelper#getScopeGroupUser()} instead.
	 */
	@Deprecated
	@Override
	public abstract User getScopeGroupUser();

	/**
	 * @deprecated  Call {@link LiferayPortletHelper#getServiceContext()} instead.
	 */
	@Deprecated
	@Override
	public abstract ServiceContext getServiceContext();

	/**
	 * @deprecated  Call {@link FacesContextHelper#getSession(boolean)} instead.
	 */
	@Deprecated
	@Override
	public abstract Object getSession(boolean create);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getSession(boolean)} instead.
	 */
	@Deprecated
	@Override
	public abstract Object getSession(FacesContext facesContext, boolean create);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getSessionAttribute(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract Object getSessionAttribute(String name);

	/**
	 * @deprecated  Call {@link FacesContextHelper#getSessionAttribute(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract Object getSessionAttribute(FacesContext facesContext, String name);

	/**
	 * @deprecated  Call {@link PortletHelper#getSessionSharedAttribute(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract Object getSessionSharedAttribute(String name);

	/**
	 * @deprecated  Call {@link LiferayPortletHelper#getTheme()} instead.
	 */
	@Deprecated
	@Override
	public abstract Theme getTheme();

	/**
	 * @deprecated  Call {@link LiferayPortletHelper#getThemeDisplay()} instead.
	 */
	@Deprecated
	@Override
	public abstract ThemeDisplay getThemeDisplay();

	/**
	 * @deprecated  Call {@link LiferayPortletHelper#getThemeImagesURL()} instead.
	 */
	@Deprecated
	@Override
	public abstract String getThemeImagesURL();

	/**
	 * @deprecated  Call {@link LiferayPortletHelper#getUser()} instead.
	 */
	@Deprecated
	@Override
	public abstract User getUser();

	/**
	 * @deprecated  Call {@link LiferayPortletHelper#getUserId()} instead.
	 */
	@Deprecated
	@Override
	public abstract long getUserId();

	/**
	 * @deprecated  Call {@link LiferayPortletHelper#getUserRoles()} instead.
	 */
	@Deprecated
	@Override
	public abstract List<Role> getUserRoles() throws SystemException;

	/**
	 * @deprecated  Call {@link FacesContext#getViewRoot()} instead.
	 */
	@Deprecated
	@Override
	public abstract UIViewRoot getViewRoot();

	/**
	 * @deprecated  Call {@link PortletHelper#getWindowState()} instead.
	 */
	@Deprecated
	@Override
	public abstract WindowState getWindowState();

	/**
	 * @deprecated  Call {@link PortletHelper#isPortletEnvironment()} instead.
	 */
	@Deprecated
	@Override
	public abstract boolean isPortletEnvironment();

	/**
	 * @deprecated  Call {@link FacesContext#isPostback()} instead.
	 */
	@Deprecated
	@Override
	public abstract boolean isPostback();

	/**
	 * @deprecated  Call {@link FacesContext#isProcessingEvents()} instead.
	 */
	@Deprecated
	@Override
	public abstract boolean isProcessingEvents();

	/**
	 * @deprecated  Call {@link FacesContext#isProjectStage(ProjectStage)} instead.
	 */
	@Deprecated
	@Override
	public abstract boolean isProjectStage(ProjectStage stage);

	/**
	 * @deprecated  Call {@link FacesContext#isReleased()} instead.
	 */
	@Deprecated
	@Override
	public abstract boolean isReleased();

	/**
	 * @deprecated  Call {@link PortletHelper#isUserInRole(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract boolean isUserInRole(String roleName);

	/**
	 * @deprecated  Call {@link FacesContext#isValidationFailed()} instead.
	 */
	@Deprecated
	@Override
	public abstract boolean isValidationFailed();

	/**
	 * @deprecated  Call {@link PortletHelper#isWindowMaximized()} instead.
	 */
	@Deprecated
	@Override
	public abstract boolean isWindowMaximized();

	/**
	 * @deprecated  Call {@link PortletHelper#isWindowNormal()} instead.
	 */
	@Deprecated
	@Override
	public abstract boolean isWindowNormal();

	/**
	 * @deprecated  Call {@link FacesContextHelper#matchComponentInHierarchy(UIComponent, String)} instead.
	 */
	@Deprecated
	@Override
	public abstract UIComponent matchComponentInHierarchy(UIComponent parent, String partialClientId);

	/**
	 * @deprecated  Call {@link FacesContextHelper#matchComponentInHierarchy(UIComponent, String)} instead.
	 */
	@Deprecated
	@Override
	public abstract UIComponent matchComponentInHierarchy(FacesContext facesContext, UIComponent parent,
		String partialClientId);

	/**
	 * @deprecated  Call {@link FacesContextHelper#matchComponentInViewRoot(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract UIComponent matchComponentInViewRoot(String partialClientId);

	/**
	 * @deprecated  Call {@link FacesContextHelper#matchComponentInViewRoot(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract UIComponent matchComponentInViewRoot(FacesContext facesContext, String partialClientId);

	/**
	 * @deprecated  Call {@link FacesContextHelper#navigate(String, String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void navigate(String fromAction, String outcome);

	/**
	 * @deprecated  Call {@link FacesContextHelper#navigate(String, String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void navigate(FacesContext facesContext, String fromAction, String outcome);

	/**
	 * @deprecated  Call {@link FacesContextHelper#navigateTo(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void navigateTo(String outcome);

	/**
	 * @deprecated  Call {@link FacesContextHelper#navigateTo(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void navigateTo(FacesContext facesContext, String outcome);

	/**
	 * @deprecated  Call {@link FacesContextHelper#recreateComponentTree()} instead.
	 */
	@Deprecated
	@Override
	public abstract void recreateComponentTree();

	/**
	 * @deprecated  Call {@link FacesContextHelper#recreateComponentTree()} instead.
	 */
	@Deprecated
	@Override
	public abstract void recreateComponentTree(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContextHelper#registerPhaseListener(PhaseListener)} instead.
	 */
	@Deprecated
	@Override
	public abstract void registerPhaseListener(PhaseListener phaseListener);

	/**
	 * @deprecated  Call {@link FacesContext#release()} instead.
	 */
	@Deprecated
	@Override
	public abstract void release();

	/**
	 * @deprecated  Call {@link FacesContextHelper#removeChildrenFromComponentTree(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void removeChildrenFromComponentTree(String clientId);

	/**
	 * @deprecated  Call {@link FacesContextHelper#removeChildrenFromComponentTree(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void removeChildrenFromComponentTree(FacesContext facesContext, String clientId);

	/**
	 * @deprecated  Call {@link FacesContextHelper#removeMessages(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void removeMessages(String clientId);

	/**
	 * @deprecated  Call {@link FacesContextHelper#removeMessages(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void removeMessages(FacesContext facesContext, String clientId);

	/**
	 * @deprecated  Call {@link FacesContextHelper#removeMessagesForImmediateComponents()} instead.
	 */
	@Deprecated
	@Override
	public abstract void removeMessagesForImmediateComponents();

	/**
	 * @deprecated  Call {@link FacesContextHelper#removeMessagesForImmediateComponents(UIComponent)} instead.
	 */
	@Deprecated
	@Override
	public abstract void removeMessagesForImmediateComponents(UIComponent uiComponent);

	/**
	 * @deprecated  Call {@link FacesContextHelper#removeMessagesForImmediateComponents()} instead.
	 */
	@Deprecated
	@Override
	public abstract void removeMessagesForImmediateComponents(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContextHelper#removeMessagesForImmediateComponents(UIComponent)} instead.
	 */
	@Deprecated
	@Override
	public abstract void removeMessagesForImmediateComponents(FacesContext facesContext, UIComponent uiComponent);

	/**
	 * @deprecated  Call {@link FacesContextHelper#removeParentFormFromComponentTree(UIComponent)} instead.
	 */
	@Deprecated
	@Override
	public abstract void removeParentFormFromComponentTree(UIComponent uiComponent);

	/**
	 * @deprecated  Call {@link FacesContext#renderResponse()} instead.
	 */
	@Deprecated
	@Override
	public abstract void renderResponse();

	/**
	 * @deprecated  Call {@link FacesContextHelper#resetView()} instead.
	 */
	@Deprecated
	@Override
	public abstract void resetView();

	/**
	 * @deprecated  Call {@link FacesContextHelper#resetView(boolean)} instead.
	 */
	@Deprecated
	@Override
	public abstract void resetView(boolean renderResponse);

	/**
	 * @deprecated  Call {@link FacesContextHelper#resetView(boolean)} instead.
	 */
	@Deprecated
	@Override
	public abstract void resetView(FacesContext facesContext, boolean renderResponse);

	/**
	 * @deprecated  Call {@link FacesContextHelper#resolveExpression(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract Object resolveExpression(String elExpression);

	/**
	 * @deprecated  Call {@link FacesContextHelper#resolveExpression(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract Object resolveExpression(FacesContext facesContext, String elExpression);

	/**
	 * @deprecated  Call {@link FacesContext#responseComplete()} instead.
	 */
	@Deprecated
	@Override
	public abstract void responseComplete();

	/**
	 * @deprecated  Call {@link FacesContext#setCurrentPhaseId(PhaseId)} instead.
	 */
	@Deprecated
	@Override
	public abstract void setCurrentPhaseId(PhaseId currentPhaseId);

	/**
	 * @deprecated  Call {@link FacesContext#setExceptionHandler(ExceptionHandler)} instead.
	 */
	@Deprecated
	@Override
	public abstract void setExceptionHandler(ExceptionHandler exceptionHandler);

	/**
	 * @deprecated  Call {@link PortletHelper#setPortletMode(PortletMode)} instead.
	 */
	@Deprecated
	@Override
	public abstract void setPortletMode(PortletMode portletMode);

	/**
	 * @deprecated  Call {@link FacesContext#setProcessingEvents(boolean)} instead.
	 */
	@Deprecated
	@Override
	public abstract void setProcessingEvents(boolean processingEvents);

	/**
	 * @deprecated  Call {@link FacesContextHelper#setRequestAttribute(String, Object)} instead.
	 */
	@Deprecated
	@Override
	public abstract void setRequestAttribute(String name, Object value);

	/**
	 * @deprecated  Call {@link FacesContext#setResponseStream(ResponseStream)} instead.
	 */
	@Deprecated
	@Override
	public abstract void setResponseStream(ResponseStream responseStream);

	/**
	 * @deprecated  Call {@link FacesContext#setResponseWriter(ResponseWriter)} instead.
	 */
	@Deprecated
	@Override
	public abstract void setResponseWriter(ResponseWriter responseWriter);

	/**
	 * @deprecated  Call {@link FacesContextHelper#setSessionAttribute(String, Object)} instead.
	 */
	@Deprecated
	@Override
	public abstract void setSessionAttribute(String name, Object value);

	/**
	 * @deprecated  Call {@link FacesContextHelper#setSessionAttribute(String, Object)} instead.
	 */
	@Deprecated
	@Override
	public abstract void setSessionAttribute(FacesContext facesContext, String name, Object value);

	/**
	 * @deprecated  Call {@link PortletHelper#setSessionSharedAttribute(String, Object)} instead.
	 */
	@Deprecated
	@Override
	public abstract void setSessionSharedAttribute(String name, Object value);

	/**
	 * @deprecated  Call {@link FacesContext#setViewRoot(UIViewRoot)} instead.
	 */
	@Deprecated
	@Override
	public abstract void setViewRoot(UIViewRoot uiViewRoot);

	/**
	 * @deprecated  Call {@link PortletHelper#setWindowState(WindowState)} instead.
	 */
	@Deprecated
	@Override
	public abstract void setWindowState(WindowState windowState);

	/**
	 * @deprecated  Call {@link PortletHelper#setWindowStateToMaximized()} instead.
	 */
	@Deprecated
	@Override
	public abstract void setWindowStateToMaximized();

	/**
	 * @deprecated  Call {@link PortletHelper#setWindowStateToNormal()} instead.
	 */
	@Deprecated
	@Override
	public abstract void setWindowStateToNormal();

	/**
	 * @deprecated  Call {@link LiferayPortletHelper#userHasPortletPermission(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract boolean userHasPortletPermission(String actionId);

	/**
	 * @deprecated  Call {@link LiferayPortletHelper#userHasRole(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract boolean userHasRole(String roleName);

	/**
	 * @deprecated  Call {@link FacesContext#validationFailed()} instead.
	 */
	@Deprecated
	@Override
	public abstract void validationFailed();
}
