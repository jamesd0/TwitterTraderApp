package org.dj.twittertrader.service;

import org.dj.twittertrader.dao.UserDAO;
import org.dj.twittertrader.model.User;

/**
 * The Interface UserService.
 */
public interface UserService {

    /**
     * Creates the.
     * 
     * @param user
     *            the user
     */
    void create(User user);

    /**
     * Sets the user dao.
     * 
     * @param userDAO
     *            the new user dao
     */
    void setUserDAO(UserDAO userDAO);

}
