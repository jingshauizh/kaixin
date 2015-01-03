package com.amap.mapapi.map;

import java.util.Hashtable;

class ax extends Thread
{
  int a = 0;
  MapView b;
  long c;
  volatile boolean d = true;
  Thread e;
  private Hashtable f = new Hashtable();

  public ax(MapView paramMapView)
  {
    this.b = paramMapView;
    b();
  }

  public void a()
  {
    monitorenter;
    try
    {
      this.f.clear();
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

  public void a(String paramString)
  {
    monitorenter;
    try
    {
      this.f.remove(paramString);
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
    this.c = System.currentTimeMillis();
  }

  public boolean b(String paramString)
  {
    monitorenter;
    try
    {
      Object localObject2 = this.f.get(paramString);
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

  public void c()
  {
    this.d = false;
    if (this.e != null)
      this.e.interrupt();
  }

  public void c(String paramString)
  {
    monitorenter;
    try
    {
      this.f.put(paramString, "");
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

  public void run()
  {
    this.e = Thread.currentThread();
    while (this.d)
    {
      if ((this.a > 0) && (System.currentTimeMillis() - this.c > 300L))
        this.b.loadBMtilesData2(this.b.e(), true);
      try
      {
        sleep(50L);
      }
      catch (Exception localException)
      {
        Thread.currentThread().interrupt();
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.ax
 * JD-Core Version:    0.6.0
 */