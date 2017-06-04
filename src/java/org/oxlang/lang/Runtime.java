package org.oxlang.lang;

import io.lacuna.bifurcan.*;
import org.jetbrains.annotations.NotNull;
import org.oxlang.data.SimpleSymbol;
import org.oxlang.data.Symbol;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

/**
 * Created by rmckenzie on 5/13/17.
 */
public class Runtime {
  static final Symbol NS_SYM = Symbol.of("ns");
  static final Symbol REQUIRE_SYM = Symbol.of("require");
  static final Symbol AS_SYM = Symbol.of("as");
  static final Symbol REFER_SYM = Symbol.of("refer");
  static final Symbol RENAME_SYM = Symbol.of("rename");

  /**
   * A superclass for errors which occur during analysis time.
   */
  public static class AnalysisException extends Exception {
    public AnalysisException(String message) {
      super(message);
    }

    public AnalysisException(String message, Throwable cause) {
      super(message, cause);
    }
  }

  /**
   * Exception thrown when the analyzer doesn't get a leading NS, or the NS's name was wrong.
   */
  public static class IllegalNsException extends AnalysisException {
    public IllegalNsException(String message) {
      super(message);
    }

    public IllegalNsException(String message, Throwable cause) {
      super(message, cause);
    }
  }

  public static class IllegalRequireException extends IllegalNsException {
    public IllegalRequireException(String message) {
      super(message);
    }

    public IllegalRequireException(String message, Throwable cause) {
      super(message, cause);
    }
  }

  public static class IllegalRequireManipulationException extends IllegalRequireException {
    public IllegalRequireManipulationException(String message) {
      super(message);
    }
    public IllegalRequireManipulationException(String message, Throwable cause) {
      super(message, cause);
    }
  }

  private final PackageResolver resolver;
  private final Map<PackageIdentifier, Package> packages;

  public Runtime(PackageResolver resolver, Map<PackageIdentifier, Package> packages) {
    this.packages = packages;
    this.resolver = resolver;
  }

