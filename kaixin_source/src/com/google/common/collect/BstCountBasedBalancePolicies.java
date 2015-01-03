package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import javax.annotation.Nullable;

@GwtCompatible
final class BstCountBasedBalancePolicies
{
  private static final int SECOND_ROTATE_RATIO = 2;
  private static final int SINGLE_ROTATE_RATIO = 4;

  public static <K, N extends BstNode<K, N>> BstBalancePolicy<N> fullRebalancePolicy(BstAggregate<N> paramBstAggregate)
  {
    Preconditions.checkNotNull(paramBstAggregate);
    return new BstBalancePolicy(singleRebalancePolicy(paramBstAggregate), paramBstAggregate)
    {
      public N balance(BstNodeFactory<N> paramBstNodeFactory, N paramN1, @Nullable N paramN2, @Nullable N paramN3)
      {
        if (paramN2 == null)
          return BstOperations.insertMin(paramN3, paramN1, paramBstNodeFactory, this.val$singleBalancePolicy);
        if (paramN3 == null)
          return BstOperations.insertMax(paramN2, paramN1, paramBstNodeFactory, this.val$singleBalancePolicy);
        int i = this.val$countAggregate.treeValue(paramN2);
        int j = this.val$countAggregate.treeValue(paramN3);
        if (i * 4 <= j)
        {
          BstNode localBstNode2 = balance(paramBstNodeFactory, paramN1, paramN2, paramN3.childOrNull(BstSide.LEFT));
          return this.val$singleBalancePolicy.balance(paramBstNodeFactory, paramN3, localBstNode2, paramN3.childOrNull(BstSide.RIGHT));
        }
        if (j * 4 <= i)
        {
          BstNode localBstNode1 = balance(paramBstNodeFactory, paramN1, paramN2.childOrNull(BstSide.RIGHT), paramN3);
          return this.val$singleBalancePolicy.balance(paramBstNodeFactory, paramN2, paramN2.childOrNull(BstSide.LEFT), localBstNode1);
        }
        return paramBstNodeFactory.createNode(paramN1, paramN2, paramN3);
      }

      @Nullable
      public N combine(BstNodeFactory<N> paramBstNodeFactory, @Nullable N paramN1, @Nullable N paramN2)
      {
        if (paramN1 == null)
          return paramN2;
        if (paramN2 == null)
          return paramN1;
        int i = this.val$countAggregate.treeValue(paramN1);
        int j = this.val$countAggregate.treeValue(paramN2);
        if (i * 4 <= j)
        {
          BstNode localBstNode2 = combine(paramBstNodeFactory, paramN1, paramN2.childOrNull(BstSide.LEFT));
          return this.val$singleBalancePolicy.balance(paramBstNodeFactory, paramN2, localBstNode2, paramN2.childOrNull(BstSide.RIGHT));
        }
        if (j * 4 <= i)
        {
          BstNode localBstNode1 = combine(paramBstNodeFactory, paramN1.childOrNull(BstSide.RIGHT), paramN2);
          return this.val$singleBalancePolicy.balance(paramBstNodeFactory, paramN1, paramN1.childOrNull(BstSide.LEFT), localBstNode1);
        }
        return this.val$singleBalancePolicy.combine(paramBstNodeFactory, paramN1, paramN2);
      }
    };
  }

  public static <N extends BstNode<?, N>> BstBalancePolicy<N> noRebalancePolicy(BstAggregate<N> paramBstAggregate)
  {
    Preconditions.checkNotNull(paramBstAggregate);
    return new BstBalancePolicy(paramBstAggregate)
    {
      public N balance(BstNodeFactory<N> paramBstNodeFactory, N paramN1, @Nullable N paramN2, @Nullable N paramN3)
      {
        return ((BstNodeFactory)Preconditions.checkNotNull(paramBstNodeFactory)).createNode(paramN1, paramN2, paramN3);
      }

      @Nullable
      public N combine(BstNodeFactory<N> paramBstNodeFactory, @Nullable N paramN1, @Nullable N paramN2)
      {
        if (paramN1 == null)
          return paramN2;
        if (paramN2 == null)
          return paramN1;
        if (this.val$countAggregate.treeValue(paramN1) > this.val$countAggregate.treeValue(paramN2))
          return paramBstNodeFactory.createNode(paramN1, paramN1.childOrNull(BstSide.LEFT), combine(paramBstNodeFactory, paramN1.childOrNull(BstSide.RIGHT), paramN2));
        return paramBstNodeFactory.createNode(paramN2, combine(paramBstNodeFactory, paramN1, paramN2.childOrNull(BstSide.LEFT)), paramN2.childOrNull(BstSide.RIGHT));
      }
    };
  }

