package com.autonavi.aps.api;

import java.io.ByteArrayInputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ApsRequest
{
  private String a = "";
  private String b = "";
  private String c = "";
  private String d = "";
  private String e = "";
  private String f = "";
  private String g = "";
  private String h = "";
  private String i = "";
  private String j = "";
  private String k = "";
  private String l = "";
  private String m = "";
  private String n = "";
  private String o = "";
  private String p = "";
  private String q = "";
  private String r = "";
  private String s = "";
  private String t = "";
  private String u = "";
  private String v = "";
  private String w = "";
  private String x = "";
  private String y = "";
  private String z = "";

  public String convertApsRequestXml(String paramString)
  {
    Utils.writeLogCat("aps version " + String.valueOf(Constant.apsVerion));
    if (Constant.apsVerion == 2)
    {
      setApsRequestProperty(paramString);
      Utils.writeLogCat("aps request xml do need convert, xml is " + paramString);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>");
      localStringBuffer.append("<Cell_Req ver=\"1.0\"><HDR version=\"1.0\" cdma=\"").append(getCdma()).append("\" gps=\"").append(getGps()).append("\" glong=\"").append(getGlong()).append("\" glat=\"").append(getGlat()).append("\" precision=\"\"><src>").append(getSrc()).append("</src><license>").append(getLicense()).append("</license><imei>").append(getImei()).append("</imei ></HDR><DRR phnum=\"").append(getLine1number()).append("\" nettype=\"").append(getNettype()).append("\" inftype=\"").append(getInftype()).append("\">");
      if (getCdma().equalsIgnoreCase("1"))
        localStringBuffer.append("<sid>").append(getSid()).append("</sid>").append("<nid>").append(getNid()).append("</nid>").append("<bid>").append(getBid()).append("</bid>").append("<lon>").append(getLon()).append("</lon>").append("<lat>").append(getLat()).append("</lat>");
      while (true)
      {
        localStringBuffer.append("<signal>").append(getSignal()).append("</signal><nb>").append(getNb()).append("</nb><mmac>").append(getMainmac()).append("</mmac><macs>").append(getMacs()).append("</macs></DRR></Cell_Req>");
        return localStringBuffer.toString();
        localStringBuffer.append("<mcc>").append(getMcc()).append("</mcc><mnc>").append(getMnc()).append("</mnc><lac>").append(getLac()).append("</lac><cellid>").append(getCellid()).append("</cellid>");
      }
    }
    Utils.writeLogCat("aps request xml do not need convert");
    return paramString;
  }

  public String getBid()
  {
    if ((this.n == null) || (this.n.equalsIgnoreCase("null")))
      this.n = "";
    return this.n;
  }

  public String getCdma()
  {
    if ((this.d == null) || (this.d.equalsIgnoreCase("null")))
      this.d = "";
    return this.d;
  }

  public String getCellid()
  {
    if ((this.j == null) || (this.j.equalsIgnoreCase("null")))
      this.j = "";
    return this.j;
  }

  public String getClienttime()
  {
    return this.g;
  }

  public String getGaccuracy()
  {
    return this.z;
  }

  public String getGlat()
  {
    if ((this.v == null) || (this.v.equalsIgnoreCase("null")))
      this.v = "";
    return this.v;
  }

  public String getGlong()
  {
    if ((this.u == null) || (this.u.equalsIgnoreCase("null")))
      this.u = "";
    return this.u;
  }

  public String getGps()
  {
    if ((this.t == null) || (this.t.equalsIgnoreCase("null")))
      this.t = "";
    return this.t;
  }

  public String getImei()
  {
    if ((this.c == null) || (this.c.equalsIgnoreCase("null")))
      this.c = "";
    return this.c;
  }

  public String getInftype()
  {
    if (getMainmac().length() > 1);
    for (this.y = "2"; ; this.y = "1")
      return this.y;
  }

  public String getLac()
  {
    if ((this.k == null) || (this.k.equalsIgnoreCase("null")))
      this.k = "";
    return this.k;
  }

  public String getLat()
  {
    if ((this.f == null) || (this.f.equalsIgnoreCase("null")))
      this.f = "";
    return this.f;
  }

  public String getLicense()
  {
    if ((this.a == null) || (this.a.equalsIgnoreCase("null")))
      this.a = "";
    return this.a;
  }

  public String getLine1number()
  {
    try
    {
      if (getNetwork().indexOf("line1number: ") != -1)
        if (getNetwork().indexOf(", wifi") == -1)
          break label93;
      label93: for (this.w = getNetwork().substring(13 + getNetwork().indexOf("line1number: "), getNetwork().indexOf(", wifi")); ; this.w = getNetwork().substring(13 + getNetwork().indexOf("line1number: ")))
      {
        if ((this.w == null) || (this.w.equalsIgnoreCase("null")))
          this.w = "";
        return this.w;
      }
    }
    catch (Exception localException)
    {
      while (true)
        Utils.printException(localException);
    }
  }

  public String getLon()
  {
    if ((this.e == null) || (this.e.equalsIgnoreCase("null")))
      this.e = "";
    return this.e;
  }

  public String getMacs()
  {
    if ((this.r == null) || (this.r.equalsIgnoreCase("null")))
      this.r = "";
    return this.r;
  }

  public String getMainmac()
  {
    if ((this.s == null) || (this.s.equalsIgnoreCase("null")))
      this.s = "";
    return this.s;
  }

  public String getMcc()
  {
    if ((this.h == null) || (this.h.equalsIgnoreCase("null")))
      this.h = "";
    return this.h;
  }

  public String getMnc()
  {
    if ((this.i == null) || (this.i.equalsIgnoreCase("null")))
      this.i = "";
    return this.i;
  }

  public String getNb()
  {
    if ((this.q == null) || (this.q.equalsIgnoreCase("null")))
      this.q = "";
    return this.q;
  }

  public String getNettype()
  {
    if (getNetwork().indexOf("nettype") != -1)
      this.x = getNetwork().substring(6 + getNetwork().indexOf("type: "), getNetwork().indexOf(", state: "));
    if ((this.x == null) || (this.x.equalsIgnoreCase("null")))
      this.x = "";
    return this.x;
  }

  public String getNetwork()
  {
    if ((this.o == null) || (this.o.equalsIgnoreCase("null")))
      this.o = "";
    return this.o;
  }

  public String getNid()
  {
    if ((this.m == null) || (this.m.equalsIgnoreCase("null")))
      this.m = "";
    return this.m;
  }

  public String getSid()
  {
    if ((this.l == null) || (this.l.equalsIgnoreCase("null")))
      this.l = "";
    return this.l;
  }

  public String getSignal()
  {
    if ((this.p == null) || (this.p.equalsIgnoreCase("null")))
      this.p = "";
    return this.p;
  }

  public String getSrc()
  {
    if ((this.b == null) || (this.b.equalsIgnoreCase("null")))
      this.b = "";
    return this.b;
  }

  public void setApsRequestProperty(String paramString)
  {
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramString.getBytes());
    SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();
    g localg = new g(this);
    try
    {
      localSAXParserFactory.newSAXParser().parse(localByteArrayInputStream, localg);
      return;
    }
    catch (Exception localException)
    {
      Utils.printException(localException);
    }
  }

  public void setBid(String paramString)
  {
    this.n = paramString;
  }

  public void setCdma(String paramString)
  {
    this.d = paramString;
  }

  public void setCellid(String paramString)
  {
    this.j = paramString;
  }

  public void setClienttime(String paramString)
  {
    if ((paramString == null) || (paramString.length() < 2))
      paramString = "0000-00-00-00-00-00";
    this.g = paramString;
  }

  public void setGaccuracy(String paramString)
  {
    this.z = paramString;
  }

  public void setGlat(String paramString)
  {
    this.v = paramString;
  }

  public void setGlong(String paramString)
  {
    this.u = paramString;
  }

  public void setGps(String paramString)
  {
    this.t = paramString;
  }

  public void setImei(String paramString)
  {
    this.c = paramString;
  }

  public void setLac(String paramString)
  {
    this.k = paramString;
  }

  public void setLat(String paramString)
  {
    this.f = paramString;
  }

  public void setLicense(String paramString)
  {
    this.a = paramString;
  }

  public void setLon(String paramString)
  {
    this.e = paramString;
  }

  public void setMacs(String paramString)
  {
    this.r = paramString;
  }

  public void setMainmac(String paramString)
  {
    this.s = paramString;
  }

  public void setMcc(String paramString)
  {
    this.h = paramString;
  }

  public void setMnc(String paramString)
  {
    this.i = paramString;
  }

  public void setNb(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 3))
    {
      String str = paramString.replace("*" + this.i + ",", "*");
      paramString = str.substring(1 + this.i.length(), str.length());
    }
    this.q = paramString;
  }

  public void setNetwork(String paramString)
  {
    this.o = paramString;
  }

  public void setNid(String paramString)
  {
    this.m = paramString;
  }

  public void setSid(String paramString)
  {
    this.l = paramString;
  }

  public void setSignal(String paramString)
  {
    this.p = paramString;
  }

  public void setSrc(String paramString)
  {
    this.b = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.autonavi.aps.api.ApsRequest
 * JD-Core Version:    0.6.0
 */