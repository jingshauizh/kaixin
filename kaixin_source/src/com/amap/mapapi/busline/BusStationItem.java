package com.amap.mapapi.busline;

import com.amap.mapapi.core.GeoPoint;

public class BusStationItem
{
  private String a;
  private GeoPoint b;
  private String c;
  private String d;
  private int e;

  public String getmCode()
  {
    return this.d;
  }

  public GeoPoint getmCoord()
  {
    return this.b;
  }

  public String getmName()
  {
    return this.a;
  }

  public String getmSpell()
  {
    return this.c;
  }

  public int getmStationNum()
  {
    return this.e;
  }

  public void setmCode(String paramString)
  {
    this.d = paramString;
  }

  public void setmCoord(GeoPoint paramGeoPoint)
  {
    this.b = paramGeoPoint;
  }

  public void setmName(String paramString)
  {
    this.a = paramString;
  }

  public void setmSpell(String paramString)
  {
    this.c = paramString;
  }

  public void setmStationNum(int paramInt)
  {
    this.e = paramInt;
  }

  public String toString()
  {
    return "Name: " + this.a + " Coord: " + this.b.toString() + " Spell: " + this.c + " Code: " + this.d + " StationNum: " + this.e;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.busline.BusStationItem
 * JD-Core Version:    0.6.0
 */