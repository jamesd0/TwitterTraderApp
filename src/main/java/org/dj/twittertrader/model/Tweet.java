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
    private double tweetScore;

    /** The active. */
    private boolean active;

    /**
     * Instantiates a new tweet.
     * 
     * @param status
     *            the status
     */
    public Tweet(Status status) {
        id = status.getId();
        createdAt = status.getCreatedAt();
        text = status.getText();
        user = new User(status.getUser());
        retweetCount = status.getRetweetCount();
        tweetScore = 0;
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
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     * 
     * @param id
     *            the new id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the created at.
     * 
     * @return the created at
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the created at.
     * 
     * @param createdAt
     *            the new created at
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the text.
     * 
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text.
     * 
     * @param text
     *            the new text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the user.
     * 
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user.
     * 
     * @param user
     *            the new user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the retweet count.
     * 
     * @return the retweet count
     */
    public long getRetweetCount() {
        return retweetCount;
    }

    /**
     * Sets the retweet count.
     * 
     * @param retweetCount
     *            the new retweet count
     */
    public void setRetweetCount(long retweetCount) {
        this.retweetCount = retweetCount;
    }

    /**
     * Gets the tweet score.
     * 
     * @return the tweet score
     */
    public double getTweetScore() {
        return tweetScore;
    }

    /**
     * Sets the tweet score.
     * 
     * @param tweetScore
     *            the new tweet score
     */
    public void setTweetScore(double tweetScore) {
        this.tweetScore = tweetScore;
    }

    /**
     * Checks if is active.
     * 
     * @return true, if is active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the active.
     * 
     * @param active
     *            the new active
     */
    public void setActive(boolean active) {
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
    public static Tweet fromJson(String json) throws IOException {
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
    public static String toJson(Tweet tweet) throws IOException {
        return new ObjectMapper().writeValueAsString(tweet);
    }

    /**
     * Gets the latest tweets.
     * 
     * @param tweets
     *            the tweets
     * @return the latest tweets
     */
    public static List<Tweet> getLatestTweets(List<Tweet> tweets) {
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
    public static List<Tweet> getMostInfluentialTweets(List<Tweet> tweets) {
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
    public static List<Tweet> getMostDetrimentalTweets(List<Tweet> tweets) {
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

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + (active ? 1231 : 1237);
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + (int) (retweetCount ^ (retweetCount >>> 32));
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        long temp;
        temp = Double.doubleToLongBits(tweetScore);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
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
        if (Double.doubleToLongBits(tweetScore) != Double.doubleToLongBits(other.tweetScore)) {
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
}
