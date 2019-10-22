package net.thumbtack.school.concert.service;

import static org.junit.Assert.fail;

import java.util.*;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.dto.request.AddSongDtoRequest;
import net.thumbtack.school.concert.dto.request.AddSongDtoRequest.Song;
import net.thumbtack.school.concert.dto.request.GetSongsDtoRequest;
import net.thumbtack.school.concert.exception.ServerException;
import net.thumbtack.school.concert.model.Session;
import net.thumbtack.school.concert.model.User;

public class TestSongService {

    @Test
    public void testAddSongs() {

        DataBase db = new DataBase();
    	
        Set<User> su = new HashSet<>();
        su.add(new User("fname", "lname", "login", "pass"));
        db.setUsers(su);
        BidiMap<String, Session> sessions = new DualHashBidiMap<>();
        sessions.put("login", new Session("fea8056a-cd2e-4d9b-8d73-c165cd135299"));
        db.setSessions(sessions);

        AddSongDtoRequest sr = new AddSongDtoRequest();
        sr.setToken("fea8056a-cd2e-4d9b-8d73-c165cd135299");
        List<Song> songs = new ArrayList<>();
        songs.add(new AddSongDtoRequest().new Song("songName", Arrays.asList("composer"), Arrays.asList("author"), "", 25));
        sr.setSong(songs);

        SongService ss = new SongService();
        try {
            ss.addSong(new Gson().toJson(sr));
            //ss.addSong("\"song\":[{\"songName\":\"testSongName\",\"composer\":[\"testComposer\",\"testComposer\"],\"author\":[\"testAuthor\"],\"singer\":\"test Singer Д\",\"length\":\"25\"],\"token\":\"fea8056a-cd2e-4d9b-8d73-c165cd135299\"}");
        } catch (ServerException e) {
            fail(e.getServerErrorText());
        }
        GetSongsDtoRequest getSongs = new GetSongsDtoRequest();
        //getSongs.setAuthor();
        getSongs.setToken("fea8056a-cd2e-4d9b-8d73-c165cd135299");
        getSongs.setSinger("test Singer Д");
        List<String> alstr2 = new ArrayList<String>();
        alstr2.add("testCom-poser Ф.");
        alstr2.add("test Composer2");
        getSongs.setComposer(alstr2);
        List<String> alstr = new ArrayList<String>();
        alstr.add("test Author");
        getSongs.setAuthor(alstr);
        new Gson().toJson(getSongs);
        try {
            ss.getSongs("{\"composer\":[\"testCom-poser Ф.\",\"test Composer2\"],\"author\":[\"test Author\"],\"singer\":\"test Singer Д\",\"token\":\"fea8056a-cd2e-4d9b-8d73-c165cd135299\"}");
            ss.getSongs(new Gson().toJson(getSongs));
        } catch (ServerException e) {
            fail(e.getServerErrorText());
        }
    }

}
