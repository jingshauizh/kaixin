package com.autonavi.aps.api;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

final class g extends DefaultHandler
{
  private StringBuffer a = new StringBuffer();

  g(ApsRequest paramApsRequest)
  {
  }

  public final void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    this.a.append(new String(paramArrayOfChar, paramInt1, paramInt2).toString());
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
  }

  public final void endElement(String paramString1, String paramString2, String paramString3)
  {
    if (paramString2.equals("license"))
      this.b.setLicense(this.a.toString());
    while (true)
    {
      super.endElement(paramString1, paramString2, paramString3);
      return;
      if (paramString2.equals("gaccuracy"))
      {
        this.b.setGaccuracy(this.a.toString());
        continue;
      }
      if (paramString2.equals("src"))
      {
        this.b.setSrc(this.a.toString());
        continue;
      }
      if (paramString2.equals("imei"))
      {
        this.b.setImei(this.a.toString());
        continue;
      }
      if (paramString2.equals("lac"))
      {
        this.b.setLac(this.a.toString());
        continue;
      }
      if (paramString2.equals("cellid"))
      {
        this.b.setCellid(this.a.toString());
        continue;
      }
      if (paramString2.equals("mnc"))
      {
        this.b.setMnc(this.a.toString());
        continue;
      }
      if (paramString2.equals("mcc"))
      {
        this.b.setMcc(this.a.toString());
        continue;
      }
      if (paramString2.equals("sid"))
      {
        this.b.setSid(this.a.toString());
        continue;
      }
      if (paramString2.equals("nid"))
      {
        this.b.setNid(this.a.toString());
        continue;
      }
      if (paramString2.equals("bid"))
      {
        this.b.setBid(this.a.toString());
        continue;
      }
      if (paramString2.equals("network"))
      {
        this.b.setNetwork(this.a.toString());
        continue;
      }
      if (paramString2.equals("gps"))
      {
        this.b.setGps(this.a.toString());
        continue;
      }
      if (paramString2.equals("cdma"))
      {
        this.b.setCdma(this.a.toString());
        continue;
      }
      if (paramString2.equals("glat"))
      {
        this.b.setGlat(this.a.toString());
        continue;
      }
      if (paramString2.equals("glong"))
      {
        this.b.setGlong(this.a.toString());
        continue;
      }
      if (paramString2.equals("lat"))
      {
        this.b.setLat(this.a.toString());
        continue;
      }
      if (paramString2.equals("lon"))
      {
        this.b.setLon(this.a.toString());
        continue;
      }
      if (paramString2.equals("nb"))
      {
        this.b.setNb(this.a.toString());
        continue;
      }
      if (paramString2.equals("signal"))
      {
        this.b.setSignal(this.a.toString());
        continue;
      }
      if (paramString2.equals("macs"))
      {
        this.b.setMacs(this.a.toString());
        continue;
      }
      if (paramString2.equals("mainmac"))
      {
        this.b.setMainmac(this.a.toString());
        continue;
      }
      if (!paramString2.equals("clienttime"))
        continue;
      this.b.setClienttime(this.a.toString());
    }
  }

  public final void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
  {
    this.a.delete(0, this.a.toString().length());
    super.startElement(paramString1, paramString2, paramString3, paramAttributes);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.autonavi.aps.api.g
 * JD-Core Version:    0.6.0
 */