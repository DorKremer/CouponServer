package com.dor.coupons.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.dor.coupons.dto.ProvidedUserDTO;
import com.dor.coupons.enums.UserTypes;
import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@Table(name = "users")
public class User implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "user_name", nullable = false, unique=true)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Enumerated(EnumType.STRING)
	@Column(name = "user_type", nullable = false)
	private UserTypes usersTypes;

	@ManyToOne
	private Company company;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Purchase> purchases;

	public User() {

	}

	public User(ProvidedUserDTO dto) {
		this.id = dto.getId();
		this.username = dto.getUserName();
		this.password = dto.getPassword();
		this.firstName = dto.getFirstName();
		this.lastName = dto.getLastName();
		this.usersTypes = dto.getUsersTypes();
		this.company = null;
		this.purchases = null;
	}
	// CTOR for testing purposes

	public User(String username, String password, String firstName, String lastName, UserTypes usersTypes,
			Company company, List<Purchase> purchases) {

		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.usersTypes = usersTypes;
		this.company = company;
		this.purchases = purchases;
	}

	// CTOR for extraction of data from DB
	public User(long id, String username, String password, String firstName, String lastName, UserTypes usersTypes,
			Company company, List<Purchase> purchases) {
		this(username, password, firstName, lastName, usersTypes, company, purchases);
		this.id = id;

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
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
		return usersTypes;
	}

	public void setUsersTypes(UserTypes usersTypes) {
		this.usersTypes = usersTypes;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", usersTypes=" + usersTypes + ", company=" + company + ", purchases="
				+ purchases + "]";
	}

}