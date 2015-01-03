package com.amap.mapapi.core;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class GeoPoint
  implements Parcelable
{
  public static final Parcelable.Creator<GeoPoint> CREATOR;
  static final double[] a = { 0.7111111111111111D, 1.422222222222222D, 2.844444444444445D, 5.688888888888889D, 11.377777777777778D, 22.755555555555556D, 45.511111111111113D, 91.022222222222226D, 182.04444444444445D, 364.0888888888889D, 728.17777777777781D, 1456.3555555555556D, 2912.7111111111112D, 5825.4222222222224D, 11650.844444444445D, 23301.68888888889D, 46603.37777777778D, 93206.755555555559D, 186413.51111111112D, 372827.02222222224D, 745654.04444444447D };
  static final double[] b = { 40.743665431525208D, 81.487330863050417D, 162.97466172610083D, 325.94932345220167D, 651.89864690440334D, 1303.7972938088067D, 2607.5945876176133D, 5215.1891752352267D, 10430.378350470453D, 20860.756700940907D, 41721.513401881813D, 83443.026803763627D, 166886.05360752725D, 333772.10721505451D, 667544.21443010902D, 1335088.428860218D, 2670176.8577204361D, 5340353.7154408721D, 10680707.430881744D, 21361414.861763489D, 42722829.723526977D };
  static final a[] c;
  private long d = -9223372036854775808L;
  private long e = -9223372036854775808L;
  private double f = 4.9E-324D;
  private double g = 4.9E-324D;

  static
  {
    a[] arrayOfa = new a[21];
    arrayOfa[0] = new a(128, 128);
    arrayOfa[1] = new a(256, 256);
    arrayOfa[2] = new a(512, 512);
    arrayOfa[3] = new a(1024, 1024);
    arrayOfa[4] = new a(2048, 2048);
    arrayOfa[5] = new a(4096, 4096);
    arrayOfa[6] = new a(8192, 8192);
    arrayOfa[7] = new a(16384, 16384);
    arrayOfa[8] = new a(32768, 32768);
    arrayOfa[9] = new a(65536, 65536);
    arrayOfa[10] = new a(131072, 131072);
    arrayOfa[11] = new a(262144, 262144);
    arrayOfa[12] = new a(524288, 524288);
    arrayOfa[13] = new a(1048576, 1048576);
    arrayOfa[14] = new a(2097152, 2097152);
    arrayOfa[15] = new a(4194304, 4194304);
    arrayOfa[16] = new a(8388608, 8388608);
    arrayOfa[17] = new a(16777216, 16777216);
    arrayOfa[18] = new a(33554432, 33554432);
    arrayOfa[19] = new a(67108864, 67108864);
    arrayOfa[20] = new a(134217728, 134217728);
    c = arrayOfa;
    CREATOR = new f();
  }

  public GeoPoint()
  {
    this.d = 0L;
    this.e = 0L;
  }

  public GeoPoint(double paramDouble1, double paramDouble2, long paramLong1, long paramLong2)
  {
    this.f = paramDouble1;
    this.g = paramDouble2;
    this.d = paramLong1;
    this.e = paramLong2;
  }

  public GeoPoint(double paramDouble1, double paramDouble2, boolean paramBoolean)
  {
    if (paramBoolean == true)
    {
      this.d = ()(paramDouble1 * 1000000.0D);
      this.e = ()(paramDouble2 * 1000000.0D);
      return;
    }
    this.f = paramDouble1;
    this.g = paramDouble2;
  }

  public GeoPoint(int paramInt1, int paramInt2)
  {
    this.d = paramInt1;
    this.e = paramInt2;
  }

  public GeoPoint(long paramLong1, long paramLong2)
  {
    this.d = paramLong1;
    this.e = paramLong2;
  }

  private GeoPoint(Parcel paramParcel)
  {
    this.d = paramParcel.readLong();
    this.e = paramParcel.readLong();
  }

  public long a()
  {
    return this.e;
  }

  public void a(double paramDouble)
  {
    this.g = paramDouble;
  }

  public long b()
  {
    return this.d;
  }

  public void b(double paramDouble)
  {
    this.f = paramDouble;
  }

  public double c()
  {
    if (c.h == EnumMapProjection.projection_custBeijing54)
    {
      if (this.g != 4.9E-324D)
        return this.g;
      return e.a(this.e);
    }
    if ((c.h == EnumMapProjection.projection_900913) && (this.g == 4.9E-324D))
      this.g = (20037508.34D * e.a(this.e) / 180.0D);
    return this.g;
  }

  public double d()
  {
    if (c.h == EnumMapProjection.projection_custBeijing54)
    {
      if (this.f != 4.9E-324D)
        return this.f;
      return e.a(this.d);
    }
    if ((c.h == EnumMapProjection.projection_900913) && (this.f == 4.9E-324D))
      this.f = (20037508.34D * (Math.log(Math.tan(3.141592653589793D * (90.0D + e.a(this.d)) / 360.0D)) / 0.0174532925199433D) / 180.0D);
    return this.f;
  }

  public int describeContents()
  {
    return 0;
  }

  public GeoPoint e()
  {
    return new GeoPoint(this.f, this.g, this.d, this.e);
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == null);
    GeoPoint localGeoPoint;
    do
    {
      do
        return false;
      while (paramObject.getClass() != getClass());
      localGeoPoint = (GeoPoint)paramObject;
    }
    while ((this.f != localGeoPoint.f) || (this.g != localGeoPoint.g) || (this.d != localGeoPoint.d) || (this.e != localGeoPoint.e));
    return true;
  }

  public String f()
  {
    return "" + e.a(this.d) + "," + e.a(this.e);
  }

  public int getLatitudeE6()
  {
    return (int)this.d;
  }

  public int getLongitudeE6()
  {
    return (int)this.e;
  }

  public int hashCode()
  {
    return (int)(7.0D * this.g + 11.0D * this.f);
  }

  public String toString()
  {
    return "" + this.d + "," + this.e;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeLong(this.d);
    paramParcel.writeLong(this.e);
  }

  public static enum EnumMapProjection
  {
    static
    {
      EnumMapProjection[] arrayOfEnumMapProjection = new EnumMapProjection[2];
      arrayOfEnumMapProjection[0] = projection_900913;
      arrayOfEnumMapProjection[1] = projection_custBeijing54;
      $VALUES = arrayOfEnumMapProjection;
    }
  }

  public static class a
  {
    public int a;
    public int b;

    public a(int paramInt1, int paramInt2)
    {
      this.a = paramInt1;
      this.b = paramInt2;
    }
  }

  public static class b
  {
    public double a;
    public double b;

    public b(double paramDouble1, double paramDouble2)
    {
      this.a = paramDouble1;
      this.b = paramDouble2;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.core.GeoPoint
 * JD-Core Version:    0.6.0
 */