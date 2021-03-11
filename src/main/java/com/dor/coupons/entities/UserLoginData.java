package com.dor.coupons.entities;

import com.dor.coupons.enums.UserTypes;

public class UserLoginData {
	private long userId;
	private UserTypes userType;
	private Long companyId;

	public UserLoginData(User user) {
		this.userId = user.getId();
		this.userType = user.getUsersTypes();
		if (user.getCompany() == null) {
			this.companyId = (long) 0;
		} else {
			this.companyId = user.getCompany().getId();
		}
	}

	public UserLoginData() {

	}

	public UserLoginData(long userId, UserTypes userType, Long companyId) {
		super();
		this.userId = userId;
		this.userType = userType;
		this.companyId = companyId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public UserTypes getUserType() {
		return userType;
	}

	public void setUserType(UserTypes userType) {
		this.userType = userType;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

}
