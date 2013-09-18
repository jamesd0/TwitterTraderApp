package org.dj.twittertrader.service;

import java.util.List;

import org.dj.twittertrader.model.Industry;

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

}
