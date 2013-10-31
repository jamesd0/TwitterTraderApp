package org.dj.twittertrader.finance.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;

import org.dj.twittertrader.finance.FinanceDataReceiver;
import org.springframework.stereotype.Component;

import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;
import twitter4j.internal.org.json.JSONTokener;

/**
 * The Class YahooDataReceiver.
 */
@Component
public class YahooDataReceiver implements FinanceDataReceiver {

    @Override
    public final double getStockPrice(final String symbol) throws JSONException, IOException {
        String baseUrl = "http://query.yahooapis.com/v1/public/yql?q=";
        String query = "select * from yahoo.finance.quotes where symbol in ('" + symbol + "')";
        String fullUrlStr = baseUrl + URLEncoder.encode(query, "UTF-8")
                + "&format=json&diagnostics=true&env=http://datatables.org/alltables.env";

        URL fullUrl = new URL(fullUrlStr);
        InputStream is = fullUrl.openStream();

        JSONTokener tok = new JSONTokener(is);
        JSONObject result = new JSONObject(tok);
        is.close();
        return Double.parseDouble(result.getJSONObject("query").getJSONObject("results")
                .getJSONObject("quote").getString("LastTradePriceOnly"));
    }
}
