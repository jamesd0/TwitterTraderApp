package org.dj.twittertrader.utils;

import java.util.Comparator;

import org.dj.twittertrader.model.Tweet;

/**
 * The Class SortByTweetScoreDesc.
 */
public class SortByTweetScore implements Comparator<Tweet> {

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public final int compare(final Tweet o1, final Tweet o2) {
        int result = (int) (o1.getTweetScore() - o2.getTweetScore());
        if (result > 0) {
            return 1;
        } else if (result < 0) {
            return -1;
        } else {
            return result;
        }
    }

}
