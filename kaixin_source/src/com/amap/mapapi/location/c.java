package com.amap.mapapi.location;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import java.util.Iterator;
import java.util.List;

public class c
  implements LocationListener
{
  private LocationManagerProxy a;
  private LocationListener b = null;

  public c(LocationManagerProxy paramLocationManagerProxy)
  {
    this.a = paramLocationManagerProxy;
  }

  public void a()
  {
    if (this.a != null)
      this.a.removeUpdates(this);
    this.b = null;
  }

  public boolean a(LocationListener paramLocationListener, long paramLong, float paramFloat)
  {
    int i = 0;
    this.b = paramLocationListener;
    Iterator localIterator = this.a.getProviders(true).iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ((!"gps".equals(str)) && (!"network".equals(str)))
        continue;
      this.a.requestLocationUpdates(str, paramLong, paramFloat, this);
      i = 1;
    }
    return i;
  }

  public boolean a(LocationListener paramLocationListener, long paramLong, float paramFloat, String paramString)
  {
    this.b = paramLocationListener;
    boolean bool = "lbs".equals(paramString);
    int i = 0;
    if (bool)
    {
      this.a.requestLocationUpdates(paramString, paramLong, paramFloat, this);
      i = 1;
    }
    return i;
  }

  public void onLocationChanged(Location paramLocation)
  {
    if (this.b != null)
      this.b.onLocationChanged(paramLocation);
  }

  public void onProviderDisabled(String paramString)
  {
    if (this.b != null)
      this.b.onProviderDisabled(paramString);
  }

  public void onProviderEnabled(String paramString)
  {
    if (this.b != null)
      this.b.onProviderEnabled(paramString);
  }

  public void onStatusChanged(String paramString, int paramInt, Bundle paramBundle)
  {
    if (this.b != null)
      this.b.onStatusChanged(paramString, paramInt, paramBundle);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.location.c
 * JD-Core Version:    0.6.0
 */