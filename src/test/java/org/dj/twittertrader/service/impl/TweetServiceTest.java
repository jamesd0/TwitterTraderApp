package org.dj.twittertrader.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

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
     * Test select all.
     */
    @Test
    public final void testSelectAll() {
        when(tweetDAO.selectAll()).thenReturn(Arrays.asList(first, second));
        List<Tweet> result = tweetService.selectAll();
        assert (result.containsAll(Arrays.asList(first, second)));
        verify(tweetDAO, times(1)).selectAll();
        verifyNoMoreInteractions(tweetDAO);
    }

    /**
     * Test select.
     */
    @Test
    public final void testSelect() {
        when(tweetDAO.select(0)).thenReturn(first);
        Tweet result = tweetService.select(0);
        assertEquals(result, first);
        verify(tweetDAO, times(1)).select(0);
        verifyNoMoreInteractions(tweetDAO);
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

    /**
     * Test delete.
     */
    @Test
    public final void testDelete() {
        tweetService.delete(first);
        verify(tweetDAO, times(1)).delete(first);
        verifyNoMoreInteractions(tweetDAO);
    }

    /**
     * Test update.
     */
    @Test
    public final void testUpdate() {
        tweetService.update(first);
        verify(tweetDAO, times(1)).update(first);
        verifyNoMoreInteractions(tweetDAO);
    }

}
