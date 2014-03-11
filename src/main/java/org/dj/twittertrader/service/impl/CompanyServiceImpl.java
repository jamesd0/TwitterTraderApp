package org.dj.twittertrader.service.impl;

import java.util.List;

import org.dj.twittertrader.dao.CompanyDAO;
import org.dj.twittertrader.model.Company;
import org.dj.twittertrader.model.Industry;
import org.dj.twittertrader.model.Portfolio;
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

    /*
     * (non-Javadoc)
     * 
     * @see org.dj.twittertrader.service.CompanyService#create (org.dj.twittertrader.model.Company)
     */
    @Override
    @Transactional
    public final void create(final Company company) {
        companyDAO.create(company);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dj.twittertrader.service.CompanyService#update(org.dj.twittertrader .model.Company)
     */
    @Override
    @Transactional
    public final void update(final Company company) {
        companyDAO.update(company);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dj.twittertrader.service.CompanyService#delete (org.dj.twittertrader.model.Company)
     */
    @Override
    @Transactional
    public final void delete(final Company company) {
        companyDAO.delete(company);
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

    @Override
    @Transactional
    public final void populatePortfolioCompanies(final Portfolio portfolio) {
        companyDAO.populatePortfolioCompanies(portfolio);

    }

    @Override
    @Transactional
    public void populateIndustryCompanies(Industry industry) {
        companyDAO.populateIndustryCompanies(industry);

    }
}
