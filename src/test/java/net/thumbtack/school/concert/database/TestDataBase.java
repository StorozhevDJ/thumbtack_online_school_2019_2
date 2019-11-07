package net.thumbtack.school.concert.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.gson.JsonSyntaxException;

import net.thumbtack.school.concert.model.Comment;
import net.thumbtack.school.concert.model.Rating;
import net.thumbtack.school.concert.model.Session;
import net.thumbtack.school.concert.model.Song;
import net.thumbtack.school.concert.model.User;

public class TestDataBase {

	@Test
	public void testOpenClose() {
		DataBase db = new DataBase();
		try {
			db.open("errfile.json");
			fail();
		} catch (JsonSyntaxException | IOException e) {
			assertEquals(e.getClass(), FileNotFoundException.class);
		}
		db.open();
		assertNotNull(db);
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

		db.open();
		assertNull(db.selectUser(""));

		try {
			db.open("dbfile.json");
		} catch (JsonSyntaxException | IOException e) {
			fail(e.getMessage());
		}

		Set<User> users = db.getUsers();
		assertNotNull(users);
		assertEquals(users.size(), 4);
		User user = users.iterator().next();
		assertFalse(db.insertUser(user));
		assertEquals(db.getUsers().size(), 4);
		assertEquals(user, db.selectUser(user.getLogin()));
		assertNull(db.selectUser(""));
		assertTrue(db.insertUser(new User("fn", "ln", "testlogin", "testpwd")));
		assertEquals(db.getUsers().size(), 5);
		assertTrue(db.deleteUser("testlogin"));
		assertFalse(db.deleteUser("errUser"));
		assertFalse(db.deleteUser(""));
		assertFalse(db.deleteUser((String) null));
		assertEquals(db.getUsers().size(), 4);
		assertTrue(db.insertUser(new User("fn", "ln", "testlogin", "testpwd")));
		assertTrue(db.deleteUser(new User("fn", "ln", "testlogin", "testpwd")));
		assertEquals(db.getUsers().size(), 4);
		db.close();
	}

