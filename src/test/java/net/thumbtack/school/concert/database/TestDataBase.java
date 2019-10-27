package net.thumbtack.school.concert.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.gson.JsonSyntaxException;

import net.thumbtack.school.concert.model.Session;
import net.thumbtack.school.concert.model.Song;
import net.thumbtack.school.concert.model.User;

public class TestDataBase {
	
	@Test
	public void testOpenClose() {
		DataBase db = new DataBase();
		try {
			db.open("errfile.json");
		} catch (JsonSyntaxException | IOException e) {
			assertEquals(e.getClass(), FileNotFoundException.class);
		}
		try {
			db.open("dbfile.json");
		} catch (JsonSyntaxException | IOException e) {
			fail(e.getMessage());
		}
		
		DataBase db2 = new DataBase();
		
		assertNotNull(db2.getUsers());
		assertFalse(db2.getUsers().isEmpty());
		assertFalse(db2.getSessions().isEmpty());
		assertEquals(db.getUsers(), db2.getUsers());
		assertEquals(db.getSessions(), db2.getSessions());
		
		db2.close();
		
		assertTrue(db2.getUsers().isEmpty());
		assertTrue(db2.getSessions().isEmpty());
	}

	@Test
	public void testUser() {
		DataBase db = new DataBase();
		try {
			db.open("dbfile.json");
		} catch (JsonSyntaxException | IOException e) {
			fail(e.getMessage());
		}
		
		Set<User> users = db.getUsers();
		assertNotNull(users);
		assertEquals(users.size(), 3);
		User user = users.iterator().next();
		assertFalse(db.insertUser(user));
		assertEquals(db.getUsers().size(), 3);
		assertEquals(user, db.selectUser(user.getLogin()));
		assertTrue(db.insertUser(new User("fn", "ln", "testlogin", "testpwd")));
		assertEquals(db.getUsers().size(), 4);
		assertTrue(db.deleteUser("testlogin"));
		assertEquals(db.getUsers().size(), 3);
		assertTrue(db.insertUser(new User("fn", "ln", "testlogin", "testpwd")));
		assertTrue(db.deleteUser(new User("fn", "ln", "testlogin", "testpwd")));
		assertEquals(db.getUsers().size(), 3);
		db.close();
	}
	
	@Test
	public void testSession() {
		DataBase db = new DataBase();
		try {
			db.open("dbfile.json");
		} catch (JsonSyntaxException | IOException e) {
			fail(e.getMessage());
		}
		
		User user = db.getUsers().iterator().next();//new User("fn", "ln", "testlogin", "testpwd");
		Session session = new Session("de5584a0-95ba-2405-f9eb-2e5876b24eee");
		db.insertSession(user, session);
		assertEquals(db.getSessions().size(), 3);
		assertEquals(user, db.selectUser(session));
		assertTrue(db.deleteSession(session));
		assertNull(db.selectUser(session));
		
		db.close();
	}
	
	@Test
	public void testSong() {
		DataBase db = new DataBase();
		try {
			db.open("dbfile.json");
		} catch (JsonSyntaxException | IOException e) {
			fail(e.getMessage());
		}
		int dbSongSize = db.getSongs().size();
		
		
		List<Song> song = new ArrayList<>();
		song.add(new Song("songName1", Arrays.asList("composer"), Arrays.asList("author1", "author2"), "", 25, "User"));
		song.add(new Song("songName2", Arrays.asList("composer"), Arrays.asList("author"), "singer", 52, "User"));
		song.add(new Song("songName3", Arrays.asList("composer2"), Arrays.asList("author1"), "singer2", 5, "User2"));
		song.add(new Song("songName4", Arrays.asList("composer3"), Arrays.asList("author"), "singer", 150, "User2"));
		assertTrue(db.insertSongs(song));
		assertEquals(db.getSongs().size(), dbSongSize+song.size());
		assertNotNull(db.selectSong("songName1"));
		assertEquals(db.selectSong("songName1"), song.get(0));
		assertNull(db.selectSong("songName"));
		assertEquals(db.selectSong(new Song("songName1", Arrays.asList("composer"), Arrays.asList("author1", "author2"), "", 0, "")).get(0), song.get(0));
		assertEquals(db.selectSong(new Song("songName1", null, Arrays.asList("author1", "author2"), null, 0, "")).get(0), song.get(0));
		assertEquals(db.selectSong(new Song("", Arrays.asList("composer"), null, "", 0, "")).get(0), song.get(0));
		assertEquals(db.selectSong(new Song(null, Arrays.asList("composer"), Arrays.asList("author1", "author2"), null, 0, null)).get(0), song.get(0));
		assertTrue(db.selectSong(new Song("songName1", Arrays.asList("composer2"), Arrays.asList("author1", "author2"), "", 0, "")).isEmpty());
		assertEquals(db.selectSong(new Song("songName1", null, null, "", 0, "")).get(0), song.get(0));
		assertEquals(db.selectSong(new Song("songName2", null, null, "", 0, "")).get(0), song.get(1));
		assertTrue(db.selectSong(new Song("songName1", Arrays.asList("composer"), null, "singer", 0, "")).isEmpty());
		assertTrue(db.updateSong(new Song("songName1", Arrays.asList("composer"), Arrays.asList("author1", "author2"), "", 25, "newUser")));
		assertEquals(db.selectSong(new Song("", null, null, "", 0, "")).size(), db.getSongs().size());
		assertEquals(db.selectSong(new Song("", null, null, "singer", 0, "")).size(), 3);
		assertNotEquals(db.selectSong("songName1"), song.get(0));
		assertTrue(db.deleteSong(song.get(1)));
		assertEquals(db.getSongs().size(), dbSongSize+song.size()-1);

		db.close();
	}
	
	@Test
	public void testRating() {
		DataBase db = new DataBase();
		try {
			db.open("dbfile.json");
		} catch (JsonSyntaxException | IOException e) {
			fail(e.getMessage());
		}
		
		
		db.close();
	}
	
	@Test
	public void testComment() {
		DataBase db = new DataBase();
		try {
			db.open("dbfile.json");
		} catch (JsonSyntaxException | IOException e) {
			fail(e.getMessage());
		}
		
		
		db.close();
	}

}
