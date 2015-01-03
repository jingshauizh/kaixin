package com.amap.mapapi.map;

import java.util.ArrayList;

class n extends Thread
{
  an a = new an();
  int b = 30;
  volatile boolean c = true;
  Thread d;
  MapView e;

  public void a()
  {
    monitorenter;
    int i = 0;
    while (true)
    {
      int j;
      try
      {
        if (i >= this.a.size())
          continue;
        ac localac2 = (ac)this.a.get(i);
        int m = 0;
        if (m < localac2.b.size())
        {
          this.e.tileDownloadCtrl.a((String)localac2.b.get(i));
          m++;
          continue;
          this.e.tileDownloadCtrl.a();
          j = 0;
          if (j >= this.a.size())
            continue;
          ac localac1 = (ac)this.a.get(j);
          if (!localac1.j)
            break label181;
          int k = 0;
          if (k >= localac1.b.size())
            break label181;
          this.e.tileDownloadCtrl.c((String)localac1.b.get(j));
          k++;
          continue;
          this.a.clear();
          return;
        }
      }
      finally
      {
        monitorexit;
      }
      i++;
      continue;
      label181: j++;
    }
  }

  public void a(ac paramac)
  {
    monitorenter;
    try
    {
      this.a.insertElementAt(paramac, 0);
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

  public void b()
  {
    this.c = false;
    if (this.d != null)
      this.d.interrupt();
  }

  public void run()
  {
    while (this.c)
    {
      this.d = Thread.currentThread();
      ac localac = (ac)this.a.b();
      if (localac != null)
      {
        if (System.currentTimeMillis() - localac.d <= 50L)
          continue;
        localac.b();
        continue;
      }
      try
      {
        sleep(this.b);
      }
      catch (Exception localException)
      {
        Thread.currentThread().interrupt();
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.n
 * JD-Core Version:    0.6.0
 */