package org.dj.twittertrader.dao;

import java.util.List;

import org.dj.twittertrader.model.Tweet;

/**
 * The Interface TweetDAO.
 */
public interface TweetDAO {

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

}
