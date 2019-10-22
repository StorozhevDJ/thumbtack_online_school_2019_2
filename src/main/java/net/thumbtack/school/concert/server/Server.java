package net.thumbtack.school.concert.server;

import java.io.FileNotFoundException;
import com.google.gson.Gson;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.dto.response.ErrorDtoResponse;
import net.thumbtack.school.concert.exception.ServerErrorCode;
import net.thumbtack.school.concert.exception.ServerException;
import net.thumbtack.school.concert.service.SongService;
import net.thumbtack.school.concert.service.UserService;

public class Server {

	private UserService userService;
	private SongService songService;

	/**
	 * Производит всю необходимую инициализацию и запускает сервер.
	 * 
	 * @param savedDataFileName - имя файла, в котором было сохранено состояние
	 *                          сервера. Если savedDataFileName == null,
	 *                          восстановление состояния не производится, сервер
	 *                          стартует “с нуля”.
	 * @throws ServerException
	 */
	public void startServer(String savedDataFileName) throws ServerException {
		if (isServerStarted()) {
			throw new ServerException(ServerErrorCode.SERVER_ALREADY_STARTED);
		}

		if ((savedDataFileName != null) && (!savedDataFileName.isEmpty())) {
			// Start server with settings from config file
			try {
				new DataBase().open(savedDataFileName);
			} catch (Exception e) {
				throw new ServerException(ServerErrorCode.CONFIG_FILE_NOT_READ, e.getMessage());
			}
		} else {
			//Start server with default data
			new DataBase().open();
		}
		userService = new UserService();
		songService = new SongService();
	}

	/**
	 * Останавливает сервер и записывает все его содержимое в файл сохранения с
	 * именем savedDataFileName. Если savedDataFileName == null, запись содержимого
	 * не производится.
	 * 
	 * @param savedDataFileName - имя файла, в котором было сохранено состояние
	 *                          сервера.
	 * @throws ServerException
	 */
	public void stopServer(String savedDataFileName) throws ServerException {
		DataBase db = new DataBase();
		
		if (!isServerStarted()) {
			throw new ServerException(ServerErrorCode.SERVER_NOT_STARTED);
		}
		// If the filename is not empty
		if ((savedDataFileName != null) && (!savedDataFileName.isEmpty())) {
			// Save Date to file
			try {
				db.close(savedDataFileName);
			} catch (FileNotFoundException e) {
				throw new ServerException(ServerErrorCode.CONFIG_FILE_NOT_WRITED);
			}
		} else {
			// Exit without saving data
			db.close();
		}
		userService = null;
		songService = null;
	}

	/**
	 * @return boolean - Server started state
	 */
	public boolean isServerStarted() {
		return (userService != null) && (songService != null);
	}

	/**
	 * Register new user
	 * 
	 * @param jsonRequest - JSON string with Username and password for new user
	 */
	public String registerUser(String jsonRequest) {
		if (!isServerStarted()) {
			return jsonError(new ServerException(ServerErrorCode.SERVER_NOT_STARTED));
		}
		try {
			return userService.registerUser(jsonRequest);
		} catch (ServerException e) {
			return jsonError(e);
		}
	}

	/**
	 * Sign in user
	 * 
	 * @param jsonRequest - JSON string with Username and password for login user
	 */
	public String loginUser(String jsonRequest) {
		if (!isServerStarted()) {
			return jsonError(new ServerException(ServerErrorCode.SERVER_NOT_STARTED));
		}
		try {
			return userService.loginUser(jsonRequest);
		} catch (ServerException e) {
			return jsonError(e);
		}
	}

	/**
	 * Logout user
	 * 
	 * @param jsonRequest - JSON string with user UUID for logout
	 */
	public String logoutUser(String jsonRequest) {
		if (!isServerStarted()) {
			return jsonError(new ServerException(ServerErrorCode.SERVER_NOT_STARTED));
		}
		try {
			return userService.logoutUser(jsonRequest);
		} catch (ServerException e) {
			return jsonError(e);
		}
	}

	/**
	 * Delete User from DataBase
	 *
	 * @param jsonRequest
	 * @return
	 */
	public String deleteUser(String jsonRequest) {
		if (!isServerStarted()) {
			return jsonError(new ServerException(ServerErrorCode.SERVER_NOT_STARTED));
		}
		try {
			return userService.deleteUser(jsonRequest);
		} catch (ServerException e) {
			return jsonError(e);
		}
	}


	public String addSong(String jsonRequest) {
		if (!isServerStarted()) {
			return jsonError(new ServerException(ServerErrorCode.SERVER_NOT_STARTED));
		}
		try {
			return songService.addSong(jsonRequest);
		} catch (ServerException e) {
			return jsonError(e);
		}
	}

	/**
	 * Convert ServerException error to JSON string
	 */
	private String jsonError(ServerException error) {
		return new Gson().toJson(new ErrorDtoResponse(error.getServerErrorText()));
	}


}
