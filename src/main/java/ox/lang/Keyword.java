package ox.lang;
import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Created by arrdem on 9/19/15.
 *
 * A Keyword must have a non-null, legal getName.
 * A Keyword may have a namespace, but it may be null.
 */
public class Keyword implements INamed, IMeta {
    private final String name;
    private final String namespace;
    private final Map meta;

    private Keyword(@NotNull String n,
                    @Nullable String ns,
                    @NotNull Map meta) {
        this.name = n;
        this.namespace = ns;
        this.meta = meta;
    }

    public static final class Builder {
        private String name;
        private String namespace;
        private Map meta;
        private Keyword result;

        public Builder() {
            namespace = null;
            name = null;
            meta = ImmutableMap.of();
            result = null;
        }

        @NotNull
        public Keyword build() {
            if(result != null) {
                return result;
            } else {
                assert name != null : "Name cannot be null";

                result = new Keyword(name, namespace, meta);
                return result;
            }
        }

        @NotNull
        public Builder setName(@NotNull String name) {
            if(name == null)
                throw new RuntimeException("Name cannot be null");

            if(!Util.isValidName(name))
                throw new RuntimeException(String.format("Illegal name '%s'", name));

            this.name = name;
            return this;
        }

        @NotNull
        public Builder setNamespace(@Nullable String ns) {
            if(ns != null && !Util.isValidNamespace(ns))
                throw new RuntimeException(String.format("Illegal namespace '%s'!", ns));

            this.namespace = ns;
            return this;
        }

        @NotNull
        public Builder setMeta(@NotNull Map meta) {
            if(meta == null)
                throw new RuntimeException("Metadata cannot be null");

            this.meta = meta;
            return this;
        }
    }

    @NotNull
    public static Keyword of(@NotNull String name) {
        return new Builder()
                .setName(name)
                .build();
    }

    @NotNull
    public static Keyword of(@NotNull String name,
                             @NotNull Map meta) {
        return new Builder()
                .setName(name)
                .setMeta(meta)
                .build();
    }

    public static Keyword of(String namespace,
                             @NotNull String name) {
        return new Builder()
                .setNamespace(namespace)
                .setName(name)
                .build();
    }

    public static Keyword of(String namespace,
                             @NotNull String name,
                             @NotNull Map meta) {
        return new Builder()
                .setNamespace(namespace)
                .setName(name)
                .setMeta(meta)
                .build();
    }

    /* INamed
     */
    @Override
    @NotNull
    public String getName() {
        return name;
    }

    /* INamed
     */
    @Override
    @Nullable
    public String getNamespace() {
        return namespace;
    }

    /* IMeta
     */
    @Override
    @NotNull
    public Map getMeta() {
        return meta;
    }

    /* IMeta
     */
    @Override
    @NotNull
    public Object withMeta(Map meta) {
        return of(name, namespace, meta);
    }

    /* Object
     */
    @NotNull
    public String toString() {
        if(namespace != null) {
            return String.format(":%s/%s", namespace, name);
        } else {
            return String.format(":%s", name);
        }
    }

    /* Object
     */
    public boolean equals(Object other) {
        if(other == null)
            return false;

        if(other instanceof Keyword) {
            Keyword otherK = (Keyword) other;
            return ((name.equals(otherK.name)) &&
                    ((namespace == null && otherK.namespace == null) ||
                     (namespace.equals(otherK.namespace))));
        } else {
            return false;
        }
    }
}
