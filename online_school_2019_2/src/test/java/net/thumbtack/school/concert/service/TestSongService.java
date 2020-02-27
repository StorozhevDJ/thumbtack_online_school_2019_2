package net.thumbtack.school.concert.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.*;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.dto.request.AddSongDtoRequest;
import net.thumbtack.school.concert.dto.request.AddSongDtoRequest.Song;
import net.thumbtack.school.concert.dto.response.GetSongsDtoResponse;
import net.thumbtack.school.concert.dto.request.GetSongsDtoRequest;
import net.thumbtack.school.concert.exception.ServerErrorCode;
import net.thumbtack.school.concert.exception.ServerException;
import net.thumbtack.school.concert.model.Session;
import net.thumbtack.school.concert.model.User;

public class TestSongService {

    @Test
    public void testAddSongs() {

        DataBase db = new DataBase();

        Map<String, User> mu = new HashMap<>();
        mu.put("login", new User("fname", "lname", "login", "pass"));
        db.setUsers(mu);
        BidiMap<String, Session> sessions = new DualHashBidiMap<>();
        sessions.put("login", new Session("fea8056a-cd2e-4d9b-8d73-c165cd135299"));
        db.setSessions(sessions);

        AddSongDtoRequest sr = new AddSongDtoRequest();
        sr.setToken("fea8056a-cd2e-4d9b-8d73-c165cd135299");
        List<Song> songs = new ArrayList<>();
        songs.add(new AddSongDtoRequest().new Song("songName", Arrays.asList("composer"), Arrays.asList("author"), "",
                25));
        sr.setSong(songs);

        SongService ss = new SongService();
        try {
            ss.addSong("");
            fail();
        } catch (ServerException e) {
            assertEquals(ServerErrorCode.JSON_SYNTAX_ERROR, e.getServerErrorCode());
        }
        try {
            ss.addSong("!\"song\":[{\"songName\":\"testSocd135299\"}");
            fail();
        } catch (ServerException e) {
            assertEquals(ServerErrorCode.JSON_SYNTAX_ERROR, e.getServerErrorCode());
        }
        try {
            ss.addSong(
                    "{\"song\":[{\"songName\":\"\",\"composer\":[\"composer\"],\"author\":[\"author\"],\"singer\":\"\",\"length\":25}],\"token\":\"fea899\"}");
            fail();
        } catch (ServerException e) {
            assertEquals(ServerErrorCode.BAD_REQUEST, e.getServerErrorCode());
        }
        try {
            ss.addSong(
                    "{\"song\":[{\"songName\":\"songName\",\"composer\":[\"composer\"],\"author\":[\"author\"],\"singer\":\"\",\"length\":25}],\"token\":\"ffffffff-cd2e-4d9b-8d73-c165cd135299\"}");
            fail();
        } catch (ServerException e) {
            assertEquals(ServerErrorCode.TOKEN_INCORRECT, e.getServerErrorCode());
        }
        try {
            ss.addSong(new Gson().toJson(sr));
        } catch (ServerException e) {
            fail(e.getServerErrorText());
        }
        GetSongsDtoRequest getSongs = new GetSongsDtoRequest();
        // getSongs.setAuthor();
        getSongs.setSinger("test Singer Д");
        List<String> alstr2 = new ArrayList<String>();
        alstr2.add("testCom-poser Ф.");
        alstr2.add("test Composer2");
        getSongs.setComposer(alstr2);
        List<String> alstr = new ArrayList<String>();
        alstr.add("test Author");
        getSongs.setAuthor(alstr);
        getSongs.setToken("fea8056a-cd2e-4d9b-8d73-c165cd135299");
        new Gson().toJson(getSongs);
        try {
            ss.getSongs(
                    "{\"composer\":[\"testCom-poser Ф.\",\"test Composer2\"],\"author\":[\"test Author\"],\"singer\":\"test Singer Д\",\"token\":\"fea8056a-cd2e-4d9b-8d73-c165cd135299\"}");
            ss.getSongs(new Gson().toJson(getSongs));
        } catch (ServerException e) {
            fail(e.getServerErrorText());
        }
    }

