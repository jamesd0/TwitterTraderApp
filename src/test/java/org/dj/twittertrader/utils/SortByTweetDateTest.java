package org.dj.twittertrader.utils;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.dj.twittertrader.model.Tweet;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class SortByTweetDateTest.
 */
public class SortByTweetDateTest {

    /** The Constant BEFORE. */
    private static final int BEFORE = 12210;

    /** The Constant AFTER. */
    private static final int AFTER = 12234;
    /** The sorter. */
    private SortByTweetDate sorter = new SortByTweetDate();

    /**
     * Should return one when first is before second.
     */
    @Test
    public final void shouldReturnOneWhenFirstIsAfterSecond() {
        Tweet tweet = new Tweet();
        tweet.setCreatedAt(new Date(AFTER));
        Tweet tweet2 = new Tweet();
        tweet2.setCreatedAt(new Date(BEFORE));
        int result = sorter.compare(tweet, tweet2);
        assertEquals(1, result);
    }

    /**
     * Should return minus one when first is before second.
     */
    @Test
    public final void shouldReturnMinusOneWhenFirstIsBeforeSecond() {
        Tweet tweet = new Tweet();
        tweet.setCreatedAt(new Date(BEFORE));
        Tweet tweet2 = new Tweet();
        tweet2.setCreatedAt(new Date(AFTER));
        int result = sorter.compare(tweet, tweet2);
        assertEquals(-1, result);
    }

    /**
     * Should return zero when dates are equal.
     */
    @Test
    public final void shouldReturnZeroWhenDatesAreEqual() {
        Tweet tweet = new Tweet();
        tweet.setCreatedAt(new Date(BEFORE));
        Tweet tweet2 = new Tweet();
        tweet2.setCreatedAt(new Date(BEFORE));
        int result = sorter.compare(tweet, tweet2);
        assertEquals(0, result);
    }
}
