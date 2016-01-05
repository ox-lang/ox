package ox.lang.environment;

import org.jetbrains.annotations.NotNull;
import ox.lang.AObj;
import ox.lang.Symbol;
import ox.lang.environment.binding.ABinding;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by arrdem on 9/26/15.
 *
 * Represents the abstract concept of a stack based binding context. Each such context has a
 * parent, may contain a binding, and a binding may be resolved on the whole stack by recursively
 * resolving it on the elements until failure.
 */
public abstract class AEnvironment
    extends AObj
    implements Map<Symbol, ABinding> {

    /*
     * @return The parent env of an env
     */
    public abstract AEnvironment getParent();

    public ABinding get(Object o) {
        if(o instanceof Symbol)
            return asMap().get((Symbol) o);
        else return null;
    }

    @NotNull
    public abstract Map<Symbol, ABinding> asMap();

    public int size() {
        return asMap().size();
    }

    public boolean isEmpty() {
        return asMap().isEmpty();
    }

    public boolean containsKey(Object o) {
        return asMap().containsKey(o);
    }

    public boolean containsValue(Object o) {
        return asMap().containsValue(o);
    }

    public ABinding put(Symbol symbol, ABinding aBinding) {
        return null;
    }

    public ABinding remove(Object o) {
        return null;
    }

    public void putAll(Map<? extends Symbol, ? extends ABinding> map) {}

    public void clear() {}

    @NotNull
    @Override
    public Set<Symbol> keySet() {
        return asMap().keySet();
    }

    @NotNull
    @Override
    public Collection<ABinding> values() {
        return asMap().values();
    }

    @NotNull
    @Override
    public Set<Entry<Symbol, ABinding>> entrySet() {
        return asMap().entrySet();
    }

    public String toString() {
        return String.format("#%s %s",
                             getClass().getName(),
                             asMap().toString());
    }

    public int hashCode() {
        return getParent().hashCode() ^ asMap().hashCode();
    }

    public boolean equals(Object other) {
        if(other == null) {
            return (asMap().size() != 0);
        } else if (other instanceof AEnvironment) {
            AEnvironment o = (AEnvironment) other;
            return (hashCode() == o.hashCode() &&
                    asMap() == o.asMap() &&
                    getParent() == o.getParent());
        } else {
            return false;
        }
    }

    /*
     * Attempts to resolve a symbol to a fully qualified getName or to itself in an env by walking
     * parent environments until a unique resolution is found or the symbol is determined to be
     * unbound.
     *
     * @param getName The symbol to getBinding
     * @return     The fully qualified getName thereof, or null if unbound.
     */
    public Symbol resolve(Symbol name) {
        assert name != null : "Cannot resolve null!";

        ABinding res = get(name);

        if(res != null) {
            return res.getName();
        } else {
            AEnvironment parent = getParent();

            if(parent != null) {
                return parent.resolve(name);
            } else {
                return null;
            }
        }
    }
}
