package com.dor.coupons.api;

import java.sql.Date;
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

import com.dor.coupons.dto.PurchaseDTO;
import com.dor.coupons.dto.ProvidedUserDTO;
import com.dor.coupons.entities.Purchase;
import com.dor.coupons.entities.User;
import com.dor.coupons.entities.UserLoginData;
import com.dor.coupons.enums.UserTypes;
import com.dor.coupons.exceptions.ApplicationException;
import com.dor.coupons.logic.CouponsController;
import com.dor.coupons.logic.PurchasesController;
import com.dor.coupons.logic.UsersController;

@RestController
@RequestMapping("/purchases")
public class PurchasesAPI {

	@Autowired
	PurchasesController purchasesController;

	@Autowired
	UsersController usersController;

	@Autowired
	CouponsController couponsController;

	@PostMapping
	public long createPurchase(@RequestBody PurchaseDTO purchase, HttpServletRequest request)
			throws ApplicationException {
		UserLoginData userLoginData = (UserLoginData) request.getAttribute("userLoginData");
		purchase.setUserName(usersController.getUser(userLoginData.getUserId()).getUserName());
//		Date date = new Date(System.currentTimeMillis()+2000);
//		purchase.setTimeStamp(date);
		return this.purchasesController.createPurchase(purchase);
	}

	@PutMapping
	public void updatePurchase(@RequestBody PurchaseDTO purchase, HttpServletRequest request)
			throws ApplicationException {
		UserLoginData userLoginData = (UserLoginData) request.getAttribute("userLoginData");
		if (userLoginData.getUserType() == UserTypes.ADMIN) {
			this.purchasesController.updatePurchase(purchase);
			return;
		}
		if (usersController.getUser(userLoginData.getUserId()).getUserName() != purchase.getUserName()) {
			return;
		}
		this.purchasesController.updatePurchase(purchase);
	}

	@DeleteMapping("/{purchaseID}")
	public void deletePurchase(@PathVariable("purchaseID") long purchaseID, HttpServletRequest request)
			throws ApplicationException {
		UserLoginData userLoginData = (UserLoginData) request.getAttribute("userLoginData");
		Purchase purchase =purchasesController.getPurchaseEntity(purchaseID);
		if (userLoginData.getUserType() == UserTypes.ADMIN) {
			this.purchasesController.deletePurchase(purchaseID);
			return;
		}
		if (purchase.getUser().getId() != userLoginData.getUserId()) {
			return;
		}
		this.purchasesController.deletePurchase(purchaseID);
	}

	@GetMapping("/{purchaseID}")
	public PurchaseDTO getPurchase(@PathVariable("purchaseID") long purchaseID, HttpServletRequest request)
			throws ApplicationException {
		UserLoginData userLoginData = (UserLoginData) request.getAttribute("userLoginData");
		Purchase purchase =purchasesController.getPurchaseEntity(purchaseID);
		if (userLoginData.getUserType() == UserTypes.ADMIN) {
			return this.purchasesController.getPurchase(purchaseID);
		}
		if (purchase.getUser().getId() != userLoginData.getUserId()) {
			return null;
		}
		return this.purchasesController.getPurchase(purchaseID);
	}

	@GetMapping
	public List<PurchaseDTO> getAllPurchases(HttpServletRequest request) throws ApplicationException {
		UserLoginData userLoginData = (UserLoginData) request.getAttribute("userLoginData");
		if (userLoginData.getUserType() != UserTypes.ADMIN) {
			return null;
		}
		return this.purchasesController.getAllPurchases();
	}

	// As an admin, you must also include a User ID in the request
	@GetMapping("/allPurchasesByUser")
	public List<PurchaseDTO> getAllPurchases(@RequestParam("userID") long userID, HttpServletRequest request)
			throws ApplicationException {
		UserLoginData userLoginData = (UserLoginData) request.getAttribute("userLoginData");
		if (userLoginData.getUserType() == UserTypes.ADMIN) {
			return this.purchasesController.getAllPurchases(userID);
		}
		if (userLoginData.getUserId() != userID) {
			return null;
		}
		return this.purchasesController.getAllPurchases(userID);
	}

	@GetMapping("/getAllPurchasesByCompany")
	public List<PurchaseDTO> getAllPurchasesByCompany(
			HttpServletRequest request) throws ApplicationException {
		UserLoginData userLoginData = (UserLoginData) request.getAttribute("userLoginData");
		long companyID=userLoginData.getCompanyId();
		if (userLoginData.getUserType() == UserTypes.ADMIN) {
			return this.purchasesController.getAllPurchasesByCompany(companyID);
		}
		if (userLoginData.getCompanyId() != companyID) {
			return null;
		}
		return this.purchasesController.getAllPurchasesByCompany(companyID);
	}

}
