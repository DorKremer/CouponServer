package com.dor.coupons.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.dor.coupons.dto.CouponDTO;
import com.dor.coupons.entities.Coupon;
import com.dor.coupons.enums.CouponTypes;
import com.dor.coupons.exceptions.ApplicationException;

public interface ICouponDAO extends CrudRepository<Coupon, Long> {

	@Query(value = "select new com.dor.coupons.dto.CouponDTO(c) from Coupon c where c.company.id= :companyId")
	public List<CouponDTO> getCouponsByCompany(@Param("companyId") long companyID) throws ApplicationException;

	@Query(value = "select new com.dor.coupons.dto.CouponDTO(c) from Coupon c where c.category= :category")
	public List<CouponDTO> getCouponsByType(@Param("category") CouponTypes type) throws ApplicationException;

	@Query(value = "select new com.dor.coupons.dto.CouponDTO(c) from Coupon c where c.price<= :maxPrice and c in (select p.coupon from Purchase p where p.user.id= :userID)")
	public List<CouponDTO> getPurchasedCouponsByMaxPrice(@Param("userID") long userID, @Param("maxPrice") double maxPrice)
			throws ApplicationException;

	public boolean existsByName(@Param("name") String name) throws ApplicationException;

	@Transactional
	@Modifying
	@Query(value = "delete Coupon c where c.endDate<= :date")
	public void removeExpired(Date date) throws ApplicationException;

	@Query(value="select new com.dor.coupons.dto.CouponDTO(c) from Coupon c where c.name= :name")
	public CouponDTO findByName(@Param("name") String name);
}
