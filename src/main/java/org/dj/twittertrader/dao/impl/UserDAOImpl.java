package org.dj.twittertrader.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.dj.twittertrader.dao.UserDAO;
import org.dj.twittertrader.model.User;
import org.dj.twittertrader.utils.DBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * The Class UserDAOImpl.
 */
@Repository
public class UserDAOImpl implements UserDAO {
    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOImpl.class);
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
    public final List<User> selectAll() {
        List<User> list = new ArrayList<User>();
        User user;
        String sql = "SELECT * FROM User where activeUser=1";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = new User();
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
                list.add(user);
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
    public final void delete(final User user) {
        String sql = "update User set activeUser=0 where idUser=?";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(DBUtils.ONE, user.getId());
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
    public final void create(final User user) {
        String sql = "insert into User (idUser, nameUser,"
                + " screenName, followersCount, friendsCount, favouritesCount,"
                + " verified, lang, createdAtUser, locationUser, activeUser)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                + " on duplicate key update nameUser=?, screenName=?, followersCount=?,"
                + " friendsCount=?, favouritesCount=?, verified=?, lang=?, createdAtUser=?,"
                + " locationUser=?, activeUser=?";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(DBUtils.ONE, user.getId());
            statement.setString(DBUtils.TWO, user.getName());
            statement.setString(DBUtils.THREE, user.getScreenName());
            statement.setLong(DBUtils.FOUR, user.getFollowersCount());
            statement.setLong(DBUtils.FIVE, user.getFriendsCount());
            statement.setLong(DBUtils.SIX, user.getFavouritesCount());
            statement.setBoolean(DBUtils.SEVEN, user.isVerified());
            statement.setString(DBUtils.EIGHT, user.getLang());
            statement.setDate(DBUtils.NINE, DBUtils.convertDate(user.getCreatedAt()));
            statement.setString(DBUtils.TEN, user.getLocation());
            statement.setBoolean(DBUtils.ELEVEN, user.isActive());
            statement.setString(DBUtils.TWELVE, user.getName());
            statement.setString(DBUtils.THIRTEEN, user.getScreenName());
            statement.setLong(DBUtils.FOURTEEN, user.getFollowersCount());
            statement.setLong(DBUtils.FITHTEEN, user.getFriendsCount());
            statement.setLong(DBUtils.SIXTEEN, user.getFavouritesCount());
            statement.setBoolean(DBUtils.SEVENTEEN, user.isVerified());
            statement.setString(DBUtils.EIGHTEEN, user.getLang());
            statement.setDate(DBUtils.NINETEEN, DBUtils.convertDate(user.getCreatedAt()));
            statement.setString(DBUtils.TWENTY, user.getLocation());
            statement.setBoolean(DBUtils.TWENTYONE, user.isActive());
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
    public final void update(final User user) {
        String sql = "update User set nameUser=?,"
                + " screenName=?, followersCount=?, friendsCount=?, favouritesCount=?,"
                + " verified=?, lang=?, createdAtUser=?, locationUser=?, activeUser=?"
                + " where idUser=?";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(DBUtils.ONE, user.getName());
            statement.setString(DBUtils.TWO, user.getScreenName());
            statement.setLong(DBUtils.THREE, user.getFollowersCount());
            statement.setLong(DBUtils.FOUR, user.getFriendsCount());
            statement.setLong(DBUtils.FIVE, user.getFavouritesCount());
            statement.setBoolean(DBUtils.SIX, user.isVerified());
            statement.setString(DBUtils.SEVEN, user.getLang());
            statement.setDate(DBUtils.EIGHT, DBUtils.convertDate(user.getCreatedAt()));
            statement.setString(DBUtils.NINE, user.getLocation());
            statement.setBoolean(DBUtils.TEN, user.isActive());
            statement.setLong(DBUtils.ELEVEN, user.getId());
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
    public final User select(final long id) {
        User user = null;
        String sql = "SELECT * FROM User where idUser=? AND activeUser=1";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(DBUtils.ONE, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = new User();
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
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
            DBUtils.close(connection);
        }
        return user;
    }

}
