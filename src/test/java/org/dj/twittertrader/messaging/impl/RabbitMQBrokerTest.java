package org.dj.twittertrader.messaging.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * The Class RabbitMQBrokerTest.
 */
public class RabbitMQBrokerTest {

    /** The broker. */
    private RabbitMQBroker broker;

    /** The factory. */
    private ConnectionFactory factory;

    /** The channel. */
    private Channel channel;

    /** The connection. */
    private Connection connection;

    /**
     * The set up run before each test.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Before
    public final void setUp() throws IOException {
        broker = new RabbitMQBroker();
        factory = mock(ConnectionFactory.class);
        connection = mock(Connection.class);
        channel = mock(Channel.class);
        broker.setFactory(factory);
        when(factory.newConnection()).thenReturn(connection);
        when(connection.createChannel()).thenReturn(channel);
    }

    /**
     * Test single upload.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public final void testSingleUpload() throws IOException {
        broker.upload("This is a test message".getBytes());
        verify(channel, times(1)).basicPublish("", "twitter.trader", null,
                "This is a test message".getBytes());
        assertEquals(broker.isInitialised(), true);
        verify(connection, times(1)).createChannel();
        verify(factory, times(1)).newConnection();
        verifyNoMoreInteractions(channel);
        verifyNoMoreInteractions(connection);
        verifyNoMoreInteractions(factory);
    }

    /**
     * Test multiple upload.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public final void testMultipleUpload() throws IOException {
        broker.upload("This is a test message".getBytes());
        broker.upload("This is a second test message".getBytes());
        verify(channel, times(1)).basicPublish("", "twitter.trader", null,
                "This is a test message".getBytes());
        verify(channel, times(1)).basicPublish("", "twitter.trader", null,
                "This is a second test message".getBytes());
        assertEquals(broker.isInitialised(), true);
        verify(connection, times(1)).createChannel();
        verify(factory, times(1)).newConnection();
        verifyNoMoreInteractions(channel);
        verifyNoMoreInteractions(connection);
        verifyNoMoreInteractions(factory);
    }

    /**
     * Test multiple upload when the broker has already been initialised.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public final void testSingleUploadAlreadyInit() throws IOException {
        broker.setInitialised(true);
        broker.setChannel(channel);
        broker.upload("This is a test message".getBytes());
        verify(channel, times(1)).basicPublish("", "twitter.trader", null,
                "This is a test message".getBytes());
        assertEquals(broker.isInitialised(), true);
        verify(connection, times(0)).createChannel();
        verify(factory, times(0)).newConnection();
        verifyNoMoreInteractions(channel);
        verifyNoMoreInteractions(connection);
        verifyNoMoreInteractions(factory);
    }
}
