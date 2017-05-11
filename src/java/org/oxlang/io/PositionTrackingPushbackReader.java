package org.oxlang.parser;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

/**
 * Created by arrdem on 11/24/15.
 */
public class PositionTrackingPushbackReader
        extends LineNumberReader {
    private int column;

    public PositionTrackingPushbackReader(Reader in) {
        super(in);
        column = 0;
    }

    public void setColumnNumber(int col) {
        column = col;
    }

    public int getColumnNumber() {
        return column;
    }

    public int read() throws IOException {
        int chr = super.read();
        if(chr == '\n') {
            column = 0;
        } else {
            column += 1;
        }
        return chr;
    }
}