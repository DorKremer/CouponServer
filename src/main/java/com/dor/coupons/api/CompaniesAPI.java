package com.dor.coupons.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dor.coupons.dto.CompanyDTO;
import com.dor.coupons.entities.UserLoginData;
import com.dor.coupons.enums.UserTypes;
import com.dor.coupons.exceptions.ApplicationException;
import com.dor.coupons.logic.CompaniesController;
import com.dor.coupons.logic.UsersController;

@RestController
@RequestMapping("/companies")
public class CompaniesAPI {

	@Autowired
	CompaniesController companiesContoller;

	@Autowired
	UsersController usersController;

	@PostMapping
	public boolean createCompany(@RequestBody CompanyDTO company) throws ApplicationException {
		this.companiesContoller.createCompany(company);
		return true;
	}

	@PutMapping
	public void updateCompany(@RequestBody CompanyDTO company, HttpServletRequest request) throws ApplicationException {
		UserLoginData userLoginData = (UserLoginData) request.getAttribute("userLoginData");
		if (userLoginData.getUserType() == UserTypes.ADMIN) {
			this.companiesContoller.updateCompany(company);
			return;
		}
		if (userLoginData.getCompanyId() != company.getId()) {
			return;
		}
		this.companiesContoller.updateCompany(company);
	}

	@DeleteMapping("/{companyID}")
	public void deleteCompany(@PathVariable("companyID") long companyID, HttpServletRequest request)
			throws ApplicationException {
		UserLoginData userLoginData = (UserLoginData) request.getAttribute("userLoginData");
		if (userLoginData.getUserType() == UserTypes.ADMIN) {
			this.companiesContoller.deleteCompany(companyID);
			return;
		}
		if (userLoginData.getCompanyId() != companyID) {
			return;
		}
		this.companiesContoller.deleteCompany(companyID);
	}

	@GetMapping
	public List<CompanyDTO> getAllCompanies() throws ApplicationException {
		return this.companiesContoller.getAllCompanies();
	}

	@GetMapping("/{companyID}")
	public CompanyDTO getCompany(@PathVariable("companyID") long companyID) throws ApplicationException {
		return this.companiesContoller.getCompany(companyID);
	}
}
