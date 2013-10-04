package org.dj.twittertrader.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * The Class Portfolio.
 */
public class Portfolio {

    /** The id. */
    private long id;

    /** The owner. */
    private String owner;

    /** The username. */
    private String username;

    /** The password. */
    private String password;

    /** The companies. */
    private List<Company> companies;

    /** The industries. */
    private List<Industry> industries;

    /** The active. */
    private boolean active;

    /**
     * Gets the all stream tokens.
     * 
     * @return the all stream tokens
     */
    public final List<String> getAllStreamTokens() {
        List<String> list = new ArrayList<String>();
        for (Company company : companies) {
            list.addAll(company.getStreamTokens());
        }
        for (Industry industry : industries) {
            list.addAll(industry.getStreamTokens());
        }
        return list;
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
     * Gets the owner.
     * 
     * @return the owner
     */
    public final String getOwner() {
        return owner;
    }

    /**
     * Sets the owner.
     * 
     * @param owner
     *            the new owner
     */
    public final void setOwner(final String owner) {
        this.owner = owner;
    }

    /**
     * Gets the username.
     * 
     * @return the username
     */
    public final String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     * 
     * @param username
     *            the new username
     */
    public final void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Gets the password.
     * 
     * @return the password
     */
    public final String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * 
     * @param password
     *            the new password
     */
    public final void setPassword(final String password) {
        this.password = password;
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
     * Gets the industries.
     * 
     * @return the industries
     */
    public final List<Industry> getIndustries() {
        return industries;
    }

    /**
     * Sets the industries.
     * 
     * @param industries
     *            the new industries
     */
    public final void setIndustries(final List<Industry> industries) {
        this.industries = industries;
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
     * @return the portfolio
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static Portfolio fromJson(final String json) throws IOException {
        return new ObjectMapper().readValue(json, Portfolio.class);
    }

    /**
     * To json.
     * 
     * @param portfolio
     *            the portfolio
     * @return the string
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static String toJson(final Portfolio portfolio) throws IOException {
        return new ObjectMapper().writeValueAsString(portfolio);
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
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((industries == null) ? 0 : industries.hashCode());
        result = prime * result + ((owner == null) ? 0 : owner.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
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
        if (!(obj instanceof Portfolio)) {
            return false;
        }
        Portfolio other = (Portfolio) obj;
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
        if (id != other.id) {
            return false;
        }
        if (industries == null) {
            if (other.industries != null) {
                return false;
            }
        } else if (!industries.equals(other.industries)) {
            return false;
        }
        if (owner == null) {
            if (other.owner != null) {
                return false;
            }
        } else if (!owner.equals(other.owner)) {
            return false;
        }
        if (password == null) {
            if (other.password != null) {
                return false;
            }
        } else if (!password.equals(other.password)) {
            return false;
        }
        if (username == null) {
            if (other.username != null) {
                return false;
            }
        } else if (!username.equals(other.username)) {
            return false;
        }
        return true;
    }

}
