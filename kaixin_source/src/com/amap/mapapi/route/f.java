package com.amap.mapapi.route;

import com.amap.mapapi.core.GeoPoint;
import com.amap.mapapi.core.e;

public class f
{
  public Route.FromAndTo a;
  public int b;

  public f(Route.FromAndTo paramFromAndTo, int paramInt)
  {
    this.a = paramFromAndTo;
    this.b = paramInt;
  }

  public double a()
  {
    return e.a(this.a.mFrom.a());
  }

  public double b()
  {
    return e.a(this.a.mTo.a());
  }

  public double c()
  {
    return e.a(this.a.mFrom.b());
  }

  public double d()
  {
    return e.a(this.a.mTo.b());
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.route.f
 * JD-Core Version:    0.6.0
 */