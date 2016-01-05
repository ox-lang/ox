package ox.lang.environment;

import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;
import ox.lang.Symbol;
import ox.lang.environment.binding.ABinding;

import java.util.Map;

/**
 * Created by arrdem on 9/26/15.
 */
public class StackEnv
        extends AEnvironment {
    private final AEnvironment parent;
    private final Map<Symbol, ABinding> bindings;
    private final Map meta;

    private StackEnv(AEnvironment parent,
                     Map<Symbol, ABinding> bindings,
                     Map meta) {
        this.parent = parent;
        this.bindings = bindings;
        this.meta = meta;
    }

    public static class Builder {
        private AEnvironment parent;
        private Map<Symbol, ABinding> bindings;
        private Map meta;
        private StackEnv res;

        public Builder() {
            parent = null;
            bindings = ImmutableMap.of();
            meta = ImmutableMap.of();
            res = null;
        }

        public Builder setParent(AEnvironment parent) {
            assert parent != null : "The parent of an env cannot be null";

            this.parent = parent;
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

        public Builder setMeta(Map meta) {
            assert meta != null : "Meta cannot be null";

            this.meta = meta;
            return this;
        }

        public StackEnv build() {
            if(res != null) {
                return res;
            } else {
                res = new StackEnv(parent, bindings, meta);
                return res;
            }
        }
    }

    private Object of(AEnvironment parent,
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

    @NotNull
    @Override
    public Map<Symbol, ABinding> asMap() {
        return bindings;
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
