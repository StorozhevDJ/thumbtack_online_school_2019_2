package net.thumbtack.school.concert.daoimpl;

import net.thumbtack.school.concert.dao.UserDao;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.exception.ServerErrorCode;
import net.thumbtack.school.concert.exception.ServerException;
import net.thumbtack.school.concert.model.User;

public class UserDaoImpl extends DataBase implements UserDao {

	public void insert(User user) throws ServerException {
		if ((getUser(user.getLogin()) != null) || (!insertUser(user))) {
			throw new ServerException(ServerErrorCode.USERNAME_ALREADY_IN_USE, user.getLogin());
		}
	}

	public User getInfo(String login) {
		return getUser(login);
	}
	

}
