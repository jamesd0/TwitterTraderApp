package org.dj.twittertrader.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.dj.twittertrader.dao.IndustryDAO;
import org.dj.twittertrader.model.Company;
import org.dj.twittertrader.model.Industry;
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
            industry.setCompanies(getAllIndustryCompanies(industry.getId()));
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
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
            DBUtils.close(connection);
        }
        industry.setCompanies(getAllIndustryCompanies(industry.getId()));
        return industry;
    }

    /**
     * Gets the all industry companies.
     * 
     * @param id
     *            the id
     * @return the all industry companies
     */
    private List<Company> getAllIndustryCompanies(final long id) {
        List<Company> companies = new ArrayList<Company>();
        String sql = "select companyIC from industry_company where industryIC=?";
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(DBUtils.ONE, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                companies.add(companyService.select(resultSet.getLong("companyIC")));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
            DBUtils.close(connection);
        }
        return companies;
    }
}
