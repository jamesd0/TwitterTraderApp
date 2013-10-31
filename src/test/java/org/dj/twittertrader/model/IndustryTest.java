package org.dj.twittertrader.model;

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

    // /**
    // * Test json.
    // *
    // * @throws IOException
    // * Signals that an I/O exception has occurred.
    // */
    // @Test
    // public final void testJson() throws IOException {
    // String json = Industry.toJson(industry);
    // assertEquals(industry, Industry.fromJson(json));
    // }

    /**
     * Equals user.
     */
    @Test
    public final void equalsIndustry() {
        EqualsVerifier.forClass(Industry.class)
                .suppress(Warning.NONFINAL_FIELDS, Warning.NULL_FIELDS).verify();
    }
}
