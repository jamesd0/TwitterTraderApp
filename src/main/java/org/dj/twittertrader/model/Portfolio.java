package org.dj.twittertrader.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

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
            if (company.isActive()) {
                list.add(company.getName());
            }
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
     */
    public static Portfolio fromJson(final String json) {
        return new Gson().fromJson(json, Portfolio.class);
    }

    /**
     * To json.
     * 
     * @param portfolio
     *            the portfolio
     * @return the string
     */
    public static String toJson(final Portfolio portfolio) {
        return new Gson().toJson(portfolio);
    }

}
