package org.oxlang;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* This class serves as a holding tank for snippets of Java code which
 * are both stateless, and serve as helpers in interacting with the ox
 * implementation and data structures.
 *
 * As a helper class, this class should have no instance variables or
 * methods. Constants and statics are OK.
 *
 * Roughly analogous to clojure.lang.Util, but this is the preferred
 * place to put stuff rather than the alternate.
 */

public class Util {
    @NotNull
    public static String toString(Object val) {
        if(val == null) {
            return "nil";
        } else {
            return val.toString();
        }
    }
}
