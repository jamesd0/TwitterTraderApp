package org.dj.twittertrader.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.dj.twittertrader.dao.TweetDAO;
import org.dj.twittertrader.model.Company;
import org.dj.twittertrader.model.Industry;
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
        Company company;
        Industry industry;
        String sql = "SELECT * FROM Tweet " + "inner join User on Tweet.user=User.idUser"
                + " inner join Company on Tweet.company=Company.idCompany "
                + "inner join Industry on Company.industry=Industry.idIndustry"
                + " where Tweet.activeTweet=1";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tweet = new Tweet();
                user = new User();
                company = new Company();
                industry = new Industry();
                tweet.setId(resultSet.getLong("idTweet"));
                tweet.setCreatedAt(resultSet.getDate("createdAtTweet"));
                tweet.setText(resultSet.getString("text"));
                tweet.setRetweetCount(resultSet.getLong("retweetCount"));
                tweet.setActive(resultSet.getBoolean("activeTweet"));
                user.setId(resultSet.getLong("idUser"));
                user.setName(resultSet.getString("nameUser"));
                user.setScreenName(resultSet.getString("screenName"));
                user.setFollowersCount(resultSet.getInt("followersCount"));
                user.setFriendsCount(resultSet.getInt("friendsCount"));
                user.setFavouritesCount(resultSet.getInt("favouritesCount"));
                user.setVerified(resultSet.getBoolean("verified"));
                user.setLang(resultSet.getString("lang"));
                user.setCreatedAt(resultSet.getDate("createdAtUser"));
                user.setLocation(resultSet.getString("locationUser"));
                user.setActive(resultSet.getBoolean("activeUser"));
                tweet.setUser(user);
                company.setId(resultSet.getLong("idCompany"));
                company.setName(resultSet.getString("nameCompany"));
                company.setStockPrice(resultSet.getDouble("stockPrice"));
                company.setDescription(resultSet.getString("descriptionCompany"));
                company.setActive(resultSet.getBoolean("activeCompany"));
                industry.setId(resultSet.getLong("industry"));
                industry.setName(resultSet.getString("nameIndustry"));
                industry.setDescription(resultSet.getString("descriptionIndustry"));
                industry.setActive(resultSet.getBoolean("activeIndustry"));
                company.setIndustry(industry);
                tweet.setCompany(company);
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
                + " text, retweetCount, user, company, activeTweet) values (?, ?, ?, ?, ?, ?, ?)";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(DBUtils.ONE, tweet.getId());
            statement.setDate(DBUtils.TWO, DBUtils.convertDate(tweet.getCreatedAt()));
            statement.setString(DBUtils.THREE, tweet.getText());
            statement.setLong(DBUtils.FOUR, tweet.getRetweetCount());
            if (tweet.getUser() != null) {
                statement.setLong(DBUtils.FIVE, tweet.getUser().getId());
            } else {
                statement.setNull(DBUtils.FIVE, Types.BIGINT);
            }
            if (tweet.getCompany() != null) {
                statement.setLong(DBUtils.SIX, tweet.getCompany().getId());
            } else {
                statement.setNull(DBUtils.SIX, Types.BIGINT);
            }
            statement.setBoolean(DBUtils.SEVEN, tweet.isActive());
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
                + " text=?, retweetCount=?, user=?, company=?, activeTweet=? where idTweet=?";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setDate(DBUtils.ONE, DBUtils.convertDate(tweet.getCreatedAt()));
            statement.setString(DBUtils.TWO, tweet.getText());
            statement.setLong(DBUtils.THREE, tweet.getRetweetCount());
            statement.setLong(DBUtils.FOUR, tweet.getUser().getId());
            statement.setLong(DBUtils.FIVE, tweet.getCompany().getId());
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
        Company company;
        Industry industry;
        String sql = "SELECT * FROM Tweet " + "inner join User on Tweet.user=User.idUser"
                + " inner join Company on Tweet.company=Company.idCompany"
                + " inner join Industry on Company.industry=Industry.idIndustry"
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
                company = new Company();
                industry = new Industry();
                tweet.setId(resultSet.getLong("idTweet"));
                tweet.setCreatedAt(resultSet.getDate("createdAtTweet"));
                tweet.setText(resultSet.getString("text"));
                tweet.setRetweetCount(resultSet.getLong("retweetCount"));
                tweet.setActive(resultSet.getBoolean("activeTweet"));
                user.setId(resultSet.getLong("idUser"));
                user.setName(resultSet.getString("nameUser"));
                user.setScreenName(resultSet.getString("screenName"));
                user.setFollowersCount(resultSet.getInt("followersCount"));
                user.setFriendsCount(resultSet.getInt("friendsCount"));
                user.setFavouritesCount(resultSet.getInt("favouritesCount"));
                user.setVerified(resultSet.getBoolean("verified"));
                user.setLang(resultSet.getString("lang"));
                user.setCreatedAt(resultSet.getDate("createdAtUser"));
                user.setLocation(resultSet.getString("locationUser"));
                user.setActive(resultSet.getBoolean("activeUser"));
                tweet.setUser(user);
                company.setId(resultSet.getLong("idCompany"));
                company.setName(resultSet.getString("nameCompany"));
                company.setStockPrice(resultSet.getDouble("stockPrice"));
                company.setDescription(resultSet.getString("descriptionCompany"));
                company.setActive(resultSet.getBoolean("activeCompany"));
                industry.setId(resultSet.getLong("industry"));
                industry.setName(resultSet.getString("nameIndustry"));
                industry.setDescription(resultSet.getString("descriptionIndustry"));
                industry.setActive(resultSet.getBoolean("activeIndustry"));
                company.setIndustry(industry);
                tweet.setCompany(company);
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
