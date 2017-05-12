package org.oxlang.lang;

import java.util.Iterator;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;

/**
 * Created by rmckenzie on 5/11/17.
 */
public class TokenSequence implements Iterable<Token> {
  private TokenStream stream;

  public TokenSequence(TokenStream stream) {
    this.stream = stream;
  }

  @Override
  public Iterator<Token> iterator() {
    return  new TokenSequenceIterator(stream);
  }

  class TokenSequenceIterator implements Iterator<Token> {
    private TokenStream stream;
    private int next_token_idx = 0;

    TokenSequenceIterator(TokenStream stream) {
      this.stream = stream;
    }

    @Override
    public boolean hasNext() {
      try {
        Token next = this.stream.get(this.next_token_idx);
        // We have a next token if the next one isn't EOF
        return next.getType() != -1;
      } catch (IndexOutOfBoundsException e) {
        // If there were no tokens or we're off the end somehow, false.
        return false;
      }
    }

    @Override
    public Token next() {
      return this.stream.get(this.next_token_idx++);
    }
  }
}
