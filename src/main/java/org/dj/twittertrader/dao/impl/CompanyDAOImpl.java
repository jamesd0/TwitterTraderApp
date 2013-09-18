package org.dj.twittertrader.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.dj.twittertrader.dao.CompanyDAO;
import org.dj.twittertrader.model.Company;
import org.dj.twittertrader.model.Industry;
import org.dj.twittertrader.utils.DBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * The Class CompanyDAOImpl is the class that interacts with the database to
 * perform actions on the Company entity.
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

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dj.twittertrader.dao.CompanyDAO#delete(org.dj.twittertrader.model
     * .Company)
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
     * @see
     * org.dj.twittertrader.dao.CompanyDAO#update(org.dj.twittertrader.model
     * .Company)
     */
    @Override
    public final void update(final Company company) {
        String sql = "update Company set nameCompany=?," + " descriptionCompany=?, stockPrice=?,"
                + " industry=?, activeCompany=? where idCompany=?";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(DBUtils.ONE, company.getName());
            statement.setString(DBUtils.TWO, company.getDescription());
            statement.setDouble(DBUtils.THREE, company.getStockPrice());
            statement.setLong(DBUtils.FOUR, company.getIndustry().getId());
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
     * @see
     * org.dj.twittertrader.dao.CompanyDAO#create(org.dj.twittertrader.model
     * .Company)
     */
    @Override
    public final void create(final Company company) {
        String sql = "insert into Company (nameCompany,"
                + " descriptionCompany, stockPrice, industry,"
                + " activeCompany) values (?, ?, ?, ?, ?)";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(DBUtils.ONE, company.getName());
            statement.setString(DBUtils.TWO, company.getDescription());
            statement.setDouble(DBUtils.THREE, company.getStockPrice());
            statement.setLong(DBUtils.FOUR, company.getIndustry().getId());
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
        Company company;
        Industry industry;
        String sql = "SELECT Company.*, Industry.nameIndustry,"
                + " Industry.descriptionIndustry, Industry.activeIndustry "
                + "FROM twittertrader.Company " + "inner join Industry on"
                + " Company.industry=Industry.idIndustry where Company.activeCompany=1;";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                industry = new Industry();
                company = new Company();
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
                list.add(company);
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
     * @see org.dj.twittertrader.dao.CompanyDAO#select(long)
     */
    @Override
    public final Company select(final long id) {
        Company company = null;
        Industry industry;
        String sql = "SELECT Company.*, Industry.nameIndustry,"
                + " Industry.descriptionIndustry, Industry.activeIndustry"
                + " FROM twittertrader.Company inner join Industry on"
                + " Company.industry=Industry.idIndustry" + " where Company.idCompany=" + id
                + " AND activeCompany=1";
        LOGGER.info(sql);
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                industry = new Industry();
                company = new Company();
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
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            DBUtils.close(resultSet);
            DBUtils.close(statement);
            DBUtils.close(connection);
        }
        return company;
    }
}