  /**
   * Analyzes a single top level form. While there are four cases, this handles only the ns form.
   *
   * 1. (ns $name ...)
   *
   * @param nsid
   * @param form
   * @return
   */
  public static Namespace loadNsForm(NamespaceIdentifier nsid, Object form)
      throws IllegalNsException
  {

    if (!(form instanceof List))
      throw new IllegalNsException("Namespaces must start with an `ns` form!");

    List sexpr = (List) form;

    if (sexpr.size() < 2)
      throw new IllegalNsException("`ns` forms must be at least `(ns ^Symbol name)`");

    if (!NS_SYM.equals(sexpr.first()))
      throw new IllegalNsException("Namespaces must start with an `ns` form!");

    Map metadata = null;
    String docstring = null;
    Map<DefinitionIdentifier, IDefinition> contents = (Map) Maps.EMPTY;
    Map aliases = (Map) Maps.EMPTY;

    for (long idx = 2; idx <= sexpr.size(); idx++) {
      if (idx == 2) {
        if (sexpr.nth(idx) instanceof String) {
          // Case 1) first sub-expression is a docstring
          docstring = (String) sexpr.nth(idx);
        } else if (sexpr.nth(idx) instanceof Map) {
          // Case 2) first sub-expression is a metadata mapping
          metadata = (Map) sexpr.nth(idx);
        }
      } else if (idx == 3 && metadata == null && sexpr.nth(idx) instanceof Map) {
        // Case 3) third sub-expression is a metadata mapping and we don't have one already
        metadata = (Map) sexpr.nth(idx);
      } else if (sexpr.nth(idx) instanceof List && ((List) sexpr.nth(idx)).nth(0).equals(REQUIRE_SYM)) {
        // Case 4) we have n-many require forms in the body
        List subform = (List) sexpr.nth(idx);

        if (subform.size() != 2)
          throw new IllegalRequireException(
              String.format("Analyzing ns %s, subform %d (%s) was not a legal require form.",
                  nsid, idx, subform));

        if (subform.nth(1) instanceof Symbol) {
          // Case 4.1) requiring a specific name with no alias or referrals

          Symbol requirement = (Symbol) subform.nth(1);
          aliases = aliases.put(requirement, requirement);
        } else if (subform.nth(1) instanceof List) {
          // Case 4.2) requiring a namespace with manipulations

          List manipulation = (List) subform.nth(1);
          if (manipulation.size() != 3)
            throw new IllegalRequireException(
                String.format("Analyzing ns %s, subform %d (%s), got illegal referral manipulation %s. All require manipulations are of length 3.",
                    nsid, idx, subform, manipulation));

          if (REFER_SYM.equals(manipulation.first())) {
            // Case 4.2.1) (refer <ns> [& syms])

            if (!(manipulation.nth(1) instanceof Symbol))
              throw new IllegalRequireManipulationException(
                  String.format("Analyzing ns %s, subform %d (%s), got illegal refer form. Source ns must be a Symbol, got %s.",
                      nsid, idx, subform, manipulation.nth(1).getClass().getCanonicalName()));

            if (!(manipulation.nth(2) instanceof List))
              throw new IllegalRequireManipulationException(
                  String.format("Analyzing ns %s, subform %d (%s), got illegal refer form. Referrals must be a List of Symbols, got %s.",
                      nsid, idx, subform, manipulation.nth(2).getClass().getCanonicalName()));

            try {
              Symbol source = (Symbol) manipulation.nth(1);
              aliases = aliases.put(source, source);

              for (Symbol requirement : (List<Symbol>) manipulation.nth(2)) {
                contents = contents.put(new DefinitionIdentifier(nsid, requirement.getName()), );
              }
            } catch (ClassCastException ex) {
              throw new IllegalRequireManipulationException(
                  String.format("Analyzing ns %s, subform %d (%s), got illegal refer form. Referrals must be a List of Symbols. Encountered a casting exception indicative of a mal-formed referrals list.",
                      nsid, idx, subform, manipulation.nth(2).getClass().getCanonicalName()),
                  ex);
            }

          } else if (AS_SYM.equals(manipulation.first())) {
            // Case 4.2.2) (as <ns> <alias>)


          } else if (RENAME_SYM.equals(manipulation.first())) {
            // Case 4.2.3 (rename <ns> {oldname newname ...})

          } else {
            // Case 4.2.4) Anything else is mal-formed / illegal

          }

        } else {
          throw new IllegalRequireException(
              String.format("Analyzing ns %s, subform %d (%s) expected Symbol or List, got %s",
                  nsid, idx, subform, subform.nth(1).getClass().getCanonicalName()));
        }

      } else {
        throw new IllegalNsException(
            String.format("Analyzing ns %s, subform %d (%s) was neither a legal docstring, metadata or require form.",
                nsid, idx, sexpr.nth(idx)));
      }
    }

    return new Namespace(nsid, metadata, null, null, null, docstring);
  }

  /**
   * Analyzes a single top level form. While there are four cases, this handles the other three.
   *
   * 2. (def $name ...)
   * 3. (defmacro $name ...)
   * 4. (providing [$name ...] ...)
   *
   * @param form An s-expression to analyze.
   * @return A Form
   */
  @NotNull
  public static Form loadForm(Object form) {
    return null;
  }

  @NotNull
  public static Namespace loadNamespace(NamespaceIdentifier nsid, Reader rdr)
      throws IOException,
             IllegalNsException
  {
    Iterator it = OxlangReader.readAll(rdr);
    Namespace ns = loadNsForm(nsid, it.next());
    for (; it.hasNext(); ) {
      Object form = it.next();
      ns = ns.withForm(loadForm(form));
    }

    return ns;
  }

  public static Runtime loadPackage(Runtime r, PackageIdentifier id)
      throws PackageResolver.NoSuchPackageException, IOException, IllegalNsException {
    Map<NamespaceIdentifier, Reader> packageContents = r.resolver.resolveContents(id);
    if (packageContents == null) {
      throw new IllegalArgumentException(
          String.format("Unknown package identifier %s", id));
    } else {
      Map<NamespaceIdentifier, Namespace> newNamespaces = new Map<>();

      for (IMap.IEntry<NamespaceIdentifier, Reader> entry : packageContents) {
        NamespaceIdentifier nsid = entry.key();
        Reader rdr = entry.value();

        newNamespaces = newNamespaces.put(nsid, loadNamespace(nsid, rdr));
      }

      Package newPackage = new Package(id, (Set)Sets.EMPTY, newNamespaces);

      return new Runtime(r.resolver, r.packages.put(id, newPackage));
    }
  }
}
