package org.dj.twittertrader.controller;

import org.dj.twittertrader.model.Tweet;
import org.dj.twittertrader.service.TweetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
     * Sets the tweet service.
     * 
     * @param tweetService2
     *            the new tweet service
     */
    public final void setTweetService(final TweetService tweetService2) {
        this.tweetService = tweetService2;

    }
}
