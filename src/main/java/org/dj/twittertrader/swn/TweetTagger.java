package org.dj.twittertrader.swn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dj.twittertrader.model.Tweet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cmu.arktweetnlp.Tagger;
import cmu.arktweetnlp.Twokenize;
import cmu.arktweetnlp.impl.Model;
import cmu.arktweetnlp.impl.ModelSentence;
import cmu.arktweetnlp.impl.Sentence;

import com.swabunga.spell.engine.Word;

/**
 * The Class TweetTagger.
 */
@Component
public class TweetTagger extends Tagger {
    private Logger logger = LoggerFactory.getLogger(TweetTagger.class);

    /** The Constant MODEL_LOCATION. */
    private static final String MODEL_LOCATION = "/modelTagger.txt";

    /** The Constant AVERAGE_RETWEET. */
    private static final long AVERAGE_RETWEET = 100;

    /** The a tagger. */
    @Autowired
    private AcronymTagger acronymTagger;

    /** The spell checker manager. */
    @Autowired
    private SpellCheckerManager spellCheckerManager;

    /** The senti word net. */
    @Autowired
    private SentiWordNet sentiWordNet;

    /**
     * Instantiates a new tweet tagger.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public TweetTagger() throws IOException {
        super();
        loadModel(MODEL_LOCATION);
    }

    /*
     * (non-Javadoc)
     * 
     * @see cmu.arktweetnlp.Tagger#tokenizeAndTag(java.lang.String)
     */
    @Override
    public final List<TaggedToken> tokenizeAndTag(final String text) {
        if (model == null) {
            throw new RuntimeException("Must loadModel() first before tagging anything");
        }
        List<String> tokens = Twokenize.tokenizeRawTweetText(text);
        Map<Integer, List<String>> acronymTokens = new HashMap<Integer, List<String>>();
        for (String t : tokens) {
            tokens.set(tokens.indexOf(t), t.toLowerCase());
            t = t.toLowerCase();
            if (acronymTagger.getDictionary().containsKey(t)) {
                acronymTokens.put(tokens.indexOf(t),
                        Twokenize.tokenizeRawTweetText(acronymTagger.getDictionary().get(t)));
            }
        }
        int count = 0;
        for (int i : acronymTokens.keySet()) {
            tokens.addAll(i + count, acronymTokens.get(i));
            if (acronymTokens.get(i).size() > 1) {
                count += acronymTokens.get(i).size() - 1;
            }
            tokens.remove(i + count + 1);
        }
        for (String token : tokens) {
            if (!spellCheckerManager.isValidWord(token)) {
                List<Word> suggestions = spellCheckerManager.getSuggestions(token, 0);
                if (suggestions.size() > 0) {
                    tokens.set(tokens.indexOf(token),
                            Twokenize.tokenizeRawTweetText(suggestions.get(0).getWord()).get(0));
                }
            }
        }
        Sentence sentence = new Sentence();
        sentence.tokens = tokens;
        ModelSentence ms = new ModelSentence(sentence.T());
        featureExtractor.computeFeatures(sentence, ms);
        model.greedyDecode(ms, false);

        ArrayList<TaggedToken> taggedTokens = new ArrayList<TaggedToken>();

        for (int t = 0; t < sentence.T(); t++) {
            TaggedToken tt = new TaggedToken();
            tt.token = tokens.get(t);
            tt.tag = model.labelVocab.name(ms.labels[t]);
            taggedTokens.add(tt);
        }
        return taggedTokens;
    }

    /**
     * The main method.
     * 
     * @param args
     *            the arguments
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static void main(final String[] args) throws IOException {
        TweetTagger tagger = new TweetTagger();
        String text = "love hate good bad Duncan misery freedom m8 PMFJI";
        List<TaggedToken> tokens = tagger.tokenizeAndTag(text);
        for (TaggedToken token : tokens) {
            System.out.println("Token: " + token.token + " tag: " + token.tag + " wordnettag: "
                    + tagger.getWordNetTag(token.tag));
        }
    }

    /**
     * Gets the word net tag.
     * 
     * @param tag
     *            the tag
     * @return the word net tag
     */
    public final String getWordNetTag(final String tag) {
        if (tag.equals("N") || tag.equals("O") || tag.equals("^") || tag.equals("S")
                || tag.equals("Z")) {
            return "n";
        } else if (tag.equals("V") || tag.equals("L") || tag.equals("M")) {
            return "v";
        } else if (tag.equals("A")) {
            return "a";
        } else {
            return "r";
        }
    }

    /**
     * Gets the acronym tagger.
     * 
     * @return the acronym tagger
     */
    public final AcronymTagger getAcronymTagger() {
        return acronymTagger;
    }

    /**
     * Sets the acronym tagger.
     * 
     * @param acronymTagger
     *            the new acronym tagger
     */
    public final void setAcronymTagger(final AcronymTagger acronymTagger) {
        this.acronymTagger = acronymTagger;
    }

    /**
     * Gets the spell checker manager.
     * 
     * @return the spell checker manager
     */
    public final SpellCheckerManager getSpellCheckerManager() {
        return spellCheckerManager;
    }

    /**
     * Sets the spell checker manager.
     * 
     * @param spellCheckerManager
     *            the new spell checker manager
     */
    public final void setSpellCheckerManager(final SpellCheckerManager spellCheckerManager) {
        this.spellCheckerManager = spellCheckerManager;
    }

    /**
     * Gets the tweet score.
     * 
     * @param tweet
     *            the text
     * @return the tweet score
     */
    public final double getTweetScore(final Tweet tweet) {
        double score = 0;
        List<TaggedToken> tokens = tokenizeAndTag(tweet.getText());
        for (TaggedToken token : tokens) {
            score += sentiWordNet.extract(token.token, getWordNetTag(token.tag));
        }
        logger.info("UserScore" + tweet.getUser().getUserScore() + "RetweetCount"
                + (tweet.getRetweetCount() + 1));
        score *= (((tweet.getRetweetCount() + 1) * (tweet.getUser().getUserScore()) + 1) / AVERAGE_RETWEET);
        return score;
    }

    /**
     * Sets the model.
     * 
     * @param model
     *            the new model
     */
    public final void setModel(final Model model) {
        this.model = model;

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((acronymTagger == null) ? 0 : acronymTagger.hashCode());
        result = prime * result + ((sentiWordNet == null) ? 0 : sentiWordNet.hashCode());
        result = prime * result
                + ((spellCheckerManager == null) ? 0 : spellCheckerManager.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof TweetTagger)) {
            return false;
        }
        TweetTagger other = (TweetTagger) obj;
        if (acronymTagger == null) {
            if (other.acronymTagger != null) {
                return false;
            }
        } else if (!acronymTagger.equals(other.acronymTagger)) {
            return false;
        }
        if (sentiWordNet == null) {
            if (other.sentiWordNet != null) {
                return false;
            }
        } else if (!sentiWordNet.equals(other.sentiWordNet)) {
            return false;
        }
        if (spellCheckerManager == null) {
            if (other.spellCheckerManager != null) {
                return false;
            }
        } else if (!spellCheckerManager.equals(other.spellCheckerManager)) {
            return false;
        }
        return true;
    }

}
