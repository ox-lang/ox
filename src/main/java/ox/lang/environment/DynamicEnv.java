package ox.lang.environment;

import ox.lang.Symbol;

import java.util.Map;

/**
 * Created by arrdem on 9/26/15.
 */
public class DynamicEnv extends AEnvironment {
    private final AEnvironment parent;
    private final Map<Symbol,ABinding> bindings;

    private DynamicEnv(AEnvironment parent, Map<Symbol, ABinding> bindings) {
        this.parent = parent;
        this.bindings = bindings;
    }

    @Override
    public AEnvironment getParent() {
        return this.parent;
    }

    @Override
    public ABinding getBinding(Symbol name) {
        if(bindings.containsKey(name))
            return bindings.get(name);
        else
            return null;
    }

    @Override
    public Map getMeta() {
        return null;
    }

    @Override
    public Object withMeta(Map meta) {
        return null;
    }
}
