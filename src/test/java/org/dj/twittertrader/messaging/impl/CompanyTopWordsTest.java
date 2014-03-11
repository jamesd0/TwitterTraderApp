package org.dj.twittertrader.messaging.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.dj.twittertrader.model.Company;
import org.dj.twittertrader.model.Tweet;
import org.dj.twittertrader.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class CompanyTopWordsTest.
 */
public class CompanyTopWordsTest {

    /** The csp. */
    private CompanyTopWords csp;

    /** The company. */
    private Company company;

    /** The tweet. */
    private Tweet tweet;

    /** The list. */
    private List<Map<String, Double>> list;

    /**
     * Initialises objects before each test.
     */
    @Before
    public final void setUp() {
        company = TestUtil.randomCompany();
        tweet = TestUtil.randomTweet();
        list = new ArrayList<Map<String, Double>>();
        csp = new CompanyTopWords(company, list);

    }

    /**
     * Test constructor.
     */
    @Test
    public final void testConstructor() {
        assertEquals("topWords", csp.getType());
        assertEquals(company.getStockSymbol(), csp.getCompanySymbol());
        assertEquals(company.getIndustry(), csp.getIndustry());
        assertEquals(list, csp.getTopWords());
    }

    /**
     * Equals user.
     */
    @Test
    public final void equalsCompanyTopWords() {
        EqualsVerifier.forClass(CompanyTopWords.class)
                .suppress(Warning.NONFINAL_FIELDS, Warning.NULL_FIELDS).verify();
    }

}
