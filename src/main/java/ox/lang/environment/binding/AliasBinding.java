package ox.lang.environment.binding;

import com.google.common.collect.ImmutableMap;
import ox.lang.Symbol;
import ox.lang.environment.ABinding;

import java.util.Map;

/**
 * Created by arrdem on 9/19/15.
 */
public class AliasBinding extends ABinding {
    private final Symbol name;
    private final Symbol value;
    private final Map meta;

    private AliasBinding(Symbol name, Symbol value, Map meta) {
        this.name = name;
        this.value = value;
        this.meta = meta;
    }

    public static class Builder {
        private Symbol name;
        private Symbol value;
        private Map meta;
        private AliasBinding res;

        public Builder() {
            name = null;
            value = null;
            meta = ImmutableMap.of();
            res = null;
        }

        public Builder setName(Symbol name) {
            assert name != null : "Name cannot be null";
            this.name = name;
            return this;
        }

        public Builder setValue(Symbol value) {
            this.value = value;
            return this;
        }

        public Builder setMeta(Map meta) {
            assert meta != null : "Meta cannot be null";
            this.meta = meta;
            return this;
        }

        public AliasBinding build() {
            if(res != null) {
                return res;
            } else {
                assert name != null : "Cannot build with a null name";
                // value may be null
                // meta is not nillable and starts non-nil

                res = new AliasBinding(name, value, meta);
                return res;
            }
        }
    }

    public static AliasBinding of(Symbol name, Symbol val) {
        return new Builder()
                .setName(name)
                .setValue(val)
                .build();
    }

    public static AliasBinding of(Symbol name, Symbol val, Map meta) {
        return new Builder()
                .setName(name)
                .setValue(val)
                .setMeta(meta)
                .build();
    }

    @Override
    public Map meta() {
        return meta;
    }

    @Override
    public Object withMeta(Map meta) {
        return of(name, value, meta);
    }

    @Override
    public Object value() {
        return value;
    }

    @Override
    public Symbol name() {
        return name;
    }
}
