package org.dj.twittertrader.controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.dj.twittertrader.service.PortfolioService;
import org.dj.twittertrader.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class PortfolioControllerTest.
 */
public class PortfolioControllerTest {

    /** The controller. */
    private PortfolioController controller;
    /** The portfolio service. */
    private PortfolioService portfolioService;

    /**
     * Setup.
     */
    @Before
    public final void setup() {
        TestUtil.randomPortfolio();
        TestUtil.randomPortfolio();
        controller = new PortfolioController();
        portfolioService = mock(PortfolioService.class);
        controller.setPortfolioService(portfolioService);
    }

    /**
     * Test.
     */
    @Test
    public final void test() {
        assertTrue(true);
    }
}
