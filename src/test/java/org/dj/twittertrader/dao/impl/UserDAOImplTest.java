package org.dj.twittertrader.dao.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.Date;

import org.dj.twittertrader.dao.UserDAO;
import org.dj.twittertrader.model.User;
import org.dj.twittertrader.utils.DBUtils;
import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

// TODO: Auto-generated Javadoc
/**
 * The Class TweetDAOImplTest.
 */
public class UserDAOImplTest {

    /** The Constant INSERT_TWEET. */
    private static final String INSERT_USER = "insert into User (idUser, nameUser,"
            + " screenName, followersCount, friendsCount, favouritesCount,"
            + " verified, lang, createdAtUser, locationUser,scoreUser, activeUser)"
            + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?)"
            + " on duplicate key update nameUser=?, screenName=?, followersCount=?,"
            + " friendsCount=?, favouritesCount=?, verified=?, lang=?, createdAtUser=?,"
            + " locationUser=?, scoreUser=?, activeUser=?";

    /** The Constant DATE. */
    private static final Date DATE = new Date(1231231);

    /** The Constant USER_ID. */
    private static final Long USER_ID = (long) 123123;

    /** The Constant USER_NAME. */
    private static final String USER_NAME = "UserName";

    /** The Constant SCREEN_NAME. */
    private static final String SCREEN_NAME = "ScreenName";

    /** The Constant FOLLOWERS. */
    private static final Integer FOLLOWERS = 234123;

    /** The Constant FRIENDS. */
    private static final Integer FRIENDS = 123123;

    /** The Constant FAVOURITES. */
    private static final Integer FAVOURITES = 1232;

    /** The Constant VERIFIED. */
    private static final Boolean VERIFIED = true;

    /** The Constant LANG. */
    private static final String LANG = "en";

    /** The Constant LOCATION. */
    private static final String LOCATION = "Manchester";

    /** The Constant USER_SCORE. */
    private static final Double USER_SCORE = 122.66;

    /** The Constant ACTIVE. */
    private static final Boolean ACTIVE = true;

    /** The company dao. */
    private static UserDAO userDAO = new UserDAOImpl();

    /** The data source. */
    private static DriverManagerDataSource dataSource;

    /**
     * Test Create.
     * 
     * @throws SQLException
     *             the sQL exception
     */
    @Test
    public final void testCreate() throws SQLException {
        dataSource = mock(DriverManagerDataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        User user = mock(User.class);

        userDAO.setDataSource(dataSource);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(INSERT_USER)).thenReturn(statement);
        when(user.getId()).thenReturn(USER_ID);
        when(user.getName()).thenReturn(USER_NAME);
        when(user.getScreenName()).thenReturn(SCREEN_NAME);
        when(user.getFollowersCount()).thenReturn(FOLLOWERS);
        when(user.getFriendsCount()).thenReturn(FRIENDS);
        when(user.getFavouritesCount()).thenReturn(FAVOURITES);
        when(user.isVerified()).thenReturn(VERIFIED);
        when(user.getLang()).thenReturn(LANG);
        when(user.getCreatedAt()).thenReturn(DATE);
        when(user.getLocation()).thenReturn(LOCATION);
        when(user.getUserScore()).thenReturn(USER_SCORE);
        when(user.isActive()).thenReturn(ACTIVE);

        userDAO.create(user);

        verify(statement, times(1)).setLong(1, USER_ID);
        verify(statement, times(1)).setString(2, USER_NAME);
        verify(statement, times(1)).setString(DBUtils.THREE, SCREEN_NAME);
        verify(statement, times(1)).setLong(DBUtils.FOUR, FOLLOWERS);
        verify(statement, times(1)).setLong(DBUtils.FIVE, FRIENDS);
        verify(statement, times(1)).setLong(DBUtils.SIX, FAVOURITES);
        verify(statement, times(1)).setBoolean(DBUtils.SEVEN, VERIFIED);
        verify(statement, times(1)).setString(DBUtils.EIGHT, LANG);
        verify(statement, times(1)).setLong(DBUtils.NINE, DATE.getTime());
        verify(statement, times(1)).setString(DBUtils.TEN, LOCATION);
        verify(statement, times(1)).setDouble(DBUtils.ELEVEN, USER_SCORE);
        verify(statement, times(1)).setBoolean(DBUtils.TWELVE, ACTIVE);
        verify(statement, times(1)).setString(DBUtils.THIRTEEN, USER_NAME);
        verify(statement, times(1)).setString(DBUtils.FOURTEEN, SCREEN_NAME);
        verify(statement, times(1)).setLong(DBUtils.FITHTEEN, FOLLOWERS);
        verify(statement, times(1)).setLong(DBUtils.SIXTEEN, FRIENDS);
        verify(statement, times(1)).setLong(DBUtils.SEVENTEEN, FAVOURITES);
        verify(statement, times(1)).setBoolean(DBUtils.EIGHTEEN, VERIFIED);
        verify(statement, times(1)).setString(DBUtils.NINETEEN, LANG);
        verify(statement, times(1)).setLong(DBUtils.TWENTY, DATE.getTime());
        verify(statement, times(1)).setString(DBUtils.TWENTYONE, LOCATION);
        verify(statement, times(1)).setDouble(DBUtils.TWENTYTWO, USER_SCORE);
        verify(statement, times(1)).setBoolean(DBUtils.TWENTYTHREE, ACTIVE);
        verify(statement, times(1)).executeUpdate();
        verify(statement, times(1)).close();
        verify(connection, times(1)).close();
    }
}
