package org.dj.twittertrader.controller;

import org.dj.twittertrader.service.PortfolioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
     * Sets the portfolio service.
     * 
     * @param portfolioService2
     *            the new portfolio service
     */
    public final void setPortfolioService(final PortfolioService portfolioService2) {
        this.portfolioService = portfolioService2;
    }
}
