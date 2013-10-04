package org.dj.twittertrader.controller;

import java.util.List;

import org.dj.twittertrader.model.Tweet;
import org.dj.twittertrader.service.TweetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class TweetController.
 */
@Controller
@RequestMapping("/tweet")
public class TweetController {
    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(TweetController.class);

    /** The tweet service. */
    @Autowired
    private TweetService tweetService;

    /**
     * Gets all tweets.
     * 
     * @return the all tweets
     */
    @RequestMapping(value = "/getAllTweets", method = RequestMethod.GET)
    @ResponseBody
    public final List<Tweet> getAllTweets() {
        return tweetService.selectAll();
    }

    /**
     * Delete tweet.
     * 
     * @param tweet
     *            the tweet to delete
     */
    @RequestMapping(value = "/deleteTweet", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public final void deleteTweet(@RequestBody final Tweet tweet) {
        tweetService.delete(tweet);
    }

    /**
     * Creates the tweet.
     * 
     * @param tweet
     *            the tweet
     */
    @RequestMapping(value = "/createTweet", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public final void createTweet(@RequestBody final Tweet tweet) {
        tweetService.create(tweet);
    }

    /**
     * Updates the tweet.
     * 
     * @param tweet
     *            the updated tweet object
     */
    @RequestMapping(value = "/updateTweet", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public final void updateTweet(@RequestBody final Tweet tweet) {
        tweetService.update(tweet);
    }

    /**
     * Select tweet.
     * 
     * @param id
     *            the id
     * @return the string
     */
    @RequestMapping(value = "/getTweet/{id}", method = RequestMethod.GET)
    @ResponseBody
    public final Tweet selectTweet(@PathVariable final long id) {
        return tweetService.select(id);
    }

    /**
     * Sets the tweet service.
     * 
     * @param tweetService2
     *            the new tweet service
     */
    public final void setTweetService(final TweetService tweetService2) {
        this.tweetService = tweetService2;

    }
}
