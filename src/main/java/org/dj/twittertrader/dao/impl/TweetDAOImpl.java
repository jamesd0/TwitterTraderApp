package org.dj.twittertrader.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.dj.twittertrader.dao.TweetDAO;
import org.dj.twittertrader.model.Tweet;
import org.dj.twittertrader.model.User;
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
    public final List<Tweet> selectAll() {
        List<Tweet> list = new ArrayList<Tweet>();
        Tweet tweet;
        User user;
        String sql = "SELECT * FROM Tweet inner join User on Tweet.user=User.idUser"
                + " where Tweet.activeTweet=1";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tweet = new Tweet();
                user = new User();
                tweet.setId(resultSet.getLong("idTweet"));
                tweet.setCreatedAt(new Date(resultSet.getLong("createdAtTweet")));
                tweet.setText(resultSet.getString("text"));
                tweet.setRetweetCount(resultSet.getLong("retweetCount"));
                tweet.setTweetScore(resultSet.getLong("scoreTweet"));
                tweet.setActive(resultSet.getBoolean("activeTweet"));
                user.setId(resultSet.getLong("idUser"));
                user.setName(resultSet.getString("nameUser"));
                user.setScreenName(resultSet.getString("screenName"));
                user.setFollowersCount(resultSet.getInt("followersCount"));
                user.setFriendsCount(resultSet.getInt("friendsCount"));
                user.setFavouritesCount(resultSet.getInt("favouritesCount"));
                user.setVerified(resultSet.getBoolean("verified"));
                user.setLang(resultSet.getString("lang"));
                user.setCreatedAt(new Date(resultSet.getLong("createdAtUser")));
                user.setLocation(resultSet.getString("locationUser"));
                user.setUserScore(resultSet.getLong("scoreUser"));
                user.setActive(resultSet.getBoolean("activeUser"));
                tweet.setUser(user);
                list.add(tweet);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
            DBUtils.close(connection);
        }
        return list;
    }

    @Override
    public final void delete(final Tweet tweet) {
        String sql = "update Tweet set activeTweet=0 where idTweet=?";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(DBUtils.ONE, tweet.getId());
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
    public final void update(final Tweet tweet) {
        String sql = "update Tweet set createdAtTweet=?,"
                + " text=?, user=?, retweetCount=?, scoreTweet=?, activeTweet=? where idTweet=?";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(DBUtils.ONE, tweet.getCreatedAt().getTime());
            statement.setString(DBUtils.TWO, tweet.getText());
            statement.setLong(DBUtils.THREE, tweet.getRetweetCount());
            statement.setDouble(DBUtils.FOUR, tweet.getTweetScore());
            statement.setBoolean(DBUtils.SIX, tweet.isActive());
            statement.setLong(DBUtils.SEVEN, tweet.getId());
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
    public final Tweet select(final long id) {
        Tweet tweet = null;
        User user;
        String sql = "SELECT * FROM Tweet inner join User on Tweet.user=User.idUser"
                + " where idTweet=? AND activeTweet=1";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(DBUtils.ONE, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tweet = new Tweet();
                user = new User();
                tweet.setId(resultSet.getLong("idTweet"));
                tweet.setCreatedAt(new Date(resultSet.getLong("createdAtTweet")));
                tweet.setText(resultSet.getString("text"));
                tweet.setRetweetCount(resultSet.getLong("retweetCount"));
                tweet.setTweetScore(resultSet.getLong("scoreTweet"));
                tweet.setActive(resultSet.getBoolean("activeTweet"));
                user.setId(resultSet.getLong("idUser"));
                user.setName(resultSet.getString("nameUser"));
                user.setScreenName(resultSet.getString("screenName"));
                user.setFollowersCount(resultSet.getInt("followersCount"));
                user.setFriendsCount(resultSet.getInt("friendsCount"));
                user.setFavouritesCount(resultSet.getInt("favouritesCount"));
                user.setVerified(resultSet.getBoolean("verified"));
                user.setLang(resultSet.getString("lang"));
                user.setCreatedAt(new Date(resultSet.getLong("createdAtUser")));
                user.setLocation(resultSet.getString("locationUser"));
                user.setUserScore(resultSet.getLong("scoreUser"));
                user.setActive(resultSet.getBoolean("activeUser"));
                tweet.setUser(user);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
            DBUtils.close(connection);
        }
        return tweet;
    }
}
