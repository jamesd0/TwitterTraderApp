package org.dj.twittertrader.service;

import java.util.List;

import org.dj.twittertrader.dao.IndustryDAO;
import org.dj.twittertrader.model.Industry;
import org.dj.twittertrader.model.Portfolio;

/**
 * The Interface IndustryService.
 */
public interface IndustryService {

    /**
     * Select all industries.
     * 
     * @return the list of industries
     */
    List<Industry> selectAll();

    /**
     * Select a given industry.
     * 
     * @param id
     *            the id
     * @return the industry
     */
    Industry select(long id);

    /**
     * Delete.
     * 
     * @param industry
     *            the industry
     */
    void delete(Industry industry);

    /**
     * Update.
     * 
     * @param industry
     *            the industry
     */
    void update(Industry industry);

    /**
     * Creates the.
     * 
     * @param industry
     *            the industry
     */
    void create(Industry industry);

    /**
     * Sets the industry dao.
     * 
     * @param industryDAO
     *            the new industry dao
     */
    void setIndustryDAO(IndustryDAO industryDAO);

    /**
     * Populate portfolio industries.
     * 
     * @param portfolio
     *            the portfolio
     */
    void populatePortfolioIndustries(Portfolio portfolio);
}
