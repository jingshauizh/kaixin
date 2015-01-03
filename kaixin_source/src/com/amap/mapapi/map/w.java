package com.amap.mapapi.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import com.amap.mapapi.core.t;
import java.util.Iterator;

public class w extends x
{
  public String a = "";
  public int b = 18;
  public int c = 3;
  public boolean d = true;
  public boolean e = true;
  public boolean f = false;
  public boolean g = false;
  public boolean h = false;
  public long i = 0L;
  public bd j = null;
  public int k = -1;
  public String l = "";
  h m = null;
  j n = null;
  t<av.a> o = null;

  protected void a()
  {
    this.n.a(null);
    this.m.c();
    this.o.clear();
  }

  protected void a(Canvas paramCanvas)
  {
    if (this.o == null)
      return;
    Iterator localIterator = this.o.iterator();
    label16: av.a locala;
    do
    {
      if (!localIterator.hasNext())
        break;
      locala = (av.a)localIterator.next();
      if (locala.g >= 0)
        break label90;
    }
    while (!this.e);
    label90: for (Bitmap localBitmap = av.c(); ; localBitmap = this.m.a(locala.g))
    {
      PointF localPointF = locala.f;
      if ((localBitmap == null) || (localPointF == null))
        break label16;
      paramCanvas.drawBitmap(localBitmap, localPointF.x, localPointF.y, null);
      break label16;
      break;
    }
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject)
      return true;
    if (!(paramObject instanceof w))
      return false;
    w localw = (w)paramObject;
    return this.a.equals(localw.a);
  }

  public int hashCode()
  {
    return this.k;
  }

  public String toString()
  {
    return this.a;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.w
 * JD-Core Version:    0.6.0
 */