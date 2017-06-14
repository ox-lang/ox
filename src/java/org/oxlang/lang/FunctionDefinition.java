package org.oxlang.lang;

/**
 * Created by arrdem on 5/29/17.
 */
public class FunctionDefinition
    implements IDefinition<FunctionDefinition> {

  private final String docs;

  public FunctionDefinition(String docs) {
    this.docs = docs;
  }

  @Override
  public String getDocumentation() {
    return null;
  }

  @Override
  public FunctionDefinition withDocumentation(String documentation) {
    return null;
  }
}
