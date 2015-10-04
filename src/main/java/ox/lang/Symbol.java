package ox.lang;

import com.google.common.collect.ImmutableMap;
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

    public static Symbol of(String name) {
        return new Builder()
                .setName(name)
                .build();
    }

    public static Symbol of(String namespace, String name) {
        return new Builder()
                .setName(name)
                .setNamespace(namespace)
                .build();
    }

    public static Symbol of(String namespace, String name, Map meta) {
        return new Builder()
                .setMeta(meta)
                .setName(name)
                .setNamespace(namespace)
                .build();
    }

    private Symbol(String n, String ns, Map meta) {
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
            this.namespace = null;
            this.name = null;
            this.meta = ImmutableMap.of();
            this.result = null;
        }

        public Symbol build() {
            if(result != null) {
                return result;
            } else {
                if (this.name == null) {
                    throw new RuntimeException(
                            "Cannot build a Symbol with a null name!");
                }
                result = new Symbol(name, namespace, meta);
                return result;
            }
        }

        public Builder setName(String name) {
            if(Util.isValidName(name)) {
                this.name = name;
                return this;
            } else {
                throw new RuntimeException(
                        String.format("Illegal name '%s'!", name));
            }
        }

        public Builder setNamespace(String ns) {
            if(Util.isValidNamespace(ns)) {
                this.namespace = ns;
                return this;
            } else {
                throw new RuntimeException(
                        String.format("Illegal namespace '%s'!", ns));
            }
        }

        public Builder setMeta(Map meta) {
            this.meta = meta;
            return this;
        }
    }

    /* INamed
     */
    @Override
    public String getName() {
        return name;
    }

    /* INamed
     */
    @Override
    public String getNamespace() {
        return namespace;
    }

    /* IMeta
     */
    @Override
    public Map getMeta() {
        return meta;
    }

    /* IMeta
     */
    @Override
    public Object withMeta(Map meta) {
        return new Symbol(name, namespace, meta);
    }

    /* Object
     */
    public String toString() {
        if(namespace != null) {
            return String.format(":%s/%s", namespace, name);
        } else {
            return String.format(":%s", name);
        }
    }

    public boolean equals(Object other) {
        if(other instanceof Symbol) {
            Symbol otherK = (Symbol) other;
            return ((this.name.equals(otherK.name)) &&
                    (this.namespace.equals(otherK.namespace)));
        } else {
            return false;
        }
    }
}