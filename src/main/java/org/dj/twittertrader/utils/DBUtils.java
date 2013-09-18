package org.dj.twittertrader.utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class DBUtils is a utility method for use when executing queries on the
 * database.
 */
public final class DBUtils {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DBUtils.class);
    /** The Constant ONE. */
    public static final int ONE = 1;

    /** The Constant TWO. */
    public static final int TWO = 2;

    /** The Constant THREE. */
    public static final int THREE = 3;

    /** The Constant FOUR. */
    public static final int FOUR = 4;

    /** The Constant FIVE. */
    public static final int FIVE = 5;

    /** The Constant SIX. */
    public static final int SIX = 6;

    /** The Constant SEVEN. */
    public static final int SEVEN = 7;

    /** The Constant EIGHT. */
    public static final int EIGHT = 8;

    /** The Constant NINE. */
    public static final int NINE = 9;

    /** The Constant TEN. */
    public static final int TEN = 10;

    /** The Constant ELEVEN. */
    public static final int ELEVEN = 11;

    /** The Constant TWELVE. */
    public static final int TWELVE = 12;

    /** The Constant THIRTEEN. */
    public static final int THIRTEEN = 13;

    /** The Constant FOURTEEN. */
    public static final int FOURTEEN = 14;

    /** The Constant FITHTEEN. */
    public static final int FITHTEEN = 15;

    /** The Constant SIXTEEN. */
    public static final int SIXTEEN = 16;

    /** The Constant SEVENTEEN. */
    public static final int SEVENTEEN = 17;

    /** The Constant EIGHTEEN. */
    public static final int EIGHTEEN = 18;

    /** The Constant NINETEEN. */
    public static final int NINETEEN = 19;

    /** The Constant TWENTY. */
    public static final int TWENTY = 20;

    /** The Constant TWENTYONE. */
    public static final int TWENTYONE = 21;

    /**
     * Instantiates a new dB utils.
     */
    private DBUtils() {
        throw new AssertionError("The class DBUtils is a utility class"
                + " and should not be instanciated");
    }

    /**
     * The close method closes the connection.
     * 
     * @param connection
     *            the connection
     */
    public static void close(final Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    /**
     * the close method closes the statement.
     * 
     * @param statement
     *            the statement
     */
    public static void close(final Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    /**
     * The close method closes the result set.
     * 
     * @param resultSet
     *            the result set
     */
    public static void close(final ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    /**
     * Gets the logger.
     * 
     * @return the logger
     */
    public static Logger getLogger() {
        return LOGGER;
    }

    /**
     * Convert date.
     * 
     * @param createdAt
     *            the created at
     * @return the date
     */
    @SuppressWarnings("deprecation")
    public static Date convertDate(final java.util.Date createdAt) {
        if (createdAt == null) {
            return null;
        }
        Date date = new Date(createdAt.getYear(), createdAt.getMonth(), createdAt.getDate());
        return date;
    }
}
