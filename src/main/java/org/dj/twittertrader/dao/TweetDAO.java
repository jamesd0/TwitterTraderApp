package org.dj.twittertrader.dao;

import javax.sql.DataSource;

import org.dj.twittertrader.model.Tweet;

/**
 * The Interface TweetDAO.
 */
public interface TweetDAO {

    /**
     * Creates the.
     * 
     * @param tweet
     *            the tweet
     */
    void create(Tweet tweet);

    /**
     * Sets the data source.
     * 
     * @param dataSource
     *            the new data source
     */
    void setDataSource(DataSource dataSource);

}
