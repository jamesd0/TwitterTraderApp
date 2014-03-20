package org.dj.twittertrader.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.dj.twittertrader.model.Tweet;
import org.dj.twittertrader.service.TweetService;
import org.dj.twittertrader.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class TweetControllerTest.
 */
public class TweetControllerTest {

    /** The controller. */
    private TweetController controller;
    /** The tweet service. */
    private TweetService tweetService;

    /** The first. */
    private Tweet first;
    private ObjectMapper mapper;
    /** The second. */
    private Tweet second;

    /**
     * Setup.
     */
    @Before
    public final void setup() {
        first = TestUtil.randomTweet();
        second = TestUtil.randomTweet();
        controller = new TweetController();
        tweetService = mock(TweetService.class);
        controller.setTweetService(tweetService);
        mapper = new ObjectMapper().setDateFormat(new SimpleDateFormat("HH:mm:ss,dd/MM"))
                .setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    /**
     * Test for valid createTweet request.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void createTweetSuccess() throws Exception {
        standaloneSetup(controller)
                .build()
                .perform(
                        post("/tweet/createTweet").contentType(TestUtil.APPLICATION_JSON_UTF8)
                                .content(Tweet.toJson(first))).andExpect(status().isCreated());
        verify(tweetService, times(1)).create(first);
        verifyNoMoreInteractions(tweetService);
    }
}
