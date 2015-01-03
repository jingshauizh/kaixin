package com.amap.mapapi.map;

import android.graphics.Point;
import android.graphics.PointF;
import com.amap.mapapi.core.GeoPoint;
import com.amap.mapapi.core.GeoPoint.EnumMapProjection;
import com.amap.mapapi.core.c;
import com.amap.mapapi.core.e;
import java.util.ArrayList;

class ad
{
  double a = 156543.03390000001D;
  int b = 0;
  double c = -20037508.34D;
  double d = 20037508.34D;
  public int e = 3;
  public int f = 18;
  public int g = 10;
  public double[] h = null;
  public ae i = null;
  public GeoPoint j = null;
  public GeoPoint k = null;
  public Point l = null;
  public a m = null;
  ai.d n = null;
  private double o = 116.39716D;
  private double p = 39.916690000000003D;
  private double q = 0.01745329251994329D;

  public ad(ai.d paramd)
  {
    this.n = paramd;
  }

  private int a(int paramInt1, int paramInt2)
  {
    int i1 = 1;
    for (int i2 = 0; i2 < paramInt2; i2++)
      i1 *= paramInt1;
    return i1;
  }

  public float a(GeoPoint paramGeoPoint1, GeoPoint paramGeoPoint2)
  {
    double d1 = e.a(paramGeoPoint1.a());
    double d2 = e.a(paramGeoPoint1.b());
    double d3 = e.a(paramGeoPoint2.a());
    double d4 = e.a(paramGeoPoint2.b());
    double d5 = d1 * this.q;
    double d6 = d2 * this.q;
    double d7 = d3 * this.q;
    double d8 = d4 * this.q;
    double d9 = Math.sin(d5);
    double d10 = Math.sin(d6);
    double d11 = Math.cos(d5);
    double d12 = Math.cos(d6);
    double d13 = Math.sin(d7);
    double d14 = Math.sin(d8);
    double d15 = Math.cos(d7);
    double d16 = Math.cos(d8);
    double[] arrayOfDouble1 = new double[3];
    double[] arrayOfDouble2 = new double[3];
    arrayOfDouble1[0] = (d11 * d12);
    arrayOfDouble1[1] = (d12 * d9);
    arrayOfDouble1[2] = d10;
    arrayOfDouble2[0] = (d16 * d15);
    arrayOfDouble2[1] = (d16 * d13);
    arrayOfDouble2[2] = d14;
    return (float)(12742001.579854401D * Math.asin(Math.sqrt((arrayOfDouble1[0] - arrayOfDouble2[0]) * (arrayOfDouble1[0] - arrayOfDouble2[0]) + (arrayOfDouble1[1] - arrayOfDouble2[1]) * (arrayOfDouble1[1] - arrayOfDouble2[1]) + (arrayOfDouble1[2] - arrayOfDouble2[2]) * (arrayOfDouble1[2] - arrayOfDouble2[2])) / 2.0D));
  }

