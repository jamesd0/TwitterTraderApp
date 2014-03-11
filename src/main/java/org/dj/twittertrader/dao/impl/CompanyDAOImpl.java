package org.dj.twittertrader.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.dj.twittertrader.dao.CompanyDAO;
import org.dj.twittertrader.model.Company;
import org.dj.twittertrader.model.Industry;
import org.dj.twittertrader.model.Portfolio;
import org.dj.twittertrader.model.Tweet;
import org.dj.twittertrader.model.User;
import org.dj.twittertrader.service.TweetService;
import org.dj.twittertrader.utils.DBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * The Class CompanyDAOImpl is the class that interacts with the database to perform actions on the
 * Company entity.
 */
@Repository
public class CompanyDAOImpl implements CompanyDAO {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAOImpl.class);

    /** The tweet service. */
    @Autowired
    private TweetService tweetService;
    /** The data source. */
    @Autowired
    private DataSource dataSource;

    /** The connection. */
    private Connection connection;

    /** The result set. */
    private ResultSet resultSet;

    /** The statement. */
    private PreparedStatement statement;

    /*
     * (non-Javadoc)
     * 
     * @see org.dj.twittertrader.dao.CompanyDAO#delete(org.dj.twittertrader.model .Company)
     */
    @Override
    public final void delete(final Company company) {
        String sql = "update Company set activeCompany=0 where idCompany=?";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(DBUtils.ONE, company.getId());
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
     * @see org.dj.twittertrader.dao.CompanyDAO#update(org.dj.twittertrader.model .Company)
     */
    @Override
    public final void update(final Company company) {
        String sql = "update Company set nameCompany=?, descriptionCompany=?, stockPrice=?,"
                + " scoreCompany=?, activeCompany=? where idCompany=?";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(DBUtils.ONE, company.getName());
            statement.setString(DBUtils.TWO, company.getDescription());
            statement.setDouble(DBUtils.THREE, company.getStockPrice());
            statement.setLong(DBUtils.FOUR, company.getCompanyScore());
            statement.setBoolean(DBUtils.FIVE, company.isActive());
            statement.setLong(DBUtils.SIX, company.getId());
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
     * @see org.dj.twittertrader.dao.CompanyDAO#create(org.dj.twittertrader.model .Company)
     */
    @Override
    public final void create(final Company company) {
        String sql = "insert into Company (nameCompany,"
                + " descriptionCompany, stockPrice, scoreCompany,"
                + " activeCompany) values (?, ?, ?, ?, ?)";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(DBUtils.ONE, company.getName());
            statement.setString(DBUtils.TWO, company.getDescription());
            statement.setDouble(DBUtils.THREE, company.getStockPrice());
            statement.setLong(DBUtils.FOUR, company.getCompanyScore());
            statement.setBoolean(DBUtils.FIVE, company.isActive());
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
     * @see org.dj.twittertrader.dao.CompanyDAO#selectAll()
     */
    @Override
    public final List<Company> selectAll() {
        List<Company> list = new ArrayList<Company>();
        String sql = "SELECT *,(select sum(scoreTweet) from Tweet where idTweet in "
                + "(select tweetCT from company_tweet where companyCT=c.idCompany))"
                + "as score,(select nameIndustry from Industry where idIndustry="
                + "(select industryIC from industry_company where companyIC=c.idCompany))"
                + " as industry, (select price from StockPrice where company=c.idCompany "
                + "order by date desc limit 1) as stockPrice"
                + " FROM Company as c where c.activeCompany=1";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Company company = new Company();
                company.setId(resultSet.getLong("idCompany"));
                company.setName(resultSet.getString("nameCompany"));
                company.setStockPrice(resultSet.getDouble("stockPrice"));
                company.setDescription(resultSet.getString("descriptionCompany"));
                company.setCompanyScore(resultSet.getLong("score"));
                company.setStockSymbol(resultSet.getString("stockSymbol"));
                company.setStockCurrency(resultSet.getString("currency"));
                company.setIndustry(resultSet.getString("industry"));
                company.setActive(resultSet.getBoolean("activeCompany"));
                list.add(company);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
            DBUtils.close(connection);
        }
        for (Company company : list) {
            if (company != null) {
                company.setTweets(new ArrayList<Tweet>());
                company.setTags(new ArrayList<String>());
            }
        }
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dj.twittertrader.dao.CompanyDAO#select(long)
     */
    @Override
    public final Company select(final long id) {
        Company company = null;
        String sql = "SELECT *,(select sum(scoreTweet) from Tweet where idTweet in "
                + "(select tweetCT from company_tweet where companyCT=c.idCompany))"
                + "as score,(select nameIndustry from Industry where idIndustry="
                + "(select industryIC from industry_company where companyIC=c.idCompany)) as industry,"
                + "(select price from StockPrice where company=c.idCompany "
                + "order by date desc limit 1) as stockPrice"
                + " FROM Company as c where c.activeCompany=1 and c.idCompany=?";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(DBUtils.ONE, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                company = new Company();
                company.setId(resultSet.getLong("idCompany"));
                company.setName(resultSet.getString("nameCompany"));
                company.setStockPrice(resultSet.getDouble("stockPrice"));
                company.setDescription(resultSet.getString("descriptionCompany"));
                company.setCompanyScore(resultSet.getLong("score"));
                company.setStockSymbol(resultSet.getString("stockSymbol"));
                company.setStockCurrency(resultSet.getString("currency"));
                company.setIndustry(resultSet.getString("industry"));
                company.setActive(resultSet.getBoolean("activeCompany"));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
            DBUtils.close(connection);
        }
        if (company != null) {
            company.setTweets(getAllCompanyTweets(company.getId()));
            company.setTags(getAllCompanyTags(company.getId()));
        }
        return company;
    }

    /**
     * Gets the all company tags.
     * 
     * @param id
     *            the id
     * @return the all company tags
     */
    private List<String> getAllCompanyTags(final long id) {
        List<String> tags = new ArrayList<String>();
        String sql = "SELECT tag from company_tags where company=?";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(DBUtils.ONE, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tags.add(resultSet.getString("tag"));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
            DBUtils.close(connection);
        }
        return tags;
    }

    /**
     * Gets the all company tweets.
     * 
     * @param id
     *            the id
     * @return the all company tweets
     */
    private List<Tweet> getAllCompanyTweets(final long id) {
        List<Tweet> list = new ArrayList<Tweet>();
        String sql = "SELECT * FROM Tweet as t inner join User as u"
                + " on t.user=u.idUser where idTweet in"
                + " (select ct.tweetCT from company_tweet as ct where ct.companyCT=?)"
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

    /*
     * (non-Javadoc)
     * 
     * @see org.dj.twittertrader.dao.CompanyDAO#addTweetToCompany(org.dj.twittertrader
     * .model.Company, org.dj.twittertrader.model.Tweet)
     */
    @Override
    public final void addTweetToCompany(final Company company, final Tweet tweet) {
        String sql = "INSERT INTO company_tweet (companyCT, tweetCT) VALUES (?, ?)";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(DBUtils.ONE, company.getId());
            statement.setLong(DBUtils.TWO, tweet.getId());
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

    @Override
    public final void setTweetService(final TweetService tweetService) {
        this.tweetService = tweetService;

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dj.twittertrader.dao.CompanyDAO#addStockPrice(org.dj.twittertrader .model.Company,
     * double, java.util.Date)
     */
    @Override
    public final void addStockPrice(final Company company) {
        String sql = "insert into StockPrice (company, date, price) values (?, ?, ?)";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(DBUtils.ONE, company.getId());
            statement.setLong(DBUtils.TWO, new Date().getTime());
            statement.setDouble(DBUtils.THREE, company.getStockPrice());
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
    public final void populatePortfolioCompanies(final Portfolio portfolio) {
        List<Company> companies = new ArrayList<Company>();
        String sql = "SELECT *,(select sum(scoreTweet) from Tweet where idTweet in "
                + "(select tweetCT from company_tweet where companyCT=c.idCompany))"
                + "as score,(select price from StockPrice where company=c.idCompany "
                + "order by date desc limit 1) as stockPrice"
                + " FROM Company as c where c.activeCompany=1 and c.idCompany in"
                + " (select companyPK from portfolio_company where portfolioPC=?)";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(DBUtils.ONE, portfolio.getId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Company company = new Company();
                company.setId(resultSet.getLong("idCompany"));
                company.setName(resultSet.getString("nameCompany"));
                company.setStockPrice(resultSet.getDouble("stockPrice"));
                company.setDescription(resultSet.getString("descriptionCompany"));
                company.setCompanyScore(resultSet.getLong("score"));
                company.setStockSymbol(resultSet.getString("stockSymbol"));
                company.setActive(resultSet.getBoolean("activeCompany"));
                companies.add(company);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
            DBUtils.close(connection);
        }

        for (Company company : companies) {
            company.setTweets(getAllCompanyTweets(company.getId()));
            company.setTags(getAllCompanyTags(company.getId()));
        }
        portfolio.getCompanies().addAll(companies);

    }

    @Override
    public final void populateIndustryCompanies(final Industry industry) {
        List<Company> companies = new ArrayList<Company>();
        String sql = "SELECT *,(select sum(scoreTweet) from Tweet where idTweet in "
                + "(select tweetCT from company_tweet where companyCT=c.idCompany))"
                + "as score,(select price from StockPrice where company=c.idCompany "
                + "order by date desc limit 1) as stockPrice"
                + " FROM Company as c where c.activeCompany=1 and c.idCompany in"
                + " (select companyIC from industry_company where industryIC=?)";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(DBUtils.ONE, industry.getId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Company company = new Company();
                company.setId(resultSet.getLong("idCompany"));
                company.setName(resultSet.getString("nameCompany"));
                company.setStockPrice(resultSet.getDouble("stockPrice"));
                company.setDescription(resultSet.getString("descriptionCompany"));
                company.setCompanyScore(resultSet.getLong("score"));
                company.setStockSymbol(resultSet.getString("stockSymbol"));
                company.setActive(resultSet.getBoolean("activeCompany"));
                companies.add(company);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
            DBUtils.close(connection);
        }

        for (Company company : companies) {
            company.setTweets(getAllCompanyTweets(company.getId()));
            company.setTags(getAllCompanyTags(company.getId()));
        }
        industry.getCompanies().addAll(companies);

    }
}
