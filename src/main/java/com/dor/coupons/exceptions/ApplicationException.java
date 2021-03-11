package com.dor.coupons.exceptions;

import com.dor.coupons.enums.ExceptionType;

@SuppressWarnings("serial")
public class ApplicationException extends Exception {

	private ExceptionType exceptionType;

	public ApplicationException(ExceptionType exceptionType, String message) {
		super(message);
		this.exceptionType = exceptionType;
	}

	public ApplicationException(Exception e, ExceptionType exceptionType, String message) {
		super(message, e);
		this.exceptionType = exceptionType;
	}

	public ExceptionType getExceptionType() {
		return exceptionType;
	}

}
