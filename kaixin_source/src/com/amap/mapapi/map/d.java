package com.amap.mapapi.map;

import com.amap.mapapi.core.AMapException;
import java.util.ArrayList;

class d
  implements Runnable
{
  d(c paramc)
  {
  }

  public void run()
  {
    Object localObject = null;
    Thread localThread = Thread.currentThread();
    if (this.a.b != null)
      this.a.b.add(localThread);
    ArrayList localArrayList1 = null;
    while (true)
    {
      if (this.a.a)
      {
        if (this.a.e == null)
        {
          this.a.a = false;
          continue;
        }
        if (this.a.c != null)
          localArrayList1 = this.a.c.a(this.a.g(), false);
        if ((localArrayList1 != null) && (localArrayList1.size() == 0))
          continue;
        if (this.a.a)
          break label112;
      }
      while (true)
      {
        return;
        label112: if (localArrayList1 != null)
        {
          if (!this.a.a)
            continue;
          if ((this.a.e == null) || (this.a.e.e == null))
            break;
        }
      }
      try
      {
        ArrayList localArrayList2 = this.a.a(localArrayList1, this.a.e.e.c());
        localObject = localArrayList2;
        if ((localObject != null) && (this.a.c != null))
          this.a.c.a(localObject, false);
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
 * Qualified Name:     com.amap.mapapi.map.d
 * JD-Core Version:    0.6.0
 */