package ox.lang.environment;

import com.google.common.collect.ImmutableMap;
import ox.lang.Symbol;
import ox.lang.environment.binding.SpecialBinding;
import ox.lang.environment.binding.ValueBinding;

import java.beans.Statement;
import java.util.Map;

import static ox.lang.Symbol.of;

/**
 * Created by arrdem on 9/26/15.
 *
 * Represents the binding of all the language special forms. Immutable, has no parent and no
 * metadata.
 */
public class BaseEnv extends AEnvironment {
    private static String _ns = "ox.lang.rt";

    private static Symbol _if_star    = Symbol.of(_ns, "if*");
    private static Symbol _def_star   = Symbol.of(_ns, "def*");
    private static Symbol _do_star    = Symbol.of(_ns, "do*");
    private static Symbol _let_star   = Symbol.of(_ns, "let*");
    private static Symbol _list_star  = Symbol.of(_ns, "list*");
    private static Symbol _letrc_star = Symbol.of(_ns, "letrc*");
    private static Symbol _quote_star = Symbol.of(_ns, "quote");
    private static Symbol _ns_star    = Symbol.of(_ns, "ns*");
    private static Symbol _star_ns_star_ = Symbol.of(_ns, "*ns*");

    private static Map mempty = ImmutableMap.of();

    private static Map baseEnvMap =
            new ImmutableMap.Builder<Symbol, ABinding>()
                .put(_if_star, SpecialBinding.of(_if_star))
                .put(_def_star, SpecialBinding.of(_def_star))
                .put(_do_star, SpecialBinding.of(_do_star))
                .put(_let_star, SpecialBinding.of(_let_star))
                .put(_letrc_star, SpecialBinding.of(_letrc_star))
                .put(_list_star, SpecialBinding.of(_list_star))
                .put(_quote_star, SpecialBinding.of(_quote_star))
                .put(_ns_star, SpecialBinding.of(_ns_star))
                .put(_star_ns_star_, ValueBinding.of(_star_ns_star_, Symbol.of(_ns)))
                .build();


    @Override
    public AEnvironment getParent() {
        return null;
    }

    @Override
    public ABinding find(Symbol name) {
        return (ABinding) baseEnvMap.get(name);
    }

    @Override
    public Map meta() {
        return mempty;
    }

    @Override
    public Object withMeta(Map meta) {
        return this;
    }
}
