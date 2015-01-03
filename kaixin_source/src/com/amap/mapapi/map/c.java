package com.amap.mapapi.map;

import android.content.Context;
import com.amap.mapapi.core.AMapException;
import java.net.Proxy;
import java.util.ArrayList;

abstract class c<T, V> extends af
{
  protected volatile boolean a = true;
  protected ArrayList<Thread> b = null;
  protected at<T> c;
  protected au d;
  private Runnable g = new d(this);
  private Runnable h = new e(this);

  public c(ai paramai, Context paramContext)
  {
    super(paramai, paramContext);
    if (this.b == null)
      this.b = new ArrayList();
    this.d = new au(f(), this.h, this.g);
    this.d.a();
  }

  protected abstract ArrayList<T> a(ArrayList<T> paramArrayList)
    throws AMapException;

  protected abstract ArrayList<T> a(ArrayList<T> paramArrayList, Proxy paramProxy)
    throws AMapException;

  public void a()
  {
    this.c.a();
    e();
    this.c.c();
    this.c = null;
    this.e = null;
    this.f = null;
  }

  public void b()
  {
    super.b();
    e();
  }

  public void c()
  {
    super.c();
    e();
  }

  public void d()
  {
    this.a = true;
    if (this.b == null)
      this.b = new ArrayList();
    if (this.d == null)
    {
      this.d = new au(f(), this.h, this.g);
      this.d.a();
    }
  }

  public void e()
  {
    this.a = false;
    if (this.b != null)
    {
      int i = this.b.size();
      for (int j = 0; j < i; j++)
      {
        Thread localThread = (Thread)this.b.get(0);
        if (localThread == null)
          continue;
        localThread.interrupt();
        this.b.remove(0);
      }
      this.b = null;
    }
    if (this.d != null)
    {
      this.d.b();
      this.d.c();
      this.d = null;
    }
  }

  protected abstract int f();

  protected abstract int g();
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.c
 * JD-Core Version:    0.6.0
 */