package net.thumbtack.school.concert.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import net.thumbtack.school.concert.daoimpl.CommentDaoImpl;
import net.thumbtack.school.concert.daoimpl.RatingDaoImpl;
import net.thumbtack.school.concert.daoimpl.SessionDaoImpl;
import net.thumbtack.school.concert.daoimpl.SongDaoImpl;
import net.thumbtack.school.concert.dto.request.AddSongDtoRequest;
import net.thumbtack.school.concert.dto.request.DeleteSongDtoRequest;
import net.thumbtack.school.concert.dto.request.GetSongsDtoRequest;
import net.thumbtack.school.concert.dto.response.ErrorDtoResponse;
import net.thumbtack.school.concert.dto.response.GetSongsDtoResponse;
import net.thumbtack.school.concert.exception.ServerErrorCode;
import net.thumbtack.school.concert.exception.ServerException;
import net.thumbtack.school.concert.model.Comment;
import net.thumbtack.school.concert.model.Rating;
import net.thumbtack.school.concert.model.Session;
import net.thumbtack.school.concert.model.Song;
import net.thumbtack.school.concert.model.User;

public class SongService {

	/**
	 * Радиослушатель добавляет новую песню на сервер.
	 *
	 * @param jsonRequest содержит описание песни и token, полученный как результат
	 *                    выполнения команды регистрации радиослушателя.
	 * @return Метод при успешном выполнении возвращает пустой json.
	 * @throws ServerException Если же команду почему-то выполнить нельзя,
	 *                         возвращает json с элементом “error”
	 */
	public String addSong(String jsonRequest) throws ServerException {
		// Parse jsonRequest to AddSongDtoRequest
		AddSongDtoRequest newSongs;
		try {
			newSongs = new Gson().fromJson(jsonRequest, AddSongDtoRequest.class);
		} catch (JsonSyntaxException e) {
			throw new ServerException(ServerErrorCode.JSON_SYNTAX_ERROR);
		}
		// Validate AddSongDtoRequest
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<AddSongDtoRequest>> violations;
		try {
			violations = validator.validate(newSongs);
		} catch (IllegalArgumentException e) {
			throw new ServerException(ServerErrorCode.JSON_SYNTAX_ERROR);
		}
		if (!violations.isEmpty()) {
			StringBuilder err = new StringBuilder();
			for (ConstraintViolation<AddSongDtoRequest> cv : violations) {
				err.append(cv.getMessage());
			}
			throw new ServerException(ServerErrorCode.BAD_REQUEST, err.toString());
		}

		// User session check
		User user = new SessionDaoImpl().get(new Session(newSongs.getToken()));
		if (user == null) {
			throw new ServerException(ServerErrorCode.TOKEN_INCORRECT);
		}

		// Add new Song in the DB
		// Mapping data
		List<Song> songsModel = new ArrayList<>();
		List<Rating> ratingModel = new ArrayList<>();
		for (AddSongDtoRequest.Song song : newSongs.getSong()) {
			songsModel.add(new Song(song.getSongName(), song.getComposer(), song.getAuthor(), song.getSinger(),
					song.getLength(), user.getLogin()));
			ratingModel.add(new Rating(song.getSongName(), 5, user.getLogin()));
		}

		new SongDaoImpl().add(songsModel, user); // Insert songs into the DataBase
		new RatingDaoImpl().add(ratingModel);// Add default rating

		return new Gson().toJson(new ErrorDtoResponse());
	}