  PointF a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, PointF paramPointF, int paramInt5, int paramInt6)
  {
    PointF localPointF = new PointF();
    localPointF.x = (256 * (paramInt1 - paramInt3) + paramPointF.x);
    if (this.b == 0)
      localPointF.y = (256 * (paramInt2 - paramInt4) + paramPointF.y);
    while (true)
    {
      if ((256.0F + localPointF.x <= 0.0F) || (localPointF.x >= paramInt5) || (256.0F + localPointF.y <= 0.0F) || (localPointF.y >= paramInt6))
        localPointF = null;
      return localPointF;
      if (this.b != 1)
        continue;
      paramPointF.y -= 256 * (paramInt2 - paramInt4);
    }
  }

  PointF a(GeoPoint paramGeoPoint1, GeoPoint paramGeoPoint2, Point paramPoint, double paramDouble)
  {
    PointF localPointF = new PointF();
    localPointF.x = (float)((paramGeoPoint1.c() - paramGeoPoint2.c()) / paramDouble + paramPoint.x);
    localPointF.y = (float)(paramPoint.y - (paramGeoPoint1.d() - paramGeoPoint2.d()) / paramDouble);
    return localPointF;
  }

  public GeoPoint a(PointF paramPointF, GeoPoint paramGeoPoint, Point paramPoint, double paramDouble, a parama)
  {
    return b(b(paramPointF, paramGeoPoint, paramPoint, paramDouble, parama));
  }

  public GeoPoint a(GeoPoint paramGeoPoint)
  {
    if (paramGeoPoint == null);
    do
    {
      return null;
      if (c.h == GeoPoint.EnumMapProjection.projection_custBeijing54)
        return paramGeoPoint.e();
    }
    while (c.h != GeoPoint.EnumMapProjection.projection_900913);
    double d1 = paramGeoPoint.getLatitudeE6() / 1000000.0D;
    double d2 = 20037508.34D * (paramGeoPoint.getLongitudeE6() / 1000000.0D) / 180.0D;
    return new GeoPoint(20037508.34D * (Math.log(Math.tan(3.141592653589793D * (d1 + 90.0D) / 360.0D)) / 0.0174532925199433D) / 180.0D, d2, false);
  }

  public ArrayList<av.a> a(GeoPoint paramGeoPoint, int paramInt1, int paramInt2, int paramInt3)
  {
    double d1 = this.h[this.g];
    int i1 = (int)((paramGeoPoint.c() - this.c) / (256.0D * d1));
    double d2 = d1 * (i1 * 256) + this.c;
    double d3 = 0.0D;
    int i2;
    if (this.b == 0)
    {
      int i14 = (int)((this.d - paramGeoPoint.d()) / (256.0D * d1));
      d3 = this.d - d1 * (i14 * 256);
      i2 = i14;
    }
    while (true)
    {
      PointF localPointF1 = a(new GeoPoint(d3, d2, false), paramGeoPoint, this.l, d1);
      av.a locala1 = new av.a(i1, i2, this.g, -1);
      locala1.f = localPointF1;
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(locala1);
      int i3 = 1;
      int i4 = 0;
      int i5 = i1 - i3;
      label177: int i10;
      PointF localPointF4;
      if (i5 <= i1 + i3)
      {
        i10 = i2 + i3;
        localPointF4 = a(i5, i10, i1, i2, localPointF1, paramInt2, paramInt3);
        if (localPointF4 != null)
          if (i4 != 0)
            break label583;
      }
      label398: label576: label583: for (int i12 = 1; ; i12 = i4)
      {
        av.a locala5 = new av.a(i5, i10, this.g, -1);
        locala5.f = localPointF4;
        localArrayList.add(locala5);
        i4 = i12;
        int i11 = i2 - i3;
        PointF localPointF5 = a(i5, i11, i1, i2, localPointF1, paramInt2, paramInt3);
        if (localPointF5 != null)
        {
          if (i4 == 0)
            i4 = 1;
          av.a locala4 = new av.a(i5, i11, this.g, -1);
          locala4.f = localPointF5;
          localArrayList.add(locala4);
        }
        i5++;
        break label177;
        if (this.b != 1)
          break label590;
        int i13 = (int)((paramGeoPoint.d() - this.d) / (256.0D * d1));
        d3 = d1 * (256 * (i13 + 1));
        i2 = i13;
        break;
        int i6 = -1 + (i2 + i3);
        int i7;
        PointF localPointF2;
        if (i6 > i2 - i3)
        {
          i7 = i1 + i3;
          localPointF2 = a(i7, i6, i1, i2, localPointF1, paramInt2, paramInt3);
          if (localPointF2 != null)
            if (i4 != 0)
              break label576;
        }
        for (int i9 = 1; ; i9 = i4)
        {
          av.a locala3 = new av.a(i7, i6, this.g, -1);
          locala3.f = localPointF2;
          localArrayList.add(locala3);
          i4 = i9;
          int i8 = i1 - i3;
          PointF localPointF3 = a(i8, i6, i1, i2, localPointF1, paramInt2, paramInt3);
          if (localPointF3 != null)
          {
            if (i4 == 0)
              i4 = 1;
            av.a locala2 = new av.a(i8, i6, this.g, -1);
            locala2.f = localPointF3;
            localArrayList.add(locala2);
          }
          i6--;
          break label398;
          if (i4 == 0)
            return localArrayList;
          i3++;
          break;
        }
      }
      label590: i2 = 0;
    }
  }

  public void a()
  {
    if (this.i != null)
    {
      if (this.i.a > 0.0D)
        this.o = this.i.a;
      if (this.i.b > 0.0D)
        this.p = this.i.b;
      c.h = this.i.c;
      if (this.i.d > 0.0D)
        this.a = this.i.d;
      this.b = this.i.e;
      this.c = this.i.f;
      this.d = this.i.g;
      if (this.i.h >= 0)
        this.e = this.i.h;
      if (this.i.i >= 0)
        this.f = this.i.i;
      if (this.i.j >= 0)
        this.g = this.i.j;
    }
    this.h = new double[1 + this.f];
    for (int i1 = 0; i1 <= this.f; i1++)
    {
      double d1 = this.a / a(2, i1);
      this.h[i1] = d1;
    }
    GeoPoint localGeoPoint;
    if (c.h == GeoPoint.EnumMapProjection.projection_900913)
    {
      localGeoPoint = new GeoPoint(this.p, this.o, true);
      e.a = true;
    }
    while (true)
    {
      this.j = a(localGeoPoint);
      this.k = this.j.e();
      this.l = new Point(this.n.c() / 2, this.n.d() / 2);
      this.m = new a();
      if (c.h == GeoPoint.EnumMapProjection.projection_900913)
      {
        this.m.a = -20037508.0F;
        this.m.b = 20037508.0F;
        this.m.c = 20037508.0F;
        this.m.d = -20037508.0F;
      }
      return;
      localGeoPoint = new GeoPoint(this.p, this.o, false);
      e.a = false;
    }
  }

  public void a(Point paramPoint)
  {
    this.l = paramPoint;
  }

  public void a(PointF paramPointF1, PointF paramPointF2, int paramInt)
  {
    double d1 = this.h[paramInt];
    GeoPoint localGeoPoint1 = b(paramPointF1, this.j, this.l, d1, this.m);
    GeoPoint localGeoPoint2 = b(paramPointF2, this.j, this.l, d1, this.m);
    double d2 = localGeoPoint2.c() - localGeoPoint1.c();
    double d3 = localGeoPoint2.d() - localGeoPoint1.d();
    double d4 = d2 + this.j.c();
    double d5 = d3 + this.j.d();
    if (c.h == GeoPoint.EnumMapProjection.projection_900913)
    {
      while (d4 < this.m.a)
        d4 += this.m.b - this.m.a;
      while (d4 > this.m.b)
        d4 -= this.m.b - this.m.a;
      while (d5 < this.m.d)
        d5 += this.m.c - this.m.d;
      while (d5 > this.m.c)
        d5 -= this.m.c - this.m.d;
    }
    this.j.b(d5);
    this.j.a(d4);
  }

  public void a(ae paramae)
  {
    this.i = paramae;
    a();
  }

  public PointF b(GeoPoint paramGeoPoint1, GeoPoint paramGeoPoint2, Point paramPoint, double paramDouble)
  {
    PointF localPointF = a(a(paramGeoPoint1), paramGeoPoint2, paramPoint, paramDouble);
    return this.n.g().toScreenPoint(localPointF);
  }

  GeoPoint b(PointF paramPointF, GeoPoint paramGeoPoint, Point paramPoint, double paramDouble, a parama)
  {
    PointF localPointF = this.n.g().fromScreenPoint(paramPointF);
    float f1 = localPointF.x - paramPoint.x;
    float f2 = localPointF.y - paramPoint.y;
    double d1 = paramGeoPoint.c() + paramDouble * f1;
    double d2 = paramGeoPoint.d() - paramDouble * f2;
    if (c.h == GeoPoint.EnumMapProjection.projection_900913)
    {
      while (d1 < parama.a)
        d1 += parama.b - parama.a;
      while (d1 > parama.b)
        d1 -= parama.b - parama.a;
      while (d2 < parama.d)
        d2 += parama.c - parama.d;
      while (d2 > parama.c)
        d2 -= parama.c - parama.d;
    }
    return new GeoPoint(d2, d1, false);
  }

  public GeoPoint b(GeoPoint paramGeoPoint)
  {
    if (c.h == GeoPoint.EnumMapProjection.projection_custBeijing54)
      return new GeoPoint(paramGeoPoint.d(), paramGeoPoint.c(), ()paramGeoPoint.d(), ()paramGeoPoint.c());
    if (c.h == GeoPoint.EnumMapProjection.projection_900913)
    {
      float f1 = (float)(180.0D * paramGeoPoint.c() / 20037508.34D);
      return new GeoPoint((int)(1000000.0D * (float)(57.295779513082323D * (2.0D * Math.atan(Math.exp(3.141592653589793D * (float)(180.0D * paramGeoPoint.d() / 20037508.34D) / 180.0D)) - 1.570796326794897D))), (int)(1000000.0D * f1));
    }
    return null;
  }

  public av.a b()
  {
    double d1 = this.h[this.g];
    int i1 = (int)((this.j.c() - this.c) / (d1 * 256.0D));
    int i3;
    if (this.b == 0)
      i3 = (int)((this.d - this.j.d()) / (d1 * 256.0D));
    while (true)
    {
      return new av.a(i1, i3, this.g, -1);
      int i2 = this.b;
      i3 = 0;
      if (i2 != 1)
        continue;
      i3 = (int)((this.j.d() - this.d) / (d1 * 256.0D));
    }
  }

  static class a
  {
    float a;
    float b;
    float c;
    float d;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.ad
 * JD-Core Version:    0.6.0
 */