package org.dj.twittertrader.service.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.dj.twittertrader.dao.PortfolioDAO;
import org.dj.twittertrader.service.PortfolioService;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class PortfolioServiceTest.
 */
public class PortfolioServiceTest {

    /** The portfolio service. */
    private PortfolioService portfolioService;

    /** The portfolio dao. */
    private PortfolioDAO portfolioDAO;

    /**
     * Setup.
     */
    @Before
    public final void setup() {
        portfolioDAO = mock(PortfolioDAO.class);
        portfolioService = new PortfolioServiceImpl();
        portfolioService.setPortfolioDAO(portfolioDAO);
    }

    /**
     * Test.
     */
    @Test
    public final void test() {
        assertTrue(true);
    }

}
