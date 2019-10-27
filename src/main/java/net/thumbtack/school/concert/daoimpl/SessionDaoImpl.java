package net.thumbtack.school.concert.daoimpl;

import net.thumbtack.school.concert.dao.SessionDao;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.model.Session;
import net.thumbtack.school.concert.model.User;

public class SessionDaoImpl implements SessionDao {

	public void login(User user, Session session) {
        new DataBase().insertSession(user, session);
	}

	public void logout(Session session) {
        new DataBase().deleteSession(session);
    }

    public boolean checkSession(Session session) {

        return true;
    }

    public User get(Session session) {
        return new DataBase().selectUser(session);
    }
}
