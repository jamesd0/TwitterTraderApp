package org.dj.twittertrader.finance.impl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.dj.twittertrader.model.Company;
import org.junit.Test;

/**
 * The Class YahooDataReceiverTest.
 */
public class YahooDataReceiverTest {

    /** The Constant SLEEP_TIME. */
    private static final int SLEEP_TIME = 5000;
    /** The yahoo. */
    private YahooDataReceiver yahoo = new YahooDataReceiver();

    /**
     * Test.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void test() throws Exception {
        Double result = yahoo.getStockPrice("goog");
        assertTrue(result != null);
        assertTrue(result != 0.0);
        Thread.sleep(SLEEP_TIME);
    }

    /**
     * Test list.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void testList() throws Exception {
        List<Company> companies = new ArrayList<Company>();
        Company company = new Company();
        Company company2 = new Company();
        company.setName("google");
        company2.setName("easyjet");
        company.setStockSymbol("goog");
        company2.setStockSymbol("ezj.l");
        companies.add(company);
        companies.add(company2);
        yahoo.updateStockPrice(companies);
        Thread.sleep(SLEEP_TIME);

    }
}
