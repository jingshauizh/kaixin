package com.amap.mapapi.map;

class au
{
  private Thread[] a;

  public au(int paramInt, Runnable paramRunnable1, Runnable paramRunnable2)
  {
    this.a = new Thread[paramInt];
    int i = 0;
    if (i < paramInt)
    {
      if ((i == 0) && (paramInt > 1))
        this.a[i] = new Thread(paramRunnable1);
      while (true)
      {
        i++;
        break;
        this.a[i] = new Thread(paramRunnable2);
      }
    }
  }

  public void a()
  {
    for (Thread localThread : this.a)
    {
      localThread.setDaemon(true);
      localThread.start();
    }
  }

  public void b()
  {
    int i = 1;
    int j = this.a.length;
    if (j > i);
    try
    {
      this.a[0].join();
      if (i >= j);
    }
    catch (InterruptedException localInterruptedException2)
    {
      try
      {
        while (true)
        {
          this.a[i].join(300L);
          i++;
        }
        localInterruptedException2 = localInterruptedException2;
        localInterruptedException2.printStackTrace();
      }
      catch (InterruptedException localInterruptedException1)
      {
        while (true)
          localInterruptedException1.printStackTrace();
      }
    }
  }

  public void c()
  {
    if (this.a == null)
      return;
    int i = this.a.length;
    for (int j = 0; j < i; j++)
    {
      this.a[j].interrupt();
      this.a[j] = null;
    }
    this.a = null;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.au
 * JD-Core Version:    0.6.0
 */