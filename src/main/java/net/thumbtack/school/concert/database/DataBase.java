package net.thumbtack.school.concert.database;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import net.thumbtack.school.concert.model.*;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class DataBase {

    private static Map<String, User> users = new HashMap<>();
    private static BidiMap<String, Session> sessions = new DualHashBidiMap<>();
    private static Map<String, Song> songs = new HashMap<>();
    private static Set<Comment> comments = new LinkedHashSet<>();
    private static Set<Rating> ratings = new HashSet<>();

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> usersMap) {
        DataBase.users = usersMap;
    }

    public Map<String, Session> getSessions() {
        return sessions;
    }

    public void setSessions(BidiMap<String, Session> sessions) {
        DataBase.sessions = sessions;
    }

    public Map<String, Song> getSongs() {
        return songs;
    }

    public void setSongs(Map<String, Song> songsMap) {
        DataBase.songs = songsMap;
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
        if (!users.containsKey(user.getLogin())) {
            return users.put(user.getLogin(), user) == null;
        }
        return false;
    }

    /**
     * Find User with the session
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
            return users.get(login);
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
        return users.remove(user.getLogin(), user);
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
        return users.remove(login) != null;
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
     * @return true if new songs was added in DataBase
     */
    public boolean insertSongs(Map<String, Song> song) {
        if ((song == null) || (song.isEmpty())) {
            return false;
        }
        songs.putAll(song);
        return true;
    }

    /**
     * Insert new song in DataBase
     *
     * @param song - new songs to add in DB
     * @return true if new song was added in DataBase, false if song exist
     */
    public boolean insertSong(Song song, String id) {
        if ((song == null) || (id == null) || (id.isEmpty())) {
            return false;
        }
        return songs.put(id, song) == null;
    }

    /**
     * Find song with id
     *
     * @param id for finding song
     * @return Song
     */
    public Song selectSong(String id) {
        if ((id == null) || (id.isEmpty())) {
            return null;
        }
        return songs.get(id);
    }

    /**
     * Find a list of songs with any parameters: the Song Name, Composer, Author,
     * Singer, User
     *
     * @param song - parameters for searching songs
     * @return Map Songs
     */
    public Map<String, Song> selectSong(Song song) {
        return songs.entrySet().stream()
                .filter(u -> (song.getSongName() == null) || song.getSongName().isEmpty()
                        || song.getSongName().equals(u.getValue().getSongName()))
                .filter(u -> (song.getComposer() == null) || song.getComposer().equals(u.getValue().getComposer()))
                .filter(u -> (song.getAuthor() == null) || song.getAuthor().equals(u.getValue().getAuthor()))
                .filter(u -> (song.getSinger() == null) || song.getSinger().isEmpty()
                        || song.getSinger().equals(u.getValue().getSinger()))
                .filter(u -> (song.getUserLogin() == null) || song.getUserLogin().isEmpty()
                        || song.getUserLogin().equals(u.getValue().getUserLogin()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Update info for song with old name equal new name.
     *
     * @param song
     * @return true if update is successful
     */
    public boolean updateSong(Song song, String songId) {
        return songs.replace(songId, song) != null;
    }

    /**
     * Update info for songs list with Id
     *
     * @param song - Map String Songs Id, Song songs
     */
    public void updateSong(Map<String, Song> song) {
        song.forEach((k, v) -> songs.replace(k, v));
    }

    /**
     * Delete song from DataBase
     *
     * @param id song
     * @return
     */
    public boolean deleteSong(String id) {
        return songs.remove(id) != null;
    }

    /**
     * Delete songs from DataBase
     *
     * @param idList
     * @return
     */
    public boolean deleteSong(List<String> idList) {
        for (String id : idList) {
            if (songs.remove(id) == null) return false;
        }
        return true;
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
     * @param songId
     * @param user
     * @return
     */
    public List<Rating> selectRating(String songId, String user) {
        return ratings.stream()
                .filter(sn -> (songId == null) || songId.isEmpty() || songId.equals(sn.getSongId()))
                .filter(u -> (user == null) || user.isEmpty() || user.equals(u.getUser())).collect(Collectors.toList());
    }

    /**
     * Get rating list for list song name
     *
     * @param songIdList
     * @return rating list
     */
    public List<Rating> selectRating(List<String> songIdList) {
        return ratings.stream().filter(sn -> songIdList.stream().anyMatch(sn.getSongId()::equals))
                .collect(Collectors.toList());
    }

    /**
     * Get sorted songs rating list with defined songName and/or user
     *
     * @param songId
     * @param user
     * @param desc   - true for descending sorting, false for Ascending sorting
     * @return rating list
     */
    public List<Rating> selectRating(String songId, String user, boolean desc) {
        List<Rating> rating = new ArrayList<>(selectRating(songId, user));
        if (desc) {
            rating.sort(Comparator.comparing(Rating::getRating).reversed());
        } else {
            rating.sort(Comparator.comparing(Rating::getRating));
        }
        return rating;
    }

    /**
     * Update song rating
     *
     * @param rating
     * @return true if contain rating was updated
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
     * @param songId
     * @param user
     * @return true if rating with song name and user was deleted
     */
    public boolean deleteRating(String songId, String user) {
        List<Rating> rating = selectRating(songId, user);
        if (!rating.isEmpty()) {
            return ratings.removeAll(rating);
        }
        return false;
    }

    /**
     * Delete Rating for this songName and user from rating
     *
     * @param rating
     * @return true if rating with this song name and user was deleted
     */
    public boolean deleteRating(Rating rating) {
        return deleteRating(rating.getSongId(), rating.getUser());
    }

    /**
     * Add new comment in "Comment table"
     *
     * @param comment
     * @return true if this comment did not already contain in the DB
     */
    public boolean insertComment(Comment comment) {
        return comments.add(comment);
    }

    /**
     * Add new comments in "Comment table"
     *
     * @param comment
     * @return true if this list changed
     */
    public boolean insertComment(List<Comment> comment) {
        return comments.addAll(comment);
    }

    /**
     * Find and return all comments for song "songName" with author "author"
     *
     * @param songId
     * @param author
     * @return
     */
    public List<Comment> selectComment(String songId, String author) {
        return comments.stream()
                .filter(sn -> (songId == null) || songId.isEmpty() || songId.equals(sn.getSongId()))
                .filter(a -> (author == null) || author.isEmpty() || author.equals(a.getAuthor()))
                .collect(Collectors.toList());
    }

    /**
     * Get all comments for songName
     *
     * @param songId
     * @return
     */
    public List<Comment> selectComment(String songId) {
        return selectComment(songId, null);
    }

    /**
     *
     */
    public List<Comment> selectComment(List<String> songId) {
        return comments.stream().filter(c -> songId.contains(c.getSongId())).collect(Collectors.toList());
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
     * Update comments for this song and user
     *
     * @param comment
     * @return
     */
    public boolean updateComment(List<Comment> comment) {
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
        return deleteComment(comment.getSongId(), comment.getAuthor());
    }

    /**
     * Delete comments for this Song and User from comment list
     *
     * @param comment
     * @return
     */
    public boolean deleteComment(List<Comment> comment) {
        return comments.removeIf(comment::contains);
    }

    /**
     * Delete comments for this Song and User string
     *
     * @param songId
     * @param author
     * @return
     */
    public boolean deleteComment(String songId, String author) {
        return comments.removeAll(selectComment(songId, author));
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
            setUsers(new Gson().fromJson(reader.readLine(), new TypeToken<DualHashBidiMap<String, User>>() {
            }.getType()));
            setSessions(new Gson().fromJson(reader.readLine(), new TypeToken<DualHashBidiMap<String, Session>>() {
            }.getType()));
            setSongs(new Gson().fromJson(reader.readLine(), new TypeToken<DualHashBidiMap<String, Song>>() {
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
        setUsers(new HashMap<String, User>());
        setSessions(new DualHashBidiMap<>());
        setSongs(new HashMap<String, Song>());
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
