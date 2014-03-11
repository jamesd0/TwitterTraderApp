package org.dj.twittertrader.swn;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * The Class SentiStrengthTest.
 */
public class SentimentClassifierTest {

    /** The classifier. */
    private SentimentClassifier classifier = new SentimentClassifier();

    /**
     * Should return positive when positive text.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void shouldReturnPositiveWhenPositiveText() throws Exception {
        double result = classifier.classify("Facebook is good! :)");
        assertTrue(result > 0);
    }

    /**
     * Should return negative when negative text.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void shouldReturnNegativeWhenNegativeText() throws Exception {
        double result = classifier.classify("Facebook is so bad! :(");
        assertTrue(result < 0);
    }

}
