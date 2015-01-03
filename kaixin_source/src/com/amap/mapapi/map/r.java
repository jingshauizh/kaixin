package com.amap.mapapi.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

class r extends a
{
  private Bitmap[] e;
  private Rect f;
  private int g;
  private ai h;

  public r(int paramInt1, int paramInt2, ai paramai, Bitmap[] paramArrayOfBitmap)
  {
    super(paramInt1, paramInt2);
    this.e = paramArrayOfBitmap;
    this.f = new Rect(0, 0, this.e[0].getWidth(), this.e[0].getHeight());
    this.g = 0;
    this.h = paramai;
  }

  protected void a()
  {
    this.g = (1 + this.g);
    if (this.g >= this.e.length)
      this.g = 0;
    this.h.d.b(this.f.left, this.f.top, this.f.right, this.f.bottom);
  }

  public void a(Canvas paramCanvas, int paramInt1, int paramInt2)
  {
    int i = this.f.width() / 2;
    int j = this.f.height() / 2;
    this.f.set(paramInt1 - i, paramInt2 - j, i + paramInt1, j + paramInt2);
    this.g = (1 + this.g);
    if (this.g >= this.e.length)
      this.g = 0;
    paramCanvas.drawBitmap(this.e[this.g], this.f.left, this.f.top, null);
  }

  protected void b()
  {
  }

  public int h()
  {
    return this.e[0].getWidth();
  }

  public int i()
  {
    return this.e[0].getHeight();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.r
 * JD-Core Version:    0.6.0
 */