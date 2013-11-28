package org.dj.twittertrader.swn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cmu.arktweetnlp.Tagger;
import cmu.arktweetnlp.Twokenize;
import cmu.arktweetnlp.impl.ModelSentence;
import cmu.arktweetnlp.impl.Sentence;

/**
 * The Class TweetTagger.
 */
@Component
public class TweetTagger extends Tagger {

    /** The Constant MODEL_LOCATION. */
    private static final String MODEL_LOCATION = "/modelTagger.txt";

    /** The a tagger. */
    @Autowired
    private AcronymTagger aTagger;

    /** The spell checker manager. */
    @Autowired
    private SpellCheckerManager spellCheckerManager;

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
            if (aTagger.getDictionary().containsKey(t)) {
                acronymTokens.put(tokens.indexOf(t),
                        Twokenize.tokenizeRawTweetText(aTagger.getDictionary().get(t)));
            }
        }
        for (int i : acronymTokens.keySet()) {
            tokens.addAll(i, acronymTokens.get(i));
            tokens.remove(i + acronymTokens.get(i).size());
        }
        for (String token : tokens) {
            if (!spellCheckerManager.isValidWord(token)) {
                List<String> suggestions = Twokenize.tokenizeRawTweetText(spellCheckerManager
                        .getSuggestions(token, 0).get(0).getWord());
                if (suggestions.size() > 0) {
                    tokens.set(tokens.indexOf(token), suggestions.get(0));
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
        SentiWordNet wordnet = new SentiWordNet();
        for (TaggedToken token : tokens) {
            System.out.println("Token: " + token.token + " tag: " + token.tag + " wordnettag: "
                    + tagger.getWordNetTag(token.tag) + " score: "
                    + wordnet.extract(token.token, tagger.getWordNetTag(token.tag)));
        }
    }

    /**
     * Gets the word net tag.
     * 
     * @param tag
     *            the tag
     * @return the word net tag
     */
    public String getWordNetTag(final String tag) {
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
}
