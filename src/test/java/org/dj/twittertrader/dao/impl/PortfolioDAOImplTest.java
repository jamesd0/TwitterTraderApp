package org.dj.twittertrader.dao.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.dj.twittertrader.dao.PortfolioDAO;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * The Class PortfolioDAOImplTest.
 */
public class PortfolioDAOImplTest {
    /** The data source. */
    private static DriverManagerDataSource dataSource;
    /** The Constant URL. */
    private static final String URL = "jdbc:mysql://localhost:3306/twittertrader?"
            + "zeroDateTimeBehavior=convertToNull";

    /** The portfolio dao. */
    private PortfolioDAO portfolioDAO = new PortfolioDAOImpl();

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
     * Test stream tokens.
     */
    @Test
    public final void testStreamTokens() {
        portfolioDAO.setDataSource(dataSource);

        List<String> list = portfolioDAO.getStreamTokens(3);
        assertTrue(list.contains("$goog"));
        assertTrue(list.contains("Google"));
    }
}
