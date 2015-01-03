package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nullable;

final class SortedTreeMultiset<E> extends AbstractSortedMultiset<E>
{
  private final transient BstAggregate<SortedTreeMultiset<E>.Node> distinctAggregate = new BstAggregate()
  {
    public int entryValue(SortedTreeMultiset<E>.Node paramSortedTreeMultiset)
    {
      return 1;
    }

    public int treeValue(@Nullable SortedTreeMultiset<E>.Node paramSortedTreeMultiset)
    {
      return SortedTreeMultiset.this.distinctOrZero(paramSortedTreeMultiset);
    }
  };
  private final transient BstNodeFactory<SortedTreeMultiset<E>.Node> nodeFactory = new BstNodeFactory()
  {
    public SortedTreeMultiset<E>.Node createNode(SortedTreeMultiset<E>.Node paramSortedTreeMultiset1, @Nullable SortedTreeMultiset<E>.Node paramSortedTreeMultiset2, @Nullable SortedTreeMultiset<E>.Node paramSortedTreeMultiset3)
    {
      return new SortedTreeMultiset.Node(SortedTreeMultiset.this, paramSortedTreeMultiset1.getKey(), SortedTreeMultiset.Node.access$000(paramSortedTreeMultiset1), paramSortedTreeMultiset2, paramSortedTreeMultiset3, null);
    }
  };
  private final transient BstPathFactory<SortedTreeMultiset<E>.Node, BstInOrderPath<SortedTreeMultiset<E>.Node>> pathFactory = BstInOrderPath.inOrderFactory();
  private final GeneralRange<E> range;
  private final AtomicReference<SortedTreeMultiset<E>.Node> rootReference;
  private final transient BstAggregate<SortedTreeMultiset<E>.Node> sizeAggregate = new BstAggregate()
  {
    public int entryValue(SortedTreeMultiset<E>.Node paramSortedTreeMultiset)
    {
      return SortedTreeMultiset.Node.access$000(paramSortedTreeMultiset);
    }

    public int treeValue(@Nullable SortedTreeMultiset<E>.Node paramSortedTreeMultiset)
    {
      return SortedTreeMultiset.this.sizeOrZero(paramSortedTreeMultiset);
    }
  };

  private SortedTreeMultiset(GeneralRange<E> paramGeneralRange, AtomicReference<SortedTreeMultiset<E>.Node> paramAtomicReference)
  {
    super(paramGeneralRange.comparator());
    this.range = paramGeneralRange;
    this.rootReference = paramAtomicReference;
  }

  private SortedTreeMultiset(Comparator<? super E> paramComparator)
  {
    super(paramComparator);
    this.range = GeneralRange.all(paramComparator);
    this.rootReference = new AtomicReference();
  }

  public static <E extends Comparable> SortedTreeMultiset<E> create()
  {
    return new SortedTreeMultiset(Ordering.natural());
  }

  public static <E extends Comparable> SortedTreeMultiset<E> create(Iterable<? extends E> paramIterable)
  {
    SortedTreeMultiset localSortedTreeMultiset = create();
    Iterables.addAll(localSortedTreeMultiset, paramIterable);
    return localSortedTreeMultiset;
  }

  public static <E> SortedTreeMultiset<E> create(Comparator<? super E> paramComparator)
  {
    return new SortedTreeMultiset(paramComparator);
  }

  private int distinctOrZero(@Nullable SortedTreeMultiset<E>.Node paramSortedTreeMultiset)
  {
    if (paramSortedTreeMultiset == null)
      return 0;
    return paramSortedTreeMultiset.distinct;
  }

  private Iterator<Multiset.Entry<E>> iteratorInDirection(@Nullable BstInOrderPath<SortedTreeMultiset<E>.Node> paramBstInOrderPath, BstSide paramBstSide)
  {
    return new Iterator(new AbstractLinkedIterator(paramBstInOrderPath, paramBstSide)
    {
      protected BstInOrderPath<SortedTreeMultiset<E>.Node> computeNext(BstInOrderPath<SortedTreeMultiset<E>.Node> paramBstInOrderPath)
      {
        if (!paramBstInOrderPath.hasNext(this.val$direction))
          return null;
        BstInOrderPath localBstInOrderPath = paramBstInOrderPath.next(this.val$direction);
        if (SortedTreeMultiset.this.range.contains(((SortedTreeMultiset.Node)localBstInOrderPath.getTip()).getKey()));
        while (true)
        {
          return localBstInOrderPath;
          localBstInOrderPath = null;
        }
      }
    })
    {
      E toRemove = null;

      public boolean hasNext()
      {
        return this.val$pathIterator.hasNext();
      }

      public Multiset.Entry<E> next()
      {
        BstInOrderPath localBstInOrderPath = (BstInOrderPath)this.val$pathIterator.next();
        Object localObject = ((SortedTreeMultiset.Node)localBstInOrderPath.getTip()).getKey();
        this.toRemove = localObject;
        return Multisets.immutableEntry(localObject, SortedTreeMultiset.Node.access$000((SortedTreeMultiset.Node)localBstInOrderPath.getTip()));
      }

      public void remove()
      {
        if (this.toRemove != null);
        for (boolean bool = true; ; bool = false)
        {
          Preconditions.checkState(bool);
          SortedTreeMultiset.this.setCount(this.toRemove, 0);
          this.toRemove = null;
          return;
        }
      }
    };
  }

