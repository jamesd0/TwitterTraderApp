/*
 * 
 */
package org.dj.twittertrader.utils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.dj.twittertrader.model.Company;
import org.dj.twittertrader.model.Industry;
import org.dj.twittertrader.model.Portfolio;
import org.dj.twittertrader.model.Tweet;
import org.dj.twittertrader.model.User;
import org.springframework.http.MediaType;

/**
 * The Class TestUtil.
 */
public final class TestUtil {

    /** The Constant SCORE_CEILING. */
    private static final int SCORE_CEILING = 100;

    /** The Constant TEST_LIST_CEILING. */
    private static final int TEST_LIST_MAX = 10;

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
        industry.setDescription("industryDescription" + new Random().nextInt());
        industry.setName("nameIndustry" + new Random().nextInt());
        industry.setCompanies(randomCompanies());
        return industry;
    }

    /**
     * Creates a new random company.
     * 
     * @return the company
     */
    public static Company randomCompany() {
        Company company = new Company();
        company.setId(new Random().nextLong());
        company.setName("companyName" + new Random().nextInt());
        company.setDescription("companyDescription" + new Random().nextInt());
        company.setStockPrice(new Random().nextDouble());
        company.setActive(new Random().nextBoolean());
        company.setCompanyScore(new Random().nextInt(SCORE_CEILING));
        company.setTags(randomTags());
        company.setTweets(randomTweets());
        return company;
    }

    /**
     * Creates a new random user.
     * 
     * @return the user
     */
    public static User randomUser() {
        User user = new User();
        user.setActive(new Random().nextBoolean());
        user.setCreatedAt(new Date(new Random().nextLong()));
        user.setFavouritesCount(new Random().nextInt());
        user.setFollowersCount(new Random().nextInt());
        user.setFriendsCount(new Random().nextInt());
        user.setId(new Random().nextInt());
        user.setLang("lang" + new Random().nextInt());
        user.setLocation("location" + new Random().nextInt());
        user.setName("name" + new Random().nextInt());
        user.setScreenName("screenName" + new Random().nextInt());
        user.setVerified(new Random().nextBoolean());
        user.setUserScore(new Random().nextInt(SCORE_CEILING));
        return user;
    }

    /**
     * Creates a new random tweet.
     * 
     * @return the tweet
     */
    public static Tweet randomTweet() {
        Tweet tweet = new Tweet();
        tweet.setActive(new Random().nextBoolean());
        tweet.setCreatedAt(new Date(new Random().nextInt()));
        tweet.setId(new Random().nextLong());
        tweet.setRetweetCount(new Random().nextLong());
        tweet.setText(new Random().nextInt() + "");
        tweet.setTweetScore(new Random().nextInt(SCORE_CEILING));
        tweet.setUser(randomUser());
        return tweet;
    }

    /**
     * Creates a new random portfolio.
     * 
     * @return the portfolio
     */
    public static Portfolio randomPortfolio() {
        Portfolio portfolio = new Portfolio();
        portfolio.setId(new Random().nextInt());
        portfolio.setOwner("Owner" + new Random().nextInt());
        portfolio.setUsername("UserName" + new Random().nextInt());
        portfolio.setPassword("Password" + new Random().nextInt());
        portfolio.setActive(new Random().nextBoolean());
        portfolio.setCompanies(randomCompanies());
        portfolio.setIndustries(randomIndustries());
        return portfolio;
    }

    /**
     * Creates a list of random tweets.
     * 
     * @return the list
     */
    private static List<Tweet> randomTweets() {
        List<Tweet> tweets = new ArrayList<Tweet>();
        for (int i = 0; i < new Random().nextInt(TEST_LIST_MAX); i++) {
            tweets.add(randomTweet());
        }
        return tweets;
    }

    /**
     * Random tags.
     * 
     * @return the list
     */
    private static List<String> randomTags() {
        List<String> tags = new ArrayList<String>();
        for (int i = 0; i < new Random().nextInt(TEST_LIST_MAX); i++) {
            tags.add("Tag" + new Random().nextInt());
        }
        return tags;
    }

    /**
     * Random companies.
     * 
     * @return the list
     */
    private static List<Company> randomCompanies() {
        List<Company> companies = new ArrayList<Company>();
        for (int i = 0; i < new Random().nextInt(TEST_LIST_MAX); i++) {
            companies.add(randomCompany());
        }
        return companies;
    }

    /**
     * Random industries.
     * 
     * @return the list
     */
    private static List<Industry> randomIndustries() {
        List<Industry> industries = new ArrayList<Industry>();
        for (int i = 0; i < new Random().nextInt(TEST_LIST_MAX); i++) {
            industries.add(randomIndustry());
        }
        return industries;
    }

}
