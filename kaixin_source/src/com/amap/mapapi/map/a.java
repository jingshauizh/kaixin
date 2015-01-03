package com.amap.mapapi.map;

import android.os.Handler;
import android.os.Looper;

abstract class a
{
  protected int a = 0;
  public boolean b = false;
  protected int c;
  protected int d;
  private Handler e = null;
  private Runnable f = new b(this);

  public a(int paramInt1, int paramInt2)
  {
    this.c = paramInt1;
    this.d = paramInt2;
  }

  private void h()
  {
    this.b = false;
  }

  protected abstract void a();

  protected abstract void b();

  public void c()
  {
    if (!f())
    {
      this.e = new Handler(Looper.getMainLooper());
      this.b = true;
      this.a = 0;
    }
    g();
  }

  public void d()
  {
    h();
    this.f.run();
  }

  protected void e()
  {
    this.a += this.d;
    if ((this.c != -1) && (this.a > this.c))
      h();
  }

  public boolean f()
  {
    return this.b;
  }

  protected void g()
  {
    this.e.post(this.f);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.a
 * JD-Core Version:    0.6.0
 */