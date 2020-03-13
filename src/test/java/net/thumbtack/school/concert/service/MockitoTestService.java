package net.thumbtack.school.concert.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.thumbtack.school.concert.dao.CommentDao;
import net.thumbtack.school.concert.dao.RatingDao;
import net.thumbtack.school.concert.dao.SessionDao;
import net.thumbtack.school.concert.dao.SongDao;
import net.thumbtack.school.concert.dao.UserDao;
import net.thumbtack.school.concert.dto.response.GetSongsDtoResponse;
import net.thumbtack.school.concert.dto.response.LoginUserDtoResponse;
import net.thumbtack.school.concert.exception.ServerErrorCode;
import net.thumbtack.school.concert.exception.ServerException;
import net.thumbtack.school.concert.model.Comment;
import net.thumbtack.school.concert.model.Rating;
import net.thumbtack.school.concert.model.Session;
import net.thumbtack.school.concert.model.Song;
import net.thumbtack.school.concert.model.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/*
 * В рамках своего 11-го задания написать метод, который регистрирует временного пользователя на сервере,
 * скачивает от его имени некоторый список (в зависимости от задачи), удаляет этого пользователя
 * и возвращает размер полученного списка (целое число). Другими словами, должен получиться робот-клиент для класса Server.
 * Написать тесты, которые проверяют корректность вычислений, удаления пользователя и сессии.
 * Возможные исключения также протестировать.
 */

public class MockitoTestService {


    @Test
    public void testConcertServices() throws ServerException {

        User user = new User("John", "Smith", "johnsmith", "password123");

        UserDao userDao = mock(UserDao.class);
        SessionDao sessionDao = mock(SessionDao.class);
        SongDao songDao = mock(SongDao.class);
        CommentDao commentDao = mock(CommentDao.class);
        RatingDao ratingDao = mock(RatingDao.class);

        UserService userService = new UserService(userDao, sessionDao, songDao, commentDao, ratingDao);

        //Set Exception for second register
        doNothing()
                .doThrow(new ServerException(ServerErrorCode.USERNAME_ALREADY_IN_USE))
                .when(userDao).add(user);

        // Positive check
        String responseToken = userService.registerUser(new Gson().toJson(user));
        //Second user register with throw exception
        ServerException thrown = assertThrows(ServerException.class,
                () -> userService.registerUser(new Gson().toJson(user))
        );
        // Check
        Assertions.assertAll(
                () -> assertEquals(new Gson().fromJson(responseToken, LoginUserDtoResponse.class).getToken().length(), 36),
                () -> assertEquals(ServerErrorCode.USERNAME_ALREADY_IN_USE, thrown.getServerErrorCode())
        );


        // Test Song List
        SongService songService = new SongService(sessionDao, songDao, commentDao, ratingDao);

        when(sessionDao.get(new Session(new Gson().fromJson(responseToken, LoginUserDtoResponse.class).getToken())))
                .thenReturn(user);
        Map<String, Song> songsMap = new HashMap<String, Song>() {{
            put("song1", new Song("SongName1",
                    Arrays.asList("composer1", "composer2"),
                    Arrays.asList("author"),
                    "singer",
                    555,
                    "user"));
            put("song2", new Song("SongName2",
                    Arrays.asList("composer3"),
                    Arrays.asList("author", "author2"),
                    "singer2",
                    666,
                    "johnsmith"));
            put("song3", new Song("SongName3",
                    Arrays.asList("composer1", "composer3"),
                    Arrays.asList("author3"),
                    "singer",
                    777,
                    "user"));
        }};
        when(songDao.get(null, null, null)).thenReturn(songsMap);

        List<Rating> ratingList = new ArrayList<Rating>() {{
            add(new Rating("song1", 5, "user"));
            add(new Rating("song2", 5, "user"));
            add(new Rating("song2", 3, "johnsmith"));
            add(new Rating("song3", 5, "user"));
            add(new Rating("song3", 4, "johnsmith"));
        }};
        when(ratingDao.get(anyList())).thenReturn(ratingList);

        when(commentDao.get(anyList())).thenReturn(Arrays.asList(new Comment("song3", "jonhsmith", "comment")));

        // Get all Songs
        String songJson = songService.getSongs(responseToken);
        List<GetSongsDtoResponse> respList = new Gson().fromJson(songJson, new TypeToken<List<GetSongsDtoResponse>>() {
        }.getType());

        // Check result
        Assertions.assertAll(
                () -> assertEquals(3, respList.size()),
                () -> assertEquals("song1", respList.get(0).getSongId()),
                () -> assertEquals("song3", respList.get(1).getSongId()),
                () -> assertEquals("song2", respList.get(2).getSongId()),
                () -> assertTrue(Math.abs(respList.get(1).getRating() - 4.5) < 0.001)
        );

        // Set mockito to check user delete
        when(commentDao.getList(null, user.getLogin())).thenReturn(new ArrayList<Comment>());
        when(songDao.get(user.getLogin())).thenReturn(new HashMap<String, Song>());
        when(ratingDao.getRatingList(user.getLogin())).thenReturn(new ArrayList<>());
        // First user delete
        userService.deleteUser(responseToken);
        Assertions.assertAll(
                () -> verify(ratingDao, times(1)).delete(null, user.getLogin()),
                () -> verify(commentDao, times(1)).update(new ArrayList<>()),
                () -> verify(songDao, times(1)).update(new HashMap<>()),
                () -> verify(songDao, times(1)).delete(new ArrayList<>()),
                () -> verify(sessionDao, times(1)).logout(new Session(new Gson().fromJson(responseToken, LoginUserDtoResponse.class).getToken())),
                () -> verify(userDao, times(1)).delete(user.getLogin())
        );

        // Second user delete
        when(sessionDao.get(new Session(new Gson().fromJson(responseToken, LoginUserDtoResponse.class).getToken())))
                .thenReturn(null);
        assertEquals(ServerErrorCode.TOKEN_INCORRECT,
                assertThrows(ServerException.class, () -> userService.deleteUser(responseToken)).getServerErrorCode()
        );
    }


    

}
