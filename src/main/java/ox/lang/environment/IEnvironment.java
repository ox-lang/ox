package ox.lang.environment;

import ox.lang.Symbol;

/**
 * Created by arrdem on 9/26/15.
 */
public interface IEnvironment {
    IEnvironment getParent();
    ABinding resolve(Symbol name);
}
