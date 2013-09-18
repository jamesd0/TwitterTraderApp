package org.dj.twittertrader.dao;

import java.util.List;

import org.dj.twittertrader.model.Industry;

/**
 * The Interface IndustryDAO.
 */
public interface IndustryDAO {

    /**
     * Creates an industry.
     * 
     * @param industry
     *            the industry
     */
    void create(Industry industry);

    /**
     * Update an industry.
     * 
     * @param industry
     *            the industry
     */
    void update(Industry industry);

    /**
     * Delete an industry.
     * 
     * @param industry
     *            the industry
     */
    void delete(Industry industry);

    /**
     * Select all.
     * 
     * @return the list of all industries
     */
    List<Industry> selectAll();

    /**
     * Select an individual industry based on its id.
     * 
     * @param id
     *            the id
     * @return the industry
     */
    Industry select(long id);

}
