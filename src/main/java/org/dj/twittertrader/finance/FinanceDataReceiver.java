package org.dj.twittertrader.finance;

import java.io.IOException;
import java.util.List;

import org.dj.twittertrader.model.Company;

import twitter4j.internal.org.json.JSONException;

/**
 * The Interface FinanceDataReceiver.
 */
public interface FinanceDataReceiver {

    /**
     * Gets the stock price.
     * 
     * @param symbol
     *            the symbol
     * @return the stock price
     * @throws Exception
     *             the exception
     */
    double getStockPrice(String symbol) throws Exception;

    /**
     * Update stock price.
     * 
     * @param companies
     *            the companies
     * @throws JSONException
     *             the jSON exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    void updateStockPrice(List<Company> companies) throws JSONException, IOException;

}
