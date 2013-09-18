package org.dj.twittertrader.service;

import java.util.List;

import org.dj.twittertrader.model.Company;

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

}
