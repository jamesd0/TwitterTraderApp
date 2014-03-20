package org.dj.twittertrader.dao;

import org.dj.twittertrader.model.User;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * The Interface UserDAO.
 */
public interface UserDAO {

    /**
     * Creates the.
     * 
     * @param user
     *            the user
     */
    void create(User user);

    void setDataSource(DriverManagerDataSource dataSource);

}
