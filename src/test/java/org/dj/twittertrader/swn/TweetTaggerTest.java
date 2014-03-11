/*
 * 
 */
package org.dj.twittertrader.swn;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import cmu.arktweetnlp.Tagger.TaggedToken;

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
     * Assert token.
     * 
     * @param taggedToken
     *            the tagged token
     * @param token
     *            the token
     * @param tag
     *            the tag
     */
    private void assertToken(TaggedToken taggedToken, String token, String tag) {
        assertEquals(token, taggedToken.token);
        assertEquals(tag, taggedToken.tag);

    }

}
