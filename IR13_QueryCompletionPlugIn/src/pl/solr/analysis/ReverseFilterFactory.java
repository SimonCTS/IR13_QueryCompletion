package pl.solr.analysis;

import org.apache.lucene.analysis.TokenStream;
import org.apache.solr.analysis.BaseTokenFilterFactory;

public class ReverseFilterFactory extends BaseTokenFilterFactory {
  @Override
  public TokenStream create(TokenStream ts) {
    return new ReverseFilter(ts);
  }
}