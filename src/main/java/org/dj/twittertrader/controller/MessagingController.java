package org.dj.twittertrader.controller;

import org.dj.twittertrader.messaging.MessagingBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The Class MessagingController is a rest api for controlling actions to the
 * messaging broker.
 */
@Controller
@RequestMapping("/messaging")
public class MessagingController {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(MessagingController.class);

    /** The broker. */
    @Autowired
    private MessagingBroker broker;

    /**
     * Upload method uploads the message on the path to the broker. Used as a
     * test that the connection to the messaging broker is valid.
     * 
     * @param message
     *            the message
     * @return the string
     */
    @RequestMapping(value = "/uploadMessage/{message}", method = RequestMethod.GET)
    @ResponseBody
    public final String upload(@PathVariable final String message) {
        LOGGER.debug("Uploading message to broker: " + message);
        broker.upload(message.getBytes());
        return "Successfully uploaded message: " + message;

    }
}
