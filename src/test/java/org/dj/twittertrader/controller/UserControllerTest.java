package org.dj.twittertrader.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;

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

    /** The second. */
    private User second;

    /**
     * Setup.
     */
    @Before
    public final void setup() {
        first = TestUtil.randomUser();
        second = TestUtil.randomUser();
        controller = new UserController();
        userService = mock(UserService.class);
        controller.setUserService(userService);
    }

    /**
     * Test for valid getAllUsers request.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void getAllUsersTest() throws Exception {
        when(userService.selectAll()).thenReturn(Arrays.asList(first, second));
        standaloneSetup(controller).build().perform(get("/user/getAllUsers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is((int) first.getId())))
                .andExpect(jsonPath("$[0].createdAt", is(first.getCreatedAt().getTime())))
                .andExpect(jsonPath("$[0].favouritesCount", is(first.getFavouritesCount())))
                .andExpect(jsonPath("$[0].followersCount", is(first.getFollowersCount())))
                .andExpect(jsonPath("$[0].friendsCount", is(first.getFriendsCount())))
                .andExpect(jsonPath("$[0].lang", is(first.getLang())))
                .andExpect(jsonPath("$[0].location", is(first.getLocation())))
                .andExpect(jsonPath("$[0].name", is(first.getName())))
                .andExpect(jsonPath("$[0].screenName", is(first.getScreenName())))
                .andExpect(jsonPath("$[1].id", is((int) second.getId())))
                .andExpect(jsonPath("$[1].createdAt", is(second.getCreatedAt().getTime())))
                .andExpect(jsonPath("$[1].favouritesCount", is(second.getFavouritesCount())))
                .andExpect(jsonPath("$[1].followersCount", is(second.getFollowersCount())))
                .andExpect(jsonPath("$[1].friendsCount", is(second.getFriendsCount())))
                .andExpect(jsonPath("$[1].lang", is(second.getLang())))
                .andExpect(jsonPath("$[1].location", is(second.getLocation())))
                .andExpect(jsonPath("$[1].name", is(second.getName())))
                .andExpect(jsonPath("$[1].screenName", is(second.getScreenName())));
        verify(userService, times(1)).selectAll();
        verifyNoMoreInteractions(userService);
    }

    /**
     * Test for valid getUser request.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void getUserSuccess() throws Exception {
        when(userService.select(1)).thenReturn(first);
        standaloneSetup(controller).build().perform(get("/user/getUser/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id", is((int) first.getId())))
                .andExpect(jsonPath("createdAt", is(first.getCreatedAt().getTime())))
                .andExpect(jsonPath("favouritesCount", is(first.getFavouritesCount())))
                .andExpect(jsonPath("followersCount", is(first.getFollowersCount())))
                .andExpect(jsonPath("friendsCount", is(first.getFriendsCount())))
                .andExpect(jsonPath("lang", is(first.getLang())))
                .andExpect(jsonPath("location", is(first.getLocation())))
                .andExpect(jsonPath("name", is(first.getName())))
                .andExpect(jsonPath("screenName", is(first.getScreenName())));
        verify(userService, times(1)).select(1);
        verifyNoMoreInteractions(userService);
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

    /**
     * Test for valid updateUser request.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void updateUserSuccess() throws Exception {
        standaloneSetup(controller)
                .build()
                .perform(
                        put("/user/updateUser").contentType(TestUtil.APPLICATION_JSON_UTF8)
                                .content(User.toJson(first))).andExpect(status().isNoContent());
        verify(userService, times(1)).update(first);
        verifyNoMoreInteractions(userService);
    }

    /**
     * Test for valid deleteUser request.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void deleteUserSuccess() throws Exception {
        System.out.println(User.toJson(first));
        standaloneSetup(controller)
                .build()
                .perform(
                        delete("/user/deleteUser").contentType(TestUtil.APPLICATION_JSON_UTF8)
                                .content(User.toJson(first))).andExpect(status().isNoContent());
        verify(userService, times(1)).delete(first);
        verifyNoMoreInteractions(userService);
    }
}
