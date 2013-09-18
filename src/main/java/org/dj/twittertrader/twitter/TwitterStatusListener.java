/*
 * 
 */
package org.dj.twittertrader.twitter;

import java.util.List;

import org.dj.twittertrader.messaging.MessagingBroker;
import org.dj.twittertrader.model.Company;
import org.dj.twittertrader.model.Tweet;
import org.dj.twittertrader.service.CompanyService;
import org.dj.twittertrader.service.TweetService;
import org.dj.twittertrader.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving twitterStatus events. The class that is
 * interested in processing a twitterStatus event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addTwitterStatusListener</code> method. When the
 * twitterStatus event occurs, that object's appropriate method is invoked.
 * 
 * @see TwitterStatusEvent
 */
@Component
public class TwitterStatusListener implements StatusListener {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterStatusListener.class);

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

    /** The initialiased. */
    private boolean initialised = false;

    /** The companies. */
    private List<Company> companies;

    /*
     * (non-Javadoc)
     * 
     * @see twitter4j.StreamListener#onException(java.lang.Exception)
     */
    @Override
    public final void onException(final Exception e) {
        LOGGER.error("Twitter Exception" + e.getMessage());

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * twitter4j.StatusListener#onDeletionNotice(twitter4j.StatusDeletionNotice)
     */
    @Override
    public final void onDeletionNotice(final StatusDeletionNotice notice) {
        LOGGER.info("Twitter notice" + notice.toString());

    }

    /*
     * (non-Javadoc)
     * 
     * @see twitter4j.StatusListener#onScrubGeo(long, long)
     */
    @Override
    public final void onScrubGeo(final long arg0, final long arg1) {
        LOGGER.info("ScrubGEO" + arg0, arg1);

    }

    /*
     * (non-Javadoc)
     * 
     * @see twitter4j.StatusListener#onStallWarning(twitter4j.StallWarning)
     */
    @Override
    public final void onStallWarning(final StallWarning arg0) {
        LOGGER.info("Stall warning" + arg0.getMessage());

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
            LOGGER.info(status.getText());
            Tweet tweet = new Tweet(status);
            for (Company company : companies) {
                if (tweet.getText().contains(company.getName())) {
                    tweet.setCompany(company);
                }
            }
            userService.create(tweet.getUser());
            tweetService.create(tweet);
            broker.upload(Tweet.toJson(tweet).getBytes());
        }
    }

    /**
     * Inits the.
     */
    private void init() {
        companies = companyService.selectAll();
        initialised = true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see twitter4j.StatusListener#onTrackLimitationNotice(int)
     */
    @Override
    public final void onTrackLimitationNotice(final int arg0) {
        LOGGER.info("TrackLimitationNotice" + arg0);

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
    public boolean isInitialiased() {
        return initialised;
    }

    /**
     * Sets the initialiased.
     * 
     * @param initialiased
     *            the new initialiased
     */
    public void setInitialiased(boolean initialiased) {
        this.initialised = initialiased;
    }

}
