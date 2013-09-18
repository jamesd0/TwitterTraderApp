package org.dj.twittertrader.controller;

import java.util.List;

import org.dj.twittertrader.model.User;
import org.dj.twittertrader.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
     * @return the string
     */
    @RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
    @ResponseBody
    public final String deleteUser(@RequestBody final User user) {
        userService.delete(user);
        return "User deleted with id: " + user.getId();
    }

    /**
     * Creates the user.
     * 
     * @param user
     *            the user
     * @return the string
     */
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    @ResponseBody
    public final String createUser(@RequestBody final User user) {
        userService.create(user);
        return "User created successfully";
    }

    /**
     * Updates the user.
     * 
     * @param user
     *            the updated user object
     * @return the string
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
    @ResponseBody
    public final String updateUser(@RequestBody final User user) {
        userService.update(user);
        return "User updated successfully";
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
}
