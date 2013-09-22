/*
 * 
 */
package org.dj.twittertrader.utils;

import java.nio.charset.Charset;
import java.util.Random;

import org.dj.twittertrader.model.Company;
import org.dj.twittertrader.model.Industry;
import org.springframework.http.MediaType;

/**
 * The Class TestUtil.
 */
public final class TestUtil {
    /**
     * Instantiates a new dB utils.
     */
    private TestUtil() {
        throw new AssertionError("The class TestUtil is a utility class"
                + " and should not be instanciated");
    }

    /** The Constant APPLICATION_JSON_UTF8. */
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
            MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    /**
     * Creates a new random industry.
     * 
     * @return the industry
     */
    public static Industry randomIndustry() {
        Industry industry = new Industry();
        industry.setActive(new Random().nextBoolean());
        industry.setId((long) Math.random());
        industry.setDescription("industryDescription" + Math.random());
        industry.setName("nameIndustry" + Math.random());
        return industry;
    }

    /**
     * Creates a new random company.
     * 
     * @return the company
     */
    public static Company randomCompany() {
        Company company = new Company();
        company.setId((long) Math.random());
        company.setName("companyName" + Math.random());
        company.setDescription("companyDescription" + Math.random());
        company.setStockPrice(Math.random());
        company.setActive(new Random().nextBoolean());
        company.setIndustry(randomIndustry());
        return company;
    }
}
