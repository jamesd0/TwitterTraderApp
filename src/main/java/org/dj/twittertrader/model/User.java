package org.dj.twittertrader.model;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class User.
 */
public class User {

    /** The id. */
    private long id;

    /** The name. */
    private String name;

    /** The screen name. */
    private String screenName;

    /** The followers count. */
    private int followersCount;

    /** The friends count. */
    private int friendsCount;

    /** The favourites count. */
    private int favouritesCount;

    /** The verified. */
    private boolean verified;

    /** The lang. */
    private String lang;

    /** The created at. */
    private Date createdAt;

    /** The location. */
    private String location;

    /** The user score. */
    private long userScore;

    /** The active. */
    private boolean active;

    /**
     * Instantiates a new user.
     * 
     * @param user
     *            the user
     */
    public User(final twitter4j.User user) {
        followersCount = user.getFollowersCount();
        friendsCount = user.getFriendsCount();
        id = user.getId();
        name = user.getName();
        screenName = user.getScreenName();
        favouritesCount = user.getFavouritesCount();
        verified = user.isVerified();
        lang = user.getLang();
        createdAt = user.getCreatedAt();
        location = user.getLocation();
        userScore = calculateUserScore();
        active = true;
    }

    /**
     * Instantiates a new user.
     */
    public User() {
        super();
    }

    /**
     * Calculate user score. Based on the users twitter stats
     * 
     * @return the int
     */
    private long calculateUserScore() {
        return (followersCount + friendsCount + favouritesCount) * (verified ? 10 : 1);
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
     * Gets the screen name.
     * 
     * @return the screen name
     */
    public final String getScreenName() {
        return screenName;
    }

    /**
     * Sets the screen name.
     * 
     * @param screenName
     *            the new screen name
     */
    public final void setScreenName(final String screenName) {
        this.screenName = screenName;
    }

    /**
     * Gets the followers count.
     * 
     * @return the followers count
     */
    public final int getFollowersCount() {
        return followersCount;
    }

    /**
     * Sets the followers count.
     * 
     * @param followersCount
     *            the new followers count
     */
    public final void setFollowersCount(final int followersCount) {
        this.followersCount = followersCount;
    }

    /**
     * Gets the friends count.
     * 
     * @return the friends count
     */
    public final int getFriendsCount() {
        return friendsCount;
    }

    /**
     * Sets the friends count.
     * 
     * @param friendsCount
     *            the new friends count
     */
    public final void setFriendsCount(final int friendsCount) {
        this.friendsCount = friendsCount;
    }

    /**
     * Gets the favourites count.
     * 
     * @return the favourites count
     */
    public final int getFavouritesCount() {
        return favouritesCount;
    }

    /**
     * Sets the favourites count.
     * 
     * @param favouritesCount
     *            the new favourites count
     */
    public final void setFavouritesCount(final int favouritesCount) {
        this.favouritesCount = favouritesCount;
    }

    /**
     * Checks if is verified.
     * 
     * @return true, if is verified
     */
    public final boolean isVerified() {
        return verified;
    }

    /**
     * Sets the verified.
     * 
     * @param verified
     *            the new verified
     */
    public final void setVerified(final boolean verified) {
        this.verified = verified;
    }

    /**
     * Gets the lang.
     * 
     * @return the lang
     */
    public final String getLang() {
        return lang;
    }

    /**
     * Sets the lang.
     * 
     * @param lang
     *            the new lang
     */
    public final void setLang(final String lang) {
        this.lang = lang;
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
     * Gets the location.
     * 
     * @return the location
     */
    public final String getLocation() {
        return location;
    }

    /**
     * Sets the location.
     * 
     * @param location
     *            the new location
     */
    public final void setLocation(final String location) {
        this.location = location;
    }

    /**
     * Gets the user score.
     * 
     * @return the user score
     */
    public final long getUserScore() {
        return userScore;
    }

    /**
     * Sets the user score.
     * 
     * @param userScore
     *            the new user score
     */
    public final void setUserScore(final long userScore) {
        this.userScore = userScore;
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
     * @return the user
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static User fromJson(final String json) throws IOException {
        return new ObjectMapper().readValue(json, User.class);
    }

    /**
     * To json.
     * 
     * @param user
     *            the user
     * @return the string
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static String toJson(final User user) throws IOException {
        return new ObjectMapper().writeValueAsString(user);
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
        result = prime * result + favouritesCount;
        result = prime * result + followersCount;
        result = prime * result + friendsCount;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((lang == null) ? 0 : lang.hashCode());
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((screenName == null) ? 0 : screenName.hashCode());
        result = (int) (prime * result + userScore);
        result = prime * result + (verified ? 1231 : 1237);
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
        if (!(obj instanceof User)) {
            return false;
        }
        User other = (User) obj;
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
        if (favouritesCount != other.favouritesCount) {
            return false;
        }
        if (followersCount != other.followersCount) {
            return false;
        }
        if (friendsCount != other.friendsCount) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (lang == null) {
            if (other.lang != null) {
                return false;
            }
        } else if (!lang.equals(other.lang)) {
            return false;
        }
        if (location == null) {
            if (other.location != null) {
                return false;
            }
        } else if (!location.equals(other.location)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (screenName == null) {
            if (other.screenName != null) {
                return false;
            }
        } else if (!screenName.equals(other.screenName)) {
            return false;
        }
        if (userScore != other.userScore) {
            return false;
        }
        if (verified != other.verified) {
            return false;
        }
        return true;
    }

}
