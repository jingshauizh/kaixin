package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import javax.annotation.Nullable;

@GwtCompatible
abstract class BstNodeFactory<N extends BstNode<?, N>>
{
  public final N createLeaf(N paramN)
  {
    return createNode(paramN, null, null);
  }

  public abstract N createNode(N paramN1, @Nullable N paramN2, @Nullable N paramN3);
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.collect.BstNodeFactory
 * JD-Core Version:    0.6.0
 */