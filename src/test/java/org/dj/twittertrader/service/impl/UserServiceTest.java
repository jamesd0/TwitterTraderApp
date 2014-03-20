package org.dj.twittertrader.service.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.dj.twittertrader.dao.UserDAO;
import org.dj.twittertrader.model.User;
import org.dj.twittertrader.service.UserService;
import org.dj.twittertrader.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class UserServiceTest.
 */
public class UserServiceTest {

    /** The user service. */
    private UserService userService;

    /** The user dao. */
    private UserDAO userDAO;

    /** The first. */
    private User first;

    /**
     * Setup.
     */
    @Before
    public final void setup() {
        first = TestUtil.randomUser();
        userDAO = mock(UserDAO.class);
        userService = new UserServiceImpl();
        userService.setUserDAO(userDAO);
    }

    /**
     * Test create.
     */
    @Test
    public final void testCreate() {
        userService.create(first);
        verify(userDAO, times(1)).create(first);
        verifyNoMoreInteractions(userDAO);
    }

}
