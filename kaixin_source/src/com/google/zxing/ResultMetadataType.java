package com.google.zxing;

import java.util.Hashtable;

public final class ResultMetadataType
{
  public static final ResultMetadataType BYTE_SEGMENTS;
  public static final ResultMetadataType ERROR_CORRECTION_LEVEL;
  public static final ResultMetadataType ISSUE_NUMBER;
  public static final ResultMetadataType ORIENTATION;
  public static final ResultMetadataType OTHER;
  public static final ResultMetadataType POSSIBLE_COUNTRY;
  public static final ResultMetadataType SUGGESTED_PRICE;
  private static final Hashtable VALUES = new Hashtable();
  private final String name;

  static
  {
    OTHER = new ResultMetadataType("OTHER");
    ORIENTATION = new ResultMetadataType("ORIENTATION");
    BYTE_SEGMENTS = new ResultMetadataType("BYTE_SEGMENTS");
    ERROR_CORRECTION_LEVEL = new ResultMetadataType("ERROR_CORRECTION_LEVEL");
    ISSUE_NUMBER = new ResultMetadataType("ISSUE_NUMBER");
    SUGGESTED_PRICE = new ResultMetadataType("SUGGESTED_PRICE");
    POSSIBLE_COUNTRY = new ResultMetadataType("POSSIBLE_COUNTRY");
  }

  private ResultMetadataType(String paramString)
  {
    this.name = paramString;
    VALUES.put(paramString, this);
  }

  public static ResultMetadataType valueOf(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0))
      throw new IllegalArgumentException();
    ResultMetadataType localResultMetadataType = (ResultMetadataType)VALUES.get(paramString);
    if (localResultMetadataType == null)
      throw new IllegalArgumentException();
    return localResultMetadataType;
  }

  public String getName()
  {
    return this.name;
  }

  public String toString()
  {
    return this.name;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.ResultMetadataType
 * JD-Core Version:    0.6.0
 */