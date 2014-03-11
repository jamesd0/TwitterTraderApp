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

    /** The industry. */
    private String industry;

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
        this.industry = company.getIndustry();
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

    /**
     * Gets the industry.
     * 
     * @return the industry
     */
    public final String getIndustry() {
        return industry;
    }

    /**
     * Sets the industry.
     * 
     * @param industry
     *            the new industry
     */
    public final void setIndustry(final String industry) {
        this.industry = industry;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((companySymbol == null) ? 0 : companySymbol.hashCode());
        result = prime * result + ((industry == null) ? 0 : industry.hashCode());
        long temp;
        temp = Double.doubleToLongBits(tweetScore);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof CompanyTweet)) {
            return false;
        }
        CompanyTweet other = (CompanyTweet) obj;
        if (companySymbol == null) {
            if (other.companySymbol != null) {
                return false;
            }
        } else if (!companySymbol.equals(other.companySymbol)) {
            return false;
        }
        if (industry == null) {
            if (other.industry != null) {
                return false;
            }
        } else if (!industry.equals(other.industry)) {
            return false;
        }
        if (Double.doubleToLongBits(tweetScore) != Double.doubleToLongBits(other.tweetScore)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }

}
