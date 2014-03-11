package org.dj.twittertrader.controller;

import org.dj.twittertrader.finance.impl.YahooDataReceiver;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The Class FinanceController.
 */
@Controller
@RequestMapping("/finance")
public class FinanceController {

    /**
     * Gets the google stock price.
     * 
     * @return the google stock price
     * @throws Exception
     *             the exception
     */
    @RequestMapping(value = "/getGoogleStockPrice", method = RequestMethod.GET)
    @ResponseBody
    public final double getGoogleStockPrice() throws Exception {
        YahooDataReceiver r = new YahooDataReceiver();
        return r.getStockPrice("goog");
    }

}
