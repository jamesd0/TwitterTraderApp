package org.dj.twittertrader.dao;

import java.util.List;

import javax.sql.DataSource;

/**
 * The Interface PortfolioDAO.
 */
public interface PortfolioDAO {

    /**
     * Gets the stream tokens.
     * 
     * @param id
     *            the id
     * @return the stream tokens
     */
    List<String> getStreamTokens(long id);

    /**
     * Sets the data source.
     * 
     * @param dataSource
     *            the new data source
     */
    void setDataSource(DataSource dataSource);

}
