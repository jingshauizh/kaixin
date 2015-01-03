package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedSet;

@Beta
@GwtCompatible
public final class Constraints
{
  private static <E> Collection<E> checkElements(Collection<E> paramCollection, Constraint<? super E> paramConstraint)
  {
    ArrayList localArrayList = Lists.newArrayList(paramCollection);
    Iterator localIterator = localArrayList.iterator();
    while (localIterator.hasNext())
      paramConstraint.checkElement(localIterator.next());
    return localArrayList;
  }

  public static <E> Collection<E> constrainedCollection(Collection<E> paramCollection, Constraint<? super E> paramConstraint)
  {
    return new ConstrainedCollection(paramCollection, paramConstraint);
  }

  public static <E> List<E> constrainedList(List<E> paramList, Constraint<? super E> paramConstraint)
  {
    if ((paramList instanceof RandomAccess))
      return new ConstrainedRandomAccessList(paramList, paramConstraint);
    return new ConstrainedList(paramList, paramConstraint);
  }

  private static <E> ListIterator<E> constrainedListIterator(ListIterator<E> paramListIterator, Constraint<? super E> paramConstraint)
  {
    return new ConstrainedListIterator(paramListIterator, paramConstraint);
  }

  public static <E> Multiset<E> constrainedMultiset(Multiset<E> paramMultiset, Constraint<? super E> paramConstraint)
  {
    return new ConstrainedMultiset(paramMultiset, paramConstraint);
  }

  public static <E> Set<E> constrainedSet(Set<E> paramSet, Constraint<? super E> paramConstraint)
  {
    return new ConstrainedSet(paramSet, paramConstraint);
  }

  public static <E> SortedSet<E> constrainedSortedSet(SortedSet<E> paramSortedSet, Constraint<? super E> paramConstraint)
  {
    return new ConstrainedSortedSet(paramSortedSet, paramConstraint);
  }

  static <E> Collection<E> constrainedTypePreservingCollection(Collection<E> paramCollection, Constraint<E> paramConstraint)
  {
    if ((paramCollection instanceof SortedSet))
      return constrainedSortedSet((SortedSet)paramCollection, paramConstraint);
    if ((paramCollection instanceof Set))
      return constrainedSet((Set)paramCollection, paramConstraint);
    if ((paramCollection instanceof List))
      return constrainedList((List)paramCollection, paramConstraint);
    return constrainedCollection(paramCollection, paramConstraint);
  }

  public static <E> Constraint<E> notNull()
  {
    return NotNullConstraint.INSTANCE;
  }

  static class ConstrainedCollection<E> extends ForwardingCollection<E>
  {
    private final Constraint<? super E> constraint;
    private final Collection<E> delegate;

    public ConstrainedCollection(Collection<E> paramCollection, Constraint<? super E> paramConstraint)
    {
      this.delegate = ((Collection)Preconditions.checkNotNull(paramCollection));
      this.constraint = ((Constraint)Preconditions.checkNotNull(paramConstraint));
    }

    public boolean add(E paramE)
    {
      this.constraint.checkElement(paramE);
      return this.delegate.add(paramE);
    }

    public boolean addAll(Collection<? extends E> paramCollection)
    {
      return this.delegate.addAll(Constraints.access$000(paramCollection, this.constraint));
    }

    protected Collection<E> delegate()
    {
      return this.delegate;
    }
  }

  @GwtCompatible
  private static class ConstrainedList<E> extends ForwardingList<E>
  {
    final Constraint<? super E> constraint;
    final List<E> delegate;

    ConstrainedList(List<E> paramList, Constraint<? super E> paramConstraint)
    {
      this.delegate = ((List)Preconditions.checkNotNull(paramList));
      this.constraint = ((Constraint)Preconditions.checkNotNull(paramConstraint));
    }

    public void add(int paramInt, E paramE)
    {
      this.constraint.checkElement(paramE);
      this.delegate.add(paramInt, paramE);
    }

    public boolean add(E paramE)
    {
      this.constraint.checkElement(paramE);
      return this.delegate.add(paramE);
    }

    public boolean addAll(int paramInt, Collection<? extends E> paramCollection)
    {
      return this.delegate.addAll(paramInt, Constraints.access$000(paramCollection, this.constraint));
    }

    public boolean addAll(Collection<? extends E> paramCollection)
    {
      return this.delegate.addAll(Constraints.access$000(paramCollection, this.constraint));
    }

    protected List<E> delegate()
    {
      return this.delegate;
    }

    public ListIterator<E> listIterator()
    {
      return Constraints.access$100(this.delegate.listIterator(), this.constraint);
    }

    public ListIterator<E> listIterator(int paramInt)
    {
      return Constraints.access$100(this.delegate.listIterator(paramInt), this.constraint);
    }

    public E set(int paramInt, E paramE)
    {
      this.constraint.checkElement(paramE);
      return this.delegate.set(paramInt, paramE);
    }

    public List<E> subList(int paramInt1, int paramInt2)
    {
      return Constraints.constrainedList(this.delegate.subList(paramInt1, paramInt2), this.constraint);
    }
  }

