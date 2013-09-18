package org.dj.twittertrader.controller;

import java.util.List;

import org.dj.twittertrader.model.Portfolio;
import org.dj.twittertrader.service.PortfolioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The Class PortfolioController.
 */
@Controller
@RequestMapping("/portfolio")
public class PortfolioController {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(PortfolioController.class);

    /** The portfolio service. */
    @Autowired
    private PortfolioService portfolioService;

    /**
     * Login to a portfolio.
     * 
     * @param username
     *            the username
     * @param password
     *            the password
     * @return the portfolio
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public final Portfolio login(@RequestParam("username") final String username,
            @RequestParam("password") final String password) {
        Portfolio sessionPortfolio = portfolioService.login(username, password);
        LOGGER.info("Logged in as: " + sessionPortfolio.getOwner());
        return sessionPortfolio;
    }

    /**
     * Gets all portfolios.
     * 
     * @return the all portfolios
     */
    @RequestMapping(value = "/getAllPortfolios", method = RequestMethod.GET)
    @ResponseBody
    public final List<Portfolio> getAllPortfolios() {
        List<Portfolio> portfolios = portfolioService.selectAll();
        return portfolios;
    }

    /**
     * Delete portfolio.
     * 
     * @param portfolio
     *            the portfolio to delete
     * @return the string
     */
    @RequestMapping(value = "/deletePortfolio", method = RequestMethod.DELETE)
    @ResponseBody
    public final String deletePortfolio(@RequestBody final Portfolio portfolio) {
        portfolioService.delete(portfolio);
        return "Portfolio deleted with id: " + portfolio.getId();
    }

    /**
     * Creates the portfolio.
     * 
     * @param portfolio
     *            the portfolio
     * @return the string
     */
    @RequestMapping(value = "/createPortfolio", method = RequestMethod.POST)
    @ResponseBody
    public final String createPortfolio(@RequestBody final Portfolio portfolio) {
        portfolioService.create(portfolio);
        return "Portfolio created successfully";
    }

    /**
     * Updates the portfolio.
     * 
     * @param portfolio
     *            the updated portfolio object
     * @return the string
     */
    @RequestMapping(value = "/updatePortfolio", method = RequestMethod.PUT)
    @ResponseBody
    public final String updatePortfolio(@RequestBody final Portfolio portfolio) {
        portfolioService.update(portfolio);
        return "Portfolio created successfully";
    }

    /**
     * Select portfolio.
     * 
     * @param id
     *            the id
     * @return the string
     */
    @RequestMapping(value = "/getPortfolio/{id}", method = RequestMethod.GET)
    @ResponseBody
    public final Portfolio selectPortfolio(@PathVariable final long id) {
        Portfolio portfolio = portfolioService.select(id);
        return portfolio;

    }
}
