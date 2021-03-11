package com.dor.coupons.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.dor.coupons.dto.PurchaseDTO;
import com.dor.coupons.entities.Purchase;
import com.dor.coupons.exceptions.ApplicationException;

public interface IPurchaseDAO extends CrudRepository<Purchase, Long> {

	@Query(value = "select new com.dor.coupons.dto.PurchaseDTO(p) from Purchase p where p.user.id= :userId")
	public List<PurchaseDTO> getAllPurchases(@Param("userId") long userID) throws ApplicationException;

	@Query(value = "select new com.dor.coupons.dto.PurchaseDTO(p) from Purchase p where p.coupon.company.id= :companyId")
	public List<PurchaseDTO> getAllPurchasesByCompany(@Param("companyId") long companyID) throws ApplicationException;
	
	@Query(value="select new com.dor.coupons.dto.PurchaseDTO(p) from Purchase p where p.id= :id")
	public PurchaseDTO getPurchase(@Param("id") long id);
	
	@Query(value="select p from Purchase p where p.id= :id")
	public Purchase getPurchaseEntity(@Param("id")long id);
	
	@Modifying
	@Transactional
	@Query(value="delete from Purchase p where p.coupon.id in(select c.id from Coupon c where c.endDate<= :date)")
	public void deletePurchasesOfExpiredCoupons(@Param("date")Date date);
}
