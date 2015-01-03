package com.amap.mapapi.map;

import android.graphics.Bitmap;
import android.graphics.Point;
import com.amap.mapapi.core.c;
import com.amap.mapapi.core.c.a;
import com.amap.mapapi.core.n;

class l extends s
{
  private k c = new k(c.g.a(c.a.e.ordinal()), c.g.a(c.a.f.ordinal()));
  private float d = 0.0F;

  public l(ai paramai)
  {
    super(paramai, null);
    d();
  }

  private boolean b(float paramFloat)
  {
    return Math.abs(paramFloat - this.d) > 3.0F;
  }

  private void d()
  {
    this.b = this.c.a(this.d);
  }

  protected Point a()
  {
    return new Point(15, 45);
  }

  public boolean a(float paramFloat)
  {
    boolean bool = b(paramFloat);
    if (bool)
    {
      this.d = paramFloat;
      d();
    }
    return bool;
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
 * Qualified Name:     com.amap.mapapi.map.l
 * JD-Core Version:    0.6.0
 */