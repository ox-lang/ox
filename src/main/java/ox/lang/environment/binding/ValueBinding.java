package ox.lang.environment.binding;

import com.google.common.collect.ImmutableMap;
import ox.lang.Symbol;
import ox.lang.environment.ABinding;

import java.util.Map;
import java.util.Objects;

/**
 * Created by arrdem on 9/26/15.
 */
public class ValueBinding extends ABinding {
    private final Symbol name;
    private final Object value;
    private final Map meta;

    private ValueBinding(Symbol name, Object value, Map meta) {
        this.name = name;
        this.value = value;
        this.meta = meta;
    }

    public static class Builder {
        private Symbol name;
        private Object value;
        private Map meta;
        private ValueBinding res;

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

        public Builder setValue(Object value) {
            this.value = value;
            return this;
        }

        public Builder setMeta(Map meta) {
            assert meta != null : "Meta cannot be null";
            this.meta = meta;
            return this;
        }

        public ValueBinding build() {
            if(res != null) {
                return res;
            } else {
                assert name != null : "Cannot build with a null name";
                // value may be null
                // meta is not nillable and starts non-nil

                res = new ValueBinding(name, value, meta);
                return res;
            }
        }
    }

    public static ValueBinding of(Symbol name, Object val) {
        return new Builder()
                .setName(name)
                .setValue(val)
                .build();
    }

    public static ValueBinding of(Symbol name, Object val, Map meta) {
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
        assert meta != null : "Meta cannot be null";
        return new ValueBinding(name, value, meta);
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
