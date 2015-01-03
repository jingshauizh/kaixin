package com.autonavi.aps.api;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

final class h extends DefaultHandler
{
  public Location a = new Location();
  private StringBuffer b = new StringBuffer();

  h(ParserResponse paramParserResponse)
  {
  }

  public final void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    this.b.append(new String(paramArrayOfChar, paramInt1, paramInt2).toString());
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
  }

  public final void endElement(String paramString1, String paramString2, String paramString3)
  {
    if (paramString2.equals("result"))
      this.a.setResult(this.b.toString());
    while (true)
    {
      super.endElement(paramString1, paramString2, paramString3);
      return;
      if (paramString2.equals("rdesc"))
      {
        this.a.setRdesc(this.b.toString());
        continue;
      }
      if (paramString2.equals("cenx"))
      {
        try
        {
          this.a.setCenx(Double.valueOf(this.b.toString()).doubleValue());
        }
        catch (Exception localException3)
        {
          Utils.printException(localException3);
          this.a.setCenx(0.0D);
        }
        continue;
      }
      if (paramString2.equals("ceny"))
      {
        try
        {
          this.a.setCeny(Double.valueOf(this.b.toString()).doubleValue());
        }
        catch (Exception localException2)
        {
          Utils.printException(localException2);
          this.a.setCeny(0.0D);
        }
        continue;
      }
      if (paramString2.equals("radius"))
      {
        try
        {
          this.a.setRadius(Double.valueOf(this.b.toString()).doubleValue());
        }
        catch (Exception localException1)
        {
          Utils.printException(localException1);
          this.a.setRadius(0.0D);
        }
        continue;
      }
      if (paramString2.equals("citycode"))
      {
        this.a.setCitycode(this.b.toString());
        continue;
      }
      if (paramString2.equals("desc"))
      {
        this.a.setDesc(this.b.toString());
        continue;
      }
      if (!paramString2.equals("adcode"))
        continue;
      this.a.setAdcode(this.b.toString());
    }
  }

  public final void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
  {
    this.b.delete(0, this.b.toString().length());
    super.startElement(paramString1, paramString2, paramString3, paramAttributes);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.autonavi.aps.api.h
 * JD-Core Version:    0.6.0
 */