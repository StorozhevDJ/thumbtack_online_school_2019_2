package net.thumbtack.school.concert.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

		if (savedDataFileName != null) {// Start server with settings from config file
			if (!isFileNameCorrect(savedDataFileName)) {
				throw new ServerException(ServerErrorCode.CONFIG_FILE_NOT_READ, "Имя файла не корректно!");
			}
			File dbFile = new File(savedDataFileName);
			try {
				String fileName = dbFile.getCanonicalPath();
				//fileName.trim();
				
			} catch (IOException e) {
				throw new ServerException(ServerErrorCode.CONFIG_FILE_NOT_READ,
						savedDataFileName + " " + e.getMessage());
			}
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
		if (!isServerStarted()) {
			throw new ServerException(ServerErrorCode.SERVER_NOT_STARTED);
		}
		if ((savedDataFileName != null) && (!savedDataFileName.isEmpty())) {
			//Save Date to file
			File dbFile = new File(savedDataFileName);
			String fileName = null;
			try {
				fileName = dbFile.getCanonicalPath();
				
			} catch (IOException e) {
				throw new ServerException(ServerErrorCode.CONFIG_FILE_NOT_WRITED, "Имя файла не корректно!");
			}
			try (PrintWriter pw = new PrintWriter(fileName)) {
				pw.println(new Gson().toJson(new DataBase()));
				DataBase.close();
			}
			catch (FileNotFoundException e) {
				throw new ServerException(ServerErrorCode.CONFIG_FILE_NOT_WRITED);
			}
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
	 * Convert ServerException error to JSON string
	 */
	private String jsonError(ServerException error) {
		return new Gson().toJson(new ErrorDtoResponse(error.getServerErrorText()));
	}

	public static boolean isFileNameCorrect(String name) {
		// if (name.trim().isEmpty()) return false;
		Pattern pattern = Pattern.compile("(.+)?[><\\|\\?*/:\\\\\"](.+)?");
		Matcher matcher = pattern.matcher(name);
		return !matcher.find();
	}

}
