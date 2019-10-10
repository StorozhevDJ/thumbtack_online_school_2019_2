package net.thumbtack.school.concert.dao;

import net.thumbtack.school.concert.exception.ServerException;
import net.thumbtack.school.concert.model.User;

public interface UserDao {
	//UserDao();
	/**
	 * Add new user in the DataBase 
	 * 
	 * @param user - user info (user name, login, password and etc.)
	 * @throws ServerException
	 */
    void insert(User user) throws ServerException;
    
    /**
     * Get User info
     * 
     * @param login for find the User in DataBase
     * @return
     */
    User getInfo (String login);
    
    
}
