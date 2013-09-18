package org.dj.twittertrader.dao;

import java.util.List;

import org.dj.twittertrader.model.Company;

/**
 * This interface is the DAO for the Company entity. It provides an abstraction
 * layer to a database
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
     * Creates a company in the database.
     * 
     * @param company
     *            the company
     */
    void create(Company company);

    /**
     * Updates the company in the database.
     * 
     * @param company
     *            the company
     */
    void update(Company company);

    /**
     * Deletes the company from the database.
     * 
     * @param company
     *            the company
     */
    void delete(Company company);

}
