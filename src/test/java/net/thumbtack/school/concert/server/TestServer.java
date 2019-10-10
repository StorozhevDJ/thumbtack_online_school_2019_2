package net.thumbtack.school.concert.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.google.gson.Gson;

import net.thumbtack.school.concert.dto.response.ErrorDtoResponse;
import net.thumbtack.school.concert.dto.response.LoginUserDtoResponse;
import net.thumbtack.school.concert.exception.ServerErrorCode;
import net.thumbtack.school.concert.exception.ServerException;

public class TestServer {
	@TempDir
	public Path tempDir;

	// @Ignore
	@Test
	public void testStartStopServerDefault() {
		Server server = new Server();
		try {
			server.startServer(null);
			//server.startServer("testfile.json");
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.CONFIG_FILE_NOT_WRITED, e.getServerErrorCode());
		}
		try {
			//server.stopServer(null);
			server.stopServer("testfile.json");
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.OTHER_ERROR, e.getServerErrorCode());
		}

	}

	@Test
	public void testRegisterUser() {
		Server server = new Server();
		try {
			server.startServer(null);
		} catch (ServerException e) {
			fail();
		}
		String response = server.registerUser(null);
		assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
		response = server.registerUser("");
		assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
		response = server.registerUser("Hello world");
		assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
		response = server.registerUser("{\"firstName\":\"asd\"}");
		assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
		response = server.registerUser(
				"{\"firstName\":\"qwe\", \"lastName\":\"asdqwe\", \"login\":\"123qwe\", \"password\":\"asd\"}");
		assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
		response = server.registerUser(
				"{\"firstNme\":\"asd\", \"lastName\":\"asdqwe\", \"login\":\"asd123qwe\", \"password\":\"asd5678!\"}");
		assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
		response = server.registerUser(
				"{\"firstName\":\"fname\", \"lastName\":\"lname\", \"login\":\"login\", \"password\":\"pswd\"}");
		assertFalse(new Gson().fromJson(response, LoginUserDtoResponse.class).getToken().isEmpty());
		assertEquals(new Gson().fromJson(response, LoginUserDtoResponse.class).getToken().length(), 36);
		response = server.registerUser(
				"{\"firstName\":\"fname\", \"lastName\":\"lname\", \"login\":\"login\", \"password\":\"pswd\"}");
		assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());

		try {
			server.stopServer(null);
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.OTHER_ERROR, e.getServerErrorCode());
		}
	}

	/*@Test
	public void testLoginUser() {
		Server server = new Server();
		try {
			server.startServer(null);
		} catch (ServerException e) {
			fail();
		}
		server.registerUser(
				"{\"firstName\":\"fname\", \"lastName\":\"lname\", \"login\":\"login\", \"password\":\"pswd\"}");

		String response = server.loginUser(null);
		assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
		response = server.loginUser("");
		assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
		response = server.loginUser("{}");
		assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
		response = server.loginUser("{\"login\":\"login\", \"password\":\"psw5d\"}");
		assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
		response = server.loginUser("{\"login\":\"asd123\", \"password\":\"\"}");
		assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
		response = server.loginUser("{\"login\":\"asd123\", \"password\":\"asd5678\"}");
		assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
		response = server.loginUser("{\"login\":\"login\", \"password\":\"pswd\"}");
		assertTrue(new Gson().fromJson(response, LoginUserDtoResponse.class).getToken().length() == 36);

		response = server.logoutUser("");
		assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());

		try {
			server.stopServer(null);
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.OTHER_ERROR, e.getServerErrorCode());
		}

	}*/

	/*
	 * public void testFileReadWriteByteArray1() throws IOException { byte[]
	 * arrayToWrite = {0, 1, 5, -34, 67, -123}; File file =
	 * Files.createFile(tempDir.resolve("test.dat")).toFile();
	 * FileService.writeByteArrayToBinaryFile(file, arrayToWrite);
	 * assertTrue(file.exists()); assertEquals(arrayToWrite.length, file.length());
	 * byte[] arrayRead = FileService.readByteArrayFromBinaryFile(file);
	 * assertArrayEquals(arrayToWrite, arrayRead); }
	 */
}
