package com.dor.coupons.dto;

import com.dor.coupons.entities.Company;

public class CompanyDTO {

	private long id;
	private String name;
	private String address;
	private String phoneNumber;
	private Long numberOfEmployees;
	private Long numberOfCoupons;

	public CompanyDTO() {

	}

	public CompanyDTO(Company company) {
		this.id = company.getId();
		this.name = company.getName();
		this.address = company.getAddress();
		this.phoneNumber = company.getPhoneNumber();
		this.numberOfEmployees = (long) company.getUsers().size();
		this.numberOfCoupons = (long) company.getCoupons().size();
	}

	public CompanyDTO(long id, String name, String address, String phoneNumber) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
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

	public Long getNumberOfEmployees() {
		return numberOfEmployees;
	}

	public void setNumberOfEmployees(Long numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}

	public Long getNumberOfCoupons() {
		return numberOfCoupons;
	}

	public void setNumberOfCoupons(Long numberOfCoupons) {
		this.numberOfCoupons = numberOfCoupons;
	}

	@Override
	public String toString() {
		return "CompanyDTO [id=" + id + ", name=" + name + ", address=" + address + ", phoneNumber=" + phoneNumber
				+ "]";
	}

}
