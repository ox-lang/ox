package org.oxlang.lang;

/**
 * Created by arrdem on 5/15/17.
 *
 * This class represents a fully qualified name for a package within the Ox ecosystem. So long as the version is
 * repeatable, this is a globally unique identifier for a given group of definitions.
 */
public class PackageIdentifier<T extends Comparable> {
  String groupName, packageName;
  T version;

  PackageIdentifier(String groupName, String packageName, T version) {
    this.groupName = groupName;
    this.packageName = packageName;
    this.version = version;
  }

  public int compareTo(Object other) {
    if (other instanceof PackageIdentifier) {
      return version.compareTo(((PackageIdentifier) other).version);
    } else {
      return 0;
    }
  }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PackageIdentifier<?> that = (PackageIdentifier<?>) o;

        if (!groupName.equals(that.groupName)) return false;
        if (!packageName.equals(that.packageName)) return false;
        return version.equals(that.version);
    }

    @Override
    public int hashCode() {
        int result = groupName.hashCode();
        result = 31 * result + packageName.hashCode();
        result = 31 * result + version.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("<PackageIdentifier '%s' '%s' %s>", groupName, packageName, version.toString());
    }
}
