package org.dj.twittertrader.controller;

import java.util.List;

import org.dj.twittertrader.model.Industry;
import org.dj.twittertrader.service.IndustryService;
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
 * The Class IndustryController.
 */
@Controller
@RequestMapping("/industry")
public class IndustryController {
    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(IndustryController.class);

    /** The industry service. */
    @Autowired
    private IndustryService industryService;

    /**
     * Gets all companies.
     * 
     * @return the all companies
     */
    @RequestMapping(value = "/getAllIndustries", method = RequestMethod.GET)
    @ResponseBody
    public final List<Industry> getAllIndustries() {
        return industryService.selectAll();
    }

    /**
     * Gets all companies.
     * 
     * @param id
     *            the id
     * @return the all companies
     */
    @RequestMapping(value = "/getIndustry/{id}", method = RequestMethod.GET)
    @ResponseBody
    public final Industry getIndustry(@PathVariable final long id) {
        return industryService.select(id);
    }

    /**
     * Create industry.
     * 
     * @param industry
     *            the industry to delete
     */
    @RequestMapping(value = "/createIndustry", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public final void createIndustry(@RequestBody final Industry industry) {
        industryService.create(industry);
    }

    /**
     * Updates the industry.
     * 
     * @param industry
     *            the updated industry object
     */
    @RequestMapping(value = "/updateIndustry", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public final void updateIndustry(@RequestBody final Industry industry) {
        industryService.update(industry);
    }

    /**
     * Delete industry.
     * 
     * @param industry
     *            the industry to delete
     */
    @RequestMapping(value = "/deleteIndustry", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public final void deleteIndustry(@RequestBody final Industry industry) {
        industryService.delete(industry);
    }

    /**
     * Sets the industry service.
     * 
     * @param industryService2
     *            the new industry service
     */
    public final void setIndustryService(final IndustryService industryService2) {
        industryService = industryService2;

    }
}
