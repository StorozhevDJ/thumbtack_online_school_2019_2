package net.thumbtack.school.concert.service;

import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import net.thumbtack.school.concert.dao.RatingDao;
import net.thumbtack.school.concert.dao.SongDao;
import net.thumbtack.school.concert.daoimpl.RatingDaoImpl;
import net.thumbtack.school.concert.daoimpl.SessionDaoImpl;
import net.thumbtack.school.concert.daoimpl.SongDaoImpl;
import net.thumbtack.school.concert.dto.request.AddRatingDtoRequest;
import net.thumbtack.school.concert.dto.response.ErrorDtoResponse;
import net.thumbtack.school.concert.exception.ServerErrorCode;
import net.thumbtack.school.concert.exception.ServerException;
import net.thumbtack.school.concert.model.Rating;
import net.thumbtack.school.concert.model.Session;
import net.thumbtack.school.concert.model.User;

public class RatingService {

    /**
     * Add new Rating into DB
     *
     * @param jsonRequest
     * @return
     * @throws ServerException
     */
    public String addRating(String jsonRequest) throws ServerException {
        AddRatingDtoRequest newRating = fromJsonString(jsonRequest);
        // Find User
        User user = new SessionDaoImpl().get(new Session(newRating.getToken()));
        if (user == null) {
            throw new ServerException(ServerErrorCode.TOKEN_INCORRECT);
        }
        // Find Ratings for this song
        RatingDao ratingDao = new RatingDaoImpl();
        List<Rating> ratingList = ratingDao.getRatingList(newRating.getSongId());
        if (ratingList.isEmpty()) {
            throw new ServerException(ServerErrorCode.ADD_RATING_ERROR);
        }
        // Find Rating from this User
        for (Rating rating : ratingList) {
            if (rating.getUser().equals(user.getLogin())) {
                throw new ServerException(ServerErrorCode.ADD_RATING_ERROR);
            }
        }
        // Add new Rating in to DataBase
        ratingDao.add(new Rating(newRating.getSongId(), newRating.getRating(), user.getLogin()));

        return new Gson().toJson(new ErrorDtoResponse());
    }

    /**
     * Change available Rating
     *
     * @param jsonRequest
     * @return
     * @throws ServerException
     */
    public String changeRating(String jsonRequest) throws ServerException {
        AddRatingDtoRequest newRating = fromJsonString(jsonRequest);
        User user = findUser(newRating.getToken());
        // If user added this Song
        SongDao songDao = new SongDaoImpl();
        if (songDao.get(newRating.getSongId(), user.getLogin()) != null) {
            throw new ServerException(ServerErrorCode.CHANGE_RATING_ERROR, " своей песни");
        }
        // Find Ratings for this song
        RatingDao ratingDao = new RatingDaoImpl();
        List<Rating> ratingList = ratingDao.getRatingList(newRating.getSongId());
        // Find and change Rating from this User
        for (Rating rating : ratingList) {
            if (rating.getUser().equals(user.getLogin())) {
                ratingDao.update(new Rating(newRating.getSongId(), newRating.getRating(), user.getLogin()));
                return new Gson().toJson(new ErrorDtoResponse());
            }
        }
        throw new ServerException(ServerErrorCode.CHANGE_RATING_ERROR,
                ". Не найден рейтинг пользователя для указанной песни. ");
    }

    /**
     * Delete Rating
     *
     * @param jsonRequest
     * @return
     * @throws ServerException
     */
    public String deleteRating(String jsonRequest) throws ServerException {
        // Parse jsonRequest to AddRatingDtoRequest
        AddRatingDtoRequest delRating;
        try {
            delRating = new Gson().fromJson(jsonRequest, AddRatingDtoRequest.class);
        } catch (JsonSyntaxException e) {
            throw new ServerException(ServerErrorCode.JSON_SYNTAX_ERROR);
        }
        if (delRating == null) {
            throw new ServerException(ServerErrorCode.JSON_SYNTAX_ERROR);
        }
        User user = findUser(delRating.getToken());
        // If user added this Song
        SongDao songDao = new SongDaoImpl();
        if (songDao.get(delRating.getSongId(), user.getLogin()) != null) {
            throw new ServerException(ServerErrorCode.DELETE_RATING_ERROR, " своей песни");
        }
        // Find Ratings for this song
        RatingDao ratingDao = new RatingDaoImpl();
        List<Rating> ratingList = ratingDao.getRatingList(delRating.getSongId());
        // Find and change Rating from this User
        for (Rating rating : ratingList) {
            if (rating.getUser().equals(user.getLogin())) {
                ratingDao.delete(delRating.getSongId(), user.getLogin());
                return new Gson().toJson(new ErrorDtoResponse());
            }
        }
        throw new ServerException(ServerErrorCode.DELETE_RATING_ERROR);
    }

    /**
     * Parse JSON string and validate data
     *
     * @param jsonRequest
     * @return
     * @throws ServerException
     */
    private AddRatingDtoRequest fromJsonString(String jsonRequest) throws ServerException {
        // Parse jsonRequest to AddRatingDtoRequest
        AddRatingDtoRequest newRating;
        try {
            newRating = new Gson().fromJson(jsonRequest, AddRatingDtoRequest.class);
        } catch (JsonSyntaxException e) {
            throw new ServerException(ServerErrorCode.JSON_SYNTAX_ERROR);
        }
        // Validate RegisterUserDtoRequest
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<AddRatingDtoRequest>> violations;
        try {
            violations = validator.validate(newRating);
        } catch (IllegalArgumentException e) {
            throw new ServerException(ServerErrorCode.JSON_SYNTAX_ERROR);
        }
        if (!violations.isEmpty()) {
            StringBuilder err = new StringBuilder();
            for (ConstraintViolation<AddRatingDtoRequest> cv : violations) {
                err.append(cv.getMessage());
            }
            throw new ServerException(ServerErrorCode.BAD_REQUEST, err.toString());
        }
        return newRating;
    }

    /**
     * Find and check User by token
     *
     * @param token
     * @return
     * @throws ServerException
     */
    private User findUser(String token) throws ServerException {
        User user = new SessionDaoImpl().get(new Session(token));
        if (user == null) {
            throw new ServerException(ServerErrorCode.TOKEN_INCORRECT);
        }
        return user;
    }

}
