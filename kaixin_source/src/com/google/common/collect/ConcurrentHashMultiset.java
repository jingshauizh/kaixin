package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;

public final class ConcurrentHashMultiset<E> extends AbstractMultiset<E>
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private final transient ConcurrentMap<E, AtomicInteger> countMap;
  private transient ConcurrentHashMultiset<E>.EntrySet entrySet;

  @VisibleForTesting
  ConcurrentHashMultiset(ConcurrentMap<E, AtomicInteger> paramConcurrentMap)
  {
    Preconditions.checkArgument(paramConcurrentMap.isEmpty());
    this.countMap = paramConcurrentMap;
  }

  public static <E> ConcurrentHashMultiset<E> create()
  {
    return new ConcurrentHashMultiset(new ConcurrentHashMap());
  }

  @Beta
  public static <E> ConcurrentHashMultiset<E> create(GenericMapMaker<? super E, ? super Number> paramGenericMapMaker)
  {
    return new ConcurrentHashMultiset(paramGenericMapMaker.makeMap());
  }

  public static <E> ConcurrentHashMultiset<E> create(Iterable<? extends E> paramIterable)
  {
    ConcurrentHashMultiset localConcurrentHashMultiset = create();
    Iterables.addAll(localConcurrentHashMultiset, paramIterable);
    return localConcurrentHashMultiset;
  }

  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    paramObjectInputStream.defaultReadObject();
    ConcurrentMap localConcurrentMap = (ConcurrentMap)paramObjectInputStream.readObject();
    FieldSettersHolder.COUNT_MAP_FIELD_SETTER.set(this, localConcurrentMap);
  }

  private AtomicInteger safeGet(Object paramObject)
  {
    try
    {
      AtomicInteger localAtomicInteger = (AtomicInteger)this.countMap.get(paramObject);
      return localAtomicInteger;
    }
    catch (NullPointerException localNullPointerException)
    {
      return null;
    }
    catch (ClassCastException localClassCastException)
    {
    }
    return null;
  }

  private List<E> snapshot()
  {
    ArrayList localArrayList = Lists.newArrayListWithExpectedSize(size());
    Iterator localIterator = entrySet().iterator();
    while (localIterator.hasNext())
    {
      Multiset.Entry localEntry = (Multiset.Entry)localIterator.next();
      Object localObject = localEntry.getElement();
      for (int i = localEntry.getCount(); i > 0; i--)
        localArrayList.add(localObject);
    }
    return localArrayList;
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.defaultWriteObject();
    paramObjectOutputStream.writeObject(this.countMap);
  }

  public int add(E paramE, int paramInt)
  {
    int j;
    if (paramInt == 0)
    {
      j = count(paramE);
      return j;
    }
    boolean bool1;
    if (paramInt > 0)
    {
      bool1 = true;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(paramInt);
      Preconditions.checkArgument(bool1, "Invalid occurrences: %s", arrayOfObject1);
    }
    AtomicInteger localAtomicInteger1;
    label85: AtomicInteger localAtomicInteger2;
    do
    {
      localAtomicInteger1 = safeGet(paramE);
      if (localAtomicInteger1 == null)
      {
        localAtomicInteger1 = (AtomicInteger)this.countMap.putIfAbsent(paramE, new AtomicInteger(paramInt));
        j = 0;
        if (localAtomicInteger1 == null)
          break;
      }
      int i = localAtomicInteger1.get();
      if (i != 0)
      {
        if (paramInt <= 2147483647 - i);
        for (boolean bool2 = true; ; bool2 = false)
        {
          Object[] arrayOfObject2 = new Object[2];
          arrayOfObject2[0] = Integer.valueOf(paramInt);
          arrayOfObject2[1] = Integer.valueOf(i);
          Preconditions.checkArgument(bool2, "Overflow adding %s occurrences to a count of %s", arrayOfObject2);
          if (!localAtomicInteger1.compareAndSet(i, i + paramInt))
            break label85;
          return i;
          bool1 = false;
          break;
        }
      }
      localAtomicInteger2 = new AtomicInteger(paramInt);
      Object localObject = this.countMap.putIfAbsent(paramE, localAtomicInteger2);
      j = 0;
      if (localObject == null)
        break;
    }
    while (!this.countMap.replace(paramE, localAtomicInteger1, localAtomicInteger2));
    return 0;
  }

  public void clear()
  {
    this.countMap.clear();
  }

  public int count(@Nullable Object paramObject)
  {
    AtomicInteger localAtomicInteger = safeGet(paramObject);
    if (localAtomicInteger == null)
      return 0;
    return localAtomicInteger.get();
  }

  Set<E> createElementSet()
  {
    return new ForwardingSet(this.countMap.keySet())
    {
      protected Set<E> delegate()
      {
        return this.val$delegate;
      }

      public boolean remove(Object paramObject)
      {
        try
        {
          boolean bool = this.val$delegate.remove(paramObject);
          return bool;
        }
        catch (NullPointerException localNullPointerException)
        {
          return false;
        }
        catch (ClassCastException localClassCastException)
        {
        }
        return false;
      }
    };
  }

  int distinctElements()
  {
    return this.countMap.size();
  }

  Iterator<Multiset.Entry<E>> entryIterator()
  {
    return new ForwardingIterator(new AbstractIterator()
    {
      private Iterator<Map.Entry<E, AtomicInteger>> mapEntries = ConcurrentHashMultiset.this.countMap.entrySet().iterator();

      protected Multiset.Entry<E> computeNext()
      {
        Map.Entry localEntry;
        int i;
        do
        {
          if (!this.mapEntries.hasNext())
            return (Multiset.Entry)endOfData();
          localEntry = (Map.Entry)this.mapEntries.next();
          i = ((AtomicInteger)localEntry.getValue()).get();
        }
        while (i == 0);
        return Multisets.immutableEntry(localEntry.getKey(), i);
      }
    })
    {
      private Multiset.Entry<E> last;

      protected Iterator<Multiset.Entry<E>> delegate()
      {
        return this.val$readOnlyIterator;
      }

      public Multiset.Entry<E> next()
      {
        this.last = ((Multiset.Entry)super.next());
        return this.last;
      }

      public void remove()
      {
        if (this.last != null);
        for (boolean bool = true; ; bool = false)
        {
          Preconditions.checkState(bool);
          ConcurrentHashMultiset.this.setCount(this.last.getElement(), 0);
          this.last = null;
          return;
        }
      }
    };
  }

  public Set<Multiset.Entry<E>> entrySet()
  {
    EntrySet localEntrySet = this.entrySet;
    if (localEntrySet == null)
    {
      localEntrySet = new EntrySet(null);
      this.entrySet = localEntrySet;
    }
    return localEntrySet;
  }

  public boolean isEmpty()
  {
    return this.countMap.isEmpty();
  }

  public int remove(@Nullable Object paramObject, int paramInt)
  {
    int i;
    if (paramInt == 0)
    {
      i = count(paramObject);
      return i;
    }
    if (paramInt > 0);
    for (boolean bool = true; ; bool = false)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      Preconditions.checkArgument(bool, "Invalid occurrences: %s", arrayOfObject);
      AtomicInteger localAtomicInteger = safeGet(paramObject);
      i = 0;
      if (localAtomicInteger == null)
        break;
      int j;
      int k;
      do
      {
        j = localAtomicInteger.get();
        i = 0;
        if (j == 0)
          break;
        k = Math.max(0, j - paramInt);
      }
      while (!localAtomicInteger.compareAndSet(j, k));
      if (k == 0)
        this.countMap.remove(paramObject, localAtomicInteger);
      return j;
    }
  }

  public boolean removeExactly(@Nullable Object paramObject, int paramInt)
  {
    if (paramInt == 0);
    AtomicInteger localAtomicInteger;
    int j;
    do
    {
      return true;
      if (paramInt > 0);
      for (boolean bool = true; ; bool = false)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(paramInt);
        Preconditions.checkArgument(bool, "Invalid occurrences: %s", arrayOfObject);
        localAtomicInteger = safeGet(paramObject);
        if (localAtomicInteger != null)
          break;
        return false;
      }
      int i;
      do
      {
        i = localAtomicInteger.get();
        if (i < paramInt)
          return false;
        j = i - paramInt;
      }
      while (!localAtomicInteger.compareAndSet(i, j));
    }
    while (j != 0);
    this.countMap.remove(paramObject, localAtomicInteger);
    return true;
  }

  public int setCount(E paramE, int paramInt)
  {
    Multisets.checkNonnegative(paramInt, "count");
    AtomicInteger localAtomicInteger1 = safeGet(paramE);
    int i;
    if (localAtomicInteger1 == null)
      if (paramInt == 0)
        i = 0;
    do
    {
      return i;
      localAtomicInteger1 = (AtomicInteger)this.countMap.putIfAbsent(paramE, new AtomicInteger(paramInt));
      if (localAtomicInteger1 == null)
        return 0;
      do
      {
        i = localAtomicInteger1.get();
        if (i != 0)
          continue;
        if (paramInt == 0)
          return 0;
        AtomicInteger localAtomicInteger2 = new AtomicInteger(paramInt);
        if ((this.countMap.putIfAbsent(paramE, localAtomicInteger2) != null) && (!this.countMap.replace(paramE, localAtomicInteger1, localAtomicInteger2)))
          break;
        return 0;
      }
      while (!localAtomicInteger1.compareAndSet(i, paramInt));
    }
    while (paramInt != 0);
    this.countMap.remove(paramE, localAtomicInteger1);
    return i;
  }

  public boolean setCount(E paramE, int paramInt1, int paramInt2)
  {
    int i = 1;
    Multisets.checkNonnegative(paramInt1, "oldCount");
    Multisets.checkNonnegative(paramInt2, "newCount");
    AtomicInteger localAtomicInteger1 = safeGet(paramE);
    if (localAtomicInteger1 == null)
      if (paramInt1 != 0)
        i = 0;
    while (true)
    {
      return i;
      if ((paramInt2 != 0) && (this.countMap.putIfAbsent(paramE, new AtomicInteger(paramInt2)) != null))
      {
        return false;
        int j = localAtomicInteger1.get();
        if (j != paramInt1)
          break;
        if (j == 0)
        {
          if (paramInt2 == 0)
          {
            this.countMap.remove(paramE, localAtomicInteger1);
            return i;
          }
          AtomicInteger localAtomicInteger2 = new AtomicInteger(paramInt2);
          int k;
          if (this.countMap.putIfAbsent(paramE, localAtomicInteger2) != null)
          {
            boolean bool = this.countMap.replace(paramE, localAtomicInteger1, localAtomicInteger2);
            k = 0;
            if (!bool);
          }
          else
          {
            k = i;
          }
          return k;
        }
        if (!localAtomicInteger1.compareAndSet(j, paramInt2))
          break;
        if (paramInt2 != 0)
          continue;
        this.countMap.remove(paramE, localAtomicInteger1);
        return i;
      }
    }
    return false;
  }

  public int size()
  {
    long l = 0L;
    Iterator localIterator = this.countMap.values().iterator();
    while (localIterator.hasNext())
      l += ((AtomicInteger)localIterator.next()).get();
    return Ints.saturatedCast(l);
  }

  public Object[] toArray()
  {
    return snapshot().toArray();
  }

  public <T> T[] toArray(T[] paramArrayOfT)
  {
    return snapshot().toArray(paramArrayOfT);
  }

  private class EntrySet extends AbstractMultiset.EntrySet
  {
    private EntrySet()
    {
      super();
    }

    private List<Multiset.Entry<E>> snapshot()
    {
      ArrayList localArrayList = Lists.newArrayListWithExpectedSize(size());
      Iterators.addAll(localArrayList, iterator());
      return localArrayList;
    }

    ConcurrentHashMultiset<E> multiset()
    {
      return ConcurrentHashMultiset.this;
    }

    public boolean remove(Object paramObject)
    {
      boolean bool1 = paramObject instanceof Multiset.Entry;
      boolean bool2 = false;
      if (bool1)
      {
        Multiset.Entry localEntry = (Multiset.Entry)paramObject;
        Object localObject = localEntry.getElement();
        int i = localEntry.getCount();
        bool2 = false;
        if (i != 0)
          bool2 = multiset().setCount(localObject, i, 0);
      }
      return bool2;
    }

    public Object[] toArray()
    {
      return snapshot().toArray();
    }

    public <T> T[] toArray(T[] paramArrayOfT)
    {
      return snapshot().toArray(paramArrayOfT);
    }
  }

  private static class FieldSettersHolder
  {
    static final Serialization.FieldSetter<ConcurrentHashMultiset> COUNT_MAP_FIELD_SETTER = Serialization.getFieldSetter(ConcurrentHashMultiset.class, "countMap");
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.collect.ConcurrentHashMultiset
 * JD-Core Version:    0.6.0
 */