package org.dj.twittertrader.dao;

import java.util.List;

import org.dj.twittertrader.model.Portfolio;

/**
 * The Interface PortfolioDAO.
 */
public interface PortfolioDAO {

    /**
     * Select all.
     * 
     * @return the list
     */
    List<Portfolio> selectAll();

    /**
     * Delete.
     * 
     * @param portfolio
     *            the portfolio
     */
    void delete(Portfolio portfolio);

    /**
     * Creates the.
     * 
     * @param portfolio
     *            the portfolio
     */
    void create(Portfolio portfolio);

    /**
     * Update.
     * 
     * @param portfolio
     *            the portfolio
     */
    void update(Portfolio portfolio);

    /**
     * Select.
     * 
     * @param id
     *            the id
     * @return the portfolio
     */
    Portfolio select(long id);

    /**
     * Login.
     * 
     * @param username
     *            the username
     * @param password
     *            the password
     * @return the portfolio
     */
    Portfolio login(String username, String password);

    /**
     * Select snapshot.
     * 
     * @param id
     *            the id
     * @return the list
     */
    List<String> getStreamTokens(long id);

}
