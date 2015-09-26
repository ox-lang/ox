package ox.lang.environment;

import ox.lang.IMeta;
import ox.lang.environment.IBinding;

import java.util.Map;

/**
 * Created by arrdem on 9/19/15.
 */
public class AliasBinding implements IBinding, IMeta {
    @Override
    public Map meta() {
        return null;
    }

    @Override
    public Object withMeta(Map meta) {
        return null;
    }
}
