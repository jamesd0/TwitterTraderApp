package org.dj.twittertrader.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.dj.twittertrader.utils.SortByTweetDate;
import org.dj.twittertrader.utils.SortByTweetScore;

import twitter4j.Status;

import com.fasterxml.jackson.databind.ObjectMapper;

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

    /** The tweet score. */
    private long tweetScore;

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
        tweetScore = calculateTweetScore();
        active = true;
    }

    /**
     * Instantiates a new tweet.
     */
    public Tweet() {
        super();
    }

    /**
     * Calculate tweet score based on tweet content.
     * 
     * @return the int
     */
    private long calculateTweetScore() {
        return ((1 + retweetCount) + text.length() + user.getUserScore());
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
     * Gets the tweet score.
     * 
     * @return the tweet score
     */
    public final long getTweetScore() {
        return tweetScore;
    }

    /**
     * Sets the tweet score.
     * 
     * @param tweetScore
     *            the new tweet score
     */
    public final void setTweetScore(final long tweetScore) {
        this.tweetScore = tweetScore;
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
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static Tweet fromJson(final String json) throws IOException {
        return new ObjectMapper().readValue(json, Tweet.class);
    }

    /**
     * To json.
     * 
     * @param tweet
     *            the tweet
     * @return the string
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static String toJson(final Tweet tweet) throws IOException {
        return new ObjectMapper().writeValueAsString(tweet);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (active ? 1231 : 1237);
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + (int) (retweetCount ^ (retweetCount >>> 32));
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        result = (int) (prime * result + tweetScore);
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Tweet)) {
            return false;
        }
        Tweet other = (Tweet) obj;
        if (active != other.active) {
            return false;
        }
        if (createdAt == null) {
            if (other.createdAt != null) {
                return false;
            }
        } else if (!createdAt.equals(other.createdAt)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (retweetCount != other.retweetCount) {
            return false;
        }
        if (text == null) {
            if (other.text != null) {
                return false;
            }
        } else if (!text.equals(other.text)) {
            return false;
        }
        if (tweetScore != other.tweetScore) {
            return false;
        }
        if (user == null) {
            if (other.user != null) {
                return false;
            }
        } else if (!user.equals(other.user)) {
            return false;
        }
        return true;
    }

    /**
     * Gets the latest tweets.
     * 
     * @param tweets
     *            the tweets
     * @return the latest tweets
     */
    public static List<Tweet> getLatestTweets(final List<Tweet> tweets) {
        List<Tweet> list = new ArrayList<Tweet>();
        if (tweets.size() > 0) {
            Collections.sort(tweets, Collections.reverseOrder(new SortByTweetDate()));
            if (tweets.size() < 10) {
                for (Tweet t : tweets) {
                    list.add(t);
                }
            } else {
                for (Tweet t : tweets.subList(0, 10)) {
                    list.add(t);
                }
            }
        }
        return list;
    }

    /**
     * Gets the most influential tweets.
     * 
     * @param tweets
     *            the tweets
     * @return the most influential tweets
     */
    public static List<Tweet> getMostInfluentialTweets(final List<Tweet> tweets) {
        List<Tweet> list = new ArrayList<Tweet>();
        if (tweets.size() > 0) {
            Collections.sort(tweets, Collections.reverseOrder(new SortByTweetScore()));
            if (tweets.size() < 10) {
                for (Tweet t : tweets) {
                    list.add(t);
                }
            } else {
                for (Tweet t : tweets.subList(0, 10)) {
                    list.add(t);
                }
            }
        }
        return list;
    }

    /**
     * Gets the most detrimental tweets.
     * 
     * @param tweets
     *            the tweets
     * @return the most detrimental tweets
     */
    public static List<Tweet> getMostDetrimentalTweets(final List<Tweet> tweets) {
        List<Tweet> list = new ArrayList<Tweet>();
        if (tweets.size() > 0) {
            Collections.sort(tweets, new SortByTweetScore());
            if (tweets.size() < 10) {
                for (Tweet t : tweets) {
                    list.add(t);
                }
            } else {
                for (Tweet t : tweets.subList(0, 10)) {
                    list.add(t);
                }
            }
        }
        return list;
    }
}
