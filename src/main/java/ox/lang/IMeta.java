package ox.lang;

import java.util.Map;
import java.util.Objects;

/**
 * Created by arrdem on 9/19/15.
 */
public interface IMeta {
    Map meta();
    Object withMeta(Map meta);
}