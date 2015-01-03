package com.amap.mapapi.map;

import android.graphics.drawable.Drawable;
import android.view.View;
import com.amap.mapapi.core.GeoPoint;
import com.amap.mapapi.route.Route;

public class aq extends t
  implements MapView.b
{
  private RouteOverlay i;
  private int j;

  public aq(MapView paramMapView, View paramView, GeoPoint paramGeoPoint, Drawable paramDrawable, MapView.LayoutParams paramLayoutParams, RouteOverlay paramRouteOverlay, int paramInt)
  {
    super(paramMapView, paramView, paramGeoPoint, paramDrawable, paramLayoutParams);
    this.i = paramRouteOverlay;
    this.j = paramInt;
  }

  public aq(MapView paramMapView, View paramView, GeoPoint paramGeoPoint, RouteOverlay paramRouteOverlay, int paramInt)
  {
    this(paramMapView, paramView, paramGeoPoint, null, null, paramRouteOverlay, paramInt);
  }

  public void a(int paramInt)
  {
    this.i.b.onRouteEvent(this.d, this.i, this.j, paramInt);
  }

  public void a(boolean paramBoolean)
  {
    boolean bool1 = true;
    super.b();
    this.d.mRouteCtrl.a(paramBoolean, this);
    MapView.e locale1 = this.d.mRouteCtrl;
    boolean bool2;
    boolean bool3;
    label71: boolean bool4;
    label107: MapView.e locale4;
    if (this.j < this.i.getRoute().getStepCount())
    {
      bool2 = bool1;
      locale1.b(bool2);
      MapView.e locale2 = this.d.mRouteCtrl;
      if (this.j == 0)
        break label153;
      bool3 = bool1;
      locale2.a(bool3);
      MapView.e locale3 = this.d.mRouteCtrl;
      if (this.d.getMaxZoomLevel() == this.d.getZoomLevel())
        break label159;
      bool4 = bool1;
      locale3.c(bool4);
      locale4 = this.d.mRouteCtrl;
      if (this.d.getMinZoomLevel() == this.d.getZoomLevel())
        break label165;
    }
    while (true)
    {
      locale4.d(bool1);
      return;
      bool2 = false;
      break;
      label153: bool3 = false;
      break label71;
      label159: bool4 = false;
      break label107;
      label165: bool1 = false;
    }
  }

  public void c()
  {
    super.c();
    this.d.mRouteCtrl.a(false, this);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.aq
 * JD-Core Version:    0.6.0
 */