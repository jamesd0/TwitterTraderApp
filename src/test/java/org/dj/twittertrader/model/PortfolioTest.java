package org.dj.twittertrader.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

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

    /**
     * Test json.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public final void testJson() throws IOException {
        String json = Portfolio.toJson(portfolio);
        assertEquals(portfolio, Portfolio.fromJson(json));
    }

    /**
     * Equals user.
     */
    @Test
    public final void equalsTweet() {
        EqualsVerifier.forClass(Portfolio.class)
                .suppress(Warning.NONFINAL_FIELDS, Warning.NULL_FIELDS).verify();
    }

    /**
     * Test get all stream tokens.
     */
    @Test
    public final void testGetAllStreamTokens() {
        List<String> tokens = portfolio.getAllStreamTokens();
        for (Company company : portfolio.getCompanies()) {
            if (company.isActive()) {
                assertTrue(tokens.containsAll(company.getTags()));
                assertTrue(tokens.contains(company.getName()));
            }
        }
        for (Industry industry : portfolio.getIndustries()) {
            for (Company company : industry.getCompanies()) {
                if (company.isActive()) {
                    assertTrue(tokens.containsAll(company.getTags()));
                    assertTrue(tokens.contains(company.getName()));
                }
            }
        }
    }
}
