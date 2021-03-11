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
import org.springframework.web.bind.annotation.RestController;

import com.dor.coupons.dto.ReturnedUserDTO;
import com.dor.coupons.dto.ProvidedUserDTO;
import com.dor.coupons.entities.SuccessfulLoginData;
import com.dor.coupons.entities.User;
import com.dor.coupons.entities.UserLoginData;
import com.dor.coupons.entities.UserLoginRequest;
import com.dor.coupons.enums.UserTypes;
import com.dor.coupons.exceptions.ApplicationException;
import com.dor.coupons.logic.CompaniesController;
import com.dor.coupons.logic.UsersController;

@RestController
@RequestMapping("/users")
public class UsersAPI {

	@Autowired
	UsersController usersController;
	
	@Autowired
	CompaniesController companyController;

	@PostMapping
	public long createUser(@RequestBody ProvidedUserDTO user) throws ApplicationException {
		return this.usersController.createUser(user);
	}
	
	@PostMapping("/register")
	public long createCustomer(@RequestBody ProvidedUserDTO user) throws ApplicationException {
		user.setUsersTypes(UserTypes.CUSTOMER);
		user.setCompanyName(null);
		return this.usersController.createUser(user);
	}

	@PutMapping
	public boolean updateUser(@RequestBody ProvidedUserDTO user, HttpServletRequest request) throws ApplicationException {
		UserLoginData userLoginData = (UserLoginData) request.getAttribute("userLoginData");
		User userChanging=new User(user);
		if(userLoginData.getUserType()==UserTypes.ADMIN) {
			this.usersController.updateUser(user);
			return true;
		}
		if (userLoginData.getUserId() != userChanging.getId()) {
			return false;
		}
		this.usersController.updateUser(user);
		return true;
	}

	@DeleteMapping("/{userID}")
	public boolean deleteUser(@PathVariable("userID") long userID, HttpServletRequest request)
			throws ApplicationException {
		UserLoginData userLoginData = (UserLoginData) request.getAttribute("userLoginData");
		if (userLoginData.getUserType() == UserTypes.ADMIN) {
			this.usersController.deleteUser(userID);
			return true;
		}
		if (userLoginData.getUserId() != userID) {
			return false;
		}

		this.usersController.deleteUser(userID);
		return true;
	}

	@GetMapping("/{userID}")
	public ReturnedUserDTO getUser(@PathVariable("userID") long userID, HttpServletRequest request)
			throws ApplicationException {
		UserLoginData userLoginData = (UserLoginData) request.getAttribute("userLoginData");
		if (userLoginData.getUserType() == UserTypes.ADMIN) {
			return this.usersController.getUser(userID);
		}
		if (userLoginData.getUserId() != userID) {
			return null;
		}

		return this.usersController.getUser(userID);
	}

	@GetMapping
	public List<ReturnedUserDTO> getAllUsers(HttpServletRequest request) throws ApplicationException {
		UserLoginData userLoginData = (UserLoginData) request.getAttribute("userLoginData");
		if (userLoginData.getUserType() != UserTypes.ADMIN) {
			return null;
		}
		return this.usersController.getAllUsers();
	}

	@PostMapping("/login")
	public SuccessfulLoginData login(@RequestBody UserLoginRequest userLoginRequest) throws ApplicationException {
		return this.usersController.login(userLoginRequest);
	}
	
	@PostMapping("/logout")
	public void logout(@RequestBody String token) {
		this.usersController.logout(token);
	}

}
