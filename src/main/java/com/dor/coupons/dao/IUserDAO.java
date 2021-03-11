package com.dor.coupons.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dor.coupons.dto.ReturnedUserDTO;
import com.dor.coupons.dto.ProvidedUserDTO;
import com.dor.coupons.entities.User;

import com.dor.coupons.exceptions.ApplicationException;

public interface IUserDAO extends CrudRepository<User, Long> {

	@Query(value = "select u from User u where u.username= :username and u.password= :password")
	public User login(@Param("username") String username, @Param("password") String password)
			throws ApplicationException;

	@Query(value="select new com.dor.coupons.dto.ProvidedUserDTO(u) from User u where u.username= :username")
	public ProvidedUserDTO findByUsername(@Param("username") String username);

	public boolean existsByUsername(String username) throws ApplicationException;
	
	@Query(value="select new com.dor.coupons.dto.ReturnedUserDTO(u) from User u where u.id= :id")
	public ReturnedUserDTO getUser(@Param("id") long id);
	
	@Query(value="select new com.dor.coupons.dto.ReturnedUserDTO(u) from User u")
	public List<ReturnedUserDTO> getAllUsers();
	
	@Query(value="select u from User u where u.username= :username")
	public User getUserByUsername(@Param("username")String username);
}
