package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import javax.annotation.Nullable;

@GwtCompatible
abstract interface BstBalancePolicy<N extends BstNode<?, N>>
{
  public abstract N balance(BstNodeFactory<N> paramBstNodeFactory, N paramN1, @Nullable N paramN2, @Nullable N paramN3);

  @Nullable
  public abstract N combine(BstNodeFactory<N> paramBstNodeFactory, @Nullable N paramN1, @Nullable N paramN2);
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.collect.BstBalancePolicy
 * JD-Core Version:    0.6.0
 */