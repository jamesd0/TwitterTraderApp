package org.dj.twittertrader.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

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

    /** The second. */
    private User second;

    /**
     * Setup.
     */
    @Before
    public final void setup() {
        first = TestUtil.randomUser();
        second = TestUtil.randomUser();
        userDAO = mock(UserDAO.class);
        userService = new UserServiceImpl();
        userService.setUserDAO(userDAO);
    }

    /**
     * Test select all.
     */
    @Test
    public final void testSelectAll() {
        when(userDAO.selectAll()).thenReturn(Arrays.asList(first, second));
        List<User> result = userService.selectAll();
        assert (result.containsAll(Arrays.asList(first, second)));
        verify(userDAO, times(1)).selectAll();
        verifyNoMoreInteractions(userDAO);
    }

    /**
     * Test select.
     */
    @Test
    public final void testSelect() {
        when(userDAO.select(0)).thenReturn(first);
        User result = userService.select(0);
        assertEquals(result, first);
        verify(userDAO, times(1)).select(0);
        verifyNoMoreInteractions(userDAO);
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

    /**
     * Test delete.
     */
    @Test
    public final void testDelete() {
        userService.delete(first);
        verify(userDAO, times(1)).delete(first);
        verifyNoMoreInteractions(userDAO);
    }

    /**
     * Test update.
     */
    @Test
    public final void testUpdate() {
        userService.update(first);
        verify(userDAO, times(1)).update(first);
        verifyNoMoreInteractions(userDAO);
    }

}
