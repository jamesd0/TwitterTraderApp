package org.dj.twittertrader.messaging.impl;

import java.util.List;
import java.util.Map;

import org.dj.twittertrader.model.Company;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class CompanyTopWords.
 */
public class CompanyTopWords {

    /** The type. */
    private String type = "topWords";

    /** The top words. */
    private List<Map<String, Double>> topWords;

    /** The company symbol. */
    private String companySymbol;

    /** The industry. */
    private String industry;

    /**
     * Instantiates a new company top words.
     * 
     * @param company
     *            the company
     * @param list
     *            the top words
     */
    public CompanyTopWords(final Company company, final List<Map<String, Double>> list) {
        this.topWords = list;
        this.companySymbol = company.getStockSymbol();
        this.industry = company.getIndustry();
    }

    /**
     * Gets the broker message.
     * 
     * @param ctw
     *            the ct
     * @return the broker message
     * @throws JsonProcessingException
     *             the json processing exception
     */
    public static final byte[] getBrokerMessage(final CompanyTopWords ctw)
            throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(ctw).getBytes();
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
     * Gets the top words.
     * 
     * @return the top words
     */
    public final List<Map<String, Double>> getTopWords() {
        return topWords;
    }

    /**
     * Sets the top words.
     * 
     * @param topWords
     *            the new top words
     */
    public final void setTopWords(final List<Map<String, Double>> topWords) {
        this.topWords = topWords;
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
        result = prime * result + ((topWords == null) ? 0 : topWords.hashCode());
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
        if (!(obj instanceof CompanyTopWords)) {
            return false;
        }
        CompanyTopWords other = (CompanyTopWords) obj;
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
        if (topWords == null) {
            if (other.topWords != null) {
                return false;
            }
        } else if (!topWords.equals(other.topWords)) {
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
