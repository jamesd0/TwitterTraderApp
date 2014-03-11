package org.dj.twittertrader.swn;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * The Class SentiWordNetTest.
 */
public class SentiWordNetTest {

    /** The Constant DELTA. */
    private static final double DELTA = 1e-15;

    /** The Constant AMAZING_ADJ. */
    private static final double AMAZING_ADJ = 0.27272727272727276;
    /** The senti word net. */
    private SentiWordNet sentiWordNet;

    /**
     * Sets the up.
     * 
     * @throws Exception
     *             the exception
     */
    @Before
    public final void setUp() throws Exception {
        sentiWordNet = new SentiWordNet();
    }

    /**
     * Test amazing adj.
     */
    @Test
    public final void testAmazingAdj() {
        Double score = sentiWordNet.extract("amazing", "a");
        assertEquals(score, AMAZING_ADJ, DELTA);
    }

    /**
     * Test amazing ver.
     */
    @Test
    public final void testAmazingVer() {
        Double score = sentiWordNet.extract("amazing", "v");
        assertEquals(score, 0.0, DELTA);
    }

}
