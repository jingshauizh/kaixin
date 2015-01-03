package com.amap.mapapi.core;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class t<T>
  implements List<T>
{
  protected LinkedList<T> a = new LinkedList();

  public void add(int paramInt, T paramT)
  {
    monitorenter;
    try
    {
      this.a.add(paramInt, paramT);
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public boolean add(T paramT)
  {
    monitorenter;
    try
    {
      boolean bool = this.a.add(paramT);
      monitorexit;
      return bool;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public boolean addAll(int paramInt, Collection<? extends T> paramCollection)
  {
    monitorenter;
    try
    {
      boolean bool = this.a.addAll(paramInt, paramCollection);
      monitorexit;
      return bool;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public boolean addAll(Collection<? extends T> paramCollection)
  {
    monitorenter;
    try
    {
      boolean bool = this.a.addAll(paramCollection);
      monitorexit;
      return bool;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public void c(T paramT)
  {
    monitorenter;
    try
    {
      add(paramT);
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public void clear()
  {
    monitorenter;
    try
    {
      this.a.clear();
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public boolean contains(Object paramObject)
  {
    monitorenter;
    try
    {
      boolean bool = this.a.contains(paramObject);
      monitorexit;
      return bool;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public boolean containsAll(Collection<?> paramCollection)
  {
    monitorenter;
    try
    {
      boolean bool = this.a.containsAll(paramCollection);
      monitorexit;
      return bool;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public T get(int paramInt)
  {
    monitorenter;
    try
    {
      Object localObject3 = this.a.get(paramInt);
      localObject2 = localObject3;
      return localObject2;
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        Object localObject2 = null;
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject1;
  }

  public int indexOf(Object paramObject)
  {
    monitorenter;
    try
    {
      int i = this.a.indexOf(paramObject);
      monitorexit;
      return i;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public boolean isEmpty()
  {
    monitorenter;
    try
    {
      boolean bool = this.a.isEmpty();
      monitorexit;
      return bool;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public Iterator<T> iterator()
  {
    monitorenter;
    try
    {
      ListIterator localListIterator = this.a.listIterator();
      monitorexit;
      return localListIterator;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public int lastIndexOf(Object paramObject)
  {
    monitorenter;
    try
    {
      int i = this.a.lastIndexOf(paramObject);
      monitorexit;
      return i;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public ListIterator<T> listIterator()
  {
    monitorenter;
    try
    {
      ListIterator localListIterator = this.a.listIterator();
      monitorexit;
      return localListIterator;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public ListIterator<T> listIterator(int paramInt)
  {
    monitorenter;
    try
    {
      ListIterator localListIterator = this.a.listIterator(paramInt);
      monitorexit;
      return localListIterator;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public T remove(int paramInt)
  {
    monitorenter;
    try
    {
      Object localObject2 = this.a.remove(paramInt);
      monitorexit;
      return localObject2;
    }
    finally
    {
      localObject1 = finally;
      monitorexit;
    }
    throw localObject1;
  }

  public boolean remove(Object paramObject)
  {
    monitorenter;
    try
    {
      boolean bool = this.a.remove(paramObject);
      monitorexit;
      return bool;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public boolean removeAll(Collection<?> paramCollection)
  {
    monitorenter;
    try
    {
      boolean bool = this.a.removeAll(paramCollection);
      monitorexit;
      return bool;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public boolean retainAll(Collection<?> paramCollection)
  {
    monitorenter;
    try
    {
      boolean bool = this.a.retainAll(paramCollection);
      monitorexit;
      return bool;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public T set(int paramInt, T paramT)
  {
    monitorenter;
    try
    {
      Object localObject2 = this.a.set(paramInt, paramT);
      monitorexit;
      return localObject2;
    }
    finally
    {
      localObject1 = finally;
      monitorexit;
    }
    throw localObject1;
  }

  public int size()
  {
    monitorenter;
    try
    {
      int i = this.a.size();
      monitorexit;
      return i;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public List<T> subList(int paramInt1, int paramInt2)
  {
    monitorenter;
    try
    {
      List localList = this.a.subList(paramInt1, paramInt2);
      monitorexit;
      return localList;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public Object[] toArray()
  {
    monitorenter;
    try
    {
      Object[] arrayOfObject = this.a.toArray();
      monitorexit;
      return arrayOfObject;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public <V> V[] toArray(V[] paramArrayOfV)
  {
    monitorenter;
    try
    {
      Object[] arrayOfObject = this.a.toArray(paramArrayOfV);
      monitorexit;
      return arrayOfObject;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.core.t
 * JD-Core Version:    0.6.0
 */