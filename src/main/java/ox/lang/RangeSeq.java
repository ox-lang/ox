package ox.lang;

import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Map;

/**
 * Created by arrdem on 11/2/15.
 */
public class RangeSeq implements IMeta, ISeq<Long> {
    private final Map meta;
    private final long base, step, end;

    private RangeSeq(long base, long step, long end, @NotNull Map meta) {
        this.meta = meta;
        this.base = base;
        this.step = step;
        this.end = end;
    }

    public static RangeSeq of(long base, long step, long end) {
        return new RangeSeq(base, step, end, ImmutableMap.of());
    }

    public static RangeSeq of(long base, long step, long end, @NotNull Map meta) {
        return new RangeSeq(base, step, end, meta);
    }

    @NotNull
    @Override
    public Map getMeta() {
        return meta;
    }

    @NotNull
    @Override
    public Object withMeta(@NotNull Map meta) {
        return of(base, step, end, meta);
    }

    @Override
    public Long first() {
        return base;
    }

    @Override
    public ISeq<Long> rest() {
        long next = base + step;
        if(base < end && next > end
                || base > end && next < end)
            return null;
        else
            return of(next, step, end, meta);
    }
}
