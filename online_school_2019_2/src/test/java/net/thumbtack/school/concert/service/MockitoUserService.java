package net.thumbtack.school.concert.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import net.thumbtack.school.concert.dao.SessionDao;
import net.thumbtack.school.concert.dao.UserDao;
import net.thumbtack.school.concert.daoimpl.SessionDaoMyBatisImpl;
import net.thumbtack.school.concert.daoimpl.UserDaoMyBatisImpl;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.dto.response.LoginUserDtoResponse;
import net.thumbtack.school.concert.exception.ServerErrorCode;
import net.thumbtack.school.concert.exception.ServerException;
import net.thumbtack.school.concert.model.User;
import net.thumbtack.school.concert.utils.MyBatisUtils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MockitoUserService {

    @BeforeAll()
    public static void setUp() {
        boolean initSqlSessionFactory = MyBatisUtils.initSqlSessionFactory();
        if (!initSqlSessionFactory) {
            throw new RuntimeException("Can't create connection, stop");
        }
    }

    @BeforeEach()
    public void clearDatabase() {
        new SessionDaoMyBatisImpl().deleteAll();
        new UserDaoMyBatisImpl().deleteAll();
    }
	
    @Test
    public void testRegisterUser() throws ServerException {
        DataBase db = new DataBase();
        try {
            db.open("dbfile.json");
        } catch (JsonSyntaxException | IOException e) {
            fail(e.getMessage());
        }

        UserService userService = new UserService();

        User user = new User("John", "Smith", "johnsmith", "password123");

        UserDao userDao = mock(UserDao.class);
        userService.setUserDao(userDao);


        SessionDao sessionDao = mock(SessionDao.class);
        userService.setSessionDao(sessionDao);


        //when(userDao.get("johnsmith")).thenReturn(user);

        //Check Exception second register
        doNothing()
                .doThrow(new ServerException(ServerErrorCode.USERNAME_ALREADY_IN_USE))
                .when(userDao).add(user);



        // Positive check
        String responseToken = userService.registerUser(new Gson().toJson(user));

        try {
            userService.registerUser(new Gson().toJson(user));
        } catch (ServerException e) {

        }






        /*try {
            userService.registerUser(null);
            fail();
        } catch (ServerException e) {
            assertEquals(ServerErrorCode.JSON_SYNTAX_ERROR, e.getServerErrorCode());
        }
        try {
            userService.registerUser("");
            fail();
        } catch (ServerException e) {
            assertEquals(ServerErrorCode.JSON_SYNTAX_ERROR, e.getServerErrorCode());
        }
        try {
            userService.registerUser("Hello world");
            fail();
        } catch (ServerException e) {
            assertEquals(ServerErrorCode.JSON_SYNTAX_ERROR, e.getServerErrorCode());
        }
        try {
            userService.registerUser("{\"firstName\":\"asd\"}");
            fail();
        } catch (ServerException e) {
            assertEquals(ServerErrorCode.BAD_REQUEST, e.getServerErrorCode());
        }
        try {
            userService.registerUser(
                    "{\"firstName\":\"qwe\", \"lastName\":\"asdqwe\", \"login\":\"123qwe\", \"password\":\"asd\"}");
            fail();
        } catch (ServerException e) {
            assertEquals(ServerErrorCode.BAD_REQUEST, e.getServerErrorCode());
        }
        try {
            userService.registerUser(
                    "{\"firstName\":\"\", \"lastName\":\"asdqwe\", \"login\":\"123qwe\", \"password\":\"qwe45678\"}");
            fail();
        } catch (ServerException e) {
            assertEquals(ServerErrorCode.BAD_REQUEST, e.getServerErrorCode());
        }
        try {
            userService.registerUser("{\"firstName\":\"fname\", \"lastName\":\"\", \"login\":\"login\", \"password\":\"pswd\"}");
            fail();
        } catch (ServerException e) {
            assertEquals(ServerErrorCode.BAD_REQUEST, e.getServerErrorCode());
        }
        try {
            userService.registerUser("{\"firstName\":\"fname\", \"lastName\":\"lname\", \"login\":\"\", \"password\":\"pswd\"}");
            fail();
        } catch (ServerException e) {
            assertEquals(ServerErrorCode.BAD_REQUEST, e.getServerErrorCode());
        }
        try {
            userService.registerUser(
                    "{\"firstName\":\"fname\", \"lastName\":\"lname\", \"login\":\"login\", \"password\":\"\"}");
            fail();
        } catch (ServerException e) {
            assertEquals(ServerErrorCode.BAD_REQUEST, e.getServerErrorCode());
        }
        try {
            userService.registerUser(
                    "{\"firstNme\":\"asd\", \"lastName\":\"asdqwe\", \"login\":\"asd123qwe\", \"password\":\"asd5678!\"}");
            fail();
        } catch (ServerException e) {
            assertEquals(ServerErrorCode.BAD_REQUEST, e.getServerErrorCode());
        }

        try {
            response = userService.registerUser(
                    "{\"firstName\":\"TestFName\", \"lastName\":\"TestLName\", \"login\":\"testLogin\", \"password\":\"pswd\"}");
        } catch (ServerException e) {
            fail(e.getServerErrorText());
        }
        assertFalse(new Gson().fromJson(response, LoginUserDtoResponse.class).getToken().isEmpty());
        assertEquals(new Gson().fromJson(response, LoginUserDtoResponse.class).getToken().length(), 36);

        try {
            userService.registerUser(
                    "{\"firstName\":\"TestFName\", \"lastName\":\"TestLName\", \"login\":\"testLogin\", \"password\":\"pswd\"}");
            fail();
        } catch (ServerException e) {
            assertEquals(ServerErrorCode.USERNAME_ALREADY_IN_USE, e.getServerErrorCode());
        }*/
        
        db.close();
    }

    @Test
    public void testLoginLogoutUser() {
        DataBase db = new DataBase();
        try {
            db.open("dbfile.json");
        } catch (JsonSyntaxException | IOException e) {
            fail(e.getMessage());
        }

        UserService us = new UserService();

        String response = new String();

        try {
            us.loginUser("{\"login\":\"login\", \"password\":\"psw5d\"}");
            fail();
        } catch (ServerException e) {
            assertEquals(ServerErrorCode.LOGIN_INCORRECT, e.getServerErrorCode());
        }
        try {
            us.loginUser("{\"login\":\"asd123\", \"password\":\"asd5678\"}");
            fail();
        } catch (ServerException e) {
            assertEquals(ServerErrorCode.LOGIN_INCORRECT, e.getServerErrorCode());
        }
        try {
            response = us.loginUser("{\"login\":\"login\", \"password\":\"pswd\"}");
        } catch (ServerException e) {
            fail(e.getServerErrorText());
        }
        assertFalse(new Gson().fromJson(response, LoginUserDtoResponse.class).getToken().isEmpty());
        assertEquals(new Gson().fromJson(response, LoginUserDtoResponse.class).getToken().length(), 36);

        try {
            response = us.logoutUser(response);
        } catch (ServerException e) {
            fail(e.getServerErrorText());
        }

        db.close();
    }

    @Test
    public void testDeleteUser() {
        DataBase db = new DataBase();
        try {
            db.open("dbfile.json");
        } catch (JsonSyntaxException | IOException e) {
            fail(e.getMessage());
        }

        UserService us = new UserService();

        try {
            us.deleteUser("{\"token\":\"ffffffff-0000-1111-bea8-d9282a42ba48\"}");
            fail();
        } catch (ServerException e) {
            assertEquals(ServerErrorCode.TOKEN_INCORRECT, e.getServerErrorCode());
        }
        int userCnt = db.getUsers().size();
        int ratingCnt = db.getRatings().size();
        int songsCnt = db.getSongs().size();
        int sessionsCnt = db.getSessions().size();
        try {
            us.deleteUser("{\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
        } catch (ServerException e) {
            fail(e.getServerErrorText());
        }
        assertEquals(userCnt, db.getUsers().size() + 1);
        assertEquals(sessionsCnt, db.getSessions().size() + 1);
        assertEquals(ratingCnt, db.getRatings().size() + 3);
        assertEquals(songsCnt, db.getSongs().size() + 0);

        userCnt = db.getUsers().size();
        ratingCnt = db.getRatings().size();
        songsCnt = db.getSongs().size();
        sessionsCnt = db.getSessions().size();
        try {
            us.deleteUser("{\"token\":\"bc7844a3-94be-4606-b9eb-1e5876b75eef\"}");
        } catch (ServerException e) {
            fail(e.getServerErrorText());
        }
        assertEquals(userCnt, db.getUsers().size() + 1);
        assertEquals(sessionsCnt, db.getSessions().size() + 1);
        assertEquals(ratingCnt, db.getRatings().size() + 3);
        assertEquals(songsCnt, db.getSongs().size() + 2);

        db.close();
    }

}
