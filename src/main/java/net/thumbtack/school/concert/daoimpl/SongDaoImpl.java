package net.thumbtack.school.concert.daoimpl;

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
public class SongDaoImpl extends DataBase implements SongDao {


	public void insert(Song[] songModel, User user) throws ServerException {
		if (selectUser(user.getLogin()) == null) {
			throw new ServerException(ServerErrorCode.USERNAME_ALREADY_IN_USE, user.getLogin());
		}

	}

	public Song[] select(String[] composer, String[] author, String singer) throws ServerException {

		return null;
	}
}
