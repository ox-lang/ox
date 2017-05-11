package org.oxlang.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Created by arrdem on 9/19/15.
 *
 * A Keyword must have a non-null, legal getName.
 * A Keyword may have a namespace, but it may be null.
 */
public class Keyword
        extends AObj
        implements Named {
    private final String name;
    private final String namespace;

    private Keyword(@NotNull String n,
                    @Nullable String ns) {
        this.name = n;
        this.namespace = ns;
    }

    public static final class Builder {
        private String name;
        private String namespace;
        private Keyword result;

        public Builder() {
            namespace = null;
            name = null;
            result = null;
        }

        @NotNull
        public Keyword build() {
            if(result != null) {
                return result;
            } else {
                assert name != null : "Name cannot be null";

                result = new Keyword(name, namespace);
                return result;
            }
        }

        @NotNull
        public Builder setName(@NotNull String name) {
            if(name == null)
                throw new RuntimeException("Name cannot be null");

            if(!Named.isValidName(name))
                throw new RuntimeException(String.format("Illegal name '%s'", name));

            this.name = name;
            return this;
        }

        @NotNull
        public Builder setNamespace(@Nullable String ns) {
            if(ns != null && !Named.isValidNamespace(ns))
                throw new RuntimeException(String.format("Illegal namespace '%s'!", ns));

            this.namespace = ns;
            return this;
        }
    }

    @NotNull
    public static Keyword of(@NotNull String name) {
        return new Builder()
                .setName(name)
                .build();
    }

    public static Keyword of(String namespace,
                             @NotNull String name) {
        return new Builder()
                .setNamespace(namespace)
                .setName(name)
                .build();
    }

    /* Named
     */
    @Override
    @NotNull
    public String getName() {
        return name;
    }

    /* Named
     */
    @Override
    @Nullable
    public String getNamespace() {
        return namespace;
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
