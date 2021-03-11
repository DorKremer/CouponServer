package com.dor.coupons.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.dor.coupons.dao.ICompanyDAO;
import com.dor.coupons.dto.CompanyDTO;
import com.dor.coupons.entities.Company;
import com.dor.coupons.enums.ExceptionType;
import com.dor.coupons.exceptions.ApplicationException;

@Controller
public class CompaniesController {

	@Autowired
	private ICompanyDAO companiesDao;

	// CTOR
	public CompaniesController() {
	}

	public long createCompany(CompanyDTO company) throws ApplicationException {
		validateCreateCompany(company);
		Company companyToSave = new Company(company);
		try {
			this.companiesDao.save(companyToSave);
		} catch (Exception e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());
		}
		return companyToSave.getId();
	}

	public void updateCompany(CompanyDTO company) throws ApplicationException {
		validateUpdateCompany(company);
		Company companyToSave = new Company(company);
		try {
			this.companiesDao.save(companyToSave);
		} catch (Exception e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());
		}
	}

	public void deleteCompany(long id) throws ApplicationException {
		try {
			this.companiesDao.deleteById(id);
		} catch (Exception e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());
		}
	}

	public CompanyDTO getCompany(long id) throws ApplicationException {
		CompanyDTO companyDto;
		try {
			companyDto = new CompanyDTO(this.companiesDao.findById(id).get());
		} catch (Exception e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());

		}
		return companyDto;
	}

	public CompanyDTO findByName(String name) {
		return this.companiesDao.findByName(name);
	}

	public List<CompanyDTO> getAllCompanies() throws ApplicationException {
		List<Company> companies;
		try {
			companies = (List<Company>) this.companiesDao.findAll();
		} catch (Exception e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());

		}

		// turns the Entities to DTO's
		List<CompanyDTO> companiesDto = new ArrayList<CompanyDTO>();
		for (Company company : companies) {
			CompanyDTO companyDto = new CompanyDTO(company);
			companiesDto.add(companyDto);
		}
		return companiesDto;
	}
	

	private void validateCreateCompany(CompanyDTO company) throws ApplicationException {
		try {
			// checking there's no duplicate name in DB
			if (companiesDao.existsByName(company.getName())) {
				throw new ApplicationException(ExceptionType.NAME_ALREADY_EXISTS,
						ExceptionType.NAME_ALREADY_EXISTS.getExceptionMessage());
			}
		} catch (ApplicationException e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());

		}
		validateText(company.getPhoneNumber(), "Phone number", 10);
		validateText(company.getName(), "Name", 2);
		validateText(company.getAddress(), "Address", 2);
	}

	private void validateUpdateCompany(CompanyDTO company) throws ApplicationException {
		try {
			// checking if the company already exists in DB
			this.companiesDao.findById(company.getId()).get();
		} catch (Exception e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());

		}
		validateText(company.getAddress(), "Address", 2);
		validateText(company.getPhoneNumber(), "Phone number", 10);
	}

	private void validateText(String txt, String title, int minLength) throws ApplicationException {
		if (txt == null) {
			throw new ApplicationException(ExceptionType.MUST_INSERT_A_VALUE, title + " must have an input");
		}
		if (txt.length() < minLength) {
			throw new ApplicationException(ExceptionType.INPUT_TOO_SHORT,
					title + " must have an input longer than " + minLength);
		}
	}
	
	public Company getCompanyEntity(String name) {
		return this.companiesDao.getCompanyEntity(name);
	}
	

}
