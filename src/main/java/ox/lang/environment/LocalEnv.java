package ox.lang.environment;

import ox.lang.Symbol;

import java.util.Map;

/**
 * Created by arrdem on 9/26/15.
 */
public class LocalEnv extends AEnvironment {
    @Override
    public AEnvironment getParent() {
        return null;
    }

    @Override
    public ABinding getBinding(Symbol name) {
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
