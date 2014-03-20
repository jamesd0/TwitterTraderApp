package org.dj.twittertrader.swn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dj.twittertrader.ir.StopAnalyser;
import org.dj.twittertrader.messaging.MessagingBroker;
import org.dj.twittertrader.messaging.impl.CompanyTopWords;
import org.dj.twittertrader.messaging.impl.CompanyTweet;
import org.dj.twittertrader.model.Company;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swabunga.spell.engine.Word;

/**
 * The Class TweetTagger.
 */
@Component
public class TweetTagger extends Tagger {

    /** The Constant MIN_WORD_LENGTH. */
    private static int MIN_WORD_LENGTH = 3;

    /** The Constant URL_REGEX. */
    private static String URL_REGEX = "<\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]"
            + "*[-a-zA-Z0-9+&@#/%=~_|]>";

    /** The Constant NO_OF_TOP_WORDS. */
    private static int NO_OF_TOP_WORDS = 50;

    /** The logger. */
    private Logger logger = LoggerFactory.getLogger(TweetTagger.class);

    /** The Constant MODEL_LOCATION. */
    private static String MODEL_LOCATION = "/modelTagger.txt";

    /** The Constant AVERAGE_RETWEET. */
    private static long AVERAGE_RETWEET = 1;

    /** The a tagger. */
    @Autowired
    private AcronymTagger acronymTagger = new AcronymTagger();

    /** The spell checker manager. */
    @Autowired
    private SpellCheckerManager spellCheckerManager = new SpellCheckerManager();

    /** The senti word net. */
    @Autowired
    /** The tf idf. */
    private Map<String, Map<String, Integer>> tfIdf;

    /** The stop analyser. */
    @Autowired
    private StopAnalyser stopAnalyser = new StopAnalyser();

    /** The broker. */
    @Autowired
    private MessagingBroker broker;

    /** The classifier. */
    @Autowired
    private SentimentClassifier classifier;

    public Map<String, Map<String, Integer>> getTfIdf() {
        return tfIdf;
    }

    public void setTfIdf(Map<String, Map<String, Integer>> tfIdf) {
        this.tfIdf = tfIdf;
    }

    public StopAnalyser getStopAnalyser() {
        return stopAnalyser;
    }

    public void setStopAnalyser(StopAnalyser stopAnalyser) {
        this.stopAnalyser = stopAnalyser;
    }

    public SentimentClassifier getClassifier() {
        return classifier;
    }

    public void setClassifier(SentimentClassifier classifier) {
        this.classifier = classifier;
    }

    public Map<String, Integer> getDocumentFrequencyMap() {
        return documentFrequencyMap;
    }

    public void setDocumentFrequencyMap(Map<String, Integer> documentFrequencyMap) {
        this.documentFrequencyMap = documentFrequencyMap;
    }

    public Map<String, List<Map<String, Double>>> getPreviousTopWords() {
        return previousTopWords;
    }

    public void setPreviousTopWords(Map<String, List<Map<String, Double>>> previousTopWords) {
        this.previousTopWords = previousTopWords;
    }

    /** The senti word net. */
    @Autowired
    private SentiWordNet sentiWordNet;

    public SentiWordNet getSentiWordNet() {
        return sentiWordNet;
    }

    public void setSentiWordNet(SentiWordNet sentiWordNet) {
        this.sentiWordNet = sentiWordNet;
    }

    /** The document frequency map. */
    private Map<String, Integer> documentFrequencyMap;

    /** The previous top words. */
    private Map<String, List<Map<String, Double>>> previousTopWords;

