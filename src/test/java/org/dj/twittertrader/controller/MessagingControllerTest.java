package org.dj.twittertrader.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.dj.twittertrader.messaging.MessagingBroker;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class MessagingControllerTest.
 */
public class MessagingControllerTest {

    /** The controller. */
    private MessagingController controller;

    /** The broker. */
    private MessagingBroker broker;

    /**
     * Setup.
     */
    @Before
    public final void setup() {
        controller = new MessagingController();
        broker = mock(MessagingBroker.class);
        controller.setBroker(broker);
    }

    /**
     * Upload message test.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void uploadMessageTest() throws Exception {
        standaloneSetup(controller).build().perform(get("/messaging/uploadMessage/TestMessage"))
                .andExpect(status().isNoContent());
        verify(broker, times(1)).upload("TestMessage".getBytes());
        verifyNoMoreInteractions(broker);
    }

}
