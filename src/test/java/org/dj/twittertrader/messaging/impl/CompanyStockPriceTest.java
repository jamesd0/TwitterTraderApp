package org.dj.twittertrader.messaging.impl;

import static org.junit.Assert.assertEquals;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.dj.twittertrader.model.Company;
import org.dj.twittertrader.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class CompanyStockPriceTest.
 */
public class CompanyStockPriceTest {

    /** The csp. */
    private CompanyStockPrice csp;

    /** The company. */
    private Company company;

    /**
     * Initialises objects before each test.
     */
    @Before
    public final void setUp() {
        company = TestUtil.randomCompany();
        csp = new CompanyStockPrice(company);

    }

    /**
     * Test constructor.
     */
    @Test
    public final void testConstructor() {
        assertEquals("stockPrice", csp.getType());
        assertEquals(company.getStockSymbol(), csp.getCompanySymbol());
        assertEquals(company.getIndustry(), csp.getIndustry());
        assertEquals(company.getStockPrice(), csp.getStockPrice(), 0.0);
    }

    /**
     * Equals user.
     */
    @Test
    public final void equalsCompanyStockPrice() {
        EqualsVerifier.forClass(CompanyStockPrice.class)
                .suppress(Warning.NONFINAL_FIELDS, Warning.NULL_FIELDS).verify();
    }

}
