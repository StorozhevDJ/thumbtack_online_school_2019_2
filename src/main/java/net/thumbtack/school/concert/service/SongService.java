package net.thumbtack.school.concert.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import net.thumbtack.school.concert.daoimpl.RatingDaoImpl;
import net.thumbtack.school.concert.daoimpl.SessionDaoImpl;
import net.thumbtack.school.concert.daoimpl.SongDaoImpl;
import net.thumbtack.school.concert.dto.request.AddSongDtoRequest;
import net.thumbtack.school.concert.dto.request.GetSongsDtoRequest;
import net.thumbtack.school.concert.exception.ServerErrorCode;
import net.thumbtack.school.concert.exception.ServerException;
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
		User user = new SessionDaoImpl().getUser(new Session(newSongs.getToken()));
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

		new SongDaoImpl().insert(songsModel, user);                // Insert songs into the DataBase
        new RatingDaoImpl().insert(ratingModel);//Add initial rating

		return new Gson().toJson(null);
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

		// Get Songs list
		/*
		 * GetSongsDtoRequest getSongsDto = new
		 * GetSongsDtoRequest(getSongs.getComposer(), getSongs.getAuthor(),
		 * getSongs.getSinger(), getSongs.getToken());
		 */

		/*
		 * GetSongsDtoRequest getSong = new GetSongsDtoRequest();
		 * GetSongsDtoRequest.Song s = new GetSongsDtoRequest().new Song();
		 * ArrayList<GetSongsDtoRequest.Song> song = new ArrayList<>();
		 * getSong.setToken("testToken"); song.add(s) getSong.setSong(song);
		 */

		getSongs.getAuthor();

		// ArrayList<GetSongsDtoResponse> songsDtoResponse = new ArrayList<>();

		return requestJsonString;
	}

}
