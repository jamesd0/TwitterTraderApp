package org.dj.twittertrader.swn;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.springframework.stereotype.Component;

import cmu.arktweetnlp.util.BasicFileIO;

/**
 * The Class SentiWordNet.
 */
@Component
public class SentiWordNet {

    /** The path to swn. */
    private String pathToSWN = "/SentiWordNet.txt";

    /** The _dict. */
    private HashMap<String, Double> dictionary;

    /** The csv. */
    private BufferedReader csv;

    /**
     * Instantiates a new senti word net.
     */
    public SentiWordNet() {

        dictionary = new HashMap<String, Double>();
        HashMap<String, Vector<Double>> temporary = new HashMap<String, Vector<Double>>();
        try {
            csv = BasicFileIO.openFileOrResource(pathToSWN);
            String line = "";
            while ((line = csv.readLine()) != null) {
                String[] data = line.split("\t");
                Double score = Double.parseDouble(data[2]) - Double.parseDouble(data[3]);
                String[] words = data[4].split(" ");
                for (String w : words) {
                    String[] wordNet = w.split("#");
                    wordNet[0] += "#" + data[0];
                    int index = Integer.parseInt(wordNet[1]) - 1;
                    if (temporary.containsKey(wordNet[0])) {
                        Vector<Double> v = temporary.get(wordNet[0]);
                        if (index > v.size()) {
                            for (int i = v.size(); i < index; i++) {
                                v.add(0.0);
                            }
                        }
                        v.add(index, score);
                        temporary.put(wordNet[0], v);
                    } else {
                        Vector<Double> v = new Vector<Double>();
                        for (int i = 0; i < index; i++) {
                            v.add(0.0);
                        }
                        v.add(index, score);
                        temporary.put(wordNet[0], v);
                    }
                }
            }
            Set<String> temp = temporary.keySet();
            for (Iterator<String> iterator = temp.iterator(); iterator.hasNext();) {
                String word = iterator.next();
                Vector<Double> v = temporary.get(word);
                double score = 0.0;
                double sum = 0.0;
                for (int i = 0; i < v.size(); i++) {
                    score += ((double) 1 / (double) (i + 1)) * v.get(i);
                }
                for (int i = 1; i <= v.size(); i++) {
                    sum += (double) 1 / (double) i;
                }
                score /= sum;
                dictionary.put(word, score);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csv.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Extract.
     * 
     * @param word
     *            the word
     * @param pos
     *            the pos
     * @return the double
     */
    public final Double extract(final String word, final String pos) {
        Double score = dictionary.get(word + "#" + pos);
        if (score == null) {
            score = (double) 0;
        }
        return score;
    }
}
