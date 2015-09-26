package ox.lang.environment;

import ox.lang.Symbol;

import java.util.Map;

/**
 * Created by arrdem on 9/26/15.
 */
public class DynamicEnv implements IEnvironment {
    private static IEnvironment parent;
    private static Map<Symbol, ABinding> bindings;

    private DynamicEnv(IEnvironment parent, Map<Symbol, ABinding> bindings) {
        this.parent = parent;
        this.bindings = bindings;
    }

    @Override
    public IEnvironment getParent() {
        return this.parent;
    }

    @Override
    public ABinding resolve(Symbol name) {
        if(bindings.containsKey(name))
            return bindings.get(name);
        else
            return null;
    }
}
