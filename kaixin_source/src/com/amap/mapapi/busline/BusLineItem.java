package com.amap.mapapi.busline;

import com.amap.mapapi.core.GeoPoint;
import java.util.ArrayList;

public class BusLineItem
{
  private ArrayList<BusStationItem> A;
  private GeoPoint B = null;
  private GeoPoint C = null;
  private float a;
  private String b;
  private int c;
  private String d;
  private int e;
  private float f;
  private ArrayList<GeoPoint> g;
  private String h;
  private String i;
  private String j;
  private String k;
  private String l;
  private String m;
  private String n;
  private float o;
  private float p;
  private boolean q;
  private boolean r;
  private boolean s;
  private boolean t;
  private boolean u;
  private int v;
  private boolean w;
  private String x;
  private String y;
  private boolean z;

  private void a()
  {
    if ((this.g == null) || (this.g.size() == 0))
      return;
    int i1 = 2147483647;
    int i2 = -2147483648;
    int i3 = 2147483647;
    int i4 = -2147483648;
    for (int i5 = 0; i5 < this.g.size(); i5++)
    {
      GeoPoint localGeoPoint = (GeoPoint)this.g.get(i5);
      int i6 = localGeoPoint.getLongitudeE6();
      int i7 = localGeoPoint.getLatitudeE6();
      if (i6 < i3)
        i3 = i6;
      if (i7 < i1)
        i1 = i7;
      if (i6 > i2)
        i2 = i6;
      if (i7 <= i4)
        continue;
      i4 = i7;
    }
    this.B = new GeoPoint(i1, i3);
    this.C = new GeoPoint(i4, i2);
  }

  public GeoPoint getLowerLeftPoint()
  {
    if (this.B == null)
      a();
    return this.B;
  }

  public GeoPoint getUpperRightPoint()
  {
    if (this.C == null)
      a();
    return this.C;
  }

  public boolean getmAir()
  {
    return this.w;
  }

  public boolean getmAuto()
  {
    return this.r;
  }

  public float getmBasicPrice()
  {
    return this.o;
  }

  public boolean getmCommunicationTicket()
  {
    return this.q;
  }

  public String getmCompany()
  {
    return this.n;
  }

  public int getmDataSource()
  {
    return this.v;
  }

  public String getmDescription()
  {
    return this.d;
  }

  public String getmEndTime()
  {
    return this.m;
  }

  public String getmFrontName()
  {
    return this.j;
  }

  public String getmFrontSpell()
  {
    return this.x;
  }

  public String getmKeyName()
  {
    return this.i;
  }

  public float getmLength()
  {
    return this.a;
  }

  public String getmLineId()
  {
    return this.h;
  }

  public String getmName()
  {
    return this.b;
  }

  public float getmSpeed()
  {
    return this.f;
  }

  public String getmStartTime()
  {
    return this.l;
  }

  public ArrayList<BusStationItem> getmStations()
  {
    return this.A;
  }

  public int getmStatus()
  {
    return this.e;
  }

  public String getmTerminalName()
  {
    return this.k;
  }

  public String getmTerminalSpell()
  {
    return this.y;
  }

  public float getmTotalPrice()
  {
    return this.p;
  }

  public int getmType()
  {
    return this.c;
  }

  public ArrayList<GeoPoint> getmXys()
  {
    return this.g;
  }

  public boolean ismDoubleDeck()
  {
    return this.u;
  }

  public boolean ismExpressWay()
  {
    return this.z;
  }

  public boolean ismIcCard()
  {
    return this.s;
  }

  public boolean ismLoop()
  {
    return this.t;
  }

  public void setmAir(boolean paramBoolean)
  {
    this.w = paramBoolean;
  }

  public void setmAuto(boolean paramBoolean)
  {
    this.r = paramBoolean;
  }

  public void setmBasicPrice(float paramFloat)
  {
    this.o = paramFloat;
  }

  public void setmCommunicationTicket(boolean paramBoolean)
  {
    this.q = paramBoolean;
  }

  public void setmCompany(String paramString)
  {
    this.n = paramString;
  }

  public void setmDataSource(int paramInt)
  {
    this.v = paramInt;
  }

  public void setmDescription(String paramString)
  {
    this.d = paramString;
  }

  public void setmDoubleDeck(boolean paramBoolean)
  {
    this.u = paramBoolean;
  }

  public void setmEndTime(String paramString)
  {
    this.m = paramString;
  }

  public void setmExpressWay(boolean paramBoolean)
  {
    this.z = paramBoolean;
  }

  public void setmFrontName(String paramString)
  {
    this.j = paramString;
  }

  public void setmFrontSpell(String paramString)
  {
    this.x = paramString;
  }

  public void setmIcCard(boolean paramBoolean)
  {
    this.s = paramBoolean;
  }

  public void setmKeyName(String paramString)
  {
    this.i = paramString;
  }

  public void setmLength(float paramFloat)
  {
    this.a = paramFloat;
  }

  public void setmLineId(String paramString)
  {
    this.h = paramString;
  }

  public void setmLoop(boolean paramBoolean)
  {
    this.t = paramBoolean;
  }

  public void setmName(String paramString)
  {
    this.b = paramString;
  }

  public void setmSpeed(float paramFloat)
  {
    this.f = paramFloat;
  }

  public void setmStartTime(String paramString)
  {
    this.l = paramString;
  }

  public void setmStations(ArrayList<BusStationItem> paramArrayList)
  {
    this.A = paramArrayList;
  }

  public void setmStatus(int paramInt)
  {
    this.e = paramInt;
  }

  public void setmTerminalName(String paramString)
  {
    this.k = paramString;
  }

  public void setmTerminalSpell(String paramString)
  {
    this.y = paramString;
  }

  public void setmTotalPrice(float paramFloat)
  {
    this.p = paramFloat;
  }

  public void setmType(int paramInt)
  {
    this.c = paramInt;
  }

  public void setmXys(ArrayList<GeoPoint> paramArrayList)
  {
    this.g = paramArrayList;
  }

  public String toString()
  {
    return this.b + " " + this.l + "-" + this.m;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.busline.BusLineItem
 * JD-Core Version:    0.6.0
 */