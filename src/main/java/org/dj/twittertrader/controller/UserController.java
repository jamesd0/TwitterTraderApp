package org.dj.twittertrader.controller;

import java.util.List;

import org.dj.twittertrader.model.User;
import org.dj.twittertrader.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class UserController.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /** The user service. */
    @Autowired
    private UserService userService;

    /**
     * Gets all users.
     * 
     * @return the all users
     */
    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    @ResponseBody
    public final List<User> getAllUsers() {
        return userService.selectAll();
    }

    /**
     * Delete user.
     * 
     * @param user
     *            the user to delete
     */
    @RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public final void deleteUser(@RequestBody final User user) {
        userService.delete(user);
    }

    /**
     * Creates the user.
     * 
     * @param user
     *            the user
     */
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public final void createUser(@RequestBody final User user) {
        userService.create(user);
    }

    /**
     * Updates the user.
     * 
     * @param user
     *            the updated user object
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public final void updateUser(@RequestBody final User user) {
        userService.update(user);
    }

    /**
     * Select user.
     * 
     * @param id
     *            the id
     * @return the string
     */
    @RequestMapping(value = "/getUser/{id}", method = RequestMethod.GET)
    @ResponseBody
    public final User selectUser(@PathVariable final long id) {
        return userService.select(id);
    }

    /**
     * Sets the user service.
     * 
     * @param userService2
     *            the new user service
     */
    public final void setUserService(final UserService userService2) {
        userService = userService2;

    }
}
