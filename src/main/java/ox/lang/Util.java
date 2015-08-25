package ox.lang;
import java.lang.IllegalArgumentException;
import java.lang.Character;

/* This class serves as a holding tank for snippets of Java code which
 * are both stateless, and serve as helpers in interacting with the ox
 * implementation and datastructures.
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
}
