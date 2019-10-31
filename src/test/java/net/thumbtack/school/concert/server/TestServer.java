package net.thumbtack.school.concert.server;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.thumbtack.school.concert.dto.response.ErrorDtoResponse;
import net.thumbtack.school.concert.dto.response.GetSongsDtoResponse;
import net.thumbtack.school.concert.dto.response.LoginUserDtoResponse;
import net.thumbtack.school.concert.exception.ServerErrorCode;
import net.thumbtack.school.concert.exception.ServerException;

public class TestServer {
	@TempDir
	public Path tempDir;

	@Test
	public void testStartStopServerDefault() {
		Server server = new Server();

		try {
			server.startServer("");
			server.stopServer("");
		} catch (ServerException e) {
			fail(e.getServerErrorText());
		}
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
			server.startServer(tempDir.resolve("sd*f?.n").toString());
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.CONFIG_FILE_NOT_READ, e.getServerErrorCode());
		}
		try {
			server.startServer(null);
		} catch (ServerException e) {
			fail(e.getServerErrorText());
		}
		server.registerUser(
				"{\"firstName\":\"fname\", \"lastName\":\"lname\", \"login\":\"login\", \"password\":\"pswd\"}");
		server.registerUser(
				"{\"firstName\":\"fnameTwo\", \"lastName\":\"lnameTwo\", \"login\":\"login2\", \"password\":\"pswd2\"}");
		server.registerUser(
				"{\"firstName\":\"fname\", \"lastName\":\"lname\", \"login\":\"login3\", \"password\":\"pswd\"}");
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
		String response = server.loginUser(
				"{\"firstName\":\"fname\", \"lastName\":\"lname\", \"login\":\"login\", \"password\":\"pswd\"}");
		assertEquals(new Gson().fromJson(response, LoginUserDtoResponse.class).getToken().length(), 36);
		try {
			server.stopServer(tempDir + "/");
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.CONFIG_FILE_NOT_WRITED, e.getServerErrorCode());
		}
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
			fail(e.getServerErrorText());
		}

		assertFalse(new Gson().fromJson(server.registerUser(null), ErrorDtoResponse.class).getError().isEmpty());
		assertFalse(new Gson().fromJson(server.registerUser(""), ErrorDtoResponse.class).getError().isEmpty());
		assertFalse(
				new Gson().fromJson(server.registerUser("Hello world"), ErrorDtoResponse.class).getError().isEmpty());
		assertFalse(new Gson().fromJson(server.registerUser("{\"firstName\":\"asd\"}"), ErrorDtoResponse.class)
				.getError().isEmpty());
		String response = server.registerUser(
				"{\"firstName\":\"qwe\", \"lastName\":\"asdqwe\", \"login\":\"123qwe\", \"password\":\"asd\"}");
		assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
		response = server.registerUser(
				"{\"firstName\":\"\", \"lastName\":\"asdqwe\", \"login\":\"123qwe\", \"password\":\"qwe45678\"}");
		assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
		response = server.registerUser(
				"{\"firstName\":\"fname\", \"lastName\":\"\", \"login\":\"login\", \"password\":\"pswd\"}");
		assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
		response = server.registerUser(
				"{\"firstName\":\"fname\", \"lastName\":\"lname\", \"login\":\"\", \"password\":\"pswd\"}");
		assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
		response = server.registerUser(
				"{\"firstName\":\"fname\", \"lastName\":\"lname\", \"login\":\"login\", \"password\":\"\"}");
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
			fail(e.getServerErrorText());
		}
	}

	@Test
	public void testLoginLogoutUser() {
		Server server = new Server();
		try {
			server.startServer("dbfile.json");
		} catch (ServerException e) {
			fail(e.getServerErrorText());
		}

		assertFalse(new Gson().fromJson(server.loginUser(null), ErrorDtoResponse.class).getError().isEmpty());
		assertFalse(new Gson().fromJson(server.loginUser(""), ErrorDtoResponse.class).getError().isEmpty());
		assertFalse(new Gson().fromJson(server.loginUser("{}"), ErrorDtoResponse.class).getError().isEmpty());
		String response = server.loginUser("{\"login\":\"login\", \"password\":\"psw5d\"}");
		assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
		response = server.loginUser("{\"login\":\"\", \"password\":\"pswd\"}");
		assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
		response = server.loginUser("{\"login\":\"login\", \"password\":\"\"}");
		assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
		response = server.loginUser("{\"login\":\"asd123\", \"password\":\"\"}");
		assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
		response = server.loginUser("{\"login\":\"asd123\", \"password\":\"asd5678\"}");
		assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
		response = server.loginUser("{\"login\":\"login\", \"password\":\"pswd\"}");
		assertEquals(new Gson().fromJson(response, LoginUserDtoResponse.class).getToken().length(), 36);

		response = server.logoutUser(response);
		assertNull(new Gson().fromJson(server.logoutUser(response), LoginUserDtoResponse.class).getToken());
		assertFalse(new Gson().fromJson(server.logoutUser(""), ErrorDtoResponse.class).getError().isEmpty());

		try {
			server.stopServer(null);
		} catch (ServerException e) {
			fail(e.getServerErrorText());
		}
	}

	@Test
	public void testDeleteUser() {
		Server server = new Server();
		try {
			server.startServer("dbfile.json");
		} catch (ServerException e) {
			fail(e.getServerErrorText());
		}

		assertFalse(new Gson().fromJson(server.deleteUser(null), ErrorDtoResponse.class).getError().isEmpty());
		assertNull(new Gson().fromJson(server.deleteUser("{\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}"),
				ErrorDtoResponse.class).getError());
		
		try {
			server.stopServer(null);
		} catch (ServerException e) {
			fail(e.getServerErrorText());
		}
	}

	@Test
	public void testAddSong() {
		Server server = new Server();
		try {
			server.startServer("dbfile.json");
		} catch (ServerException e) {
			fail(e.getServerErrorText());
		}

		assertFalse(new Gson().fromJson(server.addSongs(null), ErrorDtoResponse.class).getError().isEmpty());
		assertFalse(new Gson().fromJson(server.loginUser(""), ErrorDtoResponse.class).getError().isEmpty());
		assertFalse(new Gson().fromJson(server.loginUser("{}"), ErrorDtoResponse.class).getError().isEmpty());
		String response = server.loginUser("{\"login\":\"login\", \"password\":\"psw5d\"}");
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
			fail(e.getServerErrorText());
		}
	}

	@Test
	public void testGetSong() {
		Server server = new Server();
		try {
			server.startServer("dbfile.json");
		} catch (ServerException e) {
			fail(e.getServerErrorText());
		}

		// GetSongsDtoRequest gs = new GetSongsDtoRequest();
		// gs.setToken("aeb9610c-6053-4061-bea8-d9282a42ba48");
		// server.getSongs(new
		// Gson().toJson(gs));//{"token":"aeb9610c-6053-4061-bea8-d9282a42ba48"}
		String response = server.getSongs("{\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
		List<GetSongsDtoResponse> r = new Gson().fromJson(response, new TypeToken<List<GetSongsDtoResponse>>() {
		}.getType());
		assertEquals(r.size(), 4);

		try {
			server.stopServer(null);
		} catch (ServerException e) {
			fail(e.getServerErrorText());
		}
	}

	@Test
	public void testDeleteSong() {
		Server server = new Server();
		try {
			server.startServer("dbfile.json");
		} catch (ServerException e) {
			fail(e.getServerErrorText());
		}

		String response = server.getSongs("{\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
		List<GetSongsDtoResponse> defSongs = new Gson().fromJson(response, new TypeToken<List<GetSongsDtoResponse>>() {
		}.getType());

		response = server
				.deleteSong("{\"songName\":\"songName13\", \"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");

		response = server.getSongs("{\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
		List<GetSongsDtoResponse> newSongs = new Gson().fromJson(response, new TypeToken<List<GetSongsDtoResponse>>() {
		}.getType());
		assertEquals(defSongs.size(), newSongs.size() + 1);

		try {
			server.stopServer(null);
		} catch (ServerException e) {
			fail(e.getServerErrorText());
		}
	}

}
