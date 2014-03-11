package org.dj.twittertrader.swn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.springframework.stereotype.Component;

import cmu.arktweetnlp.util.BasicFileIO;

import com.aliasi.classify.BaseClassifierEvaluator;
import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.ConditionalClassification;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.lm.NGramProcessLM;
import com.aliasi.util.AbstractExternalizable;

/**
 * The Class Classifier.
 */
@Component
public class SentimentClassifier {

    /** The Constant PERCENTAGE. */
    private static final int PERCENTAGE = 100;

    /** The Constant NANO_TO_SECONDS. */
    private static final int NANO_TO_SECONDS = 1000000000;

    /** The Constant NGRAM_SIZES. */
    private static final int[] NGRAM_SIZES = { 1, 2, 3, 4, 5, 6, 7, 8 };

    /** The Constant TESTING_THRESHOLD. */
    private static final int TESTING_THRESHOLD = 1000000;

    /** The lmc. */
    private DynamicLMClassifier<NGramProcessLM> lmc;

    /** The categories. */
    private String[] categories = { "pos", "neg" };

    /**
     * Instantiates a new classifier.
     */
    @SuppressWarnings("unchecked")
    public SentimentClassifier() {
        try {
            lmc = (DynamicLMClassifier<NGramProcessLM>) AbstractExternalizable.readObject(new File(
                    "/home/duncan/git/TwitterTraderApp/src/main/resources/polarity.model"));
            categories = lmc.categories();
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
    public final double classify(final String text) {
        ConditionalClassification classification = lmc.classify(text);
        double result = classification.conditionalProbability(classification.bestCategory());
        if (classification.bestCategory().equals("neg")) {
            result *= -1;
        }
        return result;
    }

    /**
     * Train classifier on csv.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void trainClassifierOnCSV() throws IOException {
        int numTrainingCases = 0;
        int numTrainingChars = 0;
        String csvFile = "/SentimentAnalysisDataset.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        Classification classificationPos = new Classification("pos");
        Classification classificationNeg = new Classification("neg");
        try {

            br = BasicFileIO.openFileOrResource(csvFile);
            boolean firstLine = true;
            while ((line = br.readLine()) != null && numTrainingCases < TESTING_THRESHOLD) {

                // use comma as separator
                if (!firstLine) {
                    ++numTrainingCases;
                    String[] lineSplit = line.split(cvsSplitBy);
                    String review = lineSplit[3];
                    numTrainingChars += review.length();
                    String classification = lineSplit[1];
                    if (classification.equals("1")) {
                        Classified<CharSequence> classified = new Classified<CharSequence>(review,
                                classificationPos);
                        lmc.handle(classified);
                    } else {
                        Classified<CharSequence> classified = new Classified<CharSequence>(review,
                                classificationNeg);
                        lmc.handle(classified);
                    }

                } else {
                    firstLine = false;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // com.aliasi.util.AbstractExternalizable.compileTo(mClassifier, new
        // File("polarity.model"));
        System.out.print(" Training Cases: " + numTrainingCases);
    }

    /**
     * Evaluate on csv.
     * 
     * @throws Exception
     *             the exception
     */
    private void evaluateClassifierOnCSV() throws Exception {
        boolean storeInstances = false;
        BaseClassifierEvaluator<CharSequence> evaluator = new BaseClassifierEvaluator<CharSequence>(
                null, categories, storeInstances);
        String csvFile = "/SentimentAnalysisDataset.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        int lineNumber = 0;
        long startTime = 0;
        long finishTime = 0;
        try {

            br = BasicFileIO.openFileOrResource(csvFile);
            startTime = System.nanoTime();
            while ((line = br.readLine()) != null) {
                if (lineNumber > TESTING_THRESHOLD) {
                    String[] lineSplit = line.split(cvsSplitBy);
                    String review = lineSplit[3];
                    String category = lineSplit[1];
                    if (category.equals("1")) {
                        category = "pos";
                    } else {
                        category = "neg";
                    }
                    Classification classification = lmc.classify(review);
                    evaluator.addClassification(category, classification, null);
                }

                lineNumber++;
            }
            finishTime = System.nanoTime();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String[] lines = evaluator.toString().split("\\n");
        System.out.print("Accuracy: " + lines[4]);
        System.out.println(" Time(s): " + (double) (finishTime - startTime) / NANO_TO_SECONDS);
    }

    /**
     * Evaluate dictionary on csv.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void evaluateDictionaryOnCSV() throws IOException {
        String csvFile = "/SentimentAnalysisDataset.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        int lineNumber = 0;
        int dictionaryLookupCorrect = 0;
        long startTime = 0;
        long finishTime = 0;
        TweetTagger tagger = new TweetTagger();
        try {

            br = BasicFileIO.openFileOrResource(csvFile);
            startTime = System.nanoTime();
            while ((line = br.readLine()) != null) {
                if (lineNumber > TESTING_THRESHOLD) {
                    String[] lineSplit = line.split(cvsSplitBy);
                    String review = lineSplit[3];
                    String category = lineSplit[1];
                    if (category.equals("1")) {
                        category = "pos";
                    } else {
                        category = "neg";
                    }
                    double result = tagger.getDictionaryLookupScoreOfString(review);
                    if (result < 0 && category.equals("neg")) {
                        dictionaryLookupCorrect++;
                    } else if (result > 0 && category.equals("pos")) {
                        dictionaryLookupCorrect++;
                    }
                }

                lineNumber++;
            }

            finishTime = System.nanoTime();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println();
        int testedResults = lineNumber - TESTING_THRESHOLD;
        System.out.println(testedResults);
        System.out.println(dictionaryLookupCorrect);
        System.out
                .println("DictionaryLookup success rate: "
                        + (((double) dictionaryLookupCorrect / (double) testedResults) * (double) PERCENTAGE)
                        + "%");
        System.out.println("Time(s): " + (double) (finishTime - startTime) / NANO_TO_SECONDS);

    }

    /**
     * The main method.
     * 
     * @param args
     *            the arguments
     */
    public static void main(final String[] args) {
        try {
            for (int i : NGRAM_SIZES) {
                new SentimentClassifier(args, i).run();
            }
        } catch (Throwable t) {
            System.out.println("Thrown: " + t);
            t.printStackTrace(System.out);
        }
    }

    /**
     * Instantiates a new sentiment classifier.
     * 
     * @param args
     *            the args
     * @param nGram
     *            the n gram
     * @throws ClassNotFoundException
     *             the class not found exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private SentimentClassifier(final String[] args, final int nGram)
            throws ClassNotFoundException, IOException {
        new File(args[0], "txt_sentoken");
        lmc = DynamicLMClassifier.createNGramProcess(categories, nGram);
        System.out.print("nGram: " + nGram);
        File modelFile = new File(args[0], "/subjectivity.model");
        FileInputStream fileIn = new FileInputStream(modelFile);
        ObjectInputStream objIn = new ObjectInputStream(fileIn);
        objIn.close();
    }

    /**
     * Run.
     * 
     * @throws Exception
     *             the exception
     */
    private void run() throws Exception {
        trainClassifierOnCSV();
        evaluateClassifierOnCSV();
        evaluateDictionaryOnCSV();
    }
}
