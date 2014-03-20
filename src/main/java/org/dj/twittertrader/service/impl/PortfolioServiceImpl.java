package org.dj.twittertrader.service.impl;

import java.util.List;

import org.dj.twittertrader.dao.PortfolioDAO;
import org.dj.twittertrader.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class PortfolioServiceImpl.
 */
@Service
public class PortfolioServiceImpl implements PortfolioService {

    /** The portfolio dao. */
    @Autowired
    private PortfolioDAO portfolioDAO;

    /**
     * Gets the portfolio dao.
     * 
     * @return the portfolio dao
     */
    public final PortfolioDAO getPortfolioDAO() {
        return portfolioDAO;
    }

    /**
     * Sets the portfolio dao.
     * 
     * @param portfolioDAO
     *            the new portfolio dao
     */
    @Override
    public final void setPortfolioDAO(final PortfolioDAO portfolioDAO) {
        this.portfolioDAO = portfolioDAO;
    }

    @Override
    public final List<String> getStreamTokens(final long id) {
        return portfolioDAO.getStreamTokens(id);
    }
}
