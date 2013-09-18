package org.dj.twittertrader.messaging;


/**
 * The Interface MessagingBroker.
 */
public interface MessagingBroker {

    /**
     * Upload.
     * 
     * @param message
     *            the message
     */
    void upload(byte[] message);

}
