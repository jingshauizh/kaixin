package com.amap.mapapi.location;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

class b
  implements LocationListener
{
  b(a parama)
  {
  }

  public void onLocationChanged(Location paramLocation)
  {
    a.a(this.a, paramLocation);
    a.a(System.currentTimeMillis());
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

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.location.b
 * JD-Core Version:    0.6.0
 */