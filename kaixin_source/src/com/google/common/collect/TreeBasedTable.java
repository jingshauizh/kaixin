package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import javax.annotation.Nullable;

@Beta
@GwtCompatible(serializable=true)
public class TreeBasedTable<R, C, V> extends StandardRowSortedTable<R, C, V>
{
  private static final long serialVersionUID;
  private final Comparator<? super C> columnComparator;

  TreeBasedTable(Comparator<? super R> paramComparator, Comparator<? super C> paramComparator1)
  {
    super(new TreeMap(paramComparator), new Factory(paramComparator1));
    this.columnComparator = paramComparator1;
  }

  public static <R extends Comparable, C extends Comparable, V> TreeBasedTable<R, C, V> create()
  {
    return new TreeBasedTable(Ordering.natural(), Ordering.natural());
  }

  public static <R, C, V> TreeBasedTable<R, C, V> create(TreeBasedTable<R, C, ? extends V> paramTreeBasedTable)
  {
    TreeBasedTable localTreeBasedTable = new TreeBasedTable(paramTreeBasedTable.rowComparator(), paramTreeBasedTable.columnComparator());
    localTreeBasedTable.putAll(paramTreeBasedTable);
    return localTreeBasedTable;
  }

  public static <R, C, V> TreeBasedTable<R, C, V> create(Comparator<? super R> paramComparator, Comparator<? super C> paramComparator1)
  {
    Preconditions.checkNotNull(paramComparator);
    Preconditions.checkNotNull(paramComparator1);
    return new TreeBasedTable(paramComparator, paramComparator1);
  }

  public Comparator<? super C> columnComparator()
  {
    return this.columnComparator;
  }

  public boolean contains(@Nullable Object paramObject1, @Nullable Object paramObject2)
  {
    return super.contains(paramObject1, paramObject2);
  }

  public boolean containsColumn(@Nullable Object paramObject)
  {
    return super.containsColumn(paramObject);
  }

  public boolean containsRow(@Nullable Object paramObject)
  {
    return super.containsRow(paramObject);
  }

  public boolean containsValue(@Nullable Object paramObject)
  {
    return super.containsValue(paramObject);
  }

  Iterator<C> createColumnKeyIterator()
  {
    return new MergingIterator(Iterables.transform(this.backingMap.values(), new Function()
    {
      public Iterator<C> apply(Map<C, V> paramMap)
      {
        return paramMap.keySet().iterator();
      }
    }), columnComparator());
  }

  public boolean equals(@Nullable Object paramObject)
  {
    return super.equals(paramObject);
  }

  public V get(@Nullable Object paramObject1, @Nullable Object paramObject2)
  {
    return super.get(paramObject1, paramObject2);
  }

  public V remove(@Nullable Object paramObject1, @Nullable Object paramObject2)
  {
    return super.remove(paramObject1, paramObject2);
  }

  public SortedMap<C, V> row(R paramR)
  {
    return new TreeRow(paramR);
  }

  public Comparator<? super R> rowComparator()
  {
    return rowKeySet().comparator();
  }

  public SortedSet<R> rowKeySet()
  {
    return super.rowKeySet();
  }

  public SortedMap<R, Map<C, V>> rowMap()
  {
    return super.rowMap();
  }

  private static class Factory<C, V>
    implements Supplier<TreeMap<C, V>>, Serializable
  {
    private static final long serialVersionUID;
    final Comparator<? super C> comparator;

    Factory(Comparator<? super C> paramComparator)
    {
      this.comparator = paramComparator;
    }

    public TreeMap<C, V> get()
    {
      return new TreeMap(this.comparator);
    }
  }

  private static class MergingIterator<T> extends AbstractIterator<T>
  {
    private final Comparator<? super T> comparator;
    private T lastValue = null;
    private final Queue<PeekingIterator<T>> queue;

    public MergingIterator(Iterable<? extends Iterator<T>> paramIterable, Comparator<? super T> paramComparator)
    {
      this.comparator = paramComparator;
      1 local1 = new Comparator()
      {
        public int compare(PeekingIterator<T> paramPeekingIterator1, PeekingIterator<T> paramPeekingIterator2)
        {
          return TreeBasedTable.MergingIterator.this.comparator.compare(paramPeekingIterator1.peek(), paramPeekingIterator2.peek());
        }
      };
      this.queue = new PriorityQueue(Math.max(1, Iterables.size(paramIterable)), local1);
      Iterator localIterator1 = paramIterable.iterator();
      while (localIterator1.hasNext())
      {
        Iterator localIterator2 = (Iterator)localIterator1.next();
        if (!localIterator2.hasNext())
          continue;
        this.queue.add(Iterators.peekingIterator(localIterator2));
      }
    }

