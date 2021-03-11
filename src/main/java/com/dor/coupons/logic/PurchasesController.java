package com.dor.coupons.logic;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.dor.coupons.dao.IPurchaseDAO;
import com.dor.coupons.dto.CouponDTO;
import com.dor.coupons.dto.PurchaseDTO;
import com.dor.coupons.dto.ProvidedUserDTO;
import com.dor.coupons.entities.Coupon;
import com.dor.coupons.entities.Purchase;
import com.dor.coupons.entities.User;
import com.dor.coupons.enums.ExceptionType;
import com.dor.coupons.exceptions.ApplicationException;

@Controller
public class PurchasesController {

	@Autowired
	private IPurchaseDAO purchaseDao;

	@Autowired
	private CouponsController couponController;

	@Autowired
	private UsersController usersController;

	public PurchasesController() {

	}

	public long createPurchase(PurchaseDTO purchase) throws ApplicationException {

		// checking if the purchase has legal values
		validateUpdatePurchase(purchase);

		// converting DTO to entity
		Purchase purchaseToSave = new Purchase(purchase);
		purchaseToSave = this.dtoToEntity(purchase);

		// the coupon amount logic:
		// extracting the coupon DTO
		CouponDTO coupon = couponController.findByName(purchase.getCouponName());

		// calculating the new amount for the coupon
		coupon.setAmount(coupon.getAmount() - purchase.getAmount());

		// saving coupon with updated amount to DB
		couponController.updateCoupon(coupon);

		Date date = new Date(System.currentTimeMillis()+2000);
		purchaseToSave.setTimeStamp(date);
		
		try {
			this.purchaseDao.save(purchaseToSave);
		} catch (Exception e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());
		}
		return purchaseToSave.getId();
	}

	public PurchaseDTO getPurchase(long purchaseID) throws ApplicationException {
		PurchaseDTO purchase;
		try {
			purchase = this.purchaseDao.getPurchase(purchaseID);
		} catch (Exception e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());
		}
		return purchase;
	}
	
	public Purchase getPurchaseEntity(long id) {
		return this.purchaseDao.getPurchaseEntity(id);
	}

	public void deletePurchase(long purchaseID) throws ApplicationException {
		try {
			this.purchaseDao.deleteById(purchaseID);
		} catch (Exception e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());
		}
	}

	public void updatePurchase(PurchaseDTO purchase) throws ApplicationException {
		validateUpdatePurchase(purchase);

		PurchaseDTO savedPurchase=this.getPurchase(purchase.getId());
		purchase.setUserName(savedPurchase.getUserName());
		// converting DTO to entity
		Purchase purchaseToSave = new Purchase(purchase);
		purchaseToSave = this.dtoToEntity(purchase);

		// the coupon amount logic:
		// extracting the coupon DTO
		CouponDTO coupon = couponController.findByName(purchase.getCouponName());

		// calculating the new amount for the coupon
		coupon.setAmount(coupon.getAmount() - purchase.getAmount());

		// saving coupon with updated amount to DB
		couponController.updateCoupon(coupon);
		
		Date date = new Date(System.currentTimeMillis()+2000);
		purchaseToSave.setTimeStamp(date);
		
		try {
			purchaseDao.save(purchaseToSave);
		} catch (Exception e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());
		}

	}

	public List<PurchaseDTO> getAllPurchases() throws ApplicationException {
		List<Purchase> purchases;
		try {
			purchases = (List<Purchase>) this.purchaseDao.findAll();
		} catch (Exception e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());
		}

		// turns the Entities to DTO's
		List<PurchaseDTO> newPurchases = new ArrayList<PurchaseDTO>();
		for (Purchase p : purchases) {
			PurchaseDTO purchase = new PurchaseDTO(p);
			newPurchases.add(purchase);
		}

		return newPurchases;
	}

	public List<PurchaseDTO> getAllPurchases(long userID) throws ApplicationException {
		List<PurchaseDTO> purchases;
		try {
			purchases = this.purchaseDao.getAllPurchases(userID);
		} catch (ApplicationException e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());
		}

		return purchases;
	}

	public List<PurchaseDTO> getAllPurchasesByCompany(long companyID) throws ApplicationException {
		List<PurchaseDTO> purchases;
		try {
			purchases = this.purchaseDao.getAllPurchasesByCompany(companyID);
		} catch (ApplicationException e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());
		}
		return purchases;
	}

	public void validateUpdatePurchase(PurchaseDTO purchase) throws ApplicationException {

		// verify purchase already exists
		try {
			this.purchaseDao.findById(purchase.getId());
		} catch (Exception e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());
		}
		CouponDTO coupon = new CouponDTO();
		Purchase originalPurchase = new Purchase(purchase);
		originalPurchase = this.dtoToEntity(purchase);
		long id = originalPurchase.getCoupon().getId();
		coupon = couponController.getCoupon(id);
		if (purchase.getAmount() <= 0) {
			throw new ApplicationException(ExceptionType.INVALID_AMOUNT,
					ExceptionType.INVALID_AMOUNT.getExceptionMessage());
		}
		if (coupon.getAmount() < purchase.getAmount()) {
			throw new ApplicationException(ExceptionType.NOT_ENOUGH_COUPONS_LEFT,
					ExceptionType.NOT_ENOUGH_COUPONS_LEFT.getExceptionMessage());
		}

	}

	public void validateCreatePurchase(PurchaseDTO purchase) throws ApplicationException {
		CouponDTO coupon = new CouponDTO();
		Purchase originalPurchase = new Purchase(purchase);
		originalPurchase = this.dtoToEntity(purchase);
		long id = originalPurchase.getCoupon().getId();
		coupon = couponController.getCoupon(id);
		if (purchase.getAmount() <= 0) {
			throw new ApplicationException(ExceptionType.INVALID_AMOUNT,
					ExceptionType.INVALID_AMOUNT.getExceptionMessage());
		}
		if (coupon.getAmount() < purchase.getAmount()) {
			throw new ApplicationException(ExceptionType.NOT_ENOUGH_COUPONS_LEFT,
					ExceptionType.NOT_ENOUGH_COUPONS_LEFT.getExceptionMessage());
		}
	}

	// adds a user entity and a coupon entity to the purchase entity created from
	// the DTO supplied
	public Purchase dtoToEntity(PurchaseDTO dto) throws ApplicationException {
		Purchase originalPurchase = new Purchase(dto);
		CouponDTO couponDto = couponController.findByName(dto.getCouponName());
//		Coupon coupon = new Coupon(couponDto);
		Coupon coupon =couponController.dtoToEntity(couponDto);
		originalPurchase.setCoupon(coupon);
		User user = usersController.getUserByUsername(dto.getUserName());
		originalPurchase.setUser(user);
		return originalPurchase;
	}
	
	public void deletePurchasesOfExpiredCoupons() {
		Date date=new Date(System.currentTimeMillis());
		this.purchaseDao.deletePurchasesOfExpiredCoupons(date);
	}

}
