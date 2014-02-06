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

    /**
     * Instantiates a new company stock price.
     * 
     * @param company
     *            the company
     * @param stockPrice
     *            the stock price
     */
    public CompanyStockPrice(final Company company, final double stockPrice) {
        this.companySymbol = company.getStockSymbol();
        this.stockPrice = stockPrice;
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

}
