package org.dj.twittertrader.swn;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import cmu.arktweetnlp.util.BasicFileIO;

/**
 * The Class AcronymTagger.
 */
@Component
public class AcronymTagger {

    /** The path to swn. */
    private String pathToSWN = "/acronyms.txt";

    /** The _dict. */
    private Map<String, String> dictionary;

    /** The csv. */
    private BufferedReader csv;

    /**
     * Instantiates a new acronym tagger.
     */
    public AcronymTagger() {
        dictionary = new HashMap<String, String>();
        try {
            csv = BasicFileIO.openFileOrResource(pathToSWN);

            String line = "";
            while ((line = csv.readLine()) != null) {

                String[] data = line.split("!!");
                dictionary.put(data[0].toLowerCase(), data[1].toLowerCase());
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Gets the dictionary.
     * 
     * @return the dictionary
     */
    public final Map<String, String> getDictionary() {
        return dictionary;
    }

    /**
     * Prints the dict.
     */
    public final void printDict() {
        for (String key : dictionary.keySet()) {
            System.out.println("Key: " + key + " value: " + dictionary.get(key));
        }

    }

    /**
     * The main method.
     * 
     * @param args
     *            the arguments
     */
    public static void main(final String[] args) {
        AcronymTagger tagger = new AcronymTagger();
        tagger.printDict();
    }

}
