package net.thumbtack.school.concert.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import net.thumbtack.school.concert.model.Comment;
import net.thumbtack.school.concert.model.Rating;
import net.thumbtack.school.concert.model.Session;
import net.thumbtack.school.concert.model.Song;
import net.thumbtack.school.concert.model.User;

public class DataBase {

	private static Set<User> users = new HashSet<>();
	private static BidiMap<String, Session> sessions = new DualHashBidiMap<>();
	private static Set<Song> songs = new HashSet<>();
	private static Set<Comment> comments = new HashSet<>();
	private static Set<Rating> ratings = new HashSet<>();

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		DataBase.users = users;
	}

	public Map<String, Session> getSessions() {
		return sessions;
	}

	public void setSessions(BidiMap<String, Session> sessions) {
		DataBase.sessions = sessions;
	}

	public Set<Song> getSongs() {
		return songs;
	}

	public void setSongs(Set<Song> songs) {
		DataBase.songs = songs;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		DataBase.comments = comments;
	}

	public Set<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<Rating> ratings) {
		DataBase.ratings = ratings;
	}

	/**
	 * Insert new User in table
	 * 
	 * @param user
	 * @return
	 */
	public boolean insertUser(User user) {
		return users.add(user);
	}

	/**
	 * Find User with the session
	 *
	 * @param session for find User
	 * @return
	 */
	public User selectUser(Session session) {
		if ((session != null) && (sessions != null)) {
			return selectUser(sessions.getKey(session));
		}
		return null;
	}

	/**
	 * Find User with the login
	 *
	 * @param login for find User
	 * @return
	 */
	public User selectUser(String login) {
		if ((users != null) && (login != null) && (!login.isEmpty())) {
			for (User user : users) {
				if (user.getLogin().equals(login)) {
					return user;
				}
			}
		}
		return null;
	}

	/**
	 * Delete User from table
	 * 
	 * @param user
	 * @return
	 */
	public boolean deleteUser(User user) {
		return users.remove(user);
	}

	/**
	 * Delete User with login = login from table
	 * 
	 * @param login
	 * @return
	 */
	public boolean deleteUser(String login) {
		if ((login == null) || (login.isEmpty())) {
			return false;
		}
		for (User user : users) {
			if (user.getLogin().equals(login)) {
				return users.remove(user);
			}
		}
		return false;
	}

	/**
	 * Add new session for the logged user into table
	 * 
	 * @param user
	 * @param session
	 * @return
	 */
	public boolean insertSession(User user, Session session) {
		return sessions.put(user.getLogin(), session) != null;
	}

	/**
	 * Delete session from table for logged out user
	 * 
	 * @param session
	 * @return
	 */
	public boolean deleteSession(Session session) {
		return sessions.entrySet().removeIf(entries -> entries.getValue() == session);
	}

	/**
	 * Insert new songs in DataBase
	 *
	 * @param song - List of new songs to add in DB
	 * @return true if new songs was added in DataBase
	 */
	public boolean insertSongs(List<Song> song) {
		if ((song == null) || (song.isEmpty())) {
			return false;
		}
		return songs.addAll(song);
	}

	/**
	 * Insert new song in DataBase
	 *
	 * @param song - new songs to add in DB
	 * @return true if new song was added in DataBase
	 */
	public boolean insertSong(Song song) {
		if (song == null) {
			return false;
		}
		return songs.add(song);
	}

	/**
	 * Find song with name equals songName
	 *
	 * @param songName
	 * @return Song
	 */
	public Song selectSong(String songName) {
		if ((songName == null) || (songName.isEmpty())) {
			return null;
		}
		for (Song song : songs) {
			if (song.getSongName().equals(songName)) {
				return song;
			}
		}
		return null;
	}

	/**
	 * Update info for song with old name equal new name
	 *
	 * @param song
	 * @return true if update is successful
	 */
	public boolean updateSong(Song song) {
		if (deleteSong(selectSong(song.getSongName()))) {
			return insertSong(song);
		}
		return false;
	}

	/**
	 * Delete song from DataBase
	 *
	 * @param song
	 * @return
	 */
	public boolean deleteSong(Song song) {
		return songs.remove(song);
	}

	public boolean insertRating(Rating rating) {
		return ratings.add(rating);
	}

	public boolean insertRating(Set<Rating> rating) {
		return ratings.addAll(rating);
	}

	/**
	 * Read and open JSON file with DataBase data
	 *
	 * @param fileName filename with data in JSON format
	 * @throws JsonSyntaxException
	 * @throws IOException
	 */
	public void open(String fileName) throws JsonSyntaxException, IOException {
		File dbFile = new File(fileName);
		try (BufferedReader reader = new BufferedReader(new FileReader(dbFile))) {
			setUsers(new Gson().fromJson(reader.readLine(), new TypeToken<Set<User>>() {
			}.getType()));
			setSessions(new Gson().fromJson(reader.readLine(), new TypeToken<DualHashBidiMap<String, Session>>() {
			}.getType()));
			setSongs(new Gson().fromJson(reader.readLine(), new TypeToken<Set<Song>>() {
			}.getType()));
			setComments(new Gson().fromJson(reader.readLine(), new TypeToken<Set<Comment>>() {
			}.getType()));
			setRatings(new Gson().fromJson(reader.readLine(), new TypeToken<Set<Rating>>() {
			}.getType()));
		}
	}

	/**
	 * Initialize DataBase with default data
	 */
	public void open() {
		setUsers(new HashSet<>());
		setSessions(new DualHashBidiMap<>());
		setSongs(new HashSet<>());
		setComments(new HashSet<>());
		setRatings(new HashSet<>());
	}

	/**
	 * Save DataBease data in file and close
	 *
	 * @param fileName to save data
	 * @throws FileNotFoundException
	 */
	public void close(String fileName) throws FileNotFoundException {
		try (PrintWriter pw = new PrintWriter(new File(fileName))) {
			pw.println(new Gson().toJson(users));
			pw.println(new Gson().toJson(sessions));
			pw.println(new Gson().toJson(songs));
			pw.println(new Gson().toJson(comments));
			pw.println(new Gson().toJson(ratings));
		}
		close();
	}

	/**
	 * Close DataBase - Clear all data
	 */
	public void close() {
		users.clear();
		sessions.clear();
		songs.clear();
		comments.clear();
		ratings.clear();
	}

	/*
	 * Аналогом таблицы в java является Set<TableRow> где TableRow класс строки
	 * таблицы. Или, лучше, Map<ID_записи, TableRow>
	 * 
	 * Аналогом индекса является HashMap<тут_тип_данных_индекса, TableRow> на каждый
	 * индекс- свой HashMap. Можно делать HashMap<индекс1,HashMap<индекс2,
	 * TableRow>> - это индекс по двум полям.
	 * 
	 * Соответственно insert в "таблицу" делает вставку в Set и все Map'ы. Поиск-
	 * поиск по нужной мапе.
	 */

	/*
	 * Find in ArrayList boolean isModified = CollectionUtils.filter(linkedList1,
	 *       new Predicate<Customer>() {         public boolean evaluate(Customer
	 * customer) {             return
	 * Arrays.asList("Daniel","Kyle").contains(customer.getName());         }
	 *     });            assertTrue(linkedList1.size() == 2);
	 */

	/*
	 * DataBase structure:
	 *
	 * Set <Users>: String firstName; String lastName; String login UN NN; String
	 * password;
	 *
	 * Sessions: String token; String userLogin UN NN
	 *
	 * Comment: String userLogin; String songName; String comment
	 *
	 * Rating: String songName; String userLogin; String rating
	 *
	 * Set <Song>: String userLogin; Song song
	 * 
	 * 
	 * Map
	 */
}
