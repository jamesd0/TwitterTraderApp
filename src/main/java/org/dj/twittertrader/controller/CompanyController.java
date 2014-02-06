package org.dj.twittertrader.controller;

import java.util.List;

import org.dj.twittertrader.model.Company;
import org.dj.twittertrader.service.CompanyService;
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
     * Delete company.
     * 
     * @param company
     *            the company to delete
     */
    @RequestMapping(value = "/deleteCompany", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public final void deleteCompany(@RequestBody final Company company) {
        companyService.delete(company);
    }

    /**
     * Creates the company.
     * 
     * @param company
     *            the company
     */
    @RequestMapping(value = "/createCompany", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public final void createCompany(@RequestBody final Company company) {
        companyService.create(company);
    }

    /**
     * Updates the company.
     * 
     * @param company
     *            the updated company object
     */
    @RequestMapping(value = "/updateCompany", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public final void updateCompany(@RequestBody final Company company) {
        companyService.update(company);
    }

    /**
     * Select company.
     * 
     * @param id
     *            the id
     * @return the string
     */
    @RequestMapping(value = "/getCompany/{id}", method = RequestMethod.GET)
    @ResponseBody
    public final Company selectCompany(@PathVariable final long id) {
        return companyService.select(id);
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
