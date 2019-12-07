package net.thumbtack.school.concert.server;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.thumbtack.school.concert.dto.request.AddCommentDtoRequest;
import net.thumbtack.school.concert.dto.request.AddRatingDtoRequest;
import net.thumbtack.school.concert.dto.request.AddSongDtoRequest;
import net.thumbtack.school.concert.dto.request.DeleteSongDtoRequest;
import net.thumbtack.school.concert.dto.request.GetSongsDtoRequest;
import net.thumbtack.school.concert.dto.request.LoginUserDtoRequest;
import net.thumbtack.school.concert.dto.request.RegisterUserDtoRequest;
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
        assertFalse(new Gson().fromJson(server.addSongs(""), ErrorDtoResponse.class).getError().isEmpty());
        assertFalse(new Gson().fromJson(server.addSongs("{}"), ErrorDtoResponse.class).getError().isEmpty());
        String response = server.addSongs("{\"song\":[{\"songName\":\"songName\",\"composer\":[\"composer\"],\"author\":[\"author\"],\"singer\":\"\",\"length\":25}],\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
        assertEquals("{}", response);

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

        String response = server.getSongs("{\"token\":\"ffffffff-6053-4061-bea8-d9282a42ba48\"}");
        assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
        response = server.getSongs("{\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
        List<GetSongsDtoResponse> r = new Gson().fromJson(response, new TypeToken<List<GetSongsDtoResponse>>() {
        }.getType());
        assertEquals(5, r.size());

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

        String response = server
                .deleteSong("{\"songId\":\"songT4\", \"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");

        response = server.deleteSong("{\"songId\":\"songErr\", \"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
        assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());

        try {
            server.stopServer(null);
        } catch (ServerException e) {
            fail(e.getServerErrorText());
        }
    }

    @Test
    public void testRating() {
        Server server = new Server();
        try {
            server.startServer("dbfile.json");
        } catch (ServerException e) {
            fail(e.getServerErrorText());
        }

        String response = server.addRating("{\"songId\":\"songT1\",\"rating\":\"4\",\"token\":\"ffffffff-0000-4061-bea8-d9282a42ba48\"}");
        assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
        response = server.addRating("{\"songId\":\"songT1\",\"rating\":\"4\",\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
        assertNull(new Gson().fromJson(response, ErrorDtoResponse.class).getError());

        response = server.changeRating("{\"songId\":\"songT1\",\"rating\":\"4\",\"token\":\"ffffffff-0000-4061-bea8-d9282a42ba48\"}");
        assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
        response = server.changeRating("{\"songId\":\"songT1\",\"rating\":\"3\",\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
        assertNull(new Gson().fromJson(response, ErrorDtoResponse.class).getError());

        response = server.deleteRating("{\"songId\":\"songT1\",\"token\":\"ffffffff-0000-4061-bea8-d9282a42ba48\"}");
        assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
        response = server.deleteRating("{\"songId\":\"songT1\",\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
        assertNull(new Gson().fromJson(response, ErrorDtoResponse.class).getError());

        try {
            server.stopServer(null);
        } catch (ServerException e) {
            fail(e.getServerErrorText());
        }
    }

    @Test
    public void testComment() {
        Server server = new Server();
        try {
            server.startServer("dbfile.json");
        } catch (ServerException e) {
            fail(e.getServerErrorText());
        }

        String response = server.addComment("{\"songId\":\"songT1\",\"comment\":\"TestComment\",\"token\":\"ffffffff-0000-1234-bea8-d9282a42ba48\"}");
        assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
        response = server.addComment("{\"songId\":\"songT1\",\"comment\":\"TestComment\",\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
        assertNull(new Gson().fromJson(response, ErrorDtoResponse.class).getError());

        response = server.changeComment("{\"songId\":\"songT1\",\"comment\":\"TestComment\",\"token\":\"ffffffff-0000-1234-bea8-d9282a42ba48\"}");
        assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
        response = server.changeComment("{\"songId\":\"songT1\",\"comment\":\"TestComment\",\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
        assertNull(new Gson().fromJson(response, ErrorDtoResponse.class).getError());

        response = server.disclaimComment("{\"songId\":\"songT1\",\"token\":\"ffffffff-0000-1234-bea8-d9282a42ba48\"}");
        assertFalse(new Gson().fromJson(response, ErrorDtoResponse.class).getError().isEmpty());
        response = server.disclaimComment("{\"songId\":\"songT1\",\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
        assertNull(new Gson().fromJson(response, ErrorDtoResponse.class).getError());

        try {
            server.stopServer(null);
        } catch (ServerException e) {
            fail(e.getServerErrorText());
        }
    }
    
    @Test
    public void testPositiveAll() {
        Server server = new Server();
        try {
            server.startServer("dbfile.json");
        } catch (ServerException e) {
            fail(e.getServerErrorText());
        }
        String userLogin = "ivanov123";
        String userPassword = "passIvanov";
        //Register new User
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest();
        registerUserDtoRequest.setFirstName("Иван");
        registerUserDtoRequest.setLastName("Иванов");
        registerUserDtoRequest.setLogin(userLogin);
        registerUserDtoRequest.setPassword(userPassword);
        String response = server.registerUser(new Gson().toJson(registerUserDtoRequest));
        String token = new Gson().fromJson(response, LoginUserDtoResponse.class).getToken();
        assertEquals(token.length(), 36);
        //Login user
        LoginUserDtoRequest loginUserDtoRequest = new LoginUserDtoRequest();
        loginUserDtoRequest.setLogin(userLogin);
        loginUserDtoRequest.setPassword(userPassword);
        response = server.loginUser(new Gson().toJson(loginUserDtoRequest));
        token = new Gson().fromJson(response, LoginUserDtoResponse.class).getToken();
        
        //Get songs list
        GetSongsDtoRequest getSongsDtoRequest = new GetSongsDtoRequest();
        getSongsDtoRequest.setToken(token);
        response = server.getSongs(new Gson().toJson(getSongsDtoRequest));
        List<GetSongsDtoResponse> respList = new Gson().fromJson(response, new TypeToken<List<GetSongsDtoResponse>>(){}.getType());
        assertEquals(5, respList.size());
        //Add filter by singer
        getSongsDtoRequest.setSinger("singer");
        response = server.getSongs(new Gson().toJson(getSongsDtoRequest));
        respList = new Gson().fromJson(response, new TypeToken<List<GetSongsDtoResponse>>(){}.getType());
        assertEquals(2, respList.size());
        
        //Add rating
        GetSongsDtoResponse song = respList.get(0);
        AddRatingDtoRequest addRatingDtoRequest = new AddRatingDtoRequest();
        addRatingDtoRequest.setToken(token);
        addRatingDtoRequest.setSongId(song.getSongId());
        addRatingDtoRequest.setRating(1);
        response = server.addRating(new Gson().toJson(addRatingDtoRequest));
        assertEquals("{}", response);
        response = server.getSongs(new Gson().toJson(getSongsDtoRequest));
        respList = new Gson().fromJson(response, new TypeToken<List<GetSongsDtoResponse>>(){}.getType());
        assertNotEquals(song.getSongName(), respList.get(0).getSongName());
        assertTrue(Math.abs(respList.get(1).getRating()-3)<0.0001);
        //Change rating
        addRatingDtoRequest.setRating(2);
        response = server.changeRating(new Gson().toJson(addRatingDtoRequest));
        assertEquals("{}", response);
        response = server.getSongs(new Gson().toJson(getSongsDtoRequest));
        respList = new Gson().fromJson(response, new TypeToken<List<GetSongsDtoResponse>>(){}.getType());
        assertTrue(Math.abs(respList.get(1).getRating()-3.5)<0.0001);
        //Delete rating
        response = server.deleteRating(new Gson().toJson(addRatingDtoRequest));
        assertEquals("{}", response);
        response = server.getSongs(new Gson().toJson(getSongsDtoRequest));
        respList = new Gson().fromJson(response, new TypeToken<List<GetSongsDtoResponse>>(){}.getType());
        assertEquals(song.getSongName(), respList.get(0).getSongName());
        assertTrue(Math.abs(respList.get(0).getRating()-5)<0.0001);
        
        //Add new comment
        String testComment = "Test comment";
        AddCommentDtoRequest addCommentDtoRequest = new AddCommentDtoRequest();
        addCommentDtoRequest.setToken(token);
        addCommentDtoRequest.setSongId(song.getSongId());
        addCommentDtoRequest.setComment(testComment);
        response = server.addComment(new Gson().toJson(addCommentDtoRequest));
        assertEquals("{}", response);
        response = server.getSongs(new Gson().toJson(getSongsDtoRequest));
        respList = new Gson().fromJson(response, new TypeToken<List<GetSongsDtoResponse>>(){}.getType());
        String[] comments = respList.get(0).getComents();
        boolean err=true;
        for (String comment: comments) {
        	if (comment.equals(testComment)) {
        		err = false;
        		break;
        	}
        }
        assertFalse(err);
        //Change comment
        int oldCommentSize=comments.length;
        testComment = "My Test comment";
        addCommentDtoRequest.setComment(testComment);
        response = server.changeComment(new Gson().toJson(addCommentDtoRequest));
        assertEquals("{}", response);
        response = server.getSongs(new Gson().toJson(getSongsDtoRequest));
        respList = new Gson().fromJson(response, new TypeToken<List<GetSongsDtoResponse>>(){}.getType());
        comments = respList.get(0).getComents();
        assertEquals(oldCommentSize, comments.length);
        err=true;
        for (String comment: comments) {
        	if (comment.equals(testComment)) {
        		err = false;
        		break;
        	}
        }
        assertFalse(err);
        //Delete comment
        response = server.disclaimComment(new Gson().toJson(addCommentDtoRequest));
        assertEquals("{}", response);
        response = server.getSongs(new Gson().toJson(getSongsDtoRequest));
        respList = new Gson().fromJson(response, new TypeToken<List<GetSongsDtoResponse>>(){}.getType());
        comments = respList.get(0).getComents();
        assertEquals(oldCommentSize, comments.length);
        response = server.changeComment(new Gson().toJson(addCommentDtoRequest));
        assertNotEquals("{}", response);
        
        //Add new Song
        List<AddSongDtoRequest.Song> songList = new ArrayList<>();
        songList.add(new AddSongDtoRequest().new Song("newTestSongName1", Arrays.asList("composer"), Arrays.asList("author"), "singer", 123));
        AddSongDtoRequest addSongDtoRequest = new AddSongDtoRequest();
        addSongDtoRequest.setToken(token);
        addSongDtoRequest.setSong(songList);
        response = server.addSongs(new Gson().toJson(addSongDtoRequest));
        assertEquals("{}", response);
        //Add more new Songs
        songList.clear();
        songList.add(new AddSongDtoRequest().new Song("newTestSongName2", Arrays.asList("composer"), Arrays.asList("author2", "author2"), "singer2", 222));
        songList.add(new AddSongDtoRequest().new Song("newTestSongName3", Arrays.asList("composer2", "composer3"), Arrays.asList("author"), "singer2", 333));
        addSongDtoRequest.setSong(songList);
        response = server.addSongs(new Gson().toJson(addSongDtoRequest));
        assertEquals("{}", response);
        //Get songs (with added songs)
        getSongsDtoRequest = new GetSongsDtoRequest();
        getSongsDtoRequest.setToken(token);
        response = server.getSongs(new Gson().toJson(getSongsDtoRequest));
        respList = new Gson().fromJson(response, new TypeToken<List<GetSongsDtoResponse>>(){}.getType());
        assertEquals(5+3, respList.size());
        //
        getSongsDtoRequest.setSinger("singer2");
        response = server.getSongs(new Gson().toJson(getSongsDtoRequest));
        respList = new Gson().fromJson(response, new TypeToken<List<GetSongsDtoResponse>>(){}.getType());
        assertEquals(4, respList.size());
        //Delete song
        DeleteSongDtoRequest deleteSongDtoRequest = new DeleteSongDtoRequest();
        deleteSongDtoRequest.setToken(token);
        String songId = respList.stream().
        		filter(s -> s.getUserName().equals(userLogin)).
        		filter(s -> s.getSongName().equals("newTestSongName2")).
        		map(GetSongsDtoResponse::getSongId).
        		collect(Collectors.toList()).get(0);
        deleteSongDtoRequest.setSongId(songId);
        response = server.deleteSong(new Gson().toJson(deleteSongDtoRequest));
        assertEquals("{}", response);
        //Get song list
        getSongsDtoRequest = new GetSongsDtoRequest();
        getSongsDtoRequest.setToken(token);
        response = server.getSongs(new Gson().toJson(getSongsDtoRequest));
        respList = new Gson().fromJson(response, new TypeToken<List<GetSongsDtoResponse>>(){}.getType());
        assertEquals(5+2, respList.size());

        try {
            server.stopServer(null);
        } catch (ServerException e) {
            fail(e.getServerErrorText());
        }
    }

}
