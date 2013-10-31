package org.dj.twittertrader.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.dj.twittertrader.dao.IndustryDAO;
import org.dj.twittertrader.model.Company;
import org.dj.twittertrader.model.Industry;
import org.dj.twittertrader.model.Portfolio;
import org.dj.twittertrader.model.Tweet;
import org.dj.twittertrader.model.User;
import org.dj.twittertrader.service.CompanyService;
import org.dj.twittertrader.utils.DBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * The Class IndustryDAOImpl is the class that interacts with the database to
 * perform actions on the Industry entity.
 */
@Repository
public class IndustryDAOImpl implements IndustryDAO {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(IndustryDAOImpl.class);

    /** The data source. */
    @Autowired
    private DataSource dataSource;

    /** The company service. */
    @Autowired
    private CompanyService companyService;

    /** The connection. */
    private Connection connection;

    /** The result set. */
    private ResultSet resultSet;

    /** The statement. */
    private PreparedStatement statement;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dj.twittertrader.dao.IndustryDAO#create(org.dj.twittertrader.model
     * .Industry)
     */
    @Override
    public final void create(final Industry industry) {
        String sql = "insert into Industry (nameIndustry,"
                + " descriptionIndustry, activeIndustry) values (?, ?, ?)";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(DBUtils.ONE, industry.getName());
            statement.setString(DBUtils.TWO, industry.getDescription());
            statement.setBoolean(DBUtils.THREE, industry.isActive());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
            DBUtils.close(connection);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dj.twittertrader.dao.IndustryDAO#delete(org.dj.twittertrader.model
     * .Industry)
     */
    @Override
    public final void delete(final Industry industry) {
        String sql = "update Industry set activeIndustry=0 where idIndustry=?";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(DBUtils.ONE, industry.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
            DBUtils.close(connection);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dj.twittertrader.dao.IndustryDAO#update(org.dj.twittertrader.model
     * .Industry)
     */
    @Override
    public final void update(final Industry industry) {
        String sql = "update Industry set nameIndustry=?," + " descriptionIndustry=?,"
                + " activeIndustry=? where idIndustry=?";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(DBUtils.ONE, industry.getName());
            statement.setString(DBUtils.TWO, industry.getDescription());
            statement.setBoolean(DBUtils.THREE, industry.isActive());
            statement.setLong(DBUtils.FOUR, industry.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
            DBUtils.close(connection);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dj.twittertrader.dao.IndustryDAO#selectAll()
     */
    @Override
    public final List<Industry> selectAll() {
        List<Industry> list = new ArrayList<Industry>();
        String sql = "SELECT * FROM Industry where activeIndustry=1";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Industry industry = new Industry();
                industry.setId(resultSet.getLong("idIndustry"));
                industry.setName(resultSet.getString("nameIndustry"));
                industry.setDescription(resultSet.getString("descriptionIndustry"));
                industry.setActive(resultSet.getBoolean("activeIndustry"));
                industry.setCompanies(new ArrayList<Company>());
                list.add(industry);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
            DBUtils.close(connection);
        }
        for (Industry industry : list) {
            companyService.populateIndustryCompanies(industry);
            industry.setTweets(getAllIndustryTweets(industry.getId()));
            industry.setStreamTokens(getIndustryStreamingTokens(industry.getId()));
        }
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dj.twittertrader.dao.IndustryDAO#select(long)
     */
    @Override
    public final Industry select(final long id) {
        Industry industry = new Industry();
        String sql = "SELECT * FROM Industry where idIndustry=? AND activeIndustry=1";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(DBUtils.ONE, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                industry.setId(resultSet.getLong("idIndustry"));
                industry.setName(resultSet.getString("nameIndustry"));
                industry.setDescription(resultSet.getString("descriptionIndustry"));
                industry.setActive(resultSet.getBoolean("activeIndustry"));
                industry.setCompanies(new ArrayList<Company>());
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
            DBUtils.close(connection);
        }
        LOGGER.info(industry.toString());
        if (industry != null) {
            companyService.populateIndustryCompanies(industry);
            industry.setTweets(getAllIndustryTweets(industry.getId()));
            industry.setStreamTokens(getIndustryStreamingTokens(industry.getId()));
        }
        return industry;
    }

    /**
     * Gets the industry streaming tokens.
     * 
     * @param id
     *            the id
     * @return the industry streaming tokens
     */
    private List<String> getIndustryStreamingTokens(final long id) {
        List<String> tokens = new ArrayList<String>();
        String sql = "select tag as tags from company_tags as ctags where"
                + " ctags.company in(select companyIC from industry_company where industryIC=" + id
                + ") union select nameCompany as tags from Company where"
                + " idCompany in (select companyIC from industry_company where industryIC=" + id
                + ")";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tokens.add(resultSet.getString("tags"));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
            DBUtils.close(connection);
        }
        return tokens;
    }

    /**
     * Gets the all industry tweets.
     * 
     * @param id
     *            the id
     * @return the all industry tweets
     */
    private List<Tweet> getAllIndustryTweets(final long id) {
        List<Tweet> list = new ArrayList<Tweet>();
        String sql = "SELECT * FROM Tweet as t inner join User as u"
                + " on t.user=u.idUser where idTweet in"
                + " (select ct.tweetCT from company_tweet as ct where ct.companyCT in "
                + "(select companyIC from industry_company where industryIC=?))"
                + " order by scoreTweet desc";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(DBUtils.ONE, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Tweet tweet = new Tweet();
                User user = new User();
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
    public final void populatePortfolioIndustries(final Portfolio portfolio) {
        List<Industry> industries = new ArrayList<Industry>();
        String sql = "SELECT * FROM Industry where activeIndustry=1 and idIndustry in "
                + "(select industryPI from portfolio_industry where portfolioPI=?)";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(DBUtils.ONE, portfolio.getId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Industry industry = new Industry();
                industry.setId(resultSet.getLong("idIndustry"));
                industry.setName(resultSet.getString("nameIndustry"));
                industry.setDescription(resultSet.getString("descriptionIndustry"));
                industry.setActive(resultSet.getBoolean("activeIndustry"));
                industry.setCompanies(new ArrayList<Company>());
                industries.add(industry);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
            DBUtils.close(connection);
        }
        for (Industry industry : industries) {
            companyService.populateIndustryCompanies(industry);
            industry.setTweets(getAllIndustryTweets(industry.getId()));
            industry.setStreamTokens(getIndustryStreamingTokens(industry.getId()));
        }
        portfolio.getIndustries().addAll(industries);
    }
}
