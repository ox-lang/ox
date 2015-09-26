package ox.lang;

import ox.lang.Util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Created by arrdem on 9/26/15.
 */
public class UtilTest {

    @Test
    public void testIsValidName() throws Exception {
        assertEquals(true, Util.isValidName("foo"));
        assertEquals(true, Util.isValidName("a16z"));
        assertEquals(true, Util.isValidName("foo/bar"));

        assertEquals(false, Util.isValidName("("));
        assertEquals(false, Util.isValidName("aaaaaa("));

        assertEquals(false, Util.isValidName(")"));
        assertEquals(false, Util.isValidName("aaaaaa)"));

        assertEquals(false, Util.isValidName("}"));
        assertEquals(false, Util.isValidName("aaaaaaa}"));

        assertEquals(false, Util.isValidName("{"));
        assertEquals(false, Util.isValidName("aaaaaaa{"));

        assertEquals(false, Util.isValidName("["));
        assertEquals(false, Util.isValidName("aaaaaaa["));

        assertEquals(false, Util.isValidName("]"));
        assertEquals(false, Util.isValidName("aaaaaaa]"));

        assertEquals(false, Util.isValidName(":foo"));
    }

    @Test
    public void testIsValidNamespace() throws Exception {
        assertEquals(true, Util.isValidNamespace("foo"));
        assertEquals(true, Util.isValidNamespace("a16z"));
        assertEquals(true, Util.isValidNamespace("foo.bar"));

        assertEquals(false, Util.isValidNamespace("foo/bar"));
        assertEquals(false, Util.isValidNamespace("foo.bar/baz"));
    }
}