package org.dj.twittertrader.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.dj.twittertrader.model.User;
import org.dj.twittertrader.service.UserService;
import org.dj.twittertrader.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class UserControllerTest.
 */
public class UserControllerTest {

    /** The controller. */
    private UserController controller;
    /** The user service. */
    private UserService userService;

    /** The first. */
    private User first;

    /**
     * Setup.
     */
    @Before
    public final void setup() {
        first = TestUtil.randomUser();
        controller = new UserController();
        userService = mock(UserService.class);
        controller.setUserService(userService);
    }

    /**
     * Test for valid createUser request.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void createUserSuccess() throws Exception {
        standaloneSetup(controller)
                .build()
                .perform(
                        post("/user/createUser").contentType(TestUtil.APPLICATION_JSON_UTF8)
                                .content(User.toJson(first))).andExpect(status().isCreated());
        System.out.println(User.toJson(first));
        verify(userService, times(1)).create(first);
        verifyNoMoreInteractions(userService);
    }

}
