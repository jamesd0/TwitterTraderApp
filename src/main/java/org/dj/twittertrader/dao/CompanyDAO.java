package org.dj.twittertrader.dao;

import java.util.List;

import javax.sql.DataSource;

import org.dj.twittertrader.model.Company;
import org.dj.twittertrader.model.Tweet;

/**
 * This interface is the DAO for the Company entity. It provides an abstraction layer to a database
 * 
 * @author duncan
 */
public interface CompanyDAO {
    /**
     * This method selects all Companies from the database.
     * 
     * @return a list of all companies
     */
    List<Company> selectAll();

    /**
     * Select an individual company from the database identified by the id.
     * 
     * @param id
     *            the id
     * @return the company
     */
    Company select(long id);

    /**
     * Adds the tweet to company.
     * 
     * @param company
     *            the company
     * @param tweet
     *            the tweet
     */
    void addTweetToCompany(Company company, Tweet tweet);

    /**
     * Sets the data source.
     * 
     * @param dataSource
     *            the new data source
     */
    void setDataSource(DataSource dataSource);

    /**
     * Adds the stock price.
     * 
     * @param company
     *            the company
     */
    void addStockPrice(Company company);

}
