package org.dj.twittertrader.finance.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.dj.twittertrader.finance.FinanceDataReceiver;
import org.dj.twittertrader.model.Company;
import org.springframework.stereotype.Component;

import twitter4j.internal.org.json.JSONArray;
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
        String query = "select LastTradePriceOnly from yahoo.finance.quotes where symbol in ('"
                + symbol + "')";
        String fullUrlStr = baseUrl + URLEncoder.encode(query, "UTF-8")
                + "&format=json&diagnostics=true&env=http://datatables.org/alltables.env";

        URL fullUrl = new URL(fullUrlStr);
        InputStream is = fullUrl.openStream();

        JSONTokener tok = new JSONTokener(is);
        JSONObject result = new JSONObject(tok);
        is.close();
        if (!result.getJSONObject("query").isNull("results")) {
            return Double.parseDouble(result.getJSONObject("query").getJSONObject("results")
                    .getJSONObject("quote").getString("LastTradePriceOnly"));
        } else {
            throw new JSONException("Failed to get quote from Yahoo");
        }
    }

    @Override
    public final void updateStockPrice(List<Company> companies) throws JSONException, IOException {
        String baseUrl = "http://query.yahooapis.com/v1/public/yql?q=";
        String companiesString = "";
        int count = 0;
        for (Company company : companies) {
            if (count == companies.size()) {
                companiesString += company.getStockSymbolNotAbreviated();
            } else {
                companiesString += company.getStockSymbolNotAbreviated() + ",";
            }
            count++;
        }
        String query = "select LastTradePriceOnly from yahoo.finance.quotes where symbol in ('"
                + companiesString + "')";
        String fullUrlStr = baseUrl + URLEncoder.encode(query, "UTF-8")
                + "&format=json&diagnostics=true&env=http://datatables.org/alltables.env";

        URL fullUrl = new URL(fullUrlStr);
        InputStream is = fullUrl.openStream();

        JSONTokener tok = new JSONTokener(is);
        JSONObject result = new JSONObject(tok);
        is.close();
        JSONArray prices = result.getJSONObject("query").getJSONObject("results")
                .getJSONArray("quote");
        if (!result.getJSONObject("query").isNull("results")) {
            for (int i = 0; i < companies.size(); i++) {
                companies.get(i)
                        .setStockPrice(
                                Double.parseDouble(prices.getJSONObject(i).getString(
                                        "LastTradePriceOnly")));
            }

        } else {
            throw new JSONException("Failed to get quote from Yahoo");
        }
    }
}
