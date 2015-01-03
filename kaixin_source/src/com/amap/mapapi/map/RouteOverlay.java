package com.amap.mapapi.map;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import com.amap.mapapi.core.AMapException;
import com.amap.mapapi.core.GeoPoint;
import com.amap.mapapi.core.r;
import com.amap.mapapi.route.Route;
import com.amap.mapapi.route.Route.FromAndTo;
import com.amap.mapapi.route.Route.d;
import com.amap.mapapi.route.Segment;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RouteOverlay extends Overlay
{
  public static final int OnDetail = 1;
  public static final int OnIconClick = 4;
  public static final int OnNext = 3;
  public static final int OnOverview = 0;
  public static final int OnPrev = 2;
  List<ar> a = null;
  a b = new a();
  private aq c = null;
  private boolean d = true;
  private boolean e = true;
  private List<RouteMessageHandler> f = new ArrayList();
  private MapView g = null;
  private int h = 0;
  private boolean i = false;
  private boolean j = true;
  protected MapActivity mContext;
  protected Route mRoute = null;

  public RouteOverlay(MapActivity paramMapActivity, Route paramRoute)
  {
    r.a(paramMapActivity);
    this.mContext = paramMapActivity;
    this.mRoute = paramRoute;
  }

  static Point a(MapView paramMapView, GeoPoint paramGeoPoint)
  {
    return paramMapView.getProjection().toPixels(paramGeoPoint, null);
  }

  private o a(int paramInt)
  {
    if (paramInt == 0)
      return (o)this.a.get(0);
    return (o)this.a.get(-1 + this.a.size());
  }

  private void a(MapView paramMapView)
  {
    if (this.i == true)
      return;
    this.a = new ArrayList();
    this.a.add(new o(this, 0, this.mRoute.mHelper.g(0), ItemizedOverlay.boundCenterBottom(r.a), this.b, true));
    int k = this.mRoute.getStepCount();
    int m = 0;
    if (m < k)
    {
      if ((m > 0) && (m < k - 1))
      {
        GeoPoint[] arrayOfGeoPoint1 = this.mRoute.getStep(m).getShapes();
        GeoPoint localGeoPoint2 = this.mRoute.getStep(m + 1).getShapes()[0];
        GeoPoint[] arrayOfGeoPoint2 = new GeoPoint[1 + arrayOfGeoPoint1.length];
        System.arraycopy(arrayOfGeoPoint1, 0, arrayOfGeoPoint2, 0, arrayOfGeoPoint1.length);
        arrayOfGeoPoint2[(-1 + arrayOfGeoPoint2.length)] = localGeoPoint2;
        this.a.add(new y(this, arrayOfGeoPoint2, this.mRoute.mHelper.a(m)));
        label187: View localView = this.mRoute.mHelper.a(paramMapView, this.mContext, this.b, this, m);
        if (localView == null)
          break label341;
        GeoPoint localGeoPoint1 = this.mRoute.mHelper.g(m);
        MapView.LayoutParams localLayoutParams = new MapView.LayoutParams(-2, -2, localGeoPoint1, 0, 0, 85);
        List localList = this.a;
        Drawable localDrawable = r.b(this.mContext);
        localList.add(new bg(this, m, localGeoPoint1, localView, localDrawable, localLayoutParams));
      }
      while (true)
      {
        m++;
        break;
        this.a.add(new y(this, this.mRoute.getStep(m).getShapes(), this.mRoute.mHelper.a(m)));
        break label187;
        label341: if (!Route.isDrive(this.mRoute.getMode()))
          continue;
        this.a.add(new o(this, m + 1, this.mRoute.mHelper.g(m + 1), ItemizedOverlay.boundCenter(r.j), this.b, false));
      }
    }
    this.a.add(new o(this, k, this.mRoute.mHelper.g(k), ItemizedOverlay.boundCenterBottom(r.b), this.b, true));
    this.i = true;
  }

  private void a(MapView paramMapView, int paramInt)
  {
    paramMapView.getController().zoomOut();
  }

  static boolean a(MapView paramMapView, Point paramPoint, int paramInt)
  {
    if (paramPoint == null);
    int k;
    int m;
    do
    {
      return false;
      k = paramMapView.getWidth() - paramInt;
      m = paramMapView.getHeight() - paramInt;
    }
    while ((paramPoint.x <= paramInt) || (paramPoint.x >= k) || (paramPoint.y <= paramInt) || (paramPoint.y >= m));
    return true;
  }

  private void b(MapView paramMapView, int paramInt)
  {
    paramMapView.getController().zoomIn();
  }

  private void b(MapView paramMapView, GeoPoint paramGeoPoint)
  {
    Point localPoint = a(paramMapView, paramGeoPoint);
    if (!a(paramMapView, localPoint, 30))
    {
      localPoint.x -= paramMapView.getWidth() / 4;
      paramMapView.getController().animateTo(paramMapView.getProjection().fromPixels(localPoint.x, localPoint.y));
    }
  }

  public void addToMap(MapView paramMapView)
  {
    this.g = paramMapView;
    a(this.g);
    if (!this.g.getOverlays().contains(this))
      this.g.getOverlays().add(this);
    Iterator localIterator = this.a.iterator();
    while (localIterator.hasNext())
      ((ar)localIterator.next()).a(this.g);
  }

  public void closePopupWindow()
  {
    if (this.c != null)
      this.c.c();
    this.c = null;
  }

  public void draw(Canvas paramCanvas, MapView paramMapView, boolean paramBoolean)
  {
    Iterator localIterator1 = this.a.iterator();
    while (localIterator1.hasNext())
    {
      ar localar2 = (ar)localIterator1.next();
      if (!(localar2 instanceof y))
        continue;
      localar2.a(paramCanvas, paramMapView, paramBoolean);
    }
    Iterator localIterator2 = this.a.iterator();
    while (localIterator2.hasNext())
    {
      ar localar1 = (ar)localIterator2.next();
      if ((localar1 instanceof y))
        continue;
      localar1.a(paramCanvas, paramMapView, paramBoolean);
    }
  }

  public void enableDrag(boolean paramBoolean)
  {
    this.e = paramBoolean;
  }

  public void enablePopup(boolean paramBoolean)
  {
    this.d = paramBoolean;
    if (!this.d)
      closePopupWindow();
  }

  public GeoPoint getEndPos()
  {
    return a(this.mRoute.getStepCount()).b;
  }

  protected View getInfoView(MapView paramMapView, int paramInt)
  {
    return this.mRoute.mHelper.b(paramMapView, this.mContext, this.b, this, paramInt);
  }

  public Route getRoute()
  {
    return this.mRoute;
  }

  public GeoPoint getStartPos()
  {
    return a(0).b;
  }

  public boolean isStartEndMoved()
  {
    return (!getStartPos().equals(this.mRoute.getStartPos())) || (!getEndPos().equals(this.mRoute.getTargetPos()));
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent, MapView paramMapView)
  {
    boolean bool = false;
    Iterator localIterator = this.a.iterator();
    do
    {
      if (!localIterator.hasNext())
        break;
      bool = ((ar)localIterator.next()).a(paramMotionEvent, paramMapView);
    }
    while (!bool);
    return bool;
  }

  public boolean onTrackballEvent(MotionEvent paramMotionEvent, MapView paramMapView)
  {
    return onTouchEvent(paramMotionEvent, paramMapView);
  }

  public void registerRouteMessage(RouteMessageHandler paramRouteMessageHandler)
  {
    this.f.add(paramRouteMessageHandler);
  }

  public boolean removeFromMap(MapView paramMapView)
  {
    boolean bool = paramMapView.getOverlays().remove(this);
    if (bool)
    {
      closePopupWindow();
      this.g = null;
      Iterator localIterator = this.a.iterator();
      while (localIterator.hasNext())
        ((ar)localIterator.next()).b(paramMapView);
    }
    return bool;
  }

  public void renewOverlay(MapView paramMapView)
    throws AMapException
  {
    removeFromMap(paramMapView);
    this.i = false;
    if (isStartEndMoved())
      this.mRoute = ((Route)Route.calculateRoute(this.mContext, new Route.FromAndTo(getStartPos(), getEndPos(), 0), this.mRoute.getMode()).get(0));
    addToMap(paramMapView);
  }

  public void restoreOverlay(MapView paramMapView)
  {
    removeFromMap(paramMapView);
    a(0).b = this.mRoute.getStartPos().e();
    a(this.mRoute.getStepCount()).b = this.mRoute.getTargetPos().e();
    addToMap(paramMapView);
  }

  public void setBusLinePaint(Paint paramPaint)
  {
    if (paramPaint != null)
    {
      if (!Paint.Style.STROKE.equals(paramPaint.getStyle()))
        paramPaint.setStyle(Paint.Style.STROKE);
      r.l = paramPaint;
    }
  }

  public void setCarLinePaint(Paint paramPaint)
  {
    if (paramPaint != null)
    {
      if (!Paint.Style.STROKE.equals(paramPaint.getStyle()))
        paramPaint.setStyle(Paint.Style.STROKE);
      r.m = paramPaint;
    }
  }

  public void setFootLinePaint(Paint paramPaint)
  {
    if (paramPaint != null)
    {
      if (!Paint.Style.STROKE.equals(paramPaint.getStyle()))
        paramPaint.setStyle(Paint.Style.STROKE);
      r.k = paramPaint;
    }
  }

  public void showNextPopUpWindow()
  {
    if ((this.mRoute != null) && (this.h < this.mRoute.getStepCount()))
      takeDefaultAction(this.g, this.h, 3);
  }

  public boolean showPopupWindow(int paramInt)
  {
    if ((!this.d) || (isStartEndMoved()));
    View localView;
    do
    {
      return false;
      if (this.g == null)
        throw new UnsupportedOperationException("routeoverlay must be added to map frist!");
      localView = getInfoView(this.g, paramInt);
    }
    while (localView == null);
    GeoPoint localGeoPoint = this.mRoute.mHelper.g(paramInt);
    if (this.g.mRouteCtrl.a)
      b(this.g, localGeoPoint);
    this.c = new aq(this.g, localView, localGeoPoint, this, paramInt);
    this.c.a(this.j);
    return true;
  }

  public void showPrevPopUpWindow()
  {
    if (this.h > 0)
      takeDefaultAction(this.g, this.h, 2);
  }

  public void showRouteButton(boolean paramBoolean)
  {
    this.j = paramBoolean;
  }

  protected void takeDefaultAction(MapView paramMapView, int paramInt1, int paramInt2)
  {
    switch (paramInt2)
    {
    default:
    case 3:
    case 2:
    case 0:
    case 1:
    }
    while (true)
    {
      showPopupWindow(paramInt1);
      this.h = paramInt1;
      return;
      paramInt1 = this.mRoute.mHelper.d(paramInt1);
      continue;
      paramInt1 = this.mRoute.mHelper.e(paramInt1);
      continue;
      closePopupWindow();
      a(paramMapView, paramInt1);
      continue;
      closePopupWindow();
      b(paramMapView, paramInt1);
    }
  }

  public void unregisterRouteMessage(RouteMessageHandler paramRouteMessageHandler)
  {
    this.f.remove(paramRouteMessageHandler);
  }

  class a
    implements RouteMessageHandler
  {
    a()
    {
    }

    private boolean a(MapView paramMapView, RouteOverlay paramRouteOverlay, int paramInt, GeoPoint paramGeoPoint)
    {
      if (!RouteOverlay.b(RouteOverlay.this))
        return false;
      RouteOverlay.this.closePopupWindow();
      RouteOverlay.a(RouteOverlay.this, paramInt).a(paramGeoPoint);
      paramMapView.invalidate();
      return true;
    }

    public void onDrag(MapView paramMapView, RouteOverlay paramRouteOverlay, int paramInt, GeoPoint paramGeoPoint)
    {
      if (a(paramMapView, paramRouteOverlay, paramInt, paramGeoPoint))
      {
        Iterator localIterator = RouteOverlay.a(RouteOverlay.this).iterator();
        while (localIterator.hasNext())
          ((RouteMessageHandler)localIterator.next()).onDrag(paramMapView, paramRouteOverlay, paramInt, paramGeoPoint);
      }
    }

    public void onDragBegin(MapView paramMapView, RouteOverlay paramRouteOverlay, int paramInt, GeoPoint paramGeoPoint)
    {
      if (a(paramMapView, paramRouteOverlay, paramInt, paramGeoPoint))
      {
        Iterator localIterator = RouteOverlay.a(RouteOverlay.this).iterator();
        while (localIterator.hasNext())
          ((RouteMessageHandler)localIterator.next()).onDragBegin(paramMapView, paramRouteOverlay, paramInt, paramGeoPoint);
      }
    }

    public void onDragEnd(MapView paramMapView, RouteOverlay paramRouteOverlay, int paramInt, GeoPoint paramGeoPoint)
    {
      if (a(paramMapView, paramRouteOverlay, paramInt, paramGeoPoint))
      {
        Iterator localIterator = RouteOverlay.a(RouteOverlay.this).iterator();
        while (localIterator.hasNext())
          ((RouteMessageHandler)localIterator.next()).onDragEnd(paramMapView, paramRouteOverlay, paramInt, paramGeoPoint);
      }
    }

    public boolean onRouteEvent(MapView paramMapView, RouteOverlay paramRouteOverlay, int paramInt1, int paramInt2)
    {
      boolean bool = false;
      Iterator localIterator = RouteOverlay.a(RouteOverlay.this).iterator();
      do
      {
        if (!localIterator.hasNext())
          break;
        bool = ((RouteMessageHandler)localIterator.next()).onRouteEvent(paramMapView, paramRouteOverlay, paramInt1, paramInt2);
      }
      while (!bool);
      if (!bool)
        RouteOverlay.this.takeDefaultAction(paramMapView, paramInt1, paramInt2);
      return bool;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.RouteOverlay
 * JD-Core Version:    0.6.0
 */