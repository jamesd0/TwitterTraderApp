package org.dj.twittertrader.service.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.dj.twittertrader.dao.TweetDAO;
import org.dj.twittertrader.model.Tweet;
import org.dj.twittertrader.service.TweetService;
import org.dj.twittertrader.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class TweetServiceTest.
 */
public class TweetServiceTest {

    /** The tweet service. */
    private TweetService tweetService;

    /** The tweet dao. */
    private TweetDAO tweetDAO;

    /** The first. */
    private Tweet first;

    /** The second. */
    private Tweet second;

    /**
     * Setup.
     */
    @Before
    public final void setup() {
        first = TestUtil.randomTweet();
        second = TestUtil.randomTweet();
        tweetDAO = mock(TweetDAO.class);
        tweetService = new TweetServiceImpl();
        tweetService.setTweetDAO(tweetDAO);
    }

    /**
     * Test create.
     */
    @Test
    public final void testCreate() {
        tweetService.create(first);
        verify(tweetDAO, times(1)).create(first);
        verifyNoMoreInteractions(tweetDAO);
    }

}
