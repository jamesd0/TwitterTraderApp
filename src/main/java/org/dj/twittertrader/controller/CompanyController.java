package org.dj.twittertrader.controller;

import java.util.List;

import org.dj.twittertrader.model.Company;
import org.dj.twittertrader.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The Class CompanyController is a REST api to control the Company Entity.
 */
@Controller
@RequestMapping("/company")
public class CompanyController {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);

    /** The company service. */
    @Autowired
    private CompanyService companyService;

    /**
     * Gets all companies.
     * 
     * @return the all companies
     */
    @RequestMapping(value = "/getAllCompanies", method = RequestMethod.GET)
    @ResponseBody
    public final List<Company> getAllCompanies() {
        List<Company> companies = companyService.selectAll();
        LOGGER.info("Returned list of companies");
        return companies;
    }

    /**
     * Gets all companies.
     * 
     * @return the all companies
     */
    @RequestMapping(value = "/getCompany/{id}", method = RequestMethod.GET)
    @ResponseBody
    public final Company getCompany(@PathVariable final int id) {
        Company company = companyService.select(id);
        LOGGER.info("Returned list of companies");
        return company;
    }

    /**
     * Sets the company service.
     * 
     * @param companyService
     *            the companyService to set
     */
    public final void setCompanyService(final CompanyService companyService) {
        this.companyService = companyService;
    }
}