  private int mutate(E paramE, SortedTreeMultiset<E>.MultisetModifier paramSortedTreeMultiset)
  {
    BstMutationRule localBstMutationRule = BstMutationRule.createRule(paramSortedTreeMultiset, BstCountBasedBalancePolicies.singleRebalancePolicy(this.distinctAggregate), this.nodeFactory);
    BstMutationResult localBstMutationResult = BstOperations.mutate(comparator(), localBstMutationRule, (BstNode)this.rootReference.get(), paramE);
    if (!this.rootReference.compareAndSet(localBstMutationResult.getOriginalRoot(), localBstMutationResult.getChangedRoot()))
      throw new ConcurrentModificationException();
    Node localNode = (Node)localBstMutationResult.getOriginalTarget();
    if (localNode == null)
      return 0;
    return localNode.elemOccurrences;
  }

  private int sizeOrZero(@Nullable SortedTreeMultiset<E>.Node paramSortedTreeMultiset)
  {
    if (paramSortedTreeMultiset == null)
      return 0;
    return paramSortedTreeMultiset.size;
  }

  public int add(E paramE, int paramInt)
  {
    Preconditions.checkNotNull(paramE);
    if (paramInt == 0)
      return count(paramE);
    Preconditions.checkArgument(this.range.contains(paramE));
    return mutate(paramE, new AddModifier(paramInt, null));
  }

  E checkElement(Object paramObject)
  {
    Preconditions.checkNotNull(paramObject);
    return paramObject;
  }

  public void clear()
  {
    Node localNode1 = (Node)this.rootReference.get();
    Node localNode2 = (Node)BstRangeOps.minusRange(this.range, BstCountBasedBalancePolicies.fullRebalancePolicy(this.distinctAggregate), this.nodeFactory, localNode1);
    if (!this.rootReference.compareAndSet(localNode1, localNode2))
      throw new ConcurrentModificationException();
  }

  public int count(@Nullable Object paramObject)
  {
    if (paramObject == null);
    int i;
    while (true)
    {
      return 0;
      try
      {
        Object localObject = checkElement(paramObject);
        if (!this.range.contains(localObject))
          continue;
        Node localNode = (Node)BstOperations.seek(comparator(), (BstNode)this.rootReference.get(), localObject);
        if (localNode == null)
        {
          i = 0;
        }
        else
        {
          int j = localNode.elemOccurrences;
          i = j;
        }
      }
      catch (ClassCastException localClassCastException)
      {
        return 0;
      }
    }
    return i;
  }

  Iterator<Multiset.Entry<E>> descendingEntryIterator()
  {
    Node localNode = (Node)this.rootReference.get();
    return iteratorInDirection((BstInOrderPath)BstRangeOps.furthestPath(this.range, BstSide.RIGHT, this.pathFactory, localNode), BstSide.LEFT);
  }

  int distinctElements()
  {
    Node localNode = (Node)this.rootReference.get();
    return BstRangeOps.totalInRange(this.distinctAggregate, this.range, localNode);
  }

  Iterator<Multiset.Entry<E>> entryIterator()
  {
    Node localNode = (Node)this.rootReference.get();
    return iteratorInDirection((BstInOrderPath)BstRangeOps.furthestPath(this.range, BstSide.LEFT, this.pathFactory, localNode), BstSide.RIGHT);
  }

  public SortedMultiset<E> headMultiset(E paramE, BoundType paramBoundType)
  {
    Preconditions.checkNotNull(paramE);
    return new SortedTreeMultiset(this.range.intersect(GeneralRange.upTo(this.comparator, paramE, paramBoundType)), this.rootReference);
  }

  public int remove(@Nullable Object paramObject, int paramInt)
  {
    if (paramObject == null);
    while (true)
    {
      return 0;
      if (paramInt == 0)
        return count(paramObject);
      try
      {
        Object localObject = checkElement(paramObject);
        if (!this.range.contains(localObject))
          continue;
        int i = mutate(localObject, new RemoveModifier(paramInt, null));
        return i;
      }
      catch (ClassCastException localClassCastException)
      {
      }
    }
    return 0;
  }

  public int setCount(E paramE, int paramInt)
  {
    Preconditions.checkNotNull(paramE);
    Preconditions.checkArgument(this.range.contains(paramE));
    return mutate(paramE, new SetCountModifier(paramInt, null));
  }

  public boolean setCount(E paramE, int paramInt1, int paramInt2)
  {
    Preconditions.checkNotNull(paramE);
    Preconditions.checkArgument(this.range.contains(paramE));
    return mutate(paramE, new ConditionalSetCountModifier(paramInt1, paramInt2, null)) == paramInt1;
  }

