package org.dj.twittertrader.service.impl;

import java.util.List;

import org.dj.twittertrader.dao.CompanyDAO;
import org.dj.twittertrader.model.Company;
import org.dj.twittertrader.model.Tweet;
import org.dj.twittertrader.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class CompanyServiceImpl.
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    /** The company dao. */
    @Autowired
    private CompanyDAO companyDAO;

    /*
     * (non-Javadoc)
     * 
     * @see org.dj.twittertrader.service.CompanyService#selectAll()
     */
    @Override
    @Transactional
    public final List<Company> selectAll() {
        return companyDAO.selectAll();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dj.twittertrader.service.CompanyService#select(long)
     */
    @Override
    @Transactional
    public final Company select(final long id) {
        return companyDAO.select(id);
    }

    /**
     * Gets the company dao.
     * 
     * @return the companyDAO
     */
    public final CompanyDAO getCompanyDAO() {
        return companyDAO;
    }

    /**
     * Sets the company dao.
     * 
     * @param sourceDAO
     *            the companyDAO to set
     */
    @Override
    public final void setCompanyDAO(final CompanyDAO sourceDAO) {
        this.companyDAO = sourceDAO;
    }

    @Override
    @Transactional
    public final void addTweetToCompany(final Company company, final Tweet tweet) {
        companyDAO.addTweetToCompany(company, tweet);

    }

    @Override
    @Transactional
    public final void addStockPrice(final Company company) {
        companyDAO.addStockPrice(company);

    }
}
