package com.tencent.mm.sdk.storage;

import android.os.Handler;
import android.os.Looper;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArraySet;

public abstract class MStorageEvent<T, E>
{
  private int bY = 0;
  private final Hashtable<T, Object> bZ = new Hashtable();
  private final CopyOnWriteArraySet<E> ca = new CopyOnWriteArraySet();

  private Vector<T> e()
  {
    monitorenter;
    try
    {
      Vector localVector = new Vector();
      localVector.addAll(this.bZ.keySet());
      monitorexit;
      return localVector;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  private void f()
  {
    Vector localVector = e();
    if ((localVector == null) || (localVector.size() <= 0))
      return;
    HashMap localHashMap = new HashMap();
    Iterator localIterator1 = localVector.iterator();
    while (localIterator1.hasNext())
    {
      Object localObject1 = localIterator1.next();
      Object localObject2 = this.bZ.get(localObject1);
      Iterator localIterator2 = this.ca.iterator();
      while (localIterator2.hasNext())
      {
        Object localObject3 = localIterator2.next();
        if ((localObject3 == null) || (localObject2 == null))
          continue;
        if ((localObject2 instanceof Looper))
        {
          Looper localLooper = (Looper)localObject2;
          Handler localHandler = (Handler)localHashMap.get(localLooper);
          if (localHandler == null)
          {
            localHandler = new Handler(localLooper);
            localHashMap.put(localLooper, localHandler);
          }
          localHandler.post(new MStorageEvent.1(this, localObject1, localObject3));
          continue;
        }
        processEvent(localObject1, localObject3);
      }
    }
    this.ca.clear();
  }

  public void add(T paramT, Looper paramLooper)
  {
    monitorenter;
    try
    {
      if (!this.bZ.containsKey(paramT))
      {
        if (paramLooper == null)
          break label30;
        this.bZ.put(paramT, paramLooper);
      }
      while (true)
      {
        return;
        label30: this.bZ.put(paramT, new Object());
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void doNotify()
  {
    if (!isLocked())
      f();
  }

  public boolean event(E paramE)
  {
    return this.ca.add(paramE);
  }

  public boolean isLocked()
  {
    return this.bY > 0;
  }

  public void lock()
  {
    this.bY = (1 + this.bY);
  }

  protected abstract void processEvent(T paramT, E paramE);

  public void remove(T paramT)
  {
    monitorenter;
    try
    {
      this.bZ.remove(paramT);
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

  public void removeAll()
  {
    monitorenter;
    try
    {
      this.bZ.clear();
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

  public void unlock()
  {
    this.bY = (-1 + this.bY);
    if (this.bY <= 0)
    {
      this.bY = 0;
      f();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.storage.MStorageEvent
 * JD-Core Version:    0.6.0
 */