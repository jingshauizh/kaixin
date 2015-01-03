package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import javax.annotation.Nullable;

@GwtCompatible
abstract interface BstAggregate<N extends BstNode<?, N>>
{
  public abstract int entryValue(N paramN);

  public abstract int treeValue(@Nullable N paramN);
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.collect.BstAggregate
 * JD-Core Version:    0.6.0
 */