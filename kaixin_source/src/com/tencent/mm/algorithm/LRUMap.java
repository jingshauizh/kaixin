package com.tencent.mm.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class LRUMap<K, O>
{
  private Map<K, LRUMap<K, O>.TimeVal<O>> c = null;
  private int d;
  private int e;
  private PreRemoveCallback<K, O> f = null;

  public LRUMap(int paramInt)
  {
    this(paramInt, null);
  }

  public LRUMap(int paramInt, PreRemoveCallback<K, O> paramPreRemoveCallback)
  {
    this.d = paramInt;
    this.e = 0;
    this.f = paramPreRemoveCallback;
    this.c = new HashMap();
  }

  public boolean check(K paramK)
  {
    return this.c.containsKey(paramK);
  }

  public boolean checkAndUpTime(K paramK)
  {
    if (this.c.containsKey(paramK))
    {
      ((TimeVal)this.c.get(paramK)).UpTime();
      return true;
    }
    return false;
  }

  public void clear()
  {
    this.c.clear();
  }

  public void clear(OnClearListener<K, O> paramOnClearListener)
  {
    if (this.c != null)
    {
      if (paramOnClearListener != null)
      {
        Iterator localIterator = this.c.entrySet().iterator();
        while (localIterator.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          paramOnClearListener.onClear(localEntry.getKey(), ((TimeVal)localEntry.getValue()).obj);
        }
      }
      this.c.clear();
    }
  }

  public O get(K paramK)
  {
    return getAndUptime(paramK);
  }

  public O getAndUptime(K paramK)
  {
    TimeVal localTimeVal = (TimeVal)this.c.get(paramK);
    if (localTimeVal == null)
      return null;
    localTimeVal.UpTime();
    return localTimeVal.obj;
  }

  public void remove(K paramK)
  {
    if (!this.c.containsKey(paramK))
      return;
    if (this.f != null)
      this.f.preRemoveCallback(paramK, ((TimeVal)this.c.get(paramK)).obj);
    this.c.remove(paramK);
  }

  public void setMaxSize(int paramInt)
  {
    if (paramInt > 0)
      this.d = paramInt;
  }

  public void setPerDeleteSize(int paramInt)
  {
    if (paramInt > 0)
      this.e = paramInt;
  }

  public int size()
  {
    return this.c.size();
  }

  public void update(K paramK, O paramO)
  {
    int i;
    Iterator localIterator;
    if ((TimeVal)this.c.get(paramK) == null)
    {
      TimeVal localTimeVal = new TimeVal(paramO);
      this.c.put(paramK, localTimeVal);
      if (this.c.size() > this.d)
      {
        ArrayList localArrayList = new ArrayList(this.c.entrySet());
        Collections.sort(localArrayList, new LRUMap.1(this));
        if (this.e > 0)
          break label161;
        i = this.d / 10;
        if (i <= 0)
          i = 1;
        localIterator = localArrayList.iterator();
      }
    }
    int k;
    for (int j = i; ; j = k)
      if (localIterator.hasNext())
      {
        remove(((Map.Entry)localIterator.next()).getKey());
        k = j - 1;
        if (k > 0)
          continue;
      }
      else
      {
        return;
        label161: i = this.e;
        break;
        ((TimeVal)this.c.get(paramK)).UpTime();
        ((TimeVal)this.c.get(paramK)).obj = paramO;
        return;
      }
  }

  public static abstract interface OnClearListener<K, O>
  {
    public abstract void onClear(K paramK, O paramO);
  }

  public static abstract interface PreRemoveCallback<K, O>
  {
    public abstract void preRemoveCallback(K paramK, O paramO);
  }

  public class TimeVal<OO>
  {
    public OO obj;
    public Long t;

    public TimeVal()
    {
      Object localObject;
      this.obj = localObject;
      UpTime();
    }

    public void UpTime()
    {
      this.t = Long.valueOf(System.currentTimeMillis());
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.algorithm.LRUMap
 * JD-Core Version:    0.6.0
 */