  public int size()
  {
    Node localNode = (Node)this.rootReference.get();
    return BstRangeOps.totalInRange(this.sizeAggregate, this.range, localNode);
  }

  public SortedMultiset<E> tailMultiset(E paramE, BoundType paramBoundType)
  {
    Preconditions.checkNotNull(paramE);
    return new SortedTreeMultiset(this.range.intersect(GeneralRange.downTo(this.comparator, paramE, paramBoundType)), this.rootReference);
  }

  private final class AddModifier extends SortedTreeMultiset.MultisetModifier
  {
    private final int countToAdd;

    private AddModifier(int arg2)
    {
      super(null);
      int i;
      if (i > 0);
      for (boolean bool = true; ; bool = false)
      {
        Preconditions.checkArgument(bool);
        this.countToAdd = i;
        return;
      }
    }

    int newCount(int paramInt)
    {
      return paramInt + this.countToAdd;
    }
  }

  private final class ConditionalSetCountModifier extends SortedTreeMultiset.MultisetModifier
  {
    private final int expectedCount;
    private final int setCount;

    private ConditionalSetCountModifier(int paramInt1, int arg3)
    {
      super(null);
      int i;
      int k;
      if (i >= 0)
      {
        k = j;
        if (paramInt1 < 0)
          break label51;
      }
      while (true)
      {
        Preconditions.checkArgument(j & k);
        this.expectedCount = paramInt1;
        this.setCount = i;
        return;
        k = 0;
        break;
        label51: j = 0;
      }
    }

    int newCount(int paramInt)
    {
      if (paramInt == this.expectedCount)
        paramInt = this.setCount;
      return paramInt;
    }
  }

  private abstract class MultisetModifier
    implements BstModifier<E, SortedTreeMultiset<E>.Node>
  {
    private MultisetModifier()
    {
    }

    @Nullable
    public BstModificationResult<SortedTreeMultiset<E>.Node> modify(E paramE, @Nullable SortedTreeMultiset<E>.Node paramSortedTreeMultiset)
    {
      if (paramSortedTreeMultiset == null);
      int j;
      for (int i = 0; ; i = SortedTreeMultiset.Node.access$000(paramSortedTreeMultiset))
      {
        j = newCount(i);
        if (i != j)
          break;
        return BstModificationResult.identity(paramSortedTreeMultiset);
      }
      if (j == 0)
        return BstModificationResult.rebalancingChange(paramSortedTreeMultiset, null);
      if (i == 0)
        return BstModificationResult.rebalancingChange(null, new SortedTreeMultiset.Node(SortedTreeMultiset.this, paramE, j, null));
      return BstModificationResult.rebuildingChange(paramSortedTreeMultiset, new SortedTreeMultiset.Node(SortedTreeMultiset.this, paramE, j, null));
    }

    abstract int newCount(int paramInt);
  }

  private final class Node extends BstNode<E, SortedTreeMultiset<E>.Node>
  {
    private final int distinct;
    private final int elemOccurrences;
    private final int size;

    private Node(int arg2)
    {
      this(localObject, i, null, null);
    }

    private Node(int paramSortedTreeMultiset1, @Nullable SortedTreeMultiset<E>.Node paramSortedTreeMultiset2, @Nullable SortedTreeMultiset<E>.Node arg4)
    {
      super(localBstNode1, localBstNode2);
      if (paramSortedTreeMultiset2 > 0);
      for (boolean bool = true; ; bool = false)
      {
        Preconditions.checkArgument(bool);
        this.elemOccurrences = paramSortedTreeMultiset2;
        this.size = (paramSortedTreeMultiset2 + SortedTreeMultiset.this.sizeOrZero(localBstNode1) + SortedTreeMultiset.this.sizeOrZero(localBstNode2));
        this.distinct = (1 + SortedTreeMultiset.this.distinctOrZero(localBstNode1) + SortedTreeMultiset.this.distinctOrZero(localBstNode2));
        return;
      }
    }
  }

  private final class RemoveModifier extends SortedTreeMultiset.MultisetModifier
  {
    private final int countToRemove;

    private RemoveModifier(int arg2)
    {
      super(null);
      int i;
      if (i > 0);
      for (boolean bool = true; ; bool = false)
      {
        Preconditions.checkArgument(bool);
        this.countToRemove = i;
        return;
      }
    }

    int newCount(int paramInt)
    {
      return Math.max(0, paramInt - this.countToRemove);
    }
  }

  private final class SetCountModifier extends SortedTreeMultiset.MultisetModifier
  {
    private final int countToSet;

    private SetCountModifier(int arg2)
    {
      super(null);
      int i;
      if (i >= 0);
      for (boolean bool = true; ; bool = false)
      {
        Preconditions.checkArgument(bool);
        this.countToSet = i;
        return;
      }
    }

    int newCount(int paramInt)
    {
      return this.countToSet;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.collect.SortedTreeMultiset
 * JD-Core Version:    0.6.0
 */