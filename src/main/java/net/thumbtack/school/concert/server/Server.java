package net.thumbtack.school.concert.server;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import net.thumbtack.school.concert.dto.request.LoginUserDtoRequest;
import net.thumbtack.school.concert.dto.request.RegisterUserDtoRequest;
import net.thumbtack.school.concert.dto.response.ErrorDtoResponse;
import net.thumbtack.school.concert.exception.ServerErrorCode;
import net.thumbtack.school.concert.exception.ServerException;
import net.thumbtack.school.concert.service.UserService;

public class Server {

	private boolean serverStarted;

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
		if (IsServerStarted()) {
			throw new ServerException(ServerErrorCode.SERVER_ALREADY_STARTED);
		}

		if (savedDataFileName != null) {// Start server with settings from config file
			if (isFileNameCorrect(savedDataFileName)) {
				throw new ServerException(ServerErrorCode.CONFIG_FILE_NOT_READ, "Имя файла не корректно!");
			}
			File file = new File(savedDataFileName);
			try {
				String fileName = file.getCanonicalPath();
				fileName.trim();
			} catch (IOException e) {
				throw new ServerException(ServerErrorCode.CONFIG_FILE_NOT_READ,
						savedDataFileName + " " + e.getMessage());
			}
		} else {// Start server with default settings

		}
		serverStarted = true;
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
		if (!IsServerStarted()) {
			throw new ServerException(ServerErrorCode.SERVER_NOT_STARTED);
		}
		serverStarted = false;
		throw new ServerException("Test stop exception");
	}

	/**
	 * @return boolean - Server started state
	 */
	public boolean IsServerStarted() {
		return serverStarted;
	}

	/**
	 * Register new user
	 * 
	 * @param jsonRequest - JSON string with Username and password for new user
	 */
	public String registerUser(String jsonRequest) {
		if (!IsServerStarted()) {
			return jsonError(new ServerException(ServerErrorCode.SERVER_NOT_STARTED));
		}
		RegisterUserDtoRequest registerUserDTO;
		try {
			registerUserDTO = new Gson().fromJson(jsonRequest, RegisterUserDtoRequest.class);
		} catch (JsonSyntaxException e) {
			return jsonError(new ServerException(ServerErrorCode.JSON_SYNTAX_ERROR));
		}
		try {
			return new Gson().toJson(new UserService().registerUser(registerUserDTO));
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
		if (!IsServerStarted()) {
			return jsonError(new ServerException(ServerErrorCode.SERVER_NOT_STARTED));
		}
		LoginUserDtoRequest userDto;
		try {
			userDto = new Gson().fromJson(jsonRequest, LoginUserDtoRequest.class);
		} catch (JsonSyntaxException e) {
			return jsonError(new ServerException(ServerErrorCode.JSON_SYNTAX_ERROR));
		}
		try {
			return new Gson().toJson(new UserService().loginUser(userDto));
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
		if (!IsServerStarted()) {
			return jsonError(new ServerException(ServerErrorCode.SERVER_NOT_STARTED));
		}
		
		return null;
	}

	/**
	 * Convert Error to JSON string
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
