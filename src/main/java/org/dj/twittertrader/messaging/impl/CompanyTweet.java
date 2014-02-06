package org.dj.twittertrader.messaging.impl;

import org.dj.twittertrader.model.Company;
import org.dj.twittertrader.model.Tweet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class CompanyTweet.
 */
public class CompanyTweet {

    /** The type. */
    private String type = "companyTweet";

    /** The company symbol. */
    private String companySymbol;

    /** The stock price. */
    private double tweetScore;

    /**
     * Instantiates a new company stock price.
     * 
     * @param company
     *            the company
     * @param tweet
     *            the tweet
     */
    public CompanyTweet(final Company company, final Tweet tweet) {
        this.companySymbol = company.getStockSymbol();
        this.tweetScore = tweet.getTweetScore();
    }

    /**
     * Gets the broker message.
     * 
     * @param ct
     *            the ct
     * @return the broker message
     * @throws JsonProcessingException
     *             the json processing exception
     */
    public static final byte[] getBrokerMessage(final CompanyTweet ct)
            throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(ct).getBytes();
    }

    /**
     * Gets the type.
     * 
     * @return the type
     */
    public final String getType() {
        return type;
    }

    /**
     * Sets the type.
     * 
     * @param type
     *            the new type
     */
    public final void setType(final String type) {
        this.type = type;
    }

    /**
     * Gets the company symbol.
     * 
     * @return the company symbol
     */
    public final String getCompanySymbol() {
        return companySymbol;
    }

    /**
     * Sets the company symbol.
     * 
     * @param companySymbol
     *            the new company symbol
     */
    public final void setCompanySymbol(final String companySymbol) {
        this.companySymbol = companySymbol;
    }

    /**
     * Gets the tweet score.
     * 
     * @return the tweet score
     */
    public final double getTweetScore() {
        return tweetScore;
    }

    /**
     * Sets the tweet score.
     * 
     * @param tweetScore
     *            the new tweet score
     */
    public final void setTweetScore(final double tweetScore) {
        this.tweetScore = tweetScore;
    }

}
