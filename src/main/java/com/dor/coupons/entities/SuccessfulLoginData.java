package com.dor.coupons.entities;

import com.dor.coupons.enums.UserTypes;

public class SuccessfulLoginData {
	private long id;
	private String token;
	private UserTypes userType;
	private String firstName;
	private String lastName;
	private String companyName;
	private long companyId;

	public SuccessfulLoginData() {

	}
	
	public SuccessfulLoginData(long id, String token, UserTypes userType, String firstName, String lastName) {
		super();
		this.id = id;
		this.token = token;
		this.userType = userType;
		this.firstName = firstName;
		this.lastName = lastName;
	}



	public SuccessfulLoginData(long id, String token, UserTypes userType) {
		super();
		this.id=id;
		this.token = token;
		this.userType = userType;
	}

	public SuccessfulLoginData(long id, String token, UserTypes userType, String firstName, String lastName,
			String companyName, long companyId) {
		super();
		this.id = id;
		this.token = token;
		this.userType = userType;
		this.firstName = firstName;
		this.lastName = lastName;
		this.companyName = companyName;
		this.companyId = companyId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserTypes getUserType() {
		return userType;
	}

	public void setUserType(UserTypes userType) {
		this.userType = userType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "SuccessfulLoginData [token=" + token + ", userType=" + userType + "]";
	}

}
