package ox.lang.environment;

import com.google.common.collect.ImmutableMap;
import ox.lang.IMeta;
import ox.lang.Symbol;

import java.util.Map;

/**
 * Created by arrdem on 9/26/15.
 *
 * Represents the top level binding context which a user can alter. Definitions and namespaces
 * exist at this level as values.
 */
public class GlobalEnv
        extends AEnvironment
        implements IMeta {

    private final AEnvironment parent;
    private final Map meta;
    private final Map<Symbol,ABinding> bindings;

    private GlobalEnv(AEnvironment parent,
                      Map<Symbol, ABinding> bindings,
                      Map meta) {
        this.parent = parent;
        this.bindings = bindings;
        this.meta = meta;
    }

    public static final class Builder {
        private Map meta;
        private AEnvironment parent;
        private Map<Symbol,ABinding> bindings;
        private GlobalEnv result;

        public Builder() {
            this.meta = ImmutableMap.of();
            this.bindings = ImmutableMap.of();
            this.parent = null;
            this.result = null;
        }

        public GlobalEnv build() {
            if(this.result != null)
                return this.result;
            else {
                assert parent != null : "Cannot env with null parent!";

                this.result = new GlobalEnv(parent, bindings, meta);
                return this.result;
            }
        }

        public Builder setMeta(Map meta) {
            assert  meta != null;

            this.meta = meta;
            return this;
        }

        public Builder setBindings(Map<Symbol, ABinding> bindings) {
            assert bindings != null;

            // Typecheck the keys
            for (Object k : bindings.keySet()) {
                assert k instanceof Symbol :
                        String.format(
                                "Cannot bind non-Symbol value '%s'!",
                                k.toString());
            }

            // Typecheck the values
            for (Object v : bindings.values()) {
                assert v instanceof ABinding :
                        String.format(
                                "Illegal non-binding entry '%s! (class '%s')",
                                v.toString(),
                                v.getClass().getCanonicalName());
            }

            this.bindings = bindings;
            return this;
        }

        public Builder setParent(AEnvironment parent) {
            assert parent != null;
            this.parent = parent;
            return this;
        }
    }

    public static GlobalEnv of(AEnvironment parent,
                               Map<Symbol, ABinding> bindings) {
        return new Builder()
                .setParent(parent)
                .setBindings(bindings)
                .build();
    }

    public static GlobalEnv of(AEnvironment parent,
                               Map<Symbol, ABinding> bindings,
                               Map meta) {
        return new Builder()
                .setParent(parent)
                .setBindings(bindings)
                .setMeta(meta)
                .build();
    }

    @Override
    public AEnvironment getParent() {
        return parent;
    }

    @Override
    public ABinding getBinding(Symbol name) {
        return (ABinding) bindings.get(name);
    }

    @Override
    public Map getMeta() {
        return meta;
    }

    @Override
    public Object withMeta(Map meta) {
        return of(parent, bindings, meta);
    }
}
