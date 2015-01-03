package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
public final class TreeMultiset<E> extends AbstractMapBasedMultiset<E>
  implements SortedIterable<E>
{

  @GwtIncompatible("not needed in emulated source")
  private static final long serialVersionUID;
  private final Comparator<? super E> comparator;

  private TreeMultiset()
  {
    this(Ordering.natural());
  }

  private TreeMultiset(@Nullable Comparator<? super E> paramComparator)
  {
    super(new TreeMap((Comparator)Preconditions.checkNotNull(paramComparator)));
    this.comparator = paramComparator;
  }

  public static <E extends Comparable> TreeMultiset<E> create()
  {
    return new TreeMultiset();
  }

  public static <E extends Comparable> TreeMultiset<E> create(Iterable<? extends E> paramIterable)
  {
    TreeMultiset localTreeMultiset = create();
    Iterables.addAll(localTreeMultiset, paramIterable);
    return localTreeMultiset;
  }

  public static <E> TreeMultiset<E> create(@Nullable Comparator<? super E> paramComparator)
  {
    if (paramComparator == null)
      return new TreeMultiset();
    return new TreeMultiset(paramComparator);
  }

  @GwtIncompatible("java.io.ObjectInputStream")
  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    paramObjectInputStream.defaultReadObject();
    setBackingMap(new TreeMap((Comparator)paramObjectInputStream.readObject()));
    Serialization.populateMultiset(this, paramObjectInputStream);
  }

  @GwtIncompatible("java.io.ObjectOutputStream")
  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.defaultWriteObject();
    paramObjectOutputStream.writeObject(elementSet().comparator());
    Serialization.writeMultiset(this, paramObjectOutputStream);
  }

  public int add(E paramE, int paramInt)
  {
    if (paramE == null)
      this.comparator.compare(paramE, paramE);
    return super.add(paramE, paramInt);
  }

  public Comparator<? super E> comparator()
  {
    return this.comparator;
  }

  public int count(@Nullable Object paramObject)
  {
    try
    {
      int i = super.count(paramObject);
      return i;
    }
    catch (NullPointerException localNullPointerException)
    {
      return 0;
    }
    catch (ClassCastException localClassCastException)
    {
    }
    return 0;
  }

  Set<E> createElementSet()
  {
    return new SortedMapBasedElementSet((SortedMap)backingMap());
  }

  public SortedSet<E> elementSet()
  {
    return (SortedSet)super.elementSet();
  }

  public Iterator<E> iterator()
  {
    return super.iterator();
  }

  private class SortedMapBasedElementSet extends AbstractMapBasedMultiset<E>.MapBasedElementSet
    implements SortedSet<E>, SortedIterable<E>
  {
    SortedMapBasedElementSet()
    {
      super(localMap);
    }

    public Comparator<? super E> comparator()
    {
      return sortedMap().comparator();
    }

    public E first()
    {
      return sortedMap().firstKey();
    }

    public SortedSet<E> headSet(E paramE)
    {
      return new SortedMapBasedElementSet(TreeMultiset.this, sortedMap().headMap(paramE));
    }

    public E last()
    {
      return sortedMap().lastKey();
    }

    public boolean remove(Object paramObject)
    {
      try
      {
        boolean bool = super.remove(paramObject);
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

    SortedMap<E, Count> sortedMap()
    {
      return (SortedMap)getMap();
    }

    public SortedSet<E> subSet(E paramE1, E paramE2)
    {
      return new SortedMapBasedElementSet(TreeMultiset.this, sortedMap().subMap(paramE1, paramE2));
    }

    public SortedSet<E> tailSet(E paramE)
    {
      return new SortedMapBasedElementSet(TreeMultiset.this, sortedMap().tailMap(paramE));
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.collect.TreeMultiset
 * JD-Core Version:    0.6.0
 */