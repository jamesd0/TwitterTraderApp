/*
 * 
 */
package org.dj.twittertrader.twitter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.dj.twittertrader.finance.FinanceDataReceiver;
import org.dj.twittertrader.messaging.MessagingBroker;
import org.dj.twittertrader.messaging.impl.CompanyStockPrice;
import org.dj.twittertrader.messaging.impl.CompanyTweet;
import org.dj.twittertrader.model.Company;
import org.dj.twittertrader.model.Tweet;
import org.dj.twittertrader.service.CompanyService;
import org.dj.twittertrader.service.TweetService;
import org.dj.twittertrader.service.UserService;
import org.dj.twittertrader.swn.TweetTagger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.internal.org.json.JSONException;

/**
 * The listener interface for receiving twitterStatus events. The class that is interested in
 * processing a twitterStatus event implements this interface, and the object created with that
 * class is registered with a component using the component's <code>addTwitterStatusListener</code>
 * method. When the twitterStatus event occurs, that object's appropriate method is invoked.
 * 
 * @see TwitterStatusEvent
 */
@Component
public class TwitterStatusListener implements StatusListener {

    /** The Constant logger. */
    private Logger logger = LoggerFactory.getLogger(TwitterStatusListener.class);

    /** The broker. */
    @Autowired
    private MessagingBroker broker;

    /** The tweet service. */
    @Autowired
    private TweetService tweetService;
    /** The tweet service. */
    @Autowired
    private UserService userService;

    /** The company service. */
    @Autowired
    private CompanyService companyService;

    /** The finance data receiver. */
    @Autowired
    private FinanceDataReceiver financeDataReceiver;

    /** The tagger. */
    @Autowired
    private TweetTagger tagger;

    /**
     * @param financeDataReceiver
     *            the financeDataReceiver to set
     */
    public final void setFinanceDataReceiver(final FinanceDataReceiver financeDataReceiver) {
        this.financeDataReceiver = financeDataReceiver;
    }

    /** The initialiased. */
    private boolean initialised = false;

    /** The companies. */
    private List<Company> companies;

    /** The map. */
    private HashMap<Company, Integer> map;

    /*
     * (non-Javadoc)
     * 
     * @see twitter4j.StreamListener#onException(java.lang.Exception)
     */
    @Override
    public final void onException(final Exception e) {
        logger.error("Twitter Exception: " + e.getMessage());
    }

    /*
     * (non-Javadoc)
     * 
     * @see twitter4j.StatusListener#onDeletionNotice(twitter4j.StatusDeletionNotice)
     */
    @Override
    public final void onDeletionNotice(final StatusDeletionNotice notice) {
        logger.info("Twitter notice: " + notice.toString());
    }

    /*
     * (non-Javadoc)
     * 
     * @see twitter4j.StatusListener#onScrubGeo(long, long)
     */
    @Override
    public final void onScrubGeo(final long arg0, final long arg1) {
        logger.info("ScrubGEO: " + arg0 + ", " + arg1);
    }

    /*
     * (non-Javadoc)
     * 
     * @see twitter4j.StatusListener#onStallWarning(twitter4j.StallWarning)
     */
    @Override
    public final void onStallWarning(final StallWarning arg0) {
        logger.info("Stall warning: " + arg0.getMessage());

    }

    /*
     * (non-Javadoc)
     * 
     * @see twitter4j.StatusListener#onStatus(twitter4j.Status)
     */
    @Override
    public final void onStatus(final Status status) {
        if (!initialised) {
            init();
        }
        if (status.getUser().getLang().equals("en")) {
            Tweet tweet = new Tweet(status);
            tweet.setTweetScore(tagger.getTweetScore(tweet));
            for (Company company : companies) {
                for (String tag : company.getStreamTokens()) {
                    if (tweet.getText().contains(tag)) {
                        userService.create(tweet.getUser());
                        tweetService.create(tweet);
                        companyService.addTweetToCompany(company, tweet);
                        try {
                            if ((map.get(company) % 50) == 0) {
                                double stockPrice = financeDataReceiver.getStockPrice(company
                                        .getStockSymbol());
                                companyService.addStockPrice(company, stockPrice, new Date());
                                broker.upload(CompanyStockPrice
                                        .getBrokerMessage(new CompanyStockPrice(company, stockPrice)));
                            }
                            broker.upload(CompanyTweet.getBrokerMessage(new CompanyTweet(company,
                                    tweet)));
                        } catch (IOException e) {
                            logger.error(e.getMessage());
                        } catch (JSONException e) {
                            logger.error(e.getMessage());
                        }
                        map.put(company, map.get(company) + 1);
                    }
                }
            }
        }
    }

    /**
     * Inits the.
     */
    private void init() {
        companies = companyService.selectAll();
        map = new HashMap<Company, Integer>();
        for (Company company : companies) {
            map.put(company, 0);
        }
        initialised = true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see twitter4j.StatusListener#onTrackLimitationNotice(int)
     */
    @Override
    public final void onTrackLimitationNotice(final int arg0) {
        logger.info("TrackLimitationNotice: " + arg0);

    }

    /**
     * Gets the broker.
     * 
     * @return the broker
     */
    public final MessagingBroker getBroker() {
        return broker;
    }

    /**
     * Sets the broker.
     * 
     * @param broker
     *            the new broker
     */
    public final void setBroker(final MessagingBroker broker) {
        this.broker = broker;
    }

    /**
     * Checks if is initialiased.
     * 
     * @return true, if is initialiased
     */
    public final boolean isInitialiased() {
        return initialised;
    }

    /**
     * Sets the initialiased.
     * 
     * @param initialiased
     *            the new initialiased
     */
    public final void setInitialiased(final boolean initialiased) {
        this.initialised = initialiased;
    }

    /**
     * Sets the tweet service.
     * 
     * @param tweetService2
     *            the new tweet service
     */
    public final void setTweetService(final TweetService tweetService2) {
        this.tweetService = tweetService2;
    }

    /**
     * Sets the user service.
     * 
     * @param userService2
     *            the new user service
     */
    public final void setUserService(final UserService userService2) {
        this.userService = userService2;
    }

    /**
     * Sets the company service.
     * 
     * @param companyService2
     *            the new company service
     */
    public final void setCompanyService(final CompanyService companyService2) {
        this.companyService = companyService2;
    }

    /**
     * Sets the logger.
     * 
     * @param logger
     *            the new logger
     */
    public final void setLogger(final Logger logger) {
        this.logger = logger;
    }

    /**
     * Sets the tagger.
     * 
     * @param tagger
     *            the new tagger
     */
    public final void setTagger(final TweetTagger tagger) {
        this.tagger = tagger;
    }
}
