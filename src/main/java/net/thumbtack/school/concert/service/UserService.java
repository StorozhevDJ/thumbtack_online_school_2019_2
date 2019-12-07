package net.thumbtack.school.concert.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.concert.dao.CommentDao;
import net.thumbtack.school.concert.dao.RatingDao;
import net.thumbtack.school.concert.dao.SongDao;
import net.thumbtack.school.concert.dao.UserDao;
import net.thumbtack.school.concert.daoimpl.*;
import net.thumbtack.school.concert.dto.request.LoginUserDtoRequest;
import net.thumbtack.school.concert.dto.request.LogoutUserDtoRequest;
import net.thumbtack.school.concert.dto.request.RegisterUserDtoRequest;
import net.thumbtack.school.concert.exception.ServerErrorCode;
import net.thumbtack.school.concert.exception.ServerException;
import net.thumbtack.school.concert.model.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;
import java.util.stream.Collectors;

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
        User userModel = new User(newUser.getFirstName(), newUser.getLastName(), newUser.getLogin(), newUser.getPassword());
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
        //Parse and check JSON string
        LogoutUserDtoRequest logoutUser = fromJson(LogoutUserDtoRequest.class, jsonRequest);
        // Find User by token
        Session session = new Session(logoutUser.getToken());
        User user = new SessionDaoImpl().get(session);
        if (user == null) {
            throw new ServerException(ServerErrorCode.TOKEN_INCORRECT);
        }
        // Delete all ratings from this user
        RatingDao ratingDao = new RatingDaoImpl();
        ratingDao.delete(null, user.getLogin());
        // "Delete" all comments
        CommentDao commentDao = new CommentDaoImpl();
        List<Comment> commentList = commentDao.getList(null, user.getLogin());
        commentList.replaceAll(c -> {
            c.setAuthor("");
            return c;
        });
        commentDao.update(commentList);
        // Get all songs from this user
        SongDao songDao = new SongDaoImpl();
        Map<String, Song> songs = songDao.get(user.getLogin());
        List<String> songIdList = songs.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
        // Delete this User Login from his songs with ratings from other users
        List<Rating> ratingList = ratingDao.getRatingList(songIdList);
        Map<String, Song> songsToUpdate = new HashMap<>();
        for (Map.Entry<String, Song> me : songs.entrySet()) {
            for (Rating r : ratingList) {
                if (me.getKey().equals(r.getSongId())) {
                    me.getValue().setUserLogin("");
                    songsToUpdate.put(me.getKey(), me.getValue());
                    break;
                }
            }
        }
        songDao.update(songsToUpdate);
        // Delete songs without ratings from other users
        Map<String, Song> songsToDelete = songDao.get(user.getLogin());// Get all songs from this user
        songDao.delete(songsToDelete.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList()));
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
     * @param <T>         clazz
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
