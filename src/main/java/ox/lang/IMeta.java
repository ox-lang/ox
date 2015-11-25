package ox.lang;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Created by arrdem on 9/19/15.
 *
 * Interface for values which may have arbitrary metadata added by the user or by the system.
 * Metadata can never be null, however it may be an empty map. Any value which supports metadata
 * must be able to "update" itself, that is return something equal to itself but with different
 * metadata.
 *
 * Metadata may not be used in equality comparisons.
 */
public interface IMeta {
    /*
     * @return The metadata for the given Object.
     */
    @NotNull
    Map getMeta();

    /*
     * @param meta New metadata
     * @return     An Object with the specified metadata.
     */
    @NotNull
    Object withMeta(@NotNull Map meta);
}
