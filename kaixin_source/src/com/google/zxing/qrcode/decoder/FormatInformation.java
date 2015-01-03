package com.google.zxing.qrcode.decoder;

final class FormatInformation
{
  private static final int[] BITS_SET_IN_HALF_BYTE;
  private static final int[][] FORMAT_INFO_DECODE_LOOKUP;
  private static final int FORMAT_INFO_MASK_QR = 21522;
  private final byte dataMask;
  private final ErrorCorrectionLevel errorCorrectionLevel;

  static
  {
    int[][] arrayOfInt = new int[32][];
    int[] arrayOfInt1 = new int[2];
    arrayOfInt1[0] = 21522;
    arrayOfInt[0] = arrayOfInt1;
    arrayOfInt[1] = { 20773, 1 };
    arrayOfInt[2] = { 24188, 2 };
    arrayOfInt[3] = { 23371, 3 };
    arrayOfInt[4] = { 17913, 4 };
    arrayOfInt[5] = { 16590, 5 };
    arrayOfInt[6] = { 20375, 6 };
    arrayOfInt[7] = { 19104, 7 };
    arrayOfInt[8] = { 30660, 8 };
    arrayOfInt[9] = { 29427, 9 };
    arrayOfInt[10] = { 32170, 10 };
    arrayOfInt[11] = { 30877, 11 };
    arrayOfInt[12] = { 26159, 12 };
    arrayOfInt[13] = { 25368, 13 };
    arrayOfInt[14] = { 27713, 14 };
    arrayOfInt[15] = { 26998, 15 };
    arrayOfInt[16] = { 5769, 16 };
    arrayOfInt[17] = { 5054, 17 };
    arrayOfInt[18] = { 7399, 18 };
    arrayOfInt[19] = { 6608, 19 };
    arrayOfInt[20] = { 1890, 20 };
    arrayOfInt[21] = { 597, 21 };
    arrayOfInt[22] = { 3340, 22 };
    arrayOfInt[23] = { 2107, 23 };
    arrayOfInt[24] = { 13663, 24 };
    arrayOfInt[25] = { 12392, 25 };
    arrayOfInt[26] = { 16177, 26 };
    arrayOfInt[27] = { 14854, 27 };
    arrayOfInt[28] = { 9396, 28 };
    arrayOfInt[29] = { 8579, 29 };
    arrayOfInt[30] = { 11994, 30 };
    arrayOfInt[31] = { 11245, 31 };
    FORMAT_INFO_DECODE_LOOKUP = arrayOfInt;
    int[] arrayOfInt2 = new int[16];
    arrayOfInt2[1] = 1;
    arrayOfInt2[2] = 1;
    arrayOfInt2[3] = 2;
    arrayOfInt2[4] = 1;
    arrayOfInt2[5] = 2;
    arrayOfInt2[6] = 2;
    arrayOfInt2[7] = 3;
    arrayOfInt2[8] = 1;
    arrayOfInt2[9] = 2;
    arrayOfInt2[10] = 2;
    arrayOfInt2[11] = 3;
    arrayOfInt2[12] = 2;
    arrayOfInt2[13] = 3;
    arrayOfInt2[14] = 3;
    arrayOfInt2[15] = 4;
    BITS_SET_IN_HALF_BYTE = arrayOfInt2;
  }

  private FormatInformation(int paramInt)
  {
    this.errorCorrectionLevel = ErrorCorrectionLevel.forBits(0x3 & paramInt >> 3);
    this.dataMask = (byte)(paramInt & 0x7);
  }

  static FormatInformation decodeFormatInformation(int paramInt1, int paramInt2)
  {
    FormatInformation localFormatInformation = doDecodeFormatInformation(paramInt1, paramInt2);
    if (localFormatInformation != null)
      return localFormatInformation;
    return doDecodeFormatInformation(paramInt1 ^ 0x5412, paramInt2 ^ 0x5412);
  }

  private static FormatInformation doDecodeFormatInformation(int paramInt1, int paramInt2)
  {
    int i = 2147483647;
    int j = 0;
    for (int k = 0; ; k++)
    {
      if (k >= FORMAT_INFO_DECODE_LOOKUP.length)
      {
        if (i > 3)
          break;
        return new FormatInformation(j);
      }
      int[] arrayOfInt = FORMAT_INFO_DECODE_LOOKUP[k];
      int m = arrayOfInt[0];
      if ((m == paramInt1) || (m == paramInt2))
        return new FormatInformation(arrayOfInt[1]);
      int n = numBitsDiffering(paramInt1, m);
      if (n < i)
      {
        j = arrayOfInt[1];
        i = n;
      }
      if (paramInt1 == paramInt2)
        continue;
      int i1 = numBitsDiffering(paramInt2, m);
      if (i1 >= i)
        continue;
      j = arrayOfInt[1];
      i = i1;
    }
    return null;
  }

  static int numBitsDiffering(int paramInt1, int paramInt2)
  {
    int i = paramInt1 ^ paramInt2;
    return BITS_SET_IN_HALF_BYTE[(i & 0xF)] + BITS_SET_IN_HALF_BYTE[(0xF & i >>> 4)] + BITS_SET_IN_HALF_BYTE[(0xF & i >>> 8)] + BITS_SET_IN_HALF_BYTE[(0xF & i >>> 12)] + BITS_SET_IN_HALF_BYTE[(0xF & i >>> 16)] + BITS_SET_IN_HALF_BYTE[(0xF & i >>> 20)] + BITS_SET_IN_HALF_BYTE[(0xF & i >>> 24)] + BITS_SET_IN_HALF_BYTE[(0xF & i >>> 28)];
  }

  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof FormatInformation));
    FormatInformation localFormatInformation;
    do
    {
      return false;
      localFormatInformation = (FormatInformation)paramObject;
    }
    while ((this.errorCorrectionLevel != localFormatInformation.errorCorrectionLevel) || (this.dataMask != localFormatInformation.dataMask));
    return true;
  }

  byte getDataMask()
  {
    return this.dataMask;
  }

  ErrorCorrectionLevel getErrorCorrectionLevel()
  {
    return this.errorCorrectionLevel;
  }

  public int hashCode()
  {
    return this.errorCorrectionLevel.ordinal() << 3 | this.dataMask;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.qrcode.decoder.FormatInformation
 * JD-Core Version:    0.6.0
 */