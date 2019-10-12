package net.thumbtack.school.concert.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

//import java.io.Serializable;

import net.thumbtack.school.concert.model.Comment;
import net.thumbtack.school.concert.model.Rating;
import net.thumbtack.school.concert.model.Session;
import net.thumbtack.school.concert.model.Song;
import net.thumbtack.school.concert.model.User;

public class DataBase {

	private static Set<User> users = new HashSet<>();
	private static Map<String, Session> sessions = new HashMap<>();
	private static Set<Song> songs = new HashSet<>();
	private static Map<String, Comment[]> comments = new HashMap<>();
	private static Map<String, Rating[]> ratings = new HashMap<>();

	public Set<User> getUsers() {
		return users;
	}

	public static void setUsers(Set<User> users) {
		DataBase.users = users;
	}

	public Map<String, Session> getSessions() {
		return sessions;
	}

	public static void setSessions(Map<String, Session> sessions) {
		DataBase.sessions = sessions;
	}

	public Set<Song> getSongs() {
		return songs;
	}

	public static void setSongs(Set<Song> songs) {
		DataBase.songs = songs;
	}

	public Map<String, Comment[]> getComments() {
		return comments;
	}

	public static void setComments(Map<String, Comment[]> comments) {
		DataBase.comments = comments;
	}

	public Map<String, Rating[]> getRatings() {
		return ratings;
	}

	public static void setRatings(Map<String, Rating[]> ratings) {
		DataBase.ratings = ratings;
	}

	/**
	 * Insert new User in table
	 * 
	 * @param user
	 * @return
	 */
	protected static boolean insertUser(User user) {
		return users.add(user);
	}

	/**
	 * Find User with the login
	 * 
	 * @param login
	 * @return
	 */
	protected static User getUser(String login) {
		if (users != null) {
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
	protected static boolean deleteUser(User user) {
		return users.remove(user);
	}

	/**
	 * Delete User with login = login from table
	 * 
	 * @param login
	 * @return
	 */
	protected static boolean deleteUser(String login) {
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
	protected boolean newSession(User user, Session session) {
		return sessions.put(user.getLogin(), session) != null;
	}

	/**
	 * Delete session from table for logged out user
	 *
	 * @param session
	 * @return
	 */
	protected boolean removeSession(Session session) {
		return sessions.entrySet().removeIf(entries -> entries.getValue() == session);
	}

	public static void open(String fileName) throws JsonSyntaxException, IOException {
		File dbFile = new File(fileName);
		try (BufferedReader reader = new BufferedReader(new FileReader(dbFile))) {
			setUsers(new Gson().fromJson(reader.readLine(), new TypeToken<Set<User>>() {
			}.getType()));
			setSessions(new Gson().fromJson(reader.readLine(), new TypeToken<Map<String, Session>>() {
			}.getType()));
			setSongs(new Gson().fromJson(reader.readLine(), new TypeToken<Set<Song>>() {
			}.getType()));
			setComments(new Gson().fromJson(reader.readLine(), new TypeToken<Map<String, Comment[]>>() {
			}.getType()));
			setRatings(new Gson().fromJson(reader.readLine(), new TypeToken<Map<String, Rating>>() {
			}.getType()));
		}
	}

	public static void open() {
		setUsers(new HashSet<>());
		setSessions(new HashMap<>());
		setSongs(new HashSet<>());
		setComments(new HashMap<>());
		setRatings(new HashMap<>());
	}

	public static void close(String fileName) throws FileNotFoundException {
		try (PrintWriter pw = new PrintWriter(new File(fileName))) {
			pw.println(new Gson().toJson(users));
			pw.println(new Gson().toJson(sessions));
			pw.println(new Gson().toJson(songs));
			pw.println(new Gson().toJson(comments));
			pw.println(new Gson().toJson(ratings));
		}
		close();
	}

	public static void close() {
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
	 * Users String firstName; String lastName; String login; UN NN String password;
	 * 
	 * Sessions String token;
	 * 
	 * Songs
	 * 
	 */
}
