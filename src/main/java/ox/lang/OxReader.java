package ox.lang;

import com.google.common.collect.ImmutableMap;
import javafx.geometry.Pos;
import ox.lang.PositionTrackingPushbackReader;

import java.io.Reader;
import java.util.Map;

/**
 * Created by arrdem on 11/24/15.
 */
public class OxReader {
    private Map table = ImmutableMap.builder()
                    .put((int) '(', new ListReader())
                    .put((int) '{', new MapReader())
                    .put((int) '[', new VectorReader())
                    .put((int) ';', new CommentRreader())
                    .put((int) ':', new KeywordReader())
                    .put((int) '#', new MacroReader())
                    .put((int) '\\', new CharacterReader())
                    .put((int) '\'', new QuoteReader())
                    .put((int) '"', new StringReader())
                    .put((int) '0', new NumberReader())
                    .put((int) '1', new NumberReader())
                    .put((int) '2', new NumberReader())
                    .put((int) '3', new NumberReader())
                    .put((int) '4', new NumberReader())
                    .put((int) '5', new NumberReader())
                    .put((int) '6', new NumberReader())
                    .put((int) '7', new NumberReader())
                    .put((int) '8', new NumberReader())
                    .put((int) '9', new NumberReader())
                    .build();

    private static getReader(Reader r) {

    }

    public static IMeta read(Reader r) {
        PositionTrackingPushbackReader cr =
                new PositionTrackingPushbackReader(r);
        IMeta result = null;

        return result;
    }

    private interface FormReader {
        public IMeta read(PositionTrackingPushbackReader r);
    }

    private class ListReader implements FormReader {
    }

    private class StringReader implements FormReader {
    }

    private class MapReader implements FormReader {
    }

    private class VectorReader implements FormReader {
    }

    private class CommentRreader implements FormReader {
    }

    private class MacroReader implements FormReader {
    }

    private class QuoteReader implements FormReader {
    }

    private class NumberReader implements FormReader {
    }

    private class KeywordReader implements FormReader {
    }

    private class CharacterReader implements FormReader {
    }
}

