package org.dj.twittertrader.messaging.impl;

import static org.junit.Assert.assertEquals;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.dj.twittertrader.model.Company;
import org.dj.twittertrader.model.Tweet;
import org.dj.twittertrader.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class CompanyTweetTest.
 */
public class CompanyTweetTest {

    /** The csp. */
    private CompanyTweet csp;

    /** The company. */
    private Company company;

    /** The tweet. */
    private Tweet tweet;

    /**
     * Initialises objects before each test.
     */
    @Before
    public final void setUp() {
        company = TestUtil.randomCompany();
        tweet = TestUtil.randomTweet();
        csp = new CompanyTweet(company, tweet);

    }

    /**
     * Test constructor.
     */
    @Test
    public final void testConstructor() {
        assertEquals("companyTweet", csp.getType());
        assertEquals(company.getStockSymbol(), csp.getCompanySymbol());
        assertEquals(company.getIndustry(), csp.getIndustry());
        assertEquals(tweet.getTweetScore(), csp.getTweetScore(), 0.0);
    }

    /**
     * Equals user.
     */
    @Test
    public final void equalsCompanyTweet() {
        EqualsVerifier.forClass(CompanyTweet.class)
                .suppress(Warning.NONFINAL_FIELDS, Warning.NULL_FIELDS).verify();
    }

}