    @Test
    public void testGetSongList() {
        DataBase db = new DataBase();
        try {
            db.open("dbfile.json");
        } catch (JsonSyntaxException | IOException e) {
            fail(e.getMessage());
        }

        SongService ss = new SongService();
        try {
            ss.getSongs("");
            fail();
        } catch (ServerException e) {
            assertEquals(ServerErrorCode.JSON_SYNTAX_ERROR, e.getServerErrorCode());
        }
        try {
            ss.getSongs("{\"composer\":[\"tes");
            fail();
        } catch (ServerException e) {
            assertEquals(ServerErrorCode.JSON_SYNTAX_ERROR, e.getServerErrorCode());
        }
        try {
            ss.getSongs(
                    "{\"composer\":[\"testCom-poser Ф.\"],\"author\":[\"test %\"],\"singer\":\"\",\"token\":\"fea8056a-cd2e-4d9b-8d73-c165cd135299\"}");
            fail();
        } catch (ServerException e) {
            assertEquals(ServerErrorCode.BAD_REQUEST, e.getServerErrorCode());
        }
        try {
            ss.getSongs("{\"token\":\"ffffffff-cd2e-4d9b-8d73-c165cd135299\"}");
            fail();
        } catch (ServerException e) {
            assertEquals(ServerErrorCode.TOKEN_INCORRECT, e.getServerErrorCode());
        }
        String result = new String();
        try {
            result = ss.getSongs(
                    "{\"composer\":[\"testCom-poser Ф.\",\"test Composer2\"],\"author\":[\"test Author\"],\"singer\":\"test Singer Д\",\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
        } catch (ServerException e) {
            fail(e.getServerErrorText());
        }
        assertEquals("[]", result);
        try {
            result = ss.getSongs("{\"singer\":\"singer\",\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
        } catch (ServerException e) {
            fail(e.getServerErrorText());
        }
        List<GetSongsDtoResponse> respList = new Gson().fromJson(result, new TypeToken<List<GetSongsDtoResponse>>() {
        }.getType());
        assertFalse(respList.isEmpty());
        try {
            result = ss.getSongs("{\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
        } catch (ServerException e) {
            fail(e.getServerErrorText());
        }
        respList = new Gson().fromJson(result, new TypeToken<List<GetSongsDtoResponse>>() {
        }.getType());
        assertFalse(respList.isEmpty());
    }
    
    @Test
    public void testGetAllSongList() {
        DataBase db = new DataBase();
        try {
            db.open("dbfile.json");
        } catch (JsonSyntaxException | IOException e) {
            fail(e.getMessage());
        }
        String result = new String();
        try {
            result = new SongService().getAllSongs("{\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
        } catch (ServerException e) {
            fail(e.getServerErrorText());
        }
        List<GetSongsDtoResponse> respList = new Gson().fromJson(result, new TypeToken<List<GetSongsDtoResponse>>() {
        }.getType());
        assertEquals(6, respList.size());
    }

    @Test
    public void testDeleteSong() {
        DataBase db = new DataBase();
        try {
            db.open("dbfile.json");
        } catch (JsonSyntaxException | IOException e) {
            fail(e.getMessage());
        }

        SongService ss = new SongService();
        String response = new String();
        try {
            ss.deleteSong("{\"tok");
            fail();
        } catch (ServerException e) {
            assertEquals(ServerErrorCode.JSON_SYNTAX_ERROR, e.getServerErrorCode());
        }
        try {
            ss.deleteSong("");
            fail();
        } catch (ServerException e) {
            assertEquals(ServerErrorCode.JSON_SYNTAX_ERROR, e.getServerErrorCode());
        }
        try {
            ss.deleteSong("{\"token\":\"aeb942ba48\"}");
            fail();
        } catch (ServerException e) {
            assertEquals(ServerErrorCode.BAD_REQUEST, e.getServerErrorCode());
        }
        try {
            ss.deleteSong("{\"songId\":\"songT4\", \"token\":\"ffffffff-0000-1111-bea8-d9282a42ba48\"}");
            fail();
        } catch (ServerException e) {
            assertEquals(ServerErrorCode.TOKEN_INCORRECT, e.getServerErrorCode());
        }

        try {
            response = ss.getSongs("{\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
        } catch (ServerException e) {
            fail(e.getServerErrorText());
        }

        int oldSongSize = db.getSongs().size();
        int oldRatingSize = db.getRatings().size();
        try {
            response = ss
                    .deleteSong("{\"songId\":\"songT4\", \"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
        } catch (ServerException e) {
            fail(e.getServerErrorText());
        }
        assertEquals("{}", response);
        assertEquals(oldSongSize, db.getSongs().size());
        assertTrue(oldRatingSize > db.getRatings().size());
        try {
            response = ss
                    .deleteSong("{\"songId\":\"songT4\", \"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}");
            fail();
        } catch (ServerException e) {
            assertEquals(ServerErrorCode.SONG_NOT_FOUND, e.getServerErrorCode());
        }
        oldSongSize = db.getSongs().size();
        oldRatingSize = db.getRatings().size();
        try {
            response = ss
                    .deleteSong("{\"songId\":\"songT1\", \"token\":\"bc7844a3-94be-4606-b9eb-1e5876b75eef\"}");
        } catch (ServerException e) {
            fail(e.getServerErrorText());
        }
        assertEquals("{}", response);
        assertTrue(oldSongSize > db.getSongs().size());
        assertTrue(oldRatingSize > db.getRatings().size());
    }

}