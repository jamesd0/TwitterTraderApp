package org.dj.twittertrader.controller;

import java.io.IOException;

import org.dj.twittertrader.finance.impl.YahooDataReceiver;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import twitter4j.internal.org.json.JSONException;

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
     * @throws JSONException
     *             the jSON exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @RequestMapping(value = "/getGoogleStockPrice", method = RequestMethod.GET)
    @ResponseBody
    public final double getGoogleStockPrice() throws JSONException, IOException {
        YahooDataReceiver r = new YahooDataReceiver();
        return r.getStockPrice("goog");
    }

}
