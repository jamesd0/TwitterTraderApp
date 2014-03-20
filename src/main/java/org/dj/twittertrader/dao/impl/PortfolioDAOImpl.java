package org.dj.twittertrader.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.dj.twittertrader.dao.PortfolioDAO;
import org.dj.twittertrader.service.CompanyService;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(PortfolioDAOImpl.class);
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

    @Override
    public final List<String> getStreamTokens(final long id) {
        List<String> tokens = new ArrayList<String>();
        String sql = "select tag as tags from company_tags as ctags where"
                + " ctags.company in(select companyPK from portfolio_company as pc"
                + " where pc.portfolioPC=" + id
                + ") union select nameCompany as tags from Company where"
                + " idCompany in (select companyPK from portfolio_company "
                + "as pc where pc.portfolioPC=" + id + ") and Company.activeCompany=1"
                + " union select nameCompany as tags from Company where"
                + " idCompany in (select companyIC from industry_company "
                + "as ic where ic.industryIC in"
                + " (select industryPI from portfolio_industry as pi where pi.portfolioPI=" + id
                + ")) and Company.activeCompany=1;";
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
        tokens.addAll(getStockSymbolTags(id));
        return tokens;
    }

    /**
     * Gets the stock symbol tags.
     * 
     * @param id
     *            the id
     * @return the stock symbol tags
     */
    private List<String> getStockSymbolTags(long id) {
        List<String> tokens = new ArrayList<String>();
        String sql = "select stockSymbol as tags from twittertrader.Company"
                + " where idCompany in (select companyPK from twittertrader.portfolio_company"
                + " as pc where pc.portfolioPC = " + id + ") and Company.activeCompany = 1"
                + " union select stockSymbol as tags from twittertrader.Company "
                + "where idCompany in (select companyIC from twittertrader.industry_company"
                + " as ic where ic.industryIC in "
                + "(select industryPI from twittertrader.portfolio_industry"
                + " as pi where pi.portfolioPI = " + id + ")) and Company.activeCompany = 1;";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String tag = resultSet.getString("tags");
                if (tag.contains(".")) {
                    tokens.add("$" + tag.split("\\.")[0]);
                } else {
                    tokens.add("$" + tag);
                }
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

    @Override
    public final void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;

    }
}
