package net.thumbtack.school.concert.dao;

import net.thumbtack.school.concert.exception.ServerException;
import net.thumbtack.school.concert.model.Session;
import net.thumbtack.school.concert.model.User;

public interface SessionDao {

    /**
     * Add the User token in session table
     *
     * @param session
     * @throws ServerException
     */
    void login(User user, Session session) throws ServerException;

    /**
     * Delete session from DataBase for logged out user
     *
     * @param session
     */
    void logout(Session session);

    /**
     * Check user session
     *
     * @param session
     * @return
     */
    boolean checkSession(Session session);

    /**
     * Get user from session
     *
     * @param session
     * @return
     */
    User get(Session session);
}
