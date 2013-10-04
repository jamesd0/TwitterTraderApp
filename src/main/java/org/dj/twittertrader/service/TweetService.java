package org.dj.twittertrader.service;

import java.util.List;

import org.dj.twittertrader.dao.TweetDAO;
import org.dj.twittertrader.model.Tweet;

/**
 * The Interface TweetService.
 */
public interface TweetService {

    /**
     * Select all.
     * 
     * @return the list
     */
    List<Tweet> selectAll();

    /**
     * Delete.
     * 
     * @param tweet
     *            the tweet
     */
    void delete(Tweet tweet);

    /**
     * Creates the.
     * 
     * @param tweet
     *            the tweet
     */
    void create(Tweet tweet);

    /**
     * Update.
     * 
     * @param tweet
     *            the tweet
     */
    void update(Tweet tweet);

    /**
     * Select.
     * 
     * @param id
     *            the id
     * @return the tweet
     */
    Tweet select(long id);

    /**
     * Sets the tweet dao.
     * 
     * @param tweetDAO
     *            the new tweet dao
     */
    void setTweetDAO(TweetDAO tweetDAO);

}
