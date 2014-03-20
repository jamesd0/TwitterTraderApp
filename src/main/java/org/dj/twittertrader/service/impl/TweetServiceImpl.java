package org.dj.twittertrader.service.impl;

import org.dj.twittertrader.dao.TweetDAO;
import org.dj.twittertrader.model.Tweet;
import org.dj.twittertrader.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import twitter4j.Status;

/**
 * The Class TweetServiceImpl.
 */
@Service
public class TweetServiceImpl implements TweetService {

    /** The tweet dao. */
    @Autowired
    private TweetDAO tweetDAO;

    @Override
    @Transactional
    public final void create(final Tweet tweet) {
        tweetDAO.create(tweet);

    }

    /**
     * Gets the tweet dao.
     * 
     * @return the tweetDAO
     */
    public final TweetDAO getTweetDAO() {
        return tweetDAO;
    }

    /**
     * Sets the tweet dao.
     * 
     * @param tweetDAO
     *            the tweetDAO to set
     */
    public final void setTweetDAO(final TweetDAO tweetDAO) {
        this.tweetDAO = tweetDAO;
    }

    @Override
    public Tweet makeTweet(final Status status) {
        return new Tweet(status);
    }
}
