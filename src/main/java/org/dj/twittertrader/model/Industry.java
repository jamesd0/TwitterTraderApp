package org.dj.twittertrader.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

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

    /** The companies. */
    private List<Company> companies;

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
     * Gets the companies.
     * 
     * @return the companies
     */
    public final List<Company> getCompanies() {
        return companies;
    }

    /**
     * Sets the companies.
     * 
     * @param companies
     *            the new companies
     */
    public final void setCompanies(final List<Company> companies) {
        this.companies = companies;
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
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static Industry fromJson(final String json) throws IOException {
        return new ObjectMapper().readValue(json, Industry.class);
    }

    /**
     * To json.
     * 
     * @param industry
     *            the industry
     * @return the string
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static String toJson(final Industry industry) throws IOException {
        return new ObjectMapper().writeValueAsString(industry);
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
        result = prime * result + ((companies == null) ? 0 : companies.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        if (!(obj instanceof Industry)) {
            return false;
        }
        Industry other = (Industry) obj;
        if (active != other.active) {
            return false;
        }
        if (companies == null) {
            if (other.companies != null) {
                return false;
            }
        } else if (!companies.equals(other.companies)) {
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
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    /**
     * Gets the stream tokens.
     * 
     * @return the stream tokens
     */
    public final List<String> getStreamTokens() {
        List<String> list = new ArrayList<String>();
        for (Company company : getCompanies()) {
            list.addAll(company.getStreamTokens());
        }
        return list;
    }

}
