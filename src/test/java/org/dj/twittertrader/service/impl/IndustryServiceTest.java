package org.dj.twittertrader.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.dj.twittertrader.dao.IndustryDAO;
import org.dj.twittertrader.model.Industry;
import org.dj.twittertrader.service.IndustryService;
import org.dj.twittertrader.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class IndustryServiceTest.
 */
public class IndustryServiceTest {
    /** The industry service. */
    private IndustryService industryService;

    /** The industry dao. */
    private IndustryDAO industryDAO;

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
        industryDAO = mock(IndustryDAO.class);
        industryService = new IndustryServiceImpl();
        industryService.setIndustryDAO(industryDAO);
    }

    /**
     * Test select all.
     */
    @Test
    public final void testSelectAll() {
        when(industryDAO.selectAll()).thenReturn(Arrays.asList(first, second));
        List<Industry> result = industryService.selectAll();
        assert (result.containsAll(Arrays.asList(first, second)));
        verify(industryDAO, times(1)).selectAll();
        verifyNoMoreInteractions(industryDAO);
    }

    /**
     * Test select.
     */
    @Test
    public final void testSelect() {
        when(industryDAO.select(0)).thenReturn(first);
        Industry result = industryService.select(0);
        assertEquals(result, first);
        verify(industryDAO, times(1)).select(0);
        verifyNoMoreInteractions(industryDAO);
    }

    /**
     * Test create.
     */
    @Test
    public final void testCreate() {
        industryService.create(first);
        verify(industryDAO, times(1)).create(first);
        verifyNoMoreInteractions(industryDAO);
    }

    /**
     * Test delete.
     */
    @Test
    public final void testDelete() {
        industryService.delete(first);
        verify(industryDAO, times(1)).delete(first);
        verifyNoMoreInteractions(industryDAO);
    }

    /**
     * Test update.
     */
    @Test
    public final void testUpdate() {
        industryService.update(first);
        verify(industryDAO, times(1)).update(first);
        verifyNoMoreInteractions(industryDAO);
    }

}
