package org.dj.twittertrader.swn;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import cmu.arktweetnlp.util.BasicFileIO;

import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.engine.Word;
import com.swabunga.spell.event.SpellChecker;

/**
 * The Class SpellCheckerManager.
 */
@Component
public class SpellCheckerManager {

    /** The Constant DICTIONARY_LOCATION. */
    private static final String DICTIONARY_LOCATION = "/dictionary.txt";

    /** The dictionary. */
    private SpellDictionaryHashMap dictionary = null;

    /** The spell checker. */
    private SpellChecker spellChecker = null;

    /**
     * Instantiates a new spell checker manager.
     */
    public SpellCheckerManager() {

        try {
            dictionary = new SpellDictionaryHashMap(
                    BasicFileIO.openFileOrResource(DICTIONARY_LOCATION));
        } catch (IOException e) {

            e.printStackTrace();
        }
        spellChecker = new SpellChecker(dictionary);
    }

    /**
     * Checks if is valid word.
     * 
     * @param word
     *            the word
     * @return true, if is valid word
     */
    public final boolean isValidWord(String word) {
        return spellChecker.isCorrect(word);
    }

    /**
     * Gets the suggestions.
     * 
     * @param word
     *            the word
     * @param threshold
     *            the threshold
     * @return the suggestions
     */
    @SuppressWarnings("unchecked")
    public final List<Word> getSuggestions(final String word, final int threshold) {
        return spellChecker.getSuggestions(word, threshold);
    }

    public static void main(String[] args) {
        SpellCheckerManager manager = new SpellCheckerManager();
        for (Word suggestion : manager.getSuggestions("amazon", 0)) {
            System.out.println(suggestion.getWord());
        }
    }
}
