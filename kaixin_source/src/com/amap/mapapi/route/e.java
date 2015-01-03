package com.amap.mapapi.route;

import com.amap.mapapi.core.AMapException;
import com.amap.mapapi.core.GeoPoint;
import com.amap.mapapi.core.m;
import java.io.InputStream;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

public abstract class e extends m<f, ArrayList<Route>>
{
  protected String j = "起点";
  protected String k = "终点";
  protected GeoPoint l = ((f)this.b).a.mFrom;
  protected GeoPoint m = ((f)this.b).a.mTo;

  public e(f paramf, Proxy paramProxy, String paramString1, String paramString2)
  {
    super(paramf, paramProxy, paramString1, paramString2);
  }

  protected ArrayList<Route> a(InputStream paramInputStream)
    throws AMapException
  {
    return null;
  }

  public abstract void a(List<GeoPoint> paramList);

  protected GeoPoint[] a(String[] paramArrayOfString)
  {
    int i = 0;
    GeoPoint[] arrayOfGeoPoint = new GeoPoint[paramArrayOfString.length / 2];
    int n = paramArrayOfString.length;
    int i1 = 0;
    while (i1 < n - 1)
    {
      long l1 = ()(1000000.0D * Double.parseDouble(paramArrayOfString[i1]));
      arrayOfGeoPoint[i] = new GeoPoint(()(1000000.0D * Double.parseDouble(paramArrayOfString[(i1 + 1)])), l1);
      i1 += 2;
      i++;
    }
    return arrayOfGeoPoint;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.route.e
 * JD-Core Version:    0.6.0
 */