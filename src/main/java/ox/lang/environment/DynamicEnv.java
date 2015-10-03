package ox.lang.environment;

import ox.lang.Symbol;

import java.util.Map;

/**
 * Created by arrdem on 9/26/15.
 */
public class DynamicEnv extends AEnvironment {
    private static AEnvironment parent;
    private static Map<Symbol, ABinding> bindings;

    private DynamicEnv(AEnvironment parent, Map<Symbol, ABinding> bindings) {
        this.parent = parent;
        this.bindings = bindings;
    }

    @Override
    public AEnvironment getParent() {
        return this.parent;
    }

    @Override
    public ABinding find(Symbol name) {
        if(bindings.containsKey(name))
            return bindings.get(name);
        else
            return null;
    }

    @Override
    public Map meta() {
        return null;
    }

    @Override
    public Object withMeta(Map meta) {
        return null;
    }
}
