package org.dj.twittertrader.finance;

import java.io.IOException;

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
     * @throws JSONException
     *             the jSON exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    double getStockPrice(String symbol) throws JSONException, IOException;
}
