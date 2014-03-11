package org.dj.twittertrader.twitter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.dj.twittertrader.finance.FinanceDataReceiver;
import org.dj.twittertrader.messaging.MessagingBroker;
import org.dj.twittertrader.messaging.impl.CompanyStockPrice;
import org.dj.twittertrader.model.Company;
import org.dj.twittertrader.model.Tweet;
import org.dj.twittertrader.service.CompanyService;
import org.dj.twittertrader.service.TweetService;
import org.dj.twittertrader.service.UserService;
import org.dj.twittertrader.swn.TweetTagger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.User;

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

    /** The google. */
    private Company company1;

    /** The company apple. */
    private Company company2;

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
        company1 = mock(Company.class);
        company2 = mock(Company.class);
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
        when(companyService.selectAll()).thenReturn(Arrays.asList(company1, company2));
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
     * @throws Exception
     *             the exception
     */
    @Test
    public final void testOnStatusNoStockLookup() throws Exception {
        User user = mock(User.class);
        Status status = mock(Status.class);
        Company google = mock(Company.class);
        Company apple = mock(Company.class);
        Tweet tweet = mock(Tweet.class);
        org.dj.twittertrader.model.User trUser = mock(org.dj.twittertrader.model.User.class);

        listener.setInitialiased(true);
        listener.setCompanies(Arrays.asList(google, apple));

        when(status.getUser()).thenReturn(user);
        when(user.getLang()).thenReturn("en");
        when(google.getStreamTokens()).thenReturn(Arrays.asList("$goog", "Google"));
        when(apple.getStreamTokens()).thenReturn(Arrays.asList("$appl", "Apple"));
        when(tagger.dealWithNewStatus(status, apple)).thenReturn(tweet);
        when(status.getText()).thenReturn("Apple");
        when(tweet.getUser()).thenReturn(trUser);

        listener.onStatus(status);

        verify(tagger, times(1)).dealWithNewStatus(status, company2);
        verify(userService, times(1)).create(trUser);
        verify(tweetService, times(1)).create(tweet);
        verify(companyService, times(1)).addTweetToCompany(apple, tweet);
    }

    /**
     * Test on status with stock lookup.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void testOnStatusWithStockLookup() throws Exception {
        User user = mock(User.class);
        Status status = mock(Status.class);
        Company google = mock(Company.class);
        Company apple = mock(Company.class);
        Tweet tweet = mock(Tweet.class);
        org.dj.twittertrader.model.User trUser = mock(org.dj.twittertrader.model.User.class);

        listener.setInitialiased(true);
        listener.setCompanies(Arrays.asList(google, apple));
        listener.setLastStockTime(System.currentTimeMillis() - 30000);

        when(status.getUser()).thenReturn(user);
        when(user.getLang()).thenReturn("en");
        when(google.getStreamTokens()).thenReturn(Arrays.asList("$goog", "Google"));
        when(google.getStockSymbol()).thenReturn("goog");
        when(google.getStockPrice()).thenReturn(101.10);
        when(google.getIndustry()).thenReturn("tech");
        when(apple.getStreamTokens()).thenReturn(Arrays.asList("$appl", "Apple"));
        when(apple.getStockSymbol()).thenReturn("aapl");
        when(apple.getStockPrice()).thenReturn(1022.10);
        when(apple.getIndustry()).thenReturn("tech");
        when(tagger.dealWithNewStatus(status, apple)).thenReturn(tweet);
        when(status.getText()).thenReturn("Apple");
        when(tweet.getUser()).thenReturn(trUser);

        listener.onStatus(status);

        verify(tagger, times(1)).dealWithNewStatus(status, company2);
        verify(userService, times(1)).create(trUser);
        verify(tweetService, times(1)).create(tweet);
        verify(companyService, times(1)).addTweetToCompany(apple, tweet);
        verify(financeReceiver, times(1)).updateStockPrice(Arrays.asList(google, apple));

        verify(companyService, times(2)).addStockPrice(google);
        // verify(companyService, times(1)).addStockPrice(apple);

        verify(broker, times(1)).upload(CompanyStockPrice.getBrokerMessageIndividual(google));
        verify(broker, times(1)).upload(CompanyStockPrice.getBrokerMessageIndividual(apple));
    }
}
