package ox.lang;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ObjectArrays;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Arrays;

/**
 * Created by arrdem on 11/2/15.
 */
public class ArraySeq extends AObj
    implements ISeq {
    private final Object[] backing;
    private final int index;
    private final Map meta;

    private ArraySeq(Object[] backing, int index, @NotNull Map meta) {
        this.meta = meta;
        this.backing = backing;
        this.index = index;
    }

    public static class Builder {
        private Map meta;
        private Object[] backing;
        private int index;
        private ArraySeq res;

        public Builder() {
            index = -1;
            backing = null;
            meta = ImmutableMap.of();
            res = null;
        }

        public Builder setBacking(@NotNull Object[] backing) {
            if(res != null)
                throw new RuntimeException("Cannot update backing after building the result!");

            if(backing == null)
                throw new RuntimeException("The backing array cannot be null!");

            if(index != -1)
                throw new RuntimeException("Backing must be set before index!");

            this.backing = backing;
            return this;
        }

        public Builder setIndex(int index) {
            if(res != null)
                throw new RuntimeException("Cannot update index after building the result!");

            if(index < 0)
                throw new RuntimeException("ArraySeq indexes must be positive!");

            if(backing == null)
                throw new RuntimeException("Index can only be set after backing!");

            if(index > backing.length)
                throw new RuntimeException("Cannot index beyond the length of backing!");

            this.index = index;
            return this;
        }

        public Builder setMeta(@NotNull Map meta) {
            if(meta == null)
                throw new RuntimeException("Metadata cannot be null!");

            this.meta = meta;
            return this;
        }

        public ArraySeq build() {
            if(res != null) {
                return res;
            } else {
                if(index == -1)
                    throw new RuntimeException("Cannot build an ArraySeq with uninitialized index!");

                if(backing == null)
                    throw new RuntimeException("Cannot build an ArraySeq with uninitialized backing!");

                res = new ArraySeq(backing, index, meta);
                return res;
            }
        }
    }

    @NotNull
    public static ArraySeq of(@NotNull Object[] backing,
                              int index) {
        return new Builder()
                .setBacking(backing)
                .setIndex(index)
                .build();
    }

    @NotNull
    public static ArraySeq of(@NotNull Object[] backing,
                              int index,
                              @NotNull Map meta) {
        return new Builder()
                .setBacking(backing)
                .setIndex(index)
                .setMeta(meta)
                .build();
    }

    @NotNull
    @Override
    public Map getMeta() {
        return meta;
    }

    @NotNull
    @Override
    public Object withMeta(@NotNull Map meta) {
        return of(backing, index, meta);
    }

    @Override
    public Object first() {
        if(index < backing.length) {
            return backing[index];
        } else {
            return null;
        }
    }

    @Override
    public ISeq rest() {
        if(index < backing.length) {
            return of(backing, index + 1, meta);
        } else {
            return null;
        }
    }

    private Object[] backing() {
        return backing;
    }

    private int index() {
        return index;
    }
    
    @Override
    public boolean equals(Object other) {
        if(other == null)
            return false;

        if(other instanceof ArraySeq) {
            ArraySeq o = (ArraySeq) other;
            return (Arrays.equals(backing, o.backing()) &&
                    index == o.index());
        } else if(other instanceof ISeq) {
            ISeq o = (ISeq) other;
            ISeq me = this;

            while(me != null && o != null &&
                  me.first() == o.first()) {
                me = me.rest();
                o = o.rest();
            }

            // FIXME: fukkn slooooow
            if(me == o) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('(');
        for (int i = index; i < backing.length; i++) {
            if(i != index)
                builder.append(',');
            Object o = backing[i].toString();
            builder.append(Util.toString(o));
        }
        builder.append(')');
        return builder.toString();
    }
}
