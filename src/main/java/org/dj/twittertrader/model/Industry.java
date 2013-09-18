package org.dj.twittertrader.model;

import com.google.gson.Gson;

/**
 * The Class Industry.
 */
public class Industry {

    /** The id. */
    private long id;

    /** The name. */
    private String name;

    /** The description. */
    private String description;

    /** The active. */
    private boolean active;

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
     * @return the industry
     */
    public static Industry fromJson(final String json) {
        return new Gson().fromJson(json, Industry.class);
    }

    /**
     * To json.
     * 
     * @param industry
     *            the industry
     * @return the string
     */
    public static String toJson(final Industry industry) {
        return new Gson().toJson(industry);
    }
}
