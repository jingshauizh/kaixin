package com.amap.mapapi.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import com.amap.mapapi.core.GeoPoint;
import com.amap.mapapi.core.c.a;
import com.amap.mapapi.core.e;
import com.amap.mapapi.core.n;
import com.amap.mapapi.location.LocationManagerProxy;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MyLocationOverlay extends Overlay
  implements SensorEventListener, SensorListener, LocationListener, Overlay.Snappable
{
  private ai a;
  private m b;
  private boolean c = false;
  private boolean d = false;
  private float e = (0.0F / 0.0F);
  private l f;
  private r g;
  private final LinkedList<Runnable> h = new LinkedList();
  private LocationManagerProxy i;
  private com.amap.mapapi.location.c j;
  private Criteria k;
  private Location l;
  private Context m;
  private String n;

  public MyLocationOverlay(Context paramContext, MapView paramMapView)
  {
    if (paramMapView == null)
      throw new RuntimeException("MapView 不能为空！");
    this.m = paramContext;
    this.a = paramMapView.a();
    this.b = ((m)this.a.e.a(2));
    this.e = 0.0F;
    this.f = new l(this.a);
    ai localai = this.a;
    Bitmap[] arrayOfBitmap = new Bitmap[2];
    arrayOfBitmap[0] = com.amap.mapapi.core.c.g.a(c.a.g.ordinal());
    arrayOfBitmap[1] = com.amap.mapapi.core.c.g.a(c.a.g.ordinal());
    this.g = new r(-1, 1000, localai, arrayOfBitmap);
    if (this.j != null)
      disableMyLocation();
    c();
    disableCompass();
  }

  private GeoPoint a(Location paramLocation)
  {
    GeoPoint localGeoPoint = null;
    if (paramLocation != null)
      localGeoPoint = new GeoPoint(e.a(paramLocation.getLatitude()), e.a(paramLocation.getLongitude()));
    return localGeoPoint;
  }

  private String b()
  {
    String str = this.i.getBestProvider(this.k, true);
    Object localObject1;
    Object localObject2;
    if (str == null)
    {
      Iterator localIterator = this.i.getProviders(true).iterator();
      localObject1 = str;
      if (!localIterator.hasNext())
        break label84;
      localObject2 = (String)localIterator.next();
      if ((!"gps".equals(localObject2)) && (!"network".equals(localObject2)))
        break label111;
    }
    while (true)
    {
      localObject1 = localObject2;
      break;
      localObject1 = str;
      label84: Log.d("MyLocationOverlay", "getProvider " + (String)localObject1);
      return localObject1;
      label111: localObject2 = localObject1;
    }
  }

  private void c()
  {
    this.k = new Criteria();
    this.k.setAccuracy(2);
    this.k.setAltitudeRequired(false);
    this.k.setBearingRequired(false);
    this.k.setPowerRequirement(2);
  }

  private Rect d()
  {
    GeoPoint localGeoPoint = getMyLocation();
    Rect localRect = null;
    if (localGeoPoint != null)
    {
      int i1 = this.g.h() / 2;
      int i2 = this.g.i() / 2;
      Point localPoint = this.a.a.toPixels(localGeoPoint, null);
      localRect = new Rect(localPoint.x - i1, localPoint.y - i2, i1 + localPoint.x, i2 + localPoint.y);
    }
    return localRect;
  }

  void a()
  {
  }

  public void disableCompass()
  {
    this.b.e();
    this.d = false;
  }

  public void disableMyLocation()
  {
    if (this.j != null)
      this.j.a();
    this.j = null;
    this.c = false;
    if (this.i != null)
      this.i.destory();
    this.i = null;
  }

  protected boolean dispatchTap()
  {
    return false;
  }

  public boolean draw(Canvas paramCanvas, MapView paramMapView, boolean paramBoolean, long paramLong)
  {
    if (paramBoolean);
    do
    {
      return false;
      if (!this.c)
        continue;
      Location localLocation = getLastFix();
      if (localLocation == null)
        continue;
      drawMyLocation(paramCanvas, this.a.b.g(), localLocation, a(localLocation), paramLong);
    }
    while (!this.d);
    drawCompass(paramCanvas, this.e);
    return false;
  }

  protected void drawCompass(Canvas paramCanvas, float paramFloat)
  {
    this.f.a(paramFloat);
    this.f.draw(paramCanvas, this.a.b.g(), false, 0L);
  }

  protected void drawMyLocation(Canvas paramCanvas, MapView paramMapView, Location paramLocation, GeoPoint paramGeoPoint, long paramLong)
  {
    Point localPoint = this.a.a.toPixels(paramGeoPoint, null);
    float f1 = 500.0F;
    Paint localPaint = new Paint();
    localPaint.setColor(-16776961);
    localPaint.setAlpha(40);
    aj localaj = paramMapView.b().a();
    if ((!paramLocation.equals("lbs")) && (paramLocation.hasAccuracy()) && (paramLocation.getAccuracy() > 0.0F))
    {
      if (!localaj.m)
        break label200;
      f1 = aj.j * paramLocation.getAccuracy();
    }
    while (true)
    {
      paramCanvas.drawCircle(localPoint.x, localPoint.y, (int)paramMapView.getProjection().metersToEquatorPixels(f1), localPaint);
      localPaint.setAlpha(255);
      localPaint.setStyle(Paint.Style.STROKE);
      localPaint.setAntiAlias(true);
      paramCanvas.drawCircle(localPoint.x, localPoint.y, (int)paramMapView.getProjection().metersToEquatorPixels(f1), localPaint);
      this.g.a(paramCanvas, localPoint.x, localPoint.y);
      return;
      label200: f1 = bj.h * paramLocation.getAccuracy();
    }
  }

  public boolean enableCompass()
  {
    if (this.b.a(this))
    {
      this.d = true;
      return true;
    }
    return false;
  }

  public boolean enableMyLocation()
  {
    com.amap.mapapi.location.c localc = this.j;
    boolean bool = false;
    if (localc == null)
    {
      if (this.i == null)
        this.i = LocationManagerProxy.getInstance(this.m);
      this.j = new com.amap.mapapi.location.c(this.i);
      this.n = b();
      if (!"lbs".equals(this.n))
        break label95;
    }
    label95: for (bool = this.j.a(this, 10000L, 5.0F, this.n); ; bool = this.j.a(this, 10000L, 5.0F))
    {
      if (bool)
        this.c = true;
      return bool;
    }
  }

  public Location getLastFix()
  {
    return this.l;
  }

  public GeoPoint getMyLocation()
  {
    return a(getLastFix());
  }

  public float getOrientation()
  {
    return this.e;
  }

  public boolean isCompassEnabled()
  {
    return this.d;
  }

  public boolean isMyLocationEnabled()
  {
    return this.c;
  }

  public void onAccuracyChanged(int paramInt1, int paramInt2)
  {
  }

  public void onAccuracyChanged(Sensor paramSensor, int paramInt)
  {
  }

  public void onLocationChanged(Location paramLocation)
  {
    Log.d("MyLocationOverlay", "onLocationChanged " + paramLocation.getLatitude() + "," + paramLocation.getLongitude());
    if (paramLocation != null)
    {
      this.l = paramLocation;
      if (this.a.d != null)
        this.a.d.d();
      if ((this.h != null) && (this.h.size() > 0))
      {
        Iterator localIterator = this.h.iterator();
        while (localIterator.hasNext())
        {
          Runnable localRunnable = (Runnable)localIterator.next();
          if (localRunnable == null)
            continue;
          new Thread(localRunnable).start();
        }
        this.h.clear();
      }
    }
  }

  public void onProviderDisabled(String paramString)
  {
    Log.d("MyLocationOverlay", "onProviderDisabled " + paramString);
  }

  public void onProviderEnabled(String paramString)
  {
    Log.d("MyLocationOverlay", "onProviderEnabled " + paramString);
  }

  public void onSensorChanged(int paramInt, float[] paramArrayOfFloat)
  {
    this.e = paramArrayOfFloat[0];
    if (this.a.d != null)
      this.a.d.a(this.f.c().left, this.f.c().top, this.f.b.getWidth() + this.f.a().x, this.f.b.getHeight() + this.f.a().y);
  }

  public void onSensorChanged(SensorEvent paramSensorEvent)
  {
    this.e = paramSensorEvent.values[0];
    if (this.a.d != null)
      this.a.d.a(this.f.c().left, this.f.c().top, this.f.b.getWidth() + this.f.a().x, this.f.b.getHeight() + this.f.a().y);
  }

  public boolean onSnapToItem(int paramInt1, int paramInt2, Point paramPoint, MapView paramMapView)
  {
    GeoPoint localGeoPoint = getMyLocation();
    int i1 = 0;
    if (localGeoPoint != null)
    {
      Point localPoint = paramMapView.getProjection().toPixels(localGeoPoint, null);
      paramPoint.x = localPoint.x;
      paramPoint.y = localPoint.y;
      double d1 = paramInt1 - localPoint.x;
      double d2 = paramInt2 - localPoint.y;
      boolean bool = d1 * d1 + d2 * d2 < 64.0D;
      i1 = 0;
      if (bool)
        i1 = 1;
    }
    return i1;
  }

  public void onStatusChanged(String paramString, int paramInt, Bundle paramBundle)
  {
    Log.d("MyLocationOverlay", "onStatusChanged " + paramString + " " + paramInt);
    if ((this.c) && (paramString != null) && (paramString.equals(this.n)) && ((paramInt == 0) || (paramInt == 1)))
    {
      this.n = b();
      if ("lbs".equals(this.n))
        this.j.a(this, 10000L, 5.0F, this.n);
    }
    else
    {
      return;
    }
    this.j.a(this, 10000L, 5.0F);
  }

  public boolean onTap(GeoPoint paramGeoPoint, MapView paramMapView)
  {
    boolean bool1 = this.c;
    boolean bool2 = false;
    if (bool1)
    {
      Rect localRect = d();
      bool2 = false;
      if (localRect != null)
      {
        Point localPoint = this.a.a.toPixels(paramGeoPoint, null);
        boolean bool3 = localRect.contains(localPoint.x, localPoint.y);
        bool2 = false;
        if (bool3)
          bool2 = dispatchTap();
      }
    }
    return bool2;
  }

  public boolean runOnFirstFix(Runnable paramRunnable)
  {
    if ((this.l != null) && (this.j != null))
    {
      new Thread(paramRunnable).start();
      return true;
    }
    this.h.addLast(paramRunnable);
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.MyLocationOverlay
 * JD-Core Version:    0.6.0
 */