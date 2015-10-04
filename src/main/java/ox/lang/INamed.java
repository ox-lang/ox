package ox.lang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by arrdem on 9/19/15.
 *
 * In Ox as in Clojure, things which are named may be namespace qualified. This interface
 * represents that pattern.
 *
 * Named things are by convention written a/b where a is said to be the namespace and b the name.
 * A named thing must always have a non-null name. It may have a non-null namespace, but this is
 * not required or guaranteed.
 */
public interface INamed {
    /*
     * @return A String relating to the textual representation of the Object.
     *
     * Note that this may never be null. Every named thing must have a name.
     */
    @NotNull
    String getName();

    /*
     * @return A String relating to the namespace of the Object, or null.
     *
     * Note that unlike the name, the namespace may be null. Some fully qualified names such as
     * namespaces have no namespace part.
     */
    @Nullable
    String getNamespace();
}
