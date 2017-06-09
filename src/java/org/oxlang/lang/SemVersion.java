package org.oxlang.lang;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SemVer versions can be compared for equality and for ordering.
 */
public class SemVersion extends ConcreteVersion implements Comparable<SemVersion> {
  private static final Pattern PATTERN = Pattern.compile("^(\\d+)\\.(\\d+)\\.(\\d+)$");
  public final int major;
  public final int minor;
  public final int patch;

  public SemVersion(int major, int minor, int patch) {
    if (major < 0)
      throw new IllegalArgumentException("Got illegal negative major version!");

    this.major = major;

    if (minor < 0)
      throw new IllegalArgumentException("Got illegal negative minor version!");

    this.minor = minor;

    if (patch < 0)
      throw new IllegalArgumentException("Got illegal negative patch version!");

    this.patch = patch;
  }

  public static SemVersion of(String text) {
    Matcher m = PATTERN.matcher(text);
    if (m.find()) {
      try {
        return new SemVersion(
            Integer.parseInt(m.group(1)),
            Integer.parseInt(m.group(2)),
            Integer.parseInt(m.group(3)));
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException(
            String.format("Could not parse '%s' as a SemVer version!", text));
      }
    } else {
      throw new IllegalArgumentException(
          String.format("Could not parse '%s' as a SemVer version!", text));
    }
  }

  @Override
  public boolean matches(ConcreteVersion v) {
    return this.equals(v);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SemVersion version = (SemVersion) o;

    if (major != version.major) return false;
    if (minor != version.minor) return false;
    return patch == version.patch;
  }

  @Override
  public int hashCode() {
    int result = major;
    result = 31 * result + minor;
    result = 31 * result + patch;
    return result;
  }

  @Override
  public int compareTo(@NotNull SemVersion o) {
    // Short circuit
    if (this.equals(o))
      return 0;

    if (this.major > o.major)
      return 1;
    if (this.major < o.major)
      return -1;

    if (this.minor > o.minor)
      return 1;
    if (this.minor < o.minor)
      return -1;

    if (this.patch > o.patch)
      return 1;
    if (this.patch < o.patch)
      return -1;

    // we're equal the hard way somehow
    return 0;
  }

  @Override
  public String toString() {
    return String.format("%d.%d.%d", major, minor, patch);
  }
}
