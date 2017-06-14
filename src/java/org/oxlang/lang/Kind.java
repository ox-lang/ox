package org.oxlang.lang;

import org.jetbrains.annotations.NotNull;

/**
 * Created by arrdem on 5/29/17.
 */
public abstract class Kind {
  public abstract boolean isNullary();

  public static class Star extends Kind {
    @Override
    public boolean isNullary() {
      return true;
    }

    @Override
    public boolean equals(Object o) {
      return o instanceof Star;
    }
  }

  public static class KFun extends Kind {
    @NotNull
    public final Kind l, r;

    public KFun(@NotNull Kind l, @NotNull Kind r) {
      this.l = l;
      this.r = r;
    }

    @Override
    public boolean isNullary() {
      return false;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      KFun kFun = (KFun) o;

      if (!l.equals(kFun.l)) return false;
      return r.equals(kFun.r);
    }

    @Override
    public int hashCode() {
      int result = l.hashCode();
      result = 31 * result + r.hashCode();
      return result;
    }
  }
}
