package ox.lang.environment;

import ox.lang.Symbol;

/**
 * Created by arrdem on 9/26/15.
 */
public class LocalEnv implements IEnvironment {
    @Override
    public IEnvironment getParent() {
        return null;
    }

    @Override
    public ABinding resolve(Symbol name) {
        return null;
    }
}
