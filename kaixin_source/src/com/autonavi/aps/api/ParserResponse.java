package com.autonavi.aps.api;

import java.io.ByteArrayInputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ParserResponse
{
  public Location ParserApsXml(String paramString)
  {
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramString.getBytes());
    SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();
    h localh = new h(this);
    try
    {
      localSAXParserFactory.newSAXParser().parse(localByteArrayInputStream, localh);
      return localh.a;
    }
    catch (Exception localException)
    {
      while (true)
        Utils.printException(localException);
    }
  }

  public String ParserSapsXml(String paramString)
  {
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramString.getBytes());
    SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();
    i locali = new i(this);
    try
    {
      localSAXParserFactory.newSAXParser().parse(localByteArrayInputStream, locali);
      return locali.a;
    }
    catch (Exception localException)
    {
      while (true)
        Utils.printException(localException);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.autonavi.aps.api.ParserResponse
 * JD-Core Version:    0.6.0
 */