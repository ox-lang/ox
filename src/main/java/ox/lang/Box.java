package ox.lang;

import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;
import java.util.Map;

/**
 * Created by arrdem on 1/5/2016.
 */

public class Box
    extends AObj
    implements IRef {

    public final Object value;
    private final Map meta;

    private Box(Object value,
                @NotNull Map meta) {
        this.value = value;
        this.meta = meta;
    }

    public static final class Builder {
        private Object value;
        private Map meta;

        public Builder() {
            value = null;
            meta = ImmutableMap.of();
        }

        public Builder setValue(Object value) {
            this.value = value;
            return this;
        }

        public Builder setMeta(@NotNull Map meta) {
            assert meta != null;
            this.meta = meta;
            return this;
        }

        public Box build() {
            return new Box(value, meta);
        }
    }

    static public Box of(Object value) {
        return new Builder()
            .setValue(value)
            .build();
    }

    static public Box of(Object value,
                         @NotNull Map meta) {
        return new Builder()
            .setValue(value)
            .setMeta(meta)
            .build();
    }
    
    // IRef
    public Object deref() {
        return value;
    }

    // AObj
    @NotNull
    public String toString() {
        return Util.toString(value);
    }

    public boolean equals(Object other) {
        if(other == null && value == null)
            return true;

        if(value != null) {
            if(value == other) {
                return true;
            } else if(other instanceof IRef) {
                IRef r = (IRef) other;
                Object v = r.deref();

                return (value == v ||
                        (value != null && value.equals(v)));
            } else {
                return value.equals(other);
            }
        } else {
            return false;
        }
    }

    // IMeta
    @NotNull
    public Map getMeta() {
        return meta;
    }

    @NotNull
    public Object withMeta(@NotNull Map meta) {
        return new Builder()
            .setValue(value)
            .setMeta(meta)
            .build();
    }

    @NotNull
    public String toStringWithMeta() {
        return String.format("#box [%s, %s]",
                             Util.toString(value),
                             Util.toString(meta));
    }
}
