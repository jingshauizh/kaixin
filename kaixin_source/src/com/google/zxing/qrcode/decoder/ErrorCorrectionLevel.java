package com.google.zxing.qrcode.decoder;

public final class ErrorCorrectionLevel
{
  private static final ErrorCorrectionLevel[] FOR_BITS;
  public static final ErrorCorrectionLevel H;
  public static final ErrorCorrectionLevel L = new ErrorCorrectionLevel(0, 1, "L");
  public static final ErrorCorrectionLevel M = new ErrorCorrectionLevel(1, 0, "M");
  public static final ErrorCorrectionLevel Q = new ErrorCorrectionLevel(2, 3, "Q");
  private final int bits;
  private final String name;
  private final int ordinal;

  static
  {
    H = new ErrorCorrectionLevel(3, 2, "H");
    ErrorCorrectionLevel[] arrayOfErrorCorrectionLevel = new ErrorCorrectionLevel[4];
    arrayOfErrorCorrectionLevel[0] = M;
    arrayOfErrorCorrectionLevel[1] = L;
    arrayOfErrorCorrectionLevel[2] = H;
    arrayOfErrorCorrectionLevel[3] = Q;
    FOR_BITS = arrayOfErrorCorrectionLevel;
  }

  private ErrorCorrectionLevel(int paramInt1, int paramInt2, String paramString)
  {
    this.ordinal = paramInt1;
    this.bits = paramInt2;
    this.name = paramString;
  }

  public static ErrorCorrectionLevel forBits(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= FOR_BITS.length))
      throw new IllegalArgumentException();
    return FOR_BITS[paramInt];
  }

  public int getBits()
  {
    return this.bits;
  }

  public String getName()
  {
    return this.name;
  }

  public int ordinal()
  {
    return this.ordinal;
  }

  public String toString()
  {
    return this.name;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
 * JD-Core Version:    0.6.0
 */