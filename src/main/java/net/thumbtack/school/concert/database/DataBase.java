package net.thumbtack.school.concert.database;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//import java.io.Serializable;

import net.thumbtack.school.concert.model.Comment;
import net.thumbtack.school.concert.model.Rating;
import net.thumbtack.school.concert.model.Session;
import net.thumbtack.school.concert.model.Song;
import net.thumbtack.school.concert.model.User;

public class DataBase /* implements Serializable */ {

	/*
	 * private static DataBase instance;
	 * 
	 * private DataBase() { }
	 * 
	 * public static DataBase getInstance() { if (instance == null) { instance = new
	 * DataBase(); } return instance; }
	 */

	// private static final long serialVersionUID = -2656800665876719635L;




	//Comment[] comment;
	//Rating[] rating;

	private static Set<User> users = new HashSet<>();
	private static Map<User, Session> sessions = new HashMap<>();
	private static Set<Song> songs = new HashSet<>();
	private static Map<User, Comment[]> comments = new HashMap<>();
	private static Map<User, Rating[]> ratings = new HashMap<>();

	public static Set<User> getUsers() {
		return users;
	}

	public static void setUsers(Set<User> users) {
		DataBase.users = users;
	}

	public static Map<User, Session> getSessions() {
		return sessions;
	}

	public static void setSessions(Map<User, Session> sessions) {
		DataBase.sessions = sessions;
	}

	public static Set<Song> getSongs() {
		return songs;
	}

	public static void setSongs(Set<Song> songs) {
		DataBase.songs = songs;
	}

	public static Map<User, Comment[]> getComments() {
		return comments;
	}

	public static void setComments(Map<User, Comment[]> comments) {
		DataBase.comments = comments;
	}

	public static Map<User, Rating[]> getRatings() {
		return ratings;
	}

	public static void setRatings(Map<User, Rating[]> ratings) {
		DataBase.ratings = ratings;
	}


	/**
	 * Insert new User in table
	 * 
	 * @param user
	 * @return
	 */
	public static boolean insertUser(User user) {
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
	public static boolean deleteUser(User user) {
		return users.remove(user);
	}

	/**
	 * Delete User with login = login from table
	 * 
	 * @param login
	 * @return
	 */
	public static boolean deleteUser(String login) {
		for (User user : users) {
			if (user.getLogin().equals(login)) {
				return users.remove(user);
			}
		}
		return false;
	}



	protected boolean newSession (User user, Session session) {
		return sessions.put(user, session)!=null;
	}

	public static void close(){
		setUsers(new HashSet<>());
		setSessions(new HashMap<>());
		setSongs(new HashSet<>());
		setComments(new HashMap<>());
		setRatings(new HashMap<>());
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
