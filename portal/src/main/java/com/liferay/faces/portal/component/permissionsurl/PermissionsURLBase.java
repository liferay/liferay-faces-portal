/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.portal.component.permissionsurl;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIOutput;


/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class PermissionsURLBase extends UIOutput {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portal.component.permissionsurl.PermissionsURL";
	public static final String RENDERER_TYPE = "com.liferay.faces.portal.component.permissionsurl.PermissionsURLRenderer";

	// Protected Enumerations
	protected enum PermissionsURLPropertyKeys {
		modelResource,
		modelResourceDescription,
		redirect,
		resourceGroupId,
		resourcePrimKey,
		roleTypes,
		var,
		windowState
	}

	public PermissionsURLBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	/**
	 * <code>modelResource</code> attribute description:
	 * <br /><br />
	 * The Fully-Qualified Class Name (FQCN) of the ServiceBuilder model.
	 */
	public String getModelResource() {
		return (String) getStateHelper().eval(PermissionsURLPropertyKeys.modelResource, "");
	}

	/**
	 * <code>modelResource</code> attribute description:
	 * <br /><br />
	 * The Fully-Qualified Class Name (FQCN) of the ServiceBuilder model.
	 */
	public void setModelResource(String modelResource) {
		getStateHelper().put(PermissionsURLPropertyKeys.modelResource, modelResource);
	}

	/**
	 * <code>modelResourceDescription</code> attribute description:
	 * <br /><br />
	 * The description of the model resource that is to be displayed in the Liferay Portal Permissions UI.
	 */
	public String getModelResourceDescription() {
		return (String) getStateHelper().eval(PermissionsURLPropertyKeys.modelResourceDescription, "");
	}

	/**
	 * <code>modelResourceDescription</code> attribute description:
	 * <br /><br />
	 * The description of the model resource that is to be displayed in the Liferay Portal Permissions UI.
	 */
	public void setModelResourceDescription(String modelResourceDescription) {
		getStateHelper().put(PermissionsURLPropertyKeys.modelResourceDescription, modelResourceDescription);
	}

	/**
	 * <code>redirect</code> attribute description:
	 * <br /><br />
	 * The URL that should be redirected-to when the user clicks on the "Return to Full Page" link in the Liferay Portal Permissions UI.
	 */
	public String getRedirect() {
		return (String) getStateHelper().eval(PermissionsURLPropertyKeys.redirect, null);
	}

	/**
	 * <code>redirect</code> attribute description:
	 * <br /><br />
	 * The URL that should be redirected-to when the user clicks on the "Return to Full Page" link in the Liferay Portal Permissions UI.
	 */
	public void setRedirect(String redirect) {
		getStateHelper().put(PermissionsURLPropertyKeys.redirect, redirect);
	}

	/**
	 * <code>resourceGroupId</code> attribute description:
	 * <br /><br />
	 * The scope group id for the resource. The default value is <code>LiferayFacesContext.getInstance().getScopeGroupId()</code>
	 */
	public String getResourceGroupId() {
		return (String) getStateHelper().eval(PermissionsURLPropertyKeys.resourceGroupId, null);
	}

	/**
	 * <code>resourceGroupId</code> attribute description:
	 * <br /><br />
	 * The scope group id for the resource. The default value is <code>LiferayFacesContext.getInstance().getScopeGroupId()</code>
	 */
	public void setResourceGroupId(String resourceGroupId) {
		getStateHelper().put(PermissionsURLPropertyKeys.resourceGroupId, resourceGroupId);
	}

	/**
	 * <code>resourcePrimKey</code> attribute description:
	 * <br /><br />
	 * The primary key used to identify the resource.
	 */
	public String getResourcePrimKey() {
		return (String) getStateHelper().eval(PermissionsURLPropertyKeys.resourcePrimKey, null);
	}

	/**
	 * <code>resourcePrimKey</code> attribute description:
	 * <br /><br />
	 * The primary key used to identify the resource.
	 */
	public void setResourcePrimKey(String resourcePrimKey) {
		getStateHelper().put(PermissionsURLPropertyKeys.resourcePrimKey, resourcePrimKey);
	}

	/**
	 * <code>roleTypes</code> attribute description:
	 * <br /><br />
	 * An array of type int that corresponds to the roles that are to be displayed in the Liferay Portal Permissions UI. For more info, see <a href="http://docs.liferay.com/portal/6.2/javadocs/com/liferay/portal/model/RoleConstants.html" target="_blank">com.liferay.portal.model.RoleConstants</a>.
	 */
	public int[] getRoleTypes() {
		return (int[]) getStateHelper().eval(PermissionsURLPropertyKeys.roleTypes, null);
	}

	/**
	 * <code>roleTypes</code> attribute description:
	 * <br /><br />
	 * An array of type int that corresponds to the roles that are to be displayed in the Liferay Portal Permissions UI. For more info, see <a href="http://docs.liferay.com/portal/6.2/javadocs/com/liferay/portal/model/RoleConstants.html" target="_blank">com.liferay.portal.model.RoleConstants</a>.
	 */
	public void setRoleTypes(int[] roleTypes) {
		getStateHelper().put(PermissionsURLPropertyKeys.roleTypes, roleTypes);
	}

	/**
	 * <code>var</code> attribute description:
	 * <br /><br />
	 * The name of the variable that is to be introduced into the EL that contains the value of the generated permissions URL. If not specified, then the generated permissions URL is written directly to the response.
	 */
	public String getVar() {
		return (String) getStateHelper().eval(PermissionsURLPropertyKeys.var, null);
	}

	/**
	 * <code>var</code> attribute description:
	 * <br /><br />
	 * The name of the variable that is to be introduced into the EL that contains the value of the generated permissions URL. If not specified, then the generated permissions URL is written directly to the response.
	 */
	public void setVar(String var) {
		getStateHelper().put(PermissionsURLPropertyKeys.var, var);
	}

	/**
	 * <code>windowState</code> attribute description:
	 * <br /><br />
	 * The portlet window state for which the Liferay Portal Permissions UI should be displayed. Valid values include: "maximized" (the default), "pop_up", and "exclusive".
	 */
	public String getWindowState() {
		return (String) getStateHelper().eval(PermissionsURLPropertyKeys.windowState, null);
	}

	/**
	 * <code>windowState</code> attribute description:
	 * <br /><br />
	 * The portlet window state for which the Liferay Portal Permissions UI should be displayed. Valid values include: "maximized" (the default), "pop_up", and "exclusive".
	 */
	public void setWindowState(String windowState) {
		getStateHelper().put(PermissionsURLPropertyKeys.windowState, windowState);
	}
}
//J+
