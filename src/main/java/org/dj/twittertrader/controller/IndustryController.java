package org.dj.twittertrader.controller;

import java.util.List;

import org.dj.twittertrader.model.Industry;
import org.dj.twittertrader.service.IndustryService;
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
        List<Industry> industries = industryService.selectAll();
        return industries;
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
        Industry industry = industryService.select(id);
        return industry;
    }

    /**
     * Updates the industry.
     * 
     * @param industry
     *            the updated industry object
     * @return the string
     */
    @RequestMapping(value = "/updateIndustry", method = RequestMethod.PUT)
    @ResponseBody
    public final String updateCompany(@RequestBody final Industry industry) {
        industryService.update(industry);
        return "Industry updated successfully";
    }

    /**
     * Delete industry.
     * 
     * @param industry
     *            the industry to delete
     * @return the string
     */
    @RequestMapping(value = "/deleteIndustry", method = RequestMethod.DELETE)
    @ResponseBody
    public final String deleteCompany(@RequestBody final Industry industry) {
        industryService.delete(industry);
        return "Industry deleted with id: " + industry.getId();
    }
}
