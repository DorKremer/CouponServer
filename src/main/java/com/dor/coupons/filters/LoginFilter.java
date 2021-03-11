package com.dor.coupons.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.dor.coupons.entities.UserLoginData;
import com.dor.coupons.logic.CacheController;

@Component

public class LoginFilter implements Filter {

	@Autowired
	private CacheController cacheController;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String pageRequested = httpRequest.getRequestURL().toString();

		if (pageRequested.endsWith("/login")) {
			chain.doFilter(request, response);
			return;
		}
		
		if (pageRequested.endsWith("/register")) {
			chain.doFilter(request, response);
			return;
		}
		
		if (pageRequested.endsWith("/coupons") && httpRequest.getMethod().toString().equals("GET")) {
			chain.doFilter(request, response);
			return;
		}

		if (pageRequested.endsWith("/users") && httpRequest.getMethod().toString().equals("POST")) {
			chain.doFilter(request, response);
			return;
		}

		String token = httpRequest.getHeader("Authorization");
		UserLoginData userLoginData = (UserLoginData) cacheController.get(token);

		if (userLoginData != null) {
			httpRequest.setAttribute("userLoginData", userLoginData);
			chain.doFilter(httpRequest, response);
			return;
		}

		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setStatus(401);

	}

}
