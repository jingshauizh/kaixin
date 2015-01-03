package com.amap.mapapi.map;

import com.amap.mapapi.core.GeoPoint;

public abstract interface RouteMessageHandler
{
  public abstract void onDrag(MapView paramMapView, RouteOverlay paramRouteOverlay, int paramInt, GeoPoint paramGeoPoint);

  public abstract void onDragBegin(MapView paramMapView, RouteOverlay paramRouteOverlay, int paramInt, GeoPoint paramGeoPoint);

  public abstract void onDragEnd(MapView paramMapView, RouteOverlay paramRouteOverlay, int paramInt, GeoPoint paramGeoPoint);

  public abstract boolean onRouteEvent(MapView paramMapView, RouteOverlay paramRouteOverlay, int paramInt1, int paramInt2);
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.RouteMessageHandler
 * JD-Core Version:    0.6.0
 */