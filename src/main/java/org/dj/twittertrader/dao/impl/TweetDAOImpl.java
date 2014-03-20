package org.dj.twittertrader.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.dj.twittertrader.dao.TweetDAO;
import org.dj.twittertrader.model.Tweet;
import org.dj.twittertrader.utils.DBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * The Class TweetDAOImpl.
 */
@Repository
public class TweetDAOImpl implements TweetDAO {
    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(TweetDAOImpl.class);
    /** The data source. */
    @Autowired
    private DataSource dataSource;
    /** The connection. */
    private Connection connection;

    /** The result set. */
    private ResultSet resultSet;

    /** The statement. */
    private PreparedStatement statement;

    @Override
    public final void create(final Tweet tweet) {
        String sql = "insert into Tweet (idTweet, createdAtTweet,"
                + " text, user, retweetCount, scoreTweet, activeTweet)"
                + " values (?, ?, ?, ?, ?, ?, ?)"
                + " on duplicate key update createdAtTweet=?, text=?, user=?,"
                + " retweetCount=?, scoreTweet=?, activeTweet=?";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(DBUtils.ONE, tweet.getId());
            statement.setLong(DBUtils.TWO, tweet.getCreatedAt().getTime());
            statement.setString(DBUtils.THREE, tweet.getText());
            if (tweet.getUser() != null) {
                statement.setLong(DBUtils.FOUR, tweet.getUser().getId());
            } else {
                statement.setNull(DBUtils.FOUR, Types.BIGINT);
            }
            statement.setLong(DBUtils.FIVE, tweet.getRetweetCount());
            statement.setDouble(DBUtils.SIX, tweet.getTweetScore());
            statement.setBoolean(DBUtils.SEVEN, tweet.isActive());
            statement.setLong(DBUtils.EIGHT, tweet.getCreatedAt().getTime());
            statement.setString(DBUtils.NINE, tweet.getText());
            if (tweet.getUser() != null) {
                statement.setLong(DBUtils.TEN, tweet.getUser().getId());
            } else {
                statement.setNull(DBUtils.TEN, Types.BIGINT);
            }
            statement.setLong(DBUtils.ELEVEN, tweet.getRetweetCount());
            statement.setDouble(DBUtils.TWELVE, tweet.getTweetScore());
            statement.setBoolean(DBUtils.THIRTEEN, tweet.isActive());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
            DBUtils.close(connection);
        }

    }

    @Override
    public final void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;

    }

}
