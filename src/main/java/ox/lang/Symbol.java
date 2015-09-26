package ox.lang;

import java.util.Map;

/**
 * Created by arrdem on 9/19/15.
 *
 * A Symbol must have a non-null, legal name.
 * A Symbol may have a namespace, but it may be null.
 */
public class Symbol implements INamed, IMeta {
    final String name;
    final String namespace;
    final Map meta;

    private Symbol(String n, String ns, Map meta) {
        this.name = n;
        this.namespace = ns;
        this.meta = meta;
    }

    public class Builder {
        private String name;
        private String namespace;
        private Map meta;
        private Symbol result;

        public Builder() {
            this.namespace = null;
            this.name = null;
            this.meta = null;
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
    public Map meta() {
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