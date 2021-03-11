package com.dor.coupons.dto;

import java.sql.Date;

import com.dor.coupons.entities.Coupon;
import com.dor.coupons.enums.CouponTypes;

public class CouponDTO {

	private Long id;
	private String name;
	private String description;
	private double price;
	private Date startDate;
	private Date endDate;
	private CouponTypes category;
	private long amount;
	private String companyName;

	public CouponDTO(Coupon c) {
		this.id = c.getId();
		this.name = c.getName();
		this.description = c.getDescription();
		this.price = c.getPrice();
		this.startDate = c.getStartDate();
		this.endDate = c.getEndDate();
		this.category = c.getCategory();
		this.amount = c.getAmount();
		this.companyName = c.getCompany().getName();
	}

	public CouponDTO(String name, String description, double price, Date startDate, Date endDate, CouponTypes category,
			long amount, String companyName) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.startDate = startDate;
		this.endDate = endDate;
		this.category = category;
		this.amount = amount;
		this.companyName = companyName;
	}

	public CouponDTO() {

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public CouponTypes getCategory() {
		return category;
	}

	public void setCategory(CouponTypes category) {
		this.category = category;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public String toString() {
		return "CouponDTO [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", category=" + category + ", amount=" + amount
				+ ", companyName=" + companyName + "]";
	}

	

}
