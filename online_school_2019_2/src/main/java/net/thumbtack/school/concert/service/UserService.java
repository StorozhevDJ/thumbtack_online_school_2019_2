package net.thumbtack.school.concert.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import javax.validation.ConstraintViolation;

import net.thumbtack.school.concert.dao.CommentDao;
import net.thumbtack.school.concert.dao.RatingDao;
import net.thumbtack.school.concert.dao.SessionDao;
import net.thumbtack.school.concert.dao.SongDao;
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

    private UserDao userDao;
    private SessionDao sessionDao;
    private SongDao songDao;
    private CommentDao commentDao;
    private RatingDao ratingDao;


    public UserService(UserDao userDao, SessionDao sessionDao, SongDao songDao, CommentDao commentDao, RatingDao ratingDao) {
        setUserDao(userDao);
        this.sessionDao = sessionDao;
        this.songDao = songDao;
        this.commentDao = commentDao;
        this.ratingDao = ratingDao;
    }

    public UserService() {
        this(new UserDaoImpl(), new SessionDaoImpl(), new SongDaoImpl(), new CommentDaoImpl(), new RatingDaoImpl());
    }


    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


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
        User userModel = new User(newUser.getFirstName(), newUser.getLastName(), newUser.getLogin(), newUser.getPassword());
        userDao.add(userModel);
        // Login and return UUID for this user
        Session session = new Session(UUID.randomUUID().toString());
        sessionDao.login(userModel, session);
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
        userModel = userDao.get(user.getLogin());
        if ((userModel == null) || (!userModel.getPassword().equals(user.getPassword()))) {
            //throw new ServerException(ServerErrorCode.LOGIN_INCORRECT);
        }

        // add uuid to session in DB
        Session session = new Session(UUID.randomUUID().toString());
        sessionDao.login(userModel, session);
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
        sessionDao.logout(new Session(logoutUser.getToken()));
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
        User user = sessionDao.get(session);
        if (user == null) {
            throw new ServerException(ServerErrorCode.TOKEN_INCORRECT);
        }
        // Delete all ratings from this user
        ratingDao.delete(null, user.getLogin());
        // "Delete" all comments
        List<Comment> commentList = commentDao.getList(null, user.getLogin());
        commentList.replaceAll(c -> {c.setAuthor(""); return c;});
        commentDao.update(commentList);
        // Get all songs with ratings from this user
        Map<String, Song> songs = songDao.get(user.getLogin());// Get all songs from this user
        List<String> songIdList = songs.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
        List<Rating> ratingList = ratingDao.getRatingList(songIdList);
        // Delete user from songs with ratings from other users
        Map<String, Song> songsToUpdate = new HashMap<>();
        Iterator<Map.Entry<String, Song>> iterator = songs.entrySet().iterator();
        while (iterator.hasNext()) {
        	Map.Entry<String, Song> me = iterator.next();
        	for (Rating r : ratingList) {
                if (me.getKey().equals(r.getSongId())) {
                    me.getValue().setUserLogin("");
                    songsToUpdate.put(me.getKey(), me.getValue());
                    break;
                }
            }
        }
        songDao.update(songsToUpdate);
        // Delete all songs if no ratings from other users
        Map<String, Song> songsToDelete = songDao.get(user.getLogin());// Get all songs from this user
        songDao.delete(songsToDelete.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList()));
        // User token delete from DB
        sessionDao.logout(session);
        // Delete User
        userDao.delete(user.getLogin());
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
