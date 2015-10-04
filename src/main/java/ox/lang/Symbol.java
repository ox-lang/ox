package ox.lang;

import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Created by arrdem on 9/19/15.
 *
 * A Symbol must have a non-null, legal getName.
 * A Symbol may have a namespace, but it may be null.
 *
 * Symbols are used to represent literal textual symbols, and to store the
 * textual getName to which values are bound during evaluation. The symbol class
 * itself is just a holder for metadata, a getName, possibly a namespace and
 * possibly metadata.
 */
public class Symbol implements INamed, IMeta {
    private final String name;
    private final String namespace;
    private final Map meta;

    private Symbol(@NotNull String n,
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
        private Symbol result;

        public Builder() {
            namespace = null;
            name = null;
            meta = ImmutableMap.of();
            result = null;
        }

        @NotNull
        public Symbol build() {
            if(result != null) {
                return result;
            } else {
                assert name != null : "Name cannot be null";

                result = new Symbol(name, namespace, meta);
                return result;
            }
        }

        @NotNull
        public Builder setName(@NotNull String name) {
            assert name != null : "Name cannot be null";
            assert Util.isValidName(name) : String.format("Illegal name '%s'", name);

            this.name = name;
            return this;
        }

        @NotNull
        public Builder setNamespace(@Nullable String ns) {
            if(ns != null) {
                assert Util.isValidNamespace(ns) : String.format("Illegal namespace '%s'!", ns);

                this.namespace = ns;
            }
            return this;
        }

        @NotNull
        public Builder setMeta(@NotNull Map meta) {
            assert meta != null : "Metadata cannot be null";

            this.meta = meta;
            return this;
        }
    }

    @NotNull
    public static Symbol of(@NotNull String name) {
        return new Builder()
                .setName(name)
                .build();
    }

    @NotNull
    public static Symbol of(@NotNull String name,
                             @NotNull Map meta) {
        return new Builder()
                .setName(name)
                .setMeta(meta)
                .build();
    }

    public static Symbol of(String namespace,
                             @NotNull String name) {
        return new Builder()
                .setNamespace(namespace)
                .setName(name)
                .build();
    }

    public static Symbol of(String namespace,
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
            return String.format("%s/%s", namespace, name);
        } else {
            return String.format("%s", name);
        }
    }

    /* Object
     */
    public boolean equals(Object other) {
        if(other == null)
            return false;

        if(other instanceof Symbol) {
            Symbol otherS = (Symbol) other;
            return ((name.equals(otherS.name)) &&
                    ((this.namespace == null && otherS.namespace == null) ||
                     (namespace.equals(otherS.namespace))));
        } else {
            return false;
        }
    }
}
