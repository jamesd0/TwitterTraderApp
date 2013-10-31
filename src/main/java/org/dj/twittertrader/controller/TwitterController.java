package org.dj.twittertrader.controller;

import java.util.List;

import org.dj.twittertrader.service.PortfolioService;
import org.dj.twittertrader.twitter.TwitterStatusListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "/twitter")
public class TwitterController {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterController.class);

    /** The portfolio service. */
    @Autowired
    private PortfolioService portfolioService;

    /** The listener. */
    @Autowired
    private TwitterStatusListener listener;

    /** The stream. */
    private TwitterStream stream = new TwitterStreamFactory().getInstance();

    /**
     * Simply starts the stream.
     * 
     * @param id
     *            the id
     */
    @RequestMapping(value = "/start/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK, reason = "Successfully started stream")
    public final void start(@PathVariable final long id) {
        LOGGER.info("Shutting down stream");
        stream.shutdown();
        stream.addListener(listener);
        List<String> portfolioTokens = portfolioService.getStreamTokens(id);
        stream.filter(new FilterQuery(0, new long[0], portfolioTokens
                .toArray(new String[portfolioTokens.size()])));
        LOGGER.info("Successfully started stream: " + portfolioTokens);

    }

    /**
     * Stop.
     */
    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK, reason = "Successfully stopped stream")
    public final void stop() {
        stream.shutdown();
        LOGGER.info("Successfully stopped stream");
    }

    /**
     * Sets the listener.
     * 
     * @param listener
     *            the listener to set
     */
    public final void setListener(final TwitterStatusListener listener) {
        this.listener = listener;
    }

    /**
     * Sets the stream.
     * 
     * @param stream
     *            the stream to set
     */
    public final void setStream(final TwitterStream stream) {
        this.stream = stream;
    }

    /**
     * Sets the portfolio service.
     * 
     * @param portfolioService2
     *            the new portfolio service
     */
    public final void setPortfolioService(final PortfolioService portfolioService2) {
        this.portfolioService = portfolioService2;

    }

}
