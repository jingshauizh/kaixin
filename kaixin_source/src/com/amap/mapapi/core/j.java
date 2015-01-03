package com.amap.mapapi.core;

import java.util.Random;

public class j
{
  private static j g;
  private String a = "http://webrd01.is.autonavi.com";
  private String b = "http://tm.mapabc.com";
  private String c = "http://restapi.amap.com";
  private String d = "http://ds.mapabc.com:8888";
  private String e = "http://mst01.is.autonavi.com";
  private String f = "http://tmds.mapabc.com";

  public static j a()
  {
    monitorenter;
    try
    {
      if (g == null)
        g = new j();
      j localj = g;
      return localj;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void a(String paramString)
  {
    this.e = paramString;
  }

  public String b()
  {
    String str = "";
    switch (new Random(System.currentTimeMillis()).nextInt(100000) % 4)
    {
    default:
    case 0:
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      this.a = str;
      return this.a;
      if (c.e == 2)
      {
        str = "http://wprd01.is.autonavi.com";
        continue;
      }
      str = "http://webrd01.is.autonavi.com";
      continue;
      if (c.e == 2)
      {
        str = "http://wprd02.is.autonavi.com";
        continue;
      }
      str = "http://webrd02.is.autonavi.com";
      continue;
      if (c.e == 2)
      {
        str = "http://wprd03.is.autonavi.com";
        continue;
      }
      str = "http://webrd03.is.autonavi.com";
      continue;
      if (c.e == 2)
      {
        str = "http://wprd04.is.autonavi.com";
        continue;
      }
      str = "http://webrd04.is.autonavi.com";
    }
  }

  public void b(String paramString)
  {
    this.f = paramString;
  }

  public String c()
  {
    return this.b;
  }

  public void c(String paramString)
  {
    this.a = paramString;
  }

  public String d()
  {
    return this.c;
  }

  public void d(String paramString)
  {
    this.b = paramString;
  }

  public String e()
  {
    String str = "";
    switch (new Random(System.currentTimeMillis()).nextInt(100000) % 4)
    {
    default:
    case 0:
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      this.e = str;
      return this.e;
      str = "http://mst01.is.autonavi.com";
      continue;
      str = "http://mst02.is.autonavi.com";
      continue;
      str = "http://mst03.is.autonavi.com";
      continue;
      str = "http://mst04.is.autonavi.com";
    }
  }

  public void e(String paramString)
  {
    this.c = paramString;
  }

  public String f()
  {
    return this.d;
  }

  public void f(String paramString)
  {
    this.d = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.core.j
 * JD-Core Version:    0.6.0
 */