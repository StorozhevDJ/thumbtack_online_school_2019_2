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

    public void insert(List<Song> songModel, User user) throws ServerException {
		DataBase db = new DataBase();
		if (db.selectUser(user.getLogin()) == null) {
			throw new ServerException(ServerErrorCode.USERNAME_ALREADY_IN_USE, user.getLogin());
		}
		db.insertSongs(songModel);    //Add new songs into the DataBase
        // TODO add rating
	}

	public Song[] select(String[] composer, String[] author, String singer) throws ServerException {
        // TODO  add code to select Songs from table
		return null;
	}
}
