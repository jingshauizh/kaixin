package com.google.zxing.client.result;

public final class ParsedResultType
{
  public static final ParsedResultType ADDRESSBOOK = new ParsedResultType("ADDRESSBOOK");
  public static final ParsedResultType ANDROID_INTENT;
  public static final ParsedResultType CALENDAR;
  public static final ParsedResultType EMAIL_ADDRESS = new ParsedResultType("EMAIL_ADDRESS");
  public static final ParsedResultType GEO;
  public static final ParsedResultType ISBN;
  public static final ParsedResultType MOBILETAG_RICH_WEB;
  public static final ParsedResultType NDEF_SMART_POSTER;
  public static final ParsedResultType PRODUCT = new ParsedResultType("PRODUCT");
  public static final ParsedResultType SMS;
  public static final ParsedResultType TEL;
  public static final ParsedResultType TEXT;
  public static final ParsedResultType URI = new ParsedResultType("URI");
  public static final ParsedResultType WIFI;
  private final String name;

  static
  {
    TEXT = new ParsedResultType("TEXT");
    ANDROID_INTENT = new ParsedResultType("ANDROID_INTENT");
    GEO = new ParsedResultType("GEO");
    TEL = new ParsedResultType("TEL");
    SMS = new ParsedResultType("SMS");
    CALENDAR = new ParsedResultType("CALENDAR");
    WIFI = new ParsedResultType("WIFI");
    NDEF_SMART_POSTER = new ParsedResultType("NDEF_SMART_POSTER");
    MOBILETAG_RICH_WEB = new ParsedResultType("MOBILETAG_RICH_WEB");
    ISBN = new ParsedResultType("ISBN");
  }

  private ParsedResultType(String paramString)
  {
    this.name = paramString;
  }

  public String toString()
  {
    return this.name;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.ParsedResultType
 * JD-Core Version:    0.6.0
 */