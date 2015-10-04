package ox.lang.environment;

import ox.lang.IMeta;
import ox.lang.Symbol;

import java.util.Map;

/**
 * Created by arrdem on 9/26/15.
 *
 * Represents the abstract concept of a singular binding as it may exist within a binding context
 * (see also AEnvironment). The definition of a binding is that it has a symbolic name, and a
 * value. It may also have metadata.
 *
 * In the future this API may change to allow bindings to be uniquely referenced by some ID other
 * than the symbolic name for the binding.
 */
public abstract class ABinding implements IMeta {
    public abstract Object value();
    public abstract Symbol name();
}
