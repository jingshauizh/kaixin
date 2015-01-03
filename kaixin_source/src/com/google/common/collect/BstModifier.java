package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import javax.annotation.Nullable;

@GwtCompatible
abstract interface BstModifier<K, N extends BstNode<K, N>>
{
  public abstract BstModificationResult<N> modify(K paramK, @Nullable N paramN);
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.collect.BstModifier
 * JD-Core Version:    0.6.0
 */