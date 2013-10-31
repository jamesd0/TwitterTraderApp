package org.dj.twittertrader.service.impl;

import java.util.List;

import org.dj.twittertrader.dao.PortfolioDAO;
import org.dj.twittertrader.model.Portfolio;
import org.dj.twittertrader.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class PortfolioServiceImpl.
 */
@Service
public class PortfolioServiceImpl implements PortfolioService {

    /** The portfolio dao. */
    @Autowired
    private PortfolioDAO portfolioDAO;

    @Override
    @Transactional
    public final List<Portfolio> selectAll() {
        return portfolioDAO.selectAll();
    }

    @Override
    @Transactional
    public final void delete(final Portfolio portfolio) {
        portfolioDAO.delete(portfolio);

    }

    @Override
    @Transactional
    public final void create(final Portfolio portfolio) {
        portfolioDAO.create(portfolio);

    }

    @Override
    @Transactional
    public final void update(final Portfolio portfolio) {
        portfolioDAO.update(portfolio);

    }

    @Override
    @Transactional
    public final Portfolio select(final long id) {
        return portfolioDAO.select(id);
    }

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
    @Transactional
    public final Portfolio login(final String username, final String password) {
        return portfolioDAO.login(username, password);
    }

    @Override
    public final List<String> getStreamTokens(final long id) {
        return portfolioDAO.getStreamTokens(id);
    }
}
