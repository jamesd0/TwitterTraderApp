package org.dj.twittertrader.controller;

import java.util.List;

import org.dj.twittertrader.model.Company;
import org.dj.twittertrader.service.CompanyService;
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
        return companies;
    }

    /**
     * Delete company.
     * 
     * @param company
     *            the company to delete
     * @return the string
     */
    @RequestMapping(value = "/deleteCompany", method = RequestMethod.DELETE)
    @ResponseBody
    public final String deleteCompany(@RequestBody final Company company) {
        companyService.delete(company);
        return "Company deleted with id: " + company.getId();
    }

    /**
     * Creates the company.
     * 
     * @param company
     *            the company
     * @return the string
     */
    @RequestMapping(value = "/createCompany", method = RequestMethod.POST)
    @ResponseBody
    public final String createCompany(@RequestBody final Company company) {
        companyService.create(company);
        return "Company created successfully";
    }

    /**
     * Updates the company.
     * 
     * @param company
     *            the updated company object
     * @return the string
     */
    @RequestMapping(value = "/updateCompany", method = RequestMethod.PUT)
    @ResponseBody
    public final String updateCompany(@RequestBody final Company company) {
        companyService.update(company);
        return "Company created successfully";
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
        Company company = companyService.select(id);
        return company;

    }
}
