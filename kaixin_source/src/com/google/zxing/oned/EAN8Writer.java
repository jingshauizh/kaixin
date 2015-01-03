package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Hashtable;

public final class EAN8Writer extends UPCEANWriter
{
  private static final int codeWidth = 67;

  public BitMatrix encode(String paramString, BarcodeFormat paramBarcodeFormat, int paramInt1, int paramInt2, Hashtable paramHashtable)
    throws WriterException
  {
    if (paramBarcodeFormat != BarcodeFormat.EAN_8)
      throw new IllegalArgumentException("Can only encode EAN_8, but got " + paramBarcodeFormat);
    return super.encode(paramString, paramBarcodeFormat, paramInt1, paramInt2, paramHashtable);
  }

  public byte[] encode(String paramString)
  {
    if (paramString.length() != 8)
      throw new IllegalArgumentException("Requested contents should be 8 digits long, but got " + paramString.length());
    byte[] arrayOfByte = new byte[67];
    int i = 0 + appendPattern(arrayOfByte, 0, UPCEANReader.START_END_PATTERN, 1);
    int j = 0;
    int m;
    if (j > 3)
      m = i + appendPattern(arrayOfByte, i, UPCEANReader.MIDDLE_PATTERN, 0);
    for (int n = 4; ; n++)
    {
      if (n > 7)
      {
        (m + appendPattern(arrayOfByte, m, UPCEANReader.START_END_PATTERN, 1));
        return arrayOfByte;
        int k = Integer.parseInt(paramString.substring(j, j + 1));
        i += appendPattern(arrayOfByte, i, UPCEANReader.L_PATTERNS[k], 0);
        j++;
        break;
      }
      int i1 = Integer.parseInt(paramString.substring(n, n + 1));
      m += appendPattern(arrayOfByte, m, UPCEANReader.L_PATTERNS[i1], 1);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.EAN8Writer
 * JD-Core Version:    0.6.0
 */