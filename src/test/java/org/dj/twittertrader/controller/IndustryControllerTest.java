package org.dj.twittertrader.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;

import org.dj.twittertrader.model.Industry;
import org.dj.twittertrader.service.IndustryService;
import org.dj.twittertrader.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class IndustryControllerTest.
 */
public class IndustryControllerTest {

    /** The controller. */
    private IndustryController controller;
    /** The industry service. */
    private IndustryService industryService;

    /** The first. */
    private Industry first;

    /** The second. */
    private Industry second;

    /**
     * Setup.
     */
    @Before
    public final void setup() {
        first = TestUtil.randomIndustry();
        second = TestUtil.randomIndustry();
        controller = new IndustryController();
        industryService = mock(IndustryService.class);
        controller.setIndustryService(industryService);
    }

    /**
     * Test for valid getAllIndustries request.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void getAllIndustriesTest() throws Exception {
        when(industryService.selectAll()).thenReturn(Arrays.asList(first, second));
        standaloneSetup(controller).build().perform(get("/industry/getAllIndustries"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is((int) first.getId())))
                .andExpect(jsonPath("$[0].description", is(first.getDescription())))
                .andExpect(jsonPath("$[0].name", is(first.getName())))
                .andExpect(jsonPath("$[1].id", is((int) second.getId())))
                .andExpect(jsonPath("$[1].description", is(second.getDescription())))
                .andExpect(jsonPath("$[1].name", is(second.getName())));
        verify(industryService, times(1)).selectAll();
        verifyNoMoreInteractions(industryService);
    }

    /**
     * Test for valid getIndustry request.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void getIndustrySuccess() throws Exception {
        when(industryService.select(1)).thenReturn(first);
        standaloneSetup(controller).build().perform(get("/industry/getIndustry/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id", is((int) first.getId())))
                .andExpect(jsonPath("description", is(first.getDescription())))
                .andExpect(jsonPath("name", is(first.getName())));
        verify(industryService, times(1)).select(1);
        verifyNoMoreInteractions(industryService);
    }

    /**
     * Test for valid createIndustry request.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void createIndustrySuccess() throws Exception {
        standaloneSetup(controller)
                .build()
                .perform(
                        post("/industry/createIndustry")
                                .contentType(TestUtil.APPLICATION_JSON_UTF8).content(
                                        Industry.toJson(first))).andExpect(status().isCreated());
        verify(industryService, times(1)).create(first);
        verifyNoMoreInteractions(industryService);
    }

    /**
     * Test for valid updateIndustry request.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void updateIndustrySuccess() throws Exception {
        standaloneSetup(controller)
                .build()
                .perform(
                        put("/industry/updateIndustry").contentType(TestUtil.APPLICATION_JSON_UTF8)
                                .content(Industry.toJson(first))).andExpect(status().isNoContent());
        verify(industryService, times(1)).update(first);
        verifyNoMoreInteractions(industryService);
    }

    /**
     * Test for valid deleteIndustry request.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void deleteIndustrySuccess() throws Exception {
        standaloneSetup(controller)
                .build()
                .perform(
                        delete("/industry/deleteIndustry").contentType(
                                TestUtil.APPLICATION_JSON_UTF8).content(Industry.toJson(first)))
                .andExpect(status().isNoContent());
        verify(industryService, times(1)).delete(first);
        verifyNoMoreInteractions(industryService);
    }
}
