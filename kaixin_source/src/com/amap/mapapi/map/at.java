package com.amap.mapapi.map;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

class at<T>
{
  protected LinkedList<T> a = new LinkedList();
  protected final Semaphore b = new Semaphore(0, false);
  protected boolean c = true;

  public ArrayList<T> a(int paramInt, boolean paramBoolean)
  {
    if (this.a == null);
    while (true)
    {
      return null;
      try
      {
        this.b.acquire();
        label16: if (!this.c)
          continue;
        return b(paramInt, paramBoolean);
      }
      catch (InterruptedException localInterruptedException)
      {
        break label16;
      }
    }
  }

  public void a()
  {
    this.c = false;
    this.b.release(100);
  }

  public void a(List<T> paramList, boolean paramBoolean)
  {
    monitorenter;
    try
    {
      LinkedList localLinkedList = this.a;
      if (localLinkedList == null);
      while (true)
      {
        return;
        if (paramBoolean == true)
          this.a.clear();
        if (paramList != null)
          this.a.addAll(paramList);
        b();
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  protected ArrayList<T> b(int paramInt, boolean paramBoolean)
  {
    int i = 0;
    monitorenter;
    try
    {
      LinkedList localLinkedList = this.a;
      ArrayList localArrayList;
      if (localLinkedList == null)
        localArrayList = null;
      while (true)
      {
        return localArrayList;
        int j = this.a.size();
        if (paramInt > j)
          paramInt = j;
        localArrayList = new ArrayList(paramInt);
        while (i < paramInt)
        {
          localArrayList.add(this.a.get(0));
          this.a.removeFirst();
          i++;
        }
        b();
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  protected void b()
  {
    if (this.a == null);
    do
      return;
    while ((!this.c) || (this.a.size() == 0));
    this.b.release();
  }

  public void c()
  {
    if (this.a == null)
      return;
    this.a.clear();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.at
 * JD-Core Version:    0.6.0
 */