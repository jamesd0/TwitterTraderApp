package org.dj.twittertrader.model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Random;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.dj.twittertrader.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class TweetTest.
 */
public class TweetTest {

    private static final int USER_SCORE = 20740;

    /** The Constant TEST_INT. */
    private static final int TEST_INT = 231231;

    /** The user. */
    private Tweet tweet;

    /** The twitter user. */
    private twitter4j.Status tStatus;

    /** The t user. */
    private twitter4j.User tUser;

    /**
     * Initialises objects before each test.
     */
    @Before
    public final void setUp() {
        tweet = TestUtil.randomTweet();
        tStatus = mock(twitter4j.Status.class);
        tUser = mock(twitter4j.User.class);
    }

    /**
     * Test constructor.
     */
    @Test
    public final void testConstructor() {
        long date = new Random().nextLong();
        when(tStatus.getCreatedAt()).thenReturn(new Date(date));
        when(tStatus.getId()).thenReturn((long) TEST_INT);
        when(tStatus.getText()).thenReturn("RandomText");
        when(tStatus.getUser()).thenReturn(tUser);
        when(tStatus.getRetweetCount()).thenReturn(TEST_INT);
        when(tUser.getCreatedAt()).thenReturn(new Date(date));
        when(tUser.getFollowersCount()).thenReturn(TEST_INT);
        when(tUser.getFriendsCount()).thenReturn(TEST_INT);
        when(tUser.getId()).thenReturn((long) TEST_INT);
        when(tUser.getName()).thenReturn("FullName");
        when(tUser.getScreenName()).thenReturn("ScreenName");
        when(tUser.getFavouritesCount()).thenReturn(TEST_INT);
        when(tUser.isVerified()).thenReturn(true);
        when(tUser.getLang()).thenReturn("en");
        when(tUser.getLocation()).thenReturn("Location");
        tweet = new Tweet(tStatus);
        assertEquals(tweet.getCreatedAt(), new Date(date));
        assertEquals(tweet.getId(), TEST_INT);
        assertEquals(tweet.getRetweetCount(), TEST_INT);
        assertEquals(tweet.getText(), "RandomText");
        assertEquals(tweet.getTweetScore(), 0);
        assertEquals(tweet.getUser().getCreatedAt(), new Date(date));
        assertEquals(tweet.getUser().getFavouritesCount(), TEST_INT);
        assertEquals(tweet.getUser().getFollowersCount(), TEST_INT);
        assertEquals(tweet.getUser().getFriendsCount(), TEST_INT);
        assertEquals(tweet.getUser().getId(), TEST_INT);
        assertEquals(tweet.getUser().getLang(), "en");
        assertEquals(tweet.getUser().getLocation(), "Location");
        assertEquals(tweet.getUser().getName(), "FullName");
        assertEquals(tweet.getUser().getScreenName(), "ScreenName");
        assertEquals(tweet.getUser().isVerified(), true);
        assertEquals(tweet.getUser().isActive(), true);
        assertEquals(tweet.getUser().getUserScore(), USER_SCORE);
    }

    // /**
    // * Test json.
    // *
    // * @throws IOException
    // * Signals that an I/O exception has occurred.
    // */
    // @Test
    // public final void testJson() throws IOException {
    // String json = Tweet.toJson(tweet);
    // assertEquals(tweet, Tweet.fromJson(json));
    // }

    /**
     * Equals user.
     */
    @Test
    public final void equalsTweet() {
        EqualsVerifier.forClass(Tweet.class).suppress(Warning.NONFINAL_FIELDS, Warning.NULL_FIELDS)
                .verify();
    }

}
