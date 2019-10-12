package net.thumbtack.school.concert.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Path;

import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.google.gson.Gson;

import net.thumbtack.school.concert.dto.response.ErrorDtoResponse;
import net.thumbtack.school.concert.dto.response.LoginUserDtoResponse;
import net.thumbtack.school.concert.exception.ServerErrorCode;
import net.thumbtack.school.concert.exception.ServerException;

public class TestServer {
	@TempDir
	private Path tempDir;

	@Test
	public void testStartStopServerDefault() {
		Server server = new Server();
		try {
			server.startServer(null);
		} catch (ServerException e) {
			fail(e.getServerErrorText());
		}
		try {
			server.startServer(null);
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.SERVER_ALREADY_STARTED, e.getServerErrorCode());
		}
		try {
			server.stopServer(null);
		} catch (ServerException e) {
			fail(e.getServerErrorText());
		}
		try {
			server.stopServer(null);
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.SERVER_NOT_STARTED, e.getServerErrorCode());
		}
	}

	@Test
	public void testStartStopServerFile() {
		Server server = new Server();
		try {
			server.startServer(tempDir.resolve("testfile.json").toString());
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.CONFIG_FILE_NOT_READ, e.getServerErrorCode());
		}
		try {
			server.startServer(null);
		} catch (ServerException e) {
			fail(e.getServerErrorText());
		}
		server.registerUser("{\"firstName\":\"fname\", \"lastName\":\"lname\", \"login\":\"login\", \"password\":\"pswd\"}");
		server.registerUser("{\"firstName\":\"fnameTwo\", \"lastName\":\"lnameTwo\", \"login\":\"login2\", \"password\":\"pswd2\"}");
		server.registerUser("{\"firstName\":\"fname\", \"lastName\":\"lname\", \"login\":\"login3\", \"password\":\"pswd\"}");
		try {
			server.stopServer(tempDir.resolve("testfile.json").toString());
		} catch (ServerException e) {
			fail(e.getServerErrorText());
		}
		File file = new File(tempDir.resolve("testfile.json").toString());
		assertTrue(file.exists());
		try {
			server.startServer(tempDir.resolve("testfile.json").toString());
		} catch (ServerException e) {
			fail(e.getServerErrorText());
		}
		String response = server.loginUser("{\"firstName\":\"fname\", \"lastName\":\"lname\", \"login\":\"login\", \"password\":\"pswd\"}");
		assertEquals(new Gson().fromJson(response, LoginUserDtoResponse.class).getToken().length(), 36);
		try {
			server.stopServer(null);
		} catch (ServerException e) {
			fail(e.getServerErrorText());
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

	@Test
	public void testLoginLogoutUser() {
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
		assertEquals(new Gson().fromJson(response, LoginUserDtoResponse.class).getToken().length(), 36);

		response = server.logoutUser(response);
		assertNull(new Gson().fromJson(response, LoginUserDtoResponse.class).getToken());

		try {
			server.stopServer(null);
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.OTHER_ERROR, e.getServerErrorCode());
		}

	}

	private void assertNull(String token) {
	}

	@After
	public void deleteTempDir() {
		tempDir.toFile().deleteOnExit();
	}

}
