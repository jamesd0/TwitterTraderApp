/*
 * 
 */
package org.dj.twittertrader.swn;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import org.dj.twittertrader.messaging.MessagingBroker;
import org.dj.twittertrader.messaging.impl.CompanyTopWords;
import org.dj.twittertrader.messaging.impl.CompanyTweet;
import org.dj.twittertrader.model.Company;
import org.dj.twittertrader.model.Tweet;
import org.dj.twittertrader.model.User;
import org.junit.BeforeClass;
import org.junit.Test;

import cmu.arktweetnlp.Tagger.TaggedToken;

// TODO: Auto-generated Javadoc
/**
 * The Class TweetTaggerTest.
 */
public class TweetTaggerTest {

    /** The tagger. */
    private static TweetTagger tagger;

    /**
     * Sets the up.
     * 
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public static final void setUp() throws Exception {
        tagger = new TweetTagger();
        tagger.setAcronymTagger(new AcronymTagger());
        tagger.setSpellCheckerManager(new SpellCheckerManager());
    }

    /**
     * Should give noun when given chocolate.
     */
    @Test
    public final void shouldGiveNounWhenGivenChocolate() {
        String text = "chocolate";
        List<TaggedToken> tokens = tagger.tokenizeAndTag(text);
        assertEquals(1, tokens.size());
        assertEquals("chocolate", tokens.get(0).token);
        assertEquals("N", tokens.get(0).tag);
    }

    /**
     * Should give adj when given good.
     */
    @Test
    public final void shouldGiveAdjWhenGivenGood() {
        String text = "good";
        List<TaggedToken> tokens = tagger.tokenizeAndTag(text);
        assertEquals(1, tokens.size());
        assertToken(tokens.get(0), "good", "A");
    }

    /**
     * Should give laugh out loud when given lol.
     */
    @Test
    public final void shouldGiveLaughOutLoudWhenGivenLOL() {
        String text = "lol";
        List<TaggedToken> tokens = tagger.tokenizeAndTag(text);

        assertEquals(3, tokens.size());
        assertToken(tokens.get(0), "laugh", "V");
        assertToken(tokens.get(1), "out", "T");
        assertToken(tokens.get(2), "loud", "A");
    }

    /**
     * Should give awesome when given awsome.
     */
    @Test
    public final void shouldGiveAwesomeWhenGivenAewsome() {
        String text = "aewsome";
        List<TaggedToken> tokens = tagger.tokenizeAndTag(text);

        assertEquals(1, tokens.size());
        assertToken(tokens.get(0), "awesome", "A");
    }

    /**
     * Deal with new status test.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public final void dealWithNewStatusTest() throws IOException {
        Tweet tweet = mock(Tweet.class);
        Company company = mock(Company.class);
        User user = mock(User.class);
        SentimentClassifier classifier = mock(SentimentClassifier.class);
        MessagingBroker broker = mock(MessagingBroker.class);

        tagger.setClassifier(classifier);
        tagger.setBroker(broker);

        when(tweet.getText()).thenReturn("This is text for tokenising");
        when(tweet.getUser()).thenReturn(user);
        when(user.getUserScore()).thenReturn(100.0);
        when(tweet.getRetweetCount()).thenReturn((long) 10.0);
        when(classifier.classify(tweet.getText())).thenReturn(4.99);

        tagger.dealWithNewStatus(tweet, company);

        verify(tweet, times(1)).setTweetScore(5489.0);
        verify(broker, times(1)).upload(
                CompanyTopWords.getBrokerMessage(new CompanyTopWords(company, tagger
                        .getTopWords(company))));
        verify(broker, times(1)).upload(
                CompanyTweet.getBrokerMessage(new CompanyTweet(company, tweet)));
    }

    /**
     * Test get word net tag n.
     */
    @Test
    public final void testGetWordNetTagN() {
        String tag = "";
        tag = tagger.getWordNetTag("N");
        assertEquals("n", tag);
    }

    /**
     * Test get word net tag o.
     */
    @Test
    public final void testGetWordNetTagO() {
        String tag = "";
        tag = tagger.getWordNetTag("O");
        assertEquals("n", tag);
    }

    /**
     * Test get word net tag hat.
     */
    @Test
    public final void testGetWordNetTagHat() {
        String tag = "";
        tag = tagger.getWordNetTag("^");
        assertEquals("n", tag);
    }

    /**
     * Test get word net tag s.
     */
    @Test
    public final void testGetWordNetTagS() {
        String tag = "";
        tag = tagger.getWordNetTag("S");
        assertEquals("n", tag);
    }

    @Test
    public final void testGetWordNetTagZ() {
        String tag = "";
        tag = tagger.getWordNetTag("Z");
        assertEquals("n", tag);
    }

    /**
     * Test get word net tag v.
     */
    @Test
    public final void testGetWordNetTagV() {
        String tag = "";
        tag = tagger.getWordNetTag("V");
        assertEquals("v", tag);
    }

    /**
     * Test get word net tag l.
     */
    @Test
    public final void testGetWordNetTagL() {
        String tag = "";
        tag = tagger.getWordNetTag("L");
        assertEquals("v", tag);
    }

    /**
     * Test get word net tag m.
     */
    @Test
    public final void testGetWordNetTagM() {
        String tag = "";
        tag = tagger.getWordNetTag("M");
        assertEquals("v", tag);
    }

    /**
     * Test get word net tag a.
     */
    @Test
    public final void testGetWordNetTagA() {
        String tag = "";
        tag = tagger.getWordNetTag("A");
        assertEquals("a", tag);
    }

    /**
     * Test get word net tag all others.
     */
    @Test
    public final void testGetWordNetTagAllOthers() {
        String tag = "";
        tag = tagger.getWordNetTag("P");
        assertEquals("r", tag);
    }

    @Test
    public final void testGetScoreOfString() {
        SentimentClassifier classifier = mock(SentimentClassifier.class);
        tagger.setClassifier(classifier);

        when(classifier.classify("This is a lovely tweet")).thenReturn(100.0);
        double score = 0;
        score = tagger.getScoreOfString("This is a lovely tweet");
        assertEquals(100.0, score, 0.0);
    }

    @Test
    public final void testGetDictionaryScoreOfString() {
        SentiWordNet wordnet = mock(SentiWordNet.class);
        tagger.setSentiWordNet(wordnet);

        when(wordnet.extract(anyString(), anyString())).thenReturn(100.0);
        double score = 0;
        score = tagger.getDictionaryLookupScoreOfString("This is a lovely tweet");
        assertEquals(500.0, score, 0.0);
    }

    /**
     * Assert token.
     * 
     * @param taggedToken
     *            the tagged token
     * @param token
     *            the token
     * @param tag
     *            the tag
     */
    private void assertToken(final TaggedToken taggedToken, final String token, final String tag) {
        assertEquals(token, taggedToken.token);
        assertEquals(tag, taggedToken.tag);

    }

}
