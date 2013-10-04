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

import org.dj.twittertrader.model.Portfolio;
import org.dj.twittertrader.service.PortfolioService;
import org.dj.twittertrader.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class PortfolioControllerTest.
 */
public class PortfolioControllerTest {

    /** The controller. */
    private PortfolioController controller;
    /** The portfolio service. */
    private PortfolioService portfolioService;

    /** The first. */
    private Portfolio first;

    /** The second. */
    private Portfolio second;

    /**
     * Setup.
     */
    @Before
    public final void setup() {
        first = TestUtil.randomPortfolio();
        second = TestUtil.randomPortfolio();
        controller = new PortfolioController();
        portfolioService = mock(PortfolioService.class);
        controller.setPortfolioService(portfolioService);
    }

    /**
     * Test for valid getAllPortfolios request.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void getAllPortfoliosTest() throws Exception {
        when(portfolioService.selectAll()).thenReturn(Arrays.asList(first, second));
        standaloneSetup(controller).build().perform(get("/portfolio/getAllPortfolios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is((int) first.getId())))
                .andExpect(jsonPath("$[1].id", is((int) second.getId())));
        verify(portfolioService, times(1)).selectAll();
        verifyNoMoreInteractions(portfolioService);
    }

    /**
     * Test for valid getPortfolio request.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void getPortfolioSuccess() throws Exception {
        when(portfolioService.select(1)).thenReturn(first);
        standaloneSetup(controller).build().perform(get("/portfolio/getPortfolio/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id", is((int) first.getId())));
        verify(portfolioService, times(1)).select(1);
        verifyNoMoreInteractions(portfolioService);
    }

    /**
     * Test for valid createPortfolio request.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void createPortfolioSuccess() throws Exception {
        standaloneSetup(controller)
                .build()
                .perform(
                        post("/portfolio/createPortfolio").contentType(
                                TestUtil.APPLICATION_JSON_UTF8).content(Portfolio.toJson(first)))
                .andExpect(status().isCreated());
        verify(portfolioService, times(1)).create(first);
        verifyNoMoreInteractions(portfolioService);
    }

    /**
     * Test for valid updatePortfolio request.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void updatePortfolioSuccess() throws Exception {
        standaloneSetup(controller)
                .build()
                .perform(
                        put("/portfolio/updatePortfolio").contentType(
                                TestUtil.APPLICATION_JSON_UTF8).content(Portfolio.toJson(first)))
                .andExpect(status().isNoContent());
        verify(portfolioService, times(1)).update(first);
        verifyNoMoreInteractions(portfolioService);
    }

    /**
     * Test for valid deletePortfolio request.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void deletePortfolioSuccess() throws Exception {
        standaloneSetup(controller)
                .build()
                .perform(
                        delete("/portfolio/deletePortfolio").contentType(
                                TestUtil.APPLICATION_JSON_UTF8).content(Portfolio.toJson(first)))
                .andExpect(status().isNoContent());
        verify(portfolioService, times(1)).delete(first);
        verifyNoMoreInteractions(portfolioService);
    }

    /**
     * Login success.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public final void loginSuccess() throws Exception {
        when(portfolioService.login("tester", "test")).thenReturn(first);
        standaloneSetup(controller).build()
                .perform(post("/portfolio/login?username=tester&password=test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id", is((int) first.getId())));
        verify(portfolioService, times(1)).login("tester", "test");
        verifyNoMoreInteractions(portfolioService);
    }
}
