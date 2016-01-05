package ox.lang;

import com.google.common.collect.ImmutableMap;
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
    /* This function serves as a helper for constructing Unicode
     * characters from their hex representations, a task that would
     * otherwise be quite the chore from ox itself.
     */
    public static Character readUnicodeChar(String token, int offset, int length, int base) {
        if(token.length() != offset + length)
            throw new IllegalArgumentException("Invalid unicode character: \\" + token);

        int uc = 0;
        for(int i = offset; i < offset + length; ++i) {
            int d = Character.digit(token.charAt(i), base);
            if(d == -1)
                throw new IllegalArgumentException("Invalid digit: " + token.charAt(i));
            uc = uc * base + d;
        }
        return new Character((char) uc);
    }


    /* Validator for Symbol and Keyword names.
     */

    private static Pattern namePattern =
            Pattern.compile("^([^;:#\"'\\[\\]\\(\\)\\{\\}\\d\\s][^;:#\"'\\[\\]\\(\\)\\{\\}\\s]*)$");

    public static boolean isValidName(String name) {
        Matcher m = namePattern.matcher(name);
        return m.matches();
    }

    /* Validator for Symbol and Keyword namespaces.
     */
    private static Pattern namespacePattern =
            Pattern.compile("(([^;:#\"'\\[\\]\\(\\)\\{\\}\\d\\s/][^;:#\"'\\[\\]\\(\\)\\{\\}\\s/]*)\\.?)+");

    public static boolean isValidNamespace(String ns) {
        Matcher m = namespacePattern.matcher(ns);
        return m.matches();
    }

    @NotNull
    public static String toString(Object val) {
        if(val == null) {
            return "nil";
        } else {
            return val.toString();
        }
    }

    @NotNull
    public static Map getMeta(Object o) {
        if(o instanceof IMeta) {
            return ((IMeta) o).getMeta();
        } else {
            return ImmutableMap.of();
        }
    }
}
