package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Hashtable;

public final class EAN13Writer extends UPCEANWriter
{
  private static final int codeWidth = 95;

  public BitMatrix encode(String paramString, BarcodeFormat paramBarcodeFormat, int paramInt1, int paramInt2, Hashtable paramHashtable)
    throws WriterException
  {
    if (paramBarcodeFormat != BarcodeFormat.EAN_13)
      throw new IllegalArgumentException("Can only encode EAN_13, but got " + paramBarcodeFormat);
    return super.encode(paramString, paramBarcodeFormat, paramInt1, paramInt2, paramHashtable);
  }

  public byte[] encode(String paramString)
  {
    if (paramString.length() != 13)
      throw new IllegalArgumentException("Requested contents should be 13 digits long, but got " + paramString.length());
    int i = Integer.parseInt(paramString.substring(0, 1));
    int j = EAN13Reader.FIRST_DIGIT_ENCODINGS[i];
    byte[] arrayOfByte = new byte[95];
    int k = 0 + appendPattern(arrayOfByte, 0, UPCEANReader.START_END_PATTERN, 1);
    int m = 1;
    int i1;
    if (m > 6)
      i1 = k + appendPattern(arrayOfByte, k, UPCEANReader.MIDDLE_PATTERN, 0);
    for (int i2 = 7; ; i2++)
    {
      if (i2 > 12)
      {
        (i1 + appendPattern(arrayOfByte, i1, UPCEANReader.START_END_PATTERN, 1));
        return arrayOfByte;
        int n = Integer.parseInt(paramString.substring(m, m + 1));
        if ((0x1 & j >> 6 - m) == 1)
          n += 10;
        k += appendPattern(arrayOfByte, k, UPCEANReader.L_AND_G_PATTERNS[n], 0);
        m++;
        break;
      }
      int i3 = Integer.parseInt(paramString.substring(i2, i2 + 1));
      i1 += appendPattern(arrayOfByte, i1, UPCEANReader.L_PATTERNS[i3], 1);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.EAN13Writer
 * JD-Core Version:    0.6.0
 */