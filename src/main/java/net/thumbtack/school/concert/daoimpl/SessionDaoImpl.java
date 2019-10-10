package net.thumbtack.school.concert.daoimpl;

import net.thumbtack.school.concert.dao.SessionDao;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.exception.ServerErrorCode;
import net.thumbtack.school.concert.exception.ServerException;
import net.thumbtack.school.concert.model.Session;
import net.thumbtack.school.concert.model.User;

public class SessionDaoImpl extends DataBase implements SessionDao {

	public void loginUser(User user, Session session) {
		newSession(user, session);
	}

	public void insert(User user) throws ServerException {
		if ((getUser(user.getLogin()) != null) || (!insertUser(user))) {
			throw new ServerException(ServerErrorCode.USERNAME_ALREADY_IN_USE);
		}
	}

}
