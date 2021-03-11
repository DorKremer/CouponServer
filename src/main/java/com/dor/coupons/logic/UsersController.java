package com.dor.coupons.logic;

import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.dor.coupons.dao.IUserDAO;
import com.dor.coupons.dto.CompanyDTO;
import com.dor.coupons.dto.ReturnedUserDTO;
import com.dor.coupons.dto.ProvidedUserDTO;
import com.dor.coupons.entities.Company;
import com.dor.coupons.entities.SuccessfulLoginData;
import com.dor.coupons.entities.User;
import com.dor.coupons.entities.UserLoginData;
import com.dor.coupons.entities.UserLoginRequest;
import com.dor.coupons.enums.ExceptionType;
import com.dor.coupons.exceptions.ApplicationException;

@Controller
public class UsersController {

	private final String SALT = "MI^&NECUV&I*MNI*^C&*121265MKJM";

	@Autowired
	private IUserDAO usersDao;

	@Autowired
	private CompaniesController companyController;

	@Autowired
	private CacheController cacheController;

	// CTOR
	public UsersController() {

	}

	public long createUser(ProvidedUserDTO user) throws ApplicationException {
		validateCreateUser(user);
		
		//Every user will have a different password hash value even if the passwords are the same
		user.setPassword(String.valueOf((user.getPassword() + SALT + user.getUserName()).hashCode()));
		User userToSave = new User(user);
		Company company=companyController.getCompanyEntity(user.getCompanyName());
		userToSave.setCompany(company);
		try {
			this.usersDao.save(userToSave);
		} catch (Exception e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());
		}
		return userToSave.getId();
	}

	public void updateUser(ProvidedUserDTO user) throws ApplicationException {
		validateUpdateUser(user);
		
		//Every user will have a different password hash value even if the passwords are the same
		user.setPassword(String.valueOf((user.getPassword() + SALT + user.getUserName()).hashCode()));
		User userToSave = new User(user);
		Company company=companyController.getCompanyEntity(user.getCompanyName());
		try {
			this.usersDao.save(userToSave);
		} catch (Exception e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());
		}
	}

	public void validateUpdateUser(ProvidedUserDTO user) throws ApplicationException {
		try {
			this.usersDao.findById(user.getId());
		} catch (Exception e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());
		}
		validateText(user.getFirstName(), "First name", 2);
		validateText(user.getLastName(), "Last name", 2);
		validateText(user.getUserName(), "Username", 4);
		validateText(user.getPassword(), "Password", 4);
	}

	public void deleteUser(long id) throws ApplicationException {
		try {
			this.usersDao.deleteById(id);
		} catch (Exception e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());
		}
	}

	public ReturnedUserDTO getUser(long userID) throws ApplicationException {
		ReturnedUserDTO user = null;
		try {
			user = this.usersDao.getUser(userID);
		} catch (Exception e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());
		}
		return user;
	}

	public List<ReturnedUserDTO> getAllUsers() throws ApplicationException {
		List<ReturnedUserDTO> users;
		try {
			users = this.usersDao.getAllUsers();
		} catch (Exception e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());
		}
		return users;
	}

	public ProvidedUserDTO findByUsername(String username) {
		return this.usersDao.findByUsername(username);
	}

	public SuccessfulLoginData login(UserLoginRequest userLoginRequest) throws ApplicationException {
		userLoginRequest.setPassword(
				String.valueOf((userLoginRequest.getPassword() + SALT + userLoginRequest.getUsername()).hashCode()));
		User user;
		try {
			user = this.usersDao.login(userLoginRequest.getUsername(), userLoginRequest.getPassword());
		} catch (ApplicationException e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_EXCEPTION,
					ExceptionType.GENERAL_EXCEPTION.getExceptionMessage());
		}
		UserLoginData userLoginData = new UserLoginData(user);

		String token = generateToken(userLoginRequest);
		cacheController.put(String.valueOf(token), userLoginData);
		if(user.getCompany()==null) {
			return new SuccessfulLoginData(user.getId(), token, userLoginData.getUserType(), user.getFirstName(),user.getLastName());
		}
		return new SuccessfulLoginData(user.getId(), token, userLoginData.getUserType(), user.getFirstName(),user.getLastName(), user.getCompany().getName(), user.getCompany().getId());
	}
	
	public void logout(String token) {
		cacheController.remove(token);
	}

	private String generateToken(UserLoginRequest userLoginRequest) {
		int text = (userLoginRequest.getUsername() + Calendar.getInstance().getTime().toString() + SALT).hashCode();
		return String.valueOf(text);
	}

	private void validateCreateUser(ProvidedUserDTO user) throws ApplicationException {
		if (user.getUserName() == null) {
			throw new ApplicationException(ExceptionType.NAME_CANT_BE_NULL,
					ExceptionType.NAME_CANT_BE_NULL.getExceptionMessage());
		}

		if (user.getUserName().isEmpty()) {
			throw new ApplicationException(ExceptionType.MUST_ENTER_NAME,
					ExceptionType.MUST_ENTER_NAME.getExceptionMessage());
		}

		if (user.getUserName().length() < 4) {
			throw new ApplicationException(ExceptionType.INPUT_TOO_SHORT,
					ExceptionType.INPUT_TOO_SHORT.getExceptionMessage());
		}

		if (this.usersDao.existsByUsername(user.getUserName())) {
			throw new ApplicationException(ExceptionType.NAME_ALREADY_EXISTS,
					ExceptionType.NAME_ALREADY_EXISTS.getExceptionMessage());
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
	
	public User dtoToEntity(ProvidedUserDTO userDto) {
		User user=new User(userDto);
		CompanyDTO companyDto=companyController.findByName(userDto.getCompanyName());
		Company company=new Company(companyDto);
		user.setCompany(company);
		return user;
	}
	public User getUserByUsername(String username) {
		return this.usersDao.getUserByUsername(username);
	}
}