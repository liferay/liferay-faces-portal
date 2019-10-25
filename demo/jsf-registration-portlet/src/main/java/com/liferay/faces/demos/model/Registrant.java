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
package com.liferay.faces.demos.model;

import com.liferay.portal.kernel.model.UserWrapper;
import com.liferay.portal.kernel.service.persistence.UserUtil;


/**
 * This is a Data Transfer Object (DTO) that will contain form field data for the new registrant.
 *
 * @author  Neil Griffin
 */
public class Registrant extends UserWrapper {

	// serialVersionUID
	private static final long serialVersionUID = 8086195839473515324L;

	// Private Data Members
	private String companyName;
	private String emailAddress;
	private String favoriteColor;
	private String firstName;
	private String lastName;
	private String middleName;
	private String password1;
	private String password2;
	private String mobilePhone;
	private String screenName;

	public Registrant(long companyId) {
		super(UserUtil.create(0L));
		clearProperties();
		setCompanyId(companyId);
	}

	public void clearProperties() {
		companyName = null;
		emailAddress = null;
		favoriteColor = null;
		firstName = null;
		lastName = null;
		middleName = null;
		password1 = null;
		password2 = null;
		mobilePhone = null;
		screenName = null;
	}

	public String getCompanyName() {
		return companyName;
	}

	@Override
	public String getEmailAddress() {
		return emailAddress;
	}

	public String getFavoriteColor() {
		return favoriteColor;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public String getMiddleName() {
		return middleName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public String getPassword1() {
		return password1;
	}

	public String getPassword2() {
		return password2;
	}

	@Override
	public String getScreenName() {
		return screenName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setFavoriteColor(String favoriteColor) {
		this.favoriteColor = favoriteColor;
	}

	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	@Override
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
}
