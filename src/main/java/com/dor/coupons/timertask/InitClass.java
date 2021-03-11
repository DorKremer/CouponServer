package com.dor.coupons.timertask;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dor.coupons.exceptions.ApplicationException;
import com.dor.coupons.logic.CouponsController;
import com.dor.coupons.logic.PurchasesController;

@Component
@EnableScheduling
public class InitClass {

	@Autowired
	CouponsController couponController;
	
	@Autowired
	PurchasesController purchaseController;

	// Needs to change foreign key type of Purchases->Coupons to cascade
	@PostConstruct
	@Scheduled(cron = "0 0 0 * * ?")
	public void init() {

		try {
			this.purchaseController.deletePurchasesOfExpiredCoupons();
			this.couponController.removeExpiredCoupons();
		} catch (ApplicationException e) {
			System.out.println("Expired coupons removal failed");
		}

	}
}
