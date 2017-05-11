package org.oxlang.data;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by arrdem on 9/26/15.
 */
public class SymbolTest {

    static String
            _NS_   = "ox.lang.test",
            _NAME_ = "foo";

    static Symbol name = Symbol.of(_NS_, _NAME_);

    @Test
    public void testGetName() throws Exception {
        assertEquals(name.getName(), _NAME_);
    }

    @Test
    public void testGetNamespace() throws Exception {
        assertEquals(name.getNamespace(), _NS_);
    }

    @Test
    public void testToString() throws Exception {
        assertEquals(name.toString(), _NS_+"/"+_NAME_);
    }

    @Test
    public void testEquals() throws Exception {
        name.equals(Symbol.of(_NS_, _NAME_));
    }
}
