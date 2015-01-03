package com.autonavi.aps.api;

public class CdmaCellBean
{
  private int a;
  private int b;
  private String c;
  private String d;
  private int e;
  private int f;
  private int g;
  private int h = 10;

  public int getBid()
  {
    return this.g;
  }

  public int getLat()
  {
    return this.a;
  }

  public int getLon()
  {
    return this.b;
  }

  public String getMcc()
  {
    return this.c;
  }

  public String getMnc()
  {
    return this.d;
  }

  public int getNid()
  {
    return this.f;
  }

  public int getSid()
  {
    return this.e;
  }

  public int getSignal()
  {
    return this.h;
  }

  public void setBid(int paramInt)
  {
    this.g = paramInt;
  }

  public void setLat(int paramInt)
  {
    if (paramInt < 2147483647)
      this.a = paramInt;
  }

  public void setLon(int paramInt)
  {
    if (this.a < 2147483647)
      this.b = paramInt;
  }

  public void setMcc(String paramString)
  {
    this.c = paramString;
  }

  public void setMnc(String paramString)
  {
    this.d = paramString;
  }

  public void setNid(int paramInt)
  {
    this.f = paramInt;
  }

  public void setSid(int paramInt)
  {
    this.e = paramInt;
  }

  public void setSignal(int paramInt)
  {
    this.h = paramInt;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.autonavi.aps.api.CdmaCellBean
 * JD-Core Version:    0.6.0
 */