    protected T computeNext()
    {
      if (!this.queue.isEmpty())
      {
        PeekingIterator localPeekingIterator = (PeekingIterator)this.queue.poll();
        Object localObject = localPeekingIterator.next();
        if ((this.lastValue != null) && (this.comparator.compare(localObject, this.lastValue) == 0));
        for (int i = 1; ; i = 0)
        {
          if (localPeekingIterator.hasNext())
            this.queue.add(localPeekingIterator);
          if (i != 0)
            break;
          this.lastValue = localObject;
          return this.lastValue;
        }
      }
      this.lastValue = null;
      return endOfData();
    }
  }

  private class TreeRow extends StandardTable<R, C, V>.Row
    implements SortedMap<C, V>
  {

    @Nullable
    final C lowerBound;

    @Nullable
    final C upperBound;
    transient SortedMap<C, V> wholeRow;

    TreeRow()
    {
      this(localObject, null, null);
    }

    TreeRow(@Nullable C paramC1, @Nullable C arg3)
    {
      super(paramC1);
      Object localObject1;
      this.lowerBound = localObject1;
      Object localObject2;
      this.upperBound = localObject2;
      if ((localObject1 == null) || (localObject2 == null) || (compare(localObject1, localObject2) <= 0));
      for (boolean bool = true; ; bool = false)
      {
        Preconditions.checkArgument(bool);
        return;
      }
    }

    SortedMap<C, V> backingRowMap()
    {
      return (SortedMap)super.backingRowMap();
    }

    public Comparator<? super C> comparator()
    {
      return TreeBasedTable.this.columnComparator();
    }

    int compare(Object paramObject1, Object paramObject2)
    {
      return comparator().compare(paramObject1, paramObject2);
    }

    SortedMap<C, V> computeBackingRowMap()
    {
      SortedMap localSortedMap = wholeRow();
      if (localSortedMap != null)
      {
        if (this.lowerBound != null)
          localSortedMap = localSortedMap.tailMap(this.lowerBound);
        if (this.upperBound != null)
          localSortedMap = localSortedMap.headMap(this.upperBound);
        return localSortedMap;
      }
      return null;
    }

    public boolean containsKey(Object paramObject)
    {
      return (rangeContains(paramObject)) && (super.containsKey(paramObject));
    }

    public C firstKey()
    {
      if (backingRowMap() == null)
        throw new NoSuchElementException();
      return backingRowMap().firstKey();
    }

    public SortedMap<C, V> headMap(C paramC)
    {
      Preconditions.checkArgument(rangeContains(Preconditions.checkNotNull(paramC)));
      return new TreeRow(TreeBasedTable.this, this.rowKey, this.lowerBound, paramC);
    }

    public C lastKey()
    {
      if (backingRowMap() == null)
        throw new NoSuchElementException();
      return backingRowMap().lastKey();
    }

    void maintainEmptyInvariant()
    {
      if ((wholeRow() != null) && (this.wholeRow.isEmpty()))
      {
        TreeBasedTable.this.backingMap.remove(this.rowKey);
        this.wholeRow = null;
        this.backingRowMap = null;
      }
    }

    public V put(C paramC, V paramV)
    {
      Preconditions.checkArgument(rangeContains(Preconditions.checkNotNull(paramC)));
      return super.put(paramC, paramV);
    }

    boolean rangeContains(@Nullable Object paramObject)
    {
      return (paramObject != null) && ((this.lowerBound == null) || (compare(this.lowerBound, paramObject) <= 0)) && ((this.upperBound == null) || (compare(this.upperBound, paramObject) > 0));
    }

    public SortedMap<C, V> subMap(C paramC1, C paramC2)
    {
      if ((rangeContains(Preconditions.checkNotNull(paramC1))) && (rangeContains(Preconditions.checkNotNull(paramC2))));
      for (boolean bool = true; ; bool = false)
      {
        Preconditions.checkArgument(bool);
        return new TreeRow(TreeBasedTable.this, this.rowKey, paramC1, paramC2);
      }
    }

    public SortedMap<C, V> tailMap(C paramC)
    {
      Preconditions.checkArgument(rangeContains(Preconditions.checkNotNull(paramC)));
      return new TreeRow(TreeBasedTable.this, this.rowKey, paramC, this.upperBound);
    }

    SortedMap<C, V> wholeRow()
    {
      if ((this.wholeRow == null) || ((this.wholeRow.isEmpty()) && (TreeBasedTable.this.backingMap.containsKey(this.rowKey))))
        this.wholeRow = ((SortedMap)TreeBasedTable.this.backingMap.get(this.rowKey));
      return this.wholeRow;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.collect.TreeBasedTable
 * JD-Core Version:    0.6.0
 */