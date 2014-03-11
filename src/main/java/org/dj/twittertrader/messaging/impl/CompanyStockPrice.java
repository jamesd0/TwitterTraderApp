package org.dj.twittertrader.messaging.impl;

import org.dj.twittertrader.model.Company;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class CompanyStockPrice.
 */
public class CompanyStockPrice {

    /** The type. */
    private String type = "stockPrice";

    /** The company symbol. */
    private String companySymbol;

    /** The stock price. */
    private double stockPrice;

    /** The industry. */
    private String industry;

    /**
     * Instantiates a new company stock price.
     * 
     * @param company
     *            the company
     */
    public CompanyStockPrice(final Company company) {
        this.companySymbol = company.getStockSymbol();
        this.stockPrice = company.getStockPrice();
        this.industry = company.getIndustry();
    }

    /**
     * Gets the broker message.
     * 
     * @param companyStockPrice
     *            the company stock price
     * @return the broker message
     * @throws JsonProcessingException
     *             the json processing exception
     */
    public static final byte[] getBrokerMessage(final CompanyStockPrice companyStockPrice)
            throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(companyStockPrice).getBytes();
    }

    /**
     * Gets the broker message individual.
     * 
     * @param company
     *            the company the stock price
     * @return the broker message individual
     * @throws JsonProcessingException
     *             the json processing exception
     */
    public static final byte[] getBrokerMessageIndividual(final Company company)
            throws JsonProcessingException {
        CompanyStockPrice companyStockPrice = new CompanyStockPrice(company);
        return new ObjectMapper().writeValueAsString(companyStockPrice).getBytes();
    }

    /**
     * Gets the type.
     * 
     * @return the type
     */
    public final String getType() {
        return type;
    }

    /**
     * Sets the type.
     * 
     * @param type
     *            the new type
     */
    public final void setType(final String type) {
        this.type = type;
    }

    /**
     * Gets the company symbol.
     * 
     * @return the company symbol
     */
    public final String getCompanySymbol() {
        return companySymbol;
    }

    /**
     * Sets the company symbol.
     * 
     * @param companySymbol
     *            the new company symbol
     */
    public final void setCompanySymbol(final String companySymbol) {
        this.companySymbol = companySymbol;
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
    public final String getIndustry() {
        return industry;
    }

    /**
     * Sets the industry.
     * 
     * @param industry
     *            the new industry
     */
    public final void setIndustry(final String industry) {
        this.industry = industry;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((companySymbol == null) ? 0 : companySymbol.hashCode());
        result = prime * result + ((industry == null) ? 0 : industry.hashCode());
        long temp;
        temp = Double.doubleToLongBits(stockPrice);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof CompanyStockPrice)) {
            return false;
        }
        CompanyStockPrice other = (CompanyStockPrice) obj;
        if (companySymbol == null) {
            if (other.companySymbol != null) {
                return false;
            }
        } else if (!companySymbol.equals(other.companySymbol)) {
            return false;
        }
        if (industry == null) {
            if (other.industry != null) {
                return false;
            }
        } else if (!industry.equals(other.industry)) {
            return false;
        }
        if (Double.doubleToLongBits(stockPrice) != Double.doubleToLongBits(other.stockPrice)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }

}
