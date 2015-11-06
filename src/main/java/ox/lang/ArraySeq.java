package ox.lang;

import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Created by arrdem on 11/2/15.
 */
public class ArraySeq implements ISeq, IMeta {
    private final Map meta;
    private final Object[] backing;
    private final int index;

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
}
