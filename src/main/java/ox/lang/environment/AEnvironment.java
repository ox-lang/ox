package ox.lang.environment;

import ox.lang.IMeta;
import ox.lang.Symbol;

/**
 * Created by arrdem on 9/26/15.
 */
public abstract class AEnvironment implements IMeta {
    public abstract AEnvironment getParent();
    public abstract ABinding find(Symbol name);
}
