package org.dj.twittertrader.service;

import java.util.List;

import org.dj.twittertrader.dao.UserDAO;
import org.dj.twittertrader.model.User;

/**
 * The Interface UserService.
 */
public interface UserService {

    /**
     * Select all.
     * 
     * @return the list
     */
    List<User> selectAll();

    /**
     * Delete.
     * 
     * @param user
     *            the user
     */
    void delete(User user);

    /**
     * Creates the.
     * 
     * @param user
     *            the user
     */
    void create(User user);

    /**
     * Update.
     * 
     * @param user
     *            the user
     */
    void update(User user);

    /**
     * Select.
     * 
     * @param id
     *            the id
     * @return the user
     */
    User select(long id);

    /**
     * Sets the user dao.
     * 
     * @param userDAO
     *            the new user dao
     */
    void setUserDAO(UserDAO userDAO);

}
