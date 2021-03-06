package org.dj.twittertrader.messaging.impl;

import java.io.IOException;

import org.dj.twittertrader.messaging.MessagingBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

/**
 * The Class RabbitMQBroker.
 */
@Component
public class RabbitMQBroker implements MessagingBroker {

    /** The Constant logger. */
    private Logger logger = LoggerFactory.getLogger(RabbitMQBroker.class);

    /** The Constant QUEUE_NAME. */
    private static final String QUEUE_NAME = "twitter.trader";

    /** The Constant EXCHANGE_NAME. */
    private static final String EXCHANGE_NAME = "twittertrader-exchange";

    /** The Constant ROUTING_KEY. */
    private static final String ROUTING_KEY = "routingKey";

    /** The factory. */
    @Autowired
    private ConnectionFactory factory;

    /** The channel. */
    private Channel channel;

    /** The initialised. */
    private boolean initialised = false;

    /*
     * (non-Javadoc)
     * 
     * @see org.dj.twittertrader.messaging.MessagingBroker#upload(byte[])
     */
    @Override
    public final void upload(final byte[] message) throws IOException {
        if (!initialised) {
            this.init();
        }
        try {
            logger.info("Uploading message: " + new String(message, "UTF-8") + " to queue: "
                    + QUEUE_NAME);
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }

    /**
     * Initialises the channel.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void init() throws IOException {
        try {
            channel = factory.newConnection().createChannel();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw e;
        }
        initialised = true;

    }

    /**
     * Sets the factory.
     * 
     * @param factory2
     *            the new factory
     */
    public final void setFactory(final ConnectionFactory factory2) {
        this.factory = factory2;
    }

    /**
     * Checks if is initialised.
     * 
     * @return true, if is initialised
     */
    public final boolean isInitialised() {
        return initialised;
    }

    /**
     * Sets the initialised.
     * 
     * @param initialised
     *            the new initialised
     */
    public final void setInitialised(final boolean initialised) {
        this.initialised = initialised;
    }

    /**
     * Sets the channel.
     * 
     * @param channel2
     *            the new channel
     */
    public final void setChannel(final Channel channel2) {
        this.channel = channel2;

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
}
