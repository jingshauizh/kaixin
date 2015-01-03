package com.amap.mapapi.location;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import com.amap.mapapi.core.b;
import com.amap.mapapi.core.e;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.List<Ljava.lang.String;>;

public class LocationManagerProxy
{
  public static final String GPS_PROVIDER = "gps";
  public static final String KEY_LOCATION_CHANGED = "location";
  public static final String KEY_PROVIDER_ENABLED = "providerEnabled";
  public static final String KEY_PROXIMITY_ENTERING = "entering";
  public static final String KEY_STATUS_CHANGED = "status";
  public static final String NETWORK_PROVIDER = "network";
  private static LocationManagerProxy b = null;
  private LocationManager a = null;
  private a c = null;
  private Context d;
  private c e;
  private b f;
  private ArrayList<PendingIntent> g = new ArrayList();
  private Hashtable<String, LocationProviderProxy> h = new Hashtable();
  private String i;
  private double j;
  private double k;
  private boolean l = false;
  private long m = 0L;
  private double n = 0.0D;
  private c o;
  private a p;
  private ArrayList<PendingIntent> q = new ArrayList();
  private Thread r;

  private LocationManagerProxy(Activity paramActivity)
  {
    b.a(paramActivity);
    a(paramActivity.getApplicationContext());
  }

  private LocationManagerProxy(Context paramContext)
  {
    a(paramContext);
  }

  private void a(Context paramContext)
  {
    this.d = paramContext;
    this.a = ((LocationManager)paramContext.getSystemService("location"));
    this.c = a.a(paramContext.getApplicationContext(), this.a);
    this.r = new Thread(this.c);
    this.r.setDaemon(true);
  }

