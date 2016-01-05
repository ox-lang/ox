package ox.lang.environment.binding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ox.lang.AObj;
import ox.lang.Symbol;

/**
 * Created by arrdem on 9/26/15.
 *
 * Represents the abstract concept of a singular binding as it may exist within a binding context
 * (see also AEnvironment). The definition of a binding is that it has a symbolic getName, and a
 * getValue. It may also have metadata.
 *
 * In the future this API may change to allow bindings to be uniquely referenced by some ID other
 * than the symbolic getName for the binding.
 */
public abstract class ABinding
    extends AObj {
    @Nullable
    public abstract Object getValue();

    @NotNull
    public abstract Symbol getName();

    @NotNull
    @Override
    public String toString() {
        return String.format("#binding [%s %s]",
                             getName().toString(),
                             getValue().toString());
    }

    public int hashCode() {
        return getName().hashCode() ^ getValue().hashCode();
    }

    public boolean equals(Object other) {
        if(other == null) {
            return false;
        } else if (other == this) {
            return true;
        } else if (other instanceof ABinding) {
            ABinding ab = (ABinding) other;
            return (hashCode() == ab.hashCode() &&
                    getValue() == ab.getValue() &&
                    getName() == ab.getName());
        } else {
            return false;
        }
    }
}
