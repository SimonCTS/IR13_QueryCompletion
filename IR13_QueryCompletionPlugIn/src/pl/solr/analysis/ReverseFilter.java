package pl.solr.analysis;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public final class ReverseFilter extends TokenFilter {
  private CharTermAttribute charTermAttr;

  protected ReverseFilter(TokenStream ts) {
    super(ts);
    this.charTermAttr = addAttribute(CharTermAttribute.class);
  }

  @Override
  public boolean incrementToken() throws IOException {
    if (!input.incrementToken()) {
      return false;
    }

    int length = charTermAttr.length();
    char[] buffer = charTermAttr.buffer();
    char[] newBuffer = new char[length];
    for (int i = 0; i < length; i++) {
      newBuffer[i] = buffer[length - 1 - i];
    }
    charTermAttr.setEmpty();
    charTermAttr.copyBuffer(newBuffer, 0, newBuffer.length);
    return true;
  }
}