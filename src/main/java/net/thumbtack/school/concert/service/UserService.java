package net.thumbtack.school.concert.service;

import java.util.Set;
import java.util.UUID;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import javax.validation.ConstraintViolation;

import net.thumbtack.school.concert.dao.UserDao;
import net.thumbtack.school.concert.daoimpl.SessionDaoImpl;
import net.thumbtack.school.concert.daoimpl.UserDaoImpl;
import net.thumbtack.school.concert.dto.request.LoginUserDtoRequest;
import net.thumbtack.school.concert.dto.request.LogoutUserDtoRequest;
import net.thumbtack.school.concert.dto.request.RegisterUserDtoRequest;
import net.thumbtack.school.concert.exception.ServerErrorCode;
import net.thumbtack.school.concert.exception.ServerException;
import net.thumbtack.school.concert.model.Session;
import net.thumbtack.school.concert.model.User;

public class UserService {

	/**
	 * Add new User (Register) in to DataBase
	 *
	 * @param jsonRequest - string with about User
	 * @return JSON string with new user token
	 * @throws ServerException - JSON_SYNTAX_ERROR, BAD_REQUEST
	 */
	public String registerUser(String jsonRequest) throws ServerException {
		// Parse jsonRequest to RegisterUserDtoRequest
		RegisterUserDtoRequest newUser;
		try {
			newUser = new Gson().fromJson(jsonRequest, RegisterUserDtoRequest.class);
		} catch (JsonSyntaxException e) {
			throw new ServerException(ServerErrorCode.JSON_SYNTAX_ERROR);
		}
		// Validate RegisterUserDtoRequest
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<RegisterUserDtoRequest>> violations;
		try {
			violations = validator.validate(newUser);
		} catch (IllegalArgumentException e) {
			throw new ServerException(ServerErrorCode.JSON_SYNTAX_ERROR);
		}
		if (!violations.isEmpty()) {
			StringBuilder err = new StringBuilder();
			for (ConstraintViolation<RegisterUserDtoRequest> cv : violations) {
				// System.err.println(cv.getMessage());
				err.append(cv.getMessage());
			}
			throw new ServerException(ServerErrorCode.BAD_REQUEST, err.toString());
		}

		// Add new user in the DB
		UserDao userDao = new UserDaoImpl();
		User userModel = new User(newUser.getFirstName(), newUser.getLastName(), newUser.getLogin(),
				newUser.getPassword());
		userDao.insert(userModel);
		// Login and return UUID for this user
		Session session = new Session(UUID.randomUUID().toString());
		new SessionDaoImpl().loginUser(userModel, session);
		return new Gson().toJson(session);
	}

	/**
	 * Finding user in DB and check password and create new user session
	 *
	 * @param jsonRequest string with info for login
	 * @return logined user token
	 * @throws ServerException - JSON_SYNTAX_ERROR, BAD_REQUEST, LOGIN_INCORRECT
	 */
	public String loginUser(String jsonRequest) throws ServerException {
		// Parse JSON string to LoginUserDtoRequest
		LoginUserDtoRequest user;
		try {
			user = new Gson().fromJson(jsonRequest, LoginUserDtoRequest.class);
		} catch (JsonSyntaxException e) {
			throw new ServerException(ServerErrorCode.JSON_SYNTAX_ERROR);
		}
		// Validate received date
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<LoginUserDtoRequest>> violations;
		try {
			violations = validator.validate(user);
		} catch (IllegalArgumentException e) {
			throw new ServerException(ServerErrorCode.JSON_SYNTAX_ERROR);
		}
		if (!violations.isEmpty()) {
			StringBuilder err = new StringBuilder();
			for (ConstraintViolation<LoginUserDtoRequest> cv : violations) {
				// System.err.println(cv.getMessage());
				err.append(cv.getMessage());
			}
			throw new ServerException(ServerErrorCode.BAD_REQUEST, err.toString());
		}

		// find pair User&password in DB
		User userModel = new UserDaoImpl().getInfo(user.getLogin());
		if ((userModel == null) || (!userModel.getPassword().equals(user.getPassword()))) {
			throw new ServerException(ServerErrorCode.LOGIN_INCORRECT);
		}

		// add uuid to session in DB
		Session session = new Session(UUID.randomUUID().toString());
		new SessionDaoImpl().loginUser(userModel, session);
		return new Gson().toJson(session);
	}

	/**
	 * Removing the User UUID from DB
	 *
	 * @param jsonRequest string with user token for logout user
	 * @return JSON string with null token
	 * @throws ServerException - JSON_SYNTAX_ERROR, BAD_REQUEST
	 */
	public String logoutUser(String jsonRequest) throws ServerException {
		// Parse jsonRequest to LogoutUserDtoRequest
		LogoutUserDtoRequest logoutUser;
		try {
			logoutUser = new Gson().fromJson(jsonRequest, LogoutUserDtoRequest.class);
		} catch (JsonSyntaxException e) {
			throw new ServerException(ServerErrorCode.JSON_SYNTAX_ERROR);
		}
		// Validate received date
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<LogoutUserDtoRequest>> violations;
		try {
			violations = validator.validate(logoutUser);
		} catch (IllegalArgumentException e) {
			throw new ServerException(ServerErrorCode.JSON_SYNTAX_ERROR);
		}
		if (!violations.isEmpty()) {
			StringBuilder err = new StringBuilder();
			for (ConstraintViolation<LogoutUserDtoRequest> cv : violations) {
				// System.err.println(cv.getMessage());
				err.append(cv.getMessage());
			}
			throw new ServerException(ServerErrorCode.BAD_REQUEST, err.toString());
		}

		// User token delete from DB
		new SessionDaoImpl().logoutUser(new Session(logoutUser.getToken()));
		// Return JSON token with null values
		return new Gson().toJson(new Session());
	}

	/**
	 * Delete User from database
	 *
	 * @param jsonRequest string with user token for logout user
	 * @return JSON string with null token
	 * @throws ServerException JSON_SYNTAX_ERROR, BAD_REQUEST
	 */
	public String deleteUser(String jsonRequest) throws ServerException {
		// Parse jsonRequest to LogoutUserDtoRequest
		LogoutUserDtoRequest logoutUser;
		try {
			logoutUser = new Gson().fromJson(jsonRequest, LogoutUserDtoRequest.class);
		} catch (JsonSyntaxException e) {
			throw new ServerException(ServerErrorCode.JSON_SYNTAX_ERROR);
		}
		// Validate received date
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<LogoutUserDtoRequest>> violations;
		try {
			violations = validator.validate(logoutUser);
		} catch (IllegalArgumentException e) {
			throw new ServerException(ServerErrorCode.JSON_SYNTAX_ERROR);
		}
		if (!violations.isEmpty()) {
			StringBuilder err = new StringBuilder();
			for (ConstraintViolation<LogoutUserDtoRequest> cv : violations) {
				// System.err.println(cv.getMessage());
				err.append(cv.getMessage());
			}
			throw new ServerException(ServerErrorCode.BAD_REQUEST, err.toString());
		}

		// User token delete from DB
		new SessionDaoImpl().logoutUser(new Session(logoutUser.getToken()));
		// Return JSON token with null values
		return new Gson().toJson(new Session());
	}
}