  public static LocationManagerProxy getInstance(Activity paramActivity)
  {
    monitorenter;
    try
    {
      if (b == null)
        b = new LocationManagerProxy(paramActivity);
      while (true)
      {
        LocationManagerProxy localLocationManagerProxy = b;
        return localLocationManagerProxy;
        b.destory();
        b = new LocationManagerProxy(paramActivity);
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public static LocationManagerProxy getInstance(Context paramContext)
  {
    monitorenter;
    try
    {
      if (b == null)
        b = new LocationManagerProxy(paramContext);
      while (true)
      {
        LocationManagerProxy localLocationManagerProxy = b;
        return localLocationManagerProxy;
        b.destory();
        b = new LocationManagerProxy(paramContext);
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public boolean addGpsStatusListener(GpsStatus.Listener paramListener)
  {
    if (this.a != null)
      return this.a.addGpsStatusListener(paramListener);
    return false;
  }

  public void addProximityAlert(double paramDouble1, double paramDouble2, float paramFloat, long paramLong, PendingIntent paramPendingIntent)
  {
    if ("lbs".equals(this.i))
    {
      if (this.o == null)
        this.o = new c(this);
      if (this.p == null)
        this.p = new a();
      this.o.a(this.p, 10000L, paramFloat, this.i);
      this.l = true;
      this.j = paramDouble1;
      this.k = paramDouble2;
      this.n = paramFloat;
      if (paramLong != -1L)
        this.m = (paramLong + e.a());
      this.q.add(paramPendingIntent);
    }
    do
      return;
    while (this.a == null);
    this.a.addProximityAlert(paramDouble1, paramDouble2, paramFloat, paramLong, paramPendingIntent);
  }

  public void addTestProvider(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6, boolean paramBoolean7, int paramInt1, int paramInt2)
  {
    if (this.a != null)
      this.a.addTestProvider(paramString, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramBoolean5, paramBoolean6, paramBoolean7, paramInt1, paramInt2);
  }

  public void clearTestProviderEnabled(String paramString)
  {
    if (this.a != null)
      this.a.clearTestProviderEnabled(paramString);
  }

  public void clearTestProviderLocation(String paramString)
  {
    if (this.a != null)
      this.a.clearTestProviderLocation(paramString);
  }

  public void clearTestProviderStatus(String paramString)
  {
    if (this.a != null)
      this.a.clearTestProviderStatus(paramString);
  }

  public void destory()
  {
    if (this.c != null)
      this.c.a();
    if (this.h != null)
      this.h.clear();
    if (this.g != null)
      this.g.clear();
    if (this.q != null)
      this.q.clear();
    this.h = null;
    this.g = null;
    this.q = null;
    this.c = null;
    b = null;
  }

  public List<String> getAllProviders()
  {
    List localList = this.a.getAllProviders();
    if (localList != null)
    {
      if (!localList.contains("lbs"))
        localList.add("lbs");
      return localList;
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.add("lbs");
    localArrayList.addAll(this.a.getAllProviders());
    return localArrayList;
  }

  public String getBestProvider(Criteria paramCriteria, boolean paramBoolean)
  {
    String str = "lbs";
    if (paramCriteria == null);
    do
    {
      return str;
      if (getProvider("lbs").meetsCriteria(paramCriteria))
        continue;
      str = this.a.getBestProvider(paramCriteria, paramBoolean);
    }
    while ((!paramBoolean) || (e.c(this.d)));
    return this.a.getBestProvider(paramCriteria, paramBoolean);
  }

  public GpsStatus getGpsStatus(GpsStatus paramGpsStatus)
  {
    if (this.a != null)
      return this.a.getGpsStatus(paramGpsStatus);
    return null;
  }

  public Location getLastKnownLocation(String paramString)
  {
    if ("lbs".equals(paramString))
    {
      this.i = paramString;
      return this.c.b();
    }
    if (this.a != null)
      return this.a.getLastKnownLocation(paramString);
    return null;
  }

  public LocationProviderProxy getProvider(String paramString)
  {
    if (paramString == null)
      throw new IllegalArgumentException("name不能为空！");
    if (this.h.containsKey(paramString))
      return (LocationProviderProxy)this.h.get(paramString);
    LocationProviderProxy localLocationProviderProxy = LocationProviderProxy.a(this.a, paramString);
    this.h.put(paramString, localLocationProviderProxy);
    return localLocationProviderProxy;
  }

  public List<String> getProviders(Criteria paramCriteria, boolean paramBoolean)
  {
    List localList = this.a.getProviders(paramCriteria, paramBoolean);
    if ("lbs".equals(getBestProvider(paramCriteria, paramBoolean)))
      localList.add("lbs");
    return localList;
  }

  public List<String> getProviders(boolean paramBoolean)
  {
    Object localObject = this.a.getProviders(paramBoolean);
    if (isProviderEnabled("lbs"))
    {
      if (localObject == null)
        localObject = new ArrayList();
      ((List)localObject).add("lbs");
    }
    return (List<String>)localObject;
  }

  public boolean isProviderEnabled(String paramString)
  {
    if ("lbs".equals(paramString))
      return e.c(this.d);
    return this.a.isProviderEnabled(paramString);
  }

  public void removeGpsStatusListener(GpsStatus.Listener paramListener)
  {
    if (this.a != null)
      this.a.removeGpsStatusListener(paramListener);
  }

  public void removeProximityAlert(PendingIntent paramPendingIntent)
  {
    if ("lbs".equals(this.i))
    {
      if (this.o != null)
        this.o.a();
      this.q.remove(paramPendingIntent);
      this.o = null;
      this.l = false;
      this.m = 0L;
      this.n = 0.0D;
      this.j = 0.0D;
      this.k = 0.0D;
    }
    do
      return;
    while (this.a == null);
    this.a.removeProximityAlert(paramPendingIntent);
  }

  public void removeUpdates(PendingIntent paramPendingIntent)
  {
    if (this.e != null)
    {
      this.g.remove(paramPendingIntent);
      this.e.a();
    }
    this.e = null;
    this.a.removeUpdates(paramPendingIntent);
  }

  public void removeUpdates(LocationListener paramLocationListener)
  {
    if (paramLocationListener != null)
    {
      if (this.c != null)
        this.c.a(paramLocationListener);
      this.a.removeUpdates(paramLocationListener);
    }
  }

  public void requestLocationUpdates(String paramString, long paramLong, float paramFloat, PendingIntent paramPendingIntent)
  {
    if ("lbs".equals(paramString))
    {
      if (this.e == null)
        this.e = new c(this);
      if (this.f == null)
        this.f = new b();
      this.e.a(this.f, paramLong, paramFloat);
      this.g.add(paramPendingIntent);
      return;
    }
    this.a.requestLocationUpdates(paramString, paramLong, paramFloat, paramPendingIntent);
  }

  public void requestLocationUpdates(String paramString, long paramLong, float paramFloat, LocationListener paramLocationListener)
  {
    if ((this.r != null) && (!this.r.isAlive()))
      this.r.start();
    this.i = paramString;
    if ("lbs".equals(paramString))
    {
      this.c.a(paramLong, paramFloat, paramLocationListener);
      return;
    }
    if ("gps".equals(paramString))
    {
      this.c.a(paramLong, paramFloat, paramLocationListener);
      return;
    }
    this.a.requestLocationUpdates(paramString, paramLong, paramFloat, paramLocationListener);
  }

  class a
    implements LocationListener
  {
    a()
    {
    }

    public void onLocationChanged(Location paramLocation)
    {
      if ((LocationManagerProxy.c(LocationManagerProxy.this)) && (LocationManagerProxy.d(LocationManagerProxy.this).size() > 0))
      {
        double d1 = paramLocation.getLatitude();
        double d2 = paramLocation.getLongitude();
        double d3 = Math.abs((d1 - LocationManagerProxy.e(LocationManagerProxy.this)) * (d1 - LocationManagerProxy.e(LocationManagerProxy.this)) + (d2 - LocationManagerProxy.f(LocationManagerProxy.this)) * (d2 - LocationManagerProxy.f(LocationManagerProxy.this)));
        Iterator localIterator = LocationManagerProxy.d(LocationManagerProxy.this).iterator();
        while (localIterator.hasNext())
        {
          PendingIntent localPendingIntent = (PendingIntent)localIterator.next();
          if ((e.a() > LocationManagerProxy.g(LocationManagerProxy.this)) && (LocationManagerProxy.g(LocationManagerProxy.this) != 0L))
          {
            LocationManagerProxy.this.removeProximityAlert(localPendingIntent);
            continue;
          }
          if (Math.abs(d3 - LocationManagerProxy.h(LocationManagerProxy.this) * LocationManagerProxy.h(LocationManagerProxy.this)) >= 0.5D)
            continue;
          Intent localIntent = new Intent();
          Bundle localBundle = new Bundle();
          localBundle.putParcelable("location", paramLocation);
          localIntent.putExtras(localBundle);
          try
          {
            localPendingIntent.send(LocationManagerProxy.b(LocationManagerProxy.this), 0, localIntent);
          }
          catch (PendingIntent.CanceledException localCanceledException)
          {
            localCanceledException.printStackTrace();
          }
        }
      }
    }

    public void onProviderDisabled(String paramString)
    {
    }

    public void onProviderEnabled(String paramString)
    {
    }

    public void onStatusChanged(String paramString, int paramInt, Bundle paramBundle)
    {
    }
  }

  class b
    implements LocationListener
  {
    b()
    {
    }

    public void onLocationChanged(Location paramLocation)
    {
      if ((LocationManagerProxy.a(LocationManagerProxy.this) != null) && (LocationManagerProxy.a(LocationManagerProxy.this).size() > 0))
      {
        Iterator localIterator = LocationManagerProxy.a(LocationManagerProxy.this).iterator();
        while (localIterator.hasNext())
        {
          PendingIntent localPendingIntent = (PendingIntent)localIterator.next();
          Intent localIntent = new Intent();
          Bundle localBundle = new Bundle();
          localBundle.putParcelable("location", paramLocation);
          localIntent.putExtras(localBundle);
          try
          {
            localPendingIntent.send(LocationManagerProxy.b(LocationManagerProxy.this), 0, localIntent);
          }
          catch (PendingIntent.CanceledException localCanceledException)
          {
            localCanceledException.printStackTrace();
          }
        }
      }
    }

    public void onProviderDisabled(String paramString)
    {
    }

    public void onProviderEnabled(String paramString)
    {
    }

    public void onStatusChanged(String paramString, int paramInt, Bundle paramBundle)
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.location.LocationManagerProxy
 * JD-Core Version:    0.6.0
 */