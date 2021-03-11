package com.dor.coupons.entities;

public class ExceptionBean {

	int exceptionNumber;
	String exceptionMessage;

	public ExceptionBean(int exceptionNumber, String exceptionMessage) {
		super();
		this.exceptionNumber = exceptionNumber;
		this.exceptionMessage = exceptionMessage;
	}

	public int getExceptionNumber() {
		return exceptionNumber;
	}

	public void setExceptionNumber(int exceptionNumber) {
		this.exceptionNumber = exceptionNumber;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

}
