package com.amap.mapapi.geocoder;

import android.content.Context;
import android.location.Address;
import com.amap.mapapi.core.AMapException;
import com.amap.mapapi.core.GeoPoint.b;
import com.amap.mapapi.core.e;
import com.amap.mapapi.core.i;
import com.amap.mapapi.core.p;
import com.amap.mapapi.core.q;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.List<Landroid.location.Address;>;

public final class Geocoder
{
  public static final String Cross = "Cross";
  public static final String POI = "POI";
  public static final String Street_Road = "StreetAndRoad";
  private String a;
  private Context b;

  public Geocoder(Context paramContext)
  {
    com.amap.mapapi.core.b.a(paramContext);
    a(paramContext, e.a(paramContext));
  }

  public Geocoder(Context paramContext, String paramString)
  {
    com.amap.mapapi.core.b.a(paramContext);
    a(paramContext, e.a(paramContext));
  }

  private List<Address> a(double paramDouble1, double paramDouble2, int paramInt, boolean paramBoolean)
    throws AMapException
  {
    if (e.a)
    {
      if ((paramDouble1 < e.a(1000000L)) || (paramDouble1 > e.a(65000000L)))
        throw new AMapException("无效的参数 - IllegalArgumentException latitude == " + paramDouble1);
      if ((paramDouble2 < e.a(50000000L)) || (paramDouble2 > e.a(145000000L)))
        throw new AMapException("无效的参数 - IllegalArgumentException longitude == " + paramDouble2);
    }
    if (paramInt <= 0)
      return new ArrayList();
    return (List)new p(new q(paramDouble2, paramDouble1, paramInt, paramBoolean), e.b(this.b), this.a, null).g();
  }

  private List<Address> a(List<Address> paramList, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, int paramInt)
  {
    Object localObject = null;
    if (paramList != null)
    {
      ArrayList localArrayList = new ArrayList();
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        Address localAddress = (Address)localIterator.next();
        double d1 = localAddress.getLongitude();
        double d2 = localAddress.getLatitude();
        if ((d1 > paramDouble4) || (d1 < paramDouble2) || (d2 > paramDouble3) || (d2 < paramDouble1) || (localArrayList.size() >= paramInt))
          continue;
        localArrayList.add(localAddress);
      }
      localObject = localArrayList;
    }
    return localObject;
  }

  private void a(Context paramContext, String paramString)
  {
    this.b = paramContext;
    this.a = paramString;
  }

  public List<Address> getFromLocation(double paramDouble1, double paramDouble2, int paramInt)
    throws AMapException
  {
    return a(paramDouble1, paramDouble2, paramInt, false);
  }

  public List<Address> getFromLocationName(String paramString, int paramInt)
    throws AMapException
  {
    return getFromLocationName(paramString, paramInt, e.a(1000000L), e.a(50000000L), e.a(65000000L), e.a(145000000L));
  }

  public List<Address> getFromLocationName(String paramString, int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
    throws AMapException
  {
    if (paramString == null)
      throw new IllegalArgumentException("locationName == null");
    if (e.a)
    {
      if ((paramDouble1 < e.a(1000000L)) || (paramDouble1 > e.a(65000000L)))
        throw new AMapException("无效的参数 - IllegalArgumentException lowerLeftLatitude == " + paramDouble1);
      if ((paramDouble2 < e.a(50000000L)) || (paramDouble2 > e.a(145000000L)))
        throw new AMapException("无效的参数 - IllegalArgumentException lowerLeftLongitude == " + paramDouble2);
      if ((paramDouble3 < e.a(1000000L)) || (paramDouble3 > e.a(65000000L)))
        throw new AMapException("无效的参数 - IllegalArgumentException upperRightLatitude == " + paramDouble3);
      if ((paramDouble4 < e.a(50000000L)) || (paramDouble4 > e.a(145000000L)))
        throw new AMapException("无效的参数 - IllegalArgumentException upperRightLongitude == " + paramDouble4);
    }
    Object localObject;
    if (paramInt <= 0)
      localObject = new ArrayList();
    do
    {
      return localObject;
      localObject = (List)new a(new b(paramString, 15), e.b(this.b), this.a, null).g();
    }
    while (!e.a);
    return (List<Address>)a((List)localObject, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramInt);
  }

  public List<Address> getFromRawGpsLocation(double paramDouble1, double paramDouble2, int paramInt)
    throws AMapException
  {
    while (true)
    {
      try
      {
        GeoPoint.b localb = new i(new GeoPoint.b(paramDouble2, paramDouble1), e.b(this.b), this.a, null).a();
        if (localb == null)
          break label102;
        d1 = localb.a;
        d2 = localb.b;
        if ((Double.valueOf(0.0D).doubleValue() == d1) && (Double.valueOf(0.0D).doubleValue() == d2))
          return null;
      }
      catch (Exception localException)
      {
        return new ArrayList();
      }
      return a(d2, d1, paramInt, false);
      label102: double d1 = paramDouble2;
      double d2 = paramDouble1;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.geocoder.Geocoder
 * JD-Core Version:    0.6.0
 */