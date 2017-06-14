package org.oxlang.lang;

import org.jetbrains.annotations.NotNull;
import org.oxlang.data.Symbol;

import java.util.WeakHashMap;

/**
 * Created by arrdem on 5/29/17.
 *
 * Cases: a -> b
 *        a
 *
 * both require some notion of a "type context"
 */
public abstract class Type {

  /**
   * @return The kind (Either *, * -> * or #) of the type
   */
  public abstract Kind getKind();

  public static class TVar extends Type {
    @NotNull
    public final Symbol id;
    @NotNull
    public final Kind kind;

    public TVar(@NotNull Symbol id, @NotNull Kind kind) {
      this.id = id;
      this.kind = kind;
    }

    @Override
    public Kind getKind() {
      return kind;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      TVar tVar = (TVar) o;

      if (!id.equals(tVar.id)) return false;
      return getKind().equals(tVar.getKind());
    }

    @Override
    public int hashCode() {
      int result = id.hashCode();
      result = 31 * result + getKind().hashCode();
      return result;
    }
  }

  public static class TCon extends Type {
    @NotNull
    public final Symbol id;
    @NotNull
    public final Kind kind;

    public TCon(@NotNull Symbol id, @NotNull Kind kind) {
      this.id = id;
      this.kind = kind;
    }

    @Override
    public Kind getKind() {
      return kind;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      TCon tCon = (TCon) o;

      if (!id.equals(tCon.id)) return false;
      return getKind().equals(tCon.getKind());
    }

    @Override
    public int hashCode() {
      int result = id.hashCode();
      result = 31 * result + getKind().hashCode();
      return result;
    }
  }

  public static class TApp extends Type {
    @NotNull
    public final Type kfn_type, kfn;

    public TApp(@NotNull Type kfn_type, @NotNull Type kfn) {
      this.kfn_type = kfn_type;
      this.kfn = kfn;
    }

    @Override
    public Kind getKind() {
      return ((Kind.KFun)kfn_type.getKind()).r;
    }
  }

  public static class TGen extends Type {
    public final int id;

    public TGen(int id) {
      this.id = id;
    }

    @Override
    public Kind getKind() {
      return null; // FIXME
    }
  }
}
