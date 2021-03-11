package com.dor.coupons.dto;

import java.sql.Date;

import com.dor.coupons.entities.Purchase;

public class PurchaseDTO {

	private long id;
	private Date timeStamp;
	private long amount;
	private String userName;
	private String couponName;

	public PurchaseDTO(Purchase purchase) {
		this.id = purchase.getId();
		this.timeStamp = purchase.getTimeStamp();
		this.amount = purchase.getAmount();
		this.userName = purchase.getUser().getUsername();
		this.couponName = purchase.getCoupon().getName();
	}

	public PurchaseDTO() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	@Override
	public String toString() {
		return "PurchaseDTO [id=" + id + ", timeStamp=" + timeStamp + ", amount=" + amount + ", userName=" + userName
				+ ", couponName=" + couponName + "]";
	}

	

}
