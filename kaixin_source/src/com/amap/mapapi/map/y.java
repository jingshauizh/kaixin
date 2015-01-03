package com.amap.mapapi.map;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import com.amap.mapapi.core.GeoPoint;
import java.util.ArrayList;

class y extends ar
{
  private Paint a;
  private GeoPoint[] b;
  private Path d;

  y(RouteOverlay paramRouteOverlay, GeoPoint[] paramArrayOfGeoPoint, Paint paramPaint)
  {
    super(paramRouteOverlay);
    this.a = paramPaint;
    this.b = paramArrayOfGeoPoint;
  }

  private int a(MapView paramMapView, ArrayList<Point> paramArrayList)
  {
    Object localObject = RouteOverlay.a(paramMapView, this.b[0]);
    int i = 0;
    if (i < -1 + this.b.length)
    {
      i++;
      Point localPoint = RouteOverlay.a(paramMapView, this.b[i]);
      if (paramArrayList.size() == 0)
      {
        paramArrayList.add(localObject);
        paramArrayList.add(localPoint);
      }
      while (true)
      {
        localObject = localPoint;
        break;
        if (a((Point)localObject, localPoint))
        {
          paramArrayList.set(-1 + paramArrayList.size(), localPoint);
          continue;
        }
        paramArrayList.add(localPoint);
      }
    }
    if ((paramArrayList.size() > 2) && (a((Point)paramArrayList.get(0), (Point)paramArrayList.get(1))))
      paramArrayList.remove(1);
    return i;
  }

  private void a(Canvas paramCanvas, MapView paramMapView, ArrayList<Point> paramArrayList)
  {
    if (this.d == null)
      this.d = new Path();
    int i = 1;
    int j = paramArrayList.size();
    int k = 0;
    if (k < j)
    {
      Point localPoint = (Point)paramArrayList.get(k);
      if (i != 0)
        this.d.moveTo(localPoint.x, localPoint.y);
      for (int m = 0; ; m = i)
      {
        k++;
        i = m;
        break;
        this.d.lineTo(localPoint.x, localPoint.y);
      }
    }
    paramCanvas.drawPath(this.d, this.a);
    this.d.reset();
  }

  private boolean a(Point paramPoint1, Point paramPoint2)
  {
    return (Math.abs(paramPoint1.x - paramPoint2.x) <= 2) && (Math.abs(paramPoint1.y - paramPoint2.y) <= 2);
  }

  public void a(Canvas paramCanvas, MapView paramMapView, boolean paramBoolean)
  {
    if (paramBoolean);
    ArrayList localArrayList;
    do
    {
      return;
      localArrayList = new ArrayList();
      a(paramMapView, localArrayList);
    }
    while (localArrayList.size() <= 0);
    a(paramCanvas, paramMapView, localArrayList);
    localArrayList.clear();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.y
 * JD-Core Version:    0.6.0
 */