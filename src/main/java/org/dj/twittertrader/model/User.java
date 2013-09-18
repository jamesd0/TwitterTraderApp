package org.dj.twittertrader.model;

import java.util.Date;

import com.google.gson.Gson;

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
        active = true;
    }

    /**
     * Instantiates a new user.
     */
    public User() {
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
     */
    public static User fromJson(final String json) {
        return new Gson().fromJson(json, User.class);
    }

    /**
     * To json.
     * 
     * @param user
     *            the user
     * @return the string
     */
    public static String toJson(final User user) {
        return new Gson().toJson(user);
    }

}
