package org.dj.twittertrader.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.dj.twittertrader.dao.CompanyDAO;
import org.dj.twittertrader.model.Company;
import org.dj.twittertrader.service.CompanyService;
import org.dj.twittertrader.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class CompanyServiceTest.
 */
public class CompanyServiceTest {

    /** The company service. */
    private CompanyService companyService;

    /** The company dao. */
    private CompanyDAO companyDAO;

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
        companyDAO = mock(CompanyDAO.class);
        companyService = new CompanyServiceImpl();
        companyService.setCompanyDAO(companyDAO);
    }

    /**
     * Test select all.
     */
    @Test
    public final void testSelectAll() {
        when(companyDAO.selectAll()).thenReturn(Arrays.asList(first, second));
        List<Company> result = companyService.selectAll();
        assert (result.containsAll(Arrays.asList(first, second)));
        verify(companyDAO, times(1)).selectAll();
        verifyNoMoreInteractions(companyDAO);
    }

    /**
     * Test select.
     */
    @Test
    public final void testSelect() {
        when(companyDAO.select(0)).thenReturn(first);
        Company result = companyService.select(0);
        assertEquals(result, first);
        verify(companyDAO, times(1)).select(0);
        verifyNoMoreInteractions(companyDAO);
    }

}
