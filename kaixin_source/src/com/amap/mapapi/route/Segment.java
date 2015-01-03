package com.amap.mapapi.route;

import com.amap.mapapi.core.GeoPoint;

public class Segment
{
  private GeoPoint a = null;
  private GeoPoint b = null;
  protected int mLength;
  protected Route mRoute;
  protected GeoPoint[] mShapes;
  protected String strTimeConsume = "";

  private void a()
  {
    int i = 2147483647;
    int j = -2147483648;
    GeoPoint[] arrayOfGeoPoint = this.mShapes;
    int k = arrayOfGeoPoint.length;
    int m = 0;
    int n = j;
    int i1 = i;
    int i2;
    int i3;
    if (m < k)
    {
      GeoPoint localGeoPoint = arrayOfGeoPoint[m];
      i2 = localGeoPoint.getLongitudeE6();
      i3 = localGeoPoint.getLatitudeE6();
      if (i2 > n)
        n = i2;
      if (i2 >= i1)
        break label132;
    }
    while (true)
    {
      if (i3 > j)
        j = i3;
      if (i3 < i);
      while (true)
      {
        m++;
        i1 = i2;
        i = i3;
        break;
        this.a = new GeoPoint(i, i1);
        this.b = new GeoPoint(j, n);
        return;
        i3 = i;
      }
      label132: i2 = i1;
    }
  }

  private int b()
  {
    int i = this.mRoute.getSegmentIndex(this);
    if (i < 0)
      throw new IllegalArgumentException("this segment is not in the route !");
    return i;
  }

  public String getConsumeTime()
  {
    return this.strTimeConsume;
  }

  public GeoPoint getFirstPoint()
  {
    return this.mShapes[0];
  }

  public GeoPoint getLastPoint()
  {
    return this.mShapes[(-1 + this.mShapes.length)];
  }

  public int getLength()
  {
    return this.mLength;
  }

  public GeoPoint getLowerLeftPoint()
  {
    if (this.a == null)
      a();
    return this.a;
  }

  public Segment getNext()
  {
    int i = b();
    if (i == -1 + this.mRoute.getStepCount())
      return null;
    return this.mRoute.getStep(i + 1);
  }

  public Segment getPrev()
  {
    int i = b();
    if (i == 0)
      return null;
    return this.mRoute.getStep(i - 1);
  }

  public GeoPoint[] getShapes()
  {
    return this.mShapes;
  }

  public GeoPoint getUpperRightPoint()
  {
    if (this.b == null)
      a();
    return this.b;
  }

  public void setConsumeTime(String paramString)
  {
    this.strTimeConsume = paramString;
  }

  public void setLength(int paramInt)
  {
    this.mLength = paramInt;
  }

  public void setRoute(Route paramRoute)
  {
    this.mRoute = paramRoute;
  }

  public void setShapes(GeoPoint[] paramArrayOfGeoPoint)
  {
    this.mShapes = paramArrayOfGeoPoint;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.route.Segment
 * JD-Core Version:    0.6.0
 */