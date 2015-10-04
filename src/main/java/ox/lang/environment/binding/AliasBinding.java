package ox.lang.environment.binding;

import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ox.lang.Symbol;
import ox.lang.environment.ABinding;

import java.util.Map;

/**
 * Created by arrdem on 9/19/15.
 *
 * A binding representing the alias of some other binding. This concept may need to get hammered
 * down some more, since evaluating an alias in some context is very much nontrivial. But for now
 * it exists.
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

        @NotNull
        public Builder setName(@Nullable Symbol name) {
            assert name != null : "Name cannot be null";

            this.name = name;
            return this;
        }

        @NotNull
        public Builder setValue(@Nullable Symbol value) {
            this.value = value;
            return this;
        }

        @NotNull
        public Builder setMeta(@Nullable Map meta) {
            assert meta != null : "Meta cannot be null";

            this.meta = meta;
            return this;
        }

        public AliasBinding build() {
            if(res != null) {
                return res;
            } else {
                assert name != null : "Cannot build with a null name";
                // getValue may be null
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
