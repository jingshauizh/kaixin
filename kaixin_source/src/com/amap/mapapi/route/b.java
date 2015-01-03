package com.amap.mapapi.route;

import android.view.View;
import android.view.View.OnClickListener;
import com.amap.mapapi.map.MapView;
import com.amap.mapapi.map.RouteMessageHandler;
import com.amap.mapapi.map.RouteOverlay;

class b
{
  private RouteMessageHandler a;
  private int b;
  private int c;
  private MapView d;
  private RouteOverlay e;

  public b(MapView paramMapView, RouteMessageHandler paramRouteMessageHandler, RouteOverlay paramRouteOverlay, int paramInt1, int paramInt2)
  {
    this.d = paramMapView;
    this.a = paramRouteMessageHandler;
    this.b = paramInt2;
    this.c = paramInt1;
    this.e = paramRouteOverlay;
  }

  public void a(View paramView)
  {
    paramView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        b.e(b.this).onRouteEvent(b.a(b.this), b.b(b.this), b.c(b.this), b.d(b.this));
      }
    });
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.route.b
 * JD-Core Version:    0.6.0
 */