package ox.lang.environment;

import com.google.common.collect.ImmutableMap;
import ox.lang.Symbol;

import java.util.Map;

import static ox.lang.Symbol.of;

/**
 * Created by arrdem on 9/26/15.
 */
public class BaseEnv extends AEnvironment {

    private static Map baseEnvMap =
            new ImmutableMap.Builder<Symbol, ABinding>()
            .put(of("if"), null)
            .build();


    @Override
    public AEnvironment getParent() {
        return null;
    }

    @Override
    public ABinding find(Symbol name) {
        return null;
    }

    @Override
    public Map meta() {
        return null;
    }

    @Override
    public Object withMeta(Map meta) {
        return null;
    }
}
