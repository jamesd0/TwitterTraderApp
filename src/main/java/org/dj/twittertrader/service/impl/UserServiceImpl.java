package org.dj.twittertrader.service.impl;

import java.util.List;

import org.dj.twittertrader.dao.UserDAO;
import org.dj.twittertrader.model.User;
import org.dj.twittertrader.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class UserServiceImpl.
 */
@Service
public class UserServiceImpl implements UserService {

    /** The user dao. */
    @Autowired
    private UserDAO userDAO;

    /*
     * (non-Javadoc)
     * 
     * @see org.dj.twittertrader.service.UserService#selectAll()
     */
    @Override
    @Transactional
    public final List<User> selectAll() {
        return userDAO.selectAll();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dj.twittertrader.service.UserService#delete(org.dj.twittertrader.
     * model.User)
     */
    @Override
    @Transactional
    public final void delete(final User user) {
        userDAO.delete(user);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dj.twittertrader.service.UserService#create(org.dj.twittertrader.
     * model.User)
     */
    @Override
    @Transactional
    public final void create(final User user) {
        userDAO.create(user);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dj.twittertrader.service.UserService#update(org.dj.twittertrader.
     * model.User)
     */
    @Override
    @Transactional
    public final void update(final User user) {
        userDAO.update(user);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dj.twittertrader.service.UserService#select(long)
     */
    @Override
    @Transactional
    public final User select(final long id) {
        return userDAO.select(id);
    }

    /**
     * Gets the user dao.
     * 
     * @return the userDAO
     */
    public final UserDAO getUserDAO() {
        return userDAO;
    }

    /**
     * Sets the user dao.
     * 
     * @param userDAO
     *            the userDAO to set
     */
    public final void setUserDAO(final UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