	@Test
	public void testSession() {
		DataBase db = new DataBase();
		Session session = new Session("de5584a0-95ba-2405-f9eb-2e5876b24eee");

		db.open();
		assertNull(db.selectUser(session));
		assertNull(db.selectUser((Session) null));

		try {
			db.open("dbfile.json");
		} catch (JsonSyntaxException | IOException e) {
			fail(e.getMessage());
		}
		User user = db.getUsers().iterator().next();// new User("fn", "ln", "testlogin", "testpwd");

		assertFalse(db.insertSession(user, session));
		assertFalse(db.insertSession(null, session));
		assertFalse(db.insertSession(user, null));
		assertFalse(db.insertSession(null, null));
		assertEquals(db.getSessions().size(), 3);
		assertEquals(user, db.selectUser(session));
		assertTrue(db.deleteSession(session));
		assertNull(db.selectUser(session));
		assertNull(db.selectUser((Session) null));
		assertTrue(db.insertSession(user, session));
		assertNotNull(db.selectUser(session));

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
		assertEquals(db.getSongs().size(), dbSongSize + song.size());
		assertFalse(db.insertSongs(song));
		assertFalse(db.insertSongs(null));
		assertFalse(db.insertSongs(new ArrayList<Song>()));
		assertFalse(db.insertSong(null));
		assertFalse(db.insertSong(new Song("songName1", Arrays.asList("c"), Arrays.asList("a1"), "", 25, "")));

		assertNotNull(db.selectSong("songName1"));
		assertEquals(db.selectSong("songName1"), song.get(0));
		assertNull(db.selectSong("songName"));
		assertNull(db.selectSong(""));
		assertNull(db.selectSong((String) null));
		assertEquals(db.selectSong(
				new Song("songName1", Arrays.asList("composer"), Arrays.asList("author1", "author2"), "", 0, ""))
				.get(0), song.get(0));
		assertEquals(
				db.selectSong(new Song("songName1", null, Arrays.asList("author1", "author2"), null, 0, "User")).get(0),
				song.get(0));
		assertEquals(db.selectSong(new Song("", Arrays.asList("composer"), null, "", 0, "")).get(0), song.get(0));
		assertEquals(db
				.selectSong(
						new Song(null, Arrays.asList("composer"), Arrays.asList("author1", "author2"), null, 0, null))
				.get(0), song.get(0));
		assertTrue(db.selectSong(
				new Song("songName1", Arrays.asList("composer2"), Arrays.asList("author1", "author2"), "", 0, ""))
				.isEmpty());
		assertEquals(db.selectSong(new Song("songName1", null, null, "", 0, "")).get(0), song.get(0));
		assertEquals(db.selectSong(new Song("songName2", null, null, "", 0, "")).get(0), song.get(1));
		assertTrue(db.selectSong(new Song("songName1", Arrays.asList("composer"), null, "singer", 0, "")).isEmpty());
		assertTrue(db.selectSong(new Song("songName1", Arrays.asList("composer"), null, "", 0, "User2")).isEmpty());
		assertTrue(db.updateSong(new Song("songName1", Arrays.asList("composer"), Arrays.asList("author1", "author2"),
				"", 25, "newUser")));

		assertFalse(db.updateSong(new Song("NameErr", Arrays.asList("com"), Arrays.asList("author", ""), "", 25, "")));
		assertEquals(db.selectSong(new Song("", null, null, "", 0, "")).size(), db.getSongs().size());
		assertEquals(db.selectSong(new Song("", null, null, "singer", 0, "")).size(), 3);
		assertNotEquals(db.selectSong("songName1"), song.get(0));
		assertTrue(db.deleteSong(song.get(1)));
		assertEquals(db.getSongs().size(), dbSongSize + song.size() - 1);

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

		List<Rating> ratings = new ArrayList<>();
		ratings.add(new Rating("SongName1Test", 1, "UserTest"));
		ratings.add(new Rating("SongName2Test", 2, "UserTest"));

		int ratSize = db.getRatings().size();
		assertTrue(db.insertRating(ratings.get(0)));
		assertEquals(db.getRatings().size(), ratSize + 1);
		assertTrue(db.deleteRating(ratings.get(0)));
		assertEquals(db.getRatings().size(), ratSize);
		assertTrue(db.insertRating(ratings));
		assertEquals(db.getRatings().size(), ratSize + ratings.size());

		assertEquals(db.selectRating(null, null, true).size(), db.getRatings().size());
		assertEquals(db.selectRating("", "", false).size(), ratSize + ratings.size());
		assertEquals(db.selectRating(null, "UserTest").size(), ratings.size());
		assertEquals(db.selectRating("SongName1Test", null).size(), 1);

		assertTrue(db.updateRating(new Rating("SongName2Test", 3, "UserTest")));
		assertFalse(db.updateRating(new Rating("SongNameTest", 3, "UserTest")));

		assertTrue(db.deleteRating(ratings.get(0).getSongName(), ratings.get(0).getUser()));
		assertFalse(db.deleteRating(ratings.get(0).getSongName(), ratings.get(0).getUser()));
		assertFalse(db.deleteRating("SongNameErr", "UserTest"));

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

		List<Comment> comments = new ArrayList<>();
		comments.add(new Comment("SongName1Test", "UserTest", "Test comment1"));
		comments.add(new Comment("SongName2Test", "UserTest", "Test comment1"));

		int comSize = db.getComments().size();

		assertTrue(db.insertComment(comments.get(0)));
		assertEquals(db.getComments().size(), comSize + 1);
		assertTrue(db.deleteComment(comments.get(0)));
		assertEquals(db.getComments().size(), comSize);

		assertEquals(db.selectComment("").size(), comSize);
		assertEquals(db.selectComment(null).size(), comSize);
		assertEquals(db.selectComment("songName11").size(), 2);

		assertEquals(db.selectComment("", "").size(), comSize);
		assertEquals(db.selectComment(null, null).size(), comSize);
		assertEquals(db.selectComment("songName11", null).size(), 2);
		assertEquals(db.selectComment(null, "User10").size(), 2);

		assertTrue(db.updateComment(new Comment(db.selectComment("").get(0).getSongName(),
				db.selectComment("").get(0).getAuthor(), "New comment")));
		assertFalse(db.updateComment(new Comment("songNameErr", null, null)));

		db.close();
	}

}
