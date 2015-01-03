package com.amap.mapapi.map;

import com.amap.mapapi.core.AMapException;
import java.util.ArrayList;

class e
  implements Runnable
{
  e(c paramc)
  {
  }

  public void run()
  {
    Object localObject1 = null;
    Thread localThread = Thread.currentThread();
    while (true)
    {
      ArrayList localArrayList2;
      synchronized (this.a.b)
      {
        if (this.a.b == null)
          continue;
        this.a.b.add(localThread);
        localArrayList2 = null;
        if (!this.a.a)
          break label134;
        if (this.a.e == null)
          this.a.a = false;
      }
      if (this.a.c != null)
        localArrayList2 = this.a.c.a(this.a.g(), true);
      if ((localArrayList2 != null) && (localArrayList2.size() == 0))
        continue;
      if (!this.a.a)
        label134: return;
      try
      {
        ArrayList localArrayList3 = this.a.a(localArrayList2);
        localObject1 = localArrayList3;
        if ((localObject1 != null) && (this.a.c != null))
          this.a.c.a(localObject1, false);
        if (this.a.a != true)
          continue;
        try
        {
          Thread.sleep(50L);
        }
        catch (Exception localException)
        {
          Thread.currentThread().interrupt();
        }
      }
      catch (AMapException localAMapException)
      {
        while (true)
          localAMapException.printStackTrace();
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.e
 * JD-Core Version:    0.6.0
 */