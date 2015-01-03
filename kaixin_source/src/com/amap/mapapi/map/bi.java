package com.amap.mapapi.map;

import android.graphics.Bitmap;
import android.graphics.Point;

class bi extends s
{
  public bi(ai paramai, Bitmap paramBitmap)
  {
    super(paramai, paramBitmap);
  }

  protected Point a()
  {
    return new Point(0, -10 + (this.a.b.d() - this.b.getHeight()));
  }

  public void a(Bitmap paramBitmap)
  {
    this.b = paramBitmap;
  }

  public void b()
  {
    if ((this.b != null) && (!this.b.isRecycled()))
    {
      this.b.recycle();
      this.b = null;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.bi
 * JD-Core Version:    0.6.0
 */