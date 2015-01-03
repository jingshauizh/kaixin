package com.amap.mapapi.map;

import java.util.ArrayList;

class ao extends Thread
{
  volatile boolean a = true;
  Thread b;
  MapView c;
  ArrayList<ap> d = new ArrayList();

  public ao(MapView paramMapView)
  {
    this.c = paramMapView;
  }

  public void a()
  {
    this.a = false;
    if (this.b != null)
      this.b.interrupt();
  }

  public void a(ap paramap)
  {
    this.d.add(paramap);
  }

  public boolean a(String paramString)
  {
    int i = 0;
    try
    {
      while (i < this.d.size())
      {
        boolean bool = ((ap)this.d.get(i)).a.equals(paramString);
        if (bool)
          return true;
        i++;
      }
    }
    catch (Exception localException)
    {
      return false;
    }
    return false;
  }

  public void run()
  {
    while (this.a)
    {
      this.b = Thread.currentThread();
      if (this.d.size() > 0)
      {
        ap localap = (ap)this.d.get(0);
        this.d.remove(0);
        if (!this.c.a(localap.a))
          continue;
        localap.a(this.c.f);
        this.c.postInvalidate();
      }
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
 * Qualified Name:     com.amap.mapapi.map.ao
 * JD-Core Version:    0.6.0
 */