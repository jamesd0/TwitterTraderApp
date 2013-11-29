package org.dj.twittertrader.controller;

import java.util.List;

import org.dj.twittertrader.swn.SentiWordNet;
import org.dj.twittertrader.swn.TweetTagger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import cmu.arktweetnlp.Tagger.TaggedToken;

/**
 * The Class SentimentAnalysisController.
 */
@Controller
@RequestMapping("/senti")
public class SentimentAnalysisController {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(SentimentAnalysisController.class);

    /** The tagger. */
    @Autowired
    private TweetTagger tagger;

    /** The wordnet. */
    @Autowired
    private SentiWordNet wordnet;

    /**
     * Test.
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public final void test() {
        String text = "love hate good bad Duncan hellp misery freedom m8 PMFJI lol";
        List<TaggedToken> tokens = tagger.tokenizeAndTag(text);
        for (TaggedToken token : tokens) {
            LOGGER.info("Token: " + token.token + " tag: " + token.tag + " wordnettag: "
                    + tagger.getWordNetTag(token.tag) + " score: "
                    + wordnet.extract(token.token, tagger.getWordNetTag(token.tag)));
        }
    }
}
