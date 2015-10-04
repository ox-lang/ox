package ox.lang.environment.binding;

import com.google.common.collect.ImmutableMap;
import ox.lang.Symbol;
import ox.lang.environment.ABinding;

import java.util.Map;

/**
 * Created by arrdem on 9/26/15.
 *
 * Special bindings are used to represent symbolic bindings. They have no actual getValue, but
 * instead represent the bindings of special forms which cannot be more meaningfully evaluated
 * except to themselves.
 *
 * This is may change, as one could imagine syntactically legal uses of say if in conjunction
 * with eval or apply.
 */
public class SpecialBinding extends ABinding {
    private final Symbol name;
    private final Map meta;

    private SpecialBinding(Symbol name, Map meta) {
        this.name = name;
        this.meta = meta;
    }

    public static final class Builder {
        private Symbol name;
        private Map meta;
        private SpecialBinding result;

        public Builder() {
            this.name = null;
            this.meta = ImmutableMap.of();
            this.result = null;
        }

        public Builder setName(Symbol name) {
            if(name == null) {
                throw new RuntimeException(
                        "Bindings cannot have null names!");
            } else {
                this.name = name;
                return this;
            }
        }

        public Builder setMeta(Map meta) {
            this.meta = meta;
            return this;
        }

        public SpecialBinding build() {
            if(this.result != null) {
                return this.result;
            } else {
                this.result = new SpecialBinding(name, meta);
                return this.result;
            }
        }
    }

    public static ABinding of(Symbol name) {
        return new Builder()
                .setName(name)
                .build();
    }

    @Override
    public Map getMeta() {
        return meta;
    }

    @Override
    public Object withMeta(Map meta) {
        return new SpecialBinding(name, meta);
    }

    @Override
    public Object getValue() {
        return this;
    }

    @Override
    public Symbol getName() {
        return name;
    }
}