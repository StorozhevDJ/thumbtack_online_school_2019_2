package net.thumbtack.school.concert.daoimpl;

import java.util.List;

import net.thumbtack.school.concert.dao.SongDao;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.exception.ServerErrorCode;
import net.thumbtack.school.concert.exception.ServerException;
import net.thumbtack.school.concert.model.Song;
import net.thumbtack.school.concert.model.User;

/**
 * Class for save Song date
 * 
 * @author Denis
 *
 */
public class SongDaoImpl implements SongDao {

	public void add(List<Song> songModel, User user) throws ServerException {
		DataBase db = new DataBase();
		if (db.selectUser(user.getLogin()) == null) {
			throw new ServerException(ServerErrorCode.USERNAME_ALREADY_IN_USE, user.getLogin());
		}
		db.insertSongs(songModel); // Add new songs into the DataBase
	}

	public List<Song> get(List<String> composer, List<String> author, String singer) {
		return new DataBase().selectSong(new Song(null, composer, author, singer, 0, null));
	}

	public Song get(String songName, String user) {
		Song song = new DataBase().selectSong(songName);
		if (song == null) {
			return null;
		}
		return song.getUserLogin().equals(user) ? song : null;
	}

	public boolean delete(Song song) {
		return new DataBase().deleteSong(song);
	}

}
