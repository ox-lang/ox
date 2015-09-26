package ox.lang.environment;

import ox.lang.Symbol;

/**
 * Created by arrdem on 9/26/15.
 */
public class GlobalEnv implements IEnvironment {
    @Override
    public IEnvironment getParent() {
        return null;
    }

    @Override
    public IBinding resolve(Symbol name) {
        return null;
    }
}
