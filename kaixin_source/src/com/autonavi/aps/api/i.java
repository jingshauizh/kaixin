package com.autonavi.aps.api;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

final class i extends DefaultHandler
{
  public String a = "";
  private boolean b = false;

  i(ParserResponse paramParserResponse)
  {
  }

  public final void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    if (this.b)
      this.a = new String(paramArrayOfChar, paramInt1, paramInt2);
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
  }

  public final void endElement(String paramString1, String paramString2, String paramString3)
  {
    if (paramString2.equals("sres"))
      this.b = false;
    super.endElement(paramString1, paramString2, paramString3);
  }

  public final void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
  {
    if (paramString2.equals("sres"))
      this.b = true;
    super.startElement(paramString1, paramString2, paramString3, paramAttributes);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.autonavi.aps.api.i
 * JD-Core Version:    0.6.0
 */