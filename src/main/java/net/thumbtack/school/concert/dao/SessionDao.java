package net.thumbtack.school.concert.dao;

import net.thumbtack.school.concert.exception.ServerException;
import net.thumbtack.school.concert.model.Session;
import net.thumbtack.school.concert.model.User;

public interface SessionDao {
	
	/**
     * Add the User token in session table
     *
     * @param user model for adding session
     * @param session model with new session
     * @throws ServerException
     */
    void loginUser(User user, Session session);

    /**
     * Delete session from DataBase for logged out user
     *
     * @param session model for deleting
     */
    void logoutUser(Session session);
}