  public static <K, N extends BstNode<K, N>> BstBalancePolicy<N> singleRebalancePolicy(BstAggregate<N> paramBstAggregate)
  {
    Preconditions.checkNotNull(paramBstAggregate);
    return new BstBalancePolicy(paramBstAggregate)
    {
      private N rotateL(BstNodeFactory<N> paramBstNodeFactory, N paramN1, @Nullable N paramN2, N paramN3)
      {
        Preconditions.checkNotNull(paramN3);
        BstNode localBstNode1 = paramN3.childOrNull(BstSide.LEFT);
        BstNode localBstNode2 = paramN3.childOrNull(BstSide.RIGHT);
        if (this.val$countAggregate.treeValue(localBstNode1) >= 2 * this.val$countAggregate.treeValue(localBstNode2))
          paramN3 = singleR(paramBstNodeFactory, paramN3, localBstNode1, localBstNode2);
        return singleL(paramBstNodeFactory, paramN1, paramN2, paramN3);
      }

      private N rotateR(BstNodeFactory<N> paramBstNodeFactory, N paramN1, N paramN2, @Nullable N paramN3)
      {
        Preconditions.checkNotNull(paramN2);
        BstNode localBstNode1 = paramN2.childOrNull(BstSide.RIGHT);
        BstNode localBstNode2 = paramN2.childOrNull(BstSide.LEFT);
        if (this.val$countAggregate.treeValue(localBstNode1) >= 2 * this.val$countAggregate.treeValue(localBstNode2))
          paramN2 = singleL(paramBstNodeFactory, paramN2, localBstNode2, localBstNode1);
        return singleR(paramBstNodeFactory, paramN1, paramN2, paramN3);
      }

      private N singleL(BstNodeFactory<N> paramBstNodeFactory, N paramN1, @Nullable N paramN2, N paramN3)
      {
        Preconditions.checkNotNull(paramN3);
        return paramBstNodeFactory.createNode(paramN3, paramBstNodeFactory.createNode(paramN1, paramN2, paramN3.childOrNull(BstSide.LEFT)), paramN3.childOrNull(BstSide.RIGHT));
      }

      private N singleR(BstNodeFactory<N> paramBstNodeFactory, N paramN1, N paramN2, @Nullable N paramN3)
      {
        Preconditions.checkNotNull(paramN2);
        return paramBstNodeFactory.createNode(paramN2, paramN2.childOrNull(BstSide.LEFT), paramBstNodeFactory.createNode(paramN1, paramN2.childOrNull(BstSide.RIGHT), paramN3));
      }

      public N balance(BstNodeFactory<N> paramBstNodeFactory, N paramN1, @Nullable N paramN2, @Nullable N paramN3)
      {
        int i = this.val$countAggregate.treeValue(paramN2);
        int j = this.val$countAggregate.treeValue(paramN3);
        if (i + j > 1)
        {
          if (j >= i * 4)
            return rotateL(paramBstNodeFactory, paramN1, paramN2, paramN3);
          if (i >= j * 4)
            return rotateR(paramBstNodeFactory, paramN1, paramN2, paramN3);
        }
        return paramBstNodeFactory.createNode(paramN1, paramN2, paramN3);
      }

      @Nullable
      public N combine(BstNodeFactory<N> paramBstNodeFactory, @Nullable N paramN1, @Nullable N paramN2)
      {
        if (paramN1 == null)
          return paramN2;
        if (paramN2 == null)
          return paramN1;
        BstNode localBstNode;
        if (this.val$countAggregate.treeValue(paramN1) > this.val$countAggregate.treeValue(paramN2))
        {
          BstMutationResult localBstMutationResult2 = BstOperations.extractMax(paramN1, paramBstNodeFactory, this);
          localBstNode = localBstMutationResult2.getOriginalTarget();
          paramN1 = localBstMutationResult2.getChangedRoot();
        }
        while (true)
        {
          return paramBstNodeFactory.createNode(localBstNode, paramN1, paramN2);
          BstMutationResult localBstMutationResult1 = BstOperations.extractMin(paramN2, paramBstNodeFactory, this);
          localBstNode = localBstMutationResult1.getOriginalTarget();
          paramN2 = localBstMutationResult1.getChangedRoot();
        }
      }
    };
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.collect.BstCountBasedBalancePolicies
 * JD-Core Version:    0.6.0
 */