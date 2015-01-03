package com.google.zxing;

import java.util.Hashtable;

public final class BarcodeFormat
{
  public static final BarcodeFormat AZTEC;
  public static final BarcodeFormat CODABAR;
  public static final BarcodeFormat CODE_128;
  public static final BarcodeFormat CODE_39;
  public static final BarcodeFormat CODE_93;
  public static final BarcodeFormat DATA_MATRIX;
  public static final BarcodeFormat EAN_13;
  public static final BarcodeFormat EAN_8;
  public static final BarcodeFormat ITF;
  public static final BarcodeFormat PDF_417;
  public static final BarcodeFormat QR_CODE;
  public static final BarcodeFormat RSS_14;
  public static final BarcodeFormat RSS_EXPANDED;
  public static final BarcodeFormat UPC_A;
  public static final BarcodeFormat UPC_E;
  public static final BarcodeFormat UPC_EAN_EXTENSION;
  private static final Hashtable VALUES = new Hashtable();
  private final String name;

  static
  {
    AZTEC = new BarcodeFormat("AZTEC");
    CODABAR = new BarcodeFormat("CODABAR");
    CODE_39 = new BarcodeFormat("CODE_39");
    CODE_93 = new BarcodeFormat("CODE_93");
    CODE_128 = new BarcodeFormat("CODE_128");
    DATA_MATRIX = new BarcodeFormat("DATA_MATRIX");
    EAN_8 = new BarcodeFormat("EAN_8");
    EAN_13 = new BarcodeFormat("EAN_13");
    ITF = new BarcodeFormat("ITF");
    PDF_417 = new BarcodeFormat("PDF_417");
    QR_CODE = new BarcodeFormat("QR_CODE");
    RSS_14 = new BarcodeFormat("RSS_14");
    RSS_EXPANDED = new BarcodeFormat("RSS_EXPANDED");
    UPC_A = new BarcodeFormat("UPC_A");
    UPC_E = new BarcodeFormat("UPC_E");
    UPC_EAN_EXTENSION = new BarcodeFormat("UPC_EAN_EXTENSION");
  }

  private BarcodeFormat(String paramString)
  {
    this.name = paramString;
    VALUES.put(paramString, this);
  }

  public static BarcodeFormat valueOf(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0))
      throw new IllegalArgumentException();
    BarcodeFormat localBarcodeFormat = (BarcodeFormat)VALUES.get(paramString);
    if (localBarcodeFormat == null)
      throw new IllegalArgumentException();
    return localBarcodeFormat;
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
 * Qualified Name:     com.google.zxing.BarcodeFormat
 * JD-Core Version:    0.6.0
 */