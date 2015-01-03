package com.amap.mapapi.map;

import android.graphics.drawable.Drawable;
import android.view.View;
import com.amap.mapapi.core.GeoPoint;

class bg extends am
{
  private MapView.LayoutParams d;
  private View e;

  public bg(RouteOverlay paramRouteOverlay, int paramInt, GeoPoint paramGeoPoint, View paramView, Drawable paramDrawable, MapView.LayoutParams paramLayoutParams)
  {
    super(paramRouteOverlay, paramInt, paramGeoPoint);
    this.e = paramView;
    this.e.setBackgroundDrawable(paramDrawable);
    this.d = paramLayoutParams;
  }

  public void a(MapView paramMapView)
  {
    paramMapView.addView(this.e, this.d);
  }

  public void b(MapView paramMapView)
  {
    paramMapView.removeView(this.e);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.bg
 * JD-Core Version:    0.6.0
 */