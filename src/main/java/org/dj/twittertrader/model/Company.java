package org.dj.twittertrader.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class Company is an entity.
 */
public class Company {

    /** The id. */
    private long id;

    /** The name. */
    private String name;

    /** The description. */
    private String description;

    /** The tweets. */
    private List<Tweet> tweets;

    /** The stock symbol. */
    private String stockSymbol;

    /** The stock currency. */
    private String stockCurrency;
    /** The stock price. */
    private double stockPrice;

    /** The industry. */
    private String industry;

    /**
     * The tags. A tag is a word that is associated with the company example for CocaCola coke would
     * be a tag
     */
    private List<String> tags;

    /** The company score. */
    private long companyScore;

    /** The active. */
    private boolean active;

    /**
     * Instantiates a new company.
     */
    public Company() {
        super();
    }

    /**
     * Gets the tweets today.
     * 
     * @return the tweets today
     */
    public final int getTweetsWeek() {
        Calendar startOfWeek = Calendar.getInstance();
        startOfWeek.set(Calendar.HOUR_OF_DAY, 0);
        startOfWeek.set(Calendar.MINUTE, 0);
        startOfWeek.set(Calendar.SECOND, 0);
        startOfWeek.add(Calendar.DATE, ((startOfWeek.get(Calendar.DAY_OF_WEEK) - 1) * -1));
        int count = 0;
        for (Tweet t : tweets) {
            if (t.getCreatedAt().after(startOfWeek.getTime())) {
                count++;
            }
        }
        return count;
    }

