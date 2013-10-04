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
 * The Class CompanyTest.
 */
public class CompanyTest {

    /** The user. */
    private Company company;

    /**
     * Initialises objects before each test.
     */
    @Before
    public final void setUp() {
        company = TestUtil.randomCompany();
    }

    /**
     * Test json.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public final void testJson() throws IOException {
        String json = Company.toJson(company);
        assertEquals(company, Company.fromJson(json));
    }

    /**
     * Test get stream tokens.
     */
    @Test
    public final void testGetStreamTokens() {
        List<String> tokens = company.getStreamTokens();
        if (company.isActive()) {
            assertTrue(tokens.containsAll(company.getTags()));
            assertTrue(tokens.contains(company.getName()));
        }
    }

    /**
     * Equals user.
     */
    @Test
    public final void equalsTweet() {
        EqualsVerifier.forClass(Company.class)
                .suppress(Warning.NONFINAL_FIELDS, Warning.NULL_FIELDS).verify();
    }
}
