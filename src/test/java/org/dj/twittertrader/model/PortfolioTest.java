package org.dj.twittertrader.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.dj.twittertrader.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class PortfolioTest.
 */
public class PortfolioTest {

    /** The user. */
    private Portfolio portfolio;

    /**
     * Initialises objects before each test.
     */
    @Before
    public final void setUp() {
        portfolio = TestUtil.randomPortfolio();
    }

    //
    // /**
    // * Test json.
    // *
    // * @throws IOException
    // * Signals that an I/O exception has occurred.
    // */
    // @Test
    // public final void testJson() throws IOException {
    // String json = Portfolio.toJson(portfolio);
    // assertEquals(portfolio, Portfolio.fromJson(json));
    // }

    /**
     * Equals user.
     */
    @Test
    public final void equalsTweet() {
        EqualsVerifier.forClass(Portfolio.class)
                .suppress(Warning.NONFINAL_FIELDS, Warning.NULL_FIELDS).verify();
    }
}
