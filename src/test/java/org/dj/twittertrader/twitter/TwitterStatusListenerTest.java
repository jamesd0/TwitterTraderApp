package org.dj.twittertrader.twitter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import org.dj.twittertrader.finance.FinanceDataReceiver;
import org.dj.twittertrader.messaging.MessagingBroker;
import org.dj.twittertrader.model.Company;
import org.dj.twittertrader.model.Tweet;
import org.dj.twittertrader.service.CompanyService;
import org.dj.twittertrader.service.TweetService;
import org.dj.twittertrader.service.UserService;
import org.dj.twittertrader.swn.TweetTagger;
import org.dj.twittertrader.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.User;
import twitter4j.internal.org.json.JSONException;

/**
 * The Class TwitterStatusListenerTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class TwitterStatusListenerTest {

    /** The Constant FIRST. */
    private static final long FIRST = 123123;

    /** The Constant SECOND. */
    private static final long SECOND = 123123;
    /** The listener. */
    private TwitterStatusListener listener;

    /** The broker. */
    private MessagingBroker broker;

    /** The tweet service. */
    private TweetService tweetService;

    /** The user service. */
    private UserService userService;

    /** The company service. */
    private CompanyService companyService;

    /** The logger. */
    private Logger logger;

    /** The company first. */
    private Company companyFirst;

    /** The company second. */
    private Company companySecond;

    /** The tagger. */
    private TweetTagger tagger;

    /** The finance receiver. */
    private FinanceDataReceiver financeReceiver;

    /**
     * Sets the up.
     */
    @Before
    public final void setUp() {
        listener = new TwitterStatusListener();
        broker = mock(MessagingBroker.class);
        tweetService = mock(TweetService.class);
        userService = mock(UserService.class);
        companyService = mock(CompanyService.class);
        financeReceiver = mock(FinanceDataReceiver.class);
        logger = mock(Logger.class);
        tagger = mock(TweetTagger.class);
        listener.setBroker(broker);
        listener.setTweetService(tweetService);
        listener.setUserService(userService);
        listener.setCompanyService(companyService);
        listener.setLogger(logger);
        listener.setFinanceDataReceiver(financeReceiver);
        listener.setInitialiased(false);
        listener.setTagger(tagger);
        companyFirst = TestUtil.randomCompany();
        companySecond = TestUtil.randomCompany();
    }

    /**
     * Test on exception.
     */
    @Test
    public final void testOnException() {
        Exception exception = new Exception("This is a test exception");
        listener.onException(exception);
        verify(logger, times(1)).error("Twitter Exception: This is a test exception");
    }

    /**
     * Test on deletion notice.
     */
    @Test
    public final void testOnDeletionNotice() {
        StatusDeletionNotice deletionNotice = mock(StatusDeletionNotice.class);
        when(deletionNotice.toString()).thenReturn("This is a test twitter deletion notice");
        listener.onDeletionNotice(deletionNotice);
        verify(logger, times(1)).info("Twitter notice: This is a test twitter deletion notice");
    }

    /**
     * Test on scrub geo.
     */
    @Test
    public final void testOnScrubGEO() {
        listener.onScrubGeo(FIRST, SECOND);
        verify(logger, times(1)).info("ScrubGEO: " + FIRST + ", " + SECOND);
    }

    /**
     * Test track limitation notice.
     */
    @Test
    public final void testTrackLimitationNotice() {
        listener.onTrackLimitationNotice(2);
        verify(logger, times(1)).info("TrackLimitationNotice: " + 2);
    }

    /**
     * Test on status when the status is not EN.
     */
    @Test
    public final void testOnStatusNotEN() {
        when(companyService.selectAll()).thenReturn(Arrays.asList(companyFirst, companySecond));
        Status status = mock(Status.class);
        User user = mock(User.class);
        when(status.getUser()).thenReturn(user);
        when(user.getLang()).thenReturn("NotEN");
        listener.onStatus(status);
        verify(companyService, times(1)).selectAll();
        assertEquals(listener.isInitialiased(), true);
        verifyNoMoreInteractions(logger);
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(tweetService);
        verifyNoMoreInteractions(broker);
    }

    /**
     * Test on status.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws JSONException
     */
    @Test
    public final void testOnStatus() throws IOException, JSONException {
        when(companyService.selectAll()).thenReturn(Arrays.asList(companyFirst, companySecond));
        when(financeReceiver.getStockPrice(any(String.class))).thenReturn(100.10);
        when(tagger.getTweetScore(any(Tweet.class))).thenReturn((double) 233.21);
        Status status = mock(Status.class);
        User user = mock(User.class);
        when(status.getUser()).thenReturn(user);
        when(status.getId()).thenReturn((long) 21231);
        when(status.getCreatedAt()).thenReturn(new Date(123123));
        when(status.getText()).thenReturn("TestText");
        when(user.getLang()).thenReturn("en");
        when(status.getRetweetCount()).thenReturn(1234123);
        when(user.getFollowersCount()).thenReturn(123);
        when(user.getFriendsCount()).thenReturn(12312);
        when(user.getId()).thenReturn((long) 123123);
        when(user.getName()).thenReturn("Name");
        when(user.getScreenName()).thenReturn("ScreenName");
        when(user.getFavouritesCount()).thenReturn(12);
        when(user.isVerified()).thenReturn(true);
        when(user.getCreatedAt()).thenReturn(new Date(123123));
        when(user.getLocation()).thenReturn("Location");
        Tweet predictedResultTweet = new Tweet(status);
        predictedResultTweet.setTweetScore(23321);
        companyFirst.setName("NotMatch");
        companySecond.setName("TestText");
        companySecond.setActive(true);
        listener.onStatus(status);

        verify(logger, times(1)).info(status.getText());
        verify(companyService, times(1)).selectAll();
        verify(userService, times(1)).create(new org.dj.twittertrader.model.User(status.getUser()));
        verify(tweetService, times(1)).create(predictedResultTweet);
        verify(broker, times(1)).upload(Tweet.toJson(predictedResultTweet).getBytes());
        assertEquals(listener.isInitialiased(), true);
        verifyNoMoreInteractions(logger);
        verifyNoMoreInteractions(userService);
        verifyNoMoreInteractions(tweetService);
        verifyNoMoreInteractions(broker);
    }
}
