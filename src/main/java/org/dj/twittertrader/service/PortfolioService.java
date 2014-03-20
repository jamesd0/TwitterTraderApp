package org.dj.twittertrader.service;

import java.util.List;

import org.dj.twittertrader.dao.PortfolioDAO;

/**
 * The Interface PortfolioService.
 */
public interface PortfolioService {

    /**
     * Sets the portfolio dao.
     * 
     * @param portfolioDAO
     *            the new portfolio dao
     */
    void setPortfolioDAO(PortfolioDAO portfolioDAO);

    /**
     * Gets the stream tokens.
     * 
     * @param id
     *            the id
     * @return the stream tokens
     */
    List<String> getStreamTokens(long id);

}