	/**
	 * Радиослушатель получает список песен.
	 * 
	 * @param requestJsonString содержит параметры для отбора песен и token,
	 *                          полученный как результат выполнения команды
	 *                          регистрации радиослушателя.
	 * @return Метод при успешном выполнении возвращает json с описанием всех песен.
	 * @throws ServerException Если же команду почему-то выполнить нельзя,
	 *                         возвращает json с элементом “error”
	 */
	public String getSongs(String requestJsonString) throws ServerException {
		// Parse jsonRequest to GetSongsDtoRequest
		GetSongsDtoRequest getSongs;
		try {
			getSongs = new Gson().fromJson(requestJsonString, GetSongsDtoRequest.class);
		} catch (JsonSyntaxException e) {
			throw new ServerException(ServerErrorCode.JSON_SYNTAX_ERROR);
		}
		// Validate GetSongsDtoRequest
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<GetSongsDtoRequest>> violations;
		try {
			violations = validator.validate(getSongs);
		} catch (IllegalArgumentException e) {
			throw new ServerException(ServerErrorCode.JSON_SYNTAX_ERROR);
		}
		if (!violations.isEmpty()) {
			StringBuilder err = new StringBuilder();
			for (ConstraintViolation<GetSongsDtoRequest> cv : violations) {
				err.append(cv.getMessage());
			}
			throw new ServerException(ServerErrorCode.BAD_REQUEST, err.toString());
		}

		// User session check
		if (!new SessionDaoImpl().checkSession(new Session(getSongs.getToken()))) {
			throw new ServerException(ServerErrorCode.TOKEN_INCORRECT);
		}

		// Get Songs list (with filter)
		List<Song> songList = new SongDaoImpl().get(getSongs.getComposer(), getSongs.getAuthor(), getSongs.getSinger());

		// Get average Song Rating from DataBase
		Map<Song, Float> ratingSongMap = new HashMap<>();
		RatingDaoImpl rat = new RatingDaoImpl();
		for (Song s : songList) {
			ratingSongMap.put(s, rat.get(s.getSongName()));
		}
		// Sorting rating Map
		Map<Song, Float> sortedRatingMap = ratingSongMap.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).collect(Collectors.toMap(
						Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

		// Add Songs with rating and comments into GetSongsDtoResponse List
		List<GetSongsDtoResponse> respList = new ArrayList<>();
		int time = 0;
		for (Map.Entry<Song, Float> entry : sortedRatingMap.entrySet()) {
			if (time + entry.getKey().getLength() < 60 * 60) {
				time += entry.getKey().getLength() + 10;

				// String[] comments = new
				// CommentDaoImpl().get(entry.getKey().getSongName()).toArray(new String[0]);
				List<Comment> commentsList = new CommentDaoImpl().get(entry.getKey().getSongName());
				List<String> commentsStringList = new ArrayList<>();
				for (Comment comment : commentsList) {
					commentsStringList.add(comment.getComment());
				}
				respList.add(new GetSongsDtoResponse(entry.getKey().getSongName(),
						entry.getKey().getComposer().toArray(new String[0]),
						entry.getKey().getAuthor().toArray(new String[0]), entry.getKey().getSinger(),
						entry.getKey().getUserLogin(), entry.getValue(), commentsStringList.toArray(new String[0])));
			}
		}

		return new Gson().toJson(respList);
	}

	/**
	 * Delete song from server
	 * 
	 * @param requestJsonString contains Song Name and User Token * @return Метод
	 *                          при успешном выполнении возвращает пустой json.
	 * @throws ServerException
	 */
	public String deleteSong(String requestJsonString) throws ServerException {
		// Parse jsonRequest to DeleteSongDtoRequest
		DeleteSongDtoRequest deleteSongDto;
		try {
			deleteSongDto = new Gson().fromJson(requestJsonString, DeleteSongDtoRequest.class);
		} catch (JsonSyntaxException e) {
			throw new ServerException(ServerErrorCode.JSON_SYNTAX_ERROR);
		}
		// Validate DeleteSongDtoRequest
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<DeleteSongDtoRequest>> violations;
		try {
			violations = validator.validate(deleteSongDto);
		} catch (IllegalArgumentException e) {
			throw new ServerException(ServerErrorCode.JSON_SYNTAX_ERROR);
		}
		if (!violations.isEmpty()) {
			StringBuilder err = new StringBuilder();
			for (ConstraintViolation<DeleteSongDtoRequest> cv : violations) {
				err.append(cv.getMessage());
			}
			throw new ServerException(ServerErrorCode.BAD_REQUEST, err.toString());
		}
		// User session check
		User user = new SessionDaoImpl().get(new Session(deleteSongDto.getToken()));
		if (user == null) {
			throw new ServerException(ServerErrorCode.TOKEN_INCORRECT);
		}
		// Find song with this name from this user
		SongDaoImpl songDaoImpl = new SongDaoImpl();
		Song song = songDaoImpl.get(deleteSongDto.getSongName(), user.getLogin());
		if (song == null) {
			return new Gson().toJson(new ErrorDtoResponse());
		}

		// Delete song or rating
		RatingDaoImpl ratingDaoImpl = new RatingDaoImpl();
		List<Rating> ratingList = ratingDaoImpl.getRatingList(song.getSongName());
		if (ratingList.size() == 1) {// If there are ratings from other users, then delete rating
			songDaoImpl.delete(song);
		}
		ratingDaoImpl.delete(song.getSongName(), user.getLogin());

		return new Gson().toJson(new ErrorDtoResponse());
	}

}
