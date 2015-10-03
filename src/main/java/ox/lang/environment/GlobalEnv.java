package ox.lang.environment;

import com.google.common.collect.ImmutableMap;
import ox.lang.IMeta;
import ox.lang.Symbol;

import java.util.Map;

/**
 * Created by arrdem on 9/26/15.
 */
public class GlobalEnv implements IEnvironment, IMeta {

    private final IEnvironment parent;
    private final Map meta;
    private final Map<Symbol,ABinding> bindings;

    private GlobalEnv(IEnvironment parent,
                      Map<Symbol, ABinding> bindings,
                      Map meta) {
        this.parent = parent;
        this.bindings = bindings;
        this.meta = meta;
    }

    public static final class Builder {
        private Map meta;
        private IEnvironment parent;
        private Map bindings;
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
                if(parent == null)
                    throw new RuntimeException(
                            "Cannot build global env with null parent!");

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
                if (!(k instanceof Symbol)) {
                    throw new RuntimeException(
                            String.format(
                                "Cannot bind non-Symbol value '%s'!",
                                k.toString()));
                }
            }

            // Typecheck the values
            for (Object v : bindings.values()) {
                if (!(v instanceof ABinding)) {
                    throw new RuntimeException(
                            String.format(
                                "Illegal non-binding entry '%s! (class '%s')",
                                v.toString(),
                                v.getClass().getCanonicalName()));
                }
            }

            this.bindings = bindings;
            return this;
        }

        public Builder setParent(IEnvironment parent) {
            assert parent != null;
            this.parent = parent;
            return this;
        }
    }

    @Override
    public IEnvironment getParent() {
        return parent;
    }

    @Override
    public ABinding find(Symbol name) {
        return (ABinding) bindings.get(name);
    }

    @Override
    public Map meta() {
        return meta;
    }

    @Override
    public Object withMeta(Map meta) {
        return new GlobalEnv(parent, bindings, meta);
    }
}
