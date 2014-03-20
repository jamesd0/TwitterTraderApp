package org.dj.twittertrader.service.impl;

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
     * @see org.dj.twittertrader.service.UserService#create(org.dj.twittertrader. model.User)
     */
    @Override
    @Transactional
    public final void create(final User user) {
        userDAO.create(user);

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
