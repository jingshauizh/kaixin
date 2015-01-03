package com.amap.mapapi.map;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;

class f
{
  protected Bitmap a = null;
  protected Canvas b = null;
  protected Bitmap.Config c;

  public f(Bitmap.Config paramConfig)
  {
    this.c = paramConfig;
  }

  public void a()
  {
    if (this.a != null)
      this.a.recycle();
    this.a = null;
    this.b = null;
  }

  public void a(int paramInt1, int paramInt2)
  {
    a();
    this.a = Bitmap.createBitmap(paramInt1, paramInt2, this.c);
    this.b = new Canvas(this.a);
  }

  public void a(Bitmap paramBitmap)
  {
    this.a = paramBitmap;
    this.b = new Canvas(this.a);
  }

  public void a(g paramg)
  {
    this.b.save(1);
    paramg.a(this.b);
    this.b.restore();
  }

  public Bitmap b()
  {
    return this.a;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.f
 * JD-Core Version:    0.6.0
 */