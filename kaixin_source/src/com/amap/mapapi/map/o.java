package com.amap.mapapi.map;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import com.amap.mapapi.core.GeoPoint;

class o extends am
{
  private Drawable d;
  private RouteMessageHandler e;
  private boolean f;
  private boolean g;
  private int h;
  private int i;
  private boolean j = false;

  public o(RouteOverlay paramRouteOverlay, int paramInt, GeoPoint paramGeoPoint, Drawable paramDrawable, RouteMessageHandler paramRouteMessageHandler, boolean paramBoolean)
  {
    super(paramRouteOverlay, paramInt, paramGeoPoint);
    this.d = paramDrawable;
    this.e = paramRouteMessageHandler;
    this.f = false;
    this.g = false;
    Rect localRect = this.d.getBounds();
    this.h = (int)(1.5D * localRect.width());
    this.i = (int)(1.5D * localRect.height());
    this.j = paramBoolean;
  }

  private boolean a(MapView paramMapView, int paramInt1, int paramInt2)
  {
    Point localPoint = a(paramMapView, this.b);
    Rect localRect = this.d.getBounds();
    if (this.j == true)
    {
      int k = localRect.width() / 2;
      int m = localRect.height();
      return (2 * Math.abs(paramInt1 - localPoint.x) < k) && (localPoint.y - paramInt2 > 0) && (localPoint.y - paramInt2 < m);
    }
    return localRect.contains(paramInt1 - localPoint.x, paramInt2 - localPoint.y);
  }

  private boolean b(MapView paramMapView, Point paramPoint)
  {
    Point localPoint = a(paramMapView, this.b);
    return (localPoint.x - paramPoint.x) * (localPoint.x - paramPoint.x) + (localPoint.y - paramPoint.y) * (localPoint.y - paramPoint.y) > this.h * this.i;
  }

  public void a(Canvas paramCanvas, MapView paramMapView, boolean paramBoolean)
  {
    if ((this.d == null) || (paramBoolean))
      return;
    Point localPoint = a(paramMapView, this.b);
    Overlay.a(paramCanvas, this.d, localPoint.x, localPoint.y);
  }

  public void a(GeoPoint paramGeoPoint)
  {
    this.b = paramGeoPoint;
  }

  public boolean a(MotionEvent paramMotionEvent, MapView paramMapView)
  {
    Point localPoint = new Point((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY());
    if ((paramMotionEvent.getAction() == 0) && (a(paramMapView, localPoint.x, localPoint.y)))
      this.f = true;
    while (true)
    {
      return true;
      if (paramMotionEvent.getAction() != 2)
        break label147;
      if (this.g)
      {
        if (!b(paramMapView, localPoint))
          continue;
        this.e.onDrag(paramMapView, this.c, this.a, a(paramMapView, localPoint));
        return true;
      }
      if (!this.f)
        break;
      if (!b(paramMapView, localPoint))
        continue;
      this.g = true;
      this.e.onDragBegin(paramMapView, this.c, this.a, a(paramMapView, localPoint));
      return true;
    }
    return false;
    label147: if ((paramMotionEvent.getAction() == 1) && (this.f))
    {
      this.f = false;
      if (this.g)
      {
        this.g = false;
        this.e.onDragEnd(paramMapView, this.c, this.a, a(paramMapView, localPoint));
        return true;
      }
      this.e.onRouteEvent(paramMapView, this.c, this.a, 4);
      return true;
    }
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.o
 * JD-Core Version:    0.6.0
 */