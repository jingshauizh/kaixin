package com.amap.mapapi.map;

import android.graphics.Point;
import com.amap.mapapi.core.GeoPoint;

public abstract interface Projection
{
  public abstract GeoPoint fromPixels(int paramInt1, int paramInt2);

  public abstract float metersToEquatorPixels(float paramFloat);

  public abstract Point toPixels(GeoPoint paramGeoPoint, Point paramPoint);
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.Projection
 * JD-Core Version:    0.6.0
 */