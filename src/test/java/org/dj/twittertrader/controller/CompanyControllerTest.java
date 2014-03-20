package org.dj.twittertrader.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;

import org.dj.twittertrader.model.Company;
import org.dj.twittertrader.service.CompanyService;
import org.dj.twittertrader.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class CompanyControllerTest.
 */
public class CompanyControllerTest {

    /** The controller. */
    private CompanyController controller;
    /** The company service. */
    private CompanyService companyService;

    /** The first. */
    private Company first;

    /** The second. */
    private Company second;

    /**
     * Setup.
     */
    @Before
    public final void setup() {
        first = TestUtil.randomCompany();
        second = TestUtil.randomCompany();
        controller = new CompanyController();
        companyService = mock(CompanyService.class);
        controller.setCompanyService(companyService);
    }

    /**
     * Test for valid getAllCompanies request.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void getAllCompaniesTest() throws Exception {
        when(companyService.selectAll()).thenReturn(Arrays.asList(first, second));
        standaloneSetup(controller).build().perform(get("/company/getAllCompanies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(first.getId())))
                .andExpect(jsonPath("$[0].description", is(first.getDescription())))
                .andExpect(jsonPath("$[0].name", is(first.getName())))
                .andExpect(jsonPath("$[1].id", is(second.getId())))
                .andExpect(jsonPath("$[1].description", is(second.getDescription())))
                .andExpect(jsonPath("$[1].name", is(second.getName())));
        verify(companyService, times(1)).selectAll();
        verifyNoMoreInteractions(companyService);
    }

    /**
     * Test for valid getCompany request.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void getCompanySuccess() throws Exception {
        when(companyService.select(1)).thenReturn(first);
        standaloneSetup(controller).build().perform(get("/company/getCompany/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id", is(first.getId())))
                .andExpect(jsonPath("description", is(first.getDescription())))
                .andExpect(jsonPath("name", is(first.getName())));
        verify(companyService, times(1)).select(1);
        verifyNoMoreInteractions(companyService);
    }

}
