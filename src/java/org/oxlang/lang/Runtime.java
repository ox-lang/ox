package org.oxlang.lang;

import org.oxlang.data.Symbol;

import io.lacuna.bifurcan.Map;

/**
 * Created by rmckenzie on 5/13/17.
 */
public class Runtime {
  class Definition {

  }

  class Namespace {
    Symbol name;
    Map<Symbol, Definition> contents = new Map<Symbol, Definition>();
  }

  class PackageIdentifier<T extends Comparable> {
    String groupName, packageName;
    T version;

    PackageIdentifier(String groupName, String packageName, T version) {
      this.groupName = groupName;
      this.packageName = packageName;
      this.version = version;
    }

    public int compareTo(PackageIdentifier other) {
      return version.compareTo(other.version);
    }
  }

  class Package {
    Map<Symbol, Namespace> contents = new Map();
    PackageIdentifier id;
  }

}
