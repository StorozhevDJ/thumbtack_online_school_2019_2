package net.thumbtack.school.concert.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import net.thumbtack.school.concert.dao.CommentDao;
import net.thumbtack.school.concert.dao.RatingDao;
import net.thumbtack.school.concert.dao.SessionDao;
import net.thumbtack.school.concert.dao.SongDao;
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

    private SessionDao sessionDao;
    private SongDao songDao;
    private CommentDao commentDao;
    private RatingDao ratingDao;
    

    public SongService (SessionDao sessionDao, SongDao songDao, CommentDao commentDao, RatingDao ratingDao) {
    	this.sessionDao = sessionDao;
    	this.songDao = songDao;
    	this.commentDao = commentDao;
    	this.ratingDao = ratingDao;
    }
    
    public SongService () {
    	this(new SessionDaoImpl(), new SongDaoImpl(), new CommentDaoImpl(), new RatingDaoImpl());
    }
    
    
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
		AddSongDtoRequest newSongs = fromJson(AddSongDtoRequest.class, jsonRequest);
		User user = findUser(newSongs.getToken());
		// Add new Song in the DB
		// Mapping data
		Map<String, Song> songsModel = new HashMap<>();
		List<Rating> ratingModel = new ArrayList<>();
		for (AddSongDtoRequest.Song song : newSongs.getSong()) {
			String songId = UUID.randomUUID().toString();
			songsModel.put(songId, new Song(song.getSongName(), song.getComposer(), song.getAuthor(), song.getSinger(),
					song.getLength(), user.getLogin()));
			ratingModel.add(new Rating(songId, 5, user.getLogin()));
		}

        songDao.add(songsModel); // Insert songs into the DataBase
        ratingDao.add(ratingModel);// Add default rating

		return new Gson().toJson(new ErrorDtoResponse());
	}

	/**
	 * Радиослушатель получает список песен.
	 * <p>
	 * В любой момент любой радиослушатель может получить следующие списки
	 * <p>
	 * Все заявленные в концерт песни. Все заявленные в концерт песни указанного
	 * композитора или композиторов. Все заявленные в концерт песни указанного
	 * автора слов или авторов слов. Все заявленные в концерт песни указанного
	 * исполнителя.
	 * <p>
	 * <p>
	 * В любой момент любой радиослушатель может получить текущую пробную программу
	 * концерта. Пробная программа - это концерт из песен, набравших наибольшие
	 * суммы оценок при условии, что суммарная продолжительность концерта не
	 * превышает 60 минут с учетом того, что между каждыми двумя песнями делается
	 * пауза продолжительностью в 10 секунд. В случае, если очередная песня из
	 * списка наиболее популярных не может быть добавлена в концерт, потому что при
	 * этом будет превышено время концерта, эта песня пропускается, и делается
	 * попытка добавить следующую по популярности песню и т.д. В концерт должно
	 * включаться максимально возможное количество песен.
	 * <p>
	 * В пробную программу концерта для каждой песни включаются Название песни,
	 * композитор(ы), автор(ы) слов, исполнитель Данные о радиослушателе,
	 * предложившем песню. Средняя оценка песни. Все комментарии к этому
	 * предложению.
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
		GetSongsDtoRequest getSongs = fromJson(GetSongsDtoRequest.class, requestJsonString);
		findUser(getSongs.getToken());
		// Get Songs list (with filter)
        Map<String, Song> songList = songDao.get(getSongs.getComposer(), getSongs.getAuthor(),
				getSongs.getSinger());
		List<String> songIdList = songList.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
		// Get rating list for song Id list
        List<Rating> ratingList = ratingDao.get(songIdList);
		// Get all comments for found songs
        List<Comment> commentsList = commentDao.get(songIdList);

		// Calculate average ratings for song
		Map<String, Float> ratingSongMap = calculateAvgRating(songList, ratingList);// new HashMap<>();
		// Sorting rating Map
		Map<String, Float> sortedRatingMap = ratingSongMap.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).collect(Collectors.toMap(
						Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

		// Add Songs with rating and comments into GetSongsDtoResponse List
		List<GetSongsDtoResponse> respList = new ArrayList<>();
		int time = 0;
		for (Map.Entry<String, Float> entry : sortedRatingMap.entrySet()) {
			Song song = songList.get(entry.getKey());
			if (time + song.getLength() < 60 * 60) {
				time += song.getLength() + 10;
				List<String> songCommentsList = commentsList.stream().filter(s -> s.getSongId().equals(entry.getKey()))
						.map(s -> s.getComment()).collect(Collectors.toList());

				// Making a concert list
				respList.add(
						new GetSongsDtoResponse(song.getSongName(), song.getComposer().stream().toArray(String[]::new),
								song.getAuthor().stream().toArray(String[]::new), song.getSinger(), song.getUserLogin(),
								entry.getValue().floatValue(), songCommentsList.stream().toArray(String[]::new),
								entry.getKey()));
			}
		}
		return new Gson().toJson(respList);
	}

	/**
	 * Радиослушатель получает список всех песен заявленных на концерт.
	 * 
	 * @param requestJsonString
	 * @return
	 * @throws ServerException
	 */
	public String getAllSongs(String requestJsonString) throws ServerException {
		// Parse jsonRequest to GetSongsDtoRequest
		GetSongsDtoRequest getSongs = fromJson(GetSongsDtoRequest.class, requestJsonString);
		findUser(getSongs.getToken());
		// Get Songs list
        Map<String, Song> songList = songDao.get(null);
		// Get rating list
        List<Rating> ratingList = ratingDao.getRatingList((String) null);
		// Get all comments
        List<Comment> commentsList = commentDao.get((String) null);

		// Calculate average ratings for song
		Map<String, Float> ratingSongMap = calculateAvgRating(songList, ratingList);// new HashMap<>();

		// Add Songs with rating and comments into GetSongsDtoResponse List
		List<GetSongsDtoResponse> respList = new ArrayList<>();
		for (Map.Entry<String, Float> entry : ratingSongMap.entrySet()) {
			Song song = songList.get(entry.getKey());
			List<String> songCommentsList = commentsList.stream().filter(s -> s.getSongId().equals(entry.getKey()))
					.map(s -> s.getComment()).collect(Collectors.toList());
			// Making a song list
			respList.add(new GetSongsDtoResponse(song.getSongName(), song.getComposer().stream().toArray(String[]::new),
					song.getAuthor().stream().toArray(String[]::new), song.getSinger(), song.getUserLogin(),
					entry.getValue().floatValue(), songCommentsList.stream().toArray(String[]::new), entry.getKey()));
		}
		return new Gson().toJson(respList);
	}

	/**
	 * Delete song from server
	 *
	 * @param requestJsonString contains Song Name and User Token
	 * @return Метод при успешном выполнении возвращает пустой json.
	 * @throws ServerException
	 */
	public String deleteSong(String requestJsonString) throws ServerException {
		// Parse jsonRequest to DeleteSongDtoRequest
		DeleteSongDtoRequest deleteSongDto = fromJson(DeleteSongDtoRequest.class, requestJsonString);
		User user = findUser(deleteSongDto.getToken());
		// Find song with this name from this user
		String songId = deleteSongDto.getSongId();
		Song song = songDao.get(songId, user.getLogin());
		if (song == null) {
			throw new ServerException(ServerErrorCode.SONG_NOT_FOUND);
		}
		// Delete song or rating
		List<Rating> ratingList = ratingDao.getRatingList(songId);
		if (ratingList.size() == 1) {// If there are ratings from other users, then delete rating
			songDao.delete(songId);
		} else {
			song.setUserLogin("");
			songDao.update(song, songId);
		}
		ratingDao.delete(songId, user.getLogin());

		return new Gson().toJson(new ErrorDtoResponse());
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
				err.append(cv.getMessage());
			}
			throw new ServerException(ServerErrorCode.BAD_REQUEST, err.toString());
		}
		return data;
	}

	/**
	 * Find and check User by token
	 * 
	 * @param token
	 * @return
	 * @throws ServerException
	 */
	private User findUser(String token) throws ServerException {
        User user = sessionDao.get(new Session(token));
		if (user == null) {
			throw new ServerException(ServerErrorCode.TOKEN_INCORRECT);
		}
		return user;
	}

	/**
	 * Calculate average ratings for song
	 * 
	 * @param songList
	 * @param ratingList
	 * @return ratingSongMap - Map<String, Float>
	 */
	private Map<String, Float> calculateAvgRating(Map<String, Song> songList, List<Rating> ratingList) {
		Map<String, Float> ratingSongMap = new HashMap<>();
		Iterator<Map.Entry<String, Song>> iterator = songList.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Song> me = iterator.next();
			Long sumOdd = ratingList.stream().filter(r -> r.getSongId().equals(me.getKey()))
					.mapToLong(ob -> (ob.getRating())).reduce(0, (a, b) -> a + b);
			Long cnt = ratingList.stream().filter(r -> r.getSongId().equals(me.getKey())).count();
			float ratAvg = (float) sumOdd / (float) (cnt > 0 ? cnt : 1);
			ratingSongMap.put(me.getKey(), ratAvg);
		}
		return ratingSongMap;
	}

}
