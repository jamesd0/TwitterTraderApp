package org.dj.twittertrader.utils;

import static org.junit.Assert.assertEquals;

import org.dj.twittertrader.model.Tweet;
import org.junit.Test;

/**
 * The Class SortByTweetScoreTest.
 */
public class SortByTweetScoreTest {

    /** The Constant BEFORE. */
    private static final int SMALLER = 12210;

    /** The Constant AFTER. */
    private static final int BIGGER = 12234;
    /** The sorter. */
    private SortByTweetScore sorter = new SortByTweetScore();

    /**
     * Should return one when first is before second.
     */
    @Test
    public final void shouldReturnOneWhenFirstIsGreaterThanSecond() {
        Tweet tweet = new Tweet();
        tweet.setTweetScore(BIGGER);
        Tweet tweet2 = new Tweet();
        tweet2.setTweetScore(SMALLER);
        int result = sorter.compare(tweet, tweet2);
        assertEquals(1, result);
    }

    /**
     * Should return minus one when first is before second.
     */
    @Test
    public final void shouldReturnMinusOneWhenFirstIsLessThanSecond() {
        Tweet tweet = new Tweet();
        tweet.setTweetScore(SMALLER);
        Tweet tweet2 = new Tweet();
        tweet2.setTweetScore(BIGGER);
        int result = sorter.compare(tweet, tweet2);
        assertEquals(-1, result);
    }

    /**
     * Should return zero when dates are equal.
     */
    @Test
    public final void shouldReturnZeroWhenScoresAreEqual() {
        Tweet tweet = new Tweet();
        tweet.setTweetScore(BIGGER);
        Tweet tweet2 = new Tweet();
        tweet2.setTweetScore(BIGGER);
        int result = sorter.compare(tweet, tweet2);
        assertEquals(0, result);
    }
}
