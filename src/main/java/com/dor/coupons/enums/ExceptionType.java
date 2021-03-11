package com.dor.coupons.enums;

public enum ExceptionType {
	INPUT_TOO_SHORT(600, "Input must be longer", false), GENERAL_EXCEPTION(601, "General exception", true),
	LOGIN_FAILED(603, "Login failed. Please try again.", false),
	NAME_ALREADY_EXISTS(604, "The name you chose already exists. Please enter another name", false),
	MUST_ENTER_NAME(605, "Can not insert an null/empty name", false),
	MUST_ENTER_ADDRESS(606, "Can not insert an null/empty address", false),
	ID_DOES_NOT_EXIST(607, "This ID does'nt exist", false),
	INVALID_PASSWORD(608,
			"Password must contain at least 8 charaters, only UpperCase lettersand and at least one digit", false),
	NOT_ENOUGH_COUPONS_LEFT(609, "Not enough coupons left to purchase the amount requested", false),
	COUPON_EXPIERED(610, "The coupon is expiered", false),
	COUPON_TITLE_EXIST(611, "The title of this coupon is already exists, please change the title", false),
	INVALID_PRICE(612, "Coupon price must be more than 0", false),
	INVALID_EMAIL(613, "Email address is InValid, Please enter a valid email address", false),
	INVALID_AMOUNT(614, "Coupon's amount must be more than 0", false),
	INVALID_DATES(615, "The dates you've entered are wrong", false),
	MUST_INSERT_A_VALUE(616, "Must insert a value", false), NAME_CANT_BE_NULL(617, "Name must not be null", false),
	FEATURE_UNAVAILABLE_FOR_YOU(618, "Feature is unavailable for you", false);

	private int exceptionNumber;
	private String exceptionMessage;
	private boolean isShowStackTrace;

	private ExceptionType(int exceptionNumber, String internalMessage, boolean isShowStackTrace) {
		this.exceptionNumber = exceptionNumber;
		this.exceptionMessage = internalMessage;
		this.isShowStackTrace = isShowStackTrace;
	}

	private ExceptionType(int exceptionNumber, String internalMessage) {
		this.exceptionNumber = exceptionNumber;
		this.exceptionMessage = internalMessage;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public boolean isShowStackTrace() {
		return isShowStackTrace;
	}

	public int getExceptionNumber() {
		return exceptionNumber;
	}

}
