package org.dj.twittertrader.controller;

import java.util.List;

import org.dj.twittertrader.model.Tweet;
import org.dj.twittertrader.service.TweetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
     * @return the string
     */
    @RequestMapping(value = "/deleteTweet", method = RequestMethod.DELETE)
    @ResponseBody
    public final String deleteTweet(@RequestBody final Tweet tweet) {
        tweetService.delete(tweet);
        return "Tweet deleted with id: " + tweet.getId();
    }

    /**
     * Creates the tweet.
     * 
     * @param tweet
     *            the tweet
     * @return the string
     */
    @RequestMapping(value = "/createTweet", method = RequestMethod.POST)
    @ResponseBody
    public final String createTweet(@RequestBody final Tweet tweet) {
        tweetService.create(tweet);
        return "Tweet created successfully";
    }

    /**
     * Updates the tweet.
     * 
     * @param tweet
     *            the updated tweet object
     * @return the string
     */
    @RequestMapping(value = "/updateTweet", method = RequestMethod.PUT)
    @ResponseBody
    public final String updateTweet(@RequestBody final Tweet tweet) {
        tweetService.update(tweet);
        return "Tweet updated successfully";
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
}
