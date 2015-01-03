package com.tencent.mm.sdk.platformtools;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import junit.framework.Assert;

public final class ObserverPool
{
  private final HashMap<String, LinkedList<Listener>> ay = new HashMap();

  public final boolean add(String paramString, Listener paramListener)
  {
    monitorenter;
    try
    {
      Assert.assertNotNull(paramListener);
      LinkedList localLinkedList = (LinkedList)this.ay.get(paramString);
      if (localLinkedList == null)
      {
        localLinkedList = new LinkedList();
        this.ay.put(paramString, localLinkedList);
      }
      if (localLinkedList.contains(paramListener))
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = paramString;
        arrayOfObject[1] = Util.getStack();
        Log.e("MicroMsg.ObserverPool", "Cannot add the same listener twice. EventId: %s, Stack: %s.", arrayOfObject);
      }
      boolean bool;
      for (int i = 0; ; i = bool)
      {
        return i;
        bool = localLinkedList.add(paramListener);
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public final void asyncPublish(Event paramEvent)
  {
    asyncPublish(paramEvent, Looper.myLooper());
  }

  public final void asyncPublish(Event paramEvent, Looper paramLooper)
  {
    Assert.assertNotNull(paramLooper);
    new Handler(paramLooper).post(new ObserverPool.2(this, paramEvent));
  }

  public final boolean publish(Event paramEvent)
  {
    Assert.assertNotNull(paramEvent);
    String str = paramEvent.getId();
    LinkedList localLinkedList = (LinkedList)this.ay.get(str);
    if (localLinkedList == null)
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = str;
      arrayOfObject[1] = Util.getStack();
      Log.w("MicroMsg.ObserverPool", "No listener for this event %s, Stack: %s.", arrayOfObject);
      return false;
    }
    int i = 0x1 & paramEvent.getFlag();
    int j = 0;
    if (i != 0)
      j = 1;
    if (j != 0)
      Collections.sort(localLinkedList, new ObserverPool.1(this));
    Iterator localIterator = localLinkedList.iterator();
    while ((localIterator.hasNext()) && ((!((Listener)localIterator.next()).callback(paramEvent)) || (j == 0)));
    paramEvent.onComplete();
    return true;
  }

  public final void release()
  {
    monitorenter;
    try
    {
      this.ay.clear();
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

  public final boolean remove(String paramString)
  {
    monitorenter;
    try
    {
      Assert.assertNotNull(paramString);
      Object localObject2 = this.ay.remove(paramString);
      if (localObject2 != null)
      {
        i = 1;
        return i;
      }
      int i = 0;
    }
    finally
    {
      monitorexit;
    }
  }

  public final boolean remove(String paramString, Listener paramListener)
  {
    monitorenter;
    while (true)
    {
      try
      {
        Assert.assertNotNull(paramListener);
        LinkedList localLinkedList = (LinkedList)this.ay.get(paramString);
        if (localLinkedList != null)
        {
          bool = localLinkedList.remove(paramListener);
          if (bool)
            continue;
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = paramString;
          arrayOfObject[1] = Util.getStack();
          Log.e("MicroMsg.ObserverPool", "Listener isnot existed in the map. EventId: %s, Stack: %s.", arrayOfObject);
          return bool;
        }
      }
      finally
      {
        monitorexit;
      }
      boolean bool = false;
    }
  }

  public final String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ObserverPool profile:\n");
    localStringBuilder.append("\tEvent number: ").append(this.ay.size()).append("\n");
    localStringBuilder.append("\tDetail:\n");
    Iterator localIterator = this.ay.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localStringBuilder.append("\t").append(str).append(" : ").append(((LinkedList)this.ay.get(str)).size()).append("\n");
    }
    localStringBuilder.append("End...");
    return localStringBuilder.toString();
  }

  public static class Event
  {
    public static final int FLAG_ORDER_EXE = 1;
    private final String aB;
    private int aC;
    public final Bundle data = new Bundle();

    public Event(String paramString)
    {
      Assert.assertNotNull(paramString);
      this.aB = paramString;
    }

    public int getFlag()
    {
      return this.aC;
    }

    public String getId()
    {
      return this.aB;
    }

    public void onComplete()
    {
    }

    public Event setFlag(int paramInt)
    {
      this.aC = paramInt;
      return this;
    }
  }

  public static abstract class Listener
  {
    private final int priority;

    public Listener()
    {
      this.priority = 0;
    }

    public Listener(int paramInt)
    {
      this.priority = paramInt;
    }

    public abstract boolean callback(ObserverPool.Event paramEvent);

    public int getPriority()
    {
      return this.priority;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.ObserverPool
 * JD-Core Version:    0.6.0
 */