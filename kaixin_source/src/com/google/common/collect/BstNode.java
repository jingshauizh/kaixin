package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Comparator;
import javax.annotation.Nullable;

@GwtCompatible
class BstNode<K, N extends BstNode<K, N>>
{
  private final K key;

  @Nullable
  private final N left;

  @Nullable
  private final N right;

  BstNode(K paramK, @Nullable N paramN1, @Nullable N paramN2)
  {
    this.key = Preconditions.checkNotNull(paramK);
    this.left = paramN1;
    this.right = paramN2;
  }

  @Nullable
  public final N childOrNull(BstSide paramBstSide)
  {
    switch (1.$SwitchMap$com$google$common$collect$BstSide[paramBstSide.ordinal()])
    {
    default:
      throw new AssertionError();
    case 1:
      return this.left;
    case 2:
    }
    return this.right;
  }

  public final N getChild(BstSide paramBstSide)
  {
    BstNode localBstNode = childOrNull(paramBstSide);
    if (localBstNode != null);
    for (boolean bool = true; ; bool = false)
    {
      Preconditions.checkState(bool);
      return localBstNode;
    }
  }

  public final K getKey()
  {
    return this.key;
  }

  public final boolean hasChild(BstSide paramBstSide)
  {
    return childOrNull(paramBstSide) != null;
  }

  protected final boolean orderingInvariantHolds(Comparator<? super K> paramComparator)
  {
    int i = 1;
    Preconditions.checkNotNull(paramComparator);
    int j = 1;
    int k;
    if (hasChild(BstSide.LEFT))
    {
      if (paramComparator.compare(getChild(BstSide.LEFT).getKey(), this.key) < 0)
      {
        k = i;
        j &= k;
      }
    }
    else if (hasChild(BstSide.RIGHT))
      if (paramComparator.compare(getChild(BstSide.RIGHT).getKey(), this.key) <= 0)
        break label101;
    while (true)
    {
      j &= i;
      return j;
      k = 0;
      break;
      label101: i = 0;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.collect.BstNode
 * JD-Core Version:    0.6.0
 */