package net.thumbtack.school.concert.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import javax.validation.ConstraintViolation;

import net.thumbtack.school.concert.dao.UserDao;
import net.thumbtack.school.concert.daoimpl.CommentDaoImpl;
import net.thumbtack.school.concert.daoimpl.RatingDaoImpl;
import net.thumbtack.school.concert.daoimpl.SessionDaoImpl;
import net.thumbtack.school.concert.daoimpl.SongDaoImpl;
import net.thumbtack.school.concert.daoimpl.UserDaoImpl;
import net.thumbtack.school.concert.dto.request.LoginUserDtoRequest;
import net.thumbtack.school.concert.dto.request.LogoutUserDtoRequest;
import net.thumbtack.school.concert.dto.request.RegisterUserDtoRequest;
import net.thumbtack.school.concert.exception.ServerErrorCode;
import net.thumbtack.school.concert.exception.ServerException;
import net.thumbtack.school.concert.model.Comment;
import net.thumbtack.school.concert.model.Rating;
import net.thumbtack.school.concert.model.Session;
import net.thumbtack.school.concert.model.Song;
import net.thumbtack.school.concert.model.User;

public class UserService {

	/**
	 * Add new User (Register) in to DataBase
	 *
	 * @param jsonRequest
	 * @return
	 * @throws ServerException
	 */
	public String registerUser(String jsonRequest) throws ServerException {
		// Parse jsonRequest to RegisterUserDtoRequest
		RegisterUserDtoRequest newUser = fromJson(RegisterUserDtoRequest.class, jsonRequest);

		// Add new user in the DB
		UserDao userDao = new UserDaoImpl();
		User userModel = new User(newUser.getFirstName(), newUser.getLastName(), newUser.getLogin(),
				newUser.getPassword());
		userDao.add(userModel);
		// Login and return UUID for this user
		Session session = new Session(UUID.randomUUID().toString());
		new SessionDaoImpl().login(userModel, session);
		return new Gson().toJson(session);
	}

	/**
	 * Finding user in DB and check password and create new user session
	 *
	 * @param jsonRequest
	 * @return
	 * @throws ServerException
	 */
	public String loginUser(String jsonRequest) throws ServerException {
		// Parse JSON string to LoginUserDtoRequest
		LoginUserDtoRequest user = fromJson(LoginUserDtoRequest.class, jsonRequest);

		// find pair User&password in DB
		User userModel = new User();
		userModel = new UserDaoImpl().get(user.getLogin());
		if ((userModel == null) || (!userModel.getPassword().equals(user.getPassword()))) {
			throw new ServerException(ServerErrorCode.LOGIN_INCORRECT);
		}

		// add uuid to session in DB
		Session session = new Session(UUID.randomUUID().toString());
		new SessionDaoImpl().login(userModel, session);
		return new Gson().toJson(session);
	}

	/**
	 * Removing the User UUID from DB
	 *
	 * @param jsonRequest
	 * @return
	 * @throws ServerException
	 */
	public String logoutUser(String jsonRequest) throws ServerException {
		LogoutUserDtoRequest logoutUser = fromJson(LogoutUserDtoRequest.class, jsonRequest);
		// User token delete from DB
		new SessionDaoImpl().logout(new Session(logoutUser.getToken()));
		// Return JSON token with null values
		return new Gson().toJson(new Session());
	}

	/**
	 * Delete User from database
	 *
	 * @param jsonRequest
	 * @return
	 * @throws ServerException
	 */
	public String deleteUser(String jsonRequest) throws ServerException {
		LogoutUserDtoRequest logoutUser = fromJson(LogoutUserDtoRequest.class, jsonRequest);
		// Find User
		Session session = new Session(logoutUser.getToken());
		User user = new SessionDaoImpl().get(session);
		if (user == null) {
			throw new ServerException(ServerErrorCode.TOKEN_INCORRECT);
		}
		// Delete added songs, ratings and comments
		RatingDaoImpl ratingDaoImpl = new RatingDaoImpl();
		List<Rating> ratingList = new ArrayList<>();
		CommentDaoImpl commentDaoImpl = new CommentDaoImpl();
		List<Comment> commentList = new ArrayList<Comment>();
		SongDaoImpl songDaoImpl = new SongDaoImpl();
		List<Song> songs = songDaoImpl.get(user.getLogin());// Get all songs from this user
		for (Song song : songs) {
			// Delete song and rating
			ratingList = ratingDaoImpl.getRatingList(song.getSongName());
			ratingDaoImpl.delete(song.getSongName(), user.getLogin());// Delete rating
			if (ratingList.size() <= 1) {// If there are ratings from other users, then delete only rating and change
											// user to empty
				songDaoImpl.delete(song);// Else delete song too
			} else {
				song.setUserLogin("");
				songDaoImpl.update(song);
			}
			// Delete author from comments
			commentList = commentDaoImpl.get(song.getSongName());
			for (Comment comment : commentList) {
				if (comment.getAuthor().equals(user.getLogin())) {
					comment.setAuthor("");
					commentDaoImpl.update(comment);
				}
			}
		}
		// User token delete from DB
		new SessionDaoImpl().logout(session);
		// Delete User
		new UserDaoImpl().delete(user.getLogin());
		// Return JSON token with null values
		return new Gson().toJson(new Session());
	}

	/**
	 * Parse JSON string and validate data
	 * 
	 * @param <T> clazz
	 * @param jsonRequest
	 * @return
	 * @throws ServerException
	 */
	private <T> T fromJson(Class<T> clazz, String jsonRequest) throws ServerException {
		// Parse jsonRequest to LogoutUserDtoRequest
		T data;
		try {
			data = new Gson().fromJson(jsonRequest, clazz);
		} catch (JsonSyntaxException e) {
			throw new ServerException(ServerErrorCode.JSON_SYNTAX_ERROR);
		}
		// Validate received date
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<T>> violations;
		try {
			violations = validator.validate(data);
		} catch (IllegalArgumentException e) {
			throw new ServerException(ServerErrorCode.JSON_SYNTAX_ERROR);
		}
		if (!violations.isEmpty()) {
			StringBuilder err = new StringBuilder();
			for (ConstraintViolation<T> cv : violations) {
				// System.err.println(cv.getMessage());
				err.append(cv.getMessage());
			}
			throw new ServerException(ServerErrorCode.BAD_REQUEST, err.toString());
		}
		return data;
	}
}
