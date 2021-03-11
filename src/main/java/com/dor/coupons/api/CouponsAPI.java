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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dor.coupons.dto.CouponDTO;
import com.dor.coupons.entities.UserLoginData;
import com.dor.coupons.enums.CouponTypes;
import com.dor.coupons.enums.ExceptionType;
import com.dor.coupons.enums.UserTypes;
import com.dor.coupons.exceptions.ApplicationException;
import com.dor.coupons.logic.CompaniesController;
import com.dor.coupons.logic.CouponsController;

@RestController
@RequestMapping("/coupons")
public class CouponsAPI {

	@Autowired
	CouponsController couponsController;

	@Autowired
	CompaniesController companyController;

	@PostMapping
	public boolean createCoupon(@RequestBody CouponDTO coupon, HttpServletRequest request) throws ApplicationException {
		UserLoginData userLoginData = (UserLoginData) request.getAttribute("userLoginData");
		coupon.setId(0);
		if (userLoginData.getUserType() == UserTypes.ADMIN) {
			this.couponsController.createCoupon(coupon);
			return true;
		}
		coupon.setCompanyName(companyController.getCompany(userLoginData.getCompanyId()).getName());
		this.couponsController.createCoupon(coupon);
		return true;
	}

	@PutMapping
	public void updateCoupon(@RequestBody CouponDTO coupon, HttpServletRequest request) throws ApplicationException {
		UserLoginData userLoginData = (UserLoginData) request.getAttribute("userLoginData");
		if (userLoginData.getUserType() == UserTypes.ADMIN) {
			this.couponsController.updateCoupon(coupon);
			return;
		}
		coupon.setCompanyName(companyController.getCompany(userLoginData.getCompanyId()).getName());
		this.couponsController.updateCoupon(coupon);
	}

	@DeleteMapping("/{couponID}")
	public long deleteCoupon(@PathVariable("couponID") long couponID, HttpServletRequest request)
			throws ApplicationException {
		UserLoginData userLoginData = (UserLoginData) request.getAttribute("userLoginData");
		CouponDTO couponChanging = couponsController.getCoupon(couponID);

		// checks if user is an admin, if so then immediately deletes coupon
		if (userLoginData.getUserType() == UserTypes.ADMIN) {
			this.couponsController.deleteCoupon(couponID);
			return 0;
		}

		if (userLoginData.getCompanyId() == null || couponChanging.getCompanyName() !=companyController.getCompany(userLoginData.getCompanyId()).getName() ) {
			throw new ApplicationException(ExceptionType.FEATURE_UNAVAILABLE_FOR_YOU,
					ExceptionType.FEATURE_UNAVAILABLE_FOR_YOU.getExceptionMessage());
		}
		this.couponsController.deleteCoupon(couponID);
		return couponID;
	}

	@GetMapping("/{couponID}")
	public CouponDTO getCoupon(@PathVariable("couponID") long couponID) throws ApplicationException {
		return this.couponsController.getCoupon(couponID);
	}

	@GetMapping
	public List<CouponDTO> getAllCoupons() throws ApplicationException {
		return this.couponsController.getAllCoupons();
	}

	@GetMapping("/getCouponsByCompanyID")
	public List<CouponDTO> getCouponsByCompanyID(HttpServletRequest request)
			throws ApplicationException {
		UserLoginData userLoginData = (UserLoginData) request.getAttribute("userLoginData");
		long companyID = userLoginData.getCompanyId();
		return this.couponsController.getCouponsByCompany(companyID);
	}

	@GetMapping("/getCouponsByType")
	public List<CouponDTO> getCouponsByType(@RequestParam("couponType") CouponTypes couponType)
			throws ApplicationException {
		return this.couponsController.getCouponsByType(couponType);
	}

	@GetMapping("/getPurchasedCouponsByMaxPrice")
	public List<CouponDTO> getPurchasedCouponsByMaxPrice(@RequestParam("userID") long userID,
			@RequestParam("maxPrice") double maxPrice) throws ApplicationException {
		return this.couponsController.getPurchasedCouponsByMaxPrice(userID, maxPrice);
	}

}
