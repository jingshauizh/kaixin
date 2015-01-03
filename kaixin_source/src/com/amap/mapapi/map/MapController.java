package com.amap.mapapi.map;

import android.graphics.Point;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import com.amap.mapapi.core.GeoPoint;
import com.amap.mapapi.core.c;
import com.amap.mapapi.core.e;
import com.mapabc.minimap.map.vmap.VMapProjection;
import java.util.LinkedList;
import java.util.List;

public final class MapController
  implements View.OnKeyListener
{
  private int a = 0;
  private int b = 0;
  private ai c;
  private boolean d;
  private b e;
  private a f;

  MapController(ai paramai)
  {
    this.c = paramai;
    this.d = false;
    this.e = new b();
    this.f = new a();
  }

  private int a(float paramFloat)
  {
    int i = 1;
    int j = 0;
    int k = i;
    while (true)
    {
      if (k > paramFloat)
        return j;
      int m = k * 2;
      int n = i + 1;
      k = m;
      j = i;
      i = n;
    }
  }

  private boolean a(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    return a(paramInt1, paramInt2, paramBoolean1, paramBoolean2, 1);
  }

  private boolean a(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, int paramInt3)
  {
    if (paramBoolean1);
    for (int i = paramInt3 + this.c.b.e(); ; i = this.c.b.e() - paramInt3)
    {
      int j = this.c.b.g().b(i);
      if (j == this.c.b.e())
        break;
      zoomAnimationAtLevel(paramInt1, paramInt2, j, paramBoolean1, paramBoolean2);
      return true;
    }
    return false;
  }

  public static float calculateDistance(GeoPoint paramGeoPoint1, GeoPoint paramGeoPoint2)
  {
    double d1 = e.a(paramGeoPoint1.a());
    double d2 = e.a(paramGeoPoint1.b());
    double d3 = e.a(paramGeoPoint2.a());
    double d4 = e.a(paramGeoPoint2.b());
    double d5 = d1 * 0.01745329251994329D;
    double d6 = d2 * 0.01745329251994329D;
    double d7 = d3 * 0.01745329251994329D;
    double d8 = d4 * 0.01745329251994329D;
    double d9 = Math.sin(d5);
    double d10 = Math.sin(d6);
    double d11 = Math.cos(d5);
    double d12 = Math.cos(d6);
    double d13 = Math.sin(d7);
    double d14 = Math.sin(d8);
    double d15 = Math.cos(d7);
    double d16 = Math.cos(d8);
    double[] arrayOfDouble1 = new double[3];
    double[] arrayOfDouble2 = new double[3];
    arrayOfDouble1[0] = (d11 * d12);
    arrayOfDouble1[1] = (d12 * d9);
    arrayOfDouble1[2] = d10;
    arrayOfDouble2[0] = (d16 * d15);
    arrayOfDouble2[1] = (d16 * d13);
    arrayOfDouble2[2] = d14;
    return (float)(12742001.579854401D * Math.asin(Math.sqrt((arrayOfDouble1[0] - arrayOfDouble2[0]) * (arrayOfDouble1[0] - arrayOfDouble2[0]) + (arrayOfDouble1[1] - arrayOfDouble2[1]) * (arrayOfDouble1[1] - arrayOfDouble2[1]) + (arrayOfDouble1[2] - arrayOfDouble2[2]) * (arrayOfDouble1[2] - arrayOfDouble2[2])) / 2.0D));
  }

  boolean a()
  {
    return b.a(this.e).f();
  }

  boolean a(int paramInt)
  {
    return a(this.c.b.c() / 2, this.c.b.d() / 2, true, false, paramInt);
  }

  public void animateTo(GeoPoint paramGeoPoint)
  {
    this.f.a(paramGeoPoint, null, null);
  }

  public void animateTo(GeoPoint paramGeoPoint, Message paramMessage)
  {
    this.f.a(paramGeoPoint, paramMessage, null);
  }

  public void animateTo(GeoPoint paramGeoPoint, Runnable paramRunnable)
  {
    this.f.a(paramGeoPoint, null, paramRunnable);
  }

  boolean b(int paramInt)
  {
    return a(this.c.b.c() / 2, this.c.b.d() / 2, false, false, paramInt);
  }

  public int getReqLatSpan()
  {
    return this.a;
  }

  public int getReqLngSpan()
  {
    return this.b;
  }

  public boolean onKey(View paramView, int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getAction() != 0)
      return false;
    int i = 1;
    switch (paramInt)
    {
    default:
      i = 0;
    case 21:
    case 22:
    case 19:
    case 20:
    }
    while (true)
    {
      return i;
      scrollBy(-10, 0);
      continue;
      scrollBy(10, 0);
      continue;
      scrollBy(0, -10);
      continue;
      scrollBy(0, 10);
    }
  }

  public void scrollBy(int paramInt1, int paramInt2)
  {
    if (this.d)
      this.d = false;
    do
      return;
    while ((paramInt1 == 0) && (paramInt2 == 0));
    if (c.n == true)
    {
      PointF localPointF1 = new PointF(0.0F, 0.0F);
      PointF localPointF2 = new PointF(paramInt1, paramInt2);
      this.c.g.a(localPointF1, localPointF2, this.c.b.e());
    }
    this.c.b.a(false, false);
  }

  public void setCenter(GeoPoint paramGeoPoint)
  {
    this.c.b.a(paramGeoPoint);
    if (this.c.b.g().VMapMode)
    {
      be localbe = VMapProjection.LatLongToPixels(paramGeoPoint.getLatitudeE6() / 1000000.0D, paramGeoPoint.getLongitudeE6() / 1000000.0D, 20);
      this.c.b.g().centerX = localbe.a;
      this.c.b.g().centerY = localbe.b;
    }
  }

  public void setFitView(List<GeoPoint> paramList)
  {
    if ((paramList == null) || (paramList.size() < 2))
      return;
    int i = 2147483647;
    int j = -2147483648;
    int k = 2147483647;
    int m = -2147483648;
    for (int n = 0; n < paramList.size(); n++)
    {
      GeoPoint localGeoPoint = (GeoPoint)paramList.get(n);
      int i1 = localGeoPoint.getLongitudeE6();
      int i2 = localGeoPoint.getLatitudeE6();
      if (i1 < k)
        k = i1;
      if (i2 < i)
        i = i2;
      if (i1 > j)
        j = i1;
      if (i2 <= m)
        continue;
      m = i2;
    }
    setCenter(new GeoPoint((i + m) / 2, (k + j) / 2));
    zoomToSpan(m - i, j - k);
  }

  public void setReqLatSpan(int paramInt)
  {
    this.a = paramInt;
  }

  public void setReqLngSpan(int paramInt)
  {
    this.b = paramInt;
  }

  public int setZoom(int paramInt)
  {
    MapView localMapView = this.c.b.g();
    int i = localMapView.b(paramInt);
    int j;
    if (!this.c.b.g().VMapMode)
    {
      j = this.c.g.g;
      this.c.b.a(i);
    }
    while (true)
    {
      if (localMapView.d != null)
      {
        ZoomButtonsController.OnZoomListener localOnZoomListener = localMapView.getZoomButtonsController().getOnZoomListener();
        if ((j < i) && (localOnZoomListener != null))
          localOnZoomListener.onZoom(true);
        if ((j > i) && (localOnZoomListener != null))
          localOnZoomListener.onZoom(false);
      }
      return i;
      j = localMapView.mapLevel;
      this.c.b.a(i);
    }
  }

  public void stopAnimation(boolean paramBoolean)
  {
    this.e.a();
    this.f.a();
  }

  public void stopPanning()
  {
    this.d = true;
  }

  public void zoomAnimationAtLevel(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.e.a(paramInt1, paramInt2, paramInt3, paramBoolean1, paramBoolean2);
  }

  public boolean zoomIn()
  {
    return a(1);
  }

  public boolean zoomInFixing(int paramInt1, int paramInt2)
  {
    return a(paramInt1, paramInt2, true, true);
  }

  public boolean zoomOut()
  {
    return b(1);
  }

  public boolean zoomOutFixing(int paramInt1, int paramInt2)
  {
    return a(paramInt1, paramInt2, false, true);
  }

  public void zoomToSpan(int paramInt1, int paramInt2)
  {
    if ((paramInt1 <= 0) || (paramInt2 <= 0))
      return;
    int i = this.c.b.b();
    int j = this.c.b.a();
    int k = this.c.b.e();
    int m = this.c.a.b();
    int n = this.c.a.a();
    if ((m == 0) && (n == 0))
    {
      this.a = paramInt1;
      this.b = paramInt2;
      return;
    }
    float f1 = Math.max(paramInt1 / n, paramInt2 / m);
    int i1;
    if (f1 > 1.0F)
    {
      i1 = k - a(f1);
      if (i1 > i)
        break label179;
    }
    while (true)
    {
      setZoom(i);
      return;
      if (f1 < 0.5D)
      {
        i = -1 + (k + a(1.0F / f1));
        if (i < j)
          continue;
        i = j;
        continue;
      }
      i = k;
      continue;
      label179: i = i1;
    }
  }

  class a
    implements bc
  {
    private bb b = null;
    private Message c = null;
    private Runnable d = null;

    a()
    {
    }

    private bb b(GeoPoint paramGeoPoint)
    {
      return new bb(500, 10, MapController.a(MapController.this).g.j, paramGeoPoint, MapController.a(MapController.this).b.e(), this);
    }

    private void c()
    {
      this.b = null;
      this.c = null;
      this.d = null;
    }

    public void a()
    {
      if (this.b != null)
        this.b.d();
    }

    public void a(GeoPoint paramGeoPoint)
    {
      if (paramGeoPoint == null)
        return;
      if ((paramGeoPoint.b() == -9223372036854775808L) || (paramGeoPoint.a() == -9223372036854775808L))
      {
        GeoPoint localGeoPoint = MapController.a(MapController.this).g.b(paramGeoPoint);
        MapController.this.setCenter(localGeoPoint);
        return;
      }
      MapController.this.setCenter(paramGeoPoint);
    }

    public void a(GeoPoint paramGeoPoint, Message paramMessage, Runnable paramRunnable)
    {
      MapController.a(MapController.this).c.a = true;
      MapController.a(MapController.this).g.k = paramGeoPoint.e();
      a();
      this.b = b(paramGeoPoint);
      this.c = paramMessage;
      this.d = paramRunnable;
      this.b.c();
    }

    public void b()
    {
      if (this.c != null)
        this.c.getTarget().sendMessage(this.c);
      if (this.d != null)
        this.d.run();
      c();
      if (MapController.a(MapController.this).c != null)
        MapController.a(MapController.this).c.a = false;
    }
  }

  class b
    implements Animation.AnimationListener
  {
    private LinkedList<Animation> b = new LinkedList();
    private boolean c = false;
    private bj d = null;

    b()
    {
    }

    private void a(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
    {
      if (this.d == null)
        this.d = new bj(MapController.a(MapController.this).b.g(), this);
      this.d.j = paramBoolean;
      this.d.i = paramInt1;
      this.d.a(paramInt1, false, paramInt2, paramInt3);
      this.c = true;
    }

    private void b(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
    {
      if (this.d == null)
        this.d = new bj(MapController.a(MapController.this).b.g(), this);
      this.d.i = paramInt1;
      this.d.j = paramBoolean;
      if (this.d.j == true)
      {
        Point localPoint = new Point(paramInt2, paramInt3);
        GeoPoint localGeoPoint = MapController.a(MapController.this).b.g().getProjection().fromPixels(paramInt2, paramInt3);
        MapController.a(MapController.this).g.j = MapController.a(MapController.this).g.a(localGeoPoint);
        MapController.a(MapController.this).g.a(localPoint);
      }
      this.d.a(paramInt1, true, paramInt2, paramInt3);
      this.c = true;
    }

    public void a()
    {
      this.b.clear();
    }

    public void a(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2)
    {
      if (!paramBoolean1)
      {
        a(paramInt3, paramInt1, paramInt2, paramBoolean2);
        return;
      }
      b(paramInt3, paramInt1, paramInt2, paramBoolean2);
    }

    public void onAnimationEnd(Animation paramAnimation)
    {
      MapView localMapView = MapController.a(MapController.this).b.g();
      if (this.b.size() == 0)
      {
        this.c = false;
        localMapView.getZoomMgr().a(true);
        MapController.a(MapController.this).d.d();
        return;
      }
      Animation localAnimation = (Animation)this.b.remove();
      localMapView.b().startAnimation(localAnimation);
    }

    public void onAnimationRepeat(Animation paramAnimation)
    {
    }

    public void onAnimationStart(Animation paramAnimation)
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.MapController
 * JD-Core Version:    0.6.0
 */