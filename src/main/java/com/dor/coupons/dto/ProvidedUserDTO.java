package com.dor.coupons.dto;

import com.dor.coupons.entities.User;
import com.dor.coupons.enums.UserTypes;

public class ProvidedUserDTO {

	private long id;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private UserTypes userType;
	private String companyName;

	public ProvidedUserDTO(User user) {
		this.id = user.getId();
		this.userName = user.getUsername();
		this.password = user.getPassword();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.userType = user.getUsersTypes();
		if (user.getCompany() == null) {
			this.companyName = "";
		} else {
			this.companyName = user.getCompany().getName();
		}
	}

	public ProvidedUserDTO() {

	}

	public ProvidedUserDTO(long id, String userName, String password, String firstName, String lastName, UserTypes usersTypes,
			String companyName) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userType = usersTypes;
		this.companyName = companyName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String name) {
		this.companyName = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", userName=" + userName + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", usersTypes=" + userType + "]";
	}

}
