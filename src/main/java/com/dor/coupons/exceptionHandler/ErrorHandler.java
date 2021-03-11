package com.dor.coupons.exceptionHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dor.coupons.entities.ExceptionBean;
import com.dor.coupons.enums.ExceptionType;
import com.dor.coupons.exceptions.ApplicationException;

// Exception handler class
@SuppressWarnings("unused")
@RestControllerAdvice
public class ErrorHandler {

	// Response - Object in Spring
	@ExceptionHandler
	@ResponseBody
	// Variable name is throwable in order to remember that it handles Exception and
	// Error
	public ExceptionBean toResponse(Throwable throwable, HttpServletResponse response) {

		if (throwable instanceof ApplicationException) {

			ApplicationException appException = (ApplicationException) throwable;

			ExceptionType errorType = appException.getExceptionType();
			int errorNumber = errorType.getExceptionNumber();
			String errorMessage = errorType.getExceptionMessage();
			response.setStatus(errorNumber);

			ExceptionBean errorBean = new ExceptionBean(errorNumber, errorMessage);
			if (appException.getExceptionType().isShowStackTrace()) {
				appException.printStackTrace();
			}

			return errorBean;
		}

		response.setStatus(600);

		String errorMessage = throwable.getMessage();
		ExceptionBean errorBean = new ExceptionBean(601, errorMessage);
		throwable.printStackTrace();

		return errorBean;
	}

}

//	@ExceptionHandler(ApplicationException.class)
//	public ErrorBean applicationExceptionHandler(HttpServletResponse response, ApplicationException applicationExction) {
//
//		ErrorType errorType = applicationExction.getErrorType(); 
//		int errorNumber = errorType.getErrorNumber();
//		String errorMessage = errorType.getErrorMessage();
//		String errorName = errorType.getErrorName();
//
//		ErrorBean errorBean = new ErrorBean(errorNumber, errorMessage, errorName); 
//		response.setStatus(errorNumber);
//
//		//		check is critical - parameter in exceptions that we created
//		if(applicationExction.getErrorType().isShowStackTrace()) {
//			applicationExction.printStackTrace();
//		}
//
//		return errorBean;
//	}
//
//	@ExceptionHandler(Exception.class)
//	public ErrorBean ExceptionHandler(HttpServletResponse response, Exception exception) {
//
//		int errorNumber = 601;
//		String errorMessage = exception.getMessage();
//
//		ErrorBean errorBean = new ErrorBean(errorNumber, errorMessage, "GENERAL ERROR");
//		response.setStatus(errorNumber);
//		exception.printStackTrace();
//
//		return errorBean;
//	}
