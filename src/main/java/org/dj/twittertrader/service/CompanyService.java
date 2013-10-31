package org.dj.twittertrader.service;

import java.util.Date;
import java.util.List;

import org.dj.twittertrader.dao.CompanyDAO;
import org.dj.twittertrader.model.Company;
import org.dj.twittertrader.model.Industry;
import org.dj.twittertrader.model.Portfolio;
import org.dj.twittertrader.model.Tweet;

/**
 * The Interface CompanyService.
 */
public interface CompanyService {

    /**
     * Selects all Companies from the database.
     * 
     * @return the list
     */
    List<Company> selectAll();

    /**
     * Select a single company from the database identified by its id.
     * 
     * @param id
     *            the id
     * @return the company
     */
    Company select(long id);

    /**
     * Creates a company in the database.
     * 
     * @param company
     *            the company
     */
    void create(Company company);

    /**
     * Deletes the company from the database.
     * 
     * @param company
     *            the company
     */
    void delete(Company company);

    /**
     * Updates the company in the database.
     * 
     * @param company
     *            the company
     */
    void update(Company company);

    /**
     * Sets the company dao.
     * 
     * @param companyDAO
     *            the new company dao
     */
    void setCompanyDAO(CompanyDAO companyDAO);

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
     * Adds the stock price.
     * 
     * @param company
     *            the company
     * @param stockPrice
     *            the stock price
     * @param date
     *            the date
     */
    void addStockPrice(Company company, double stockPrice, Date date);

    /**
     * Populate portfolio companies.
     * 
     * @param portfolio
     *            the portfolio
     */
    void populatePortfolioCompanies(Portfolio portfolio);

    /**
     * Populate industry companies.
     * 
     * @param industry
     *            the industry
     */
    void populateIndustryCompanies(Industry industry);

}
