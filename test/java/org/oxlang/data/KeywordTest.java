package org.oxlang.data;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by arrdem on 9/26/15.
 */
public class KeywordTest {

    @Test
    public void testGetName() throws Exception {
        assertEquals(
                Keyword.of("foo").getName(),
                "foo");
    }

    @Test
    public void testGetNamespace() throws Exception {
        assertEquals(
                Keyword.of("foo").getNamespace(),
                null);

        assertEquals(
                Keyword.of("foo", "bar").getNamespace(),
                "foo");
    }

    @Test
    public void testEquals() throws Exception {
        assertEquals(
                Keyword.of("foo"),
                Keyword.of("foo"));

        assertEquals(
                Keyword.of("foo", "bar"),
                Keyword.of("foo", "bar"));

        assertNotEquals(
                Keyword.of("foo"),
                Keyword.of("bar"));

        assertNotEquals(
                Keyword.of("foo"),
                null);
    }
}
