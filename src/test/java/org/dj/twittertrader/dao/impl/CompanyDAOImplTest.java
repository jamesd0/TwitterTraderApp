package org.dj.twittertrader.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.List;

import org.dj.twittertrader.dao.CompanyDAO;
import org.dj.twittertrader.model.Company;
import org.dj.twittertrader.model.Tweet;
import org.dj.twittertrader.utils.DBUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

/**
 * The Class CompanyDAOImplTest.
 */
public class CompanyDAOImplTest {

    /** The Constant URL. */
    private static final String URL = "jdbc:mysql://localhost:3306/twittertrader?"
            + "zeroDateTimeBehavior=convertToNull";

    /** The Constant ADD_COMPANY_TWEET. */
    private static final String ADD_COMPANY_TWEET = "INSERT INTO company_tweet"
            + " (companyCT, tweetCT) VALUES (?, ?)";

    /** The Constant ADD_STOCK_PRICE. */
    private static final String ADD_STOCK_PRICE = "insert into StockPrice"
            + " (company, date, price) values (?, ?, ?)";

    /** The Constant GOOGLE_ID. */
    private static final int GOOGLE_ID = 16;

    /** The Constant COMPANY_ID. */
    private static final Long COMPANY_ID = (long) 16;

    /** The Constant TWEET_ID. */
    private static final Long TWEET_ID = (long) 240923;

    /** The Constant STOCK_PRICE. */
    private static final Double STOCK_PRICE = 23.23;

    /** The company dao. */
    private static CompanyDAO companyDAO = new CompanyDAOImpl();

    /** The data source. */
    private static DriverManagerDataSource dataSource;

    /**
     * Before class initialise datasource.
     */
    @BeforeClass
    public static final void beforeClass() {
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setPassword("manch3ster");
        dataSource.setUrl(URL);
        dataSource.setUsername("twittertrader");
    }

    /**
     * Test select google.
     */
    @Test
    public final void testSelectGoogle() {
        companyDAO.setDataSource(dataSource);
        Company company = companyDAO.select(GOOGLE_ID);
        assertEquals("Google", company.getName());
        assertEquals("goog", company.getStockSymbol());
        assertEquals("$", company.getStockCurrency());
    }

    /**
     * Test.
     */
    @Test
    public final void testSelectAll() {
        companyDAO.setDataSource(dataSource);
        List<Company> companies = companyDAO.selectAll();
        assertEquals(DBUtils.TEN, companies.size());
    }

    /**
     * Test add tweet to company.
     * 
     * @throws SQLException
     *             the sQL exception
     */
    @Test
    public final void testAddTweetToCompany() throws SQLException {
        DriverManagerDataSource dataSourceMock = mock(DriverManagerDataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        Company company = mock(Company.class);
        Tweet tweet = mock(Tweet.class);

        companyDAO.setDataSource(dataSourceMock);

        when(dataSourceMock.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(ADD_COMPANY_TWEET)).thenReturn(statement);
        when(company.getId()).thenReturn(COMPANY_ID);
        when(tweet.getId()).thenReturn(TWEET_ID);

        companyDAO.addTweetToCompany(company, tweet);

        verify(statement, times(1)).setLong(1, COMPANY_ID);
        verify(statement, times(1)).setLong(2, TWEET_ID);
        verify(statement, times(1)).executeUpdate();
        verify(statement, times(1)).close();
        verify(connection, times(1)).close();
    }

    /**
     * Test add stock price.
     * 
     * @throws SQLException
     *             the sQL exception
     */
    @Test
    public final void testAddStockPrice() throws SQLException {
        DriverManagerDataSource dataSourceMock = mock(DriverManagerDataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        Company company = mock(Company.class);

        companyDAO.setDataSource(dataSourceMock);

        when(dataSourceMock.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(ADD_STOCK_PRICE)).thenReturn(statement);
        when(company.getId()).thenReturn(COMPANY_ID);
        when(company.getStockPrice()).thenReturn(STOCK_PRICE);

        companyDAO.addStockPrice(company);

        verify(statement, times(1)).setLong(1, COMPANY_ID);
        verify(statement, times(2)).setLong(anyInt(), anyLong());
        verify(statement, times(1)).setDouble(DBUtils.THREE, STOCK_PRICE);
        verify(statement, times(1)).executeUpdate();
        verify(statement, times(1)).close();
        verify(connection, times(1)).close();
    }
}
