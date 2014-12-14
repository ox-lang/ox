package ox.lang;
import java.lang.IllegalArgumentException;
import java.lang.Character;

public class Util {
    public static Character readUnicodeChar(String token, int offset, int length, int base) {
        if(token.length() != offset + length)
            throw new IllegalArgumentException("Invalid unicode character: \\" + token);
        int uc = 0;
        for(int i = offset; i < offset + length; ++i)
            {
                int d = Character.digit(token.charAt(i), base);
                if(d == -1)
                    throw new IllegalArgumentException("Invalid digit: " + token.charAt(i));
                uc = uc * base + d;
            }
        return new Character((char) uc);
    }
}
