package org.oxlang.data;

import org.jetbrains.annotations.NotNull;

/**
 * Created by arrdem on 10/5/15.
 */
public abstract class AObj {

    @Override
    @NotNull
    public abstract String toString();

    @Override
    public abstract boolean equals(Object other);
}
