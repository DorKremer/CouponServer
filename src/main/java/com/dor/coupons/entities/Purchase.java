package com.dor.coupons.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dor.coupons.dto.PurchaseDTO;

@SuppressWarnings("serial")
@Entity
@Table(name = "purchases")
public class Purchase implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "time_stamp", nullable = false)
	private Date timeStamp;

	@Column(name = "amount", nullable = false)
	private long amount;

	@ManyToOne
	private User user;

	@ManyToOne
	private Coupon coupon;

	public Purchase() {

	}

	public Purchase(PurchaseDTO dto) {
		this.id = dto.getId();
		this.amount = dto.getAmount();
		this.timeStamp = dto.getTimeStamp();
		this.coupon = null;
		this.user = null;
	}

	public Purchase(Date timeStamp, long amount, User user, Coupon coupon) {
		super();
		this.timeStamp = timeStamp;
		this.amount = amount;
		this.user = user;
		this.coupon = coupon;
	}

	public Purchase(long id, Date timeStamp, long amount, User user, Coupon coupon) {
		this(timeStamp, amount, user, coupon);
		this.id = id;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	@Override
	public String toString() {
		return "Purchase [id=" + id + ", timeStamp=" + timeStamp + ", amount=" + amount + ", user=" + user + ", coupon="
				+ coupon + "]";
	}

}
