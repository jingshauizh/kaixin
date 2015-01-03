package com.amap.mapapi.location;

import android.location.Criteria;
import android.location.LocationManager;
import android.location.LocationProvider;

public class LocationProviderProxy
{
  public static final int AVAILABLE = 2;
  public static final String MapABCNetwork = "lbs";
  public static final int OUT_OF_SERVICE = 0;
  public static final int TEMPORARILY_UNAVAILABLE = 1;
  private LocationManager a;
  private String b;

  protected LocationProviderProxy(LocationManager paramLocationManager, String paramString)
  {
    this.a = paramLocationManager;
    this.b = paramString;
  }

  private LocationProvider a()
  {
    return this.a.getProvider(this.b);
  }

  static LocationProviderProxy a(LocationManager paramLocationManager, String paramString)
  {
    return new LocationProviderProxy(paramLocationManager, paramString);
  }

  public int getAccuracy()
  {
    if ("lbs".equals(this.b))
      return 2;
    return a().getAccuracy();
  }

  public String getName()
  {
    if ("lbs".equals(this.b))
      return "lbs";
    return a().getName();
  }

  public int getPowerRequirement()
  {
    if ("lbs".equals(this.b))
      return 2;
    return a().getPowerRequirement();
  }

  public boolean hasMonetaryCost()
  {
    if ("lbs".equals(this.b))
      return false;
    return a().hasMonetaryCost();
  }

  public boolean meetsCriteria(Criteria paramCriteria)
  {
    if ("lbs".equals(this.b))
    {
      if (paramCriteria == null);
      do
        return true;
      while ((!paramCriteria.isAltitudeRequired()) && (!paramCriteria.isBearingRequired()) && (!paramCriteria.isSpeedRequired()) && (paramCriteria.getAccuracy() != 1));
      return false;
    }
    return a().meetsCriteria(paramCriteria);
  }

  public boolean requiresCell()
  {
    if ("lbs".equals(this.b))
      return true;
    return a().requiresCell();
  }

  public boolean requiresNetwork()
  {
    if ("lbs".equals(this.b))
      return true;
    return a().requiresNetwork();
  }

  public boolean requiresSatellite()
  {
    if ("lbs".equals(this.b))
      return false;
    return a().requiresNetwork();
  }

  public boolean supportsAltitude()
  {
    if ("lbs".equals(this.b))
      return false;
    return a().supportsAltitude();
  }

  public boolean supportsBearing()
  {
    if ("lbs".equals(this.b))
      return false;
    return a().supportsBearing();
  }

  public boolean supportsSpeed()
  {
    if ("lbs".equals(this.b))
      return false;
    return a().supportsSpeed();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.location.LocationProviderProxy
 * JD-Core Version:    0.6.0
 */