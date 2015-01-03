package com.amap.mapapi.map;

import android.os.Handler;

class b
  implements Runnable
{
  b(a parama)
  {
  }

  public void run()
  {
    this.a.e();
    if (!this.a.f())
    {
      a.a(this.a).removeCallbacks(this);
      a.a(this.a, null);
      this.a.b();
    }
    long l1;
    long l2;
    do
    {
      return;
      l1 = System.currentTimeMillis();
      this.a.a();
      this.a.g();
      l2 = System.currentTimeMillis();
    }
    while (l2 - l1 >= this.a.d);
    try
    {
      Thread.sleep(this.a.d - (l2 - l1));
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      localInterruptedException.printStackTrace();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.b
 * JD-Core Version:    0.6.0
 */