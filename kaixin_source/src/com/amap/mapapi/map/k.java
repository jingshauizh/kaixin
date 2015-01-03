package com.amap.mapapi.map;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

class k
  implements g
{
  f a = new f(Bitmap.Config.ARGB_4444);
  private Drawable b;
  private Drawable c;
  private int d;
  private int e;
  private float f = 0.0F;

  public k(Bitmap paramBitmap1, Bitmap paramBitmap2)
  {
    this.d = paramBitmap1.getWidth();
    this.e = paramBitmap1.getHeight();
    this.b = a(paramBitmap1);
    this.c = a(paramBitmap2);
  }

  private Drawable a(Bitmap paramBitmap)
  {
    BitmapDrawable localBitmapDrawable = new BitmapDrawable(paramBitmap);
    localBitmapDrawable.setBounds(0, 0, this.d, this.e);
    return localBitmapDrawable;
  }

  public Bitmap a(float paramFloat)
  {
    this.f = paramFloat;
    this.a.a(this.d, this.e);
    this.a.a(this);
    return this.a.b();
  }

  public void a(Canvas paramCanvas)
  {
    this.b.draw(paramCanvas);
    paramCanvas.rotate(-this.f, this.d / 2, this.e / 2);
    this.c.draw(paramCanvas);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.k
 * JD-Core Version:    0.6.0
 */