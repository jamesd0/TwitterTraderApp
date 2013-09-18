package org.dj.twittertrader.model;

import java.util.Date;

import twitter4j.Status;

import com.google.gson.Gson;

/**
 * The Class Tweet.
 */
public class Tweet {

    /** The id. */
    private long id;

    /** The created at. */
    private Date createdAt;

    /** The text. */
    private String text;

    /** The user. */
    private User user;

    /** The retweet count. */
    private long retweetCount;

    /** The company. */
    private Company company;

    /** The active. */
    private boolean active;

    /**
     * Instantiates a new tweet.
     * 
     * @param status
     *            the status
     */
    public Tweet(final Status status) {
        id = status.getId();
        createdAt = status.getCreatedAt();
        text = status.getText();
        user = new User(status.getUser());
        retweetCount = status.getRetweetCount();
        active = true;
    }

    /**
     * Instantiates a new tweet.
     */
    public Tweet() {
        super();
    }

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public final long getId() {
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
     * Gets the created at.
     * 
     * @return the created at
     */
    public final Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the created at.
     * 
     * @param createdAt
     *            the new created at
     */
    public final void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the text.
     * 
     * @return the text
     */
    public final String getText() {
        return text;
    }

    /**
     * Sets the text.
     * 
     * @param text
     *            the new text
     */
    public final void setText(final String text) {
        this.text = text;
    }

    /**
     * Gets the user.
     * 
     * @return the user
     */
    public final User getUser() {
        return user;
    }

    /**
     * Sets the user.
     * 
     * @param user
     *            the new user
     */
    public final void setUser(final User user) {
        this.user = user;
    }

    /**
     * Gets the retweet count.
     * 
     * @return the retweet count
     */
    public final long getRetweetCount() {
        return retweetCount;
    }

    /**
     * Sets the retweet count.
     * 
     * @param retweetCount
     *            the new retweet count
     */
    public final void setRetweetCount(final long retweetCount) {
        this.retweetCount = retweetCount;
    }

    /**
     * Gets the company.
     * 
     * @return the company
     */
    public final Company getCompany() {
        return company;
    }

    /**
     * Sets the company.
     * 
     * @param company
     *            the new company
     */
    public final void setCompany(final Company company) {
        this.company = company;
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
     * From json.
     * 
     * @param json
     *            the json
     * @return the tweet
     */
    public static Tweet fromJson(final String json) {
        return new Gson().fromJson(json, Tweet.class);
    }

    /**
     * To json.
     * 
     * @param tweet
     *            the tweet
     * @return the string
     */
    public static String toJson(final Tweet tweet) {
        return new Gson().toJson(tweet);
    }

}
