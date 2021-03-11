package com.dor.coupons.dto;

import com.dor.coupons.entities.User;
import com.dor.coupons.enums.UserTypes;

public class ReturnedUserDTO {

	private String username;
	private String firstName;
	private String lastName;
	private UserTypes userType;
	private String companyName;

	public ReturnedUserDTO(User user) {
		this.username = user.getUsername();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.userType = user.getUsersTypes();
		if (user.getCompany() == null) {
			this.companyName = "";
		} else {
			this.companyName = user.getCompany().getName();
		}
	}

	public ReturnedUserDTO(String userName, String firstName, String lastName, UserTypes usersTypes,
			String companyName) {
		super();
		this.username = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userType = usersTypes;
		this.companyName = companyName;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
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

	public UserTypes getUsersTypes() {
		return userType;
	}

	public void setUsersTypes(UserTypes usersTypes) {
		this.userType = usersTypes;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public String toString() {
		return "ReturnableUserDTO [userName=" + username + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", usersTypes=" + userType + ", companyName=" + companyName + "]";
	}

}
