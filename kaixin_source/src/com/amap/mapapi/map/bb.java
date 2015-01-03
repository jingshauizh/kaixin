package com.amap.mapapi.map;

import com.amap.mapapi.core.GeoPoint;

class bb extends a
{
  private GeoPoint e;
  private GeoPoint f;
  private int g;
  private int h;
  private int i;
  private int j;
  private int k;
  private int l;
  private int m;
  private int n;
  private bc o;

  public bb(int paramInt1, int paramInt2, GeoPoint paramGeoPoint1, GeoPoint paramGeoPoint2, int paramInt3, bc parambc)
  {
    super(paramInt1, paramInt2);
    this.e = paramGeoPoint1;
    this.f = paramGeoPoint2;
    this.g = (int)this.e.c();
    this.h = (int)this.e.d();
    this.o = parambc;
    this.k = (int)Math.abs(paramGeoPoint2.c() - this.e.c());
    this.l = (int)Math.abs(paramGeoPoint2.d() - this.e.d());
    this.m = 7;
    a(paramInt3);
  }

  private int a(int paramInt1, int paramInt2, int paramInt3)
  {
    int i1;
    if (paramInt2 > paramInt1)
    {
      i1 = paramInt1 + paramInt3;
      if (i1 >= paramInt2)
      {
        this.n = 0;
        return paramInt2;
      }
    }
    else
    {
      i1 = paramInt1 - paramInt3;
      if (i1 <= paramInt2)
      {
        this.n = 0;
        return paramInt2;
      }
    }
    return i1;
  }

  private void a(int paramInt)
  {
    this.i = (this.k / this.m);
    this.j = (this.l / this.m);
  }

  protected void a()
  {
    int i1 = (int)this.f.c();
    int i2 = (int)this.f.d();
    if (!f())
    {
      this.g = i1;
      this.h = i2;
      this.o.a(new GeoPoint(this.h, this.g, false));
      return;
    }
    this.n = (1 + this.n);
    this.i += this.n * (1 + this.n);
    this.j += this.n * (1 + this.n);
    this.g = a(this.g, i1, this.i);
    this.h = a(this.h, i2, this.j);
    this.o.a(new GeoPoint(this.h, this.g, false));
  }

  protected void b()
  {
    this.o.b();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.bb
 * JD-Core Version:    0.6.0
 */