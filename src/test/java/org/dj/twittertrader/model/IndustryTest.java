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
 * The Class IndustryTest.
 */
public class IndustryTest {

    /** The user. */
    private Industry industry;

    /**
     * Initialises objects before each test.
     */
    @Before
    public final void setUp() {
        industry = TestUtil.randomIndustry();
    }

    /**
     * Test json.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public final void testJson() throws IOException {
        String json = Industry.toJson(industry);
        assertEquals(industry, Industry.fromJson(json));
    }

    /**
     * Equals user.
     */
    @Test
    public final void equalsTweet() {
        EqualsVerifier.forClass(Industry.class)
                .suppress(Warning.NONFINAL_FIELDS, Warning.NULL_FIELDS).verify();
    }

    /**
     * Test get stream tokens.
     */
    @Test
    public final void testGetStreamTokens() {
        List<String> tokens = industry.getStreamTokens();
        for (Company company : industry.getCompanies()) {
            if (company.isActive()) {
                assertTrue(tokens.containsAll(company.getTags()));
                assertTrue(tokens.contains(company.getName()));
            }
        }
    }
}