    /**
     * Instantiates a new tweet tagger.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public TweetTagger() throws IOException {
        super();
        loadModel(MODEL_LOCATION);
        tfIdf = new HashMap<String, Map<String, Integer>>();
        documentFrequencyMap = new HashMap<String, Integer>();
        previousTopWords = new HashMap<String, List<Map<String, Double>>>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see cmu.arktweetnlp.Tagger#tokenizeAndTag(java.lang.String)
     */
    @Override
    public List<TaggedToken> tokenizeAndTag(String text) {
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
     * Gets the word net tag.
     * 
     * @param tag
     *            the tag
     * @return the word net tag
     */
    public String getWordNetTag(String tag) {
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
    public AcronymTagger getAcronymTagger() {
        return acronymTagger;
    }

    /**
     * Sets the acronym tagger.
     * 
     * @param acronymTagger
     *            the new acronym tagger
     */
    public void setAcronymTagger(AcronymTagger acronymTagger) {
        this.acronymTagger = acronymTagger;
    }

    /**
     * Gets the spell checker manager.
     * 
     * @return the spell checker manager
     */
    public SpellCheckerManager getSpellCheckerManager() {
        return spellCheckerManager;
    }

    /**
     * Sets the spell checker manager.
     * 
     * @param spellCheckerManager
     *            the new spell checker manager
     */
    public void setSpellCheckerManager(SpellCheckerManager spellCheckerManager) {
        this.spellCheckerManager = spellCheckerManager;
    }

    /**
     * Tokenise.
     * 
     * @param tweet
     *            the tweet
     * @return the list
     */
    public List<TaggedToken> tokenise(Tweet tweet) {
        return tokenizeAndTag(tweet.getText());
    }

    /**
     * Gets the tweet score.
     * 
     * @param tweet
     *            the text
     * @return the tweet score
     */
    public double getTweetScore(Tweet tweet) {
        double score = 0;
        score = classifier.classify(tweet.getText());
        score *= tweet.getUser().getUserScore();
        score *= ((tweet.getRetweetCount() + 1) / AVERAGE_RETWEET);
        return score;
    }

    /**
     * Gets the score of string.
     * 
     * @param tweet
     *            the tweet
     * @return the score of string
     */
    public double getScoreOfString(String tweet) {
        return classifier.classify(tweet);
    }

    /**
     * Gets the dictionary lookup score of string.
     * 
     * @param tweet
     *            the tweet
     * @return the dictionary lookup score of string
     */
    public double getDictionaryLookupScoreOfString(String tweet) {
        double score = 0;
        List<TaggedToken> tokens = tokenizeAndTag(tweet);
        for (TaggedToken token : tokens) {
            score += sentiWordNet.extract(token.token, getWordNetTag(token.tag));
        }
        return score;
    }

    /**
     * Perform tf idf.
     * 
     * @param tokens
     *            the tokens
     * @param company
     *            the company
     */
    public void addTokensToMap(List<TaggedToken> tokens, Company company) {
        if (!tfIdf.containsKey(company.getStockSymbol())) {
            tfIdf.put(company.getStockSymbol(), new HashMap<String, Integer>());
        }
        Map<String, Boolean> inTweetAlreadyMap = new HashMap<String, Boolean>();
        Map<String, Integer> tokenMap = tfIdf.get(company.getStockSymbol());
        for (TaggedToken token : tokens) {
            if (!stopAnalyser.isStopWord(token.token) && !isRemovableWord(token.token)) {
                if (!tokenMap.containsKey(token.token)) {
                    tokenMap.put(token.token, 1);
                } else {
                    tokenMap.put(token.token, tokenMap.get(token.token) + 1);
                }
                if (!inTweetAlreadyMap.containsKey(token.token)
                        || !inTweetAlreadyMap.get(token.token)) {
                    if (!documentFrequencyMap.containsKey(token.token)) {
                        documentFrequencyMap.put(token.token, 1);
                    } else {
                        documentFrequencyMap.put(token.token,
                                documentFrequencyMap.get(token.token) + 1);
                    }
                    inTweetAlreadyMap.put(token.token, true);
                }
            }
        }
    }

    /**
     * Checks if is removable word.
     * 
     * @param token
     *            the token
     * @return true, if is removable word
     */
    private boolean isRemovableWord(String token) {
        Pattern pattern = Pattern.compile(URL_REGEX);
        Matcher matcher = pattern.matcher(token);
        if (matcher.matches()) {
            return true;
        }
        if (token.length() < MIN_WORD_LENGTH) {
            return true;
        }
        if (token.contains("://")) {
            return true;
        }
        return token.matches(".*[^\\x20-\\x7E].*");
    }

    /**
     * Gets the top words.
     * 
     * @param company
     *            the company
     * @return the top words
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<Map<String, Double>> getTopWords(Company company) {
        List<Map<String, Double>> topWords = new ArrayList<Map<String, Double>>();
        Map<String, Double> termToTfIdf = new TreeMap<String, Double>();
        Map<String, Integer> tokenMap = tfIdf.get(company.getStockSymbol());
        for (String token : tokenMap.keySet()) {
            double tfIdf = 0;
            double termFrequency = tokenMap.get(token);
            double documentFrequency = calculateDocumentFrequency(token);
            tfIdf = termFrequency / documentFrequency;
            termToTfIdf.put(token, tfIdf);
        }
        List<Entry<String, Double>> entrySet = new ArrayList<Entry<String, Double>>();
        entrySet.addAll(termToTfIdf.entrySet());
        Collections.sort(entrySet, new Comparator() {

            @Override
            public int compare(Object o1, Object o2) {
                return ((Entry<String, Double>) o1).getValue().compareTo(
                        ((Entry<String, Double>) o2).getValue());
            }

        });
        if (entrySet.size() < NO_OF_TOP_WORDS) {
            for (int i = 0; i < entrySet.size(); i++) {
                Map<String, Double> tempMap = new HashMap<String, Double>();
                tempMap.put(entrySet.get(i).getKey(), entrySet.get(i).getValue());
                topWords.add(tempMap);
            }
        } else {
            for (int i = entrySet.size() - NO_OF_TOP_WORDS; i < entrySet.size(); i++) {
                Map<String, Double> tempMap = new HashMap<String, Double>();
                tempMap.put(entrySet.get(i).getKey(), entrySet.get(i).getValue());
                topWords.add(tempMap);
            }
        }
        return topWords;
    }

    /**
     * Calculate document frequency.
     * 
     * @param token
     *            the token
     * @return the int
     */
    private int calculateDocumentFrequency(String token) {
        return documentFrequencyMap.get(token);
    }

    /**
     * Sets the model.
     * 
     * @param model
     *            the new model
     */
    public void setModel(Model model) {
        this.model = model;

    }

    /**
     * Deal with new status.
     * 
     * @param tweet
     *            the tweet
     * @param company
     *            the company
     * @return the tweet
     */
    public Tweet dealWithNewStatus(Tweet tweet, Company company) {
        List<TaggedToken> tokens = tokenise(tweet);
        tweet.setTweetScore(getTweetScore(tweet));
        addTokensToMap(tokens, company);
        try {
            List<Map<String, Double>> currentTopWords = getTopWords(company);
            if (!currentTopWords.equals(previousTopWords.get(company.getStockSymbol()))) {

                previousTopWords.put(company.getStockSymbol(), currentTopWords);
                broker.upload(CompanyTopWords.getBrokerMessage(new CompanyTopWords(company,
                        getTopWords(company))));
            }
            broker.upload(CompanyTweet.getBrokerMessage(new CompanyTweet(company, tweet)));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tweet;
    }

    public MessagingBroker getBroker() {
        return broker;
    }

    public void setBroker(MessagingBroker broker) {
        this.broker = broker;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + ((acronymTagger == null) ? 0 : acronymTagger.hashCode());
        result = prime * result + ((broker == null) ? 0 : broker.hashCode());
        result = prime * result + ((classifier == null) ? 0 : classifier.hashCode());
        result = prime * result
                + ((documentFrequencyMap == null) ? 0 : documentFrequencyMap.hashCode());
        result = prime * result + ((logger == null) ? 0 : logger.hashCode());
        result = prime * result + ((previousTopWords == null) ? 0 : previousTopWords.hashCode());
        result = prime * result + ((sentiWordNet == null) ? 0 : sentiWordNet.hashCode());
        result = prime * result
                + ((spellCheckerManager == null) ? 0 : spellCheckerManager.hashCode());
        result = prime * result + ((stopAnalyser == null) ? 0 : stopAnalyser.hashCode());
        result = prime * result + ((tfIdf == null) ? 0 : tfIdf.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
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
        if (broker == null) {
            if (other.broker != null) {
                return false;
            }
        } else if (!broker.equals(other.broker)) {
            return false;
        }
        if (classifier == null) {
            if (other.classifier != null) {
                return false;
            }
        } else if (!classifier.equals(other.classifier)) {
            return false;
        }
        if (documentFrequencyMap == null) {
            if (other.documentFrequencyMap != null) {
                return false;
            }
        } else if (!documentFrequencyMap.equals(other.documentFrequencyMap)) {
            return false;
        }
        if (logger == null) {
            if (other.logger != null) {
                return false;
            }
        } else if (!logger.equals(other.logger)) {
            return false;
        }
        if (previousTopWords == null) {
            if (other.previousTopWords != null) {
                return false;
            }
        } else if (!previousTopWords.equals(other.previousTopWords)) {
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
        if (stopAnalyser == null) {
            if (other.stopAnalyser != null) {
                return false;
            }
        } else if (!stopAnalyser.equals(other.stopAnalyser)) {
            return false;
        }
        if (tfIdf == null) {
            if (other.tfIdf != null) {
                return false;
            }
        } else if (!tfIdf.equals(other.tfIdf)) {
            return false;
        }
        return true;
    }
}
