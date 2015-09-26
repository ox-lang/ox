package ox.lang;

import clojure.lang.Named;

import javax.lang.model.element.Name;
import java.security.Key;

/**
 * Created by arrdem on 9/19/15.
 */
public class Keyword implements INamed {
    final String name;
    final String namespace;

    private Keyword(String n, String ns) {
        this.name = n;
        this.namespace = ns;
    }

    public class Builder {
        private String name;
        private String namespace;
        private Keyword result = null;

        public Keyword build() {
            if(result != null) {
                return result;
            } else {
                result = new Keyword(name, namespace);
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
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getNamespace() {
        return namespace;
    }
}
