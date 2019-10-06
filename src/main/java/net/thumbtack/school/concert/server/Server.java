package net.thumbtack.school.concert.server;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.thumbtack.school.concert.exception.ServerErrorCode;
import net.thumbtack.school.concert.exception.ServerException;

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
		if (serverStarted) {
			throw new ServerException(ServerErrorCode.SERVER_ALREADY_STARTED);
		}

		if (savedDataFileName != null) {//Start server with settings from config file
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
		}
		else {//Start server with default settings

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
		if (!serverStarted) {
			throw new ServerException(ServerErrorCode.SERVER_NOT_STARTED);
		}
		serverStarted = false;
		throw new ServerException("Test stop exception");
	}

	/**
	 * Register new user
	 * 
	 * @param jsonRequest - JSON string with Username and password for new user
	 */
	public String registerUser(String jsonRequest) throws ServerException {
		throw new ServerException("This method is in development");
	}

	/**
	 * Login user
	 * 
	 * @param jsonRequest - JSON string with Username and password for login user
	 */
	public String loginUser(String jsonRequest) throws ServerException {
		UUID uuid = UUID.randomUUID();
		String uuidString = uuid.toString();

		throw new ServerException("This method is in development");
	}

	/**
	 * Logout user
	 * 
	 * @param jsonRequest - JSON string with user UUID for logout
	 */
	public String logoutUser(String jsonRequest) throws ServerException {
		throw new ServerException("This method is in development");
	}

	public static boolean isFileNameCorrect(String name) {
		//if (name.trim().isEmpty()) return false;
		Pattern pattern = Pattern.compile("(.+)?[><\\|\\?*/:\\\\\"](.+)?");
		Matcher matcher = pattern.matcher(name);
		return !matcher.find();
	}

}
