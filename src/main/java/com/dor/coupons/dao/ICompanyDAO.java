package com.dor.coupons.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dor.coupons.dto.CompanyDTO;
import com.dor.coupons.entities.Company;

public interface ICompanyDAO extends CrudRepository<Company, Long> {

	public boolean existsByName(@Param(value = "name") String name);

	@Query(value="select new com.dor.coupons.dto.CompanyDTO(c) from Company c where c.name= :name")
	public CompanyDTO findByName(@Param(value = "name") String name);
	
	@Query(value="select c from Company c where c.name= :name")
	public Company getCompanyEntity(@Param("name") String name);
}
