package org.dj.twittertrader.controller;

import java.util.List;

import org.dj.twittertrader.model.Portfolio;
import org.dj.twittertrader.service.PortfolioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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
     */
    @RequestMapping(value = "/deletePortfolio", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public final void deletePortfolio(@RequestBody final Portfolio portfolio) {
        portfolioService.delete(portfolio);
    }

    /**
     * Creates the portfolio.
     * 
     * @param portfolio
     *            the portfolio
     */
    @RequestMapping(value = "/createPortfolio", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public final void createPortfolio(@RequestBody final Portfolio portfolio) {
        portfolioService.create(portfolio);
    }

    /**
     * Updates the portfolio.
     * 
     * @param portfolio
     *            the updated portfolio object
     */
    @RequestMapping(value = "/updatePortfolio", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public final void updatePortfolio(@RequestBody final Portfolio portfolio) {
        portfolioService.update(portfolio);
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

    /**
     * Sets the portfolio service.
     * 
     * @param portfolioService2
     *            the new portfolio service
     */
    public final void setPortfolioService(final PortfolioService portfolioService2) {
        this.portfolioService = portfolioService2;
    }
}
