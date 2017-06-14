package org.oxlang.lang;

/**
 * Created by arrdem on 5/23/17.
 *
 * An abstract class representing a definition in source.
 * Definitions have a value, and serve to join that value with documentation.
 */
public interface IDefinition<T extends IDefinition> {
  abstract String getDocumentation();
  abstract T withDocumentation(String documentation);
}
