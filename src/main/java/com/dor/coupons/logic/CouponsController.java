package com.dor.coupons.logic;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.dor.coupons.dao.ICouponDAO;
import com.dor.coupons.dto.CompanyDTO;
import com.dor.coupons.dto.CouponDTO;
import com.dor.coupons.entities.Company;
import com.dor.coupons.entities.Coupon;
import com.dor.coupons.enums.CouponTypes;
import com.dor.coupons.enums.ExceptionType;
import com.dor.coupons.exceptions.ApplicationException;

@Controller
public class CouponsController {

	@Autowired
	private ICouponDAO couponDao;

	@Autowired
	private CompaniesController companyController;

	public CouponsController() {
	}

	public CouponDTO getCoupon(long id) throws ApplicationException {
		CouponDTO couponDto = null;
		try {
			couponDto = new CouponDTO(this.couponDao.findById(id).get());
		} catch (Exception e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());
		}
		return couponDto;
	}

	public CouponDTO findByName(String name) {
		return this.couponDao.findByName(name);
	}

	public long createCoupon(CouponDTO coupon) throws ApplicationException {
		validateCreateCoupon(coupon);

		// turning the DTO to an Entity and inserts a company
		Coupon couponToSave = this.dtoToEntity(coupon);
		try {
			this.couponDao.save(couponToSave);
		} catch (Exception e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());
		}
		return couponToSave.getId();
	}

	public void updateCoupon(CouponDTO coupon) throws ApplicationException {

		// checking if the coupon already exists
		this.couponDao.findById(coupon.getId());
		validateUpdateCoupon(coupon);

		// turning the DTO to an Entity and inserts a company
		Coupon couponToSave = this.dtoToEntity(coupon);
		try {
			this.couponDao.save(couponToSave);
		} catch (Exception e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());
		}
	}

	public List<CouponDTO> getAllCoupons() throws ApplicationException {
		List<Coupon> coupons = null;
		try {
			coupons = (List<Coupon>) this.couponDao.findAll();
		} catch (Exception e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());
		}

		// turns the Entities to DTO's
		List<CouponDTO> newCoupons = new ArrayList<CouponDTO>();
		for (Coupon c : coupons) {
			CouponDTO couponDto = new CouponDTO(c);
			newCoupons.add(couponDto);
		}
		return newCoupons;
	}

	public void deleteCoupon(long id) throws ApplicationException {
		try {
			this.couponDao.deleteById(id);
		} catch (Exception e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());
		}
	}

	public List<CouponDTO> getCouponsByCompany(long companyID) throws ApplicationException {
		List<CouponDTO> coupons = null;
		
		try {
			coupons = this.couponDao.getCouponsByCompany(companyID);
		} catch (ApplicationException e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());
		}
		
		return coupons;
	}

	public List<CouponDTO> getCouponsByType(CouponTypes type) throws ApplicationException {
		List<CouponDTO> coupons = null;
		try {
			coupons = this.couponDao.getCouponsByType(type);
		} catch (ApplicationException e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());
		}

		return coupons;
	}

	public List<CouponDTO> getPurchasedCouponsByMaxPrice(long userID, double maxPrice) throws ApplicationException {
		List<CouponDTO> coupons = null;
		try {
			coupons = this.couponDao.getPurchasedCouponsByMaxPrice(userID, maxPrice);
		} catch (ApplicationException e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());
		}

		return coupons;
	}
	
	public void removeExpiredCoupons() throws ApplicationException {
		Date date=new Date(System.currentTimeMillis());
		try {
			this.couponDao.removeExpired(date);
		} catch (ApplicationException e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());
		}
	}

	// validating the name is legal+doesn't exist in DB already and date of a coupon
	public void validateCreateCoupon(CouponDTO coupon) throws ApplicationException {
		validateText(coupon.getName(), "Name", 2);
		long millis = System.currentTimeMillis();
		Date now = new Date(millis);

		if (coupon.getEndDate().before(now)) {
			throw new ApplicationException(ExceptionType.INVALID_DATES,
					ExceptionType.INVALID_DATES.getExceptionMessage());
		}

		if (couponDao.existsByName(coupon.getName())) {
			throw new ApplicationException(ExceptionType.COUPON_TITLE_EXIST,
					ExceptionType.COUPON_TITLE_EXIST.getExceptionMessage());
		}
	}

	// validating the name and date of a coupon
	public void validateUpdateCoupon(CouponDTO coupon) throws ApplicationException {
		validateText(coupon.getName(), "Name", 2);
		long millis = System.currentTimeMillis();
		Date now = new Date(millis);

		if (coupon.getEndDate().before(now)) {
			throw new ApplicationException(ExceptionType.INVALID_DATES,
					ExceptionType.INVALID_DATES.getExceptionMessage());
		}
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

	// turning the DTO to an Entity and inserting a company
	public Coupon dtoToEntity(CouponDTO dto) throws ApplicationException {
		Coupon originalCoupon = new Coupon(dto);
		CompanyDTO companyDto = companyController.findByName(dto.getCompanyName());
		Company company = new Company(companyDto);
		originalCoupon.setCompany(company);
		return originalCoupon;
	}

}
