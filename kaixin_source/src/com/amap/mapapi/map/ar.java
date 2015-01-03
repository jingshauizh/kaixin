package com.amap.mapapi.map;

import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import com.amap.mapapi.core.GeoPoint;

class ar
{
  protected RouteOverlay c;

  public ar(RouteOverlay paramRouteOverlay)
  {
    this.c = paramRouteOverlay;
  }

  protected Point a(MapView paramMapView, GeoPoint paramGeoPoint)
  {
    return paramMapView.getProjection().toPixels(paramGeoPoint, null);
  }

  protected GeoPoint a(MapView paramMapView, Point paramPoint)
  {
    return paramMapView.getProjection().fromPixels(paramPoint.x, paramPoint.y);
  }

  public void a(Canvas paramCanvas, MapView paramMapView, boolean paramBoolean)
  {
  }

  public void a(MapView paramMapView)
  {
  }

  public boolean a(MotionEvent paramMotionEvent, MapView paramMapView)
  {
    return false;
  }

  public void b(MapView paramMapView)
  {
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.ar
 * JD-Core Version:    0.6.0
 */