package org.dj.twittertrader.model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.dj.twittertrader.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class UserTest. Tests various User entity functionality.
 */
public class UserTest {

    /** The Constant TEST_INT. */
    private static final int TEST_INT = 231231;

    /** The user. */
    private User user;

    /** The twitter user. */
    private twitter4j.User tUser;

    /**
     * Initialises objects before each test.
     */
    @Before
    public final void setUp() {
        user = TestUtil.randomUser();
        tUser = mock(twitter4j.User.class);
    }

    /**
     * Test constructor.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public final void testConstructor() throws IOException {
        long date = new Random().nextLong();
        when(tUser.getCreatedAt()).thenReturn(new Date(date));
        when(tUser.getFollowersCount()).thenReturn(TEST_INT);
        when(tUser.getFriendsCount()).thenReturn(TEST_INT);
        when(tUser.getId()).thenReturn((long) TEST_INT);
        when(tUser.getName()).thenReturn("FullName");
        when(tUser.getScreenName()).thenReturn("ScreenName");
        when(tUser.getFavouritesCount()).thenReturn(TEST_INT);
        when(tUser.isVerified()).thenReturn(true);
        when(tUser.getLang()).thenReturn("en");
        when(tUser.getLocation()).thenReturn("Location");
        user = new User(tUser);
        assertEquals(user.getCreatedAt(), new Date(date));
        assertEquals(user.getFavouritesCount(), TEST_INT);
        assertEquals(user.getFollowersCount(), TEST_INT);
        assertEquals(user.getFriendsCount(), TEST_INT);
        assertEquals(user.getId(), TEST_INT);
        assertEquals(user.getLang(), "en");
        assertEquals(user.getLocation(), "Location");
        assertEquals(user.getName(), "FullName");
        assertEquals(user.getScreenName(), "ScreenName");
        assertEquals(user.isVerified(), true);
        assertEquals(user.isActive(), true);
        assertEquals(user.getUserScore(), TEST_INT * TEST_INT * TEST_INT * 10);
    }

    /**
     * Test json.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public final void testJson() throws IOException {
        String json = User.toJson(user);
        assertEquals(user, User.fromJson(json));
    }

    /**
     * Equals user.
     */
    @Test
    public final void equalsUser() {
        EqualsVerifier.forClass(User.class).suppress(Warning.NONFINAL_FIELDS, Warning.NULL_FIELDS)
                .verify();
    }
}
