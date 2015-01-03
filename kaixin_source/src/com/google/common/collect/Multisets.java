package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Set<Lcom.google.common.collect.Multiset.Entry<TE;>;>;
import java.util.Set<TE;>;
import javax.annotation.Nullable;

@GwtCompatible
public final class Multisets
{
  static <E> boolean addAllImpl(Multiset<E> paramMultiset, Collection<? extends E> paramCollection)
  {
    if (paramCollection.isEmpty())
      return false;
    if ((paramCollection instanceof Multiset))
    {
      Iterator localIterator = cast(paramCollection).entrySet().iterator();
      while (localIterator.hasNext())
      {
        Multiset.Entry localEntry = (Multiset.Entry)localIterator.next();
        paramMultiset.add(localEntry.getElement(), localEntry.getCount());
      }
    }
    Iterators.addAll(paramMultiset, paramCollection.iterator());
    return true;
  }

  static <T> Multiset<T> cast(Iterable<T> paramIterable)
  {
    return (Multiset)paramIterable;
  }

  static void checkNonnegative(int paramInt, String paramString)
  {
    if (paramInt >= 0);
    for (boolean bool = true; ; bool = false)
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramString;
      arrayOfObject[1] = Integer.valueOf(paramInt);
      Preconditions.checkArgument(bool, "%s cannot be negative: %s", arrayOfObject);
      return;
    }
  }

  @Beta
  public static boolean containsOccurrences(Multiset<?> paramMultiset1, Multiset<?> paramMultiset2)
  {
    Preconditions.checkNotNull(paramMultiset1);
    Preconditions.checkNotNull(paramMultiset2);
    Iterator localIterator = paramMultiset2.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Multiset.Entry localEntry = (Multiset.Entry)localIterator.next();
      if (paramMultiset1.count(localEntry.getElement()) < localEntry.getCount())
        return false;
    }
    return true;
  }

  static boolean equalsImpl(Multiset<?> paramMultiset, @Nullable Object paramObject)
  {
    if (paramObject == paramMultiset);
    while (true)
      while (true)
      {
        return true;
        if ((paramObject instanceof Multiset))
        {
          Multiset localMultiset = (Multiset)paramObject;
          if ((paramMultiset.size() != localMultiset.size()) || (paramMultiset.entrySet().size() != localMultiset.entrySet().size()))
            return false;
          Iterator localIterator = localMultiset.entrySet().iterator();
          if (!localIterator.hasNext())
            continue;
          Multiset.Entry localEntry = (Multiset.Entry)localIterator.next();
          if (paramMultiset.count(localEntry.getElement()) == localEntry.getCount())
            break;
          return false;
        }
      }
    return false;
  }

  static <E> Multiset<E> forSet(Set<E> paramSet)
  {
    return new SetMultiset(paramSet);
  }

  public static <E> Multiset.Entry<E> immutableEntry(@Nullable E paramE, int paramInt)
  {
    return new ImmutableEntry(paramE, paramInt);
  }

  static int inferDistinctElements(Iterable<?> paramIterable)
  {
    if ((paramIterable instanceof Multiset))
      return ((Multiset)paramIterable).elementSet().size();
    return 11;
  }

  public static <E> Multiset<E> intersection(Multiset<E> paramMultiset, Multiset<?> paramMultiset1)
  {
    Preconditions.checkNotNull(paramMultiset);
    Preconditions.checkNotNull(paramMultiset1);
    return new AbstractMultiset(paramMultiset, paramMultiset1)
    {
      public int count(Object paramObject)
      {
        int i = this.val$multiset1.count(paramObject);
        if (i == 0)
          return 0;
        return Math.min(i, this.val$multiset2.count(paramObject));
      }

      Set<E> createElementSet()
      {
        return Sets.intersection(this.val$multiset1.elementSet(), this.val$multiset2.elementSet());
      }

      int distinctElements()
      {
        return elementSet().size();
      }

      Iterator<Multiset.Entry<E>> entryIterator()
      {
        return new AbstractIterator(this.val$multiset1.entrySet().iterator())
        {
          protected Multiset.Entry<E> computeNext()
          {
            while (this.val$iterator1.hasNext())
            {
              Multiset.Entry localEntry = (Multiset.Entry)this.val$iterator1.next();
              Object localObject = localEntry.getElement();
              int i = Math.min(localEntry.getCount(), Multisets.1.this.val$multiset2.count(localObject));
              if (i > 0)
                return Multisets.immutableEntry(localObject, i);
            }
            return (Multiset.Entry)endOfData();
          }
        };
      }
    };
  }

  static <E> Iterator<E> iteratorImpl(Multiset<E> paramMultiset)
  {
    return new MultisetIteratorImpl(paramMultiset, paramMultiset.entrySet().iterator());
  }

  static boolean removeAllImpl(Multiset<?> paramMultiset, Collection<?> paramCollection)
  {
    if ((paramCollection instanceof Multiset));
    for (Object localObject = ((Multiset)paramCollection).elementSet(); ; localObject = paramCollection)
      return paramMultiset.elementSet().removeAll((Collection)localObject);
  }

  @Beta
  public static boolean removeOccurrences(Multiset<?> paramMultiset1, Multiset<?> paramMultiset2)
  {
    return removeOccurrencesImpl(paramMultiset1, paramMultiset2);
  }

  private static <E> boolean removeOccurrencesImpl(Multiset<E> paramMultiset, Multiset<?> paramMultiset1)
  {
    Preconditions.checkNotNull(paramMultiset);
    Preconditions.checkNotNull(paramMultiset1);
    int i = 0;
    Iterator localIterator = paramMultiset.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Multiset.Entry localEntry = (Multiset.Entry)localIterator.next();
      int j = paramMultiset1.count(localEntry.getElement());
      if (j >= localEntry.getCount())
      {
        localIterator.remove();
        i = 1;
        continue;
      }
      if (j <= 0)
        continue;
      paramMultiset.remove(localEntry.getElement(), j);
      i = 1;
    }
    return i;
  }

  static boolean retainAllImpl(Multiset<?> paramMultiset, Collection<?> paramCollection)
  {
    if ((paramCollection instanceof Multiset));
    for (Object localObject = ((Multiset)paramCollection).elementSet(); ; localObject = paramCollection)
      return paramMultiset.elementSet().retainAll((Collection)localObject);
  }

  @Beta
  public static boolean retainOccurrences(Multiset<?> paramMultiset1, Multiset<?> paramMultiset2)
  {
    return retainOccurrencesImpl(paramMultiset1, paramMultiset2);
  }

  private static <E> boolean retainOccurrencesImpl(Multiset<E> paramMultiset, Multiset<?> paramMultiset1)
  {
    Preconditions.checkNotNull(paramMultiset);
    Preconditions.checkNotNull(paramMultiset1);
    Iterator localIterator = paramMultiset.entrySet().iterator();
    int i = 0;
    while (localIterator.hasNext())
    {
      Multiset.Entry localEntry = (Multiset.Entry)localIterator.next();
      int j = paramMultiset1.count(localEntry.getElement());
      if (j == 0)
      {
        localIterator.remove();
        i = 1;
        continue;
      }
      if (j >= localEntry.getCount())
        continue;
      paramMultiset.setCount(localEntry.getElement(), j);
      i = 1;
    }
    return i;
  }

  static <E> int setCountImpl(Multiset<E> paramMultiset, E paramE, int paramInt)
  {
    checkNonnegative(paramInt, "count");
    int i = paramMultiset.count(paramE);
    int j = paramInt - i;
    if (j > 0)
      paramMultiset.add(paramE, j);
    do
      return i;
    while (j >= 0);
    paramMultiset.remove(paramE, -j);
    return i;
  }

  static <E> boolean setCountImpl(Multiset<E> paramMultiset, E paramE, int paramInt1, int paramInt2)
  {
    checkNonnegative(paramInt1, "oldCount");
    checkNonnegative(paramInt2, "newCount");
    if (paramMultiset.count(paramE) == paramInt1)
    {
      paramMultiset.setCount(paramE, paramInt2);
      return true;
    }
    return false;
  }

  static int sizeImpl(Multiset<?> paramMultiset)
  {
    long l = 0L;
    Iterator localIterator = paramMultiset.entrySet().iterator();
    while (localIterator.hasNext())
      l += ((Multiset.Entry)localIterator.next()).getCount();
    return Ints.saturatedCast(l);
  }

  @Deprecated
  public static <E> Multiset<E> unmodifiableMultiset(ImmutableMultiset<E> paramImmutableMultiset)
  {
    return (Multiset)Preconditions.checkNotNull(paramImmutableMultiset);
  }

  public static <E> Multiset<E> unmodifiableMultiset(Multiset<? extends E> paramMultiset)
  {
    if (((paramMultiset instanceof UnmodifiableMultiset)) || ((paramMultiset instanceof ImmutableMultiset)))
      return paramMultiset;
    return new UnmodifiableMultiset((Multiset)Preconditions.checkNotNull(paramMultiset));
  }

  static abstract class AbstractEntry<E>
    implements Multiset.Entry<E>
  {
    public boolean equals(@Nullable Object paramObject)
    {
      boolean bool1 = paramObject instanceof Multiset.Entry;
      int i = 0;
      if (bool1)
      {
        Multiset.Entry localEntry = (Multiset.Entry)paramObject;
        int j = getCount();
        int k = localEntry.getCount();
        i = 0;
        if (j == k)
        {
          boolean bool2 = Objects.equal(getElement(), localEntry.getElement());
          i = 0;
          if (bool2)
            i = 1;
        }
      }
      return i;
    }

    public int hashCode()
    {
      Object localObject = getElement();
      if (localObject == null);
      for (int i = 0; ; i = localObject.hashCode())
        return i ^ getCount();
    }

    public String toString()
    {
      String str = String.valueOf(getElement());
      int i = getCount();
      if (i == 1)
        return str;
      return str + " x " + i;
    }
  }

  static abstract class ElementSet<E> extends AbstractSet<E>
  {
    public void clear()
    {
      multiset().clear();
    }

    public boolean contains(Object paramObject)
    {
      return multiset().contains(paramObject);
    }

    public boolean containsAll(Collection<?> paramCollection)
    {
      return multiset().containsAll(paramCollection);
    }

    public boolean isEmpty()
    {
      return multiset().isEmpty();
    }

    public Iterator<E> iterator()
    {
      return Iterators.transform(multiset().entrySet().iterator(), new Function()
      {
        public E apply(Multiset.Entry<E> paramEntry)
        {
          return paramEntry.getElement();
        }
      });
    }

    abstract Multiset<E> multiset();

    public boolean remove(Object paramObject)
    {
      int i = multiset().count(paramObject);
      if (i > 0)
      {
        multiset().remove(paramObject, i);
        return true;
      }
      return false;
    }

    public int size()
    {
      return multiset().entrySet().size();
    }
  }

  static abstract class EntrySet<E> extends AbstractSet<Multiset.Entry<E>>
  {
    public void clear()
    {
      multiset().clear();
    }

    public boolean contains(@Nullable Object paramObject)
    {
      Multiset.Entry localEntry;
      if ((paramObject instanceof Multiset.Entry))
      {
        localEntry = (Multiset.Entry)paramObject;
        if (localEntry.getCount() > 0)
          break label23;
      }
      label23: 
      do
        return false;
      while (multiset().count(localEntry.getElement()) != localEntry.getCount());
      return true;
    }

    abstract Multiset<E> multiset();

    public boolean remove(Object paramObject)
    {
      return (contains(paramObject)) && (multiset().elementSet().remove(((Multiset.Entry)paramObject).getElement()));
    }
  }

  static final class ImmutableEntry<E> extends Multisets.AbstractEntry<E>
    implements Serializable
  {
    private static final long serialVersionUID;
    final int count;

    @Nullable
    final E element;

    ImmutableEntry(@Nullable E paramE, int paramInt)
    {
      this.element = paramE;
      this.count = paramInt;
      if (paramInt >= 0);
      for (boolean bool = true; ; bool = false)
      {
        Preconditions.checkArgument(bool);
        return;
      }
    }

    public int getCount()
    {
      return this.count;
    }

    @Nullable
    public E getElement()
    {
      return this.element;
    }
  }

  static final class MultisetIteratorImpl<E>
    implements Iterator<E>
  {
    private boolean canRemove;
    private Multiset.Entry<E> currentEntry;
    private final Iterator<Multiset.Entry<E>> entryIterator;
    private int laterCount;
    private final Multiset<E> multiset;
    private int totalCount;

    MultisetIteratorImpl(Multiset<E> paramMultiset, Iterator<Multiset.Entry<E>> paramIterator)
    {
      this.multiset = paramMultiset;
      this.entryIterator = paramIterator;
    }

    public boolean hasNext()
    {
      return (this.laterCount > 0) || (this.entryIterator.hasNext());
    }

    public E next()
    {
      if (!hasNext())
        throw new NoSuchElementException();
      if (this.laterCount == 0)
      {
        this.currentEntry = ((Multiset.Entry)this.entryIterator.next());
        int i = this.currentEntry.getCount();
        this.laterCount = i;
        this.totalCount = i;
      }
      this.laterCount = (-1 + this.laterCount);
      this.canRemove = true;
      return this.currentEntry.getElement();
    }

    public void remove()
    {
      Preconditions.checkState(this.canRemove, "no calls to next() since the last call to remove()");
      if (this.totalCount == 1)
        this.entryIterator.remove();
      while (true)
      {
        this.totalCount = (-1 + this.totalCount);
        this.canRemove = false;
        return;
        this.multiset.remove(this.currentEntry.getElement());
      }
    }
  }

  private static class SetMultiset<E> extends ForwardingCollection<E>
    implements Multiset<E>, Serializable
  {
    private static final long serialVersionUID;
    final Set<E> delegate;
    transient Set<E> elementSet;
    transient Set<Multiset.Entry<E>> entrySet;

    SetMultiset(Set<E> paramSet)
    {
      this.delegate = ((Set)Preconditions.checkNotNull(paramSet));
    }

    public int add(E paramE, int paramInt)
    {
      throw new UnsupportedOperationException();
    }

    public boolean add(E paramE)
    {
      throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection<? extends E> paramCollection)
    {
      throw new UnsupportedOperationException();
    }

    public int count(Object paramObject)
    {
      if (this.delegate.contains(paramObject))
        return 1;
      return 0;
    }

    protected Set<E> delegate()
    {
      return this.delegate;
    }

    public Set<E> elementSet()
    {
      Object localObject = this.elementSet;
      if (localObject == null)
      {
        localObject = new ElementSet();
        this.elementSet = ((Set)localObject);
      }
      return (Set<E>)localObject;
    }

    public Set<Multiset.Entry<E>> entrySet()
    {
      Object localObject = this.entrySet;
      if (localObject == null)
      {
        localObject = new Multisets.EntrySet()
        {
          public Iterator<Multiset.Entry<E>> iterator()
          {
            return Iterators.transform(Multisets.SetMultiset.this.delegate.iterator(), new Function()
            {
              public Multiset.Entry<E> apply(E paramE)
              {
                return Multisets.immutableEntry(paramE, 1);
              }
            });
          }

          Multiset<E> multiset()
          {
            return Multisets.SetMultiset.this;
          }

          public int size()
          {
            return Multisets.SetMultiset.this.delegate.size();
          }
        };
        this.entrySet = ((Set)localObject);
      }
      return (Set<Multiset.Entry<E>>)localObject;
    }

    public boolean equals(@Nullable Object paramObject)
    {
      boolean bool1 = paramObject instanceof Multiset;
      int i = 0;
      if (bool1)
      {
        Multiset localMultiset = (Multiset)paramObject;
        int j = size();
        int k = localMultiset.size();
        i = 0;
        if (j == k)
        {
          boolean bool2 = this.delegate.equals(localMultiset.elementSet());
          i = 0;
          if (bool2)
            i = 1;
        }
      }
      return i;
    }

    public int hashCode()
    {
      int i = 0;
      Iterator localIterator = iterator();
      if (localIterator.hasNext())
      {
        Object localObject = localIterator.next();
        if (localObject == null);
        for (int j = 0; ; j = localObject.hashCode())
        {
          i += (j ^ 0x1);
          break;
        }
      }
      return i;
    }

    public int remove(Object paramObject, int paramInt)
    {
      int i = 1;
      if (paramInt == 0)
      {
        i = count(paramObject);
        return i;
      }
      if (paramInt > 0);
      int k;
      for (int j = i; ; k = 0)
      {
        Preconditions.checkArgument(j);
        if (this.delegate.remove(paramObject))
          break;
        return 0;
      }
    }

    public int setCount(E paramE, int paramInt)
    {
      Multisets.checkNonnegative(paramInt, "count");
      if (paramInt == count(paramE))
        return paramInt;
      if (paramInt == 0)
      {
        remove(paramE);
        return 1;
      }
      throw new UnsupportedOperationException();
    }

    public boolean setCount(E paramE, int paramInt1, int paramInt2)
    {
      return Multisets.setCountImpl(this, paramE, paramInt1, paramInt2);
    }

    class ElementSet extends ForwardingSet<E>
    {
      ElementSet()
      {
      }

      public boolean add(E paramE)
      {
        throw new UnsupportedOperationException();
      }

      public boolean addAll(Collection<? extends E> paramCollection)
      {
        throw new UnsupportedOperationException();
      }

      protected Set<E> delegate()
      {
        return Multisets.SetMultiset.this.delegate;
      }
    }
  }

  static class UnmodifiableMultiset<E> extends ForwardingMultiset<E>
    implements Serializable
  {
    private static final long serialVersionUID;
    final Multiset<? extends E> delegate;
    transient Set<E> elementSet;
    transient Set<Multiset.Entry<E>> entrySet;

    UnmodifiableMultiset(Multiset<? extends E> paramMultiset)
    {
      this.delegate = paramMultiset;
    }

    public int add(E paramE, int paramInt)
    {
      throw new UnsupportedOperationException();
    }

    public boolean add(E paramE)
    {
      throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection<? extends E> paramCollection)
    {
      throw new UnsupportedOperationException();
    }

    public void clear()
    {
      throw new UnsupportedOperationException();
    }

    Set<E> createElementSet()
    {
      return Collections.unmodifiableSet(this.delegate.elementSet());
    }

    protected Multiset<E> delegate()
    {
      return this.delegate;
    }

    public Set<E> elementSet()
    {
      Set localSet = this.elementSet;
      if (localSet == null)
      {
        localSet = createElementSet();
        this.elementSet = localSet;
      }
      return localSet;
    }

    public Set<Multiset.Entry<E>> entrySet()
    {
      Set localSet = this.entrySet;
      if (localSet == null)
      {
        localSet = Collections.unmodifiableSet(this.delegate.entrySet());
        this.entrySet = localSet;
      }
      return localSet;
    }

    public Iterator<E> iterator()
    {
      return Iterators.unmodifiableIterator(this.delegate.iterator());
    }

    public int remove(Object paramObject, int paramInt)
    {
      throw new UnsupportedOperationException();
    }

    public boolean remove(Object paramObject)
    {
      throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection<?> paramCollection)
    {
      throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection<?> paramCollection)
    {
      throw new UnsupportedOperationException();
    }

    public int setCount(E paramE, int paramInt)
    {
      throw new UnsupportedOperationException();
    }

    public boolean setCount(E paramE, int paramInt1, int paramInt2)
    {
      throw new UnsupportedOperationException();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.collect.Multisets
 * JD-Core Version:    0.6.0
 */