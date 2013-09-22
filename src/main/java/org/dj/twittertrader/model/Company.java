package org.dj.twittertrader.model;

import java.util.List;

import com.google.gson.Gson;

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

    /** The stock price. */
    private double stockPrice;

    /** The industry. */
    private Industry industry;

    /** The active. */
    private boolean active;

    /**
     * Instantiates a new company.
     */
    public Company() {
        super();
    }

    /**
     * Instantiates a new company.
     * 
     * @param id
     *            the id
     */
    public Company(final long id) {
        super();
        this.id = id;
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
     * Gets the stock price.
     * 
     * @return the stock price
     */
    public final double getStockPrice() {
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
     * Gets the industry.
     * 
     * @return the industry
     */
    public final Industry getIndustry() {
        return industry;
    }

    /**
     * Sets the industry.
     * 
     * @param industry
     *            the new industry
     */
    public final void setIndustry(final Industry industry) {
        this.industry = industry;
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
     * @return the company
     */
    public static Company fromJson(final String json) {
        return new Gson().fromJson(json, Company.class);
    }

    /**
     * To json.
     * 
     * @param company
     *            the company
     * @return the string
     */
    public static String toJson(final Company company) {
        return new Gson().toJson(company);
    }

    /**
     * From json.
     * 
     * @param json
     *            the json
     * @return the company
     */
    @SuppressWarnings("unchecked")
    public static List<Company> fromJsonList(final String json) {
        return new Gson().fromJson(json, List.class);
    }

    /**
     * To json.
     * 
     * @param companies
     *            the companies
     * @return the string
     */
    public static String toJsonList(final List<Company> companies) {
        return new Gson().toJson(companies);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (active ? 1231 : 1237);
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((industry == null) ? 0 : industry.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        long temp;
        temp = Double.doubleToLongBits(stockPrice);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Company other = (Company) obj;
        if (active != other.active)
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (id != other.id)
            return false;
        if (industry == null) {
            if (other.industry != null)
                return false;
        } else if (!industry.equals(other.industry))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (Double.doubleToLongBits(stockPrice) != Double.doubleToLongBits(other.stockPrice))
            return false;
        return true;
    }
}
