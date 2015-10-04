package ox.lang.environment;

import ox.lang.IMeta;
import ox.lang.Symbol;

/**
 * Created by arrdem on 9/26/15.
 *
 * Represents the abstract concept of a stack based binding context. Each such context has a
 * parent, may contain a binding, and a binding may be resolved on the whole stack by recursively
 * resolving it on the elements until failure.
 */
public abstract class AEnvironment implements IMeta {
    /*
     * @return The parent env of an env
     */
    public abstract AEnvironment getParent();

    /*
     * Attempts to getBinding a binding in an env
     *
     * @param getName The symbol to getBinding
     * @return     The first binding thereof or null
     */
    public abstract ABinding getBinding(Symbol name);

    /*
     * Attempts to resolve a symbol to a fully qualified getName or to itself in an env by walking
     * parent environments until a unique resolution is found or the symbol is determined to be
     * unbound.
     *
     * @param getName The symbol to getBinding
     * @return     The fully qualified getName thereof, or null if unbound.
     */
    public Symbol resolve(Symbol name) {
        assert name != null : "Cannot resolve null!";

        ABinding res = getBinding(name);

        if(res != null) {
            return res.getName();
        } else {
            AEnvironment parent = getParent();

            if(parent != null) {
                return parent.resolve(name);
            } else {
                return null;
            }
        }
    }
}