  static class ConstrainedListIterator<E> extends ForwardingListIterator<E>
  {
    private final Constraint<? super E> constraint;
    private final ListIterator<E> delegate;

    public ConstrainedListIterator(ListIterator<E> paramListIterator, Constraint<? super E> paramConstraint)
    {
      this.delegate = paramListIterator;
      this.constraint = paramConstraint;
    }

    public void add(E paramE)
    {
      this.constraint.checkElement(paramE);
      this.delegate.add(paramE);
    }

    protected ListIterator<E> delegate()
    {
      return this.delegate;
    }

    public void set(E paramE)
    {
      this.constraint.checkElement(paramE);
      this.delegate.set(paramE);
    }
  }

  static class ConstrainedMultiset<E> extends ForwardingMultiset<E>
  {
    private final Constraint<? super E> constraint;
    private Multiset<E> delegate;

    public ConstrainedMultiset(Multiset<E> paramMultiset, Constraint<? super E> paramConstraint)
    {
      this.delegate = ((Multiset)Preconditions.checkNotNull(paramMultiset));
      this.constraint = ((Constraint)Preconditions.checkNotNull(paramConstraint));
    }

    public int add(E paramE, int paramInt)
    {
      this.constraint.checkElement(paramE);
      return this.delegate.add(paramE, paramInt);
    }

    public boolean add(E paramE)
    {
      return standardAdd(paramE);
    }

    public boolean addAll(Collection<? extends E> paramCollection)
    {
      return this.delegate.addAll(Constraints.access$000(paramCollection, this.constraint));
    }

    protected Multiset<E> delegate()
    {
      return this.delegate;
    }

    public int setCount(E paramE, int paramInt)
    {
      this.constraint.checkElement(paramE);
      return this.delegate.setCount(paramE, paramInt);
    }

    public boolean setCount(E paramE, int paramInt1, int paramInt2)
    {
      this.constraint.checkElement(paramE);
      return this.delegate.setCount(paramE, paramInt1, paramInt2);
    }
  }

  static class ConstrainedRandomAccessList<E> extends Constraints.ConstrainedList<E>
    implements RandomAccess
  {
    ConstrainedRandomAccessList(List<E> paramList, Constraint<? super E> paramConstraint)
    {
      super(paramConstraint);
    }
  }

  static class ConstrainedSet<E> extends ForwardingSet<E>
  {
    private final Constraint<? super E> constraint;
    private final Set<E> delegate;

    public ConstrainedSet(Set<E> paramSet, Constraint<? super E> paramConstraint)
    {
      this.delegate = ((Set)Preconditions.checkNotNull(paramSet));
      this.constraint = ((Constraint)Preconditions.checkNotNull(paramConstraint));
    }

    public boolean add(E paramE)
    {
      this.constraint.checkElement(paramE);
      return this.delegate.add(paramE);
    }

    public boolean addAll(Collection<? extends E> paramCollection)
    {
      return this.delegate.addAll(Constraints.access$000(paramCollection, this.constraint));
    }

    protected Set<E> delegate()
    {
      return this.delegate;
    }
  }

  private static class ConstrainedSortedSet<E> extends ForwardingSortedSet<E>
  {
    final Constraint<? super E> constraint;
    final SortedSet<E> delegate;

    ConstrainedSortedSet(SortedSet<E> paramSortedSet, Constraint<? super E> paramConstraint)
    {
      this.delegate = ((SortedSet)Preconditions.checkNotNull(paramSortedSet));
      this.constraint = ((Constraint)Preconditions.checkNotNull(paramConstraint));
    }

    public boolean add(E paramE)
    {
      this.constraint.checkElement(paramE);
      return this.delegate.add(paramE);
    }

    public boolean addAll(Collection<? extends E> paramCollection)
    {
      return this.delegate.addAll(Constraints.access$000(paramCollection, this.constraint));
    }

    protected SortedSet<E> delegate()
    {
      return this.delegate;
    }

    public SortedSet<E> headSet(E paramE)
    {
      return Constraints.constrainedSortedSet(this.delegate.headSet(paramE), this.constraint);
    }

    public SortedSet<E> subSet(E paramE1, E paramE2)
    {
      return Constraints.constrainedSortedSet(this.delegate.subSet(paramE1, paramE2), this.constraint);
    }

    public SortedSet<E> tailSet(E paramE)
    {
      return Constraints.constrainedSortedSet(this.delegate.tailSet(paramE), this.constraint);
    }
  }

  private static enum NotNullConstraint
    implements Constraint<Object>
  {
    static
    {
      NotNullConstraint[] arrayOfNotNullConstraint = new NotNullConstraint[1];
      arrayOfNotNullConstraint[0] = INSTANCE;
      $VALUES = arrayOfNotNullConstraint;
    }

    public Object checkElement(Object paramObject)
    {
      return Preconditions.checkNotNull(paramObject);
    }

    public String toString()
    {
      return "Not null";
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.collect.Constraints
 * JD-Core Version:    0.6.0
 */