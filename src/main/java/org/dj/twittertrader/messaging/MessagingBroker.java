package org.dj.twittertrader.messaging;

import java.io.IOException;

/**
 * The Interface MessagingBroker.
 */
public interface MessagingBroker {

    /**
     * Upload.
     * 
     * @param message
     *            the message
     * @throws IOException
     */
    void upload(byte[] message) throws IOException;

}
