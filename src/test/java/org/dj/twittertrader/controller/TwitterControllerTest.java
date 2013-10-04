package org.dj.twittertrader.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.dj.twittertrader.model.Portfolio;
import org.dj.twittertrader.service.PortfolioService;
import org.dj.twittertrader.twitter.TwitterStatusListener;
import org.dj.twittertrader.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;

import twitter4j.FilterQuery;
import twitter4j.TwitterStream;

/**
 * The Class TwitterControllerTest.
 */
public class TwitterControllerTest {

    /** The controller. */
    private TwitterController controller;
    /** The portfolio service. */
    private PortfolioService portfolioService;

    /** The listener. */
    private TwitterStatusListener listener;
    /** The stream. */
    private TwitterStream stream;

    /** The first. */
    private Portfolio first;

    /**
     * Setup.
     */
    @Before
    public final void setup() {
        first = TestUtil.randomPortfolio();
        controller = new TwitterController();
        portfolioService = mock(PortfolioService.class);
        stream = mock(TwitterStream.class);
        listener = mock(TwitterStatusListener.class);
        controller.setStream(stream);
        controller.setListener(listener);
        controller.setPortfolioService(portfolioService);
    }

    /**
     * Start stream test.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void startStreamTest() throws Exception {
        when(portfolioService.select(2)).thenReturn(first);
        standaloneSetup(controller).build().perform(get("/twitter/start/2"))
                .andExpect(status().isOk());
        verify(stream, times(1)).shutdown();
        verify(stream, times(1)).addListener(listener);
        verify(portfolioService, times(1)).select(2);
        verify(stream, times(1)).filter(
                new FilterQuery(0, new long[0], first.getAllStreamTokens().toArray(
                        new String[first.getAllStreamTokens().size()])));
        verifyNoMoreInteractions(portfolioService);
        verifyNoMoreInteractions(stream);
        verifyNoMoreInteractions(listener);
    }

    /**
     * Stop stream test.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void stopStreamTest() throws Exception {
        standaloneSetup(controller).build().perform(get("/twitter/stop"))
                .andExpect(status().isOk());
        verify(stream, times(1)).shutdown();
        verifyNoMoreInteractions(portfolioService);
        verifyNoMoreInteractions(stream);
        verifyNoMoreInteractions(listener);
    }
}
