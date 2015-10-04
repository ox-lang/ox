package ox.lang.environment.binding;

import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ox.lang.Symbol;
import ox.lang.environment.ABinding;

import java.util.Map;

/**
 * Created by arrdem on 9/26/15.
 *
 * Special bindings are used to represent symbolic bindings. They have no actual value, but
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
            assert name != null : "Name cannot be null";

            this.name = name;
            return this;
        }

        public Builder setMeta(Map meta) {
            assert meta != null : "Meta cannot be null";

            this.meta = meta;
            return this;
        }

        public SpecialBinding build() {
            if(result != null) {
                return result;
            } else {
                result = new SpecialBinding(name, meta);
                return result;
            }
        }
    }

    public static ABinding of(Symbol name) {
        return new Builder()
                .setName(name)
                .build();
    }

    public static ABinding of(Symbol name, Map meta) {
        return new Builder()
                .setName(name)
                .setMeta(meta)
                .build();
    }

    @NotNull
    public Map getMeta() {
        return meta;
    }

    @NotNull
    public Object withMeta(@NotNull Map meta) {
        return of(name, meta);
    }

    @Nullable
    @Override
    public Object getValue() {
        return this;
    }

    @NotNull
    public Symbol getName() {
        return name;
    }
}