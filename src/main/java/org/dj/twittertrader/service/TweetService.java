package org.dj.twittertrader.service;

import org.dj.twittertrader.dao.TweetDAO;
import org.dj.twittertrader.model.Tweet;

import twitter4j.Status;

/**
 * The Interface TweetService.
 */
public interface TweetService {

    /**
     * Creates the.
     * 
     * @param tweet
     *            the tweet
     */
    void create(Tweet tweet);

    /**
     * Sets the tweet dao.
     * 
     * @param tweetDAO
     *            the new tweet dao
     */
    void setTweetDAO(TweetDAO tweetDAO);

    Tweet makeTweet(Status status);

}
