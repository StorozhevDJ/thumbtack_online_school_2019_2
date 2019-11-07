package net.thumbtack.school.concert.daoimpl;

import net.thumbtack.school.concert.dao.UserDao;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.exception.ServerErrorCode;
import net.thumbtack.school.concert.exception.ServerException;
import net.thumbtack.school.concert.model.User;

public class UserDaoImpl implements UserDao {

	public void add(User user) throws ServerException {
		DataBase db = new DataBase();
        if ((db.selectUser(user.getLogin()) != null) || (!db.insertUser(user))) {
			throw new ServerException(ServerErrorCode.USERNAME_ALREADY_IN_USE, user.getLogin());
		}
	}

	public User get(String login) {
        return new DataBase().selectUser(login);
	}

	public void delete(String login) {
		new DataBase().deleteUser(login);
	}
}
