package org.dj.twittertrader.controller;

import java.io.IOException;

import org.dj.twittertrader.messaging.MessagingBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class MessagingController is a rest api for controlling actions to the messaging broker.
 */
@Controller
@RequestMapping("/messaging")
public class MessagingController {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MessagingController.class);

    /** The broker. */
    @Autowired
    private MessagingBroker broker;

    /**
     * Upload method uploads the message on the path to the broker. Used as a test that the
     * connection to the messaging broker is valid.
     * 
     * @param message
     *            the message
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @RequestMapping(value = "/uploadMessage/{message}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public final void upload(@PathVariable final String message) throws IOException {
        LOGGER.debug("Uploading message to broker: " + message);
        broker.upload(message.getBytes());
    }

    /**
     * Sets the broker.
     * 
     * @param broker2
     *            the new broker
     */
    public final void setBroker(final MessagingBroker broker2) {
        this.broker = broker2;
    }
}
