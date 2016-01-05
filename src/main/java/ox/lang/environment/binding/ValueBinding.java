package ox.lang.environment.binding;

import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ox.lang.Symbol;

import java.util.Map;

/**
 * Created by arrdem on 9/26/15.
 *
 * A binding which has a definite singular value. The overwhelming majority of bindings fall into
 * this category.
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
                // getValue may be null
                // getMeta is not nillable and starts non-nil

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

    @NotNull
    public Map getMeta() {
        return meta;
    }

    @NotNull
    public Object withMeta(@NotNull Map meta) {
        return of(name, value, meta);
    }

    @Nullable
    public Object getValue() {
        return value;
    }

    @NotNull
    public Symbol getName() {
        return name;
    }
}
