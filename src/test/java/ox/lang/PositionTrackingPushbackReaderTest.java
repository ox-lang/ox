package ox.lang;

import javafx.geometry.Pos;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import static org.junit.Assert.*;

/**
 * Created by arrdem on 11/24/15.
 */
public class PositionTrackingPushbackReaderTest {
    static String text = "foo\nbar\nbaz";

    @Test
    public void testSetColumnNumber() throws Exception {
        Reader r = new StringReader(text);
        PositionTrackingPushbackReader ptr = new PositionTrackingPushbackReader(r);

        for(int col : new int[]{1, 10, 5000, 2}) {
            ptr.setColumnNumber(col);
            assertEquals(col, ptr.getColumnNumber());
        }
    }

    @Test
    public void testGetColumnNumber() throws Exception {
        Reader r = new StringReader(text);
        PositionTrackingPushbackReader ptr = new PositionTrackingPushbackReader(r);

        int col = 0;
        for(int c = ptr.read(); c != -1; c = ptr.read()) {
            if(c == '\n') {
                col = 0;
            } else {
                col++;
            }

            assertEquals(ptr.getColumnNumber(), col);
        }
    }
}