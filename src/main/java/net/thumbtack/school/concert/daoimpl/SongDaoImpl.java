package net.thumbtack.school.concert.daoimpl;

import java.util.List;
import java.util.Map;

import net.thumbtack.school.concert.dao.SongDao;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.model.Song;

/**
 * Class for save Song date
 *
 * @author Denis
 */
public class SongDaoImpl implements SongDao {

    public void add(Map<String, Song> songModel) {
        DataBase db = new DataBase();
        db.insertSongs(songModel); // Add new songs into the DataBase
    }

    public Map<String, Song> get(List<String> composer, List<String> author, String singer) {
        return new DataBase().selectSong(new Song(null, composer, author, singer, 0, null));
    }

    public Map<String, Song> get(String user) {
        return new DataBase().selectSong(new Song(null, null, null, null, 0, user));
    }

    public Song get(String songId, String user) {
        Song song = new DataBase().selectSong(songId);
        if (song == null) {
            return null;
        }
        if (user != null) {
            return song.getUserLogin().equals(user) ? song : null;
        } else {
            return song;
        }
    }

    public boolean delete(String songId) {
        return new DataBase().deleteSong(songId);
    }

    public boolean delete(List<String> songsId) {
        return new DataBase().deleteSong(songsId);
    }

    public boolean update(Song song, String songId) {
        return new DataBase().updateSong(song, songId);
    }

    public void update(Map<String, Song> songs) {
        new DataBase().updateSong(songs);
    }

}
