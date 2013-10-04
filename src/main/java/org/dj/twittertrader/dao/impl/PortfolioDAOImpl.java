package org.dj.twittertrader.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.dj.twittertrader.dao.PortfolioDAO;
import org.dj.twittertrader.model.Company;
import org.dj.twittertrader.model.Industry;
import org.dj.twittertrader.model.Portfolio;
import org.dj.twittertrader.service.CompanyService;
import org.dj.twittertrader.service.IndustryService;
import org.dj.twittertrader.utils.DBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * The Class PortfolioDAOImpl.
 */
@Repository
public class PortfolioDAOImpl implements PortfolioDAO {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(TweetDAOImpl.class);
    /** The data source. */
    @Autowired
    private DataSource dataSource;

    /** The company service. */
    @Autowired
    private CompanyService companyService;

    /** The industry service. */
    @Autowired
    private IndustryService industryService;
    /** The connection. */
    private Connection connection;

    /** The result set. */
    private ResultSet resultSet;

    /** The statement. */
    private PreparedStatement statement;

    @Override
    public final List<Portfolio> selectAll() {
        List<Portfolio> list = new ArrayList<Portfolio>();
        Portfolio portfolio = null;
        String sql = "SELECT * FROM Portfolio where activePortfolio=1";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                portfolio = new Portfolio();
                portfolio.setId(resultSet.getLong("idPortfolio"));
                portfolio.setOwner(resultSet.getString("owner"));
                portfolio.setUsername(resultSet.getString("usernamePortfolio"));
                portfolio.setPassword(resultSet.getString("password"));
                portfolio.setCompanies(new ArrayList<Company>());
                portfolio.setIndustries(new ArrayList<Industry>());
                portfolio.setActive(resultSet.getBoolean("activePortfolio"));
                list.add(portfolio);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
            DBUtils.close(connection);
        }
        for (Portfolio p : list) {
            populatePortfolioCompanies(p);
            populatePortfolioIndustries(p);
        }
        return list;
    }

    @Override
    public final void delete(final Portfolio portfolio) {
        String sql = "update Portfolio set activePortfolio=0"
                + " where idPortfolio=? AND activePortfolio=1";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(DBUtils.ONE, portfolio.getId());
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
    public final void create(final Portfolio portfolio) {
        String sql = "insert into Portfolio (owner, usernamePortfolio,"
                + " password, activePortfolio) values (?, ?, ?, ?)";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(DBUtils.ONE, portfolio.getOwner());
            statement.setString(DBUtils.TWO, portfolio.getUsername());
            statement.setString(DBUtils.THREE, portfolio.getPassword());
            statement.setBoolean(DBUtils.SEVEN, portfolio.isActive());
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
    public final void update(final Portfolio portfolio) {
        String sql = "updatePortfolio set owner, usernamePortfolio=?,"
                + " password=?, activePortfolio=? where idPortfolio=?";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(DBUtils.ONE, portfolio.getOwner());
            statement.setString(DBUtils.TWO, portfolio.getUsername());
            statement.setString(DBUtils.THREE, portfolio.getPassword());
            statement.setBoolean(DBUtils.SEVEN, portfolio.isActive());
            statement.setLong(DBUtils.SEVEN, portfolio.getId());
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
    public final Portfolio select(final long id) {
        Portfolio portfolio = null;
        String sql = "SELECT * FROM Portfolio where idPortfolio=?";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(DBUtils.ONE, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                portfolio = new Portfolio();
                portfolio.setId(resultSet.getLong("idPortfolio"));
                portfolio.setOwner(resultSet.getString("owner"));
                portfolio.setUsername(resultSet.getString("usernamePortfolio"));
                portfolio.setPassword(resultSet.getString("password"));
                portfolio.setCompanies(new ArrayList<Company>());
                portfolio.setIndustries(new ArrayList<Industry>());
                portfolio.setActive(resultSet.getBoolean("activePortfolio"));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
            DBUtils.close(connection);
        }
        populatePortfolioCompanies(portfolio);
        populatePortfolioIndustries(portfolio);
        return portfolio;
    }

    /**
     * Populate portfolio industries.
     * 
     * @param portfolio
     *            the portfolio
     */
    private void populatePortfolioIndustries(final Portfolio portfolio) {
        String sql = "select industryPI from portfolio_industry where portfolioPI=?";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(DBUtils.ONE, portfolio.getId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                portfolio.getIndustries().add(
                        industryService.select(resultSet.getLong("industryPI")));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
            DBUtils.close(connection);
        }
    }

    /**
     * Populate portfolio companies.
     * 
     * @param portfolio
     *            the portfolio
     */
    private void populatePortfolioCompanies(final Portfolio portfolio) {
        String sql = "select companyPK from portfolio_company where portfolioPC=?";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(DBUtils.ONE, portfolio.getId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                portfolio.getCompanies().add(companyService.select(resultSet.getLong("companyPK")));
            }
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
     * @see org.dj.twittertrader.dao.PortfolioDAO#login(java.lang.String,
     * java.lang.String)
     */
    @Override
    public final Portfolio login(final String username, final String password) {
        Portfolio portfolio = null;
        String sql = "SELECT * FROM Portfolio where usernamePortfolio=? AND password=?";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(DBUtils.ONE, username);
            statement.setString(DBUtils.TWO, password);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                portfolio = new Portfolio();
                portfolio.setId(resultSet.getLong("idPortfolio"));
                portfolio.setOwner(resultSet.getString("owner"));
                portfolio.setUsername(resultSet.getString("usernamePortfolio"));
                portfolio.setPassword(resultSet.getString("password"));
                portfolio.setCompanies(new ArrayList<Company>());
                portfolio.setIndustries(new ArrayList<Industry>());
                portfolio.setActive(resultSet.getBoolean("activePortfolio"));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
            DBUtils.close(connection);
        }
        populatePortfolioCompanies(portfolio);
        populatePortfolioIndustries(portfolio);
        return portfolio;

    }
}
