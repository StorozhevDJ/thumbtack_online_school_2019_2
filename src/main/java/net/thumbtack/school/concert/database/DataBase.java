package net.thumbtack.school.concert.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
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
	private static Set<Comment> comments = new LinkedHashSet<>();
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
	 * @return true if user was added, false if already exist
	 */
	public boolean insertUser(User user) {
		if (selectUser(user.getLogin()) != null)
			return false;
		return users.add(user);
	}

	/**
	 * Find User with the session Equivalent "SELECT * FROM user, session WHERE
	 * session.login=session.login AND user.login=session.login"
	 *
	 * @param session for find User
	 * @return
	 */
	public User selectUser(Session session) {
		if ((session != null) && (!sessions.isEmpty())) {
			return selectUser(sessions.getKey(session));
		}
		return null;
	}

	/**
	 * Find User with the login Equivalent "SELECT * FROM user WHERE login=login"
	 *
	 * @param login for find User
	 * @return
	 */
	public User selectUser(String login) {
		if ((!users.isEmpty()) && (login != null) && (!login.isEmpty())) {
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
	 * Delete User with login = login from table Equivalent "DELETE FROM user WHERE
	 * login=login"
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
				return deleteUser(user);
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
		if ((user != null) && (session != null)) {
			return sessions.put(user.getLogin(), session) == null;
		}
		return false;
	}

	/**
	 * Delete session from table for logged out user
	 * 
	 * @param session
	 * @return
	 */
	public boolean deleteSession(Session session) {
		return sessions.entrySet().removeIf(entries -> entries.getValue().equals(session));
	}

	/**
	 * Insert new songs in DataBase
	 *
	 * @param song - List of new songs to add in DB
	 * @return true if new songs was added in DataBase, false if song exist
	 */
	public boolean insertSongs(List<Song> song) {
		if ((song == null) || (song.isEmpty())) {
			return false;
		}
		for (Song s : song) {
			if (selectSong(s.getSongName()) != null)
				return false;
		}
		return songs.addAll(song);
	}

	/**
	 * Insert new song in DataBase
	 *
	 * @param song - new songs to add in DB
	 * @return true if new song was added in DataBase, false if song exist
	 */
	public boolean insertSong(Song song) {
		if (song == null) {
			return false;
		}
		if (selectSong(song.getSongName()) != null)
			return false;
		return songs.add(song);
	}

	/**
	 * Find song with name equals songName Equivalent "SELECT * FROM songs WHERE
	 * somgName=songName"
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
	 * Find a list of songs with any parameters: the Song Name, Composer, Author,
	 * Singer, User
	 * 
	 * @param song - parameters for searching songs
	 * @return Song List
	 */
	public List<Song> selectSong(Song song) {
		List<Song> songList = new ArrayList<>();
		for (Song s : songs) {
			if ((song.getSongName() != null) && (!song.getSongName().isEmpty())) {
				if (!s.getSongName().equals(song.getSongName())) {
					continue;
				}
			}
			if (song.getComposer() != null) {
				if (!s.getComposer().equals(song.getComposer())) {
					continue;
				}
			}
			if (song.getAuthor() != null) {
				if (!s.getAuthor().equals(song.getAuthor())) {
					continue;
				}
			}
			if ((song.getSinger() != null) && (!song.getSinger().isEmpty())) {
				if (!s.getSinger().equals(song.getSinger())) {
					continue;
				}
			}
			if ((song.getUserLogin() != null) && (!song.getUserLogin().isEmpty())) {
				if (!s.getUserLogin().equals(song.getUserLogin())) {
					continue;
				}
			}
			songList.add(s);
		}
		return songList;
	}

	/**
	 * Update info for song with old name equal new name Equivalent "UPDATE song SET
	 * * composer=composer, author=author, singer=singer, length=length,
	 * userLigin=userLogin WHERE songName=songName"
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

	/**
	 * Insert rating into DataBase
	 * 
	 * @param rating
	 * @return
	 */
	public boolean insertRating(Rating rating) {
		return ratings.add(rating);
	}

	/**
	 * Insert ratings into DataBase
	 * 
	 * @param rating
	 * @return
	 */
	public boolean insertRating(List<Rating> rating) {
		return ratings.addAll(rating);
	}

	/**
	 * Get songs rating with defined songName and/or user
	 * 
	 * @param songName
	 * @param user
	 * @return
	 */
	public List<Rating> selectRating(String songName, String user) {
		List<Rating> rating = new ArrayList<>();
		for (Rating r : ratings) {
			if ((songName != null) && (!songName.isEmpty())) {
				if (!r.getSongName().equals(songName))
					continue;
			}
			if ((user != null) && (!user.isEmpty())) {
				if (!r.getUser().equals(user))
					continue;
			}
			rating.add(r);
		}
		return rating;
	}

	/**
	 * Get sorted songs rating list with defined songName and/or user
	 * 
	 * @param songName
	 * @param user
	 * @param desc     - true for descending sorting, false for Ascending sorting
	 * @return
	 */
	public List<Rating> selectRating(String songName, String user, boolean desc) {
		List<Rating> rating = new ArrayList<>(selectRating(songName, user));
		if (desc) {
			Collections.sort(rating, Comparator.comparing(Rating::getRating).reversed());
		} else {
			Collections.sort(rating, Comparator.comparing(Rating::getRating));
		}
		return rating;
	}

	/**
	 * Update song rating
	 * 
	 * @param rating
	 * @return
	 */
	public boolean updateRating(Rating rating) {
		if (deleteRating(rating)) {
			return insertRating(rating);
		}
		return false;
	}

	/**
	 * Delete Rating for this songName and user
	 * 
	 * @param songName
	 * @param user
	 * @return
	 */
	public boolean deleteRating(String songName, String user) {
		List<Rating> rating = selectRating(songName, user);
		if (!rating.isEmpty()) {
			return ratings.removeAll(rating);
		}
		return false;
	}

	/**
	 * Delete Rating for this songName and user from rating
	 * 
	 * @param rating
	 * @return
	 */
	public boolean deleteRating(Rating rating) {
		return deleteRating(rating.getSongName(), rating.getUser());
	}

	/**
	 * Add new comment in "Comment table"
	 * 
	 * @param comment
	 * @return
	 */
	public boolean insertComment(Comment comment) {
		return comments.add(comment);
	}

	/**
	 * Find and return all comments for song "songName" with author "author"
	 * 
	 * @param songName
	 * @param author
	 * @return
	 */
	public List<Comment> selectComment(String songName, String author) {
		List<Comment> comment = new ArrayList<>();
		for (Comment c : comments) {
			if ((songName != null) && (!songName.isEmpty())) {
				if (!c.getSongName().equals(songName))
					continue;
			}
			if ((author != null) && (!author.isEmpty())) {
				if (!c.getAuthor().equals(author))
					continue;
			}
			comment.add(c);
		}
		return comment;
	}

	/**
	 * Get all comments for songName
	 * 
	 * @param songName
	 * @return
	 */
	public List<Comment> selectComment(String songName) {
		return selectComment(songName, null);
	}

	/**
	 * Update comment for this song and user
	 * 
	 * @param comment
	 * @return
	 */
	public boolean updateComment(Comment comment) {
		if (deleteComment(comment)) {
			return insertComment(comment);
		}
		return false;
	}

	/**
	 * Delete comment for this Song and User from comment
	 * 
	 * @param comment
	 * @return
	 */
	public boolean deleteComment(Comment comment) {
		return deleteComment(comment.getSongName(), comment.getAuthor());
	}

	public boolean deleteComment(String songName, String author) {
		return comments.removeAll(selectComment(songName, author));
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
}
