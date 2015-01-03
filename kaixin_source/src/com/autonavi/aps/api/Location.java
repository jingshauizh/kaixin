package com.autonavi.aps.api;

public class Location
{
  private String a = "";
  private String b = "";
  private double c = 0.0D;
  private double d = 0.0D;
  private double e = 0.0D;
  private String f = "";
  private String g = "";
  private String h = "";
  private double i = 0.0D;
  private double j = 0.0D;
  private double k = 0.0D;

  public String getAdcode()
  {
    return this.h;
  }

  public double getCenx()
  {
    return this.c;
  }

  public double getCeny()
  {
    return this.d;
  }

  public String getCitycode()
  {
    return this.f;
  }

  public String getDesc()
  {
    return this.g;
  }

  public double getRadius()
  {
    return this.e;
  }

  public double getRawd()
  {
    return 0.0D;
  }

  public double getRawx()
  {
    return 0.0D;
  }

  public double getRawy()
  {
    return 0.0D;
  }

  public String getRdesc()
  {
    return this.b;
  }

  public String getResult()
  {
    return this.a;
  }

  public void setAdcode(String paramString)
  {
    this.h = paramString;
  }

  public void setCenx(double paramDouble)
  {
    this.c = paramDouble;
  }

  public void setCeny(double paramDouble)
  {
    this.d = paramDouble;
  }

  public void setCitycode(String paramString)
  {
    this.f = paramString;
  }

  public void setDesc(String paramString)
  {
    this.g = paramString;
  }

  public void setRadius(double paramDouble)
  {
    this.e = paramDouble;
  }

  public void setRawd(double paramDouble)
  {
    this.k = 0.0D;
  }

  public void setRawx(double paramDouble)
  {
    this.i = 0.0D;
  }

  public void setRawy(double paramDouble)
  {
    this.j = 0.0D;
  }

  public void setRdesc(String paramString)
  {
    this.b = paramString;
  }

  public void setResult(String paramString)
  {
    this.a = paramString;
  }

  public final String toString()
  {
    return "result:" + this.a + "\n" + "rdesc:" + this.b + "\n" + "cenx:" + this.c + "\n" + "ceny:" + this.d + "\n" + "radius:" + this.e + "\n" + "citycode:" + this.f + "\n" + "adcode:" + this.h + "\n" + "desc:" + this.g + "\n" + "rawx:" + 0.0D + "\n" + "rawy:" + 0.0D + "\n" + "rawd:" + 0.0D + "\n";
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.autonavi.aps.api.Location
 * JD-Core Version:    0.6.0
 */