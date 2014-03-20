package org.dj.twittertrader.swn;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.aliasi.classify.ConditionalClassification;
import com.aliasi.classify.LMClassifier;
import com.aliasi.util.AbstractExternalizable;

/**
 * The Class Classifier.
 */
@Component
public class SentimentClassifier {

    /** The lmc. */
    private LMClassifier lmc;

    /**
     * Instantiates a new classifier.
     */
    @SuppressWarnings("unchecked")
    public SentimentClassifier() {
        try {
            lmc = (LMClassifier) AbstractExternalizable.readObject(new File(
                    "/home/duncan/git/TwitterTraderApp/src/main/resources/polarity.model"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Classify.
     * 
     * @param text
     *            the text
     * @return the double
     */
    public double classify(final String text) {
        ConditionalClassification classification = lmc.classify(text);
        double result = classification.conditionalProbability(classification.bestCategory());
        if (classification.bestCategory().equals("neg")) {
            result *= -1;
        }
        return result;
    }

}
