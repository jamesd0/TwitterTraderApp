package org.dj.twittertrader.dao.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.Date;

import org.dj.twittertrader.dao.TweetDAO;
import org.dj.twittertrader.model.Tweet;
import org.dj.twittertrader.model.User;
import org.dj.twittertrader.utils.DBUtils;
import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

/**
 * The Class TweetDAOImplTest.
 */
public class TweetDAOImplTest {

    /** The Constant INSERT_TWEET. */
    private static final String INSERT_TWEET = "insert into Tweet (idTweet, createdAtTweet,"
            + " text, user, retweetCount, scoreTweet, activeTweet)"
            + " values (?, ?, ?, ?, ?, ?, ?)"
            + " on duplicate key update createdAtTweet=?, text=?, user=?,"
            + " retweetCount=?, scoreTweet=?, activeTweet=?";

    /** The Constant TWEET_ID. */
    private static final Long TWEET_ID = (long) 12312;

    /** The Constant DATE. */
    private static final Date DATE = new Date(1231231);

    /** The Constant TWEET_TEXT. */
    private static final String TWEET_TEXT = "This is test Tweet text";

    /** The Constant USER_ID. */
    private static final Long USER_ID = (long) 123123;

    /** The Constant RETWEET_COUNT. */
    private static final Long RETWEET_COUNT = (long) 12231;

    /** The Constant TWEET_SCORE. */
    private static final double TWEET_SCORE = 1231.222;

    /** The Constant TWEET_ACTIVE. */
    private static final Boolean TWEET_ACTIVE = true;

    /** The company dao. */
    private static TweetDAO tweetDAO = new TweetDAOImpl();

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
        Tweet tweet = mock(Tweet.class);
        User user = mock(User.class);

        tweetDAO.setDataSource(dataSource);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(INSERT_TWEET)).thenReturn(statement);
        when(tweet.getId()).thenReturn(TWEET_ID);
        when(tweet.getCreatedAt()).thenReturn(DATE);
        when(tweet.getText()).thenReturn(TWEET_TEXT);
        when(tweet.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(USER_ID);
        when(tweet.getRetweetCount()).thenReturn(RETWEET_COUNT);
        when(tweet.getTweetScore()).thenReturn(TWEET_SCORE);
        when(tweet.isActive()).thenReturn(TWEET_ACTIVE);

        tweetDAO.create(tweet);

        verify(statement, times(1)).setLong(1, TWEET_ID);
        verify(statement, times(1)).setLong(2, DATE.getTime());
        verify(statement, times(1)).setString(DBUtils.THREE, TWEET_TEXT);
        verify(statement, times(1)).setLong(DBUtils.FOUR, USER_ID);
        verify(statement, times(1)).setLong(DBUtils.FIVE, RETWEET_COUNT);
        verify(statement, times(1)).setDouble(DBUtils.SIX, TWEET_SCORE);
        verify(statement, times(1)).setBoolean(DBUtils.SEVEN, TWEET_ACTIVE);
        verify(statement, times(1)).setLong(DBUtils.EIGHT, DATE.getTime());
        verify(statement, times(1)).setString(DBUtils.NINE, TWEET_TEXT);
        verify(statement, times(1)).setLong(DBUtils.TEN, USER_ID);
        verify(statement, times(1)).setLong(DBUtils.ELEVEN, RETWEET_COUNT);
        verify(statement, times(1)).setDouble(DBUtils.TWELVE, TWEET_SCORE);
        verify(statement, times(1)).setBoolean(DBUtils.THIRTEEN, TWEET_ACTIVE);
        verify(statement, times(1)).executeUpdate();
        verify(statement, times(1)).close();
        verify(connection, times(1)).close();
    }

}
