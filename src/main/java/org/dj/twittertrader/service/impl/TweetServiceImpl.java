package org.dj.twittertrader.service.impl;

import java.util.List;

import org.dj.twittertrader.dao.TweetDAO;
import org.dj.twittertrader.model.Tweet;
import org.dj.twittertrader.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public final List<Tweet> selectAll() {
        return tweetDAO.selectAll();
    }

    @Override
    @Transactional
    public final void delete(final Tweet tweet) {
        tweetDAO.delete(tweet);

    }

    @Override
    @Transactional
    public final void create(final Tweet tweet) {
        tweetDAO.create(tweet);

    }

    @Override
    @Transactional
    public final void update(final Tweet tweet) {
        tweetDAO.update(tweet);
    }

    @Override
    @Transactional
    public final Tweet select(final long id) {
        return tweetDAO.select(id);
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
}
