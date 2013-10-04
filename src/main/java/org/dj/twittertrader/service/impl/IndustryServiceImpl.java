package org.dj.twittertrader.service.impl;

import java.util.List;

import org.dj.twittertrader.dao.IndustryDAO;
import org.dj.twittertrader.model.Industry;
import org.dj.twittertrader.service.IndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class IndustryServiceImpl.
 */
@Service
public class IndustryServiceImpl implements IndustryService {

    /** The industry dao. */
    @Autowired
    private IndustryDAO industryDAO;

    /*
     * (non-Javadoc)
     * 
     * @see org.dj.twittertrader.service.IndustryService#selectAll()
     */
    @Override
    @Transactional
    public final List<Industry> selectAll() {
        return industryDAO.selectAll();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dj.twittertrader.service.IndustryService#select(long)
     */
    @Override
    @Transactional
    public final Industry select(final long id) {
        return industryDAO.select(id);
    }

    /**
     * Gets the industry dao.
     * 
     * @return the industryDAO
     */
    public final IndustryDAO getIndustryDAO() {
        return industryDAO;
    }

    /**
     * Sets the industry dao.
     * 
     * @param industryDAO
     *            the industryDAO to set
     */
    public final void setIndustryDAO(final IndustryDAO industryDAO) {
        this.industryDAO = industryDAO;
    }

    @Override
    @Transactional
    public final void delete(final Industry industry) {
        industryDAO.delete(industry);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dj.twittertrader.service.IndustryService#update(org.dj.twittertrader
     * .model.Industry)
     */
    @Override
    @Transactional
    public final void update(final Industry industry) {
        industryDAO.update(industry);

    }

    @Override
    @Transactional
    public final void create(final Industry industry) {
        industryDAO.create(industry);

    }
}
