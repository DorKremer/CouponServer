package com.dor.coupons.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.dor.coupons.dto.CompanyDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@Table(name = "companies")
public class Company implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "name", nullable = false, unique=true)
	private String name;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;

	@OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE)
	@JsonIgnore
	List<User> users;

	@OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE)
	@JsonIgnore
	List<Coupon> coupons;

	public Company() {

	}

	public Company(CompanyDTO dto) {
		this.id = dto.getId();
		this.name = dto.getName();
		this.address = dto.getAddress();
		this.phoneNumber = dto.getPhoneNumber();
		this.users = null;
		this.coupons = null;
	}

	public Company(long id, String name, String address, String phoneNumber) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.users = null;
		this.coupons = null;
	}

	public Company(String name, String address, String phoneNumber, List<User> users, List<Coupon> coupons) {
		super();
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.users = users;
		this.coupons = coupons;
	}

	public Company(long id, String name, String address, String phoneNumber, List<User> users, List<Coupon> coupons) {
		this(name, address, phoneNumber, users, coupons);
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", address=" + address + ", phoneNumber=" + phoneNumber
				+ ", users=" + users + ", coupons=" + coupons + "]";
	}

}
