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
import org.dj.twittertrader.model.Tweet;
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
            company.setTweets(new ArrayList<Tweet>());
            company.setTags(new ArrayList<String>());
        }
        return company;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dj.twittertrader.dao.CompanyDAO#addTweetToCompany(org.dj.twittertrader
     * .model.Company, org.dj.twittertrader.model.Tweet)
     */
    @Override
    public void addTweetToCompany(final Company company, final Tweet tweet) {
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

}