    /**
     * Gets the tweets today.
     * 
     * @return the tweets today
     */
    public final int getTweetsToday() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        int count = 0;
        for (Tweet t : tweets) {
            if (t.getCreatedAt().after(today.getTime())) {
                count++;
            }
        }
        return count;
    }

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     * 
     * @param id
     *            the new id
     */
    public final void setId(final long id) {
        this.id = id;
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param name
     *            the new name
     */
    public final void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets the description.
     * 
     * @return the description
     */
    public final String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * 
     * @param description
     *            the new description
     */
    public final void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Gets the tweets.
     * 
     * @return the tweets
     */
    public final List<Tweet> getTweets() {
        return tweets;
    }

    /**
     * Sets the tweets.
     * 
     * @param tweets
     *            the new tweets
     */
    public final void setTweets(final List<Tweet> tweets) {
        this.tweets = tweets;
    }

    /**
     * Gets the stock price.
     * 
     * @return the stock price
     */
    public double getStockPrice() {
        return stockPrice;
    }

    /**
     * Sets the stock price.
     * 
     * @param stockPrice
     *            the new stock price
     */
    public final void setStockPrice(final double stockPrice) {
        this.stockPrice = stockPrice;
    }

    /**
     * Gets the tags.
     * 
     * @return the tags
     */
    public final List<String> getTags() {
        return tags;
    }

    /**
     * Sets the tags.
     * 
     * @param tags
     *            the tags to set
     */
    public final void setTags(final List<String> tags) {
        this.tags = tags;
    }

    /**
     * Gets the company score.
     * 
     * @return the company score
     */
    public long getCompanyScore() {
        return companyScore;
    }

    /**
     * Sets the company score.
     * 
     * @param score
     *            the new company score
     */
    public final void setCompanyScore(final long score) {
        this.companyScore = score;
    }

    /**
     * Checks if is active.
     * 
     * @return true, if is active
     */
    public final boolean isActive() {
        return active;
    }

    /**
     * Sets the active.
     * 
     * @param active
     *            the new active
     */
    public final void setActive(final boolean active) {
        this.active = active;
    }

    /**
     * Gets the latest tweets.
     * 
     * @return the latestTweets
     */
    public final List<Tweet> getLatestTweets() {
        return Tweet.getLatestTweets(tweets);
    }

    /**
     * Gets the most influential tweets.
     * 
     * @return the mostInfluentialTweets
     */
    public final List<Tweet> getMostInfluentialTweets() {
        return Tweet.getMostInfluentialTweets(tweets);
    }

    /**
     * Gets the most detrimental tweets.
     * 
     * @return the most detrimental tweets
     */
    public final List<Tweet> getMostDetrimentalTweets() {
        return Tweet.getMostDetrimentalTweets(tweets);
    }

    /**
     * Gets the stock symbol.
     * 
     * @return the stock symbol
     */
    public String getStockSymbol() {
        if (stockSymbol.contains(".")) {
            return stockSymbol.split("\\.")[0];
        } else {
            return stockSymbol;
        }
    }

    /**
     * Gets the stock symbol not abreviated.
     * 
     * @return the stock symbol not abreviated
     */
    public final String getStockSymbolNotAbreviated() {
        return stockSymbol;
    }

    /**
     * Sets the stock symbol.
     * 
     * @param stockSymbol
     *            the new stock symbol
     */
    public final void setStockSymbol(final String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    /**
     * Gets the stock currency.
     * 
     * @return the stock currency
     */
    public String getStockCurrency() {
        return stockCurrency;
    }

    /**
     * Sets the stock currency.
     * 
     * @param stockCurrency
     *            the new stock currency
     */
    public void setStockCurrency(String stockCurrency) {
        this.stockCurrency = stockCurrency;
    }

    /**
     * Gets the industry.
     * 
     * @return the industry
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * Sets the industry.
     * 
     * @param industry
     *            the new industry
     */
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    /**
     * From json.
     * 
     * @param json
     *            the json
     * @return the company
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static Company fromJson(final String json) throws IOException {
        return new ObjectMapper().readValue(json, Company.class);
    }

    /**
     * To json.
     * 
     * @param company
     *            the company
     * @return the string
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static String toJson(final Company company) throws IOException {
        return new ObjectMapper().writeValueAsString(company);
    }

    /**
     * Gets the stream tokens.
     * 
     * @return the stream tokens
     */
    public List<String> getStreamTokens() {
        List<String> list = new ArrayList<String>();
        if (isActive()) {
            list.add(getName());
            for (String tag : getTags()) {
                list.add(tag);
            }
        }
        return list;
    }

    /**
     * Calculate score.
     * 
     * @return the long
     */
    public final long calculateScore() {
        long score = 0;
        for (Tweet tweet : getTweets()) {
            score += tweet.getTweetScore();
        }
        return score;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (active ? 1231 : 1237);
        result = prime * result + (int) (companyScore ^ (companyScore >>> 32));
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((industry == null) ? 0 : industry.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((stockCurrency == null) ? 0 : stockCurrency.hashCode());
        long temp;
        temp = Double.doubleToLongBits(stockPrice);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((stockSymbol == null) ? 0 : stockSymbol.hashCode());
        result = prime * result + ((tags == null) ? 0 : tags.hashCode());
        result = prime * result + ((tweets == null) ? 0 : tweets.hashCode());
        return result;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Company)) {
            return false;
        }
        Company other = (Company) obj;
        if (active != other.active) {
            return false;
        }
        if (companyScore != other.companyScore) {
            return false;
        }
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!description.equals(other.description)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (industry == null) {
            if (other.industry != null) {
                return false;
            }
        } else if (!industry.equals(other.industry)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (stockCurrency == null) {
            if (other.stockCurrency != null) {
                return false;
            }
        } else if (!stockCurrency.equals(other.stockCurrency)) {
            return false;
        }
        if (Double.doubleToLongBits(stockPrice) != Double.doubleToLongBits(other.stockPrice)) {
            return false;
        }
        if (stockSymbol == null) {
            if (other.stockSymbol != null) {
                return false;
            }
        } else if (!stockSymbol.equals(other.stockSymbol)) {
            return false;
        }
        if (tags == null) {
            if (other.tags != null) {
                return false;
            }
        } else if (!tags.equals(other.tags)) {
            return false;
        }
        if (tweets == null) {
            if (other.tweets != null) {
                return false;
            }
        } else if (!tweets.equals(other.tweets)) {
            return false;
        }
        return true;
    }

}
