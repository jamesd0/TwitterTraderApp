package org.dj.twittertrader.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.dj.twittertrader.dao.PortfolioDAO;
import org.dj.twittertrader.model.Portfolio;
import org.dj.twittertrader.service.PortfolioService;
import org.dj.twittertrader.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class PortfolioServiceTest.
 */
public class PortfolioServiceTest {

    /** The portfolio service. */
    private PortfolioService portfolioService;

    /** The portfolio dao. */
    private PortfolioDAO portfolioDAO;

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
        portfolioDAO = mock(PortfolioDAO.class);
        portfolioService = new PortfolioServiceImpl();
        portfolioService.setPortfolioDAO(portfolioDAO);
    }

    /**
     * Test select all.
     */
    @Test
    public final void testSelectAll() {
        when(portfolioDAO.selectAll()).thenReturn(Arrays.asList(first, second));
        List<Portfolio> result = portfolioService.selectAll();
        assert (result.containsAll(Arrays.asList(first, second)));
        verify(portfolioDAO, times(1)).selectAll();
        verifyNoMoreInteractions(portfolioDAO);
    }

    /**
     * Test select.
     */
    @Test
    public final void testSelect() {
        when(portfolioDAO.select(0)).thenReturn(first);
        Portfolio result = portfolioService.select(0);
        assertEquals(result, first);
        verify(portfolioDAO, times(1)).select(0);
        verifyNoMoreInteractions(portfolioDAO);
    }

    /**
     * Test create.
     */
    @Test
    public final void testCreate() {
        portfolioService.create(first);
        verify(portfolioDAO, times(1)).create(first);
        verifyNoMoreInteractions(portfolioDAO);
    }

    /**
     * Test delete.
     */
    @Test
    public final void testDelete() {
        portfolioService.delete(first);
        verify(portfolioDAO, times(1)).delete(first);
        verifyNoMoreInteractions(portfolioDAO);
    }

    /**
     * Test update.
     */
    @Test
    public final void testUpdate() {
        portfolioService.update(first);
        verify(portfolioDAO, times(1)).update(first);
        verifyNoMoreInteractions(portfolioDAO);
    }

}
