package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Hashtable;

public class UPCAWriter
  implements Writer
{
  private final EAN13Writer subWriter = new EAN13Writer();

  private static String preencode(String paramString)
  {
    int i = paramString.length();
    int j;
    int k;
    if (i == 11)
    {
      j = 0;
      k = 0;
      if (k >= 11)
        paramString = paramString + (1000 - j) % 10;
    }
    do
    {
      return '0' + paramString;
      int m = '￐' + paramString.charAt(k);
      if (k % 2 == 0);
      for (int n = 3; ; n = 1)
      {
        j += n * m;
        k++;
        break;
      }
    }
    while (i == 12);
    throw new IllegalArgumentException("Requested contents should be 11 or 12 digits long, but got " + paramString.length());
  }

  public BitMatrix encode(String paramString, BarcodeFormat paramBarcodeFormat, int paramInt1, int paramInt2)
    throws WriterException
  {
    return encode(paramString, paramBarcodeFormat, paramInt1, paramInt2, null);
  }

  public BitMatrix encode(String paramString, BarcodeFormat paramBarcodeFormat, int paramInt1, int paramInt2, Hashtable paramHashtable)
    throws WriterException
  {
    if (paramBarcodeFormat != BarcodeFormat.UPC_A)
      throw new IllegalArgumentException("Can only encode UPC-A, but got " + paramBarcodeFormat);
    return this.subWriter.encode(preencode(paramString), BarcodeFormat.EAN_13, paramInt1, paramInt2, paramHashtable);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.UPCAWriter
 * JD-Core Version:    0.6.0
 */