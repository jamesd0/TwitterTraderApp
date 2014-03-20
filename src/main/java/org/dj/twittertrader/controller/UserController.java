package org.dj.twittertrader.controller;

import org.dj.twittertrader.model.User;
import org.dj.twittertrader.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
     * Sets the user service.
     * 
     * @param userService2
     *            the new user service
     */
    public final void setUserService(final UserService userService2) {
        userService = userService2;

    }
}
