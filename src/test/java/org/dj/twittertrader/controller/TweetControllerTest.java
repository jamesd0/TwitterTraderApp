package org.dj.twittertrader.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;

import org.dj.twittertrader.model.Tweet;
import org.dj.twittertrader.service.TweetService;
import org.dj.twittertrader.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;

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
    }

    /**
     * Test for valid getAllTweets request.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void getAllTweetsTest() throws Exception {
        when(tweetService.selectAll()).thenReturn(Arrays.asList(first, second));
        standaloneSetup(controller).build().perform(get("/tweet/getAllTweets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(first.getId())))
                .andExpect(jsonPath("$[0].createdAt", is((int) first.getCreatedAt().getTime())))
                .andExpect(jsonPath("$[1].id", is(second.getId())))
                .andExpect(jsonPath("$[1].createdAt", is((int) second.getCreatedAt().getTime())));
        verify(tweetService, times(1)).selectAll();
        verifyNoMoreInteractions(tweetService);
    }

    /**
     * Test for valid getTweet request.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void getTweetSuccess() throws Exception {
        when(tweetService.select(1)).thenReturn(first);
        standaloneSetup(controller).build().perform(get("/tweet/getTweet/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id", is(first.getId())))
                .andExpect(jsonPath("createdAt", is((int) first.getCreatedAt().getTime())));
        verify(tweetService, times(1)).select(1);
        verifyNoMoreInteractions(tweetService);
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

    /**
     * Test for valid updateTweet request.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void updateTweetSuccess() throws Exception {
        standaloneSetup(controller)
                .build()
                .perform(
                        put("/tweet/updateTweet").contentType(TestUtil.APPLICATION_JSON_UTF8)
                                .content(Tweet.toJson(first))).andExpect(status().isNoContent());
        verify(tweetService, times(1)).update(first);
        verifyNoMoreInteractions(tweetService);
    }

    /**
     * Test for valid deleteTweet request.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void deleteTweetSuccess() throws Exception {
        System.out.println(Tweet.toJson(first));
        standaloneSetup(controller)
                .build()
                .perform(
                        delete("/tweet/deleteTweet").contentType(TestUtil.APPLICATION_JSON_UTF8)
                                .content(Tweet.toJson(first))).andExpect(status().isNoContent());
        verify(tweetService, times(1)).delete(first);
        verifyNoMoreInteractions(tweetService);
    }

}
