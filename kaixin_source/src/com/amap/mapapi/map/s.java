package com.amap.mapapi.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import com.amap.mapapi.core.c;
import com.amap.mapapi.core.c.a;
import com.amap.mapapi.core.n;

abstract class s extends p
{
  protected ai a;
  protected Bitmap b;

  public s(ai paramai, Bitmap paramBitmap)
  {
    this.a = paramai;
    this.b = paramBitmap;
  }

  protected abstract Point a();

  public Rect c()
  {
    Point localPoint = a();
    if (this.b == null)
      this.b = c.g.a(c.a.b.ordinal());
    return new Rect(localPoint.x, localPoint.y, localPoint.x + this.b.getWidth(), localPoint.y + this.b.getHeight());
  }

  public boolean draw(Canvas paramCanvas, MapView paramMapView, boolean paramBoolean, long paramLong)
  {
    if ((this.b != null) && (this.b.isRecycled() == true))
    {
      if (paramMapView.isSatellite())
        this.b = c.g.a(c.a.c.ordinal());
    }
    else if (this.b == null)
      if (!paramMapView.isSatellite())
        break label117;
    label117: for (this.b = c.g.a(c.a.c.ordinal()); ; this.b = c.g.a(c.a.b.ordinal()))
    {
      paramCanvas.drawBitmap(this.b, c().left, c().top, null);
      return true;
      this.b = c.g.a(c.a.b.ordinal());
      break;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.s
 * JD-Core Version:    0.6.0
 */