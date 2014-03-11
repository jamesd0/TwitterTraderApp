package org.dj.twittertrader.ir;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import cmu.arktweetnlp.util.BasicFileIO;

/**
 * The Class StopAnalyser.
 */
@Component
public class StopAnalyser {

    /** The stop words. */
    private List<String> stopWords = new ArrayList<String>();

    /**
     * Instantiates a new stop analyser.
     */
    public StopAnalyser() {
        init();
    }

    /**
     * Gets the stop words.
     * 
     * @return the stop words
     */
    public final List<String> getStopWords() {
        return stopWords;
    }

    /**
     * Checks if is stop word.
     * 
     * @param word
     *            the word
     * @return the boolean
     */
    public final Boolean isStopWord(final String word) {
        List<String> words = Collections.synchronizedList(stopWords);
        return words.contains(word);
    }

    /**
     * Inits the.
     */
    private void init() {
        String stopwords = "/stopwords.txt";
        BufferedReader in = null;
        String stopWord;

        try {
            in = BasicFileIO.openFileOrResource(stopwords);

            while ((stopWord = in.readLine()) != null) {

                stopWords.add(stopWord);
            }
        } catch (IOException e) {
            System.err.println("Sentiment analysis term file ./lib/stopwords.txt lost");
        }
    }

}
