package com.amap.mapapi.location;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.amap.mapapi.core.AMapException;
import com.amap.mapapi.core.GeoPoint.b;
import com.amap.mapapi.core.e;
import com.amap.mapapi.core.i;
import com.autonavi.aps.api.APSFactory;
import com.autonavi.aps.api.IAPS;
import java.util.ArrayList;
import java.util.Iterator;

public class a
  implements Runnable
{
  private static a b = null;
  private static int f = 100;
  private static int g = 102;
  private static int h = 103;
  private static long r;
  private IAPS a = null;
  private a c = null;
  private volatile boolean d = true;
  private Thread e = null;
  private ArrayList<b> i = null;
  private android.location.Location j = null;
  private Context k;
  private LocationManager l;
  private boolean m = false;
  private android.location.Location n = null;
  private float o = 20.0F;
  private long p = 2000L;
  private long q = 5000L;
  private LocationListener s = new b(this);

  private a(Context paramContext, LocationManager paramLocationManager)
  {
    this.k = paramContext;
    this.l = paramLocationManager;
    this.c = new a();
    this.a = APSFactory.getInstance(paramContext.getApplicationContext());
    com.amap.mapapi.core.b.a(paramContext);
    this.a.setProductName("yun_droid_mapsdk");
    this.a.setLicence("32M0145A3D7E1266UY6BC6E017AD2387");
    this.a.setKey(com.amap.mapapi.core.b.b(paramContext));
    this.a.setPackageName(com.amap.mapapi.core.b.a());
    this.i = new ArrayList();
  }

  private android.location.Location a(android.location.Location paramLocation)
  {
    GeoPoint.b localb = new i(new GeoPoint.b(paramLocation.getLongitude(), paramLocation.getLatitude()), null, null, null).a();
    if (localb != null)
    {
      paramLocation.setLatitude(localb.b);
      paramLocation.setLongitude(localb.a);
    }
    return paramLocation;
  }

  private android.location.Location a(com.autonavi.aps.api.Location paramLocation)
  {
    android.location.Location localLocation = new android.location.Location("");
    localLocation.setProvider("lbs");
    localLocation.setLatitude(paramLocation.getCeny());
    localLocation.setLongitude(paramLocation.getCenx());
    localLocation.setAccuracy((float)paramLocation.getRadius());
    Bundle localBundle = new Bundle();
    localBundle.putString("citycode", paramLocation.getCitycode());
    localBundle.putString("desc", paramLocation.getDesc());
    localLocation.setExtras(localBundle);
    return localLocation;
  }

  public static a a(Context paramContext, LocationManager paramLocationManager)
  {
    monitorenter;
    try
    {
      if (b == null)
      {
        b = new a(paramContext, paramLocationManager);
        com.amap.mapapi.core.b.b(paramContext);
      }
      a locala = b;
      return locala;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private com.autonavi.aps.api.Location f()
    throws AMapException
  {
    com.autonavi.aps.api.Location localLocation1;
    if (com.amap.mapapi.core.a.a == -1)
    {
      boolean bool = com.amap.mapapi.core.a.a(this.k);
      localLocation1 = null;
      if (bool)
      {
        localLocation1 = this.a.getCurrentLocation(null);
        g();
      }
    }
    int i1;
    do
    {
      return localLocation1;
      i1 = com.amap.mapapi.core.a.a;
      localLocation1 = null;
    }
    while (i1 != 1);
    com.autonavi.aps.api.Location localLocation2 = this.a.getCurrentLocation(null);
    g();
    return localLocation2;
  }

  private void g()
  {
    com.amap.mapapi.core.a.c = 1 + com.amap.mapapi.core.a.c;
    if (com.amap.mapapi.core.a.c == 1000)
    {
      com.amap.mapapi.core.a.a = -1;
      com.amap.mapapi.core.a.c = 0;
    }
  }

  public void a()
  {
    this.d = false;
    if (this.e != null)
      this.e.interrupt();
    if (this.l != null)
      this.l.removeUpdates(this.s);
    if (this.c != null)
      this.c.removeMessages(f);
    if (this.a != null)
      this.a.onDestroy();
    this.i.clear();
    this.a = null;
    b = null;
    this.j = null;
    this.c = null;
  }

  public void a(long paramLong, float paramFloat, LocationListener paramLocationListener)
  {
    b localb = new b(paramLong, paramFloat, paramLocationListener);
    this.i.add(localb);
    if ((this.l != null) && (this.i.size() == 1))
    {
      this.l.requestLocationUpdates("gps", this.p, this.o, this.s);
      this.m = true;
    }
  }

  public void a(LocationListener paramLocationListener)
  {
    if (this.c == null)
      return;
    Message localMessage = this.c.obtainMessage();
    localMessage.what = h;
    localMessage.obj = paramLocationListener;
    this.c.sendMessage(localMessage);
  }

  public android.location.Location b()
  {
    if (this.j != null)
      return this.j;
    return e.d(this.k);
  }

  public void run()
  {
    while (true)
      if (this.d)
      {
        this.e = Thread.currentThread();
        if (this.i.size() == 0)
        {
          try
          {
            Thread.sleep(100L);
          }
          catch (Exception localException3)
          {
            Thread.currentThread().interrupt();
          }
          continue;
        }
        b localb;
        long l1;
        while (true)
        {
          synchronized (this.i)
          {
            localb = (b)this.i.get(-1 + this.i.size());
            l1 = e.a();
            if (this.o > localb.b)
            {
              f1 = localb.b;
              this.o = f1;
              if (this.p <= localb.a)
                break label203;
              l2 = localb.a;
              this.p = l2;
              if (localb == null)
                break label467;
              if (localb.a >= 5000L)
                break label212;
              l3 = 5000L;
              this.q = l3;
              if (l1 - localb.d < localb.a)
                continue;
              localb.e = true;
              if (!localb.e)
                break label467;
              if (this.c != null)
                break;
            }
          }
          float f1 = this.o;
          continue;
          label203: long l2 = this.p;
          continue;
          label212: long l3 = localb.a;
        }
        Message localMessage = this.c.obtainMessage();
        if (System.currentTimeMillis() - r > 10L * this.p)
          this.m = false;
        try
        {
          while (true)
          {
            if (this.n != null)
            {
              boolean bool = this.m;
              localObject2 = null;
              if (bool);
            }
            else
            {
              com.autonavi.aps.api.Location localLocation1 = f();
              localObject2 = localLocation1;
            }
            if ((localObject2 == null) && ((this.n == null) || (!this.m)))
              break label467;
            localb.e = false;
            localb.d = l1;
            if (localObject2 == null)
              break label406;
            localLocation = a(localObject2);
            if (localLocation.equals(this.j))
              break label467;
            this.j = localLocation;
            localb.f = localLocation;
            localMessage.what = f;
            localMessage.obj = localb;
            if (this.c != null)
              break label448;
            monitorexit;
            break;
            this.m = true;
          }
        }
        catch (Exception localException2)
        {
          android.location.Location localLocation;
          while (true)
          {
            Object localObject2 = null;
            if (localb == null)
              continue;
            localb.e = true;
            localObject2 = null;
            continue;
            label406: if (e.a(this.n.getLatitude(), this.n.getLongitude()))
            {
              localLocation = a(this.n);
              continue;
            }
            localLocation = this.n;
          }
          label448: this.c.sendMessage(localMessage);
          e.a(this.k, localLocation);
        }
        try
        {
          label467: Thread.sleep(this.q);
          monitorexit;
        }
        catch (Exception localException1)
        {
          while (true)
            Thread.currentThread().interrupt();
        }
      }
  }

  class a extends Handler
  {
    a()
    {
    }

    public void handleMessage(Message paramMessage)
    {
      if (paramMessage.what == a.c())
      {
        Iterator localIterator = a.a(a.this).iterator();
        while (localIterator.hasNext())
          ((a.b)localIterator.next()).c.onLocationChanged(((a.b)paramMessage.obj).f);
      }
      if (paramMessage.what == a.d())
        break label73;
      label73: 
      do
        return;
      while (paramMessage.what != a.e());
      LocationListener localLocationListener = (LocationListener)paramMessage.obj;
      int i = a.a(a.this).size();
      int j = 0;
      label106: if (j < i)
      {
        a.b localb = (a.b)a.a(a.this).get(j);
        if (!localLocationListener.equals(localb.c))
          break label209;
        a.a(a.this).remove(localb);
      }
      label209: for (int k = i - 1; ; k = i)
      {
        j++;
        i = k;
        break label106;
        if ((a.b(a.this) == null) || (a.a(a.this).size() != 0))
          break;
        a.b(a.this).removeUpdates(a.c(a.this));
        return;
      }
    }
  }

  public static class b
  {
    public long a;
    public float b;
    public LocationListener c;
    public long d;
    public boolean e = true;
    public android.location.Location f = null;

    public b(long paramLong, float paramFloat, LocationListener paramLocationListener)
    {
      this.a = paramLong;
      this.b = paramFloat;
      this.c = paramLocationListener;
    }

    public boolean equals(Object paramObject)
    {
      if (this == paramObject);
      b localb;
      do
        while (true)
        {
          return true;
          if (paramObject == null)
            return false;
          if (getClass() != paramObject.getClass())
            return false;
          localb = (b)paramObject;
          if (this.c != null)
            break;
          if (localb.c != null)
            return false;
        }
      while (this.c.equals(localb.c));
      return false;
    }

    public int hashCode()
    {
      if (this.c == null);
      for (int i = 0; ; i = this.c.hashCode())
        return i + 31;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.location.a
 * JD-Core Version:    0.6.0
 */